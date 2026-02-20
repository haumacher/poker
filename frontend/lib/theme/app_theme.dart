import 'package:flutter/material.dart';

class AppTheme {
  static const pokerGreen = Color(0xFF1B5E20);
  static const feltGreen = Color(0xFF2E7D32);
  static const darkBackground = Color(0xFF121212);
  static const cardWhite = Color(0xFFFAFAFA);

  static ThemeData get dark => ThemeData(
        brightness: Brightness.dark,
        colorSchemeSeed: pokerGreen,
        scaffoldBackgroundColor: darkBackground,
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFF1A1A1A),
          elevation: 0,
        ),
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
          filled: true,
          fillColor: const Color(0xFF2A2A2A),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            minimumSize: const Size(double.infinity, 48),
            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          ),
        ),
      );
}
