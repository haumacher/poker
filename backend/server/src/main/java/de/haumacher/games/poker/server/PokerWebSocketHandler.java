package de.haumacher.games.poker.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import de.haumacher.games.poker.model.ChatMsg;
import de.haumacher.games.poker.model.ClientMessage;
import de.haumacher.games.poker.model.ConfirmResultMsg;
import de.haumacher.games.poker.model.CreateTableMsg;
import de.haumacher.games.poker.model.JoinTableMsg;
import de.haumacher.games.poker.model.LeaveTableMsg;
import de.haumacher.games.poker.model.PlayerActionMsg;
import de.haumacher.games.poker.model.TableInfoMsg;
import de.haumacher.msgbuf.io.StringR;
import de.haumacher.msgbuf.json.JsonReader;

@Component
public class PokerWebSocketHandler extends TextWebSocketHandler {

	private static final Logger LOG = LoggerFactory.getLogger(PokerWebSocketHandler.class);

	private static final long DEFAULT_BUY_IN = 1000;
	private static final long DEFAULT_SMALL_BLIND = 5;
	private static final long DEFAULT_BIG_BLIND = 10;
	private static final int DEFAULT_TURN_TIMEOUT_SECONDS = 30;

	private final TableManager tableManager;
	private final Map<String, PlayerConnection> connections = new ConcurrentHashMap<>();

	public PokerWebSocketHandler(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		PlayerConnection conn = new PlayerConnection(session);
		connections.put(session.getId(), conn);
		LOG.info("WebSocket connected: {}", session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		PlayerConnection conn = connections.get(session.getId());
		if (conn == null) {
			return;
		}

		ClientMessage clientMsg;
		try {
			clientMsg = ClientMessage.readClientMessage(new JsonReader(new StringR(message.getPayload())));
		} catch (IOException e) {
			LOG.warn("Invalid message from {}: {}", session.getId(), e.getMessage());
			tableManager.sendError(session, "INVALID_MESSAGE", "Could not parse message");
			return;
		}

		if (clientMsg == null) {
			tableManager.sendError(session, "UNKNOWN_TYPE", "Unknown message type");
			return;
		}

		clientMsg.visit(new ClientMessage.Visitor<Void, PlayerConnection, RuntimeException>() {
			@Override
			public Void visit(JoinTableMsg self, PlayerConnection c) {
				handleJoin(c, self);
				return null;
			}

			@Override
			public Void visit(LeaveTableMsg self, PlayerConnection c) {
				tableManager.leaveTable(c);
				return null;
			}

			@Override
			public Void visit(PlayerActionMsg self, PlayerConnection c) {
				tableManager.handlePlayerAction(c, self.getActionType(), self.getAmount());
				return null;
			}

			@Override
			public Void visit(ChatMsg self, PlayerConnection c) {
				// Chat not implemented yet
				return null;
			}

			@Override
			public Void visit(ConfirmResultMsg self, PlayerConnection c) {
				tableManager.handleConfirmResult(c);
				return null;
			}

			@Override
			public Void visit(CreateTableMsg self, PlayerConnection c) {
				handleCreateTable(c, self);
				return null;
			}
		}, conn);
	}

	private void handleJoin(PlayerConnection conn, JoinTableMsg msg) {
		if (conn.isSeated()) {
			tableManager.sendError(conn.getSession(), "ALREADY_SEATED", "Leave your current table first");
			return;
		}

		String tableId = msg.getTableId();
		if (tableId == null || tableId.isEmpty()) {
			tableId = tableManager.createTable(DEFAULT_SMALL_BLIND, DEFAULT_BIG_BLIND, DEFAULT_TURN_TIMEOUT_SECONDS);
		} else if (tableId.length() == 6) {
			String resolved = tableManager.resolveRoomCode(tableId);
			if (resolved != null) {
				tableId = resolved;
			}
		}

		String displayName = msg.getDisplayName();
		if (displayName == null || displayName.isEmpty()) {
			displayName = conn.getSession().getId().substring(0, 8);
		}

		long chips = msg.getChips();
		if (chips <= 0) {
			chips = DEFAULT_BUY_IN;
		}

		int seat = tableManager.joinTable(tableId, conn, displayName, chips, msg.getPreferredSeat());
		if (seat < 0) {
			tableManager.sendError(conn.getSession(), "JOIN_FAILED", "Could not join table");
		} else {
			TableInfoMsg info = TableInfoMsg.create()
					.setTableId(tableId)
					.setRoomCode(tableManager.getRoomCode(tableId))
					.setSeat(seat)
					.setSmallBlind(tableManager.getSmallBlind(tableId))
					.setBigBlind(tableManager.getBigBlind(tableId))
					.setTurnTimeoutSeconds(tableManager.getTurnTimeoutSeconds(tableId));
			tableManager.sendMessage(conn.getSession(), info);
		}
	}

	private void handleCreateTable(PlayerConnection conn, CreateTableMsg msg) {
		long smallBlind = msg.getSmallBlind();
		long bigBlind = msg.getBigBlind();

		if (smallBlind <= 0) {
			smallBlind = DEFAULT_SMALL_BLIND;
			bigBlind = DEFAULT_BIG_BLIND;
		} else if (bigBlind != 2 * smallBlind) {
			tableManager.sendError(conn.getSession(), "INVALID_BLINDS", "Big blind must be exactly 2x small blind");
			return;
		}

		// turnTimeoutSeconds: negative = no timeout, 0 = use default (backward compat), positive = explicit
		int turnTimeoutSeconds = msg.getTurnTimeoutSeconds();
		if (turnTimeoutSeconds < 0) {
			turnTimeoutSeconds = 0; // 0 internally means no timeout
		} else if (turnTimeoutSeconds == 0) {
			turnTimeoutSeconds = DEFAULT_TURN_TIMEOUT_SECONDS;
		}

		String tableId = tableManager.createTable(smallBlind, bigBlind, turnTimeoutSeconds);

		TableInfoMsg info = TableInfoMsg.create()
				.setTableId(tableId)
				.setRoomCode(tableManager.getRoomCode(tableId))
				.setSeat(-1)
				.setSmallBlind(smallBlind)
				.setBigBlind(bigBlind)
				.setTurnTimeoutSeconds(turnTimeoutSeconds);
		tableManager.sendMessage(conn.getSession(), info);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		PlayerConnection conn = connections.remove(session.getId());
		if (conn != null && conn.isSeated()) {
			tableManager.leaveTable(conn);
		}
		LOG.info("WebSocket disconnected: {} ({})", session.getId(), status);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		LOG.warn("Transport error for session {}: {}", session.getId(), exception.getMessage());
	}
}
