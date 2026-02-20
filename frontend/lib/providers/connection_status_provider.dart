import 'package:flutter_riverpod/flutter_riverpod.dart';

enum ConnectionStatus { disconnected, connecting, connected, reconnecting }

final connectionStatusProvider = StateProvider<ConnectionStatus>(
  (ref) => ConnectionStatus.disconnected,
);
