package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.ServerMessage}.
 */
public abstract class ServerMessage_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.ServerMessage {

	/**
	 * Creates a {@link ServerMessage_Impl} instance.
	 */
	public ServerMessage_Impl() {
		super();
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.ServerMessage registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.ServerMessage unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.beginArray();
		out.value(jsonType());
		writeContent(out);
		out.endArray();
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		out.beginObject();
		out.name(0);
		out.value(typeId());
		writeFields(out);
		out.endObject();
	}

	/**
	 * Serializes all fields of this instance to the given binary output.
	 *
	 * @param out
	 *        The binary output to write to.
	 * @throws java.io.IOException If writing fails.
	 */
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		// No fields to write, hook for subclasses.
	}

	/** Helper for reading all fields of this instance. */
	protected final void readContent(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		while (in.hasNext()) {
			int field = in.nextName();
			readField(in, field);
		}
	}

	/** Consumes the value for the field with the given ID and assigns its value. */
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.ServerMessage} type. */
	public static final String SERVER_MESSAGE__XML_ELEMENT = "server-message";

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.ServerMessage} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ServerMessage_Impl readServerMessage_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		switch (in.getLocalName()) {
			case GameStateMsg_Impl.GAME_STATE_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.GameStateMsg_Impl.readGameStateMsg_XmlContent(in);
			}

			case HoleCardsMsg_Impl.HOLE_CARDS_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl.readHoleCardsMsg_XmlContent(in);
			}

			case HandResultMsg_Impl.HAND_RESULT_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.HandResultMsg_Impl.readHandResultMsg_XmlContent(in);
			}

			case ErrorMsg_Impl.ERROR_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.ErrorMsg_Impl.readErrorMsg_XmlContent(in);
			}

			case PlayerJoinedMsg_Impl.PLAYER_JOINED_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl.readPlayerJoinedMsg_XmlContent(in);
			}

			case PlayerLeftMsg_Impl.PLAYER_LEFT_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl.readPlayerLeftMsg_XmlContent(in);
			}

			case TableInfoMsg_Impl.TABLE_INFO_MSG__XML_ELEMENT: {
				return de.haumacher.games.poker.model.impl.TableInfoMsg_Impl.readTableInfoMsg_XmlContent(in);
			}

			default: {
				internalSkipUntilMatchingEndElement(in);
				return null;
			}
		}
	}

	/** Reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	protected final void readContentXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		for (int n = 0, cnt = in.getAttributeCount(); n < cnt; n++) {
			String name = in.getAttributeLocalName(n);
			String value = in.getAttributeValue(n);

			readFieldXmlAttribute(name, value);
		}
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}
			assert event == javax.xml.stream.XMLStreamConstants.START_ELEMENT;

			String localName = in.getLocalName();
			readFieldXmlElement(in, localName);
		}
	}

	/** Parses the given attribute value and assigns it to the field with the given name. */
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			default: {
				// Skip unknown attribute.
			}
		}
	}

	/** Reads the element under the cursor and assigns its contents to the field with the given name. */
	protected void readFieldXmlElement(javax.xml.stream.XMLStreamReader in, String localName) throws javax.xml.stream.XMLStreamException {
		switch (localName) {
			default: {
				internalSkipUntilMatchingEndElement(in);
			}
		}
	}

	protected static final void internalSkipUntilMatchingEndElement(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		int level = 0;
		while (true) {
			switch (in.next()) {
				case javax.xml.stream.XMLStreamConstants.START_ELEMENT: level++; break;
				case javax.xml.stream.XMLStreamConstants.END_ELEMENT: if (level == 0) { return; } else { level--; break; }
			}
		}
	}

}
