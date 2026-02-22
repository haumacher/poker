import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/providers/connection_status_provider.dart';
import 'package:poker_app/providers/message_dispatcher_provider.dart';
import 'package:poker_app/providers/table_info_provider.dart';
import 'package:poker_app/providers/websocket_provider.dart';

class HomeScreen extends ConsumerStatefulWidget {
  const HomeScreen({super.key});

  @override
  ConsumerState<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends ConsumerState<HomeScreen> {
  final _displayNameController = TextEditingController(text: '');
  final _roomCodeController = TextEditingController();
  int _selectedBlindIndex = 0;
  int _selectedTimeoutIndex = 1;
  bool _loading = false;
  bool _displayNameValid = false;
  bool _displayNameTouched = false;
  String _serverHost = 'localhost:8080';

  static const _blindPresets = [
    (small: 5, big: 10),
    (small: 10, big: 20),
    (small: 25, big: 50),
  ];

  static const _timeoutPresets = [
    (label: 'No Limit', seconds: -1),
    (label: '30s', seconds: 30),
    (label: '60s', seconds: 60),
  ];

  @override
  void initState() {
    super.initState();
    _displayNameController.addListener(_onDisplayNameChanged);
  }

  void _onDisplayNameChanged() {
    final valid = _displayNameController.text.trim().isNotEmpty;
    if (valid != _displayNameValid) {
      setState(() => _displayNameValid = valid);
    }
  }

  @override
  void dispose() {
    _displayNameController.removeListener(_onDisplayNameChanged);
    _displayNameController.dispose();
    _roomCodeController.dispose();
    super.dispose();
  }

  Future<void> _createTable() async {
    setState(() => _loading = true);
    try {
      final ws = ref.read(websocketServiceProvider);
      await ws.connect('ws://$_serverHost/ws');
      ref.read(messageDispatcherProvider).start();

      final blind = _blindPresets[_selectedBlindIndex];
      final timeout = _timeoutPresets[_selectedTimeoutIndex];
      ws.send(CreateTableMsg(
        smallBlind: blind.small,
        bigBlind: blind.big,
        turnTimeoutSeconds: timeout.seconds,
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
          SnackBar(content: Text('Connection failed: $e')),
        );
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<void> _joinTable() async {
    final roomCode = _roomCodeController.text.trim();
    if (roomCode.length != 6) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Room code must be 6 characters')),
      );
      return;
    }

    setState(() => _loading = true);
    try {
      final ws = ref.read(websocketServiceProvider);
      if (!ws.isConnected) {
        await ws.connect('ws://$_serverHost/ws');
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
          SnackBar(content: Text('Connection failed: $e')),
        );
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<TableInfoMsg?> _waitForTableInfo({
    bool Function(TableInfoMsg)? condition,
  }) async {
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
            const SnackBar(content: Text('Server did not respond in time')),
          );
        }
        return null;
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    final connectionStatus = ref.watch(connectionStatusProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Poker')),
      body: Center(
        child: ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 400),
          child: ListView(
            padding: const EdgeInsets.all(24),
            children: [
              // Server host
              TextField(
                decoration: const InputDecoration(labelText: 'Server'),
                onChanged: (v) => _serverHost = v,
                controller: TextEditingController(text: _serverHost),
              ),
              const SizedBox(height: 16),

              // Display name
              TextField(
                controller: _displayNameController,
                decoration: InputDecoration(
                  labelText: 'Display Name *',
                  errorText: _displayNameTouched && !_displayNameValid
                      ? 'Display name is required'
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
              Text('Create Table', style: Theme.of(context).textTheme.titleMedium),
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
                onSelectionChanged: (s) => setState(() => _selectedBlindIndex = s.first),
              ),
              const SizedBox(height: 12),
              Text('Turn Timer', style: Theme.of(context).textTheme.bodySmall),
              const SizedBox(height: 4),
              SegmentedButton<int>(
                segments: [
                  for (var i = 0; i < _timeoutPresets.length; i++)
                    ButtonSegment(
                      value: i,
                      label: Text(_timeoutPresets[i].label),
                    ),
                ],
                selected: {_selectedTimeoutIndex},
                onSelectionChanged: (s) => setState(() => _selectedTimeoutIndex = s.first),
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
                    : const Text('Create Table'),
              ),
              const SizedBox(height: 32),

              // Join table section
              Text('Join Table', style: Theme.of(context).textTheme.titleMedium),
              const SizedBox(height: 12),
              TextField(
                controller: _roomCodeController,
                decoration: const InputDecoration(
                  labelText: 'Room Code',
                  hintText: 'e.g. ABC123',
                ),
                textCapitalization: TextCapitalization.characters,
                maxLength: 6,
              ),
              const SizedBox(height: 4),
              ElevatedButton(
                onPressed: _loading || !_displayNameValid ? null : _joinTable,
                child: const Text('Join Table'),
              ),

              if (connectionStatus == ConnectionStatus.connected)
                const Padding(
                  padding: EdgeInsets.only(top: 16),
                  child: Text('Connected', style: TextStyle(color: Colors.green)),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
