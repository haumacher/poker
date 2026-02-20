package de.haumacher.games.poker.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class PotTest {

	@Test
	void simplePot() {
		Pot pot = new Pot();
		pot.addBet(0, 100);
		pot.addBet(1, 100);
		pot.addBet(2, 100);

		assertEquals(300, pot.getTotal());

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		assertEquals(1, pots.size());
		assertEquals(300, pots.get(0).amount());
		assertEquals(List.of(0, 1, 2), pots.get(0).eligibleSeats());
	}

	@Test
	void oneSidePot() {
		// Player 0 goes all-in for 50, players 1 and 2 call 100 each
		Pot pot = new Pot();
		pot.addBet(0, 50);
		pot.addBet(1, 100);
		pot.addBet(2, 100);

		assertEquals(250, pot.getTotal());

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		assertEquals(2, pots.size());

		// Main pot: 50 * 3 = 150 (all three eligible)
		assertEquals(150, pots.get(0).amount());
		assertEquals(List.of(0, 1, 2), pots.get(0).eligibleSeats());

		// Side pot: 50 * 2 = 100 (only players 1 and 2)
		assertEquals(100, pots.get(1).amount());
		assertEquals(List.of(1, 2), pots.get(1).eligibleSeats());
	}

	@Test
	void multipleSidePots() {
		// Player 0: all-in 30, Player 1: all-in 70, Player 2: 100, Player 3: 100
		Pot pot = new Pot();
		pot.addBet(0, 30);
		pot.addBet(1, 70);
		pot.addBet(2, 100);
		pot.addBet(3, 100);

		assertEquals(300, pot.getTotal());

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		assertEquals(3, pots.size());

		// Main pot: 30 * 4 = 120 (all four)
		assertEquals(120, pots.get(0).amount());
		assertEquals(List.of(0, 1, 2, 3), pots.get(0).eligibleSeats());

		// Side pot 1: 40 * 3 = 120 (players 1, 2, 3)
		assertEquals(120, pots.get(1).amount());
		assertEquals(List.of(1, 2, 3), pots.get(1).eligibleSeats());

		// Side pot 2: 30 * 2 = 60 (players 2, 3)
		assertEquals(60, pots.get(2).amount());
		assertEquals(List.of(2, 3), pots.get(2).eligibleSeats());
	}

	@Test
	void foldedPlayerExcludedFromPots() {
		Pot pot = new Pot();
		pot.addBet(0, 100);
		pot.addBet(1, 100);
		pot.addBet(2, 50);
		pot.fold(2); // Player 2 folds but their money stays

		assertEquals(250, pot.getTotal());

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		// Two levels: 50 and 100
		assertEquals(2, pots.size());

		// Main pot at level 50: 50*3 = 150, eligible: 0, 1 (not 2, they folded)
		assertEquals(150, pots.get(0).amount());
		assertEquals(List.of(0, 1), pots.get(0).eligibleSeats());

		// Side pot at level 100: 50*2 = 100, eligible: 0, 1
		assertEquals(100, pots.get(1).amount());
		assertEquals(List.of(0, 1), pots.get(1).eligibleSeats());
	}

	@Test
	void allInWithUnequalStacksThreePlayers() {
		// Classic scenario: 3 players, all different stack sizes go all-in
		Pot pot = new Pot();
		pot.addBet(0, 100);  // short stack
		pot.addBet(1, 300);  // medium stack
		pot.addBet(2, 500);  // big stack

		assertEquals(900, pot.getTotal());

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		assertEquals(3, pots.size());

		// Main: 100 * 3 = 300
		assertEquals(300, pots.get(0).amount());
		assertEquals(List.of(0, 1, 2), pots.get(0).eligibleSeats());

		// Side 1: 200 * 2 = 400
		assertEquals(400, pots.get(1).amount());
		assertEquals(List.of(1, 2), pots.get(1).eligibleSeats());

		// Side 2: 200 * 1 = 200
		assertEquals(200, pots.get(2).amount());
		assertEquals(List.of(2), pots.get(2).eligibleSeats());
	}

	@Test
	void emptyPot() {
		Pot pot = new Pot();
		assertEquals(0, pot.getTotal());
		assertTrue(pot.calculateSidePots().isEmpty());
	}

	@Test
	void resetClearsPot() {
		Pot pot = new Pot();
		pot.addBet(0, 100);
		pot.addBet(1, 100);
		pot.reset();

		assertEquals(0, pot.getTotal());
		assertTrue(pot.calculateSidePots().isEmpty());
	}
}
