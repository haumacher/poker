package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.GameStateMsg}.
 */
public class GameStateMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.GameStateMsg {

	private String _tableId = "";

	private int _handNumber = 0;

	private de.haumacher.games.poker.model.Phase _phase = de.haumacher.games.poker.model.Phase.WAITING_FOR_PLAYERS;

	private final java.util.List<de.haumacher.games.poker.model.Card> _communityCards = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.Card>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.Card element) {
			_listener.beforeAdd(GameStateMsg_Impl.this, COMMUNITY_CARDS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.Card element) {
			_listener.afterRemove(GameStateMsg_Impl.this, COMMUNITY_CARDS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(GameStateMsg_Impl.this, COMMUNITY_CARDS__PROP);
		}
	};

	private long _pot = 0L;

	private final java.util.List<de.haumacher.games.poker.model.SidePot> _sidePots = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.SidePot>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.SidePot element) {
			_listener.beforeAdd(GameStateMsg_Impl.this, SIDE_POTS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.SidePot element) {
			_listener.afterRemove(GameStateMsg_Impl.this, SIDE_POTS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(GameStateMsg_Impl.this, SIDE_POTS__PROP);
		}
	};

	private int _currentPlayerSeat = 0;

	private int _dealerSeat = 0;

	private final java.util.List<de.haumacher.games.poker.model.PlayerState> _players = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.PlayerState>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.PlayerState element) {
			_listener.beforeAdd(GameStateMsg_Impl.this, PLAYERS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.PlayerState element) {
			_listener.afterRemove(GameStateMsg_Impl.this, PLAYERS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(GameStateMsg_Impl.this, PLAYERS__PROP);
		}
	};

	private int _turnTimeRemaining = 0;

	private long _minRaise = 0L;

	/**
	 * Creates a {@link GameStateMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.GameStateMsg#create()
	 */
	public GameStateMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.GAME_STATE_MSG;
	}

	@Override
	public final String getTableId() {
		return _tableId;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setTableId(String value) {
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
	public final int getHandNumber() {
		return _handNumber;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setHandNumber(int value) {
		internalSetHandNumber(value);
		return this;
	}

	/** Internal setter for {@link #getHandNumber()} without chain call utility. */
	protected final void internalSetHandNumber(int value) {
		_listener.beforeSet(this, HAND_NUMBER__PROP, value);
		_handNumber = value;
		_listener.afterChanged(this, HAND_NUMBER__PROP);
	}

	@Override
	public final de.haumacher.games.poker.model.Phase getPhase() {
		return _phase;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setPhase(de.haumacher.games.poker.model.Phase value) {
		internalSetPhase(value);
		return this;
	}

	/** Internal setter for {@link #getPhase()} without chain call utility. */
	protected final void internalSetPhase(de.haumacher.games.poker.model.Phase value) {
		if (value == null) throw new IllegalArgumentException("Property 'phase' cannot be null.");
		_listener.beforeSet(this, PHASE__PROP, value);
		_phase = value;
		_listener.afterChanged(this, PHASE__PROP);
	}

	@Override
	public final java.util.List<de.haumacher.games.poker.model.Card> getCommunityCards() {
		return _communityCards;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setCommunityCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		internalSetCommunityCards(value);
		return this;
	}

	/** Internal setter for {@link #getCommunityCards()} without chain call utility. */
	protected final void internalSetCommunityCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		if (value == null) throw new IllegalArgumentException("Property 'communityCards' cannot be null.");
		_communityCards.clear();
		_communityCards.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg addCommunityCard(de.haumacher.games.poker.model.Card value) {
		internalAddCommunityCard(value);
		return this;
	}

	/** Implementation of {@link #addCommunityCard(de.haumacher.games.poker.model.Card)} without chain call utility. */
	protected final void internalAddCommunityCard(de.haumacher.games.poker.model.Card value) {
		_communityCards.add(value);
	}

	@Override
	public final void removeCommunityCard(de.haumacher.games.poker.model.Card value) {
		_communityCards.remove(value);
	}

	@Override
	public final long getPot() {
		return _pot;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setPot(long value) {
		internalSetPot(value);
		return this;
	}

	/** Internal setter for {@link #getPot()} without chain call utility. */
	protected final void internalSetPot(long value) {
		_listener.beforeSet(this, POT__PROP, value);
		_pot = value;
		_listener.afterChanged(this, POT__PROP);
	}

	@Override
	public final java.util.List<de.haumacher.games.poker.model.SidePot> getSidePots() {
		return _sidePots;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setSidePots(java.util.List<? extends de.haumacher.games.poker.model.SidePot> value) {
		internalSetSidePots(value);
		return this;
	}

	/** Internal setter for {@link #getSidePots()} without chain call utility. */
	protected final void internalSetSidePots(java.util.List<? extends de.haumacher.games.poker.model.SidePot> value) {
		if (value == null) throw new IllegalArgumentException("Property 'sidePots' cannot be null.");
		_sidePots.clear();
		_sidePots.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg addSidePot(de.haumacher.games.poker.model.SidePot value) {
		internalAddSidePot(value);
		return this;
	}

	/** Implementation of {@link #addSidePot(de.haumacher.games.poker.model.SidePot)} without chain call utility. */
	protected final void internalAddSidePot(de.haumacher.games.poker.model.SidePot value) {
		_sidePots.add(value);
	}

	@Override
	public final void removeSidePot(de.haumacher.games.poker.model.SidePot value) {
		_sidePots.remove(value);
	}

	@Override
	public final int getCurrentPlayerSeat() {
		return _currentPlayerSeat;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setCurrentPlayerSeat(int value) {
		internalSetCurrentPlayerSeat(value);
		return this;
	}

	/** Internal setter for {@link #getCurrentPlayerSeat()} without chain call utility. */
	protected final void internalSetCurrentPlayerSeat(int value) {
		_listener.beforeSet(this, CURRENT_PLAYER_SEAT__PROP, value);
		_currentPlayerSeat = value;
		_listener.afterChanged(this, CURRENT_PLAYER_SEAT__PROP);
	}

	@Override
	public final int getDealerSeat() {
		return _dealerSeat;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setDealerSeat(int value) {
		internalSetDealerSeat(value);
		return this;
	}

	/** Internal setter for {@link #getDealerSeat()} without chain call utility. */
	protected final void internalSetDealerSeat(int value) {
		_listener.beforeSet(this, DEALER_SEAT__PROP, value);
		_dealerSeat = value;
		_listener.afterChanged(this, DEALER_SEAT__PROP);
	}

	@Override
	public final java.util.List<de.haumacher.games.poker.model.PlayerState> getPlayers() {
		return _players;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setPlayers(java.util.List<? extends de.haumacher.games.poker.model.PlayerState> value) {
		internalSetPlayers(value);
		return this;
	}

	/** Internal setter for {@link #getPlayers()} without chain call utility. */
	protected final void internalSetPlayers(java.util.List<? extends de.haumacher.games.poker.model.PlayerState> value) {
		if (value == null) throw new IllegalArgumentException("Property 'players' cannot be null.");
		_players.clear();
		_players.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg addPlayer(de.haumacher.games.poker.model.PlayerState value) {
		internalAddPlayer(value);
		return this;
	}

	/** Implementation of {@link #addPlayer(de.haumacher.games.poker.model.PlayerState)} without chain call utility. */
	protected final void internalAddPlayer(de.haumacher.games.poker.model.PlayerState value) {
		_players.add(value);
	}

	@Override
	public final void removePlayer(de.haumacher.games.poker.model.PlayerState value) {
		_players.remove(value);
	}

	@Override
	public final int getTurnTimeRemaining() {
		return _turnTimeRemaining;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setTurnTimeRemaining(int value) {
		internalSetTurnTimeRemaining(value);
		return this;
	}

	/** Internal setter for {@link #getTurnTimeRemaining()} without chain call utility. */
	protected final void internalSetTurnTimeRemaining(int value) {
		_listener.beforeSet(this, TURN_TIME_REMAINING__PROP, value);
		_turnTimeRemaining = value;
		_listener.afterChanged(this, TURN_TIME_REMAINING__PROP);
	}

	@Override
	public final long getMinRaise() {
		return _minRaise;
	}

	@Override
	public de.haumacher.games.poker.model.GameStateMsg setMinRaise(long value) {
		internalSetMinRaise(value);
		return this;
	}

	/** Internal setter for {@link #getMinRaise()} without chain call utility. */
	protected final void internalSetMinRaise(long value) {
		_listener.beforeSet(this, MIN_RAISE__PROP, value);
		_minRaise = value;
		_listener.afterChanged(this, MIN_RAISE__PROP);
	}

	@Override
	public String jsonType() {
		return GAME_STATE_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			TABLE_ID__PROP, 
			HAND_NUMBER__PROP, 
			PHASE__PROP, 
			COMMUNITY_CARDS__PROP, 
			POT__PROP, 
			SIDE_POTS__PROP, 
			CURRENT_PLAYER_SEAT__PROP, 
			DEALER_SEAT__PROP, 
			PLAYERS__PROP, 
			TURN_TIME_REMAINING__PROP, 
			MIN_RAISE__PROP);
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
			case HAND_NUMBER__PROP: return getHandNumber();
			case PHASE__PROP: return getPhase();
			case COMMUNITY_CARDS__PROP: return getCommunityCards();
			case POT__PROP: return getPot();
			case SIDE_POTS__PROP: return getSidePots();
			case CURRENT_PLAYER_SEAT__PROP: return getCurrentPlayerSeat();
			case DEALER_SEAT__PROP: return getDealerSeat();
			case PLAYERS__PROP: return getPlayers();
			case TURN_TIME_REMAINING__PROP: return getTurnTimeRemaining();
			case MIN_RAISE__PROP: return getMinRaise();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case TABLE_ID__PROP: internalSetTableId((String) value); break;
			case HAND_NUMBER__PROP: internalSetHandNumber((int) value); break;
			case PHASE__PROP: internalSetPhase((de.haumacher.games.poker.model.Phase) value); break;
			case COMMUNITY_CARDS__PROP: internalSetCommunityCards(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.Card.class, value)); break;
			case POT__PROP: internalSetPot((long) value); break;
			case SIDE_POTS__PROP: internalSetSidePots(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.SidePot.class, value)); break;
			case CURRENT_PLAYER_SEAT__PROP: internalSetCurrentPlayerSeat((int) value); break;
			case DEALER_SEAT__PROP: internalSetDealerSeat((int) value); break;
			case PLAYERS__PROP: internalSetPlayers(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.PlayerState.class, value)); break;
			case TURN_TIME_REMAINING__PROP: internalSetTurnTimeRemaining((int) value); break;
			case MIN_RAISE__PROP: internalSetMinRaise((long) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__PROP);
		out.value(getTableId());
		out.name(HAND_NUMBER__PROP);
		out.value(getHandNumber());
		out.name(PHASE__PROP);
		getPhase().writeTo(out);
		out.name(COMMUNITY_CARDS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.Card x : getCommunityCards()) {
			x.writeTo(out);
		}
		out.endArray();
		out.name(POT__PROP);
		out.value(getPot());
		out.name(SIDE_POTS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.SidePot x : getSidePots()) {
			x.writeTo(out);
		}
		out.endArray();
		out.name(CURRENT_PLAYER_SEAT__PROP);
		out.value(getCurrentPlayerSeat());
		out.name(DEALER_SEAT__PROP);
		out.value(getDealerSeat());
		out.name(PLAYERS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.PlayerState x : getPlayers()) {
			x.writeTo(out);
		}
		out.endArray();
		out.name(TURN_TIME_REMAINING__PROP);
		out.value(getTurnTimeRemaining());
		out.name(MIN_RAISE__PROP);
		out.value(getMinRaise());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case TABLE_ID__PROP: setTableId(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case HAND_NUMBER__PROP: setHandNumber(in.nextInt()); break;
			case PHASE__PROP: setPhase(de.haumacher.games.poker.model.Phase.readPhase(in)); break;
			case COMMUNITY_CARDS__PROP: {
				java.util.List<de.haumacher.games.poker.model.Card> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
				setCommunityCards(newValue);
			}
			break;
			case POT__PROP: setPot(in.nextLong()); break;
			case SIDE_POTS__PROP: {
				java.util.List<de.haumacher.games.poker.model.SidePot> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.SidePot.readSidePot(in));
				}
				in.endArray();
				setSidePots(newValue);
			}
			break;
			case CURRENT_PLAYER_SEAT__PROP: setCurrentPlayerSeat(in.nextInt()); break;
			case DEALER_SEAT__PROP: setDealerSeat(in.nextInt()); break;
			case PLAYERS__PROP: {
				java.util.List<de.haumacher.games.poker.model.PlayerState> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.PlayerState.readPlayerState(in));
				}
				in.endArray();
				setPlayers(newValue);
			}
			break;
			case TURN_TIME_REMAINING__PROP: setTurnTimeRemaining(in.nextInt()); break;
			case MIN_RAISE__PROP: setMinRaise(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return GAME_STATE_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(TABLE_ID__ID);
		out.value(getTableId());
		out.name(HAND_NUMBER__ID);
		out.value(getHandNumber());
		out.name(PHASE__ID);
		getPhase().writeTo(out);
		out.name(COMMUNITY_CARDS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.Card> values = getCommunityCards();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.Card x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
		out.name(POT__ID);
		out.value(getPot());
		out.name(SIDE_POTS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.SidePot> values = getSidePots();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.SidePot x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
		out.name(CURRENT_PLAYER_SEAT__ID);
		out.value(getCurrentPlayerSeat());
		out.name(DEALER_SEAT__ID);
		out.value(getDealerSeat());
		out.name(PLAYERS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.PlayerState> values = getPlayers();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.PlayerState x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
		out.name(TURN_TIME_REMAINING__ID);
		out.value(getTurnTimeRemaining());
		out.name(MIN_RAISE__ID);
		out.value(getMinRaise());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.GameStateMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.GameStateMsg readGameStateMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.GameStateMsg_Impl result = new GameStateMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case TABLE_ID__ID: setTableId(in.nextString()); break;
			case HAND_NUMBER__ID: setHandNumber(in.nextInt()); break;
			case PHASE__ID: setPhase(de.haumacher.games.poker.model.Phase.readPhase(in)); break;
			case COMMUNITY_CARDS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addCommunityCard(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
			}
			break;
			case POT__ID: setPot(in.nextLong()); break;
			case SIDE_POTS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addSidePot(de.haumacher.games.poker.model.SidePot.readSidePot(in));
				}
				in.endArray();
			}
			break;
			case CURRENT_PLAYER_SEAT__ID: setCurrentPlayerSeat(in.nextInt()); break;
			case DEALER_SEAT__ID: setDealerSeat(in.nextInt()); break;
			case PLAYERS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addPlayer(de.haumacher.games.poker.model.PlayerState.readPlayerState(in));
				}
				in.endArray();
			}
			break;
			case TURN_TIME_REMAINING__ID: setTurnTimeRemaining(in.nextInt()); break;
			case MIN_RAISE__ID: setMinRaise(in.nextLong()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.GameStateMsg} type. */
	public static final String GAME_STATE_MSG__XML_ELEMENT = "game-state-msg";

	/** XML attribute or element name of a {@link #getTableId} property. */
	private static final String TABLE_ID__XML_ATTR = "table-id";

	/** XML attribute or element name of a {@link #getHandNumber} property. */
	private static final String HAND_NUMBER__XML_ATTR = "hand-number";

	/** XML attribute or element name of a {@link #getPhase} property. */
	private static final String PHASE__XML_ATTR = "phase";

	/** XML attribute or element name of a {@link #getCommunityCards} property. */
	private static final String COMMUNITY_CARDS__XML_ATTR = "community-cards";

	/** XML attribute or element name of a {@link #getPot} property. */
	private static final String POT__XML_ATTR = "pot";

	/** XML attribute or element name of a {@link #getSidePots} property. */
	private static final String SIDE_POTS__XML_ATTR = "side-pots";

	/** XML attribute or element name of a {@link #getCurrentPlayerSeat} property. */
	private static final String CURRENT_PLAYER_SEAT__XML_ATTR = "current-player-seat";

	/** XML attribute or element name of a {@link #getDealerSeat} property. */
	private static final String DEALER_SEAT__XML_ATTR = "dealer-seat";

	/** XML attribute or element name of a {@link #getPlayers} property. */
	private static final String PLAYERS__XML_ATTR = "players";

	/** XML attribute or element name of a {@link #getTurnTimeRemaining} property. */
	private static final String TURN_TIME_REMAINING__XML_ATTR = "turn-time-remaining";

	/** XML attribute or element name of a {@link #getMinRaise} property. */
	private static final String MIN_RAISE__XML_ATTR = "min-raise";

	@Override
	public String getXmlTagName() {
		return GAME_STATE_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(TABLE_ID__XML_ATTR, getTableId());
		out.writeAttribute(HAND_NUMBER__XML_ATTR, Integer.toString(getHandNumber()));
		out.writeAttribute(PHASE__XML_ATTR, getPhase().protocolName());
		out.writeAttribute(POT__XML_ATTR, Long.toString(getPot()));
		out.writeAttribute(CURRENT_PLAYER_SEAT__XML_ATTR, Integer.toString(getCurrentPlayerSeat()));
		out.writeAttribute(DEALER_SEAT__XML_ATTR, Integer.toString(getDealerSeat()));
		out.writeAttribute(TURN_TIME_REMAINING__XML_ATTR, Integer.toString(getTurnTimeRemaining()));
		out.writeAttribute(MIN_RAISE__XML_ATTR, Long.toString(getMinRaise()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		out.writeStartElement(COMMUNITY_CARDS__XML_ATTR);
		for (de.haumacher.games.poker.model.Card element : getCommunityCards()) {
			element.writeTo(out);
		}
		out.writeEndElement();
		out.writeStartElement(SIDE_POTS__XML_ATTR);
		for (de.haumacher.games.poker.model.SidePot element : getSidePots()) {
			element.writeTo(out);
		}
		out.writeEndElement();
		out.writeStartElement(PLAYERS__XML_ATTR);
		for (de.haumacher.games.poker.model.PlayerState element : getPlayers()) {
			element.writeTo(out);
		}
		out.writeEndElement();
	}

	/** Creates a new {@link de.haumacher.games.poker.model.GameStateMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static GameStateMsg_Impl readGameStateMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		GameStateMsg_Impl result = new GameStateMsg_Impl();
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
			case HAND_NUMBER__XML_ATTR: {
				setHandNumber(Integer.parseInt(value));
				break;
			}
			case PHASE__XML_ATTR: {
				setPhase(de.haumacher.games.poker.model.Phase.valueOfProtocol(value));
				break;
			}
			case POT__XML_ATTR: {
				setPot(Long.parseLong(value));
				break;
			}
			case CURRENT_PLAYER_SEAT__XML_ATTR: {
				setCurrentPlayerSeat(Integer.parseInt(value));
				break;
			}
			case DEALER_SEAT__XML_ATTR: {
				setDealerSeat(Integer.parseInt(value));
				break;
			}
			case TURN_TIME_REMAINING__XML_ATTR: {
				setTurnTimeRemaining(Integer.parseInt(value));
				break;
			}
			case MIN_RAISE__XML_ATTR: {
				setMinRaise(Long.parseLong(value));
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
			case HAND_NUMBER__XML_ATTR: {
				setHandNumber(Integer.parseInt(in.getElementText()));
				break;
			}
			case PHASE__XML_ATTR: {
				setPhase(de.haumacher.games.poker.model.Phase.valueOfProtocol(in.getElementText()));
				break;
			}
			case COMMUNITY_CARDS__XML_ATTR: {
				internalReadCommunityCardsListXml(in);
				break;
			}
			case POT__XML_ATTR: {
				setPot(Long.parseLong(in.getElementText()));
				break;
			}
			case SIDE_POTS__XML_ATTR: {
				internalReadSidePotsListXml(in);
				break;
			}
			case CURRENT_PLAYER_SEAT__XML_ATTR: {
				setCurrentPlayerSeat(Integer.parseInt(in.getElementText()));
				break;
			}
			case DEALER_SEAT__XML_ATTR: {
				setDealerSeat(Integer.parseInt(in.getElementText()));
				break;
			}
			case PLAYERS__XML_ATTR: {
				internalReadPlayersListXml(in);
				break;
			}
			case TURN_TIME_REMAINING__XML_ATTR: {
				setTurnTimeRemaining(Integer.parseInt(in.getElementText()));
				break;
			}
			case MIN_RAISE__XML_ATTR: {
				setMinRaise(Long.parseLong(in.getElementText()));
				break;
			}
			default: {
				super.readFieldXmlElement(in, localName);
			}
		}
	}

	private void internalReadCommunityCardsListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addCommunityCard(de.haumacher.games.poker.model.impl.Card_Impl.readCard_XmlContent(in));
		}
	}

	private void internalReadSidePotsListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addSidePot(de.haumacher.games.poker.model.impl.SidePot_Impl.readSidePot_XmlContent(in));
		}
	}

	private void internalReadPlayersListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addPlayer(de.haumacher.games.poker.model.impl.PlayerState_Impl.readPlayerState_XmlContent(in));
		}
	}

	@Override
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ServerMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
