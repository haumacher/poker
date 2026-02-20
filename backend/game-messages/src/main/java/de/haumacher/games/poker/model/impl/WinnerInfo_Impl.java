package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.WinnerInfo}.
 */
public class WinnerInfo_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.WinnerInfo {

	private int _seat = 0;

	private long _amount = 0L;

	private String _handDescription = "";

	/**
	 * Creates a {@link WinnerInfo_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.WinnerInfo#create()
	 */
	public WinnerInfo_Impl() {
		super();
	}

	@Override
	public final int getSeat() {
		return _seat;
	}

	@Override
	public de.haumacher.games.poker.model.WinnerInfo setSeat(int value) {
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
	public final long getAmount() {
		return _amount;
	}

	@Override
	public de.haumacher.games.poker.model.WinnerInfo setAmount(long value) {
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
	public final String getHandDescription() {
		return _handDescription;
	}

	@Override
	public de.haumacher.games.poker.model.WinnerInfo setHandDescription(String value) {
		internalSetHandDescription(value);
		return this;
	}

	/** Internal setter for {@link #getHandDescription()} without chain call utility. */
	protected final void internalSetHandDescription(String value) {
		_listener.beforeSet(this, HAND_DESCRIPTION__PROP, value);
		_handDescription = value;
		_listener.afterChanged(this, HAND_DESCRIPTION__PROP);
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.WinnerInfo registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.WinnerInfo unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public String jsonType() {
		return WINNER_INFO__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			SEAT__PROP, 
			AMOUNT__PROP, 
			HAND_DESCRIPTION__PROP);
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
			case AMOUNT__PROP: return getAmount();
			case HAND_DESCRIPTION__PROP: return getHandDescription();
			default: return de.haumacher.games.poker.model.WinnerInfo.super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case SEAT__PROP: internalSetSeat((int) value); break;
			case AMOUNT__PROP: internalSetAmount((long) value); break;
			case HAND_DESCRIPTION__PROP: internalSetHandDescription((String) value); break;
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
		out.name(AMOUNT__PROP);
		out.value(getAmount());
		out.name(HAND_DESCRIPTION__PROP);
		out.value(getHandDescription());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case SEAT__PROP: setSeat(in.nextInt()); break;
			case AMOUNT__PROP: setAmount(in.nextLong()); break;
			case HAND_DESCRIPTION__PROP: setHandDescription(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
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
		out.name(AMOUNT__ID);
		out.value(getAmount());
		out.name(HAND_DESCRIPTION__ID);
		out.value(getHandDescription());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.WinnerInfo} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.WinnerInfo readWinnerInfo_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.WinnerInfo_Impl result = new WinnerInfo_Impl();
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
			case AMOUNT__ID: setAmount(in.nextLong()); break;
			case HAND_DESCRIPTION__ID: setHandDescription(in.nextString()); break;
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.WinnerInfo} type. */
	public static final String WINNER_INFO__XML_ELEMENT = "winner-info";

	/** XML attribute or element name of a {@link #getSeat} property. */
	private static final String SEAT__XML_ATTR = "seat";

	/** XML attribute or element name of a {@link #getAmount} property. */
	private static final String AMOUNT__XML_ATTR = "amount";

	/** XML attribute or element name of a {@link #getHandDescription} property. */
	private static final String HAND_DESCRIPTION__XML_ATTR = "hand-description";

	@Override
	public String getXmlTagName() {
		return WINNER_INFO__XML_ELEMENT;
	}

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeAttribute(SEAT__XML_ATTR, Integer.toString(getSeat()));
		out.writeAttribute(AMOUNT__XML_ATTR, Long.toString(getAmount()));
		out.writeAttribute(HAND_DESCRIPTION__XML_ATTR, getHandDescription());
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.WinnerInfo} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static WinnerInfo_Impl readWinnerInfo_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		WinnerInfo_Impl result = new WinnerInfo_Impl();
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
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(value));
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
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(in.getElementText()));
				break;
			}
			case HAND_DESCRIPTION__XML_ATTR: {
				setHandDescription(in.getElementText());
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
