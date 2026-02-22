import 'package:jsontool/jsontool.dart';

/// Common functionality for JSON generation and parsing.
abstract class _JsonObject {
	@override
	String toString() {
		var buffer = StringBuffer();
		writeTo(jsonStringWriter(buffer));
		return buffer.toString();
	}

	/// The ID to announce the type of the object.
	String _jsonType();

	/// Reads the object contents (after the type information).
	void _readContent(JsonReader json) {
		json.expectObject();
		while (json.hasNextKey()) {
			var key = json.nextKey();
			_readProperty(key!, json);
		}
	}

	/// Reads the value of the property with the given name.
	void _readProperty(String key, JsonReader json) {
		json.skipAnyValue();
	}

	/// Writes this object to the given writer (including type information).
	void writeTo(JsonSink json) {
		json.startArray();
		json.addString(_jsonType());
		writeContent(json);
		json.endArray();
	}

	/// Writes the contents of this object to the given writer (excluding type information).
	void writeContent(JsonSink json) {
		json.startObject();
		_writeProperties(json);
		json.endObject();
	}

	/// Writes all key/value pairs of this object.
	void _writeProperties(JsonSink json) {
		// No properties.
	}
}

///  Card rank, ordered from lowest (TWO) to highest (ACE).
enum Rank {
	two,
	three,
	four,
	five,
	six,
	seven,
	eight,
	nine,
	ten,
	jack,
	queen,
	king,
	ace,
}

/// Writes a value of Rank to a JSON stream.
void writeRank(JsonSink json, Rank value) {
	switch (value) {
		case Rank.two: json.addString("TWO"); break;
		case Rank.three: json.addString("THREE"); break;
		case Rank.four: json.addString("FOUR"); break;
		case Rank.five: json.addString("FIVE"); break;
		case Rank.six: json.addString("SIX"); break;
		case Rank.seven: json.addString("SEVEN"); break;
		case Rank.eight: json.addString("EIGHT"); break;
		case Rank.nine: json.addString("NINE"); break;
		case Rank.ten: json.addString("TEN"); break;
		case Rank.jack: json.addString("JACK"); break;
		case Rank.queen: json.addString("QUEEN"); break;
		case Rank.king: json.addString("KING"); break;
		case Rank.ace: json.addString("ACE"); break;
		default: throw ("No such literal: " + value.name);
	}
}

/// Reads a value of Rank from a JSON stream.
Rank readRank(JsonReader json) {
	switch (json.expectString()) {
		case "TWO": return Rank.two;
		case "THREE": return Rank.three;
		case "FOUR": return Rank.four;
		case "FIVE": return Rank.five;
		case "SIX": return Rank.six;
		case "SEVEN": return Rank.seven;
		case "EIGHT": return Rank.eight;
		case "NINE": return Rank.nine;
		case "TEN": return Rank.ten;
		case "JACK": return Rank.jack;
		case "QUEEN": return Rank.queen;
		case "KING": return Rank.king;
		case "ACE": return Rank.ace;
		default: return Rank.two;
	}
}

///  Card suit. All four suits are equal in Texas Hold'em (no suit ranking).
enum Suit {
	hearts,
	diamonds,
	clubs,
	spades,
}

/// Writes a value of Suit to a JSON stream.
void writeSuit(JsonSink json, Suit value) {
	switch (value) {
		case Suit.hearts: json.addString("HEARTS"); break;
		case Suit.diamonds: json.addString("DIAMONDS"); break;
		case Suit.clubs: json.addString("CLUBS"); break;
		case Suit.spades: json.addString("SPADES"); break;
		default: throw ("No such literal: " + value.name);
	}
}

/// Reads a value of Suit from a JSON stream.
Suit readSuit(JsonReader json) {
	switch (json.expectString()) {
		case "HEARTS": return Suit.hearts;
		case "DIAMONDS": return Suit.diamonds;
		case "CLUBS": return Suit.clubs;
		case "SPADES": return Suit.spades;
		default: return Suit.hearts;
	}
}

