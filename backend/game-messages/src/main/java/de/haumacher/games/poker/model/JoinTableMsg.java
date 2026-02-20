package de.haumacher.games.poker.model;

public interface JoinTableMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.JoinTableMsg} instance.
	 */
	static de.haumacher.games.poker.model.JoinTableMsg create() {
		return new de.haumacher.games.poker.model.impl.JoinTableMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.JoinTableMsg} type in JSON format. */
	String JOIN_TABLE_MSG__TYPE = "JoinTableMsg";

	/** @see #getTableId() */
	String TABLE_ID__PROP = "tableId";

	/** @see #getPreferredSeat() */
	String PREFERRED_SEAT__PROP = "preferredSeat";

	/** Identifier for the {@link de.haumacher.games.poker.model.JoinTableMsg} type in binary format. */
	static final int JOIN_TABLE_MSG__TYPE_ID = 2;

	/** Identifier for the property {@link #getTableId()} in binary format. */
	static final int TABLE_ID__ID = 1;

	/** Identifier for the property {@link #getPreferredSeat()} in binary format. */
	static final int PREFERRED_SEAT__ID = 2;

	String getTableId();

	/**
	 * @see #getTableId()
	 */
	de.haumacher.games.poker.model.JoinTableMsg setTableId(String value);

	int getPreferredSeat();

	/**
	 * @see #getPreferredSeat()
	 */
	de.haumacher.games.poker.model.JoinTableMsg setPreferredSeat(int value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.JoinTableMsg readJoinTableMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.JoinTableMsg_Impl result = new de.haumacher.games.poker.model.impl.JoinTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.JoinTableMsg readJoinTableMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.JoinTableMsg result = de.haumacher.games.poker.model.impl.JoinTableMsg_Impl.readJoinTableMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link JoinTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static JoinTableMsg readJoinTableMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.JoinTableMsg_Impl.readJoinTableMsg_XmlContent(in);
	}

}
