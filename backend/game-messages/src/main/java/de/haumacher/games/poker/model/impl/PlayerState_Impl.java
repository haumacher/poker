package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.PlayerState}.
 */
public class PlayerState_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.PlayerState {

	private int _seat = 0;

	private String _displayName = "";

	private long _chips = 0L;

	private long _currentBet = 0L;

	private de.haumacher.games.poker.model.PlayerStatus _status = de.haumacher.games.poker.model.PlayerStatus.WAITING;

	private de.haumacher.games.poker.model.ActionType _lastAction = de.haumacher.games.poker.model.ActionType.FOLD;

	/**
	 * Creates a {@link PlayerState_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.PlayerState#create()
	 */
	public PlayerState_Impl() {
		super();
	}

	@Override
	public final int getSeat() {
		return _seat;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState setSeat(int value) {
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
	public final String getDisplayName() {
		return _displayName;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState setDisplayName(String value) {
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
	public de.haumacher.games.poker.model.PlayerState setChips(long value) {
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
	public final long getCurrentBet() {
		return _currentBet;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState setCurrentBet(long value) {
		internalSetCurrentBet(value);
		return this;
	}

	/** Internal setter for {@link #getCurrentBet()} without chain call utility. */
	protected final void internalSetCurrentBet(long value) {
		_listener.beforeSet(this, CURRENT_BET__PROP, value);
		_currentBet = value;
		_listener.afterChanged(this, CURRENT_BET__PROP);
	}

	@Override
	public final de.haumacher.games.poker.model.PlayerStatus getStatus() {
		return _status;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState setStatus(de.haumacher.games.poker.model.PlayerStatus value) {
		internalSetStatus(value);
		return this;
	}

	/** Internal setter for {@link #getStatus()} without chain call utility. */
	protected final void internalSetStatus(de.haumacher.games.poker.model.PlayerStatus value) {
		if (value == null) throw new IllegalArgumentException("Property 'status' cannot be null.");
		_listener.beforeSet(this, STATUS__PROP, value);
		_status = value;
		_listener.afterChanged(this, STATUS__PROP);
	}

	@Override
	public final de.haumacher.games.poker.model.ActionType getLastAction() {
		return _lastAction;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState setLastAction(de.haumacher.games.poker.model.ActionType value) {
		internalSetLastAction(value);
		return this;
	}

	/** Internal setter for {@link #getLastAction()} without chain call utility. */
	protected final void internalSetLastAction(de.haumacher.games.poker.model.ActionType value) {
		if (value == null) throw new IllegalArgumentException("Property 'lastAction' cannot be null.");
		_listener.beforeSet(this, LAST_ACTION__PROP, value);
		_lastAction = value;
		_listener.afterChanged(this, LAST_ACTION__PROP);
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.PlayerState registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.PlayerState unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public String jsonType() {
		return PLAYER_STATE__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			SEAT__PROP, 
			DISPLAY_NAME__PROP, 
			CHIPS__PROP, 
			CURRENT_BET__PROP, 
			STATUS__PROP, 
			LAST_ACTION__PROP);
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
			case DISPLAY_NAME__PROP: return getDisplayName();
			case CHIPS__PROP: return getChips();
			case CURRENT_BET__PROP: return getCurrentBet();
			case STATUS__PROP: return getStatus();
			case LAST_ACTION__PROP: return getLastAction();
			default: return de.haumacher.games.poker.model.PlayerState.super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case SEAT__PROP: internalSetSeat((int) value); break;
			case DISPLAY_NAME__PROP: internalSetDisplayName((String) value); break;
			case CHIPS__PROP: internalSetChips((long) value); break;
			case CURRENT_BET__PROP: internalSetCurrentBet((long) value); break;
			case STATUS__PROP: internalSetStatus((de.haumacher.games.poker.model.PlayerStatus) value); break;
			case LAST_ACTION__PROP: internalSetLastAction((de.haumacher.games.poker.model.ActionType) value); break;
		}
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		writeContent(out);
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(SEAT__PROP);
		out.value(getSeat());
		out.name(DISPLAY_NAME__PROP);
		out.value(getDisplayName());
		out.name(CHIPS__PROP);
		out.value(getChips());
		out.name(CURRENT_BET__PROP);
		out.value(getCurrentBet());
		out.name(STATUS__PROP);
		getStatus().writeTo(out);
		out.name(LAST_ACTION__PROP);
		getLastAction().writeTo(out);
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case SEAT__PROP: setSeat(in.nextInt()); break;
			case DISPLAY_NAME__PROP: setDisplayName(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case CHIPS__PROP: setChips(in.nextLong()); break;
			case CURRENT_BET__PROP: setCurrentBet(in.nextLong()); break;
			case STATUS__PROP: setStatus(de.haumacher.games.poker.model.PlayerStatus.readPlayerStatus(in)); break;
			case LAST_ACTION__PROP: setLastAction(de.haumacher.games.poker.model.ActionType.readActionType(in)); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		out.beginObject();
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
		out.name(SEAT__ID);
		out.value(getSeat());
		out.name(DISPLAY_NAME__ID);
		out.value(getDisplayName());
		out.name(CHIPS__ID);
		out.value(getChips());
		out.name(CURRENT_BET__ID);
		out.value(getCurrentBet());
		out.name(STATUS__ID);
		getStatus().writeTo(out);
		out.name(LAST_ACTION__ID);
		getLastAction().writeTo(out);
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.PlayerState} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.PlayerState readPlayerState_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerState_Impl result = new PlayerState_Impl();
		result.readContent(in);
		return result;
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
			case SEAT__ID: setSeat(in.nextInt()); break;
			case DISPLAY_NAME__ID: setDisplayName(in.nextString()); break;
			case CHIPS__ID: setChips(in.nextLong()); break;
			case CURRENT_BET__ID: setCurrentBet(in.nextLong()); break;
			case STATUS__ID: setStatus(de.haumacher.games.poker.model.PlayerStatus.readPlayerStatus(in)); break;
			case LAST_ACTION__ID: setLastAction(de.haumacher.games.poker.model.ActionType.readActionType(in)); break;
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.PlayerState} type. */
	public static final String PLAYER_STATE__XML_ELEMENT = "player-state";

	/** XML attribute or element name of a {@link #getSeat} property. */
	private static final String SEAT__XML_ATTR = "seat";

	/** XML attribute or element name of a {@link #getDisplayName} property. */
	private static final String DISPLAY_NAME__XML_ATTR = "display-name";

	/** XML attribute or element name of a {@link #getChips} property. */
	private static final String CHIPS__XML_ATTR = "chips";

	/** XML attribute or element name of a {@link #getCurrentBet} property. */
	private static final String CURRENT_BET__XML_ATTR = "current-bet";

	/** XML attribute or element name of a {@link #getStatus} property. */
	private static final String STATUS__XML_ATTR = "status";

	/** XML attribute or element name of a {@link #getLastAction} property. */
	private static final String LAST_ACTION__XML_ATTR = "last-action";

	@Override
	public String getXmlTagName() {
		return PLAYER_STATE__XML_ELEMENT;
	}

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeAttribute(SEAT__XML_ATTR, Integer.toString(getSeat()));
		out.writeAttribute(DISPLAY_NAME__XML_ATTR, getDisplayName());
		out.writeAttribute(CHIPS__XML_ATTR, Long.toString(getChips()));
		out.writeAttribute(CURRENT_BET__XML_ATTR, Long.toString(getCurrentBet()));
		out.writeAttribute(STATUS__XML_ATTR, getStatus().protocolName());
		out.writeAttribute(LAST_ACTION__XML_ATTR, getLastAction().protocolName());
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.PlayerState} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerState_Impl readPlayerState_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		PlayerState_Impl result = new PlayerState_Impl();
		result.readContentXml(in);
		return result;
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
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(value));
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
			case CURRENT_BET__XML_ATTR: {
				setCurrentBet(Long.parseLong(value));
				break;
			}
			case STATUS__XML_ATTR: {
				setStatus(de.haumacher.games.poker.model.PlayerStatus.valueOfProtocol(value));
				break;
			}
			case LAST_ACTION__XML_ATTR: {
				setLastAction(de.haumacher.games.poker.model.ActionType.valueOfProtocol(value));
				break;
			}
			default: {
				// Skip unknown attribute.
			}
		}
	}

	/** Reads the element under the cursor and assigns its contents to the field with the given name. */
	protected void readFieldXmlElement(javax.xml.stream.XMLStreamReader in, String localName) throws javax.xml.stream.XMLStreamException {
		switch (localName) {
			case SEAT__XML_ATTR: {
				setSeat(Integer.parseInt(in.getElementText()));
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
			case CURRENT_BET__XML_ATTR: {
				setCurrentBet(Long.parseLong(in.getElementText()));
				break;
			}
			case STATUS__XML_ATTR: {
				setStatus(de.haumacher.games.poker.model.PlayerStatus.valueOfProtocol(in.getElementText()));
				break;
			}
			case LAST_ACTION__XML_ATTR: {
				setLastAction(de.haumacher.games.poker.model.ActionType.valueOfProtocol(in.getElementText()));
				break;
			}
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