///  The current phase of a hand's lifecycle.
///
///  <p>Transitions: WAITING_FOR_PLAYERS → PRE_FLOP → FLOP → TURN → RIVER → SHOWDOWN.
///  Any betting round can jump directly to SHOWDOWN if all but one player folds
///  or all remaining players are all-in.</p>
enum Phase {
	///  No hand in progress, waiting for enough players to start.
	waitingForPlayers,
	///  Hole cards dealt, first betting round (before any community cards).
	preFlop,
	///  Three community cards dealt, second betting round.
	flop,
	///  Fourth community card dealt, third betting round.
	turn,
	///  Fifth community card dealt, final betting round.
	river,
	///  Hands revealed and pots awarded.
	showdown,
}

/// Writes a value of Phase to a JSON stream.
void writePhase(JsonSink json, Phase value) {
	switch (value) {
		case Phase.waitingForPlayers: json.addString("WAITING_FOR_PLAYERS"); break;
		case Phase.preFlop: json.addString("PRE_FLOP"); break;
		case Phase.flop: json.addString("FLOP"); break;
		case Phase.turn: json.addString("TURN"); break;
		case Phase.river: json.addString("RIVER"); break;
		case Phase.showdown: json.addString("SHOWDOWN"); break;
		default: throw ("No such literal: " + value.name);
	}
}

/// Reads a value of Phase from a JSON stream.
Phase readPhase(JsonReader json) {
	switch (json.expectString()) {
		case "WAITING_FOR_PLAYERS": return Phase.waitingForPlayers;
		case "PRE_FLOP": return Phase.preFlop;
		case "FLOP": return Phase.flop;
		case "TURN": return Phase.turn;
		case "RIVER": return Phase.river;
		case "SHOWDOWN": return Phase.showdown;
		default: return Phase.waitingForPlayers;
	}
}

///  A player action during a betting round.
enum ActionType {
	///  Surrender the hand and forfeit any chips already in the pot.
	fold,
	///  Pass the action when no bet is outstanding (bet stays at zero).
	check,
	///  Match the current highest bet.
	call,
	///  Increase the current bet. The raise amount must meet the minimum raise.
	raise,
	///  Bet all remaining chips. Allowed even if below the minimum raise.
	allIn,
}

/// Writes a value of ActionType to a JSON stream.
void writeActionType(JsonSink json, ActionType value) {
	switch (value) {
		case ActionType.fold: json.addString("FOLD"); break;
		case ActionType.check: json.addString("CHECK"); break;
		case ActionType.call: json.addString("CALL"); break;
		case ActionType.raise: json.addString("RAISE"); break;
		case ActionType.allIn: json.addString("ALL_IN"); break;
		default: throw ("No such literal: " + value.name);
	}
}

/// Reads a value of ActionType from a JSON stream.
ActionType readActionType(JsonReader json) {
	switch (json.expectString()) {
		case "FOLD": return ActionType.fold;
		case "CHECK": return ActionType.check;
		case "CALL": return ActionType.call;
		case "RAISE": return ActionType.raise;
		case "ALL_IN": return ActionType.allIn;
		default: return ActionType.fold;
	}
}

///  A player's current status within a hand.
enum PlayerStatus {
	///  Seated but not yet participating in a hand.
	waiting,
	///  In the current hand and able to act.
	active,
	///  Folded this hand; still seated but out until the next hand.
	folded,
	///  All chips committed; still in the hand but cannot act further.
	allIn,
	///  Voluntarily sitting out; skipped for dealing and blinds.
	sittingOut,
}

/// Writes a value of PlayerStatus to a JSON stream.
void writePlayerStatus(JsonSink json, PlayerStatus value) {
	switch (value) {
		case PlayerStatus.waiting: json.addString("WAITING"); break;
		case PlayerStatus.active: json.addString("ACTIVE"); break;
		case PlayerStatus.folded: json.addString("FOLDED"); break;
		case PlayerStatus.allIn: json.addString("ALL_IN"); break;
		case PlayerStatus.sittingOut: json.addString("SITTING_OUT"); break;
		default: throw ("No such literal: " + value.name);
	}
}

