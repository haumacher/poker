package de.haumacher.games.poker.model;

/**
 * Request to create a new table with configurable blinds.
 */
public interface CreateTableMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.CreateTableMsg} instance.
	 */
	static de.haumacher.games.poker.model.CreateTableMsg create() {
		return new de.haumacher.games.poker.model.impl.CreateTableMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.CreateTableMsg} type in JSON format. */
	String CREATE_TABLE_MSG__TYPE = "CreateTableMsg";

	/** @see #getSmallBlind() */
	String SMALL_BLIND__PROP = "smallBlind";

	/** @see #getBigBlind() */
	String BIG_BLIND__PROP = "bigBlind";

	/** @see #getTurnTimeoutSeconds() */
	String TURN_TIMEOUT_SECONDS__PROP = "turnTimeoutSeconds";

	/** Identifier for the {@link de.haumacher.games.poker.model.CreateTableMsg} type in binary format. */
	static final int CREATE_TABLE_MSG__TYPE_ID = 6;

	/** Identifier for the property {@link #getSmallBlind()} in binary format. */
	static final int SMALL_BLIND__ID = 1;

	/** Identifier for the property {@link #getBigBlind()} in binary format. */
	static final int BIG_BLIND__ID = 2;

	/** Identifier for the property {@link #getTurnTimeoutSeconds()} in binary format. */
	static final int TURN_TIMEOUT_SECONDS__ID = 3;

	/**
	 * Small blind amount. Must be positive.
	 */
	long getSmallBlind();

	/**
	 * @see #getSmallBlind()
	 */
	de.haumacher.games.poker.model.CreateTableMsg setSmallBlind(long value);

	/**
	 * Big blind amount. Must be exactly 2x smallBlind.
	 */
	long getBigBlind();

	/**
	 * @see #getBigBlind()
	 */
	de.haumacher.games.poker.model.CreateTableMsg setBigBlind(long value);

	/**
	 * Seconds per turn. 0 means no timeout.
	 */
	int getTurnTimeoutSeconds();

	/**
	 * @see #getTurnTimeoutSeconds()
	 */
	de.haumacher.games.poker.model.CreateTableMsg setTurnTimeoutSeconds(int value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.CreateTableMsg readCreateTableMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.CreateTableMsg_Impl result = new de.haumacher.games.poker.model.impl.CreateTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.CreateTableMsg readCreateTableMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.CreateTableMsg result = de.haumacher.games.poker.model.impl.CreateTableMsg_Impl.readCreateTableMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link CreateTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static CreateTableMsg readCreateTableMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.CreateTableMsg_Impl.readCreateTableMsg_XmlContent(in);
	}

}
