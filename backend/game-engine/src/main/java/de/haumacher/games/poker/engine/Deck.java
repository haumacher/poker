package de.haumacher.games.poker.engine;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.Rank;
import de.haumacher.games.poker.model.Suit;

/**
 * A standard 52-card deck that can be shuffled and dealt from.
 */
public class Deck {

	private final List<Card> cards;
	private int nextIndex;

	public Deck() {
		cards = new ArrayList<>(52);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(Card.create().setRank(rank).setSuit(suit));
			}
		}
		nextIndex = 0;
	}

	public void shuffle(SecureRandom random) {
		Collections.shuffle(cards, random);
		nextIndex = 0;
	}

	public Card deal() {
		if (nextIndex >= cards.size()) {
			throw new IllegalStateException("No cards left in deck");
		}
		return cards.get(nextIndex++);
	}

	public int remaining() {
		return cards.size() - nextIndex;
	}

	public int size() {
		return cards.size();
	}
}
