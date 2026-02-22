import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/models/poker_messages.dart' hide Card;
import 'package:poker_app/models/poker_messages.dart' as msg;
import 'package:poker_app/providers/hole_cards_provider.dart';
import 'package:poker_app/utils/hand_evaluator.dart';
import 'package:poker_app/widgets/cards/card_back.dart';
import 'package:poker_app/widgets/cards/playing_card.dart';

class SeatWidget extends ConsumerWidget {
  final PlayerState? player;
  final int seatIndex;
  final bool isDealer;
  final bool isCurrentTurn;
  final bool isLocalPlayer;
  final int turnTimeRemaining;
  final ShowdownHand? showdownHand;
  final HandResult? liveHand;
  final bool isWinner;

  const SeatWidget({
    super.key,
    required this.player,
    required this.seatIndex,
    this.isDealer = false,
    this.isCurrentTurn = false,
    this.isLocalPlayer = false,
    this.turnTimeRemaining = 0,
    this.showdownHand,
    this.liveHand,
    this.isWinner = false,
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    if (player == null) {
      return _emptySeat();
    }

    final p = player!;
    final holeCards = ref.watch(holeCardsProvider);
    final showOwnCards = isLocalPlayer && holeCards != null && holeCards.cards.isNotEmpty;
    final hasShowdown = showdownHand != null && showdownHand!.holeCards.length >= 2;

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
          // Name + dealer chip + bet tag
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
              if (p.currentBet > 0)
                Container(
                  margin: const EdgeInsets.only(left: 4),
                  padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 1),
                  decoration: BoxDecoration(
                    color: Colors.green,
                    borderRadius: BorderRadius.circular(4),
                  ),
                  child: Text(
                    '${p.currentBet}',
                    style: const TextStyle(fontSize: 9, color: Colors.white, fontWeight: FontWeight.bold),
                  ),
                ),
            ],
          ),
          const SizedBox(height: 2),

          // Turn timer badge
          if (isCurrentTurn && turnTimeRemaining > 0)
            Container(
              margin: const EdgeInsets.only(bottom: 2),
              padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 1),
              decoration: BoxDecoration(
                color: turnTimeRemaining <= 10 ? Colors.red : Colors.orange,
                borderRadius: BorderRadius.circular(4),
              ),
              child: Text(
                '${turnTimeRemaining}s',
                style: const TextStyle(fontSize: 10, fontWeight: FontWeight.bold, color: Colors.white),
              ),
            ),

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

          // Cards
          const SizedBox(height: 4),
          if (hasShowdown)
            _showdownCards(showdownHand!)
          else if (showOwnCards)
            Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                PlayingCard(
                  card: holeCards.cards[0],
                  width: 32,
                  height: 45,
                  highlighted: liveHand != null && _isInRelevant(holeCards.cards[0], liveHand!.relevantCards),
                ),
                const SizedBox(width: 2),
                PlayingCard(
                  card: holeCards.cards[1],
                  width: 32,
                  height: 45,
                  highlighted: liveHand != null && _isInRelevant(holeCards.cards[1], liveHand!.relevantCards),
                ),
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

          // Hand description (live during play, or at showdown)
          if (hasShowdown)
            Padding(
              padding: const EdgeInsets.only(top: 2),
              child: Text(
                showdownHand!.handDescription,
                style: const TextStyle(fontSize: 9, color: Colors.amber, fontWeight: FontWeight.bold),
                overflow: TextOverflow.ellipsis,
              ),
            )
          else if (isLocalPlayer && liveHand != null)
            Padding(
              padding: const EdgeInsets.only(top: 2),
              child: Text(
                liveHand!.description,
                style: const TextStyle(fontSize: 9, color: Colors.amber, fontWeight: FontWeight.bold),
                overflow: TextOverflow.ellipsis,
              ),
            ),
        ],
      ),
    );
  }

  Widget _showdownCards(ShowdownHand sh) {
    final relevant = isWinner ? evaluateHand(sh.bestCards).relevantCards : <msg.Card>[];
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        PlayingCard(
          card: sh.holeCards[0],
          width: 32,
          height: 45,
          highlighted: _isInRelevant(sh.holeCards[0], relevant),
        ),
        const SizedBox(width: 2),
        PlayingCard(
          card: sh.holeCards[1],
          width: 32,
          height: 45,
          highlighted: _isInRelevant(sh.holeCards[1], relevant),
        ),
      ],
    );
  }

  bool _isInRelevant(msg.Card card, List<msg.Card> relevantCards) {
    for (final rc in relevantCards) {
      if (rc.rank == card.rank && rc.suit == card.suit) return true;
    }
    return false;
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
