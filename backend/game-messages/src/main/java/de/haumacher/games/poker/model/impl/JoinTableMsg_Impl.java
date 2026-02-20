package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.JoinTableMsg}.
 */
public class JoinTableMsg_Impl extends de.haumacher.games.poker.model.impl.ClientMessage_Impl implements de.haumacher.games.poker.model.JoinTableMsg {

	private String _tableId = "";

	private int _preferredSeat = 0;

	private String _displayName = "";

	private long _chips = 0L;

	/**
	 * Creates a {@link JoinTableMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.JoinTableMsg#create()
	 */
	public JoinTableMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.JOIN_TABLE_MSG;
	}

	@Override
	public final String getTableId() {
		return _tableId;
	}

	@Override
	public de.haumacher.games.poker.model.JoinTableMsg setTableId(String value) {
		internalSetTableId(value);
		return this;
	}

	/** Internal setter for {@link #getTableId()} without chain call utility. */
	protected final void internalSetTableId(String value) {
		_listener.beforeSet(this, TABLE_ID__PROP, value);
		_tableId = value;
		_listener.afterChanged(this, TABLE_ID__PROP);
	}

	@Override
	public final int getPreferredSeat() {
		return _preferredSeat;
	}

	@Override
	public de.haumacher.games.poker.model.JoinTableMsg setPreferredSeat(int value) {
		internalSetPreferredSeat(value);
		return this;
	}

	/** Internal setter for {@link #getPreferredSeat()} without chain call utility. */
	protected final void internalSetPreferredSeat(int value) {
		_listener.beforeSet(this, PREFERRED_SEAT__PROP, value);
		_preferredSeat = value;
		_listener.afterChanged(this, PREFERRED_SEAT__PROP);
	}

	@Override
	public final String getDisplayName() {
		return _displayName;
	}

	@Override
	public de.haumacher.games.poker.model.JoinTableMsg setDisplayName(String value) {
		internalSetDisplayName(value);
		return this;
	}

	/** Internal setter for {@link #getDisplayName()} without chain call utility. */
	protected final void internalSetDisplayName(String value) {
		_listener.beforeSet(this, DISPLAY_NAME__PROP, value);
		_displayName = value;
		_listener.afterChanged(this, DISPLAY_NAME__PROP);
	}

	@Override
	public final long getChips() {
		return _chips;
	}

	@Override
	public de.haumacher.games.poker.model.JoinTableMsg setChips(long value) {
		internalSetChips(value);
		return this;
	}

	/** Internal setter for {@link #getChips()} without chain call utility. */
	protected final void internalSetChips(long value) {
		_listener.beforeSet(this, CHIPS__PROP, value);
		_chips = value;
		_listener.afterChanged(this, CHIPS__PROP);
	}

	@Override
	public String jsonType() {
		return JOIN_TABLE_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			TABLE_ID__PROP, 
			PREFERRED_SEAT__PROP, 
			DISPLAY_NAME__PROP, 
			CHIPS__PROP);
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
			case TABLE_ID__PROP: return getTableId();
			case PREFERRED_SEAT__PROP: return getPreferredSeat();
			case DISPLAY_NAME__PROP: return getDisplayName();
			case CHIPS__PROP: return getChips();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case TABLE_ID__PROP: internalSetTableId((String) value); break;
			case PREFERRED_SEAT__PROP: internalSetPreferredSeat((int) value); break;
			case DISPLAY_NAME__PROP: internalSetDisplayName((String) value); break;
			case CHIPS__PROP: internalSetChips((long) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__PROP);
		out.value(getTableId());
		out.name(PREFERRED_SEAT__PROP);
		out.value(getPreferredSeat());
		out.name(DISPLAY_NAME__PROP);
		out.value(getDisplayName());
		out.name(CHIPS__PROP);
		out.value(getChips());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case TABLE_ID__PROP: setTableId(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case PREFERRED_SEAT__PROP: setPreferredSeat(in.nextInt()); break;
			case DISPLAY_NAME__PROP: setDisplayName(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case CHIPS__PROP: setChips(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return JOIN_TABLE_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__ID);
		out.value(getTableId());
		out.name(PREFERRED_SEAT__ID);
		out.value(getPreferredSeat());
		out.name(DISPLAY_NAME__ID);
		out.value(getDisplayName());
		out.name(CHIPS__ID);
		out.value(getChips());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.JoinTableMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.JoinTableMsg readJoinTableMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.JoinTableMsg_Impl result = new JoinTableMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case TABLE_ID__ID: setTableId(in.nextString()); break;
			case PREFERRED_SEAT__ID: setPreferredSeat(in.nextInt()); break;
			case DISPLAY_NAME__ID: setDisplayName(in.nextString()); break;
			case CHIPS__ID: setChips(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.JoinTableMsg} type. */
	public static final String JOIN_TABLE_MSG__XML_ELEMENT = "join-table-msg";

	/** XML attribute or element name of a {@link #getTableId} property. */
	private static final String TABLE_ID__XML_ATTR = "table-id";

	/** XML attribute or element name of a {@link #getPreferredSeat} property. */
	private static final String PREFERRED_SEAT__XML_ATTR = "preferred-seat";

	/** XML attribute or element name of a {@link #getDisplayName} property. */
	private static final String DISPLAY_NAME__XML_ATTR = "display-name";

	/** XML attribute or element name of a {@link #getChips} property. */
	private static final String CHIPS__XML_ATTR = "chips";

	@Override
	public String getXmlTagName() {
		return JOIN_TABLE_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(TABLE_ID__XML_ATTR, getTableId());
		out.writeAttribute(PREFERRED_SEAT__XML_ATTR, Integer.toString(getPreferredSeat()));
		out.writeAttribute(DISPLAY_NAME__XML_ATTR, getDisplayName());
		out.writeAttribute(CHIPS__XML_ATTR, Long.toString(getChips()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.JoinTableMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static JoinTableMsg_Impl readJoinTableMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		JoinTableMsg_Impl result = new JoinTableMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case TABLE_ID__XML_ATTR: {
				setTableId(value);
				break;
			}
			case PREFERRED_SEAT__XML_ATTR: {
				setPreferredSeat(Integer.parseInt(value));
				break;
			}
			case DISPLAY_NAME__XML_ATTR: {
				setDisplayName(value);
				break;
			}
			case CHIPS__XML_ATTR: {
				setChips(Long.parseLong(value));
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
			case TABLE_ID__XML_ATTR: {
				setTableId(in.getElementText());
				break;
			}
			case PREFERRED_SEAT__XML_ATTR: {
				setPreferredSeat(Integer.parseInt(in.getElementText()));
				break;
			}
			case DISPLAY_NAME__XML_ATTR: {
				setDisplayName(in.getElementText());
				break;
			}
			case CHIPS__XML_ATTR: {
				setChips(Long.parseLong(in.getElementText()));
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
