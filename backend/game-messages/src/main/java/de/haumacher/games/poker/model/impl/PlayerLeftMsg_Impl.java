package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.PlayerLeftMsg}.
 */
public class PlayerLeftMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.PlayerLeftMsg {

	private int _seat = 0;

	/**
	 * Creates a {@link PlayerLeftMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.PlayerLeftMsg#create()
	 */
	public PlayerLeftMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.PLAYER_LEFT_MSG;
	}

	@Override
	public final int getSeat() {
		return _seat;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerLeftMsg setSeat(int value) {
		internalSetSeat(value);
		return this;
	}

	/** Internal setter for {@link #getSeat()} without chain call utility. */
	protected final void internalSetSeat(int value) {
		_listener.beforeSet(this, SEAT__PROP, value);
		_seat = value;
		_listener.afterChanged(this, SEAT__PROP);
	}

	@Override
	public String jsonType() {
		return PLAYER_LEFT_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			SEAT__PROP);
		PROPERTIES = java.util.Collections.unmodifiableList(local);
	}

	static final java.util.Set<String> TRANSIENT_PROPERTIES;
	static {
		java.util.HashSet<String> tmp = new java.util.HashSet<>();
		tmp.addAll(java.util.Arrays.asList(
				));
		TRANSIENT_PROPERTIES = java.util.Collections.unmodifiableSet(tmp);
	}

	@Override
	public java.util.List<String> properties() {
		return PROPERTIES;
	}

	@Override
	public java.util.Set<String> transientProperties() {
		return TRANSIENT_PROPERTIES;
	}

	@Override
	public Object get(String field) {
		switch (field) {
			case SEAT__PROP: return getSeat();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case SEAT__PROP: internalSetSeat((int) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(SEAT__PROP);
		out.value(getSeat());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case SEAT__PROP: setSeat(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return PLAYER_LEFT_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(SEAT__ID);
		out.value(getSeat());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.PlayerLeftMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.PlayerLeftMsg readPlayerLeftMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl result = new PlayerLeftMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case SEAT__ID: setSeat(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.PlayerLeftMsg} type. */
	public static final String PLAYER_LEFT_MSG__XML_ELEMENT = "player-left-msg";

	/** XML attribute or element name of a {@link #getSeat} property. */
	private static final String SEAT__XML_ATTR = "seat";

	@Override
	public String getXmlTagName() {
		return PLAYER_LEFT_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(SEAT__XML_ATTR, Integer.toString(getSeat()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.PlayerLeftMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerLeftMsg_Impl readPlayerLeftMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		PlayerLeftMsg_Impl result = new PlayerLeftMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(value));
				break;
			}
			default: {
				super.readFieldXmlAttribute(name, value);
			}
		}
	}

	@Override
	protected void readFieldXmlElement(javax.xml.stream.XMLStreamReader in, String localName) throws javax.xml.stream.XMLStreamException {
		switch (localName) {
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(in.getElementText()));
				break;
			}
			default: {
				super.readFieldXmlElement(in, localName);
			}
		}
	}

	@Override
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ServerMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
