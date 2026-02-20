package de.haumacher.games.poker.model;

/**
 * Private hole cards sent only to the individual player.
 *
 * <p>Never broadcast — routed to a single WebSocket connection.
 * The client uses these to display the player's own cards.</p>
 */
public interface HoleCardsMsg extends ServerMessage {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.HoleCardsMsg} instance.
	 */
	static de.haumacher.games.poker.model.HoleCardsMsg create() {
		return new de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.HoleCardsMsg} type in JSON format. */
	String HOLE_CARDS_MSG__TYPE = "HoleCardsMsg";

	/** @see #getHandNumber() */
	String HAND_NUMBER__PROP = "handNumber";

	/** @see #getCards() */
	String CARDS__PROP = "cards";

	/** Identifier for the {@link de.haumacher.games.poker.model.HoleCardsMsg} type in binary format. */
	static final int HOLE_CARDS_MSG__TYPE_ID = 2;

	/** Identifier for the property {@link #getHandNumber()} in binary format. */
	static final int HAND_NUMBER__ID = 1;

	/** Identifier for the property {@link #getCards()} in binary format. */
	static final int CARDS__ID = 2;

	/**
	 * Hand number these cards belong to (for correlation with GameStateMsg).
	 */
	int getHandNumber();

	/**
	 * @see #getHandNumber()
	 */
	de.haumacher.games.poker.model.HoleCardsMsg setHandNumber(int value);

	/**
	 * The player's two private hole cards.
	 */
	java.util.List<de.haumacher.games.poker.model.Card> getCards();

	/**
	 * @see #getCards()
	 */
	de.haumacher.games.poker.model.HoleCardsMsg setCards(java.util.List<? extends de.haumacher.games.poker.model.Card> value);

	/**
	 * Adds a value to the {@link #getCards()} list.
	 */
	de.haumacher.games.poker.model.HoleCardsMsg addCard(de.haumacher.games.poker.model.Card value);

	/**
	 * Removes a value from the {@link #getCards()} list.
	 */
	void removeCard(de.haumacher.games.poker.model.Card value);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.HoleCardsMsg readHoleCardsMsg(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl result = new de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.HoleCardsMsg readHoleCardsMsg(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.HoleCardsMsg result = de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl.readHoleCardsMsg_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link HoleCardsMsg} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static HoleCardsMsg readHoleCardsMsg(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl.readHoleCardsMsg_XmlContent(in);
	}

}
