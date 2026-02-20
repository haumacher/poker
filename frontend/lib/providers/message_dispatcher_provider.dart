import 'dart:async';

import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/providers/connection_status_provider.dart';
import 'package:poker_app/providers/error_provider.dart';
import 'package:poker_app/providers/game_state_provider.dart';
import 'package:poker_app/providers/hand_result_provider.dart';
import 'package:poker_app/providers/hole_cards_provider.dart';
import 'package:poker_app/providers/player_seat_provider.dart';
import 'package:poker_app/providers/table_info_provider.dart';
import 'package:poker_app/providers/websocket_provider.dart';

final messageDispatcherProvider = Provider<MessageDispatcher>((ref) {
  final dispatcher = MessageDispatcher(ref);
  ref.onDispose(() => dispatcher.dispose());
  return dispatcher;
});

class MessageDispatcher {
  final Ref _ref;
  StreamSubscription? _messageSub;
  StreamSubscription? _statusSub;

  MessageDispatcher(this._ref);

  void start() {
    final ws = _ref.read(websocketServiceProvider);

    _statusSub = ws.connectionStatus.listen((connected) {
      _ref.read(connectionStatusProvider.notifier).state =
          connected ? ConnectionStatus.connected : ConnectionStatus.disconnected;
    });

    _messageSub = ws.messages.listen(_dispatch);
  }

  void _dispatch(ServerMessage msg) {
    if (msg is GameStateMsg) {
      _ref.read(gameStateProvider.notifier).state = msg;
      // Clear hand result when a new hand starts
      if (msg.phase == Phase.preFlop) {
        _ref.read(handResultProvider.notifier).state = null;
      }
    } else if (msg is TableInfoMsg) {
      _ref.read(tableInfoProvider.notifier).state = msg;
      if (msg.seat >= 0) {
        _ref.read(playerSeatProvider.notifier).state = msg.seat;
      }
    } else if (msg is HoleCardsMsg) {
      _ref.read(holeCardsProvider.notifier).state = msg;
    } else if (msg is HandResultMsg) {
      _ref.read(handResultProvider.notifier).state = msg;
    } else if (msg is ErrorMsg) {
      _ref.read(errorProvider.notifier).state = msg;
    }
    // PlayerJoinedMsg and PlayerLeftMsg are followed by GameStateMsg,
    // so we don't need separate handling.
  }

  void dispose() {
    _messageSub?.cancel();
    _statusSub?.cancel();
  }
}
