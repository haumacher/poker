package de.haumacher.games.poker.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.Rank;

/**
 * Evaluates the best 5-card poker hand from a set of cards (typically 7: 2 hole + 5 community).
 */
public class HandEvaluator {

	/**
	 * Evaluate the best 5-card hand from the given cards (2-7 cards supported).
	 */
	public static EvaluatedHand evaluate(List<Card> cards) {
		if (cards.size() < 5) {
			throw new IllegalArgumentException("Need at least 5 cards, got " + cards.size());
		}
		if (cards.size() == 5) {
			return evaluate5(cards);
		}

		// Try all C(n, 5) combinations and return the best
		EvaluatedHand best = null;
		int n = cards.size();
		for (int a = 0; a < n - 4; a++) {
			for (int b = a + 1; b < n - 3; b++) {
				for (int c = b + 1; c < n - 2; c++) {
					for (int d = c + 1; d < n - 1; d++) {
						for (int e = d + 1; e < n; e++) {
							List<Card> hand = List.of(cards.get(a), cards.get(b), cards.get(c), cards.get(d), cards.get(e));
							EvaluatedHand eval = evaluate5(hand);
							if (best == null || eval.compareTo(best) < 0) {
								best = eval;
							}
						}
					}
				}
			}
		}
		return best;
	}

	/**
	 * Evaluate exactly 5 cards.
	 */
	static EvaluatedHand evaluate5(List<Card> cards) {
		// Count ranks
		int[] rankCounts = new int[13];
		int[] suitCounts = new int[4];
		for (Card card : cards) {
			rankCounts[card.getRank().ordinal()]++;
			suitCounts[card.getSuit().ordinal()]++;
		}

		boolean isFlush = false;
		for (int sc : suitCounts) {
			if (sc == 5) {
				isFlush = true;
				break;
			}
		}

		boolean isStraight = false;
		int straightHigh = -1;

		// Check for ace-low straight (A-2-3-4-5): wheel
		if (rankCounts[Rank.ACE.ordinal()] > 0 && rankCounts[Rank.TWO.ordinal()] > 0
				&& rankCounts[Rank.THREE.ordinal()] > 0 && rankCounts[Rank.FOUR.ordinal()] > 0
				&& rankCounts[Rank.FIVE.ordinal()] > 0) {
			isStraight = true;
			straightHigh = Rank.FIVE.ordinal(); // 5-high straight
		}

		// Check for regular straights
		int consecutive = 0;
		for (int i = 0; i < 13; i++) {
			if (rankCounts[i] > 0) {
				consecutive++;
				if (consecutive == 5) {
					isStraight = true;
					straightHigh = i;
				}
			} else {
				consecutive = 0;
			}
		}

		// Classify hand
		// Find groups
		int fourRank = -1, threeRank = -1;
		List<Integer> pairRanks = new ArrayList<>();
		List<Integer> singleRanks = new ArrayList<>();

		for (int i = 12; i >= 0; i--) {
			switch (rankCounts[i]) {
				case 4 -> fourRank = i;
				case 3 -> threeRank = i;
				case 2 -> pairRanks.add(i);
				case 1 -> singleRanks.add(i);
			}
		}

		if (isStraight && isFlush) {
			if (straightHigh == Rank.ACE.ordinal()) {
				return new EvaluatedHand(HandRank.ROYAL_FLUSH, new int[]{straightHigh}, cards);
			}
			return new EvaluatedHand(HandRank.STRAIGHT_FLUSH, new int[]{straightHigh}, cards);
		}

		if (fourRank >= 0) {
			int kicker = singleRanks.isEmpty() ? pairRanks.get(0) : singleRanks.get(0);
			return new EvaluatedHand(HandRank.FOUR_OF_A_KIND, new int[]{fourRank, kicker}, cards);
		}

		if (threeRank >= 0 && !pairRanks.isEmpty()) {
			return new EvaluatedHand(HandRank.FULL_HOUSE, new int[]{threeRank, pairRanks.get(0)}, cards);
		}

		if (isFlush) {
			int[] kickers = singleRanks.stream().mapToInt(Integer::intValue).toArray();
			if (kickers.length < 5) {
				// Mix of singles and pairs that form a flush — collect all rank values sorted desc
				kickers = allRanksSortedDesc(cards);
			}
			return new EvaluatedHand(HandRank.FLUSH, kickers, cards);
		}

		if (isStraight) {
			return new EvaluatedHand(HandRank.STRAIGHT, new int[]{straightHigh}, cards);
		}

		if (threeRank >= 0) {
			int[] kickers = new int[3 + singleRanks.size()];
			kickers[0] = threeRank;
			for (int i = 0; i < singleRanks.size(); i++) {
				kickers[1 + i] = singleRanks.get(i);
			}
			return new EvaluatedHand(HandRank.THREE_OF_A_KIND, kickers, cards);
		}

		if (pairRanks.size() == 2) {
			int highPair = Math.max(pairRanks.get(0), pairRanks.get(1));
			int lowPair = Math.min(pairRanks.get(0), pairRanks.get(1));
			int kicker = singleRanks.get(0);
			return new EvaluatedHand(HandRank.TWO_PAIR, new int[]{highPair, lowPair, kicker}, cards);
		}

		if (pairRanks.size() == 1) {
			int[] kickers = new int[1 + singleRanks.size()];
			kickers[0] = pairRanks.get(0);
			for (int i = 0; i < singleRanks.size(); i++) {
				kickers[1 + i] = singleRanks.get(i);
			}
			return new EvaluatedHand(HandRank.ONE_PAIR, kickers, cards);
		}

		// High card
		int[] kickers = singleRanks.stream().mapToInt(Integer::intValue).toArray();
		return new EvaluatedHand(HandRank.HIGH_CARD, kickers, cards);
	}

	private static int[] allRanksSortedDesc(List<Card> cards) {
		int[] ranks = new int[cards.size()];
		for (int i = 0; i < cards.size(); i++) {
			ranks[i] = cards.get(i).getRank().ordinal();
		}
		Arrays.sort(ranks);
		// Reverse to descending
		for (int i = 0; i < ranks.length / 2; i++) {
			int tmp = ranks[i];
			ranks[i] = ranks[ranks.length - 1 - i];
			ranks[ranks.length - 1 - i] = tmp;
		}
		return ranks;
	}
}
