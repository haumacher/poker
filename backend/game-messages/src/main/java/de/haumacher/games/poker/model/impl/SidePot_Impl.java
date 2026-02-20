package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.SidePot}.
 */
public class SidePot_Impl extends de.haumacher.msgbuf.data.AbstractDataObject implements de.haumacher.games.poker.model.SidePot {

	private long _amount = 0L;

	private final java.util.List<Integer> _eligibleSeats = new de.haumacher.msgbuf.util.ReferenceList<Integer>() {
		@Override
		protected void beforeAdd(int index, Integer element) {
			_listener.beforeAdd(SidePot_Impl.this, ELIGIBLE_SEATS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, Integer element) {
			_listener.afterRemove(SidePot_Impl.this, ELIGIBLE_SEATS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(SidePot_Impl.this, ELIGIBLE_SEATS__PROP);
		}
	};

	/**
	 * Creates a {@link SidePot_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.SidePot#create()
	 */
	public SidePot_Impl() {
		super();
	}

	@Override
	public final long getAmount() {
		return _amount;
	}

	@Override
	public de.haumacher.games.poker.model.SidePot setAmount(long value) {
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
	public final java.util.List<Integer> getEligibleSeats() {
		return _eligibleSeats;
	}

	@Override
	public de.haumacher.games.poker.model.SidePot setEligibleSeats(java.util.List<? extends Integer> value) {
		internalSetEligibleSeats(value);
		return this;
	}

	/** Internal setter for {@link #getEligibleSeats()} without chain call utility. */
	protected final void internalSetEligibleSeats(java.util.List<? extends Integer> value) {
		_eligibleSeats.clear();
		_eligibleSeats.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.SidePot addEligibleSeat(int value) {
		internalAddEligibleSeat(value);
		return this;
	}

	/** Implementation of {@link #addEligibleSeat(int)} without chain call utility. */
	protected final void internalAddEligibleSeat(int value) {
		_eligibleSeats.add(value);
	}

	@Override
	public final void removeEligibleSeat(int value) {
		_eligibleSeats.remove(value);
	}

	protected de.haumacher.msgbuf.observer.Listener _listener = de.haumacher.msgbuf.observer.Listener.NONE;

	@Override
	public de.haumacher.games.poker.model.SidePot registerListener(de.haumacher.msgbuf.observer.Listener l) {
		internalRegisterListener(l);
		return this;
	}

	protected final void internalRegisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.register(_listener, l);
	}

	@Override
	public de.haumacher.games.poker.model.SidePot unregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		internalUnregisterListener(l);
		return this;
	}

	protected final void internalUnregisterListener(de.haumacher.msgbuf.observer.Listener l) {
		_listener = de.haumacher.msgbuf.observer.Listener.unregister(_listener, l);
	}

	@Override
	public String jsonType() {
		return SIDE_POT__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			AMOUNT__PROP, 
			ELIGIBLE_SEATS__PROP);
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
			case AMOUNT__PROP: return getAmount();
			case ELIGIBLE_SEATS__PROP: return getEligibleSeats();
			default: return de.haumacher.games.poker.model.SidePot.super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case AMOUNT__PROP: internalSetAmount((long) value); break;
			case ELIGIBLE_SEATS__PROP: internalSetEligibleSeats(de.haumacher.msgbuf.util.Conversions.asList(Integer.class, value)); break;
		}
	}

	@Override
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		writeContent(out);
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(AMOUNT__PROP);
		out.value(getAmount());
		out.name(ELIGIBLE_SEATS__PROP);
		out.beginArray();
		for (int x : getEligibleSeats()) {
			out.value(x);
		}
		out.endArray();
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case AMOUNT__PROP: setAmount(in.nextLong()); break;
			case ELIGIBLE_SEATS__PROP: {
				java.util.List<Integer> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(in.nextInt());
				}
				in.endArray();
				setEligibleSeats(newValue);
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
		out.name(AMOUNT__ID);
		out.value(getAmount());
		out.name(ELIGIBLE_SEATS__ID);
		{
			java.util.List<Integer> values = getEligibleSeats();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.INT, values.size());
			for (int x : values) {
				out.value(x);
			}
			out.endArray();
		}
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.SidePot} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.SidePot readSidePot_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.SidePot_Impl result = new SidePot_Impl();
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
			case AMOUNT__ID: setAmount(in.nextLong()); break;
			case ELIGIBLE_SEATS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addEligibleSeat(in.nextInt());
				}
				in.endArray();
			}
			break;
			default: in.skipValue(); 
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.SidePot} type. */
	public static final String SIDE_POT__XML_ELEMENT = "side-pot";

	/** XML attribute or element name of a {@link #getAmount} property. */
	private static final String AMOUNT__XML_ATTR = "amount";

	/** XML attribute or element name of a {@link #getEligibleSeats} property. */
	private static final String ELIGIBLE_SEATS__XML_ATTR = "eligible-seats";

	@Override
	public String getXmlTagName() {
		return SIDE_POT__XML_ELEMENT;
	}

	@Override
	public final void writeContent(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		writeAttributes(out);
		writeElements(out);
	}

	/** Serializes all fields that are written as XML attributes. */
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		out.writeAttribute(AMOUNT__XML_ATTR, Long.toString(getAmount()));
		out.writeAttribute(ELIGIBLE_SEATS__XML_ATTR, getEligibleSeats().stream().map(x -> Integer.toString(x)).collect(java.util.stream.Collectors.joining(", ")));
	}

	/** Serializes all fields that are written as XML elements. */
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.SidePot} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static SidePot_Impl readSidePot_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		SidePot_Impl result = new SidePot_Impl();
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
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(value));
				break;
			}
			case ELIGIBLE_SEATS__XML_ATTR: {
				setEligibleSeats(java.util.Arrays.stream(value.split("\\s*,\\s*")).map(x -> Integer.parseInt(x)).collect(java.util.stream.Collectors.toList()));
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
			case AMOUNT__XML_ATTR: {
				setAmount(Long.parseLong(in.getElementText()));
				break;
			}
			case ELIGIBLE_SEATS__XML_ATTR: {
				setEligibleSeats(java.util.Arrays.stream(in.getElementText().split("\\s*,\\s*")).map(x -> Integer.parseInt(x)).collect(java.util.stream.Collectors.toList()));
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
