import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import 'package:poker_app/l10n/strings_provider.dart';
import 'package:poker_app/screens/home_screen.dart';
import 'package:poker_app/screens/table_screen.dart';
import 'package:poker_app/theme/app_theme.dart';

final _router = GoRouter(
  routes: [
    GoRoute(path: '/', builder: (context, state) => const HomeScreen()),
    GoRoute(path: '/table', builder: (context, state) => const TableScreen()),
  ],
);

class PokerApp extends ConsumerWidget {
  const PokerApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final s = ref.watch(stringsProvider);
    return MaterialApp.router(
      title: s.appTitle,
      theme: AppTheme.dark,
      routerConfig: _router,
      debugShowCheckedModeBanner: false,
    );
  }
}
