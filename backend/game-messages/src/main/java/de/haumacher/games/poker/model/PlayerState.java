package de.haumacher.games.poker.model;

/**
 * Public state of one player at the table.
 *
 * <p>Broadcast to all clients. Does not include hole cards (those are sent
	 * privately via {@link HoleCardsMsg}).</p>
 */
public interface PlayerState extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.PlayerState} instance.
	 */
	static de.haumacher.games.poker.model.PlayerState create() {
		return new de.haumacher.games.poker.model.impl.PlayerState_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerState} type in JSON format. */
	String PLAYER_STATE__TYPE = "PlayerState";

	/** @see #getSeat() */
	String SEAT__PROP = "seat";

	/** @see #getDisplayName() */
	String DISPLAY_NAME__PROP = "displayName";

	/** @see #getChips() */
	String CHIPS__PROP = "chips";

	/** @see #getCurrentBet() */
	String CURRENT_BET__PROP = "currentBet";

	/** @see #getStatus() */
	String STATUS__PROP = "status";

	/** @see #getLastAction() */
	String LAST_ACTION__PROP = "lastAction";

	/** Identifier for the property {@link #getSeat()} in binary format. */
	static final int SEAT__ID = 1;

	/** Identifier for the property {@link #getDisplayName()} in binary format. */
	static final int DISPLAY_NAME__ID = 2;

	/** Identifier for the property {@link #getChips()} in binary format. */
	static final int CHIPS__ID = 3;

	/** Identifier for the property {@link #getCurrentBet()} in binary format. */
	static final int CURRENT_BET__ID = 4;

	/** Identifier for the property {@link #getStatus()} in binary format. */
	static final int STATUS__ID = 5;

	/** Identifier for the property {@link #getLastAction()} in binary format. */
	static final int LAST_ACTION__ID = 6;

	/**
	 * Seat index (0-8) at the table.
	 */
	int getSeat();

	/**
	 * @see #getSeat()
	 */
	de.haumacher.games.poker.model.PlayerState setSeat(int value);

	/**
	 * The player's chosen display name.
	 */
	String getDisplayName();

	/**
	 * @see #getDisplayName()
	 */
	de.haumacher.games.poker.model.PlayerState setDisplayName(String value);

	/**
	 * Current chip count (excludes chips already bet this round).
	 */
	long getChips();

	/**
	 * @see #getChips()
	 */
	de.haumacher.games.poker.model.PlayerState setChips(long value);

	/**
	 * Amount bet in the current betting round. Reset to 0 when a new round begins.
	 */
	long getCurrentBet();

	/**
	 * @see #getCurrentBet()
	 */
	de.haumacher.games.poker.model.PlayerState setCurrentBet(long value);

	/**
	 * The player's status in the current hand.
	 */
	de.haumacher.games.poker.model.PlayerStatus getStatus();

	/**
	 * @see #getStatus()
	 */
	de.haumacher.games.poker.model.PlayerState setStatus(de.haumacher.games.poker.model.PlayerStatus value);

	/**
	 * The most recent action taken by this player (null if none yet this hand).
	 */
	de.haumacher.games.poker.model.ActionType getLastAction();

	/**
	 * @see #getLastAction()
	 */
	de.haumacher.games.poker.model.PlayerState setLastAction(de.haumacher.games.poker.model.ActionType value);

	@Override
	public de.haumacher.games.poker.model.PlayerState registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.PlayerState unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerState readPlayerState(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerState_Impl result = new de.haumacher.games.poker.model.impl.PlayerState_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerState readPlayerState(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.PlayerState result = de.haumacher.games.poker.model.impl.PlayerState_Impl.readPlayerState_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link PlayerState} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerState readPlayerState(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.PlayerState_Impl.readPlayerState_XmlContent(in);
	}

}