/// Reads a value of PlayerStatus from a JSON stream.
PlayerStatus readPlayerStatus(JsonReader json) {
	switch (json.expectString()) {
		case "WAITING": return PlayerStatus.waiting;
		case "ACTIVE": return PlayerStatus.active;
		case "FOLDED": return PlayerStatus.folded;
		case "ALL_IN": return PlayerStatus.allIn;
		case "SITTING_OUT": return PlayerStatus.sittingOut;
		default: return PlayerStatus.waiting;
	}
}

///  A single playing card identified by rank and suit.
class Card extends _JsonObject {
	///  The rank of this card (TWO through ACE).
	Rank rank;

	///  The suit of this card.
	Suit suit;

	/// Creates a Card.
	Card({
			this.rank = Rank.two,
			this.suit = Suit.hearts,
	});

	/// Parses a Card from a string source.
	static Card? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a Card instance from the given reader.
	static Card read(JsonReader json) {
		Card result = Card();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "Card";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "rank": {
				rank = readRank(json);
				break;
			}
			case "suit": {
				suit = readSuit(json);
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("rank");
		writeRank(json, rank);

		json.addKey("suit");
		writeSuit(json, suit);
	}

}

///  Public state of one player at the table.
///
///  <p>Broadcast to all clients. Does not include hole cards (those are sent
	///  privately via {@link HoleCardsMsg}).</p>
class PlayerState extends _JsonObject {
	///  Seat index (0-8) at the table.
	int seat;

	///  The player's chosen display name.
	String displayName;

	///  Current chip count (excludes chips already bet this round).
	int chips;

	///  Amount bet in the current betting round. Reset to 0 when a new round begins.
	int currentBet;

	///  The player's status in the current hand.
	PlayerStatus status;

	///  The most recent action taken by this player (null if none yet this hand).
	ActionType lastAction;

	/// Creates a PlayerState.
	PlayerState({
			this.seat = 0,
			this.displayName = "",
			this.chips = 0,
			this.currentBet = 0,
			this.status = PlayerStatus.waiting,
			this.lastAction = ActionType.fold,
	});

	/// Parses a PlayerState from a string source.
	static PlayerState? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a PlayerState instance from the given reader.
	static PlayerState read(JsonReader json) {
		PlayerState result = PlayerState();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "PlayerState";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "seat": {
				seat = json.expectInt();
				break;
			}
			case "displayName": {
				displayName = json.expectString();
				break;
			}
			case "chips": {
				chips = json.expectInt();
				break;
			}
			case "currentBet": {
				currentBet = json.expectInt();
				break;
			}
			case "status": {
				status = readPlayerStatus(json);
				break;
			}
			case "lastAction": {
				lastAction = readActionType(json);
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("seat");
		json.addNumber(seat);

		json.addKey("displayName");
		json.addString(displayName);

		json.addKey("chips");
		json.addNumber(chips);

		json.addKey("currentBet");
		json.addNumber(currentBet);

		json.addKey("status");
		writePlayerStatus(json, status);

		json.addKey("lastAction");
		writeActionType(json, lastAction);
	}

}

///  A side pot created when players go all-in with unequal stacks.
///
///  <p>Each side pot has an amount and a list of seats eligible to win it.
///  Players who folded or didn't contribute enough chips are excluded.</p>
class SidePot extends _JsonObject {
	///  Total chips in this side pot.
	int amount;

	///  Seat indices of players eligible to win this pot.
	List<int> eligibleSeats;

	/// Creates a SidePot.
	SidePot({
			this.amount = 0,
			this.eligibleSeats = const [],
	});

	/// Parses a SidePot from a string source.
	static SidePot? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a SidePot instance from the given reader.
	static SidePot read(JsonReader json) {
		SidePot result = SidePot();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "SidePot";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "amount": {
				amount = json.expectInt();
				break;
			}
			case "eligibleSeats": {
				json.expectArray();
				eligibleSeats = [];
				while (json.hasNext()) {
					eligibleSeats.add(json.expectInt());
				}
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("amount");
		json.addNumber(amount);

		json.addKey("eligibleSeats");
		json.startArray();
		for (var _element in eligibleSeats) {
			json.addNumber(_element);
		}
		json.endArray();
	}

}

///  Describes one winner's share of a pot at showdown.
class WinnerInfo extends _JsonObject {
	///  Seat index of the winning player.
	int seat;

