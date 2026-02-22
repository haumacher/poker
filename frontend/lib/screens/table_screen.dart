import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import 'package:poker_app/l10n/strings_provider.dart';
import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/providers/connection_status_provider.dart';
import 'package:poker_app/providers/error_provider.dart';
import 'package:poker_app/providers/game_state_provider.dart';
import 'package:poker_app/providers/hand_result_provider.dart';
import 'package:poker_app/providers/hole_cards_provider.dart';
import 'package:poker_app/providers/player_seat_provider.dart';
import 'package:poker_app/providers/table_info_provider.dart';
import 'package:poker_app/providers/websocket_provider.dart';
import 'package:poker_app/widgets/action_bar/action_bar.dart';
import 'package:poker_app/widgets/common/room_code_display.dart';
import 'package:poker_app/widgets/poker_table/poker_table.dart';

class TableScreen extends ConsumerStatefulWidget {
  const TableScreen({super.key});

  @override
  ConsumerState<TableScreen> createState() => _TableScreenState();
}

class _TableScreenState extends ConsumerState<TableScreen> {
  bool _confirmed = false;

  @override
  void initState() {
    super.initState();
    // Listen for errors to show snackbars
    ref.listenManual(errorProvider, (prev, next) {
      if (next != null && mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(next.message)),
        );
        ref.read(errorProvider.notifier).state = null;
      }
    });

    // Reset confirmed state when a new hand result arrives
    ref.listenManual(handResultProvider, (prev, next) {
      if (next != null && prev == null) {
        setState(() => _confirmed = false);
      }
    });
  }

  void _leaveTable() {
    ref.read(websocketServiceProvider).send(LeaveTableMsg());
    ref.read(websocketServiceProvider).disconnect();
    // Reset state
    ref.read(gameStateProvider.notifier).state = null;
    ref.read(tableInfoProvider.notifier).state = null;
    ref.read(holeCardsProvider.notifier).state = null;
    ref.read(handResultProvider.notifier).state = null;
    ref.read(playerSeatProvider.notifier).state = -1;
    if (mounted) context.go('/');
  }

  void _confirmResult() {
    setState(() => _confirmed = true);
    ref.read(websocketServiceProvider).send(ConfirmResultMsg());
  }

  @override
  Widget build(BuildContext context) {
    final tableInfo = ref.watch(tableInfoProvider);
    final gameState = ref.watch(gameStateProvider);
    final mySeat = ref.watch(playerSeatProvider);
    final connectionStatus = ref.watch(connectionStatusProvider);
    final handResult = ref.watch(handResultProvider);
    final s = ref.watch(stringsProvider);

    final showContinue = handResult != null && !_confirmed;

    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: _leaveTable,
        ),
        title: tableInfo != null
            ? RoomCodeDisplay(roomCode: tableInfo.roomCode)
            : Text(s.table),
        actions: [
          if (connectionStatus != ConnectionStatus.connected)
            const Padding(
              padding: EdgeInsets.only(right: 12),
              child: Icon(Icons.cloud_off, color: Colors.red),
            ),
        ],
      ),
      body: Stack(
        children: [
          Column(
            children: [
              // Hand result banner
              if (handResult != null)
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                  color: Colors.amber.withValues(alpha: 0.2),
                  child: Text(
                    handResult.winners
                        .map((w) =>
                            s.playerWins(_playerName(gameState, w.seat), w.amount, w.handDescription))
                        .join(' | '),
                    textAlign: TextAlign.center,
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                ),

              // Poker table
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8),
                  child: PokerTable(
                    gameState: gameState,
                    mySeat: mySeat,
                    handResult: handResult,
                  ),
                ),
              ),

              // Action bar
              ActionBar(
                gameState: gameState,
                mySeat: mySeat,
                strings: s,
                onAction: (action, amount) {
                  ref.read(websocketServiceProvider).send(
                        PlayerActionMsg(actionType: action, amount: amount),
                      );
                },
                showContinue: showContinue,
                onContinue: _confirmResult,
              ),
            ],
          ),

          // Reconnection overlay
          if (connectionStatus == ConnectionStatus.disconnected ||
              connectionStatus == ConnectionStatus.reconnecting)
            Container(
              color: Colors.black54,
              child: Center(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const CircularProgressIndicator(),
                    const SizedBox(height: 16),
                    Text(s.reconnecting, style: const TextStyle(fontSize: 18)),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }

  String _playerName(GameStateMsg? state, int seat) {
    final s = ref.read(stringsProvider);
    if (state == null) return s.seat(seat);
    for (final p in state.players) {
      if (p.seat == seat) return p.displayName.isEmpty ? s.seat(seat) : p.displayName;
    }
    return s.seat(seat);
  }
}
