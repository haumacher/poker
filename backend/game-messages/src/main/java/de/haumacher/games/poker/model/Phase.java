package de.haumacher.games.poker.model;

/**
 * The current phase of a hand's lifecycle.
 *
 * <p>Transitions: WAITING_FOR_PLAYERS → PRE_FLOP → FLOP → TURN → RIVER → SHOWDOWN.
 * Any betting round can jump directly to SHOWDOWN if all but one player folds
 * or all remaining players are all-in.</p>
 */
public enum Phase implements de.haumacher.msgbuf.data.ProtocolEnum {

	/**
	 * No hand in progress, waiting for enough players to start.
	 */
	WAITING_FOR_PLAYERS("WAITING_FOR_PLAYERS"),

	/**
	 * Hole cards dealt, first betting round (before any community cards).
	 */
	PRE_FLOP("PRE_FLOP"),

	/**
	 * Three community cards dealt, second betting round.
	 */
	FLOP("FLOP"),

	/**
	 * Fourth community card dealt, third betting round.
	 */
	TURN("TURN"),

	/**
	 * Fifth community card dealt, final betting round.
	 */
	RIVER("RIVER"),

	/**
	 * Hands revealed and pots awarded.
	 */
	SHOWDOWN("SHOWDOWN"),

	;

	private final String _protocolName;

	private Phase(String protocolName) {
		_protocolName = protocolName;
	}

	/**
	 * The protocol name of a {@link Phase} constant.
	 *
	 * @see #valueOfProtocol(String)
	 */
	@Override
	public String protocolName() {
		return _protocolName;
	}

	/** Looks up a {@link Phase} constant by it's protocol name. */
	public static Phase valueOfProtocol(String protocolName) {
		if (protocolName == null) { return null; }
		switch (protocolName) {
			case "WAITING_FOR_PLAYERS": return WAITING_FOR_PLAYERS;
			case "PRE_FLOP": return PRE_FLOP;
			case "FLOP": return FLOP;
			case "TURN": return TURN;
			case "RIVER": return RIVER;
			case "SHOWDOWN": return SHOWDOWN;
		}
		return WAITING_FOR_PLAYERS;
	}

	/** Writes this instance to the given output. */
	public final void writeTo(de.haumacher.msgbuf.json.JsonWriter out) throws java.io.IOException {
		out.value(protocolName());
	}

	/** Reads a new instance from the given reader. */
	public static Phase readPhase(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		return valueOfProtocol(in.nextString());
	}

	/** Writes this instance to the given binary output. */
	public final void writeTo(de.haumacher.msgbuf.binary.DataWriter out) throws java.io.IOException {
		switch (this) {
			case WAITING_FOR_PLAYERS: out.value(6); break;
			case PRE_FLOP: out.value(1); break;
			case FLOP: out.value(2); break;
			case TURN: out.value(3); break;
			case RIVER: out.value(4); break;
			case SHOWDOWN: out.value(5); break;
			default: out.value(0);
		}
	}

	/** Reads a new instance from the given binary reader. */
	public static Phase readPhase(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		switch (in.nextInt()) {
			case 6: return WAITING_FOR_PLAYERS;
			case 1: return PRE_FLOP;
			case 2: return FLOP;
			case 3: return TURN;
			case 4: return RIVER;
			case 5: return SHOWDOWN;
			default: return WAITING_FOR_PLAYERS;
		}
	}
}
