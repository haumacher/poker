package de.haumacher.games.poker.engine;

import java.util.List;

import de.haumacher.games.poker.model.Card;

/**
 * Callback interface for game events. The server layer implements this to broadcast
 * messages over WebSocket. The engine itself doesn't own threading or I/O.
 */
public interface GameListener {

	/** Called when a new hand starts and hole cards have been dealt. */
	default void onHoleCardsDealt(int seat, List<Card> cards) {}

	/** Called when the game phase changes (e.g., community cards dealt). */
	default void onPhaseChanged(GameSession session) {}

	/** Called when a player acts. */
	default void onPlayerAction(int seat, de.haumacher.games.poker.model.ActionType action, long amount) {}

	/** Called at showdown with results. */
	default void onShowdown(List<GameSession.WinResult> winners) {}

	/** Called when a player wins without showdown (all others folded). */
	default void onWinWithoutShowdown(int seat, long amount) {}

	/** Called when the turn timer expires for a player (engine requests auto-fold). */
	default void onTurnTimeout(int seat) {}
}
