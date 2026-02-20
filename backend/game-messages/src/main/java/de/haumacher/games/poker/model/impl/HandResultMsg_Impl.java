package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.HandResultMsg}.
 */
public class HandResultMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.HandResultMsg {

	private final java.util.List<de.haumacher.games.poker.model.WinnerInfo> _winners = new de.haumacher.msgbuf.util.ReferenceList<de.haumacher.games.poker.model.WinnerInfo>() {
		@Override
		protected void beforeAdd(int index, de.haumacher.games.poker.model.WinnerInfo element) {
			_listener.beforeAdd(HandResultMsg_Impl.this, WINNERS__PROP, index, element);
		}

		@Override
		protected void afterRemove(int index, de.haumacher.games.poker.model.WinnerInfo element) {
			_listener.afterRemove(HandResultMsg_Impl.this, WINNERS__PROP, index, element);
		}

		@Override
		protected void afterChanged() {
			_listener.afterChanged(HandResultMsg_Impl.this, WINNERS__PROP);
		}
	};

	/**
	 * Creates a {@link HandResultMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.HandResultMsg#create()
	 */
	public HandResultMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.HAND_RESULT_MSG;
	}

	@Override
	public final java.util.List<de.haumacher.games.poker.model.WinnerInfo> getWinners() {
		return _winners;
	}

	@Override
	public de.haumacher.games.poker.model.HandResultMsg setWinners(java.util.List<? extends de.haumacher.games.poker.model.WinnerInfo> value) {
		internalSetWinners(value);
		return this;
	}

	/** Internal setter for {@link #getWinners()} without chain call utility. */
	protected final void internalSetWinners(java.util.List<? extends de.haumacher.games.poker.model.WinnerInfo> value) {
		if (value == null) throw new IllegalArgumentException("Property 'winners' cannot be null.");
		_winners.clear();
		_winners.addAll(value);
	}

	@Override
	public de.haumacher.games.poker.model.HandResultMsg addWinner(de.haumacher.games.poker.model.WinnerInfo value) {
		internalAddWinner(value);
		return this;
	}

	/** Implementation of {@link #addWinner(de.haumacher.games.poker.model.WinnerInfo)} without chain call utility. */
	protected final void internalAddWinner(de.haumacher.games.poker.model.WinnerInfo value) {
		_winners.add(value);
	}

	@Override
	public final void removeWinner(de.haumacher.games.poker.model.WinnerInfo value) {
		_winners.remove(value);
	}

	@Override
	public String jsonType() {
		return HAND_RESULT_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			WINNERS__PROP);
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
			case WINNERS__PROP: return getWinners();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case WINNERS__PROP: internalSetWinners(de.haumacher.msgbuf.util.Conversions.asList(de.haumacher.games.poker.model.WinnerInfo.class, value)); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(WINNERS__PROP);
		out.beginArray();
		for (de.haumacher.games.poker.model.WinnerInfo x : getWinners()) {
			x.writeTo(out);
		}
		out.endArray();
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case WINNERS__PROP: {
				java.util.List<de.haumacher.games.poker.model.WinnerInfo> newValue = new java.util.ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					newValue.add(de.haumacher.games.poker.model.WinnerInfo.readWinnerInfo(in));
				}
				in.endArray();
				setWinners(newValue);
			}
			break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return HAND_RESULT_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(WINNERS__ID);
		{
			java.util.List<de.haumacher.games.poker.model.WinnerInfo> values = getWinners();
			out.beginArray(de.haumacher.msgbuf.binary.DataType.OBJECT, values.size());
			for (de.haumacher.games.poker.model.WinnerInfo x : values) {
				x.writeTo(out);
			}
			out.endArray();
		}
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.HandResultMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.HandResultMsg readHandResultMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.HandResultMsg_Impl result = new HandResultMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case WINNERS__ID: {
				in.beginArray();
				while (in.hasNext()) {
					addWinner(de.haumacher.games.poker.model.WinnerInfo.readWinnerInfo(in));
				}
				in.endArray();
			}
			break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.HandResultMsg} type. */
	public static final String HAND_RESULT_MSG__XML_ELEMENT = "hand-result-msg";

	/** XML attribute or element name of a {@link #getWinners} property. */
	private static final String WINNERS__XML_ATTR = "winners";

	@Override
	public String getXmlTagName() {
		return HAND_RESULT_MSG__XML_ELEMENT;
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
		out.writeStartElement(WINNERS__XML_ATTR);
		for (de.haumacher.games.poker.model.WinnerInfo element : getWinners()) {
			element.writeTo(out);
		}
		out.writeEndElement();
	}

	/** Creates a new {@link de.haumacher.games.poker.model.HandResultMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static HandResultMsg_Impl readHandResultMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		HandResultMsg_Impl result = new HandResultMsg_Impl();
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
			case WINNERS__XML_ATTR: {
				internalReadWinnersListXml(in);
				break;
			}
			default: {
				super.readFieldXmlElement(in, localName);
			}
		}
	}

	private void internalReadWinnersListXml(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		while (true) {
			int event = in.nextTag();
			if (event == javax.xml.stream.XMLStreamConstants.END_ELEMENT) {
				break;
			}

			addWinner(de.haumacher.games.poker.model.impl.WinnerInfo_Impl.readWinnerInfo_XmlContent(in));
		}
	}

	@Override
	public <R,A,E extends Throwable> R visit(de.haumacher.games.poker.model.ServerMessage.Visitor<R,A,E> v, A arg) throws E {
		return v.visit(this, arg);
	}

}
