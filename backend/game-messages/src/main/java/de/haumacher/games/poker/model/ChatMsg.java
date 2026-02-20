package de.haumacher.games.poker.model;

public interface ChatMsg extends ClientMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.ChatMsg} instance.
	 */
	static de.haumacher.games.poker.model.ChatMsg create() {
		return new de.haumacher.games.poker.model.impl.ChatMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.ChatMsg} type in JSON format. */
	String CHAT_MSG__TYPE = "ChatMsg";

	/** @see #getText() */
	String TEXT__PROP = "text";

	/** Identifier for the {@link de.haumacher.games.poker.model.ChatMsg} type in binary format. */
	static final int CHAT_MSG__TYPE_ID = 4;

	/** Identifier for the property {@link #getText()} in binary format. */
	static final int TEXT__ID = 1;

	String getText();

	/**
	 * @see #getText()
	 */
	de.haumacher.games.poker.model.ChatMsg setText(String value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ChatMsg readChatMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ChatMsg_Impl result = new de.haumacher.games.poker.model.impl.ChatMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ChatMsg readChatMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.ChatMsg result = de.haumacher.games.poker.model.impl.ChatMsg_Impl.readChatMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link ChatMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ChatMsg readChatMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ChatMsg_Impl.readChatMsg_XmlContent(in);
	}

}
