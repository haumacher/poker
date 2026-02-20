package de.haumacher.games.poker.engine;

import java.util.ArrayList;
import java.util.List;

import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.Rank;
import de.haumacher.games.poker.model.Suit;

/**
 * Test utility for creating cards from short notation (e.g. "Ah" = Ace of Hearts).
 */
class Cards {

	static Card card(String notation) {
		if (notation.length() != 2) {
			throw new IllegalArgumentException("Invalid card notation: " + notation);
		}
		Rank rank = parseRank(notation.charAt(0));
		Suit suit = parseSuit(notation.charAt(1));
		return Card.create().setRank(rank).setSuit(suit);
	}

	static List<Card> cards(String... notations) {
		List<Card> result = new ArrayList<>(notations.length);
		for (String n : notations) {
			result.add(card(n));
		}
		return result;
	}

	private static Rank parseRank(char c) {
		return switch (c) {
			case '2' -> Rank.TWO;
			case '3' -> Rank.THREE;
			case '4' -> Rank.FOUR;
			case '5' -> Rank.FIVE;
			case '6' -> Rank.SIX;
			case '7' -> Rank.SEVEN;
			case '8' -> Rank.EIGHT;
			case '9' -> Rank.NINE;
			case 'T' -> Rank.TEN;
			case 'J' -> Rank.JACK;
			case 'Q' -> Rank.QUEEN;
			case 'K' -> Rank.KING;
			case 'A' -> Rank.ACE;
			default -> throw new IllegalArgumentException("Unknown rank: " + c);
		};
	}

	private static Suit parseSuit(char c) {
		return switch (c) {
			case 'h' -> Suit.HEARTS;
			case 'd' -> Suit.DIAMONDS;
			case 'c' -> Suit.CLUBS;
			case 's' -> Suit.SPADES;
			default -> throw new IllegalArgumentException("Unknown suit: " + c);
		};
	}
}
