import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/models/poker_messages.dart' hide Card;
import 'package:poker_app/providers/hole_cards_provider.dart';
import 'package:poker_app/widgets/cards/card_back.dart';
import 'package:poker_app/widgets/cards/playing_card.dart';

class SeatWidget extends ConsumerWidget {
  final PlayerState? player;
  final int seatIndex;
  final bool isDealer;
  final bool isCurrentTurn;
  final bool isLocalPlayer;

  const SeatWidget({
    super.key,
    required this.player,
    required this.seatIndex,
    this.isDealer = false,
    this.isCurrentTurn = false,
    this.isLocalPlayer = false,
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    if (player == null) {
      return _emptySeat();
    }

    final p = player!;
    final holeCards = ref.watch(holeCardsProvider);
    final showCards = isLocalPlayer && holeCards != null && holeCards.cards.isNotEmpty;

    return Container(
      width: 120,
      padding: const EdgeInsets.all(6),
      decoration: BoxDecoration(
        color: isCurrentTurn
            ? Colors.amber.withValues(alpha: 0.2)
            : Colors.black54,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(
          color: isCurrentTurn ? Colors.amber : Colors.white24,
          width: isCurrentTurn ? 2 : 1,
        ),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          // Name + dealer chip
          Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              if (isDealer)
                Container(
                  width: 18,
                  height: 18,
                  margin: const EdgeInsets.only(right: 4),
                  decoration: const BoxDecoration(
                    color: Colors.yellow,
                    shape: BoxShape.circle,
                  ),
                  child: const Center(
                    child: Text('D', style: TextStyle(color: Colors.black, fontSize: 10, fontWeight: FontWeight.bold)),
                  ),
                ),
              Flexible(
                child: Text(
                  p.displayName.isEmpty ? 'Seat $seatIndex' : p.displayName,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(fontSize: 12, fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
          const SizedBox(height: 2),

          // Chips
          Text(
            '${p.chips}',
            style: const TextStyle(color: Colors.amber, fontSize: 11),
          ),

          // Status / last action
          if (p.status == PlayerStatus.folded)
            const Text('FOLDED', style: TextStyle(color: Colors.grey, fontSize: 9))
          else if (p.status == PlayerStatus.allIn)
            const Text('ALL IN', style: TextStyle(color: Colors.red, fontSize: 9, fontWeight: FontWeight.bold)),

          // Current bet
          if (p.currentBet > 0)
            Container(
              margin: const EdgeInsets.only(top: 2),
              padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 1),
              decoration: BoxDecoration(
                color: Colors.white12,
                borderRadius: BorderRadius.circular(4),
              ),
              child: Text('Bet: ${p.currentBet}', style: const TextStyle(fontSize: 10)),
            ),

          // Cards
          const SizedBox(height: 4),
          if (showCards)
            Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                PlayingCard(card: holeCards.cards[0], width: 32, height: 45),
                const SizedBox(width: 2),
                PlayingCard(card: holeCards.cards[1], width: 32, height: 45),
              ],
            )
          else if (p.status == PlayerStatus.active || p.status == PlayerStatus.allIn)
            const Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                CardBack(width: 32, height: 45),
                SizedBox(width: 2),
                CardBack(width: 32, height: 45),
              ],
            ),
        ],
      ),
    );
  }

  Widget _emptySeat() {
    return Container(
      width: 120,
      padding: const EdgeInsets.all(6),
      decoration: BoxDecoration(
        color: Colors.white.withValues(alpha: 0.05),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.white10),
      ),
      child: Center(
        child: Text(
          'Seat ${seatIndex + 1}',
          style: const TextStyle(color: Colors.white24, fontSize: 12),
        ),
      ),
    );
  }
}
