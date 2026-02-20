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
import de.haumacher.games.poker.model.JoinTableMsg;
import de.haumacher.games.poker.model.LeaveTableMsg;
import de.haumacher.games.poker.model.PlayerActionMsg;
import de.haumacher.msgbuf.io.StringR;
import de.haumacher.msgbuf.json.JsonReader;

@Component
public class PokerWebSocketHandler extends TextWebSocketHandler {

	private static final Logger LOG = LoggerFactory.getLogger(PokerWebSocketHandler.class);

	private static final long DEFAULT_BUY_IN = 1000;
	private static final long DEFAULT_SMALL_BLIND = 5;
	private static final long DEFAULT_BIG_BLIND = 10;

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
		}, conn);
	}

	private void handleJoin(PlayerConnection conn, JoinTableMsg msg) {
		if (conn.isSeated()) {
			tableManager.sendError(conn.getSession(), "ALREADY_SEATED", "Leave your current table first");
			return;
		}

		String tableId = msg.getTableId();
		if (tableId == null || tableId.isEmpty()) {
			tableId = tableManager.createTable(DEFAULT_SMALL_BLIND, DEFAULT_BIG_BLIND);
		} else if (tableId.length() == 6) {
			String resolved = tableManager.resolveRoomCode(tableId);
			if (resolved != null) {
				tableId = resolved;
			}
		}

		String displayName = conn.getSession().getId().substring(0, 8);
		boolean joined = tableManager.joinTable(tableId, conn, displayName, DEFAULT_BUY_IN, msg.getPreferredSeat());
		if (!joined) {
			tableManager.sendError(conn.getSession(), "JOIN_FAILED", "Could not join table");
		}
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
