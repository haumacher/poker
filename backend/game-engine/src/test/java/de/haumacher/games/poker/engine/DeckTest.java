package de.haumacher.games.poker.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.haumacher.games.poker.model.Card;

class DeckTest {

	@Test
	void deckContains52Cards() {
		Deck deck = new Deck();
		assertEquals(52, deck.size());
		assertEquals(52, deck.remaining());
	}

	@Test
	void allCardsUnique() {
		Deck deck = new Deck();
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < 52; i++) {
			Card card = deck.deal();
			String key = card.getRank() + ":" + card.getSuit();
			assertTrue(seen.add(key), "Duplicate card: " + key);
		}
		assertEquals(52, seen.size());
	}

	@Test
	void dealExhaustsDeck() {
		Deck deck = new Deck();
		for (int i = 0; i < 52; i++) {
			deck.deal();
		}
		assertEquals(0, deck.remaining());
		assertThrows(IllegalStateException.class, deck::deal);
	}

	@Test
	void shuffleResetsDealPosition() {
		Deck deck = new Deck();
		deck.deal();
		deck.deal();
		assertEquals(50, deck.remaining());

		deck.shuffle(new SecureRandom());
		assertEquals(52, deck.remaining());
	}

	@Test
	void shuffleChangesOrder() {
		Deck deck = new Deck();
		// Deal first 5 cards unshuffled
		Card[] before = new Card[5];
		for (int i = 0; i < 5; i++) {
			before[i] = deck.deal();
		}

		// Shuffle and deal first 5 again
		deck.shuffle(new SecureRandom());
		Card[] after = new Card[5];
		for (int i = 0; i < 5; i++) {
			after[i] = deck.deal();
		}

		// It's theoretically possible (but astronomically unlikely) that shuffle
		// produces the same order. We just check that at least one card differs.
		boolean anyDifferent = false;
		for (int i = 0; i < 5; i++) {
			if (before[i].getRank() != after[i].getRank() || before[i].getSuit() != after[i].getSuit()) {
				anyDifferent = true;
				break;
			}
		}
		assertTrue(anyDifferent, "Shuffle should change card order");
	}
}
