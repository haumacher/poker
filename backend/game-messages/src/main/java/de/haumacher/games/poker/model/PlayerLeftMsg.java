package de.haumacher.games.poker.model;

public interface PlayerLeftMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.PlayerLeftMsg} instance.
	 */
	static de.haumacher.games.poker.model.PlayerLeftMsg create() {
		return new de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerLeftMsg} type in JSON format. */
	String PLAYER_LEFT_MSG__TYPE = "PlayerLeftMsg";

	/** @see #getSeat() */
	String SEAT__PROP = "seat";

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerLeftMsg} type in binary format. */
	static final int PLAYER_LEFT_MSG__TYPE_ID = 6;

	/** Identifier for the property {@link #getSeat()} in binary format. */
	static final int SEAT__ID = 1;

	int getSeat();

	/**
	 * @see #getSeat()
	 */
	de.haumacher.games.poker.model.PlayerLeftMsg setSeat(int value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerLeftMsg readPlayerLeftMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl result = new de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerLeftMsg readPlayerLeftMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.PlayerLeftMsg result = de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl.readPlayerLeftMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link PlayerLeftMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerLeftMsg readPlayerLeftMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl.readPlayerLeftMsg_XmlContent(in);
	}

}
