package de.haumacher.games.poker.model.impl;

/**
 * Implementation of {@link de.haumacher.games.poker.model.ErrorMsg}.
 */
public class ErrorMsg_Impl extends de.haumacher.games.poker.model.impl.ServerMessage_Impl implements de.haumacher.games.poker.model.ErrorMsg {

	private String _code = "";

	private String _message = "";

	/**
	 * Creates a {@link ErrorMsg_Impl} instance.
	 *
	 * @see de.haumacher.games.poker.model.ErrorMsg#create()
	 */
	public ErrorMsg_Impl() {
		super();
	}

	@Override
	public TypeKind kind() {
		return TypeKind.ERROR_MSG;
	}

	@Override
	public final String getCode() {
		return _code;
	}

	@Override
	public de.haumacher.games.poker.model.ErrorMsg setCode(String value) {
		internalSetCode(value);
		return this;
	}

	/** Internal setter for {@link #getCode()} without chain call utility. */
	protected final void internalSetCode(String value) {
		_listener.beforeSet(this, CODE__PROP, value);
		_code = value;
		_listener.afterChanged(this, CODE__PROP);
	}

	@Override
	public final String getMessage() {
		return _message;
	}

	@Override
	public de.haumacher.games.poker.model.ErrorMsg setMessage(String value) {
		internalSetMessage(value);
		return this;
	}

	/** Internal setter for {@link #getMessage()} without chain call utility. */
	protected final void internalSetMessage(String value) {
		_listener.beforeSet(this, MESSAGE__PROP, value);
		_message = value;
		_listener.afterChanged(this, MESSAGE__PROP);
	}

	@Override
	public String jsonType() {
		return ERROR_MSG__TYPE;
	}

	static final java.util.List<String> PROPERTIES;
	static {
		java.util.List<String> local = java.util.Arrays.asList(
			CODE__PROP, 
			MESSAGE__PROP);
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
			case CODE__PROP: return getCode();
			case MESSAGE__PROP: return getMessage();
			default: return super.get(field);
		}
	}

	@Override
	public void set(String field, Object value) {
		switch (field) {
			case CODE__PROP: internalSetCode((String) value); break;
			case MESSAGE__PROP: internalSetMessage((String) value); break;
			default: super.set(field, value); break;
		}
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(CODE__PROP);
		out.value(getCode());
		out.name(MESSAGE__PROP);
		out.value(getMessage());
	}

	@Override
	protected void readField(de.haumacher.msgbuf.json.JsonReader in, String field) throws java.io.IOException {
		switch (field) {
			case CODE__PROP: setCode(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			case MESSAGE__PROP: setMessage(de.haumacher.msgbuf.json.JsonUtil.nextStringOptional(in)); break;
			default: super.readField(in, field);
		}
	}

	@Override
	public int typeId() {
		return ERROR_MSG__TYPE_ID;
	}

	@Override
	protected void writeFields(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		super.writeFields(out);
		out.name(CODE__ID);
		out.value(getCode());
		out.name(MESSAGE__ID);
		out.value(getMessage());
	}

	/** Helper for creating an object of type {@link de.haumacher.games.poker.model.ErrorMsg} from a polymorphic composition. */
	public static de.haumacher.games.poker.model.ErrorMsg readErrorMsg_Content(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ErrorMsg_Impl result = new ErrorMsg_Impl();
		result.readContent(in);
		return result;
	}

	@Override
	protected void readField(de.haumacher.msgbuf.binary.DataReader in, int field) throws java.io.IOException {
		switch (field) {
			case CODE__ID: setCode(in.nextString()); break;
			case MESSAGE__ID: setMessage(in.nextString()); break;
			default: super.readField(in, field);
		}
	}

	/** XML element name representing a {@link de.haumacher.games.poker.model.ErrorMsg} type. */
	public static final String ERROR_MSG__XML_ELEMENT = "error-msg";

	/** XML attribute or element name of a {@link #getCode} property. */
	private static final String CODE__XML_ATTR = "code";

	/** XML attribute or element name of a {@link #getMessage} property. */
	private static final String MESSAGE__XML_ATTR = "message";

	@Override
	public String getXmlTagName() {
		return ERROR_MSG__XML_ELEMENT;
	}

	/** Serializes all fields that are written as XML attributes. */
	@Override
	protected void writeAttributes(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeAttributes(out);
		out.writeAttribute(CODE__XML_ATTR, getCode());
		out.writeAttribute(MESSAGE__XML_ATTR, getMessage());
	}

	/** Serializes all fields that are written as XML elements. */
	@Override
	protected void writeElements(javax.xml.stream.XMLStreamWriter out) throws javax.xml.stream.XMLStreamException {
		super.writeElements(out);
		// No element fields.
	}

	/** Creates a new {@link de.haumacher.games.poker.model.ErrorMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ErrorMsg_Impl readErrorMsg_XmlContent(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		ErrorMsg_Impl result = new ErrorMsg_Impl();
		result.readContentXml(in);
		return result;
	}

	@Override
	protected void readFieldXmlAttribute(String name, String value) {
		switch (name) {
			case CODE__XML_ATTR: {
				setCode(value);
				break;
			}
			case MESSAGE__XML_ATTR: {
				setMessage(value);
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
			case CODE__XML_ATTR: {
				setCode(in.getElementText());
				break;
			}
			case MESSAGE__XML_ATTR: {
				setMessage(in.getElementText());
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
