package de.haumacher.games.poker.model;

public interface ErrorMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.ErrorMsg} instance.
	 */
	static de.haumacher.games.poker.model.ErrorMsg create() {
		return new de.haumacher.games.poker.model.impl.ErrorMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.ErrorMsg} type in JSON format. */
	String ERROR_MSG__TYPE = "ErrorMsg";

	/** @see #getCode() */
	String CODE__PROP = "code";

	/** @see #getMessage() */
	String MESSAGE__PROP = "message";

	/** Identifier for the {@link de.haumacher.games.poker.model.ErrorMsg} type in binary format. */
	static final int ERROR_MSG__TYPE_ID = 4;

	/** Identifier for the property {@link #getCode()} in binary format. */
	static final int CODE__ID = 1;

	/** Identifier for the property {@link #getMessage()} in binary format. */
	static final int MESSAGE__ID = 2;

	String getCode();

	/**
	 * @see #getCode()
	 */
	de.haumacher.games.poker.model.ErrorMsg setCode(String value);

	String getMessage();

	/**
	 * @see #getMessage()
	 */
	de.haumacher.games.poker.model.ErrorMsg setMessage(String value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ErrorMsg readErrorMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ErrorMsg_Impl result = new de.haumacher.games.poker.model.impl.ErrorMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ErrorMsg readErrorMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.ErrorMsg result = de.haumacher.games.poker.model.impl.ErrorMsg_Impl.readErrorMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link ErrorMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ErrorMsg readErrorMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ErrorMsg_Impl.readErrorMsg_XmlContent(in);
	}

}
