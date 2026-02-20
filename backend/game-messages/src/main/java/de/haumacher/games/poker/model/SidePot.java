package de.haumacher.games.poker.model;

public interface SidePot extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.SidePot} instance.
	 */
	static de.haumacher.games.poker.model.SidePot create() {
		return new de.haumacher.games.poker.model.impl.SidePot_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.SidePot} type in JSON format. */
	String SIDE_POT__TYPE = "SidePot";

	/** @see #getAmount() */
	String AMOUNT__PROP = "amount";

	/** @see #getEligibleSeats() */
	String ELIGIBLE_SEATS__PROP = "eligibleSeats";

	/** Identifier for the property {@link #getAmount()} in binary format. */
	static final int AMOUNT__ID = 1;

	/** Identifier for the property {@link #getEligibleSeats()} in binary format. */
	static final int ELIGIBLE_SEATS__ID = 2;

	long getAmount();

	/**
	 * @see #getAmount()
	 */
	de.haumacher.games.poker.model.SidePot setAmount(long value);

	java.util.List<Integer> getEligibleSeats();

	/**
	 * @see #getEligibleSeats()
	 */
	de.haumacher.games.poker.model.SidePot setEligibleSeats(java.util.List<? extends Integer> value);

	/**
	 * Adds a value to the {@link #getEligibleSeats()} list.
	 */
	de.haumacher.games.poker.model.SidePot addEligibleSeat(int value);

	/**
	 * Removes a value from the {@link #getEligibleSeats()} list.
	 */
	void removeEligibleSeat(int value);

	@Override
	public de.haumacher.games.poker.model.SidePot registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.SidePot unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.SidePot readSidePot(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.SidePot_Impl result = new de.haumacher.games.poker.model.impl.SidePot_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.SidePot readSidePot(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.SidePot result = de.haumacher.games.poker.model.impl.SidePot_Impl.readSidePot_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link SidePot} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static SidePot readSidePot(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.SidePot_Impl.readSidePot_XmlContent(in);
	}

}
