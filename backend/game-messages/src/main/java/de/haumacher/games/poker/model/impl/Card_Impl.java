package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.Card}.
 */
public class Card_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.Card {

	private de.haumacher.games.poker.model.Rank _rank = de.haumacher.games.poker.model.Rank.TWO;

	private de.haumacher.games.poker.model.Suit _suit = de.haumacher.games.poker.model.Suit.HEARTS;

	/**
	 * Creates a {@link Card_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.Card#create()
	 */
	public Card_Impl() {
		super();
	}

	@Override
	public final de.haumacher.games.poker.model.Rank getRank() {
		return _rank;
	}

	@Override
	public de.haumacher.games.poker.model.Card setRank(de.haumacher.games.poker.model.Rank value) {
		internalSetRank(value);
		return this;
	}

	/** Internal setter for {@link #getRank()} without chain call utility. */
	protected final void internalSetRank(de.haumacher.games.poker.model.Rank value) {
		if (value == null) throw new IllegalArgumentException("Property 'rank' cannot be null.");
		_listener.beforeSet(this, RANK__PROP, value);
		_rank = value;
		_listener.afterChanged(this, RANK__PROP);
	}

	@Override
	public final de.haumacher.games.poker.model.Suit getSuit() {
		return _suit;
	}

	@Override
	public de.haumacher.games.poker.model.Card setSuit(de.haumacher.games.poker.model.Suit value) {
		internalSetSuit(value);
		return this;
	}

	/** Internal setter for {@link #getSuit()} without chain call utility. */
	protected final void internalSetSuit(de.haumacher.games.poker.model.Suit value) {
		if (value == null) throw new IllegalArgumentException("Property 'suit' cannot be null.");
		_listener.beforeSet(this, SUIT__PROP, value);
		_suit = value;
		_listener.afterChanged(this, SUIT__PROP);
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.Card registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.Card unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public String jsonType() {
		return CARD__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			RANK__PROP, 
			SUIT__PROP);
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
			case RANK__PROP: return getRank();
			case SUIT__PROP: return getSuit();
			default: return de.haumacher.games.poker.model.Card.super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case RANK__PROP: internalSetRank((de.haumacher.games.poker.model.Rank) value); break;
			case SUIT__PROP: internalSetSuit((de.haumacher.games.poker.model.Suit) value); break;
		}
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		writeContent(out);
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(RANK__PROP);
		getRank().writeTo(out);
		out.name(SUIT__PROP);
		getSuit().writeTo(out);
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case RANK__PROP: setRank(de.haumacher.games.poker.model.Rank.readRank(in)); break;
			case SUIT__PROP: setSuit(de.haumacher.games.poker.model.Suit.readSuit(in)); break;
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
		out.name(RANK__ID);
		getRank().writeTo(out);
		out.name(SUIT__ID);
		getSuit().writeTo(out);
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.Card} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.Card readCard_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.Card_Impl result = new Card_Impl();
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
			case RANK__ID: setRank(de.haumacher.games.poker.model.Rank.readRank(in)); break;
			case SUIT__ID: setSuit(de.haumacher.games.poker.model.Suit.readSuit(in)); break;
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.Card} type. */
	public static final String CARD__XML_ELEMENT = "card";

	/** XML attribute or element name of a {@link #getRank} property. */
	private static final String RANK__XML_ATTR = "rank";

	/** XML attribute or element name of a {@link #getSuit} property. */
	private static final String SUIT__XML_ATTR = "suit";

	@Override
	public String getXmlTagName() {
		return CARD__XML_ELEMENT;
	}

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeAttribute(RANK__XML_ATTR, getRank().protocolName());
		out.writeAttribute(SUIT__XML_ATTR, getSuit().protocolName());
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.Card} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static Card_Impl readCard_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		Card_Impl result = new Card_Impl();
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
			case RANK__XML_ATTR: {
				setRank(de.haumacher.games.poker.model.Rank.valueOfProtocol(value));
				break;
			}
			case SUIT__XML_ATTR: {
				setSuit(de.haumacher.games.poker.model.Suit.valueOfProtocol(value));
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
			case RANK__XML_ATTR: {
				setRank(de.haumacher.games.poker.model.Rank.valueOfProtocol(in.getElementText()));
				break;
			}
			case SUIT__XML_ATTR: {
				setSuit(de.haumacher.games.poker.model.Suit.valueOfProtocol(in.getElementText()));
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
