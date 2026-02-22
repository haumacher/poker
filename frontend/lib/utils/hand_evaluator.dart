import 'package:poker_app/models/poker_messages.dart' as msg;

enum HandRank {
  highCard,
  onePair,
  twoPair,
  threeOfAKind,
  straight,
  flush,
  fullHouse,
  fourOfAKind,
  straightFlush,
  royalFlush,
}

class HandResult {
  final HandRank rank;
  final String description;
  final List<msg.Card> relevantCards;

  /// Internal score for comparing hands of the same rank.
  /// Higher is better.
  final int _score;

  const HandResult(this.rank, this.description, this.relevantCards, this._score);

  bool isBetterThan(HandResult other) {
    if (rank.index != other.rank.index) return rank.index > other.rank.index;
    return _score > other._score;
  }
}

/// Evaluate the best poker hand from 2–7 cards.
///
/// With 2 cards (pre-flop): only pair or high card.
/// With 5–7 cards: enumerates all C(n,5) subsets and picks the best.
HandResult evaluateHand(List<msg.Card> cards) {
  if (cards.isEmpty) {
    return const HandResult(HandRank.highCard, 'No Cards', [], 0);
  }

  if (cards.length < 5) {
    return _evaluatePartial(cards);
  }

  // 5–7 cards: enumerate all 5-card subsets
  HandResult? best;
  final subsets = _combinations(cards, 5);
  for (final subset in subsets) {
    final result = _evaluateFive(subset);
    if (best == null || result.isBetterThan(best)) {
      best = result;
    }
  }
  return best!;
}

// --- Partial evaluation (2-4 cards, pre-flop) ---

HandResult _evaluatePartial(List<msg.Card> cards) {
  // Count rank frequencies
  final freq = <msg.Rank, List<msg.Card>>{};
  for (final c in cards) {
    freq.putIfAbsent(c.rank, () => []).add(c);
  }

  // Find pairs
  final pairs = freq.entries.where((e) => e.value.length >= 2).toList();

  if (pairs.isNotEmpty) {
    // Best pair by rank index
    pairs.sort((a, b) => b.key.index.compareTo(a.key.index));
    final pairCards = pairs.first.value.sublist(0, 2);
    return HandResult(
      HandRank.onePair,
      'Pair',
      pairCards,
      pairs.first.key.index,
    );
  }

  // High card — only highlight the single highest card
  final sorted = List<msg.Card>.from(cards)
    ..sort((a, b) => b.rank.index.compareTo(a.rank.index));
  return HandResult(
    HandRank.highCard,
    'High Card',
    [sorted.first],
    sorted.first.rank.index,
  );
}

// --- Full 5-card evaluation ---

