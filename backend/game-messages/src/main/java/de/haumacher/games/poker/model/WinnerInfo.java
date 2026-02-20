package de.haumacher.games.poker.model;

/**
 * Describes one winner's share of a pot at showdown.
 */
public interface WinnerInfo extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.WinnerInfo} instance.
	 */
	static de.haumacher.games.poker.model.WinnerInfo create() {
		return new de.haumacher.games.poker.model.impl.WinnerInfo_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.WinnerInfo} type in JSON format. */
	String WINNER_INFO__TYPE = "WinnerInfo";

	/** @see #getSeat() */
	String SEAT__PROP = "seat";

	/** @see #getAmount() */
	String AMOUNT__PROP = "amount";

	/** @see #getHandDescription() */
	String HAND_DESCRIPTION__PROP = "handDescription";

	/** Identifier for the property {@link #getSeat()} in binary format. */
	static final int SEAT__ID = 1;

	/** Identifier for the property {@link #getAmount()} in binary format. */
	static final int AMOUNT__ID = 2;

	/** Identifier for the property {@link #getHandDescription()} in binary format. */
	static final int HAND_DESCRIPTION__ID = 3;

	/**
	 * Seat index of the winning player.
	 */
	int getSeat();

	/**
	 * @see #getSeat()
	 */
	de.haumacher.games.poker.model.WinnerInfo setSeat(int value);

	/**
	 * Chips awarded from this pot.
	 */
	long getAmount();

	/**
	 * @see #getAmount()
	 */
	de.haumacher.games.poker.model.WinnerInfo setAmount(long value);

	/**
	 * Human-readable hand description (e.g. "Full House", "Two Pair").
	 */
	String getHandDescription();

	/**
	 * @see #getHandDescription()
	 */
	de.haumacher.games.poker.model.WinnerInfo setHandDescription(String value);

	@Override
	public de.haumacher.games.poker.model.WinnerInfo registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.WinnerInfo unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.WinnerInfo readWinnerInfo(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.WinnerInfo_Impl result = new de.haumacher.games.poker.model.impl.WinnerInfo_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.WinnerInfo readWinnerInfo(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.WinnerInfo result = de.haumacher.games.poker.model.impl.WinnerInfo_Impl.readWinnerInfo_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link WinnerInfo} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static WinnerInfo readWinnerInfo(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.WinnerInfo_Impl.readWinnerInfo_XmlContent(in);
	}

}
