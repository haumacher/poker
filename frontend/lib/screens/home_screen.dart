import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import 'package:poker_app/l10n/app_strings.dart';
import 'package:poker_app/l10n/strings_provider.dart';
import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/providers/connection_status_provider.dart';
import 'package:poker_app/providers/message_dispatcher_provider.dart';
import 'package:poker_app/providers/table_info_provider.dart';
import 'package:poker_app/providers/user_preferences_provider.dart';
import 'package:poker_app/providers/websocket_provider.dart';

class HomeScreen extends ConsumerStatefulWidget {
  const HomeScreen({super.key});

  @override
  ConsumerState<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends ConsumerState<HomeScreen> {
  late final TextEditingController _displayNameController;
  late final TextEditingController _serverHostController;
  final _roomCodeController = TextEditingController();
  late int _selectedBlindIndex;
  late int _selectedTimeoutIndex;
  bool _loading = false;
  bool _displayNameValid = false;
  bool _displayNameTouched = false;

  static const _blindPresets = [
    (small: 5, big: 10),
    (small: 10, big: 20),
    (small: 25, big: 50),
  ];

  static const _timeoutSeconds = [-1, 30, 60];

  String _timeoutLabel(AppStrings s, int seconds) =>
      seconds < 0 ? s.noLimit : '${seconds}s';

  @override
  void initState() {
    super.initState();
    final prefs = ref.read(userPreferencesProvider);
    _displayNameController = TextEditingController(text: prefs.displayName);
    _serverHostController = TextEditingController(text: prefs.serverHost);
    _selectedBlindIndex = prefs.blindIndex;
    _selectedTimeoutIndex = prefs.timeoutIndex;
    _displayNameValid = prefs.displayName.trim().isNotEmpty;
    _displayNameController.addListener(_onDisplayNameChanged);
  }

  void _onDisplayNameChanged() {
    final valid = _displayNameController.text.trim().isNotEmpty;
    if (valid != _displayNameValid) {
      setState(() => _displayNameValid = valid);
    }
    ref.read(userPreferencesProvider.notifier).update(
      (state) => state.copyWith(displayName: _displayNameController.text),
    );
  }

  @override
  void dispose() {
    _displayNameController.removeListener(_onDisplayNameChanged);
    _displayNameController.dispose();
    _serverHostController.dispose();
    _roomCodeController.dispose();
    super.dispose();
  }

  Future<void> _createTable() async {
    final s = ref.read(stringsProvider);
    setState(() => _loading = true);
    try {
      final serverHost = _serverHostController.text;
      final ws = ref.read(websocketServiceProvider);
      await ws.connect('ws://$serverHost/ws');
      ref.read(messageDispatcherProvider).start();

      final blind = _blindPresets[_selectedBlindIndex];
      final timeout = _timeoutSeconds[_selectedTimeoutIndex];
      ws.send(CreateTableMsg(
        smallBlind: blind.small,
        bigBlind: blind.big,
        turnTimeoutSeconds: timeout,
      ));

      // Wait for TableInfoMsg from create
      final tableInfo = await _waitForTableInfo();
      if (tableInfo == null) return;

      // Now join the table we just created
      ws.send(JoinTableMsg(
        tableId: tableInfo.tableId,
        preferredSeat: -1,
        displayName: _displayNameController.text,
      ));

      // Wait for TableInfoMsg with seat assignment
      final joinInfo = await _waitForTableInfo(
        condition: (msg) => msg.seat >= 0,
      );
      if (joinInfo != null && mounted) {
        context.go('/table');
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(s.connectionFailed(e.toString()))),
        );
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<void> _joinTable() async {
    final s = ref.read(stringsProvider);
    final roomCode = _roomCodeController.text.trim();
    if (roomCode.length != 6) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(s.roomCodeInvalid)),
      );
      return;
    }

    setState(() => _loading = true);
    try {
      final serverHost = _serverHostController.text;
      final ws = ref.read(websocketServiceProvider);
      if (!ws.isConnected) {
        await ws.connect('ws://$serverHost/ws');
        ref.read(messageDispatcherProvider).start();
      }

      ws.send(JoinTableMsg(
        tableId: roomCode,
        preferredSeat: -1,
        displayName: _displayNameController.text,
      ));

      final joinInfo = await _waitForTableInfo(
        condition: (msg) => msg.seat >= 0,
      );
      if (joinInfo != null && mounted) {
        context.go('/table');
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(s.connectionFailed(e.toString()))),
        );
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<TableInfoMsg?> _waitForTableInfo({
    bool Function(TableInfoMsg)? condition,
  }) async {
    final s = ref.read(stringsProvider);
    final completer = Completer<TableInfoMsg?>();
    late final ProviderSubscription sub;

    sub = ref.listenManual(tableInfoProvider, (prev, next) {
      if (next != null && (condition == null || condition(next))) {
        sub.close();
        if (!completer.isCompleted) completer.complete(next);
      }
    });

    // Timeout after 5 seconds
    return completer.future.timeout(
      const Duration(seconds: 5),
      onTimeout: () {
        sub.close();
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(s.serverTimeout)),
          );
        }
        return null;
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    final connectionStatus = ref.watch(connectionStatusProvider);
    final s = ref.watch(stringsProvider);

