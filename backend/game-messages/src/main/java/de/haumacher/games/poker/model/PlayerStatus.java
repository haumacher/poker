package de.haumacher.games.poker.model;

/**
 * A player's current status within a hand.
 */
public enum PlayerStatus implements de.haumacher.msgbuf.data.ProtocolEnum {

	/**
	 * Seated but not yet participating in a hand.
	 */
	WAITING("WAITING"),

	/**
	 * In the current hand and able to act.
	 */
	ACTIVE("ACTIVE"),

	/**
	 * Folded this hand; still seated but out until the next hand.
	 */
	FOLDED("FOLDED"),

	/**
	 * All chips committed; still in the hand but cannot act further.
	 */
	ALL_IN("ALL_IN"),

	/**
	 * Voluntarily sitting out; skipped for dealing and blinds.
	 */
	SITTING_OUT("SITTING_OUT"),

	;

	private final String _protocolName;

	private PlayerStatus(String protocolName) {
		_protocolName = protocolName;
	}

	/**
	 * The protocol name of a {@link PlayerStatus} constant.
	 *
	 * @see #valueOfProtocol(String)
	 */
	@Override
	public String protocolName() {
		return _protocolName;
	}

	/** Looks up a {@link PlayerStatus} constant by it's protocol name. */
	public static PlayerStatus valueOfProtocol(String protocolName) {
		if (protocolName == null) { return null; }
		switch (protocolName) {
			case "WAITING": return WAITING;
			case "ACTIVE": return ACTIVE;
			case "FOLDED": return FOLDED;
			case "ALL_IN": return ALL_IN;
			case "SITTING_OUT": return SITTING_OUT;
		}
		return WAITING;
	}

	/** Writes this instance to the given output. */
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.value(protocolName());
	}

	/** Reads a new instance from the given reader. */
	public static PlayerStatus readPlayerStatus(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		return valueOfProtocol(in.nextString());
	}

	/** Writes this instance to the given binary output. */
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		switch (this) {
			case WAITING: out.value(5); break;
			case ACTIVE: out.value(1); break;
			case FOLDED: out.value(2); break;
			case ALL_IN: out.value(3); break;
			case SITTING_OUT: out.value(4); break;
			default: out.value(0);
		}
	}

	/** Reads a new instance from the given binary reader. */
	public static PlayerStatus readPlayerStatus(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		switch (in.nextInt()) {
			case 5: return WAITING;
			case 1: return ACTIVE;
			case 2: return FOLDED;
			case 3: return ALL_IN;
			case 4: return SITTING_OUT;
			default: return WAITING;
		}
	}
}
