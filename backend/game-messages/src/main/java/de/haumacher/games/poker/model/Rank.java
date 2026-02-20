package de.haumacher.games.poker.model;

public enum Rank implements de.haumacher.msgbuf.data.ProtocolEnum {

	TWO("TWO"),

	THREE("THREE"),

	FOUR("FOUR"),

	FIVE("FIVE"),

	SIX("SIX"),

	SEVEN("SEVEN"),

	EIGHT("EIGHT"),

	NINE("NINE"),

	TEN("TEN"),

	JACK("JACK"),

	QUEEN("QUEEN"),

	KING("KING"),

	ACE("ACE"),

	;

	private final String _protocolName;

	private Rank(String protocolName) {
		_protocolName = protocolName;
	}

	/**
	 * The protocol name of a {@link Rank} constant.
	 *
	 * @see #valueOfProtocol(String)
	 */
	@Override
	public String protocolName() {
		return _protocolName;
	}

	/** Looks up a {@link Rank} constant by it's protocol name. */
	public static Rank valueOfProtocol(String protocolName) {
		if (protocolName == null) { return null; }
		switch (protocolName) {
			case "TWO": return TWO;
			case "THREE": return THREE;
			case "FOUR": return FOUR;
			case "FIVE": return FIVE;
			case "SIX": return SIX;
			case "SEVEN": return SEVEN;
			case "EIGHT": return EIGHT;
			case "NINE": return NINE;
			case "TEN": return TEN;
			case "JACK": return JACK;
			case "QUEEN": return QUEEN;
			case "KING": return KING;
			case "ACE": return ACE;
		}
		return TWO;
	}

	/** Writes this instance to the given output. */
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.value(protocolName());
	}

	/** Reads a new instance from the given reader. */
	public static Rank readRank(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		return valueOfProtocol(in.nextString());
	}

	/** Writes this instance to the given binary output. */
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		switch (this) {
			case TWO: out.value(13); break;
			case THREE: out.value(1); break;
			case FOUR: out.value(2); break;
			case FIVE: out.value(3); break;
			case SIX: out.value(4); break;
			case SEVEN: out.value(5); break;
			case EIGHT: out.value(6); break;
			case NINE: out.value(7); break;
			case TEN: out.value(8); break;
			case JACK: out.value(9); break;
			case QUEEN: out.value(10); break;
			case KING: out.value(11); break;
			case ACE: out.value(12); break;
			default: out.value(0);
		}
	}

	/** Reads a new instance from the given binary reader. */
	public static Rank readRank(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		switch (in.nextInt()) {
			case 13: return TWO;
			case 1: return THREE;
			case 2: return FOUR;
			case 3: return FIVE;
			case 4: return SIX;
			case 5: return SEVEN;
			case 6: return EIGHT;
			case 7: return NINE;
			case 8: return TEN;
			case 9: return JACK;
			case 10: return QUEEN;
			case 11: return KING;
			case 12: return ACE;
			default: return TWO;
		}
	}
}
