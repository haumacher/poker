package de.haumacher.games.poker.engine;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import de.haumacher.games.poker.model.ActionType;
import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.Phase;
import de.haumacher.games.poker.model.PlayerStatus;

/**
 * The central game state machine for one poker table. Manages seats, the deck,
 * community cards, pot, dealer button, and turn tracking.
 *
 * <p>This class is the single source of truth for game state. It is framework-independent
 * (no Spring, no I/O) — the server layer wraps it and handles WebSocket delivery.</p>
 */
public class GameSession {

	public static final int MAX_SEATS = 9;

	private final PlayerSession[] seats = new PlayerSession[MAX_SEATS];
	private final Deck deck = new Deck();
	private final SecureRandom random;
	private final Pot pot = new Pot();
	private final List<Card> communityCards = new ArrayList<>(5);
	private final long smallBlind;
	private final long bigBlind;

	private Phase phase = Phase.WAITING_FOR_PLAYERS;
	private int dealerSeat = -1;
	private int currentPlayerSeat = -1;
	private int handNumber = 0;
	private long currentMaxBet = 0;
	private long lastRaiseSize = 0;

	/** The seat that last opened/raised (betting round ends when action returns here). */
	private int lastAggressor = -1;

	/** Number of players who have acted this round (used to detect round completion). */
	private boolean bettingRoundComplete;

	private GameListener listener = new GameListener() {};

