package de.haumacher.games.poker.model;

public enum ActionType implements de.haumacher.msgbuf.data.ProtocolEnum {

	FOLD("FOLD"),

	CHECK("CHECK"),

	CALL("CALL"),

	RAISE("RAISE"),

	ALL_IN("ALL_IN"),

	;

	private final String _protocolName;

	private ActionType(String protocolName) {
		_protocolName = protocolName;
	}

	/**
	 * The protocol name of a {@link ActionType} constant.
	 *
	 * @see #valueOfProtocol(String)
	 */
	@Override
	public String protocolName() {
		return _protocolName;
	}

	/** Looks up a {@link ActionType} constant by it's protocol name. */
	public static ActionType valueOfProtocol(String protocolName) {
		if (protocolName == null) { return null; }
		switch (protocolName) {
			case "FOLD": return FOLD;
			case "CHECK": return CHECK;
			case "CALL": return CALL;
			case "RAISE": return RAISE;
			case "ALL_IN": return ALL_IN;
		}
		return FOLD;
	}

	/** Writes this instance to the given output. */
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.value(protocolName());
	}

	/** Reads a new instance from the given reader. */
	public static ActionType readActionType(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		return valueOfProtocol(in.nextString());
	}

	/** Writes this instance to the given binary output. */
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		switch (this) {
			case FOLD: out.value(5); break;
			case CHECK: out.value(1); break;
			case CALL: out.value(2); break;
			case RAISE: out.value(3); break;
			case ALL_IN: out.value(4); break;
			default: out.value(0);
		}
	}

	/** Reads a new instance from the given binary reader. */
	public static ActionType readActionType(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		switch (in.nextInt()) {
			case 5: return FOLD;
			case 1: return CHECK;
			case 2: return CALL;
			case 3: return RAISE;
			case 4: return ALL_IN;
			default: return FOLD;
		}
	}
}