	///  Chips awarded from this pot.
	int amount;

	///  Human-readable hand description (e.g. "Full House", "Two Pair").
	String handDescription;

	/// Creates a WinnerInfo.
	WinnerInfo({
			this.seat = 0,
			this.amount = 0,
			this.handDescription = "",
	});

	/// Parses a WinnerInfo from a string source.
	static WinnerInfo? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a WinnerInfo instance from the given reader.
	static WinnerInfo read(JsonReader json) {
		WinnerInfo result = WinnerInfo();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "WinnerInfo";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "seat": {
				seat = json.expectInt();
				break;
			}
			case "amount": {
				amount = json.expectInt();
				break;
			}
			case "handDescription": {
				handDescription = json.expectString();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("seat");
		json.addNumber(seat);

		json.addKey("amount");
		json.addNumber(amount);

		json.addKey("handDescription");
		json.addString(handDescription);
	}

}

/// Visitor interface for ServerMessage.
abstract class ServerMessageVisitor<R, A> {
	R visitGameStateMsg(GameStateMsg self, A arg);
	R visitHoleCardsMsg(HoleCardsMsg self, A arg);
	R visitHandResultMsg(HandResultMsg self, A arg);
	R visitErrorMsg(ErrorMsg self, A arg);
	R visitPlayerJoinedMsg(PlayerJoinedMsg self, A arg);
	R visitPlayerLeftMsg(PlayerLeftMsg self, A arg);
	R visitTableInfoMsg(TableInfoMsg self, A arg);
}

///  Base type for all server-to-client messages. Dispatched via the generated Visitor.
abstract class ServerMessage extends _JsonObject {
	/// Creates a ServerMessage.
	ServerMessage();

	/// Parses a ServerMessage from a string source.
	static ServerMessage? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a ServerMessage instance from the given reader.
	static ServerMessage? read(JsonReader json) {
		ServerMessage? result;

		json.expectArray();
		if (!json.hasNext()) {
			return null;
		}

		switch (json.expectString()) {
			case "GameStateMsg": result = GameStateMsg(); break;
			case "HoleCardsMsg": result = HoleCardsMsg(); break;
			case "HandResultMsg": result = HandResultMsg(); break;
			case "ErrorMsg": result = ErrorMsg(); break;
			case "PlayerJoinedMsg": result = PlayerJoinedMsg(); break;
			case "PlayerLeftMsg": result = PlayerLeftMsg(); break;
			case "TableInfoMsg": result = TableInfoMsg(); break;
			default: result = null;
		}

		if (!json.hasNext() || json.tryNull()) {
			return null;
		}

		if (result == null) {
			json.skipAnyValue();
		} else {
			result._readContent(json);
		}
		json.endArray();

		return result;
	}

	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg);

}

///  Full public game state broadcast to all players at the table.
///
///  <p>Sent after every state change (phase transition, player action, player join/leave).
///  Contains everything a client needs to render the table — except private hole cards.</p>
class GameStateMsg extends ServerMessage {
	///  Unique identifier of the table.
	String tableId;

	///  Sequential hand number (1-based), incremented each new deal.
	int handNumber;

	///  Current phase of the hand.
	Phase phase;

	///  Community cards dealt so far (0 in PRE_FLOP, 3 after FLOP, 4 after TURN, 5 after RIVER).
	List<Card> communityCards;

	///  Total chips in all pots combined.
	int pot;

	///  Breakdown of side pots when players are all-in with unequal stacks. Empty if no side pots.
	List<SidePot> sidePots;

	///  Seat index of the player whose turn it is to act, or -1 if no action pending.
	int currentPlayerSeat;

	///  Seat index of the current dealer button.
	int dealerSeat;

	///  State of all seated players (up to 9).
	List<PlayerState> players;

	///  Seconds remaining for the current player to act before auto-fold.
	int turnTimeRemaining;