	public GameSession(long smallBlind, long bigBlind, SecureRandom random) {
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.random = random;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	// --- Seat Management ---

	public boolean addPlayer(int seat, String displayName, long chips) {
		if (seat < 0 || seat >= MAX_SEATS) return false;
		if (seats[seat] != null) return false;
		seats[seat] = new PlayerSession(seat, displayName, chips);
		return true;
	}

	public boolean removePlayer(int seat) {
		if (seat < 0 || seat >= MAX_SEATS) return false;
		if (seats[seat] == null) return false;
		if (phase != Phase.WAITING_FOR_PLAYERS && seats[seat].isInHand()) {
			// If in active hand, fold first
			seats[seat].setStatus(PlayerStatus.FOLDED);
			pot.fold(seat);
		}
		seats[seat] = null;
		return true;
	}

	public PlayerSession getPlayer(int seat) {
		if (seat < 0 || seat >= MAX_SEATS) return null;
		return seats[seat];
	}

	public PlayerSession[] getSeats() {
		return seats;
	}

	// --- Getters ---

	public Phase getPhase() {
		return phase;
	}

	public int getDealerSeat() {
		return dealerSeat;
	}

	public int getCurrentPlayerSeat() {
		return currentPlayerSeat;
	}

	public int getHandNumber() {
		return handNumber;
	}

	public List<Card> getCommunityCards() {
		return communityCards;
	}

	public long getPotTotal() {
		return pot.getTotal();
	}

	public List<Pot.PotInfo> getSidePots() {
		return pot.calculateSidePots();
	}

	public long getSmallBlind() {
		return smallBlind;
	}

	public long getBigBlind() {
		return bigBlind;
	}

	public long getMinRaise() {
		return Math.max(lastRaiseSize, bigBlind);
	}

	// --- Hand Lifecycle ---

	/**
	 * Start a new hand if enough players are ready.
	 *
	 * @return true if a hand was started, false if not enough players
	 */
	public boolean startHand() {
		List<Integer> activePlayers = getReadyPlayers();
		if (activePlayers.size() < 2) {
			return false;
		}

		handNumber++;
		phase = Phase.PRE_FLOP;
		communityCards.clear();
		pot.reset();
		currentMaxBet = 0;
		lastRaiseSize = bigBlind;

		// Reset all players
		for (int s : activePlayers) {
			seats[s].resetForNewHand();
		}

		// Advance dealer button
		dealerSeat = nextActivePlayer(dealerSeat, activePlayers);

		// Shuffle and deal
		deck.shuffle(random);

		// Post blinds
		int sbSeat, bbSeat;
		if (activePlayers.size() == 2) {
			// Heads-up: dealer is small blind
			sbSeat = dealerSeat;
			bbSeat = nextActivePlayer(dealerSeat, activePlayers);
		} else {
			sbSeat = nextActivePlayer(dealerSeat, activePlayers);
			bbSeat = nextActivePlayer(sbSeat, activePlayers);
		}
		postBlind(sbSeat, smallBlind);
		postBlind(bbSeat, bigBlind);

		// Deal hole cards (2 per player, starting left of dealer)
		int dealStart = nextActivePlayer(dealerSeat, activePlayers);
		for (int round = 0; round < 2; round++) {
			int current = dealStart;
			for (int i = 0; i < activePlayers.size(); i++) {
				Card card = deck.deal();
				seats[current].getHoleCards().add(card);
				current = nextActivePlayer(current, activePlayers);
			}
		}

		// Notify hole cards
		for (int s : activePlayers) {
			listener.onHoleCardsDealt(s, seats[s].getHoleCards());
		}

		// Action starts left of big blind (or small blind heads-up)
		if (activePlayers.size() == 2) {
			currentPlayerSeat = sbSeat; // dealer/SB acts first preflop heads-up
		} else {
			currentPlayerSeat = nextActivePlayer(bbSeat, activePlayers);
		}
		lastAggressor = currentPlayerSeat;
		bettingRoundComplete = false;

		listener.onPhaseChanged(this);
		return true;
	}

	private void postBlind(int seat, long amount) {
		PlayerSession player = seats[seat];
		long actual = Math.min(amount, player.getChips());
		player.setChips(player.getChips() - actual);
		player.setCurrentBet(actual);
		pot.addBet(seat, actual);
		if (actual > currentMaxBet) {
			currentMaxBet = actual;
		}
		if (player.getChips() == 0) {
			player.setStatus(PlayerStatus.ALL_IN);
		}
	}

	// --- Player Actions ---

	/**
	 * Handle a player action. Returns true if the action was valid and applied.
	 */
	public boolean handleAction(int seat, ActionType action, long amount) {
		if (phase == Phase.WAITING_FOR_PLAYERS || phase == Phase.SHOWDOWN) {
			return false;
		}
		if (seat != currentPlayerSeat) {
			return false;
		}
		PlayerSession player = seats[seat];
		if (player == null || !player.isActive()) {
			return false;
		}

		switch (action) {
			case FOLD -> {
				player.setStatus(PlayerStatus.FOLDED);
				player.setLastAction(ActionType.FOLD);
				pot.fold(seat);
			}
			case CHECK -> {
				if (player.getCurrentBet() < currentMaxBet) {
					return false; // Can't check when there's a bet to call
				}
				player.setLastAction(ActionType.CHECK);
			}
			case CALL -> {
				long toCall = currentMaxBet - player.getCurrentBet();
				if (toCall <= 0) {
					return false; // Nothing to call
				}
				long actual = Math.min(toCall, player.getChips());
				player.setChips(player.getChips() - actual);
				player.setCurrentBet(player.getCurrentBet() + actual);
				pot.addBet(seat, actual);
				player.setLastAction(ActionType.CALL);
				if (player.getChips() == 0) {
					player.setStatus(PlayerStatus.ALL_IN);
					player.setLastAction(ActionType.ALL_IN);
				}
			}
			case RAISE -> {
				long toCall = currentMaxBet - player.getCurrentBet();
				long raiseAmount = amount - currentMaxBet; // Raise above current max
				if (amount <= currentMaxBet) {
					return false; // Raise must be above current bet
				}
				long minRaise = getMinRaise();
				// Allow all-in even if it's less than min raise
				if (raiseAmount < minRaise && amount < player.getCurrentBet() + player.getChips()) {
					return false; // Raise too small (unless going all-in)
				}
				long totalNeeded = amount - player.getCurrentBet();
				long actual = Math.min(totalNeeded, player.getChips());
				player.setChips(player.getChips() - actual);
				player.setCurrentBet(player.getCurrentBet() + actual);
				pot.addBet(seat, actual);
				lastRaiseSize = player.getCurrentBet() - currentMaxBet;
				if (lastRaiseSize <= 0) lastRaiseSize = bigBlind;
				currentMaxBet = player.getCurrentBet();
				lastAggressor = seat;
				player.setLastAction(ActionType.RAISE);
				if (player.getChips() == 0) {
					player.setStatus(PlayerStatus.ALL_IN);
					player.setLastAction(ActionType.ALL_IN);
				}
			}
			case ALL_IN -> {
				long allIn = player.getChips();
				player.setChips(0);
				player.setCurrentBet(player.getCurrentBet() + allIn);
				pot.addBet(seat, allIn);
				if (player.getCurrentBet() > currentMaxBet) {
					lastRaiseSize = player.getCurrentBet() - currentMaxBet;
					currentMaxBet = player.getCurrentBet();
					lastAggressor = seat;
				}
				player.setStatus(PlayerStatus.ALL_IN);
				player.setLastAction(ActionType.ALL_IN);
			}
		}

		listener.onPlayerAction(seat, action, amount);

		// Check if hand should end
		List<Integer> inHand = getPlayersInHand();
		List<Integer> active = getActivePlayers();

		if (inHand.size() <= 1) {
			// Everyone else folded — last player wins
			if (inHand.size() == 1) {
				int winner = inHand.get(0);
				long winnings = pot.getTotal();
				seats[winner].addChips(winnings);
				listener.onWinWithoutShowdown(winner, winnings);
			}
			endHand();
			return true;
		}

		if (active.isEmpty()) {
			// Everyone remaining is all-in — deal out remaining community cards and go to showdown
			dealRemainingCommunityCards();
			resolveShowdown();
			return true;
		}

		// Advance to next active player
		advanceTurn();

		return true;
	}

	private void advanceTurn() {
		List<Integer> active = getActivePlayers();
		if (active.isEmpty()) {
			// All remaining players are all-in
			dealRemainingCommunityCards();
			resolveShowdown();
			return;
		}

		int next = nextActivePlayer(currentPlayerSeat, active);

		// Check if betting round is complete
		if (next == lastAggressor) {
			// Action returned to the last aggressor — round complete
			advancePhase();
		} else if (!active.contains(lastAggressor)) {
			// Last aggressor went all-in or folded. Round ends when all remaining
			// active players have matched the current bet.
			boolean allMatched = true;
			for (int s : active) {
				if (seats[s].getCurrentBet() < currentMaxBet) {
					allMatched = false;
					break;
				}
			}
			if (allMatched) {
				advancePhase();
			} else {
				currentPlayerSeat = next;
			}
		} else {
			currentPlayerSeat = next;
		}
	}

	private void advancePhase() {
		// Reset current bets for new round
		for (PlayerSession p : seats) {
			if (p != null) {
				p.setCurrentBet(0);
			}
		}
		currentMaxBet = 0;
		lastRaiseSize = bigBlind;

		switch (phase) {
			case PRE_FLOP -> {
				phase = Phase.FLOP;
				communityCards.add(deck.deal());
				communityCards.add(deck.deal());
				communityCards.add(deck.deal());
			}
			case FLOP -> {
				phase = Phase.TURN;
				communityCards.add(deck.deal());
			}
			case TURN -> {
				phase = Phase.RIVER;
				communityCards.add(deck.deal());
			}
			case RIVER -> {
				resolveShowdown();
				return;
			}
			default -> {
				return;
			}
		}

		// Set action to first active player left of dealer
		List<Integer> active = getActivePlayers();
		List<Integer> inHand = getPlayersInHand();
		if (active.size() <= 1 && inHand.size() > 1) {
			// At most one player can act but multiple are in the hand (all-in) —
			// no meaningful betting, deal remaining community cards and go to showdown
			dealRemainingCommunityCards();
			resolveShowdown();
			return;
		}
		if (active.isEmpty()) {
			return;
		}

		currentPlayerSeat = nextActivePlayer(dealerSeat, active);
		lastAggressor = currentPlayerSeat;

		listener.onPhaseChanged(this);
	}

	private void dealRemainingCommunityCards() {
		while (communityCards.size() < 5) {
			communityCards.add(deck.deal());
		}
	}

	private void resolveShowdown() {
		phase = Phase.SHOWDOWN;

		List<Pot.PotInfo> pots = pot.calculateSidePots();
		List<WinResult> allWinners = new ArrayList<>();

		for (Pot.PotInfo potInfo : pots) {
			// Find best hand among eligible players still in the hand
			List<Integer> contenders = new ArrayList<>();
			for (int s : potInfo.eligibleSeats()) {
				if (seats[s] != null && seats[s].isInHand()) {
					contenders.add(s);
				}
			}

			if (contenders.isEmpty()) continue;

			if (contenders.size() == 1) {
				int winner = contenders.get(0);
				seats[winner].addChips(potInfo.amount());
				allWinners.add(new WinResult(winner, potInfo.amount(), "Last player standing"));
				continue;
			}

			// Evaluate hands
			EvaluatedHand bestHand = null;
			List<Integer> winners = new ArrayList<>();

			for (int s : contenders) {
				List<Card> allCards = new ArrayList<>(communityCards);
				allCards.addAll(seats[s].getHoleCards());
				EvaluatedHand eval = HandEvaluator.evaluate(allCards);

				if (bestHand == null || eval.compareTo(bestHand) < 0) {
					bestHand = eval;
					winners.clear();
					winners.add(s);
				} else if (eval.compareTo(bestHand) == 0) {
					winners.add(s);
				}
			}

			// Split pot among winners
			long share = potInfo.amount() / winners.size();
			long remainder = potInfo.amount() % winners.size();
			for (int i = 0; i < winners.size(); i++) {
				long winAmount = share + (i < remainder ? 1 : 0);
				seats[winners.get(i)].addChips(winAmount);
				allWinners.add(new WinResult(winners.get(i), winAmount, bestHand.describe()));
			}
		}

		listener.onShowdown(allWinners);
		endHand();
	}

	private void endHand() {
		phase = Phase.WAITING_FOR_PLAYERS;
		currentPlayerSeat = -1;
	}

	// --- Timeout ---

	/**
	 * Called by the server layer when the turn timer expires. Auto-folds the current player.
	 */
	public void handleTimeout() {
		if (currentPlayerSeat < 0 || phase == Phase.WAITING_FOR_PLAYERS || phase == Phase.SHOWDOWN) {
			return;
		}
		listener.onTurnTimeout(currentPlayerSeat);
		handleAction(currentPlayerSeat, ActionType.FOLD, 0);
	}

	// --- Helpers ---

	private List<Integer> getReadyPlayers() {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < MAX_SEATS; i++) {
			if (seats[i] != null && seats[i].getChips() > 0
					&& seats[i].getStatus() != PlayerStatus.SITTING_OUT) {
				result.add(i);
			}
		}
		return result;
	}

	/** Players still in the hand (ACTIVE or ALL_IN, not FOLDED). */
	List<Integer> getPlayersInHand() {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < MAX_SEATS; i++) {
			if (seats[i] != null && seats[i].isInHand()) {
				result.add(i);
			}
		}
		return result;
	}

	/** Players who can still act (ACTIVE only, not ALL_IN or FOLDED). */
	private List<Integer> getActivePlayers() {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < MAX_SEATS; i++) {
			if (seats[i] != null && seats[i].isActive()) {
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * Find the next player in clockwise order from the given seat, among the active players list.
	 */
	private int nextActivePlayer(int fromSeat, List<Integer> activePlayers) {
		for (int i = 1; i <= MAX_SEATS; i++) {
			int candidate = (fromSeat + i) % MAX_SEATS;
			if (activePlayers.contains(candidate)) {
				return candidate;
			}
		}
		return fromSeat; // Should never happen with >= 1 active player
	}

	public record WinResult(int seat, long amount, String handDescription) {}
}
