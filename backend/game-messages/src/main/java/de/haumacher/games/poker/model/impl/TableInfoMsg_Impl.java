package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.TableInfoMsg}.
 */
public class TableInfoMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.TableInfoMsg {

	private String _tableId = "";

	private String _roomCode = "";

	private int _seat = 0;

	private long _smallBlind = 0L;

	private long _bigBlind = 0L;

	private int _turnTimeoutSeconds = 0;

	/**
	 * Creates a {@link TableInfoMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.TableInfoMsg#create()
	 */
	public TableInfoMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.TABLE_INFO_MSG;
	}

	@Override
	public final String getTableId() {
		return _tableId;
	}

	@Override
	public de.haumacher.games.poker.model.TableInfoMsg setTableId(String value) {
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
	public final String getRoomCode() {
		return _roomCode;
	}

	@Override
	public de.haumacher.games.poker.model.TableInfoMsg setRoomCode(String value) {
		internalSetRoomCode(value);
		return this;
	}

	/** Internal setter for {@link #getRoomCode()} without chain call utility. */
	protected final void internalSetRoomCode(String value) {
		_listener.beforeSet(this, ROOM_CODE__PROP, value);
		_roomCode = value;
		_listener.afterChanged(this, ROOM_CODE__PROP);
	}

	@Override
	public final int getSeat() {
		return _seat;
	}

	@Override
	public de.haumacher.games.poker.model.TableInfoMsg setSeat(int value) {
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
	public final long getSmallBlind() {
		return _smallBlind;
	}

	@Override
	public de.haumacher.games.poker.model.TableInfoMsg setSmallBlind(long value) {
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
	public de.haumacher.games.poker.model.TableInfoMsg setBigBlind(long value) {
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
	public de.haumacher.games.poker.model.TableInfoMsg setTurnTimeoutSeconds(int value) {
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
		return TABLE_INFO_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			TABLE_ID__PROP, 
			ROOM_CODE__PROP, 
			SEAT__PROP, 
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
			case TABLE_ID__PROP: return getTableId();
			case ROOM_CODE__PROP: return getRoomCode();
			case SEAT__PROP: return getSeat();
			case SMALL_BLIND__PROP: return getSmallBlind();
			case BIG_BLIND__PROP: return getBigBlind();
			case TURN_TIMEOUT_SECONDS__PROP: return getTurnTimeoutSeconds();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case TABLE_ID__PROP: internalSetTableId((String) value); break;
			case ROOM_CODE__PROP: internalSetRoomCode((String) value); break;
			case SEAT__PROP: internalSetSeat((int) value); break;
			case SMALL_BLIND__PROP: internalSetSmallBlind((long) value); break;
			case BIG_BLIND__PROP: internalSetBigBlind((long) value); break;
			case TURN_TIMEOUT_SECONDS__PROP: internalSetTurnTimeoutSeconds((int) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__PROP);
		out.value(getTableId());
		out.name(ROOM_CODE__PROP);
		out.value(getRoomCode());
		out.name(SEAT__PROP);
		out.value(getSeat());
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
			case TABLE_ID__PROP: setTableId(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case ROOM_CODE__PROP: setRoomCode(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case SEAT__PROP: setSeat(in.nextInt()); break;
			case SMALL_BLIND__PROP: setSmallBlind(in.nextLong()); break;
			case BIG_BLIND__PROP: setBigBlind(in.nextLong()); break;
			case TURN_TIMEOUT_SECONDS__PROP: setTurnTimeoutSeconds(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return TABLE_INFO_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__ID);
		out.value(getTableId());
		out.name(ROOM_CODE__ID);
		out.value(getRoomCode());
		out.name(SEAT__ID);
		out.value(getSeat());
		out.name(SMALL_BLIND__ID);
		out.value(getSmallBlind());
		out.name(BIG_BLIND__ID);
		out.value(getBigBlind());
		out.name(TURN_TIMEOUT_SECONDS__ID);
		out.value(getTurnTimeoutSeconds());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.TableInfoMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.TableInfoMsg readTableInfoMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.TableInfoMsg_Impl result = new TableInfoMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case TABLE_ID__ID: setTableId(in.nextString()); break;
			case ROOM_CODE__ID: setRoomCode(in.nextString()); break;
			case SEAT__ID: setSeat(in.nextInt()); break;
			case SMALL_BLIND__ID: setSmallBlind(in.nextLong()); break;
			case BIG_BLIND__ID: setBigBlind(in.nextLong()); break;
			case TURN_TIMEOUT_SECONDS__ID: setTurnTimeoutSeconds(in.nextInt()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.TableInfoMsg} type. */
	public static final String TABLE_INFO_MSG__XML_ELEMENT = "table-info-msg";

	/** XML attribute or element name of a {@link #getTableId} property. */
	private static final String TABLE_ID__XML_ATTR = "table-id";

	/** XML attribute or element name of a {@link #getRoomCode} property. */
	private static final String ROOM_CODE__XML_ATTR = "room-code";

	/** XML attribute or element name of a {@link #getSeat} property. */
	private static final String SEAT__XML_ATTR = "seat";

	/** XML attribute or element name of a {@link #getSmallBlind} property. */
	private static final String SMALL_BLIND__XML_ATTR = "small-blind";

	/** XML attribute or element name of a {@link #getBigBlind} property. */
	private static final String BIG_BLIND__XML_ATTR = "big-blind";

	/** XML attribute or element name of a {@link #getTurnTimeoutSeconds} property. */
	private static final String TURN_TIMEOUT_SECONDS__XML_ATTR = "turn-timeout-seconds";

	@Override
	public String getXmlTagName() {
		return TABLE_INFO_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(TABLE_ID__XML_ATTR, getTableId());
		out.writeAttribute(ROOM_CODE__XML_ATTR, getRoomCode());
		out.writeAttribute(SEAT__XML_ATTR, Integer.toString(getSeat()));
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

	/** Creates a new {@link de.haumacher.games.poker.model.TableInfoMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static TableInfoMsg_Impl readTableInfoMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		TableInfoMsg_Impl result = new TableInfoMsg_Impl();
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
			case ROOM_CODE__XML_ATTR: {
				setRoomCode(value);
				break;
			}
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(value));
				break;
			}
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
			case TABLE_ID__XML_ATTR: {
				setTableId(in.getElementText());
				break;
			}
			case ROOM_CODE__XML_ATTR: {
				setRoomCode(in.getElementText());
				break;
			}
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(in.getElementText()));
				break;
			}
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
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ServerMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
