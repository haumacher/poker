package de.haumacher.games.poker.model;

public interface PlayerJoinedMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.PlayerJoinedMsg} instance.
	 */
	static de.haumacher.games.poker.model.PlayerJoinedMsg create() {
		return new de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerJoinedMsg} type in JSON format. */
	String PLAYER_JOINED_MSG__TYPE = "PlayerJoinedMsg";

	/** @see #getPlayerState() */
	String PLAYER_STATE__PROP = "playerState";

	/** Identifier for the {@link de.haumacher.games.poker.model.PlayerJoinedMsg} type in binary format. */
	static final int PLAYER_JOINED_MSG__TYPE_ID = 5;

	/** Identifier for the property {@link #getPlayerState()} in binary format. */
	static final int PLAYER_STATE__ID = 1;

	de.haumacher.games.poker.model.PlayerState getPlayerState();

	/**
	 * @see #getPlayerState()
	 */
	de.haumacher.games.poker.model.PlayerJoinedMsg setPlayerState(de.haumacher.games.poker.model.PlayerState value);

	/**
	 * Checks, whether {@link #getPlayerState()} has a value.
	 */
	boolean hasPlayerState();

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerJoinedMsg readPlayerJoinedMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl result = new de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.PlayerJoinedMsg readPlayerJoinedMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.PlayerJoinedMsg result = de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl.readPlayerJoinedMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link PlayerJoinedMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerJoinedMsg readPlayerJoinedMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl.readPlayerJoinedMsg_XmlContent(in);
	}

}
