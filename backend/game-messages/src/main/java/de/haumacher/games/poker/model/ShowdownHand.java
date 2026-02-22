package de.haumacher.games.poker.model;

/**
 * A contender's evaluated hand at showdown (for revealing hole cards and best hand).
 */
public interface ShowdownHand extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.ShowdownHand} instance.
	 */
	static de.haumacher.games.poker.model.ShowdownHand create() {
		return new de.haumacher.games.poker.model.impl.ShowdownHand_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.ShowdownHand} type in JSON format. */
	String SHOWDOWN_HAND__TYPE = "ShowdownHand";

	/** @see #getSeat() */
	String SEAT__PROP = "seat";

	/** @see #getHoleCards() */
	String HOLE_CARDS__PROP = "holeCards";

	/** @see #getHandDescription() */
	String HAND_DESCRIPTION__PROP = "handDescription";

	/** @see #getBestCards() */
	String BEST_CARDS__PROP = "bestCards";

	/** Identifier for the property {@link #getSeat()} in binary format. */
	static final int SEAT__ID = 1;

	/** Identifier for the property {@link #getHoleCards()} in binary format. */
	static final int HOLE_CARDS__ID = 2;

	/** Identifier for the property {@link #getHandDescription()} in binary format. */
	static final int HAND_DESCRIPTION__ID = 3;

	/** Identifier for the property {@link #getBestCards()} in binary format. */
	static final int BEST_CARDS__ID = 4;

	/**
	 * Seat index of the contender.
	 */
	int getSeat();

	/**
	 * @see #getSeat()
	 */
	de.haumacher.games.poker.model.ShowdownHand setSeat(int value);

	/**
	 * The player's two hole cards.
	 */
	java.util.List<de.haumacher.games.poker.model.Card> getHoleCards();

	/**
	 * @see #getHoleCards()
	 */
	de.haumacher.games.poker.model.ShowdownHand setHoleCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value);

	/**
	 * Adds a value to the {@link #getHoleCards()} list.
	 */
	de.haumacher.games.poker.model.ShowdownHand addHoleCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Removes a value from the {@link #getHoleCards()} list.
	 */
	void removeHoleCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Human-readable hand description (e.g. "Three of a Kind").
	 */
	String getHandDescription();

	/**
	 * @see #getHandDescription()
	 */
	de.haumacher.games.poker.model.ShowdownHand setHandDescription(String value);

	/**
	 * The 5 cards forming the best hand (for highlighting).
	 */
	java.util.List<de.haumacher.games.poker.model.Card> getBestCards();

	/**
	 * @see #getBestCards()
	 */
	de.haumacher.games.poker.model.ShowdownHand setBestCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value);

	/**
	 * Adds a value to the {@link #getBestCards()} list.
	 */
	de.haumacher.games.poker.model.ShowdownHand addBestCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Removes a value from the {@link #getBestCards()} list.
	 */
	void removeBestCard(de.haumacher.games.poker.model.Card value);

	@Override
	public de.haumacher.games.poker.model.ShowdownHand registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.ShowdownHand unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ShowdownHand readShowdownHand(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.ShowdownHand_Impl result = new de.haumacher.games.poker.model.impl.ShowdownHand_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ShowdownHand readShowdownHand(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.ShowdownHand result = de.haumacher.games.poker.model.impl.ShowdownHand_Impl.readShowdownHand_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link ShowdownHand} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ShowdownHand readShowdownHand(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ShowdownHand_Impl.readShowdownHand_XmlContent(in);
	}

}
