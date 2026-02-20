package de.haumacher.games.poker.model;

/**
 * Confirmation sent privately after a player creates or joins a table.
 *
 * <p>Contains the room code the player can share with friends to invite them.</p>
 */
public interface TableInfoMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.TableInfoMsg} instance.
	 */
	static de.haumacher.games.poker.model.TableInfoMsg create() {
		return new de.haumacher.games.poker.model.impl.TableInfoMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.TableInfoMsg} type in JSON format. */
	String TABLE_INFO_MSG__TYPE = "TableInfoMsg";

	/** @see #getTableId() */
	String TABLE_ID__PROP = "tableId";

	/** @see #getRoomCode() */
	String ROOM_CODE__PROP = "roomCode";

	/** @see #getSeat() */
	String SEAT__PROP = "seat";

	/** @see #getSmallBlind() */
	String SMALL_BLIND__PROP = "smallBlind";

	/** @see #getBigBlind() */
	String BIG_BLIND__PROP = "bigBlind";

	/** Identifier for the {@link de.haumacher.games.poker.model.TableInfoMsg} type in binary format. */
	static final int TABLE_INFO_MSG__TYPE_ID = 7;

	/** Identifier for the property {@link #getTableId()} in binary format. */
	static final int TABLE_ID__ID = 1;

	/** Identifier for the property {@link #getRoomCode()} in binary format. */
	static final int ROOM_CODE__ID = 2;

	/** Identifier for the property {@link #getSeat()} in binary format. */
	static final int SEAT__ID = 3;

	/** Identifier for the property {@link #getSmallBlind()} in binary format. */
	static final int SMALL_BLIND__ID = 4;

	/** Identifier for the property {@link #getBigBlind()} in binary format. */
	static final int BIG_BLIND__ID = 5;

	/**
	 * Unique identifier of the table.
	 */
	String getTableId();

	/**
	 * @see #getTableId()
	 */
	de.haumacher.games.poker.model.TableInfoMsg setTableId(String value);

	/**
	 * 6-character room code for sharing with friends.
	 */
	String getRoomCode();

	/**
	 * @see #getRoomCode()
	 */
	de.haumacher.games.poker.model.TableInfoMsg setRoomCode(String value);

	/**
	 * Assigned seat index, or -1 if the player has not sat down yet (create only).
	 */
	int getSeat();

	/**
	 * @see #getSeat()
	 */
	de.haumacher.games.poker.model.TableInfoMsg setSeat(int value);

	/**
	 * Small blind amount for this table.
	 */
	long getSmallBlind();

	/**
	 * @see #getSmallBlind()
	 */
	de.haumacher.games.poker.model.TableInfoMsg setSmallBlind(long value);

	/**
	 * Big blind amount for this table.
	 */
	long getBigBlind();

	/**
	 * @see #getBigBlind()
	 */
	de.haumacher.games.poker.model.TableInfoMsg setBigBlind(long value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.TableInfoMsg readTableInfoMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.TableInfoMsg_Impl result = new de.haumacher.games.poker.model.impl.TableInfoMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.TableInfoMsg readTableInfoMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.TableInfoMsg result = de.haumacher.games.poker.model.impl.TableInfoMsg_Impl.readTableInfoMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link TableInfoMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static TableInfoMsg readTableInfoMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.TableInfoMsg_Impl.readTableInfoMsg_XmlContent(in);
	}

}
