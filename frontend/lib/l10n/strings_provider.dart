import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/l10n/app_strings.dart';
import 'package:poker_app/providers/user_preferences_provider.dart';

final stringsProvider = Provider<AppStrings>((ref) {
  final locale = ref.watch(
    userPreferencesProvider.select((p) => p.locale),
  );
  return strings[locale] ?? en;
});