HandResult _evaluateFive(List<msg.Card> cards) {
  assert(cards.length == 5);

  final sorted = List<msg.Card>.from(cards)
    ..sort((a, b) => b.rank.index.compareTo(a.rank.index));

  final isFlush = _isFlush(sorted);
  final straightHigh = _straightHighRank(sorted);
  final isStraight = straightHigh != null;

  // Rank frequency
  final freq = <msg.Rank, List<msg.Card>>{};
  for (final c in sorted) {
    freq.putIfAbsent(c.rank, () => []).add(c);
  }

  final groups = freq.entries.toList()
    ..sort((a, b) {
      final cmp = b.value.length.compareTo(a.value.length);
      if (cmp != 0) return cmp;
      return b.key.index.compareTo(a.key.index);
    });

  final topCount = groups.first.value.length;
  final secondCount = groups.length > 1 ? groups[1].value.length : 0;

  // Score for comparing same-rank hands: encode up to 5 rank indices
  int score(List<int> ranks) {
    var s = 0;
    for (final r in ranks) {
      s = s * 15 + r;
    }
    return s;
  }

  // Straight flush / royal flush
  if (isFlush && isStraight) {
    final high = straightHigh;
    if (high == msg.Rank.ace) {
      return HandResult(
        HandRank.royalFlush,
        'Royal Flush',
        List.unmodifiable(sorted),
        score([high.index]),
      );
    }
    return HandResult(
      HandRank.straightFlush,
      'Straight Flush',
      List.unmodifiable(sorted),
      score([high.index]),
    );
  }

  // Four of a kind
  if (topCount == 4) {
    return HandResult(
      HandRank.fourOfAKind,
      'Four of a Kind',
      List.unmodifiable(groups.first.value),
      score([groups.first.key.index, groups[1].key.index]),
    );
  }

  // Full house
  if (topCount == 3 && secondCount == 2) {
    return HandResult(
      HandRank.fullHouse,
      'Full House',
      List.unmodifiable(sorted),
      score([groups.first.key.index, groups[1].key.index]),
    );
  }

  // Flush
  if (isFlush) {
    return HandResult(
      HandRank.flush,
      'Flush',
      List.unmodifiable(sorted),
      score(sorted.map((c) => c.rank.index).toList()),
    );
  }

  // Straight
  if (isStraight) {
    final high = straightHigh;
    return HandResult(
      HandRank.straight,
      'Straight',
      List.unmodifiable(sorted),
      score([high.index]),
    );
  }

  // Three of a kind
  if (topCount == 3) {
    return HandResult(
      HandRank.threeOfAKind,
      'Three of a Kind',
      List.unmodifiable(groups.first.value),
      score([groups.first.key.index, groups[1].key.index, groups[2].key.index]),
    );
  }

  // Two pair
  if (topCount == 2 && secondCount == 2) {
    final highPair = groups[0].key.index > groups[1].key.index ? groups[0] : groups[1];
    final lowPair = groups[0].key.index > groups[1].key.index ? groups[1] : groups[0];
    final relevant = [...highPair.value, ...lowPair.value];
    return HandResult(
      HandRank.twoPair,
      'Two Pair',
      List.unmodifiable(relevant),
      score([highPair.key.index, lowPair.key.index, groups[2].key.index]),
    );
  }

  // One pair
  if (topCount == 2) {
    return HandResult(
      HandRank.onePair,
      'Pair',
      List.unmodifiable(groups.first.value),
      score([groups.first.key.index, groups[1].key.index, groups[2].key.index, groups[3].key.index]),
    );
  }

  // High card — only the single highest card
  return HandResult(
    HandRank.highCard,
    'High Card',
    [sorted.first],
    score(sorted.map((c) => c.rank.index).toList()),
  );
}

// --- Helpers ---

bool _isFlush(List<msg.Card> cards) {
  final suit = cards.first.suit;
  return cards.every((c) => c.suit == suit);
}

/// Returns the high rank of the straight, or null if not a straight.
/// Handles A-2-3-4-5 (wheel) where high is five.
msg.Rank? _straightHighRank(List<msg.Card> sorted) {
  // sorted is descending by rank index
  // Check normal straight
  bool isConsecutive = true;
  for (var i = 0; i < sorted.length - 1; i++) {
    if (sorted[i].rank.index - sorted[i + 1].rank.index != 1) {
      isConsecutive = false;
      break;
    }
  }
  if (isConsecutive) return sorted.first.rank;

  // Check wheel: A-5-4-3-2
  if (sorted[0].rank == msg.Rank.ace &&
      sorted[1].rank == msg.Rank.five &&
      sorted[2].rank == msg.Rank.four &&
      sorted[3].rank == msg.Rank.three &&
      sorted[4].rank == msg.Rank.two) {
    return msg.Rank.five;
  }

  return null;
}

/// Generate all C(n,k) combinations.
List<List<msg.Card>> _combinations(List<msg.Card> cards, int k) {
  final results = <List<msg.Card>>[];
  _combHelper(cards, k, 0, [], results);
  return results;
}

void _combHelper(List<msg.Card> cards, int k, int start, List<msg.Card> current, List<List<msg.Card>> results) {
  if (current.length == k) {
    results.add(List.unmodifiable(current));
    return;
  }
  for (var i = start; i < cards.length; i++) {
    current.add(cards[i]);
    _combHelper(cards, k, i + 1, current, results);
    current.removeLast();
  }
}
