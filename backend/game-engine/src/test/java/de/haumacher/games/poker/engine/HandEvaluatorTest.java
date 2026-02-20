package de.haumacher.games.poker.engine;

import static de.haumacher.games.poker.engine.Cards.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.haumacher.games.poker.model.Card;

class HandEvaluatorTest {

	@Test
	void royalFlush() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ah", "Kh", "Qh", "Jh", "Th"));
		assertEquals(HandRank.ROYAL_FLUSH, hand.getRank());
	}

	@Test
	void straightFlush() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("9s", "8s", "7s", "6s", "5s"));
		assertEquals(HandRank.STRAIGHT_FLUSH, hand.getRank());
	}

	@Test
	void straightFlushLow() {
		// Ace-low straight flush (wheel flush)
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ac", "2c", "3c", "4c", "5c"));
		assertEquals(HandRank.STRAIGHT_FLUSH, hand.getRank());
	}

	@Test
	void fourOfAKind() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ks", "Kh", "Kd", "Kc", "3s"));
		assertEquals(HandRank.FOUR_OF_A_KIND, hand.getRank());
	}

	@Test
	void fullHouse() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Js", "Jh", "Jd", "8c", "8s"));
		assertEquals(HandRank.FULL_HOUSE, hand.getRank());
	}

	@Test
	void flush() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ac", "Jc", "8c", "5c", "2c"));
		assertEquals(HandRank.FLUSH, hand.getRank());
	}

	@Test
	void straight() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ts", "9h", "8d", "7c", "6s"));
		assertEquals(HandRank.STRAIGHT, hand.getRank());
	}

	@Test
	void straightAceLow() {
		// Wheel: A-2-3-4-5
		EvaluatedHand hand = HandEvaluator.evaluate(cards("As", "2h", "3d", "4c", "5s"));
		assertEquals(HandRank.STRAIGHT, hand.getRank());
	}

	@Test
	void straightAceHigh() {
		// Broadway: T-J-Q-K-A
		EvaluatedHand hand = HandEvaluator.evaluate(cards("As", "Kh", "Qd", "Jc", "Ts"));
		assertEquals(HandRank.STRAIGHT, hand.getRank());
	}

	@Test
	void threeOfAKind() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Qs", "Qh", "Qd", "9c", "4s"));
		assertEquals(HandRank.THREE_OF_A_KIND, hand.getRank());
	}

	@Test
	void twoPair() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("As", "Ah", "7d", "7c", "Ks"));
		assertEquals(HandRank.TWO_PAIR, hand.getRank());
	}

	@Test
	void onePair() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ts", "Th", "Ad", "8c", "3s"));
		assertEquals(HandRank.ONE_PAIR, hand.getRank());
	}

	@Test
	void highCard() {
		EvaluatedHand hand = HandEvaluator.evaluate(cards("As", "Jh", "9d", "5c", "2s"));
		assertEquals(HandRank.HIGH_CARD, hand.getRank());
	}

	// --- Tiebreakers ---

	@Test
	void higherFlushWins() {
		EvaluatedHand high = HandEvaluator.evaluate(cards("Ac", "Kc", "8c", "5c", "2c"));
		EvaluatedHand low = HandEvaluator.evaluate(cards("Ks", "Qs", "8s", "5s", "2s"));
		assertTrue(high.compareTo(low) < 0, "Ace-high flush should beat King-high flush");
	}

	@Test
	void higherPairWins() {
		EvaluatedHand aces = HandEvaluator.evaluate(cards("As", "Ah", "9d", "5c", "2s"));
		EvaluatedHand kings = HandEvaluator.evaluate(cards("Ks", "Kh", "9d", "5c", "2s"));
		assertTrue(aces.compareTo(kings) < 0, "Pair of Aces should beat pair of Kings");
	}

	@Test
	void samePairKickerBreaks() {
		EvaluatedHand aceKicker = HandEvaluator.evaluate(cards("Ks", "Kh", "Ad", "5c", "2s"));
		EvaluatedHand queenKicker = HandEvaluator.evaluate(cards("Ks", "Kd", "Qh", "5c", "2s"));
		assertTrue(aceKicker.compareTo(queenKicker) < 0, "Same pair, higher kicker wins");
	}

	@Test
	void identicalHandsSplit() {
		EvaluatedHand hand1 = HandEvaluator.evaluate(cards("Ah", "Kh", "9d", "5c", "2s"));
		EvaluatedHand hand2 = HandEvaluator.evaluate(cards("As", "Ks", "9h", "5d", "2c"));
		assertEquals(0, hand1.compareTo(hand2), "Identical ranks should tie");
	}

	@Test
	void twoPairTiebreaker() {
		EvaluatedHand acesAndKings = HandEvaluator.evaluate(cards("As", "Ah", "Kd", "Kc", "2s"));
		EvaluatedHand acesAndQueens = HandEvaluator.evaluate(cards("Ad", "Ac", "Qh", "Qs", "2s"));
		assertTrue(acesAndKings.compareTo(acesAndQueens) < 0, "AA+KK should beat AA+QQ");
	}

	@Test
	void fullHouseTiebreaker() {
		EvaluatedHand kingsOverTwos = HandEvaluator.evaluate(cards("Ks", "Kh", "Kd", "2c", "2s"));
		EvaluatedHand queensOverAces = HandEvaluator.evaluate(cards("Qs", "Qh", "Qd", "Ac", "As"));
		assertTrue(kingsOverTwos.compareTo(queensOverAces) < 0, "KKK22 should beat QQQAA");
	}

	@Test
	void straightHigherWins() {
		EvaluatedHand tenHigh = HandEvaluator.evaluate(cards("Ts", "9h", "8d", "7c", "6s"));
		EvaluatedHand nineHigh = HandEvaluator.evaluate(cards("9s", "8h", "7d", "6c", "5s"));
		assertTrue(tenHigh.compareTo(nineHigh) < 0, "T-high straight beats 9-high");
	}

	@Test
	void wheelLosesToHigherStraight() {
		EvaluatedHand sixHigh = HandEvaluator.evaluate(cards("6s", "5h", "4d", "3c", "2s"));
		EvaluatedHand wheel = HandEvaluator.evaluate(cards("As", "2h", "3d", "4c", "5s"));
		assertTrue(sixHigh.compareTo(wheel) < 0, "6-high straight beats wheel");
	}

	// --- 7-card evaluation ---

	@Test
	void bestFiveFromSeven() {
		// 7 cards: hole=Ah,Kh + community=Qh,Jh,Th,3c,5d → Royal Flush
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ah", "Kh", "Qh", "Jh", "Th", "3c", "5d"));
		assertEquals(HandRank.ROYAL_FLUSH, hand.getRank());
	}

	@Test
	void bestFiveIgnoresLowCards() {
		// 7 cards where best 5 form a flush
		EvaluatedHand hand = HandEvaluator.evaluate(cards("Ac", "Kc", "Qc", "Jc", "9c", "2h", "3d"));
		assertEquals(HandRank.FLUSH, hand.getRank());
	}

	@Test
	void sevenCardStraight() {
		// 7 cards with a straight buried inside
		EvaluatedHand hand = HandEvaluator.evaluate(cards("2h", "5s", "6d", "7c", "8h", "9s", "Kd"));
		assertEquals(HandRank.STRAIGHT, hand.getRank());
	}

	@Test
	void rankOrdering() {
		// Verify that all hand ranks order correctly
		EvaluatedHand royalFlush = HandEvaluator.evaluate(cards("Ah", "Kh", "Qh", "Jh", "Th"));
		EvaluatedHand straightFlush = HandEvaluator.evaluate(cards("9s", "8s", "7s", "6s", "5s"));
		EvaluatedHand fourKind = HandEvaluator.evaluate(cards("Ks", "Kh", "Kd", "Kc", "3s"));
		EvaluatedHand fullHouse = HandEvaluator.evaluate(cards("Js", "Jh", "Jd", "8c", "8s"));
		EvaluatedHand flush = HandEvaluator.evaluate(cards("Ac", "Jc", "8c", "5c", "2c"));
		EvaluatedHand straight = HandEvaluator.evaluate(cards("Ts", "9h", "8d", "7c", "6s"));
		EvaluatedHand threeKind = HandEvaluator.evaluate(cards("Qs", "Qh", "Qd", "9c", "4s"));
		EvaluatedHand twoPair = HandEvaluator.evaluate(cards("As", "Ah", "7d", "7c", "Ks"));
		EvaluatedHand onePair = HandEvaluator.evaluate(cards("Ts", "Th", "Ad", "8c", "3s"));
		EvaluatedHand highCard = HandEvaluator.evaluate(cards("As", "Jh", "9d", "5c", "2s"));

		EvaluatedHand[] hands = {royalFlush, straightFlush, fourKind, fullHouse, flush, straight, threeKind, twoPair, onePair, highCard};
		for (int i = 0; i < hands.length - 1; i++) {
			assertTrue(hands[i].compareTo(hands[i + 1]) < 0,
					hands[i].getRank() + " should beat " + hands[i + 1].getRank());
		}
	}
}
