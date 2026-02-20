import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/services/websocket_service.dart';

final websocketServiceProvider = Provider<WebSocketService>((ref) {
  final service = WebSocketService();
  ref.onDispose(() => service.dispose());
  return service;
});
