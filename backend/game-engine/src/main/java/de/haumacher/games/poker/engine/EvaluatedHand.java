package de.haumacher.games.poker.engine;

import java.util.Arrays;
import java.util.List;

import de.haumacher.games.poker.model.Card;

/**
 * A fully evaluated poker hand with rank and kickers for tiebreaking.
 *
 * <p>Lower ordinal of {@link HandRank} means stronger hand. Kickers are ordered
 * from most significant to least significant, with higher values being better.</p>
 */
public class EvaluatedHand implements Comparable<EvaluatedHand> {

	private final HandRank rank;
	private final int[] kickers;
	private final List<Card> cards;

	public EvaluatedHand(HandRank rank, int[] kickers, List<Card> cards) {
		this.rank = rank;
		this.kickers = kickers;
		this.cards = cards;
	}

	public HandRank getRank() {
		return rank;
	}

	/**
	 * Kicker values ordered from most significant to least, used for tiebreaking.
	 * For a pair of Kings with A-9-5: kickers = [11, 12, 7, 3] (King=11, Ace=12, Nine=7, Five=3).
	 * Actually we use rank ordinals: TWO=0 .. ACE=12.
	 */
	public int[] getKickers() {
		return kickers;
	}

	public List<Card> getCards() {
		return cards;
	}

	@Override
	public int compareTo(EvaluatedHand other) {
		// Lower ordinal = better hand
		int cmp = Integer.compare(this.rank.ordinal(), other.rank.ordinal());
		if (cmp != 0) {
			return cmp;
		}
		// Same rank — compare kickers (higher is better, so reverse)
		for (int i = 0; i < Math.min(kickers.length, other.kickers.length); i++) {
			cmp = Integer.compare(other.kickers[i], this.kickers[i]);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EvaluatedHand other)) return false;
		return rank == other.rank && Arrays.equals(kickers, other.kickers);
	}

	@Override
	public int hashCode() {
		return 31 * rank.hashCode() + Arrays.hashCode(kickers);
	}

	public String describe() {
		return switch (rank) {
			case ROYAL_FLUSH -> "Royal Flush";
			case STRAIGHT_FLUSH -> "Straight Flush";
			case FOUR_OF_A_KIND -> "Four of a Kind";
			case FULL_HOUSE -> "Full House";
			case FLUSH -> "Flush";
			case STRAIGHT -> "Straight";
			case THREE_OF_A_KIND -> "Three of a Kind";
			case TWO_PAIR -> "Two Pair";
			case ONE_PAIR -> "One Pair";
			case HIGH_CARD -> "High Card";
		};
	}

	@Override
	public String toString() {
		return describe() + " " + Arrays.toString(kickers);
	}
}
