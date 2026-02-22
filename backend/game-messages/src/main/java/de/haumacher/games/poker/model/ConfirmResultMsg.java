package de.haumacher.games.poker.model;

/**
 * Sent by a player to confirm they have seen the hand result.
 */
public interface ConfirmResultMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.ConfirmResultMsg} instance.
	 */
	static de.haumacher.games.poker.model.ConfirmResultMsg create() {
		return new de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.ConfirmResultMsg} type in JSON format. */
	String CONFIRM_RESULT_MSG__TYPE = "ConfirmResultMsg";

	/** Identifier for the {@link de.haumacher.games.poker.model.ConfirmResultMsg} type in binary format. */
	static final int CONFIRM_RESULT_MSG__TYPE_ID = 5;

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ConfirmResultMsg readConfirmResultMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl result = new de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ConfirmResultMsg readConfirmResultMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.ConfirmResultMsg result = de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl.readConfirmResultMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link ConfirmResultMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ConfirmResultMsg readConfirmResultMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl.readConfirmResultMsg_XmlContent(in);
	}

}
