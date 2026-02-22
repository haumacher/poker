package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.CreateTableMsg}.
 */
public class CreateTableMsg_Impl extends de.haumacher.games.poker.model.impl.ClientMessage_Impl implements de.haumacher.games.poker.model.CreateTableMsg {

	private long _smallBlind = 0L;

	private long _bigBlind = 0L;

	private int _turnTimeoutSeconds = 0;

	/**
	 * Creates a {@link CreateTableMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.CreateTableMsg#create()
	 */
	public CreateTableMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.CREATE_TABLE_MSG;
	}

	@Override
	public final long getSmallBlind() {
		return _smallBlind;
	}

	@Override
	public de.haumacher.games.poker.model.CreateTableMsg setSmallBlind(long value) {
		internalSetSmallBlind(value);
		return this;
	}

	/** Internal setter for {@link #getSmallBlind()} without chain call utility. */
	protected final void internalSetSmallBlind(long value) {
		_listener.beforeSet(this, SMALL_BLIND__PROP, value);
		_smallBlind = value;
		_listener.afterChanged(this, SMALL_BLIND__PROP);
	}

	@Override
	public final long getBigBlind() {
		return _bigBlind;
	}

	@Override
	public de.haumacher.games.poker.model.CreateTableMsg setBigBlind(long value) {
		internalSetBigBlind(value);
		return this;
	}

	/** Internal setter for {@link #getBigBlind()} without chain call utility. */
	protected final void internalSetBigBlind(long value) {
		_listener.beforeSet(this, BIG_BLIND__PROP, value);
		_bigBlind = value;
		_listener.afterChanged(this, BIG_BLIND__PROP);
	}

	@Override
	public final int getTurnTimeoutSeconds() {
		return _turnTimeoutSeconds;
	}

	@Override
	public de.haumacher.games.poker.model.CreateTableMsg setTurnTimeoutSeconds(int value) {
		internalSetTurnTimeoutSeconds(value);
		return this;
	}

	/** Internal setter for {@link #getTurnTimeoutSeconds()} without chain call utility. */
	protected final void internalSetTurnTimeoutSeconds(int value) {
		_listener.beforeSet(this, TURN_TIMEOUT_SECONDS__PROP, value);
		_turnTimeoutSeconds = value;
		_listener.afterChanged(this, TURN_TIMEOUT_SECONDS__PROP);
	}

	@Override
	public String jsonType() {
		return CREATE_TABLE_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			SMALL_BLIND__PROP, 
			BIG_BLIND__PROP, 
			TURN_TIMEOUT_SECONDS__PROP);
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
			case SMALL_BLIND__PROP: return getSmallBlind();
			case BIG_BLIND__PROP: return getBigBlind();
			case TURN_TIMEOUT_SECONDS__PROP: return getTurnTimeoutSeconds();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case SMALL_BLIND__PROP: internalSetSmallBlind((long) value); break;
			case BIG_BLIND__PROP: internalSetBigBlind((long) value); break;
			case TURN_TIMEOUT_SECONDS__PROP: internalSetTurnTimeoutSeconds((int) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(SMALL_BLIND__PROP);
		out.value(getSmallBlind());
		out.name(BIG_BLIND__PROP);
		out.value(getBigBlind());
		out.name(TURN_TIMEOUT_SECONDS__PROP);
		out.value(getTurnTimeoutSeconds());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case SMALL_BLIND__PROP: setSmallBlind(in.nextLong()); break;
			case BIG_BLIND__PROP: setBigBlind(in.nextLong()); break;
			case TURN_TIMEOUT_SECONDS__PROP: setTurnTimeoutSeconds(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return CREATE_TABLE_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(SMALL_BLIND__ID);
		out.value(getSmallBlind());
		out.name(BIG_BLIND__ID);
		out.value(getBigBlind());
		out.name(TURN_TIMEOUT_SECONDS__ID);
		out.value(getTurnTimeoutSeconds());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.CreateTableMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.CreateTableMsg readCreateTableMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.CreateTableMsg_Impl result = new CreateTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case SMALL_BLIND__ID: setSmallBlind(in.nextLong()); break;
			case BIG_BLIND__ID: setBigBlind(in.nextLong()); break;
			case TURN_TIMEOUT_SECONDS__ID: setTurnTimeoutSeconds(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.CreateTableMsg} type. */
	public static final String CREATE_TABLE_MSG__XML_ELEMENT = "create-table-msg";

	/** XML attribute or element name of a {@link #getSmallBlind} property. */
	private static final String SMALL_BLIND__XML_ATTR = "small-blind";

	/** XML attribute or element name of a {@link #getBigBlind} property. */
	private static final String BIG_BLIND__XML_ATTR = "big-blind";

	/** XML attribute or element name of a {@link #getTurnTimeoutSeconds} property. */
	private static final String TURN_TIMEOUT_SECONDS__XML_ATTR = "turn-timeout-seconds";

	@Override
	public String getXmlTagName() {
		return CREATE_TABLE_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(SMALL_BLIND__XML_ATTR, Long.toString(getSmallBlind()));
		out.writeAttribute(BIG_BLIND__XML_ATTR, Long.toString(getBigBlind()));
		out.writeAttribute(TURN_TIMEOUT_SECONDS__XML_ATTR, Integer.toString(getTurnTimeoutSeconds()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.CreateTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static CreateTableMsg_Impl readCreateTableMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		CreateTableMsg_Impl result = new CreateTableMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case SMALL_BLIND__XML_ATTR: {
				setSmallBlind(Long.parseLong(value));
				break;
			}
			case BIG_BLIND__XML_ATTR: {
				setBigBlind(Long.parseLong(value));
				break;
			}
			case TURN_TIMEOUT_SECONDS__XML_ATTR: {
				setTurnTimeoutSeconds(Integer.parseInt(value));
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
			case SMALL_BLIND__XML_ATTR: {
				setSmallBlind(Long.parseLong(in.getElementText()));
				break;
			}
			case BIG_BLIND__XML_ATTR: {
				setBigBlind(Long.parseLong(in.getElementText()));
				break;
			}
			case TURN_TIMEOUT_SECONDS__XML_ATTR: {
				setTurnTimeoutSeconds(Integer.parseInt(in.getElementText()));
				break;
			}
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
