package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.HoleCardsMsg}.
 */
public class HoleCardsMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.HoleCardsMsg {

	private int _handNumber = 0;

	private final java.util.List<de.haumacher.games.poker.model.Card> _cards = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.Card>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.Card element) {
			_listener.beforeAdd(HoleCardsMsg_Impl.this, CARDS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.Card element) {
			_listener.afterRemove(HoleCardsMsg_Impl.this, CARDS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(HoleCardsMsg_Impl.this, CARDS__PROP);
		}
	};

	/**
	 * Creates a {@link HoleCardsMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.HoleCardsMsg#create()
	 */
	public HoleCardsMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.HOLE_CARDS_MSG;
	}

	@Override
	public final int getHandNumber() {
		return _handNumber;
	}

	@Override
	public de.haumacher.games.poker.model.HoleCardsMsg setHandNumber(int value) {
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
	public final java.util.List<de.haumacher.games.poker.model.Card> getCards() {
		return _cards;
	}

	@Override
	public de.haumacher.games.poker.model.HoleCardsMsg setCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		internalSetCards(value);
		return this;
	}

	/** Internal setter for {@link #getCards()} without chain call utility. */
	protected final void internalSetCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		if (value == null) throw new IllegalArgumentException("Property 'cards' cannot be null.");
		_cards.clear();
		_cards.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.HoleCardsMsg addCard(de.haumacher.games.poker.model.Card value) {
		internalAddCard(value);
		return this;
	}

	/** Implementation of {@link #addCard(de.haumacher.games.poker.model.Card)} without chain call utility. */
	protected final void internalAddCard(de.haumacher.games.poker.model.Card value) {
		_cards.add(value);
	}

	@Override
	public final void removeCard(de.haumacher.games.poker.model.Card value) {
		_cards.remove(value);
	}

	@Override
	public String jsonType() {
		return HOLE_CARDS_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			HAND_NUMBER__PROP, 
			CARDS__PROP);
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
			case HAND_NUMBER__PROP: return getHandNumber();
			case CARDS__PROP: return getCards();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case HAND_NUMBER__PROP: internalSetHandNumber((int) value); break;
			case CARDS__PROP: internalSetCards(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.Card.class, value)); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(HAND_NUMBER__PROP);
		out.value(getHandNumber());
		out.name(CARDS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.Card x : getCards()) {
			x.writeTo(out);
		}
		out.endArray();
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case HAND_NUMBER__PROP: setHandNumber(in.nextInt()); break;
			case CARDS__PROP: {
				java.util.List<de.haumacher.games.poker.model.Card> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
				setCards(newValue);
			}
			break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return HOLE_CARDS_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(HAND_NUMBER__ID);
		out.value(getHandNumber());
		out.name(CARDS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.Card> values = getCards();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.Card x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.HoleCardsMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.HoleCardsMsg readHoleCardsMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl result = new HoleCardsMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case HAND_NUMBER__ID: setHandNumber(in.nextInt()); break;
			case CARDS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addCard(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
			}
			break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.HoleCardsMsg} type. */
	public static final String HOLE_CARDS_MSG__XML_ELEMENT = "hole-cards-msg";

	/** XML attribute or element name of a {@link #getHandNumber} property. */
	private static final String HAND_NUMBER__XML_ATTR = "hand-number";

	/** XML attribute or element name of a {@link #getCards} property. */
	private static final String CARDS__XML_ATTR = "cards";

	@Override
	public String getXmlTagName() {
		return HOLE_CARDS_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(HAND_NUMBER__XML_ATTR, Integer.toString(getHandNumber()));
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		out.writeStartElement(CARDS__XML_ATTR);
		for (de.haumacher.games.poker.model.Card element : getCards()) {
			element.writeTo(out);
		}
		out.writeEndElement();
	}

	/** Creates a new {@link de.haumacher.games.poker.model.HoleCardsMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static HoleCardsMsg_Impl readHoleCardsMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		HoleCardsMsg_Impl result = new HoleCardsMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case HAND_NUMBER__XML_ATTR: {
				setHandNumber(Integer.parseInt(value));
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
			case HAND_NUMBER__XML_ATTR: {
				setHandNumber(Integer.parseInt(in.getElementText()));
				break;
			}
			case CARDS__XML_ATTR: {
				internalReadCardsListXml(in);
				break;
			}
			default: {
				super.readFieldXmlElement(in, localName);
			}
		}
	}

	private void internalReadCardsListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addCard(de.haumacher.games.poker.model.impl.Card_Impl.readCard_XmlContent(in));
		}
	}

	@Override
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ServerMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
