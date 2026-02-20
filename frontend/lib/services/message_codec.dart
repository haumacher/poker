import 'package:poker_app/models/poker_messages.dart';

/// Decodes a raw WebSocket string into a [ServerMessage].
///
/// Returns `null` if the message cannot be parsed.
ServerMessage? decodeServerMessage(String raw) {
  return ServerMessage.fromString(raw);
}

/// Encodes a [ClientMessage] into a JSON string for sending over WebSocket.
String encodeClientMessage(ClientMessage msg) {
  return msg.toString();
}
