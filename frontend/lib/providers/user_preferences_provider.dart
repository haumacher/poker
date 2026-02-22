import 'package:flutter_riverpod/flutter_riverpod.dart';

class UserPreferences {
  final String displayName;
  final int blindIndex;
  final int timeoutIndex;
  final String serverHost;

  const UserPreferences({
    this.displayName = '',
    this.blindIndex = 0,
    this.timeoutIndex = 1,
    this.serverHost = 'localhost:8080',
  });

  UserPreferences copyWith({
    String? displayName,
    int? blindIndex,
    int? timeoutIndex,
    String? serverHost,
  }) {
    return UserPreferences(
      displayName: displayName ?? this.displayName,
      blindIndex: blindIndex ?? this.blindIndex,
      timeoutIndex: timeoutIndex ?? this.timeoutIndex,
      serverHost: serverHost ?? this.serverHost,
    );
  }
}

final userPreferencesProvider = StateProvider<UserPreferences>(
  (ref) => const UserPreferences(),
);
