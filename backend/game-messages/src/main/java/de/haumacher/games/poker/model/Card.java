package de.haumacher.games.poker.model;

public interface Card extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/**
	 * Creates a {@link de.haumacher.games.poker.model.Card} instance.
	 */
	static de.haumacher.games.poker.model.Card create() {
		return new de.haumacher.games.poker.model.impl.Card_Impl();
	}

	/** Identifier for the {@link de.haumacher.games.poker.model.Card} type in JSON format. */
	String CARD__TYPE = "Card";

	/** @see #getRank() */
	String RANK__PROP = "rank";

	/** @see #getSuit() */
	String SUIT__PROP = "suit";

	/** Identifier for the property {@link #getRank()} in binary format. */
	static final int RANK__ID = 1;

	/** Identifier for the property {@link #getSuit()} in binary format. */
	static final int SUIT__ID = 2;

	de.haumacher.games.poker.model.Rank getRank();

	/**
	 * @see #getRank()
	 */
	de.haumacher.games.poker.model.Card setRank(de.haumacher.games.poker.model.Rank value);

	de.haumacher.games.poker.model.Suit getSuit();

	/**
	 * @see #getSuit()
	 */
	de.haumacher.games.poker.model.Card setSuit(de.haumacher.games.poker.model.Suit value);

	@Override
	public de.haumacher.games.poker.model.Card registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.Card unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.Card readCard(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.impl.Card_Impl result = new de.haumacher.games.poker.model.impl.Card_Impl();
		result.readContent(in);
		return result;
	}

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.Card readCard(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		de.haumacher.games.poker.model.Card result = de.haumacher.games.poker.model.impl.Card_Impl.readCard_Content(in);
		in.endObject();
		return result;
	}

	/** Creates a new {@link Card} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static Card readCard(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.Card_Impl.readCard_XmlContent(in);
	}

}
