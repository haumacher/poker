package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.ConfirmResultMsg}.
 */
public class ConfirmResultMsg_Impl extends de.haumacher.games.poker.model.impl.ClientMessage_Impl implements de.haumacher.games.poker.model.ConfirmResultMsg {

	/**
	 * Creates a {@link ConfirmResultMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.ConfirmResultMsg#create()
	 */
	public ConfirmResultMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.CONFIRM_RESULT_MSG;
	}

	@Override
	public String jsonType() {
		return CONFIRM_RESULT_MSG__TYPE;
	}

	@Override
	public int typeId() {
		return CONFIRM_RESULT_MSG__TYPE_ID;
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.ConfirmResultMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.ConfirmResultMsg readConfirmResultMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ConfirmResultMsg_Impl result = new ConfirmResultMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.ConfirmResultMsg} type. */
	public static final String CONFIRM_RESULT_MSG__XML_ELEMENT = "confirm-result-msg";

	@Override
	public String getXmlTagName() {
		return CONFIRM_RESULT_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.ConfirmResultMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ConfirmResultMsg_Impl readConfirmResultMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		ConfirmResultMsg_Impl result = new ConfirmResultMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			default: {
				super.readFieldXmlAttribute(name, value);
			}
		}
	}

	@Override
	protected void readFieldXmlElement(javax.xml.stream.XMLStreamReader in, String localName) throws javax.xml.stream.XMLStreamException {
		switch (localName) {
			default: {
				super.readFieldXmlElement(in, localName);
			}
		}
	}

	@Override
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ClientMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
