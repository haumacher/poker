package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.ShowdownHand}.
 */
public class ShowdownHand_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.ShowdownHand {

	private int _seat = 0;

	private final java.util.List<de.haumacher.games.poker.model.Card> _holeCards = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.Card>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.Card element) {
			_listener.beforeAdd(ShowdownHand_Impl.this, HOLE_CARDS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.Card element) {
			_listener.afterRemove(ShowdownHand_Impl.this, HOLE_CARDS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(ShowdownHand_Impl.this, HOLE_CARDS__PROP);
		}
	};

	private String _handDescription = "";

	private final java.util.List<de.haumacher.games.poker.model.Card> _bestCards = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.Card>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.Card element) {
			_listener.beforeAdd(ShowdownHand_Impl.this, BEST_CARDS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.Card element) {
			_listener.afterRemove(ShowdownHand_Impl.this, BEST_CARDS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(ShowdownHand_Impl.this, BEST_CARDS__PROP);
		}
	};

	/**
	 * Creates a {@link ShowdownHand_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.ShowdownHand#create()
	 */
	public ShowdownHand_Impl() {
		super();
	}

	@Override
	public final int getSeat() {
		return _seat;
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand setSeat(int value) {
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
	public final java.util.List<de.haumacher.games.poker.model.Card> getHoleCards() {
		return _holeCards;
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand setHoleCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		internalSetHoleCards(value);
		return this;
	}

	/** Internal setter for {@link #getHoleCards()} without chain call utility. */
	protected final void internalSetHoleCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		if (value == null) throw new IllegalArgumentException("Property 'holeCards' cannot be null.");
		_holeCards.clear();
		_holeCards.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand addHoleCard(de.haumacher.games.poker.model.Card value) {
		internalAddHoleCard(value);
		return this;
	}

	/** Implementation of {@link #addHoleCard(de.haumacher.games.poker.model.Card)} without chain call utility. */
	protected final void internalAddHoleCard(de.haumacher.games.poker.model.Card value) {
		_holeCards.add(value);
	}

	@Override
	public final void removeHoleCard(de.haumacher.games.poker.model.Card value) {
		_holeCards.remove(value);
	}

	@Override
	public final String getHandDescription() {
		return _handDescription;
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand setHandDescription(String value) {
		internalSetHandDescription(value);
		return this;
	}

	/** Internal setter for {@link #getHandDescription()} without chain call utility. */
	protected final void internalSetHandDescription(String value) {
		_listener.beforeSet(this, HAND_DESCRIPTION__PROP, value);
		_handDescription = value;
		_listener.afterChanged(this, HAND_DESCRIPTION__PROP);
	}

	@Override
	public final java.util.List<de.haumacher.games.poker.model.Card> getBestCards() {
		return _bestCards;
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand setBestCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		internalSetBestCards(value);
		return this;
	}

	/** Internal setter for {@link #getBestCards()} without chain call utility. */
	protected final void internalSetBestCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value) {
		if (value == null) throw new IllegalArgumentException("Property 'bestCards' cannot be null.");
		_bestCards.clear();
		_bestCards.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand addBestCard(de.haumacher.games.poker.model.Card value) {
		internalAddBestCard(value);
		return this;
	}

	/** Implementation of {@link #addBestCard(de.haumacher.games.poker.model.Card)} without chain call utility. */
	protected final void internalAddBestCard(de.haumacher.games.poker.model.Card value) {
		_bestCards.add(value);
	}

	@Override
	public final void removeBestCard(de.haumacher.games.poker.model.Card value) {
		_bestCards.remove(value);
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.ShowdownHand registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.ShowdownHand unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public String jsonType() {
		return SHOWDOWN_HAND__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			SEAT__PROP, 
			HOLE_CARDS__PROP, 
			HAND_DESCRIPTION__PROP, 
			BEST_CARDS__PROP);
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
			case HOLE_CARDS__PROP: return getHoleCards();
			case HAND_DESCRIPTION__PROP: return getHandDescription();
			case BEST_CARDS__PROP: return getBestCards();
			default: return de.haumacher.games.poker.model.ShowdownHand.super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case SEAT__PROP: internalSetSeat((int) value); break;
			case HOLE_CARDS__PROP: internalSetHoleCards(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.Card.class, value)); break;
			case HAND_DESCRIPTION__PROP: internalSetHandDescription((String) value); break;
			case BEST_CARDS__PROP: internalSetBestCards(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.Card.class, value)); break;
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
		out.name(HOLE_CARDS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.Card x : getHoleCards()) {
			x.writeTo(out);
		}
		out.endArray();
		out.name(HAND_DESCRIPTION__PROP);
		out.value(getHandDescription());
		out.name(BEST_CARDS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.Card x : getBestCards()) {
			x.writeTo(out);
		}
		out.endArray();
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case SEAT__PROP: setSeat(in.nextInt()); break;
			case HOLE_CARDS__PROP: {
				java.util.List<de.haumacher.games.poker.model.Card> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
				setHoleCards(newValue);
			}
			break;
			case HAND_DESCRIPTION__PROP: setHandDescription(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case BEST_CARDS__PROP: {
				java.util.List<de.haumacher.games.poker.model.Card> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
				setBestCards(newValue);
			}
			break;
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
		out.name(HOLE_CARDS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.Card> values = getHoleCards();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.Card x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
		out.name(HAND_DESCRIPTION__ID);
		out.value(getHandDescription());
		out.name(BEST_CARDS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.Card> values = getBestCards();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.Card x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.ShowdownHand} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.ShowdownHand readShowdownHand_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ShowdownHand_Impl result = new ShowdownHand_Impl();
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
			case HOLE_CARDS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addHoleCard(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
			}
			break;
			case HAND_DESCRIPTION__ID: setHandDescription(in.nextString()); break;
			case BEST_CARDS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addBestCard(de.haumacher.games.poker.model.Card.readCard(in));
				}
				in.endArray();
			}
			break;
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.ShowdownHand} type. */
	public static final String SHOWDOWN_HAND__XML_ELEMENT = "showdown-hand";

	/** XML attribute or element name of a {@link #getSeat} property. */
	private static final String SEAT__XML_ATTR = "seat";

	/** XML attribute or element name of a {@link #getHoleCards} property. */
	private static final String HOLE_CARDS__XML_ATTR = "hole-cards";

	/** XML attribute or element name of a {@link #getHandDescription} property. */
	private static final String HAND_DESCRIPTION__XML_ATTR = "hand-description";

	/** XML attribute or element name of a {@link #getBestCards} property. */
	private static final String BEST_CARDS__XML_ATTR = "best-cards";

	@Override
	public String getXmlTagName() {
		return SHOWDOWN_HAND__XML_ELEMENT;
	}

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeAttribute(SEAT__XML_ATTR, Integer.toString(getSeat()));
		out.writeAttribute(HAND_DESCRIPTION__XML_ATTR, getHandDescription());
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeStartElement(HOLE_CARDS__XML_ATTR);
		for (de.haumacher.games.poker.model.Card element : getHoleCards()) {
			element.writeTo(out);
		}
		out.writeEndElement();
		out.writeStartElement(BEST_CARDS__XML_ATTR);
		for (de.haumacher.games.poker.model.Card element : getBestCards()) {
			element.writeTo(out);
		}
		out.writeEndElement();
	}

	/** Creates a new {@link de.haumacher.games.poker.model.ShowdownHand} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ShowdownHand_Impl readShowdownHand_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		ShowdownHand_Impl result = new ShowdownHand_Impl();
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
			case HAND_DESCRIPTION__XML_ATTR: {
				setHandDescription(value);
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
			case HOLE_CARDS__XML_ATTR: {
				internalReadHoleCardsListXml(in);
				break;
			}
			case HAND_DESCRIPTION__XML_ATTR: {
				setHandDescription(in.getElementText());
				break;
			}
			case BEST_CARDS__XML_ATTR: {
				internalReadBestCardsListXml(in);
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

	private void internalReadHoleCardsListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addHoleCard(de.haumacher.games.poker.model.impl.Card_Impl.readCard_XmlContent(in));
		}
	}

	private void internalReadBestCardsListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addBestCard(de.haumacher.games.poker.model.impl.Card_Impl.readCard_XmlContent(in));
		}
	}

}
