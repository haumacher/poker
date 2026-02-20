package de.haumacher.games.poker.model;

/**
 * Request to leave the current table and cash out chips.
 */
public interface LeaveTableMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.LeaveTableMsg} instance.
	 */
	static de.haumacher.games.poker.model.LeaveTableMsg create() {
		return new de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.LeaveTableMsg} type in JSON format. */
	String LEAVE_TABLE_MSG__TYPE = "LeaveTableMsg";

	/** Identifier for the {@link de.haumacher.games.poker.model.LeaveTableMsg} type in binary format. */
	static final int LEAVE_TABLE_MSG__TYPE_ID = 3;

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.LeaveTableMsg readLeaveTableMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl result = new de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.LeaveTableMsg readLeaveTableMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.LeaveTableMsg result = de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl.readLeaveTableMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link LeaveTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static LeaveTableMsg readLeaveTableMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl.readLeaveTableMsg_XmlContent(in);
	}

}
