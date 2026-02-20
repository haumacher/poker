package de.haumacher.games.poker.model;

/**
 * Base type for all client-to-server messages. Dispatched via the generated Visitor.
 */
public interface ClientMessage extends de.haumacher.msgbuf.data.DataObject, de.haumacher.msgbuf.binary.BinaryDataObject, de.haumacher.msgbuf.observer.Observable, de.haumacher.msgbuf.xml.XmlSerializable {

	/** Type codes for the {@link de.haumacher.games.poker.model.ClientMessage} hierarchy. */
	public enum TypeKind {

		/** Type literal for {@link de.haumacher.games.poker.model.PlayerActionMsg}. */
		PLAYER_ACTION_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.JoinTableMsg}. */
		JOIN_TABLE_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.LeaveTableMsg}. */
		LEAVE_TABLE_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.ChatMsg}. */
		CHAT_MSG,

		/** Type literal for {@link de.haumacher.games.poker.model.CreateTableMsg}. */
		CREATE_TABLE_MSG,
		;

	}

	/** Visitor interface for the {@link de.haumacher.games.poker.model.ClientMessage} hierarchy.*/
	public interface Visitor<R,A,E extends Throwable> {

		/** Visit case for {@link de.haumacher.games.poker.model.PlayerActionMsg}.*/
		R visit(de.haumacher.games.poker.model.PlayerActionMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.JoinTableMsg}.*/
		R visit(de.haumacher.games.poker.model.JoinTableMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.LeaveTableMsg}.*/
		R visit(de.haumacher.games.poker.model.LeaveTableMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.ChatMsg}.*/
		R visit(de.haumacher.games.poker.model.ChatMsg self, A arg) throws E;

		/** Visit case for {@link de.haumacher.games.poker.model.CreateTableMsg}.*/
		R visit(de.haumacher.games.poker.model.CreateTableMsg self, A arg) throws E;

	}

	/** The type code of this instance. */
	TypeKind kind();

	@Override
	public de.haumacher.games.poker.model.ClientMessage registerListener(de.haumacher.msgbuf.observer.Listener l);

	@Override
	public de.haumacher.games.poker.model.ClientMessage unregisterListener(de.haumacher.msgbuf.observer.Listener l);

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ClientMessage readClientMessage(de.haumacher.msgbuf.json.JsonReader in) throws java.io.IOException {
		de.haumacher.games.poker.model.ClientMessage result;
		in.beginArray();
		String type = in.nextString();
		switch (type) {
			case PlayerActionMsg.PLAYER_ACTION_MSG__TYPE: result = de.haumacher.games.poker.model.PlayerActionMsg.readPlayerActionMsg(in); break;
			case JoinTableMsg.JOIN_TABLE_MSG__TYPE: result = de.haumacher.games.poker.model.JoinTableMsg.readJoinTableMsg(in); break;
			case LeaveTableMsg.LEAVE_TABLE_MSG__TYPE: result = de.haumacher.games.poker.model.LeaveTableMsg.readLeaveTableMsg(in); break;
			case ChatMsg.CHAT_MSG__TYPE: result = de.haumacher.games.poker.model.ChatMsg.readChatMsg(in); break;
			case CreateTableMsg.CREATE_TABLE_MSG__TYPE: result = de.haumacher.games.poker.model.CreateTableMsg.readCreateTableMsg(in); break;
			default: in.skipValue(); result = null; break;
		}
		in.endArray();
		return result;
	}

	/** The binary identifier for this concrete type in the polymorphic {@link de.haumacher.games.poker.model.ClientMessage} hierarchy. */
	abstract int typeId();

	/** Reads a new instance from the given reader. */
	static de.haumacher.games.poker.model.ClientMessage readClientMessage(de.haumacher.msgbuf.binary.DataReader in) throws java.io.IOException {
		in.beginObject();
		int typeField = in.nextName();
		assert typeField == 0;
		int type = in.nextInt();
		de.haumacher.games.poker.model.ClientMessage result;
		switch (type) {
			case de.haumacher.games.poker.model.PlayerActionMsg.PLAYER_ACTION_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.PlayerActionMsg_Impl.readPlayerActionMsg_Content(in); break;
			case de.haumacher.games.poker.model.JoinTableMsg.JOIN_TABLE_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.JoinTableMsg_Impl.readJoinTableMsg_Content(in); break;
			case de.haumacher.games.poker.model.LeaveTableMsg.LEAVE_TABLE_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.LeaveTableMsg_Impl.readLeaveTableMsg_Content(in); break;
			case de.haumacher.games.poker.model.ChatMsg.CHAT_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.ChatMsg_Impl.readChatMsg_Content(in); break;
			case de.haumacher.games.poker.model.CreateTableMsg.CREATE_TABLE_MSG__TYPE_ID: result = de.haumacher.games.poker.model.impl.CreateTableMsg_Impl.readCreateTableMsg_Content(in); break;
			default: result = null; while (in.hasNext()) {in.skipValue(); }
		}
		in.endObject();
		return result;
	}

	/** Creates a new {@link ClientMessage} and reads properties from the content (attributes and inner tags) of the currently open element in the given {@link javax.xml.stream.XMLStreamReader}. */
	public static ClientMessage readClientMessage(javax.xml.stream.XMLStreamReader in) throws javax.xml.stream.XMLStreamException {
		in.nextTag();
		return de.haumacher.games.poker.model.impl.ClientMessage_Impl.readClientMessage_XmlContent(in);
	}

	/** Accepts the given visitor. */
	public abstract <R,A,E extends Throwable> R visit(Visitor<R,A,E> v, A arg) throws E;

}
