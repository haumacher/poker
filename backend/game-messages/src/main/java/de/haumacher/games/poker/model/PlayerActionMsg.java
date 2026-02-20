package de.haumacher.games.poker.model;

/**
 * A player's action during a betting round.
 */
public interface PlayerActionMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.PlayerActionMsg} instance.
	 */
	static de.haumacher.games.poker.model.PlayerActionMsg create() {
		return new de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerActionMsg} type in JSON format. */
	String PLAYER_ACTION_MSG__TYPE = "PlayerActionMsg";

	/** @see #getActionType() */
	String ACTION_TYPE__PROP = "actionType";

	/** @see #getAmount() */
	String AMOUNT__PROP = "amount";

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerActionMsg} type in binary format. */
	static final int PLAYER_ACTION_MSG__TYPE_ID = 1;

	/** Identifier for the property {@link #getActionType()} in binary format. */
	static final int ACTION_TYPE__ID = 1;

	/** Identifier for the property {@link #getAmount()} in binary format. */
	static final int AMOUNT__ID = 2;

	/**
	 * The type of action (FOLD, CHECK, CALL, RAISE, ALL_IN).
	 */
	de.haumacher.games.poker.model.ActionType getActionType();

	/**
	 * @see #getActionType()
	 */
	de.haumacher.games.poker.model.PlayerActionMsg setActionType(de.haumacher.games.poker.model.ActionType value);

	/**
	 * Total bet amount (only meaningful for RAISE — the target bet size, not the increment).
	 * Ignored for FOLD, CHECK, CALL, ALL_IN.
	 */
	long getAmount();

	/**
	 * @see #getAmount()
	 */
	de.haumacher.games.poker.model.PlayerActionMsg setAmount(long value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerActionMsg readPlayerActionMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl result = new de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerActionMsg readPlayerActionMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.PlayerActionMsg result = de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl.readPlayerActionMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link PlayerActionMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerActionMsg readPlayerActionMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl.readPlayerActionMsg_XmlContent(in);
	}

}
