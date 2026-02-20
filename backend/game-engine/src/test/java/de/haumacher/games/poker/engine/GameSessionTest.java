package de.haumacher.games.poker.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.haumacher.games.poker.model.ActionType;
import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.Phase;
import de.haumacher.games.poker.model.PlayerStatus;

class GameSessionTest {

	private GameSession game;
	private TestListener listener;
	private static final long SB = 10;
	private static final long BB = 20;
	private static final long BUY_IN = 1000;

	@BeforeEach
	void setUp() {
		listener = new TestListener();
		game = new GameSession(SB, BB, new SecureRandom());
		game.setListener(listener);
	}

	// --- Basic Setup ---

	@Test
	void cannotStartWithFewerThanTwoPlayers() {
		game.addPlayer(0, "Alice", BUY_IN);
		assertFalse(game.startHand());
		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());
	}

	@Test
	void canStartWithTwoPlayers() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		assertTrue(game.startHand());
		assertEquals(Phase.PRE_FLOP, game.getPhase());
		assertEquals(1, game.getHandNumber());
	}

	@Test
	void blindsArePosted() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		// In heads-up, dealer is SB. Dealer starts at -1, advances to seat 0.
		// Seat 0 = dealer/SB, seat 1 = BB
		int dealer = game.getDealerSeat();
		int sbSeat = dealer; // heads-up
		int bbSeat = (dealer == 0) ? 1 : 0;

		assertEquals(BUY_IN - SB, game.getPlayer(sbSeat).getChips());
		assertEquals(BUY_IN - BB, game.getPlayer(bbSeat).getChips());
		assertEquals(SB + BB, game.getPotTotal());
	}

	@Test
	void holeCardsDealt() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		assertEquals(2, game.getPlayer(0).getHoleCards().size());
		assertEquals(2, game.getPlayer(1).getHoleCards().size());
		assertEquals(2, listener.holeCardsDealt.size()); // Both players notified
	}

	// --- Fold-to-Win ---

	@Test
	void foldToWin() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int actor = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(actor, ActionType.FOLD, 0));
		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());

		// The other player should have won the pot
		int winner = (actor == 0) ? 1 : 0;
		assertEquals(1, listener.winsWithoutShowdown.size());
		assertEquals(winner, listener.winsWithoutShowdown.get(0).seat);
	}

	// --- Full Hand Playthrough ---

	@Test
	void fullHandToShowdown() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		// Pre-flop: both call/check through
		int current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CALL, 0));
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));

		assertEquals(Phase.FLOP, game.getPhase());
		assertEquals(3, game.getCommunityCards().size());

		// Flop: check-check
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));

		assertEquals(Phase.TURN, game.getPhase());
		assertEquals(4, game.getCommunityCards().size());

		// Turn: check-check
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));

		assertEquals(Phase.RIVER, game.getPhase());
		assertEquals(5, game.getCommunityCards().size());

		// River: check-check → showdown
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));
		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CHECK, 0));

		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());
		assertFalse(listener.showdownResults.isEmpty(), "Showdown should have occurred");

		// Total chips should be preserved
		long totalChips = game.getPlayer(0).getChips() + game.getPlayer(1).getChips();
		assertEquals(2 * BUY_IN, totalChips);
	}

	// --- Raise Mechanics ---

	@Test
	void raiseAndCall() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int actor = game.getCurrentPlayerSeat();
		// Raise to 60
		assertTrue(game.handleAction(actor, ActionType.RAISE, 60));
		assertEquals(Phase.PRE_FLOP, game.getPhase()); // Still preflop

		int other = game.getCurrentPlayerSeat();
		assertNotEquals(actor, other);
		// Call the raise
		assertTrue(game.handleAction(other, ActionType.CALL, 0));

		// Should advance to flop
		assertEquals(Phase.FLOP, game.getPhase());
	}

	@Test
	void invalidRaiseTooSmall() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int actor = game.getCurrentPlayerSeat();
		// Min raise preflop is BB (20), so raising to 30 (raise of 10) should fail
		assertFalse(game.handleAction(actor, ActionType.RAISE, 30));
		// Player should still be the current actor
		assertEquals(actor, game.getCurrentPlayerSeat());
	}

	@Test
	void cannotCheckWhenBetOutstanding() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int actor = game.getCurrentPlayerSeat();
		// In preflop, the SB/first-to-act has less than the BB posted, so checking should fail
		// Actually in heads-up, dealer(SB) acts first preflop. SB posted 10, BB posted 20.
		// So SB cannot check (needs to call or raise to match BB's 20)
		assertFalse(game.handleAction(actor, ActionType.CHECK, 0));
	}

	@Test
	void cannotActOutOfTurn() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int current = game.getCurrentPlayerSeat();
		int other = (current == 0) ? 1 : 0;
		assertFalse(game.handleAction(other, ActionType.FOLD, 0));
	}

	// --- All-In Scenarios ---

	@Test
	void allInGoesToShowdown() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.ALL_IN, 0));

		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CALL, 0));

		// Both all-in → should run out board and go to showdown
		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());
		assertEquals(5, game.getCommunityCards().size());
		assertFalse(listener.showdownResults.isEmpty());

		// Chips preserved
		long total = game.getPlayer(0).getChips() + game.getPlayer(1).getChips();
		assertEquals(2 * BUY_IN, total);
	}

	@Test
	void shortStackAllIn() {
		game.addPlayer(0, "Alice", 100); // Short stack
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.ALL_IN, 0));

		current = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(current, ActionType.CALL, 0));

		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());

		// Total chips preserved
		long total = game.getPlayer(0).getChips() + game.getPlayer(1).getChips();
		assertEquals(100 + BUY_IN, total);
	}

	// --- Three Player Game ---

	@Test
	void threePlayerBlindsAndAction() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.addPlayer(2, "Charlie", BUY_IN);
		game.startHand();

		assertEquals(Phase.PRE_FLOP, game.getPhase());
		// All 3 should have hole cards
		for (int i = 0; i < 3; i++) {
			assertEquals(2, game.getPlayer(i).getHoleCards().size());
		}

		// Action should be on the player after BB (UTG in 3-player = dealer)
		// Dealer=seat X, SB=X+1, BB=X+2, UTG(action)=X+3=X (wraps)=dealer again in 3-player
		assertNotEquals(-1, game.getCurrentPlayerSeat());
	}

	@Test
	void threePlayerFoldToWin() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.addPlayer(2, "Charlie", BUY_IN);
		game.startHand();

		// Two players fold
		int first = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(first, ActionType.FOLD, 0));
		int second = game.getCurrentPlayerSeat();
		assertTrue(game.handleAction(second, ActionType.FOLD, 0));

		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());

		// Total chips across all players must equal total buy-in (pot gets awarded back)
		long totalAfter = game.getPlayer(0).getChips() + game.getPlayer(1).getChips() + game.getPlayer(2).getChips();
		assertEquals(3 * BUY_IN, totalAfter, "Total chips must be preserved");
	}

	// --- Dealer Button Rotation ---

	@Test
	void dealerRotatesEachHand() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.addPlayer(2, "Charlie", BUY_IN);

		game.startHand();
		int firstDealer = game.getDealerSeat();
		// Fold to end hand
		assertTrue(game.handleAction(game.getCurrentPlayerSeat(), ActionType.FOLD, 0));
		assertTrue(game.handleAction(game.getCurrentPlayerSeat(), ActionType.FOLD, 0));

		game.startHand();
		int secondDealer = game.getDealerSeat();
		assertNotEquals(firstDealer, secondDealer, "Dealer should rotate");

		assertTrue(game.handleAction(game.getCurrentPlayerSeat(), ActionType.FOLD, 0));
		assertTrue(game.handleAction(game.getCurrentPlayerSeat(), ActionType.FOLD, 0));

		game.startHand();
		int thirdDealer = game.getDealerSeat();
		assertNotEquals(secondDealer, thirdDealer, "Dealer should keep rotating");
	}

	// --- Timeout / Auto-fold ---

	@Test
	void timeoutAutoFolds() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);
		game.startHand();

		int current = game.getCurrentPlayerSeat();
		game.handleTimeout();

		// Player should have been folded
		assertEquals(PlayerStatus.FOLDED, game.getPlayer(current).getStatus());
		assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase()); // Other player wins
		assertEquals(1, listener.timeouts.size());
	}

	// --- Multiple Hands ---

	@Test
	void canPlayMultipleHandsInSequence() {
		game.addPlayer(0, "Alice", BUY_IN);
		game.addPlayer(1, "Bob", BUY_IN);

		for (int h = 0; h < 5; h++) {
			assertTrue(game.startHand());
			assertEquals(h + 1, game.getHandNumber());

			// Quick fold to end hand
			assertTrue(game.handleAction(game.getCurrentPlayerSeat(), ActionType.FOLD, 0));
			assertEquals(Phase.WAITING_FOR_PLAYERS, game.getPhase());
		}

		// Total chips preserved (minus the fact that folding player loses blinds each hand)
		long total = game.getPlayer(0).getChips() + game.getPlayer(1).getChips();
		assertEquals(2 * BUY_IN, total);
	}

	// --- Test Listener ---

	static class TestListener implements GameListener {
		final List<HoleCardEvent> holeCardsDealt = new ArrayList<>();
		final List<PhaseEvent> phaseChanges = new ArrayList<>();
		final List<ActionEvent> actions = new ArrayList<>();
		final List<List<GameSession.WinResult>> showdownResults = new ArrayList<>();
		final List<WinEvent> winsWithoutShowdown = new ArrayList<>();
		final List<Integer> timeouts = new ArrayList<>();

		@Override
		public void onHoleCardsDealt(int seat, List<Card> cards) {
			holeCardsDealt.add(new HoleCardEvent(seat, new ArrayList<>(cards)));
		}

		@Override
		public void onPhaseChanged(GameSession session) {
			phaseChanges.add(new PhaseEvent(session.getPhase()));
		}

		@Override
		public void onPlayerAction(int seat, ActionType action, long amount) {
			actions.add(new ActionEvent(seat, action, amount));
		}

		@Override
		public void onShowdown(List<GameSession.WinResult> winners) {
			showdownResults.add(new ArrayList<>(winners));
		}

		@Override
		public void onWinWithoutShowdown(int seat, long amount) {
			winsWithoutShowdown.add(new WinEvent(seat, amount));
		}

		@Override
		public void onTurnTimeout(int seat) {
			timeouts.add(seat);
		}

		record HoleCardEvent(int seat, List<Card> cards) {}
		record PhaseEvent(Phase phase) {}
		record ActionEvent(int seat, ActionType action, long amount) {}
		record WinEvent(int seat, long amount) {}
	}
}
