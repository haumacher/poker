import 'dart:async';
import 'dart:math';

import 'package:web_socket_channel/web_socket_channel.dart';

import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/services/message_codec.dart';

class WebSocketService {
  WebSocketChannel? _channel;
  final _messageController = StreamController<ServerMessage>.broadcast();
  final _statusController = StreamController<bool>.broadcast();
  bool _disposed = false;
  String? _lastUrl;
  Timer? _reconnectTimer;
  int _reconnectAttempt = 0;

  Stream<ServerMessage> get messages => _messageController.stream;
  Stream<bool> get connectionStatus => _statusController.stream;
  bool get isConnected => _channel != null;

  Future<void> connect(String url) async {
    _reconnectTimer?.cancel();
    _reconnectAttempt = 0;
    await _doConnect(url);
  }

  Future<void> _doConnect(String url) async {
    await _closeChannel();
    _lastUrl = url;
    final uri = Uri.parse(url);
    _channel = WebSocketChannel.connect(uri);
    await _channel!.ready;
    _reconnectAttempt = 0;
    _statusController.add(true);

    _channel!.stream.listen(
      (data) {
        if (data is String) {
          final msg = decodeServerMessage(data);
          if (msg != null) {
            _messageController.add(msg);
          }
        }
      },
      onDone: () {
        _channel = null;
        _statusController.add(false);
        _scheduleReconnect();
      },
      onError: (error) {
        _channel = null;
        _statusController.add(false);
        _scheduleReconnect();
      },
    );
  }

  void _scheduleReconnect() {
    if (_disposed || _lastUrl == null) return;
    _reconnectAttempt++;
    // Exponential backoff: 1s, 2s, 4s, 8s, max 15s
    final delay = Duration(seconds: min(pow(2, _reconnectAttempt - 1).toInt(), 15));
    _reconnectTimer = Timer(delay, () async {
      if (_disposed || _channel != null) return;
      try {
        await _doConnect(_lastUrl!);
      } catch (_) {
        // Will retry via onDone/onError
      }
    });
  }

  void send(ClientMessage msg) {
    _channel?.sink.add(encodeClientMessage(msg));
  }

  Future<void> disconnect() async {
    _reconnectTimer?.cancel();
    _lastUrl = null;
    await _closeChannel();
  }

  Future<void> _closeChannel() async {
    final ch = _channel;
    _channel = null;
    await ch?.sink.close();
  }

  void dispose() {
    if (_disposed) return;
    _disposed = true;
    _reconnectTimer?.cancel();
    disconnect();
    _messageController.close();
    _statusController.close();
  }
}
