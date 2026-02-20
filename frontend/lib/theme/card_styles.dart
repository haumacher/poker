import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart';

class CardStyles {
  static const Color redSuit = Color(0xFFD32F2F);
  static const Color blackSuit = Color(0xFF212121);

  static String suitSymbol(Suit suit) {
    switch (suit) {
      case Suit.hearts:
        return '\u2665';
      case Suit.diamonds:
        return '\u2666';
      case Suit.clubs:
        return '\u2663';
      case Suit.spades:
        return '\u2660';
    }
  }

  static Color suitColor(Suit suit) {
    switch (suit) {
      case Suit.hearts:
      case Suit.diamonds:
        return redSuit;
      case Suit.clubs:
      case Suit.spades:
        return blackSuit;
    }
  }

  static String rankLabel(Rank rank) {
    switch (rank) {
      case Rank.two:
        return '2';
      case Rank.three:
        return '3';
      case Rank.four:
        return '4';
      case Rank.five:
        return '5';
      case Rank.six:
        return '6';
      case Rank.seven:
        return '7';
      case Rank.eight:
        return '8';
      case Rank.nine:
        return '9';
      case Rank.ten:
        return '10';
      case Rank.jack:
        return 'J';
      case Rank.queen:
        return 'Q';
      case Rank.king:
        return 'K';
      case Rank.ace:
        return 'A';
    }
  }
}
