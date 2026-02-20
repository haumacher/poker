import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart' as msg;
import 'package:poker_app/widgets/cards/playing_card.dart';

class CommunityCards extends StatelessWidget {
  final List<msg.Card> cards;

  const CommunityCards({super.key, required this.cards});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        for (var i = 0; i < 5; i++) ...[
          if (i > 0) const SizedBox(width: 4),
          if (i < cards.length)
            PlayingCard(card: cards[i])
          else
            _emptySlot(),
        ],
      ],
    );
  }

  Widget _emptySlot() {
    return Container(
      width: 44,
      height: 62,
      decoration: BoxDecoration(
        color: Colors.white.withValues(alpha: 0.08),
        borderRadius: BorderRadius.circular(4),
        border: Border.all(color: Colors.white24),
      ),
    );
  }
}
