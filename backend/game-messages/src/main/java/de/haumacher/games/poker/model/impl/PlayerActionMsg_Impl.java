package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.PlayerActionMsg}.
 */
public class PlayerActionMsg_Impl extends de.haumacher.games.poker.model.impl.ClientMessage_Impl implements de.haumacher.games.poker.model.PlayerActionMsg {

	private de.haumacher.games.poker.model.ActionType _actionType = de.haumacher.games.poker.model.ActionType.FOLD;

	private long _amount = 0L;

	/**
	 * Creates a {@link PlayerActionMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.PlayerActionMsg#create()
	 */
	public PlayerActionMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.PLAYER_ACTION_MSG;
	}

	@Override
	public final de.haumacher.games.poker.model.ActionType getActionType() {
		return _actionType;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerActionMsg setActionType(de.haumacher.games.poker.model.ActionType value) {
		internalSetActionType(value);
		return this;
	}

	/** Internal setter for {@link #getActionType()} without chain call utility. */
	protected final void internalSetActionType(de.haumacher.games.poker.model.ActionType value) {
		if (value == null) throw new IllegalArgumentException("Property 'actionType' cannot be null.");
		_listener.beforeSet(this, ACTION_TYPE__PROP, value);
		_actionType = value;
		_listener.afterChanged(this, ACTION_TYPE__PROP);
	}

	@Override
	public final long getAmount() {
		return _amount;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerActionMsg setAmount(long value) {
		internalSetAmount(value);
		return this;
	}

	/** Internal setter for {@link #getAmount()} without chain call utility. */
	protected final void internalSetAmount(long value) {
		_listener.beforeSet(this, AMOUNT__PROP, value);
		_amount = value;
		_listener.afterChanged(this, AMOUNT__PROP);
	}

	@Override
	public String jsonType() {
		return PLAYER_ACTION_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			ACTION_TYPE__PROP, 
			AMOUNT__PROP);
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
			case ACTION_TYPE__PROP: return getActionType();
			case AMOUNT__PROP: return getAmount();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case ACTION_TYPE__PROP: internalSetActionType((de.haumacher.games.poker.model.ActionType) value); break;
			case AMOUNT__PROP: internalSetAmount((long) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(ACTION_TYPE__PROP);
		getActionType().writeTo(out);
		out.name(AMOUNT__PROP);
		out.value(getAmount());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case ACTION_TYPE__PROP: setActionType(de.haumacher.games.poker.model.ActionType.readActionType(in)); break;
			case AMOUNT__PROP: setAmount(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return PLAYER_ACTION_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(ACTION_TYPE__ID);
		getActionType().writeTo(out);
		out.name(AMOUNT__ID);
		out.value(getAmount());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.PlayerActionMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.PlayerActionMsg readPlayerActionMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl result = new PlayerActionMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case ACTION_TYPE__ID: setActionType(de.haumacher.games.poker.model.ActionType.readActionType(in)); break;
			case AMOUNT__ID: setAmount(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.PlayerActionMsg} type. */
	public static final String PLAYER_ACTION_MSG__XML_ELEMENT = "player-action-msg";

	/** XML attribute or element name of a {@link #getActionType} property. */
	private static final String ACTION_TYPE__XML_ATTR = "action-type";

	/** XML attribute or element name of a {@link #getAmount} property. */
	private static final String AMOUNT__XML_ATTR = "amount";

	@Override
	public String getXmlTagName() {
		return PLAYER_ACTION_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(ACTION_TYPE__XML_ATTR, getActionType().protocolName());
		out.writeAttribute(AMOUNT__XML_ATTR, Long.toString(getAmount()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.PlayerActionMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerActionMsg_Impl readPlayerActionMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		PlayerActionMsg_Impl result = new PlayerActionMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case ACTION_TYPE__XML_ATTR: {
				setActionType(de.haumacher.games.poker.model.ActionType.valueOfProtocol(value));
				break;
			}
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(value));
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
			case ACTION_TYPE__XML_ATTR: {
				setActionType(de.haumacher.games.poker.model.ActionType.valueOfProtocol(in.getElementText()));
				break;
			}
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(in.getElementText()));
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