	///  Minimum total bet amount for a legal raise.
	int minRaise;

	/// Creates a GameStateMsg.
	GameStateMsg({
			this.tableId = "",
			this.handNumber = 0,
			this.phase = Phase.waitingForPlayers,
			this.communityCards = const [],
			this.pot = 0,
			this.sidePots = const [],
			this.currentPlayerSeat = 0,
			this.dealerSeat = 0,
			this.players = const [],
			this.turnTimeRemaining = 0,
			this.minRaise = 0,
	});

	/// Parses a GameStateMsg from a string source.
	static GameStateMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a GameStateMsg instance from the given reader.
	static GameStateMsg read(JsonReader json) {
		GameStateMsg result = GameStateMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "GameStateMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "tableId": {
				tableId = json.expectString();
				break;
			}
			case "handNumber": {
				handNumber = json.expectInt();
				break;
			}
			case "phase": {
				phase = readPhase(json);
				break;
			}
			case "communityCards": {
				json.expectArray();
				communityCards = [];
				while (json.hasNext()) {
					if (!json.tryNull()) {
						var value = Card.read(json);
						if (value != null) {
							communityCards.add(value);
						}
					}
				}
				break;
			}
			case "pot": {
				pot = json.expectInt();
				break;
			}
			case "sidePots": {
				json.expectArray();
				sidePots = [];
				while (json.hasNext()) {
					if (!json.tryNull()) {
						var value = SidePot.read(json);
						if (value != null) {
							sidePots.add(value);
						}
					}
				}
				break;
			}
			case "currentPlayerSeat": {
				currentPlayerSeat = json.expectInt();
				break;
			}
			case "dealerSeat": {
				dealerSeat = json.expectInt();
				break;
			}
			case "players": {
				json.expectArray();
				players = [];
				while (json.hasNext()) {
					if (!json.tryNull()) {
						var value = PlayerState.read(json);
						if (value != null) {
							players.add(value);
						}
					}
				}
				break;
			}
			case "turnTimeRemaining": {
				turnTimeRemaining = json.expectInt();
				break;
			}
			case "minRaise": {
				minRaise = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("tableId");
		json.addString(tableId);

		json.addKey("handNumber");
		json.addNumber(handNumber);

		json.addKey("phase");
		writePhase(json, phase);

		json.addKey("communityCards");
		json.startArray();
		for (var _element in communityCards) {
			_element.writeContent(json);
		}
		json.endArray();

		json.addKey("pot");
		json.addNumber(pot);

		json.addKey("sidePots");
		json.startArray();
		for (var _element in sidePots) {
			_element.writeContent(json);
		}
		json.endArray();

		json.addKey("currentPlayerSeat");
		json.addNumber(currentPlayerSeat);

		json.addKey("dealerSeat");
		json.addNumber(dealerSeat);

		json.addKey("players");
		json.startArray();
		for (var _element in players) {
			_element.writeContent(json);
		}
		json.endArray();

		json.addKey("turnTimeRemaining");
		json.addNumber(turnTimeRemaining);

		json.addKey("minRaise");
		json.addNumber(minRaise);
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitGameStateMsg(this, arg);

}

///  Private hole cards sent only to the individual player.
///
///  <p>Never broadcast — routed to a single WebSocket connection.
///  The client uses these to display the player's own cards.</p>
class HoleCardsMsg extends ServerMessage {
	///  Hand number these cards belong to (for correlation with GameStateMsg).
	int handNumber;

	///  The player's two private hole cards.
	List<Card> cards;

	/// Creates a HoleCardsMsg.
	HoleCardsMsg({
			this.handNumber = 0,
			this.cards = const [],
	});

