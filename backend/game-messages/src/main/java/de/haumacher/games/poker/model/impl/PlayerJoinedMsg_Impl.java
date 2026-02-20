package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.PlayerJoinedMsg}.
 */
public class PlayerJoinedMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.PlayerJoinedMsg {

	private de.haumacher.games.poker.model.PlayerState _playerState = null;

	/**
	 * Creates a {@link PlayerJoinedMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.PlayerJoinedMsg#create()
	 */
	public PlayerJoinedMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.PLAYER_JOINED_MSG;
	}

	@Override
	public final de.haumacher.games.poker.model.PlayerState getPlayerState() {
		return _playerState;
	}

	@Override
	public de.haumacher.games.poker.model.PlayerJoinedMsg setPlayerState(de.haumacher.games.poker.model.PlayerState value) {
		internalSetPlayerState(value);
		return this;
	}

	/** Internal setter for {@link #getPlayerState()} without chain call utility. */
	protected final void internalSetPlayerState(de.haumacher.games.poker.model.PlayerState value) {
		_listener.beforeSet(this, PLAYER_STATE__PROP, value);
		_playerState = value;
		_listener.afterChanged(this, PLAYER_STATE__PROP);
	}

	@Override
	public final boolean hasPlayerState() {
		return _playerState != null;
	}

	@Override
	public String jsonType() {
		return PLAYER_JOINED_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			PLAYER_STATE__PROP);
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
			case PLAYER_STATE__PROP: return getPlayerState();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case PLAYER_STATE__PROP: internalSetPlayerState((de.haumacher.games.poker.model.PlayerState) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		if (hasPlayerState()) {
			out.name(PLAYER_STATE__PROP);
			getPlayerState().writeTo(out);
		}
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case PLAYER_STATE__PROP: setPlayerState(de.haumacher.games.poker.model.PlayerState.readPlayerState(in)); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return PLAYER_JOINED_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		if (hasPlayerState()) {
			out.name(PLAYER_STATE__ID);
			getPlayerState().writeTo(out);
		}
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.PlayerJoinedMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.PlayerJoinedMsg readPlayerJoinedMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl result = new PlayerJoinedMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case PLAYER_STATE__ID: setPlayerState(de.haumacher.games.poker.model.PlayerState.readPlayerState(in)); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.PlayerJoinedMsg} type. */
	public static final String PLAYER_JOINED_MSG__XML_ELEMENT = "player-joined-msg";

	/** XML attribute or element name of a {@link #getPlayerState} property. */
	private static final String PLAYER_STATE__XML_ATTR = "player-state";

	@Override
	public String getXmlTagName() {
		return PLAYER_JOINED_MSG__XML_ELEMENT;
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
		if (hasPlayerState()) {
			out.writeStartElement(PLAYER_STATE__XML_ATTR);
			getPlayerState().writeContent(out);
			out.writeEndElement();
		}
	}

	/** Creates a new {@link de.haumacher.games.poker.model.PlayerJoinedMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static PlayerJoinedMsg_Impl readPlayerJoinedMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		PlayerJoinedMsg_Impl result = new PlayerJoinedMsg_Impl();
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
			case PLAYER_STATE__XML_ATTR: {
				setPlayerState(de.haumacher.games.poker.model.impl.PlayerState_Impl.readPlayerState_XmlContent(in));
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
