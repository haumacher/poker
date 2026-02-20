package de.haumacher.games.poker.model;

/**
 * Base type for all server-to-client messages. Dispatched via the generated Visitor.
 */
public interface ServerMessage extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/** Type codes for the {@link de.haumacher.games.poker.model.ServerMessage} hierarchy. */
	public enum TypeKind {

		/** Type literal for {@link de.haumacher.games.poker.model.GameStateMsg}. */
		GAME_STATE_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.HoleCardsMsg}. */
		HOLE_CARDS_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.HandResultMsg}. */
		HAND_RESULT_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.ErrorMsg}. */
		ERROR_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.PlayerJoinedMsg}. */
		PLAYER_JOINED_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.PlayerLeftMsg}. */
		PLAYER_LEFT_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.TableInfoMsg}. */
		TABLE_INFO_MSG,
		;

	}

	/** Visitor interface for the {@link de.haumacher.games.poker.model.ServerMessage} hierarchy.*/
	public interface Visitor<R,A,E extends Throwable> {

		/** Visit case for {@link de.haumacher.games.poker.model.GameStateMsg}.*/
		R visit(de.haumacher.games.poker.model.GameStateMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.HoleCardsMsg}.*/
		R visit(de.haumacher.games.poker.model.HoleCardsMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.HandResultMsg}.*/
		R visit(de.haumacher.games.poker.model.HandResultMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.ErrorMsg}.*/
		R visit(de.haumacher.games.poker.model.ErrorMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.PlayerJoinedMsg}.*/
		R visit(de.haumacher.games.poker.model.PlayerJoinedMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.PlayerLeftMsg}.*/
		R visit(de.haumacher.games.poker.model.PlayerLeftMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.TableInfoMsg}.*/
		R visit(de.haumacher.games.poker.model.TableInfoMsg self, A arg) throws E;

	}

	/** The type code of this instance. */
	TypeKind kind();

	@Override
	public de.haumacher.games.poker.model.ServerMessage registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.ServerMessage unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ServerMessage readServerMessage(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.ServerMessage result;
		in.beginArray();
		String type = in.nextString();
		switch (type) {
			case GameStateMsg.GAME_STATE_MSG__TYPE: result = de.haumacher.games.poker.model.GameStateMsg.readGameStateMsg(in); break;
			case HoleCardsMsg.HOLE_CARDS_MSG__TYPE: result = de.haumacher.games.poker.model.HoleCardsMsg.readHoleCardsMsg(in); break;
			case HandResultMsg.HAND_RESULT_MSG__TYPE: result = de.haumacher.games.poker.model.HandResultMsg.readHandResultMsg(in); break;
			case ErrorMsg.ERROR_MSG__TYPE: result = de.haumacher.games.poker.model.ErrorMsg.readErrorMsg(in); break;
			case PlayerJoinedMsg.PLAYER_JOINED_MSG__TYPE: result = de.haumacher.games.poker.model.PlayerJoinedMsg.readPlayerJoinedMsg(in); break;
			case PlayerLeftMsg.PLAYER_LEFT_MSG__TYPE: result = de.haumacher.games.poker.model.PlayerLeftMsg.readPlayerLeftMsg(in); break;
			case TableInfoMsg.TABLE_INFO_MSG__TYPE: result = de.haumacher.games.poker.model.TableInfoMsg.readTableInfoMsg(in); break;
			default: in.skipValue(); result = null; break;
		}
		in.endArray();
		return result;
	}

	/** The binary identifier for this concrete type in the polymorphic {@link de.haumacher.games.poker.model.ServerMessage} hierarchy. */
	abstract int typeId();

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ServerMessage readServerMessage(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		int typeField = in.nextName();
		assert typeField == 0;
		int type = in.nextInt();
		de.haumacher.games.poker.model.ServerMessage result;
		switch (type) {
			case de.haumacher.games.poker.model.GameStateMsg.GAME_STATE_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.GameStateMsg_Impl.readGameStateMsg_Content(in); break;
			case de.haumacher.games.poker.model.HoleCardsMsg.HOLE_CARDS_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.HoleCardsMsg_Impl.readHoleCardsMsg_Content(in); break;
			case de.haumacher.games.poker.model.HandResultMsg.HAND_RESULT_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.HandResultMsg_Impl.readHandResultMsg_Content(in); break;
			case de.haumacher.games.poker.model.ErrorMsg.ERROR_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.ErrorMsg_Impl.readErrorMsg_Content(in); break;
			case de.haumacher.games.poker.model.PlayerJoinedMsg.PLAYER_JOINED_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.PlayerJoinedMsg_Impl.readPlayerJoinedMsg_Content(in); break;
			case de.haumacher.games.poker.model.PlayerLeftMsg.PLAYER_LEFT_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.PlayerLeftMsg_Impl.readPlayerLeftMsg_Content(in); break;
			case de.haumacher.games.poker.model.TableInfoMsg.TABLE_INFO_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.TableInfoMsg_Impl.readTableInfoMsg_Content(in); break;
			default: result = null; while (in.hasNext()) {in.skipValue(); }
		}
		in.endObject();
		return result;
	}

	/** Creates a new {@link ServerMessage} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ServerMessage readServerMessage(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ServerMessage_Impl.readServerMessage_XmlContent(in);
	}

	/** Accepts the given visitor. */
	public abstract <R,A,E extends Throwable> R visit(Visitor<R,A,E> v, A arg) throws E;

}