	/// Parses a HoleCardsMsg from a string source.
	static HoleCardsMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a HoleCardsMsg instance from the given reader.
	static HoleCardsMsg read(JsonReader json) {
		HoleCardsMsg result = HoleCardsMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "HoleCardsMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "handNumber": {
				handNumber = json.expectInt();
				break;
			}
			case "cards": {
				json.expectArray();
				cards = [];
				while (json.hasNext()) {
					if (!json.tryNull()) {
						var value = Card.read(json);
						if (value != null) {
							cards.add(value);
						}
					}
				}
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("handNumber");
		json.addNumber(handNumber);

		json.addKey("cards");
		json.startArray();
		for (var _element in cards) {
			_element.writeContent(json);
		}
		json.endArray();
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitHoleCardsMsg(this, arg);

}

///  Showdown results announcing winners and their hands.
///
///  <p>Sent after SHOWDOWN phase when multiple players remain.
///  Multiple winners are possible (split pots, multiple side pots).</p>
class HandResultMsg extends ServerMessage {
	///  All winners across main pot and side pots.
	List<WinnerInfo> winners;

	/// Creates a HandResultMsg.
	HandResultMsg({
			this.winners = const [],
	});

	/// Parses a HandResultMsg from a string source.
	static HandResultMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a HandResultMsg instance from the given reader.
	static HandResultMsg read(JsonReader json) {
		HandResultMsg result = HandResultMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "HandResultMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "winners": {
				json.expectArray();
				winners = [];
				while (json.hasNext()) {
					if (!json.tryNull()) {
						var value = WinnerInfo.read(json);
						if (value != null) {
							winners.add(value);
						}
					}
				}
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("winners");
		json.startArray();
		for (var _element in winners) {
			_element.writeContent(json);
		}
		json.endArray();
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitHandResultMsg(this, arg);

}

///  Error message sent to a single player when their action is rejected.
///
///  <p>Examples: invalid action for current state, raise too small, not your turn.
///  The error code can be used for i18n on the client side.</p>
class ErrorMsg extends ServerMessage {
	///  Machine-readable error code (e.g. "INVALID_ACTION", "RAISE_TOO_SMALL").
	String code;

	///  Human-readable error description.
	String message;

	/// Creates a ErrorMsg.
	ErrorMsg({
			this.code = "",
			this.message = "",
	});

	/// Parses a ErrorMsg from a string source.
	static ErrorMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a ErrorMsg instance from the given reader.
	static ErrorMsg read(JsonReader json) {
		ErrorMsg result = ErrorMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "ErrorMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "code": {
				code = json.expectString();
				break;
			}
			case "message": {
				message = json.expectString();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("code");
		json.addString(code);

		json.addKey("message");
		json.addString(message);
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitErrorMsg(this, arg);

}

///  Broadcast when a new player sits down at the table.
class PlayerJoinedMsg extends ServerMessage {
	///  Full state of the player who joined.
	PlayerState? playerState;

	/// Creates a PlayerJoinedMsg.
	PlayerJoinedMsg({
			this.playerState,
	});

	/// Parses a PlayerJoinedMsg from a string source.
	static PlayerJoinedMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a PlayerJoinedMsg instance from the given reader.
	static PlayerJoinedMsg read(JsonReader json) {
		PlayerJoinedMsg result = PlayerJoinedMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "PlayerJoinedMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "playerState": {
				playerState = json.tryNull() ? null : PlayerState.read(json);
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		var _playerState = playerState;
		if (_playerState != null) {
			json.addKey("playerState");
			_playerState.writeContent(json);
		}
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitPlayerJoinedMsg(this, arg);

}

///  Broadcast when a player leaves the table.
class PlayerLeftMsg extends ServerMessage {
	///  Seat index that was vacated.
	int seat;

	/// Creates a PlayerLeftMsg.
	PlayerLeftMsg({
			this.seat = 0,
	});

	/// Parses a PlayerLeftMsg from a string source.
	static PlayerLeftMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a PlayerLeftMsg instance from the given reader.
	static PlayerLeftMsg read(JsonReader json) {
		PlayerLeftMsg result = PlayerLeftMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "PlayerLeftMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "seat": {
				seat = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("seat");
		json.addNumber(seat);
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitPlayerLeftMsg(this, arg);

}

///  Confirmation sent privately after a player creates or joins a table.
///
///  <p>Contains the room code the player can share with friends to invite them.</p>
class TableInfoMsg extends ServerMessage {
	///  Unique identifier of the table.
	String tableId;

