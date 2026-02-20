package de.haumacher.games.poker.model;

/**
 * Full public game state broadcast to all players at the table.
 *
 * <p>Sent after every state change (phase transition, player action, player join/leave).
 * Contains everything a client needs to render the table — except private hole cards.</p>
 */
public interface GameStateMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.GameStateMsg} instance.
	 */
	static de.haumacher.games.poker.model.GameStateMsg create() {
		return new de.haumacher.games.poker.model.impl.GameStateMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.GameStateMsg} type in JSON format. */
	String GAME_STATE_MSG__TYPE = "GameStateMsg";

	/** @see #getTableId() */
	String TABLE_ID__PROP = "tableId";

	/** @see #getHandNumber() */
	String HAND_NUMBER__PROP = "handNumber";

	/** @see #getPhase() */
	String PHASE__PROP = "phase";

	/** @see #getCommunityCards() */
	String COMMUNITY_CARDS__PROP = "communityCards";

	/** @see #getPot() */
	String POT__PROP = "pot";

	/** @see #getSidePots() */
	String SIDE_POTS__PROP = "sidePots";

	/** @see #getCurrentPlayerSeat() */
	String CURRENT_PLAYER_SEAT__PROP = "currentPlayerSeat";

	/** @see #getDealerSeat() */
	String DEALER_SEAT__PROP = "dealerSeat";

	/** @see #getPlayers() */
	String PLAYERS__PROP = "players";

	/** @see #getTurnTimeRemaining() */
	String TURN_TIME_REMAINING__PROP = "turnTimeRemaining";

	/** @see #getMinRaise() */
	String MIN_RAISE__PROP = "minRaise";

	/** Identifier for the {@link de.haumacher.games.poker.model.GameStateMsg} type in binary format. */
	static final int GAME_STATE_MSG__TYPE_ID = 1;

	/** Identifier for the property {@link #getTableId()} in binary format. */
	static final int TABLE_ID__ID = 1;

	/** Identifier for the property {@link #getHandNumber()} in binary format. */
	static final int HAND_NUMBER__ID = 2;

	/** Identifier for the property {@link #getPhase()} in binary format. */
	static final int PHASE__ID = 3;

	/** Identifier for the property {@link #getCommunityCards()} in binary format. */
	static final int COMMUNITY_CARDS__ID = 4;

	/** Identifier for the property {@link #getPot()} in binary format. */
	static final int POT__ID = 5;

	/** Identifier for the property {@link #getSidePots()} in binary format. */
	static final int SIDE_POTS__ID = 6;

	/** Identifier for the property {@link #getCurrentPlayerSeat()} in binary format. */
	static final int CURRENT_PLAYER_SEAT__ID = 7;

	/** Identifier for the property {@link #getDealerSeat()} in binary format. */
	static final int DEALER_SEAT__ID = 8;

	/** Identifier for the property {@link #getPlayers()} in binary format. */
	static final int PLAYERS__ID = 9;

	/** Identifier for the property {@link #getTurnTimeRemaining()} in binary format. */
	static final int TURN_TIME_REMAINING__ID = 10;

	/** Identifier for the property {@link #getMinRaise()} in binary format. */
	static final int MIN_RAISE__ID = 11;

	/**
	 * Unique identifier of the table.
	 */
	String getTableId();

	/**
	 * @see #getTableId()
	 */
	de.haumacher.games.poker.model.GameStateMsg setTableId(String value);

	/**
	 * Sequential hand number (1-based), incremented each new deal.
	 */
	int getHandNumber();

	/**
	 * @see #getHandNumber()
	 */
	de.haumacher.games.poker.model.GameStateMsg setHandNumber(int value);

	/**
	 * Current phase of the hand.
	 */
	de.haumacher.games.poker.model.Phase getPhase();

	/**
	 * @see #getPhase()
	 */
	de.haumacher.games.poker.model.GameStateMsg setPhase(de.haumacher.games.poker.model.Phase value);

	/**
	 * Community cards dealt so far (0 in PRE_FLOP, 3 after FLOP, 4 after TURN, 5 after RIVER).
	 */
	java.util.List<de.haumacher.games.poker.model.Card> getCommunityCards();

	/**
	 * @see #getCommunityCards()
	 */
	de.haumacher.games.poker.model.GameStateMsg setCommunityCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value);

	/**
	 * Adds a value to the {@link #getCommunityCards()} list.
	 */
	de.haumacher.games.poker.model.GameStateMsg addCommunityCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Removes a value from the {@link #getCommunityCards()} list.
	 */
	void removeCommunityCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Total chips in all pots combined.
	 */
	long getPot();

	/**
	 * @see #getPot()
	 */
	de.haumacher.games.poker.model.GameStateMsg setPot(long value);

	/**
	 * Breakdown of side pots when players are all-in with unequal stacks. Empty if no side pots.
	 */
	java.util.List<de.haumacher.games.poker.model.SidePot> getSidePots();

	/**
	 * @see #getSidePots()
	 */
	de.haumacher.games.poker.model.GameStateMsg setSidePots(java.util.List<? extends de.haumacher.games.poker.model.SidePot> value);

	/**
	 * Adds a value to the {@link #getSidePots()} list.
	 */
	de.haumacher.games.poker.model.GameStateMsg addSidePot(de.haumacher.games.poker.model.SidePot value);

	/**
	 * Removes a value from the {@link #getSidePots()} list.
	 */
	void removeSidePot(de.haumacher.games.poker.model.SidePot value);

	/**
	 * Seat index of the player whose turn it is to act, or -1 if no action pending.
	 */
	int getCurrentPlayerSeat();

	/**
	 * @see #getCurrentPlayerSeat()
	 */
	de.haumacher.games.poker.model.GameStateMsg setCurrentPlayerSeat(int value);

	/**
	 * Seat index of the current dealer button.
	 */
	int getDealerSeat();

	/**
	 * @see #getDealerSeat()
	 */
	de.haumacher.games.poker.model.GameStateMsg setDealerSeat(int value);

	/**
	 * State of all seated players (up to 9).
	 */
	java.util.List<de.haumacher.games.poker.model.PlayerState> getPlayers();

	/**
	 * @see #getPlayers()
	 */
	de.haumacher.games.poker.model.GameStateMsg setPlayers(java.util.List<? extends de.haumacher.games.poker.model.PlayerState> value);

	/**
	 * Adds a value to the {@link #getPlayers()} list.
	 */
	de.haumacher.games.poker.model.GameStateMsg addPlayer(de.haumacher.games.poker.model.PlayerState value);

	/**
	 * Removes a value from the {@link #getPlayers()} list.
	 */
	void removePlayer(de.haumacher.games.poker.model.PlayerState value);

	/**
	 * Seconds remaining for the current player to act before auto-fold.
	 */
	int getTurnTimeRemaining();

	/**
	 * @see #getTurnTimeRemaining()
	 */
	de.haumacher.games.poker.model.GameStateMsg setTurnTimeRemaining(int value);

	/**
	 * Minimum total bet amount for a legal raise.
	 */
	long getMinRaise();

	/**
	 * @see #getMinRaise()
	 */
	de.haumacher.games.poker.model.GameStateMsg setMinRaise(long value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.GameStateMsg readGameStateMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.GameStateMsg_Impl result = new de.haumacher.games.poker.model.impl.GameStateMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.GameStateMsg readGameStateMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.GameStateMsg result = de.haumacher.games.poker.model.impl.GameStateMsg_Impl.readGameStateMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link GameStateMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static GameStateMsg readGameStateMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.GameStateMsg_Impl.readGameStateMsg_XmlContent(in);
	}

}
