package de.haumacher.games.poker.model;

/**
 * Card suit. All four suits are equal in Texas Hold'em (no suit ranking).
 */
public enum Suit implements de.haumacher.msgbuf.data.ProtocolEnum {

	HEARTS("HEARTS"),

	DIAMONDS("DIAMONDS"),

	CLUBS("CLUBS"),

	SPADES("SPADES"),

	;

	private final String _protocolName;

	private Suit(String protocolName) {
		_protocolName = protocolName;
	}

	/**
	 * The protocol name of a {@link Suit} constant.
	 *
	 * @see #valueOfProtocol(String)
	 */
	@Override
	public String protocolName() {
		return _protocolName;
	}

	/** Looks up a {@link Suit} constant by it's protocol name. */
	public static Suit valueOfProtocol(String protocolName) {
		if (protocolName == null) { return null; }
		switch (protocolName) {
			case "HEARTS": return HEARTS;
			case "DIAMONDS": return DIAMONDS;
			case "CLUBS": return CLUBS;
			case "SPADES": return SPADES;
		}
		return HEARTS;
	}

	/** Writes this instance to the given output. */
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.value(protocolName());
	}

	/** Reads a new instance from the given reader. */
	public static Suit readSuit(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		return valueOfProtocol(in.nextString());
	}

	/** Writes this instance to the given binary output. */
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		switch (this) {
			case HEARTS: out.value(4); break;
			case DIAMONDS: out.value(1); break;
			case CLUBS: out.value(2); break;
			case SPADES: out.value(3); break;
			default: out.value(0);
		}
	}

	/** Reads a new instance from the given binary reader. */
	public static Suit readSuit(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		switch (in.nextInt()) {
			case 4: return HEARTS;
			case 1: return DIAMONDS;
			case 2: return CLUBS;
			case 3: return SPADES;
			default: return HEARTS;
		}
	}
}