    return Scaffold(
      appBar: AppBar(title: Text(s.appTitle)),
      body: Center(
        child: ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 400),
          child: ListView(
            padding: const EdgeInsets.all(24),
            children: [
              // Language toggle
              SegmentedButton<AppLocale>(
                segments: const [
                  ButtonSegment(value: AppLocale.en, label: Text('English')),
                  ButtonSegment(value: AppLocale.de, label: Text('Deutsch')),
                ],
                selected: {ref.watch(userPreferencesProvider).locale},
                onSelectionChanged: (selected) {
                  ref.read(userPreferencesProvider.notifier).update(
                    (state) => state.copyWith(locale: selected.first),
                  );
                },
              ),
              const SizedBox(height: 24),

              // Server host
              TextField(
                decoration: InputDecoration(labelText: s.server),
                controller: _serverHostController,
                onChanged: (v) {
                  ref.read(userPreferencesProvider.notifier).update(
                    (state) => state.copyWith(serverHost: v),
                  );
                },
              ),
              const SizedBox(height: 16),

              // Display name
              TextField(
                controller: _displayNameController,
                decoration: InputDecoration(
                  labelText: s.displayName,
                  errorText: _displayNameTouched && !_displayNameValid
                      ? s.displayNameRequired
                      : null,
                ),
                textCapitalization: TextCapitalization.words,
                onChanged: (_) {
                  if (!_displayNameTouched) {
                    setState(() => _displayNameTouched = true);
                  }
                },
              ),
              const SizedBox(height: 32),

              // Create table section
              Text(s.createTable, style: Theme.of(context).textTheme.titleMedium),
              const SizedBox(height: 12),
              SegmentedButton<int>(
                segments: [
                  for (var i = 0; i < _blindPresets.length; i++)
                    ButtonSegment(
                      value: i,
                      label: Text('${_blindPresets[i].small}/${_blindPresets[i].big}'),
                    ),
                ],
                selected: {_selectedBlindIndex},
                onSelectionChanged: (sel) {
                  setState(() => _selectedBlindIndex = sel.first);
                  ref.read(userPreferencesProvider.notifier).update(
                    (state) => state.copyWith(blindIndex: sel.first),
                  );
                },
              ),
              const SizedBox(height: 12),
              Text(s.turnTimer, style: Theme.of(context).textTheme.bodySmall),
              const SizedBox(height: 4),
              SegmentedButton<int>(
                segments: [
                  for (var i = 0; i < _timeoutSeconds.length; i++)
                    ButtonSegment(
                      value: i,
                      label: Text(_timeoutLabel(s, _timeoutSeconds[i])),
                    ),
                ],
                selected: {_selectedTimeoutIndex},
                onSelectionChanged: (sel) {
                  setState(() => _selectedTimeoutIndex = sel.first);
                  ref.read(userPreferencesProvider.notifier).update(
                    (state) => state.copyWith(timeoutIndex: sel.first),
                  );
                },
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: _loading || !_displayNameValid ? null : _createTable,
                child: _loading
                    ? const SizedBox(
                        height: 20,
                        width: 20,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : Text(s.createTable),
              ),
              const SizedBox(height: 32),

              // Join table section
              Text(s.joinTable, style: Theme.of(context).textTheme.titleMedium),
              const SizedBox(height: 12),
              TextField(
                controller: _roomCodeController,
                decoration: InputDecoration(
                  labelText: s.roomCode,
                  hintText: s.roomCodeHint,
                ),
                textCapitalization: TextCapitalization.characters,
                maxLength: 6,
              ),
              const SizedBox(height: 4),
              ElevatedButton(
                onPressed: _loading || !_displayNameValid ? null : _joinTable,
                child: Text(s.joinTable),
              ),

              if (connectionStatus == ConnectionStatus.connected)
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Text(s.connected, style: const TextStyle(color: Colors.green)),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
