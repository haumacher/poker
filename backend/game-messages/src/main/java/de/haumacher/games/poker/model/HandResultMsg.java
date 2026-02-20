package de.haumacher.games.poker.model;

public interface HandResultMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.HandResultMsg} instance.
	 */
	static de.haumacher.games.poker.model.HandResultMsg create() {
		return new de.haumacher.games.poker.model.impl.HandResultMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.HandResultMsg} type in JSON format. */
	String HAND_RESULT_MSG__TYPE = "HandResultMsg";

	/** @see #getWinners() */
	String WINNERS__PROP = "winners";

	/** Identifier for the {@link de.haumacher.games.poker.model.HandResultMsg} type in binary format. */
	static final int HAND_RESULT_MSG__TYPE_ID = 3;

	/** Identifier for the property {@link #getWinners()} in binary format. */
	static final int WINNERS__ID = 1;

	java.util.List<de.haumacher.games.poker.model.WinnerInfo> getWinners();

	/**
	 * @see #getWinners()
	 */
	de.haumacher.games.poker.model.HandResultMsg setWinners(java.util.List<? extends de.haumacher.games.poker.model.WinnerInfo> value);

	/**
	 * Adds a value to the {@link #getWinners()} list.
	 */
	de.haumacher.games.poker.model.HandResultMsg addWinner(de.haumacher.games.poker.model.WinnerInfo value);

	/**
	 * Removes a value from the {@link #getWinners()} list.
	 */
	void removeWinner(de.haumacher.games.poker.model.WinnerInfo value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.HandResultMsg readHandResultMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.HandResultMsg_Impl result = new de.haumacher.games.poker.model.impl.HandResultMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.HandResultMsg readHandResultMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.HandResultMsg result = de.haumacher.games.poker.model.impl.HandResultMsg_Impl.readHandResultMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link HandResultMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static HandResultMsg readHandResultMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.HandResultMsg_Impl.readHandResultMsg_XmlContent(in);
	}

}