	///  6-character room code for sharing with friends.
	String roomCode;

	///  Assigned seat index, or -1 if the player has not sat down yet (create only).
	int seat;

	///  Small blind amount for this table.
	int smallBlind;

	///  Big blind amount for this table.
	int bigBlind;

	/// Creates a TableInfoMsg.
	TableInfoMsg({
			this.tableId = "",
			this.roomCode = "",
			this.seat = 0,
			this.smallBlind = 0,
			this.bigBlind = 0,
	});

	/// Parses a TableInfoMsg from a string source.
	static TableInfoMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a TableInfoMsg instance from the given reader.
	static TableInfoMsg read(JsonReader json) {
		TableInfoMsg result = TableInfoMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "TableInfoMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "tableId": {
				tableId = json.expectString();
				break;
			}
			case "roomCode": {
				roomCode = json.expectString();
				break;
			}
			case "seat": {
				seat = json.expectInt();
				break;
			}
			case "smallBlind": {
				smallBlind = json.expectInt();
				break;
			}
			case "bigBlind": {
				bigBlind = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("tableId");
		json.addString(tableId);

		json.addKey("roomCode");
		json.addString(roomCode);

		json.addKey("seat");
		json.addNumber(seat);

		json.addKey("smallBlind");
		json.addNumber(smallBlind);

		json.addKey("bigBlind");
		json.addNumber(bigBlind);
	}

	@override
	R visitServerMessage<R, A>(ServerMessageVisitor<R, A> v, A arg) => v.visitTableInfoMsg(this, arg);

}

/// Visitor interface for ClientMessage.
abstract class ClientMessageVisitor<R, A> {
	R visitPlayerActionMsg(PlayerActionMsg self, A arg);
	R visitJoinTableMsg(JoinTableMsg self, A arg);
	R visitLeaveTableMsg(LeaveTableMsg self, A arg);
	R visitChatMsg(ChatMsg self, A arg);
	R visitCreateTableMsg(CreateTableMsg self, A arg);
}

///  Base type for all client-to-server messages. Dispatched via the generated Visitor.
abstract class ClientMessage extends _JsonObject {
	/// Creates a ClientMessage.
	ClientMessage();

	/// Parses a ClientMessage from a string source.
	static ClientMessage? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a ClientMessage instance from the given reader.
	static ClientMessage? read(JsonReader json) {
		ClientMessage? result;

		json.expectArray();
		if (!json.hasNext()) {
			return null;
		}

		switch (json.expectString()) {
			case "PlayerActionMsg": result = PlayerActionMsg(); break;
			case "JoinTableMsg": result = JoinTableMsg(); break;
			case "LeaveTableMsg": result = LeaveTableMsg(); break;
			case "ChatMsg": result = ChatMsg(); break;
			case "CreateTableMsg": result = CreateTableMsg(); break;
			default: result = null;
		}

		if (!json.hasNext() || json.tryNull()) {
			return null;
		}

		if (result == null) {
			json.skipAnyValue();
		} else {
			result._readContent(json);
		}
		json.endArray();

		return result;
	}

	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg);

}

///  A player's action during a betting round.
class PlayerActionMsg extends ClientMessage {
	///  The type of action (FOLD, CHECK, CALL, RAISE, ALL_IN).
	ActionType actionType;

	///  Total bet amount (only meaningful for RAISE — the target bet size, not the increment).
	///  Ignored for FOLD, CHECK, CALL, ALL_IN.
	int amount;

	/// Creates a PlayerActionMsg.
	PlayerActionMsg({
			this.actionType = ActionType.fold,
			this.amount = 0,
	});

	/// Parses a PlayerActionMsg from a string source.
	static PlayerActionMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a PlayerActionMsg instance from the given reader.
	static PlayerActionMsg read(JsonReader json) {
		PlayerActionMsg result = PlayerActionMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "PlayerActionMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "actionType": {
				actionType = readActionType(json);
				break;
			}
			case "amount": {
				amount = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("actionType");
		writeActionType(json, actionType);

		json.addKey("amount");
		json.addNumber(amount);
	}

