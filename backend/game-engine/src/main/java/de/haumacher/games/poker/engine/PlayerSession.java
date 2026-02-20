package de.haumacher.games.poker.engine;

import java.util.ArrayList;
import java.util.List;

import de.haumacher.games.poker.model.ActionType;
import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.PlayerStatus;

/**
 * Represents one player seated at a table during a game session.
 */
public class PlayerSession {

	private final int seat;
	private String displayName;
	private long chips;
	private final List<Card> holeCards = new ArrayList<>(2);
	private long currentBet;
	private PlayerStatus status;
	private ActionType lastAction;

	public PlayerSession(int seat, String displayName, long chips) {
		this.seat = seat;
		this.displayName = displayName;
		this.chips = chips;
		this.status = PlayerStatus.WAITING;
	}

	public int getSeat() {
		return seat;
	}

	public String getDisplayName() {
		return displayName;
	}

	public long getChips() {
		return chips;
	}

	public void setChips(long chips) {
		this.chips = chips;
	}

	public void addChips(long amount) {
		this.chips += amount;
	}

	public List<Card> getHoleCards() {
		return holeCards;
	}

	public long getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(long currentBet) {
		this.currentBet = currentBet;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	public ActionType getLastAction() {
		return lastAction;
	}

	public void setLastAction(ActionType lastAction) {
		this.lastAction = lastAction;
	}

	/**
	 * Reset player state for a new hand.
	 */
	public void resetForNewHand() {
		holeCards.clear();
		currentBet = 0;
		lastAction = null;
		if (status != PlayerStatus.SITTING_OUT) {
			status = chips > 0 ? PlayerStatus.ACTIVE : PlayerStatus.SITTING_OUT;
		}
	}

	public boolean isActive() {
		return status == PlayerStatus.ACTIVE;
	}

	public boolean isInHand() {
		return status == PlayerStatus.ACTIVE || status == PlayerStatus.ALL_IN;
	}
}
