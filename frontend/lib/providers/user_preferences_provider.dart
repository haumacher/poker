import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/l10n/app_strings.dart';

class UserPreferences {
  final String displayName;
  final int blindIndex;
  final int timeoutIndex;
  final String serverHost;
  final AppLocale locale;

  const UserPreferences({
    this.displayName = '',
    this.blindIndex = 0,
    this.timeoutIndex = 1,
    this.serverHost = 'localhost:8080',
    this.locale = AppLocale.en,
  });

  UserPreferences copyWith({
    String? displayName,
    int? blindIndex,
    int? timeoutIndex,
    String? serverHost,
    AppLocale? locale,
  }) {
    return UserPreferences(
      displayName: displayName ?? this.displayName,
      blindIndex: blindIndex ?? this.blindIndex,
      timeoutIndex: timeoutIndex ?? this.timeoutIndex,
      serverHost: serverHost ?? this.serverHost,
      locale: locale ?? this.locale,
    );
  }
}

final userPreferencesProvider = StateProvider<UserPreferences>(
  (ref) => const UserPreferences(),
);