	@override
	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg) => v.visitPlayerActionMsg(this, arg);

}

///  Request to join a table and optionally sit at a preferred seat.
class JoinTableMsg extends ClientMessage {
	///  ID of the table to join.
	String tableId;

	///  Desired seat index (0-8), or -1 for any available seat.
	int preferredSeat;

	///  The player's chosen display name. Empty means server picks a default.
	String displayName;

	///  Starting chip count. Zero means server picks a default.
	int chips;

	/// Creates a JoinTableMsg.
	JoinTableMsg({
			this.tableId = "",
			this.preferredSeat = 0,
			this.displayName = "",
			this.chips = 0,
	});

	/// Parses a JoinTableMsg from a string source.
	static JoinTableMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a JoinTableMsg instance from the given reader.
	static JoinTableMsg read(JsonReader json) {
		JoinTableMsg result = JoinTableMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "JoinTableMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "tableId": {
				tableId = json.expectString();
				break;
			}
			case "preferredSeat": {
				preferredSeat = json.expectInt();
				break;
			}
			case "displayName": {
				displayName = json.expectString();
				break;
			}
			case "chips": {
				chips = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("tableId");
		json.addString(tableId);

		json.addKey("preferredSeat");
		json.addNumber(preferredSeat);

		json.addKey("displayName");
		json.addString(displayName);

		json.addKey("chips");
		json.addNumber(chips);
	}

	@override
	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg) => v.visitJoinTableMsg(this, arg);

}

///  Request to leave the current table and cash out chips.
class LeaveTableMsg extends ClientMessage {
	/// Creates a LeaveTableMsg.
	LeaveTableMsg();

	/// Parses a LeaveTableMsg from a string source.
	static LeaveTableMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a LeaveTableMsg instance from the given reader.
	static LeaveTableMsg read(JsonReader json) {
		LeaveTableMsg result = LeaveTableMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "LeaveTableMsg";

	@override
	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg) => v.visitLeaveTableMsg(this, arg);

}

///  A chat message or emoji sent to the table.
class ChatMsg extends ClientMessage {
	///  The chat text content.
	String text;

	/// Creates a ChatMsg.
	ChatMsg({
			this.text = "",
	});

	/// Parses a ChatMsg from a string source.
	static ChatMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a ChatMsg instance from the given reader.
	static ChatMsg read(JsonReader json) {
		ChatMsg result = ChatMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "ChatMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "text": {
				text = json.expectString();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("text");
		json.addString(text);
	}

	@override
	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg) => v.visitChatMsg(this, arg);

}

///  Request to create a new table with configurable blinds.
class CreateTableMsg extends ClientMessage {
	///  Small blind amount. Must be positive.
	int smallBlind;

	///  Big blind amount. Must be exactly 2x smallBlind.
	int bigBlind;

	/// Creates a CreateTableMsg.
	CreateTableMsg({
			this.smallBlind = 0,
			this.bigBlind = 0,
	});

	/// Parses a CreateTableMsg from a string source.
	static CreateTableMsg? fromString(String source) {
		return read(JsonReader.fromString(source));
	}

	/// Reads a CreateTableMsg instance from the given reader.
	static CreateTableMsg read(JsonReader json) {
		CreateTableMsg result = CreateTableMsg();
		result._readContent(json);
		return result;
	}

	@override
	String _jsonType() => "CreateTableMsg";

	@override
	void _readProperty(String key, JsonReader json) {
		switch (key) {
			case "smallBlind": {
				smallBlind = json.expectInt();
				break;
			}
			case "bigBlind": {
				bigBlind = json.expectInt();
				break;
			}
			default: super._readProperty(key, json);
		}
	}

	@override
	void _writeProperties(JsonSink json) {
		super._writeProperties(json);

		json.addKey("smallBlind");
		json.addNumber(smallBlind);

		json.addKey("bigBlind");
		json.addNumber(bigBlind);
	}

	@override
	R visitClientMessage<R, A>(ClientMessageVisitor<R, A> v, A arg) => v.visitCreateTableMsg(this, arg);

}

