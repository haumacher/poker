import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart' as msg;
import 'package:poker_app/theme/card_styles.dart';

class PlayingCard extends StatelessWidget {
  final msg.Card card;
  final double width;
  final double height;
  final bool highlighted;

  const PlayingCard({
    super.key,
    required this.card,
    this.width = 44,
    this.height = 62,
    this.highlighted = false,
  });

  @override
  Widget build(BuildContext context) {
    final color = CardStyles.suitColor(card.suit);
    final rankLabel = CardStyles.rankLabel(card.rank);
    final suitSymbol = CardStyles.suitSymbol(card.suit);

    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: highlighted ? const Color(0xFFFFF9C4) : Colors.white,
        borderRadius: BorderRadius.circular(4),
        border: Border.all(color: highlighted ? Colors.amber : Colors.grey.shade400),
        boxShadow: const [BoxShadow(color: Colors.black26, blurRadius: 2, offset: Offset(1, 1))],
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(rankLabel, style: TextStyle(color: color, fontSize: 16, fontWeight: FontWeight.bold, height: 1)),
          Text(suitSymbol, style: TextStyle(color: color, fontSize: 14, height: 1)),
        ],
      ),
    );
  }
}
