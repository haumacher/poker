import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/app.dart';

void main() {
  testWidgets('App renders home screen', (WidgetTester tester) async {
    await tester.pumpWidget(const ProviderScope(child: PokerApp()));

    // Section headers
    expect(find.text('Create Table'), findsWidgets);
    expect(find.text('Join Table'), findsWidgets);
    // Input fields
    expect(find.byType(TextField), findsWidgets);
    // Blind presets
    expect(find.text('5/10'), findsOneWidget);
  });
}
