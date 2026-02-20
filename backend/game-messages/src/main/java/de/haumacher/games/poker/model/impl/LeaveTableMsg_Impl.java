package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.LeaveTableMsg}.
 */
public class LeaveTableMsg_Impl extends de.haumacher.games.poker.model.impl.ClientMessage_Impl implements de.haumacher.games.poker.model.LeaveTableMsg {

	/**
	 * Creates a {@link LeaveTableMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.LeaveTableMsg#create()
	 */
	public LeaveTableMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.LEAVE_TABLE_MSG;
	}

	@Override
	public String jsonType() {
		return LEAVE_TABLE_MSG__TYPE;
	}

	@Override
	public int typeId() {
		return LEAVE_TABLE_MSG__TYPE_ID;
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.LeaveTableMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.LeaveTableMsg readLeaveTableMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl result = new LeaveTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.LeaveTableMsg} type. */
	public static final String LEAVE_TABLE_MSG__XML_ELEMENT = "leave-table-msg";

	@Override
	public String getXmlTagName() {
		return LEAVE_TABLE_MSG__XML_ELEMENT;
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

	/** Creates a new {@link de.haumacher.games.poker.model.LeaveTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static LeaveTableMsg_Impl readLeaveTableMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		LeaveTableMsg_Impl result = new LeaveTableMsg_Impl();
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
