package de.haumacher.games.poker.server;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import de.haumacher.games.poker.engine.GameListener;
import de.haumacher.games.poker.engine.GameSession;
import de.haumacher.games.poker.engine.PlayerSession;
import de.haumacher.games.poker.engine.Pot;
import de.haumacher.games.poker.model.ActionType;
import de.haumacher.games.poker.model.Card;
import de.haumacher.games.poker.model.ErrorMsg;
import de.haumacher.games.poker.model.GameStateMsg;
import de.haumacher.games.poker.model.HandResultMsg;
import de.haumacher.games.poker.model.HoleCardsMsg;
import de.haumacher.games.poker.model.Phase;
import de.haumacher.games.poker.model.PlayerJoinedMsg;
import de.haumacher.games.poker.model.PlayerLeftMsg;
import de.haumacher.games.poker.model.PlayerState;
import de.haumacher.games.poker.model.ServerMessage;
import de.haumacher.games.poker.model.SidePot;
import de.haumacher.games.poker.model.WinnerInfo;
import de.haumacher.msgbuf.io.StringR;
import de.haumacher.msgbuf.io.StringW;
import de.haumacher.msgbuf.json.JsonWriter;

import jakarta.annotation.PreDestroy;

@Component
public class TableManager {

	private static final Logger LOG = LoggerFactory.getLogger(TableManager.class);

	private static final String ROOM_CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

	private final Map<String, TableContext> tables = new ConcurrentHashMap<>();
	private final Map<String, String> roomCodeToTableId = new ConcurrentHashMap<>();
	private final SecureRandom random = new SecureRandom();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
		Thread t = new Thread(r, "turn-timer");
		t.setDaemon(true);
		return t;
	});

	@PreDestroy
	public void shutdown() {
		scheduler.shutdownNow();
	}

	public String createTable(long smallBlind, long bigBlind, int turnTimeoutSeconds) {
		String tableId = java.util.UUID.randomUUID().toString();
		String roomCode = generateRoomCode();

		GameSession gameSession = new GameSession(smallBlind, bigBlind, random);
		TableContext ctx = new TableContext(tableId, roomCode, gameSession, turnTimeoutSeconds);
		tables.put(tableId, ctx);
		roomCodeToTableId.put(roomCode, tableId);

		LOG.info("Table created: {} (room code: {}, timeout: {}s)", tableId, roomCode, turnTimeoutSeconds);
		return tableId;
	}

	public String getRoomCode(String tableId) {
		TableContext ctx = tables.get(tableId);
		return ctx != null ? ctx.roomCode : null;
	}

	public String resolveRoomCode(String roomCode) {
		return roomCodeToTableId.get(roomCode.toUpperCase());
	}

	public long getSmallBlind(String tableId) {
		TableContext ctx = tables.get(tableId);
		return ctx != null ? ctx.gameSession.getSmallBlind() : 0;
	}

	public long getBigBlind(String tableId) {
		TableContext ctx = tables.get(tableId);
		return ctx != null ? ctx.gameSession.getBigBlind() : 0;
	}

	public int getTurnTimeoutSeconds(String tableId) {
		TableContext ctx = tables.get(tableId);
		return ctx != null ? ctx.turnTimeoutSeconds : 0;
	}

	public int joinTable(String tableId, PlayerConnection connection, String displayName, long chips, int preferredSeat) {
		TableContext ctx = tables.get(tableId);
		if (ctx == null) {
			return -1;
		}

		synchronized (ctx) {
			int seat = preferredSeat;
			if (seat < 0 || seat >= GameSession.MAX_SEATS || ctx.gameSession.getPlayer(seat) != null) {
				seat = findOpenSeat(ctx.gameSession);
				if (seat < 0) {
					return -1;
				}
			}

			if (!ctx.gameSession.addPlayer(seat, displayName, chips)) {
				return -1;
			}

			connection.setTableId(tableId);
			connection.setSeat(seat);
			connection.setDisplayName(displayName);
			ctx.connections.put(connection.getSession().getId(), connection);

			setupListenerIfNeeded(ctx);

			PlayerState ps = buildPlayerState(ctx.gameSession.getPlayer(seat));
			PlayerJoinedMsg joinMsg = PlayerJoinedMsg.create().setPlayerState(ps);
			broadcastToTable(ctx, joinMsg);

			sendMessage(connection.getSession(), buildGameStateMsg(ctx));

			if (ctx.gameSession.getPhase() == Phase.WAITING_FOR_PLAYERS) {
				tryStartHand(ctx);
			}

			return seat;
		}
	}

	public void leaveTable(PlayerConnection connection) {
		String tableId = connection.getTableId();
		if (tableId == null) {
			return;
		}

		TableContext ctx = tables.get(tableId);
		if (ctx == null) {
			return;
		}

		synchronized (ctx) {
			int seat = connection.getSeat();
			ctx.gameSession.removePlayer(seat);
			ctx.connections.remove(connection.getSession().getId());
			ctx.pendingConfirmations.remove(connection.getSession().getId());

			connection.setTableId(null);
			connection.setSeat(-1);

			PlayerLeftMsg leftMsg = PlayerLeftMsg.create().setSeat(seat);
			broadcastToTable(ctx, leftMsg);

			broadcastGameState(ctx);

			if (ctx.connections.isEmpty()) {
				destroyTable(ctx);
			} else if (ctx.pendingConfirmations.isEmpty()
					&& ctx.gameSession.getPhase() == Phase.WAITING_FOR_PLAYERS) {
				tryStartHand(ctx);
			}
		}
	}

	public void handlePlayerAction(PlayerConnection connection, ActionType actionType, long amount) {
		String tableId = connection.getTableId();
		if (tableId == null) {
			sendError(connection.getSession(), "NOT_AT_TABLE", "You are not seated at a table");
			return;
		}

		TableContext ctx = tables.get(tableId);
		if (ctx == null) {
			return;
		}

		synchronized (ctx) {
			boolean valid = ctx.gameSession.handleAction(connection.getSeat(), actionType, amount);
			if (!valid) {
				sendError(connection.getSession(), "INVALID_ACTION", "Action not allowed");
			}
		}
	}

	private void setupListenerIfNeeded(TableContext ctx) {
		if (ctx.listenerSet) {
			return;
		}
		ctx.listenerSet = true;
		ctx.gameSession.setListener(new GameListener() {
			@Override
			public void onHoleCardsDealt(int seat, List<Card> cards) {
				PlayerConnection conn = findConnectionBySeat(ctx, seat);
				if (conn != null) {
					HoleCardsMsg msg = HoleCardsMsg.create()
							.setHandNumber(ctx.gameSession.getHandNumber())
							.setCards(cards);
					sendMessage(conn.getSession(), msg);
				}
			}

			@Override
			public void onPhaseChanged(GameSession session) {
				cancelTurnTimer(ctx);
				broadcastGameState(ctx);
				scheduleTurnTimer(ctx);
			}

			@Override
			public void onPlayerAction(int seat, ActionType action, long amount) {
				cancelTurnTimer(ctx);
				broadcastGameState(ctx);
				if (ctx.gameSession.getPhase() != Phase.WAITING_FOR_PLAYERS
						&& ctx.gameSession.getPhase() != Phase.SHOWDOWN) {
					scheduleTurnTimer(ctx);
				}
			}

			@Override
			public void onShowdown(List<GameSession.WinResult> winners) {
				cancelTurnTimer(ctx);
				HandResultMsg resultMsg = HandResultMsg.create();
				for (GameSession.WinResult wr : winners) {
					resultMsg.addWinner(WinnerInfo.create()
							.setSeat(wr.seat())
							.setAmount(wr.amount())
							.setHandDescription(wr.handDescription()));
				}
				broadcastToTable(ctx, resultMsg);
				broadcastGameState(ctx);
				awaitConfirmations(ctx);
			}

			@Override
			public void onWinWithoutShowdown(int seat, long amount) {
				cancelTurnTimer(ctx);
				HandResultMsg resultMsg = HandResultMsg.create();
				resultMsg.addWinner(WinnerInfo.create()
						.setSeat(seat)
						.setAmount(amount)
						.setHandDescription("Last player standing"));
				broadcastToTable(ctx, resultMsg);
				broadcastGameState(ctx);
				awaitConfirmations(ctx);
			}

			@Override
			public void onTurnTimeout(int seat) {
				broadcastGameState(ctx);
			}
		});
	}

	private void scheduleTurnTimer(TableContext ctx) {
		if (ctx.turnTimeoutSeconds <= 0) {
			return;
		}
		ctx.turnStartedAtMillis = System.currentTimeMillis();
		ctx.turnTimerFuture = scheduler.schedule(() -> {
			synchronized (ctx) {
				ctx.gameSession.handleTimeout();
			}
		}, ctx.turnTimeoutSeconds, TimeUnit.SECONDS);
	}

	private void cancelTurnTimer(TableContext ctx) {
		ctx.turnStartedAtMillis = 0;
		if (ctx.turnTimerFuture != null) {
			ctx.turnTimerFuture.cancel(false);
			ctx.turnTimerFuture = null;
		}
	}

	public void handleConfirmResult(PlayerConnection connection) {
		String tableId = connection.getTableId();
		if (tableId == null) return;

		TableContext ctx = tables.get(tableId);
		if (ctx == null) return;

		synchronized (ctx) {
			ctx.pendingConfirmations.remove(connection.getSession().getId());
			if (ctx.pendingConfirmations.isEmpty()) {
				tryStartHand(ctx);
			}
		}
	}

	private void awaitConfirmations(TableContext ctx) {
		ctx.pendingConfirmations.clear();
		for (PlayerConnection conn : ctx.connections.values()) {
			ctx.pendingConfirmations.add(conn.getSession().getId());
		}
	}

	private void tryStartHand(TableContext ctx) {
		if (ctx.gameSession.startHand()) {
			LOG.info("Hand #{} started on table {}", ctx.gameSession.getHandNumber(), ctx.tableId);
		}
	}

	private void destroyTable(TableContext ctx) {
		cancelTurnTimer(ctx);
		tables.remove(ctx.tableId);
		roomCodeToTableId.remove(ctx.roomCode);
		LOG.info("Table destroyed: {} (room code: {})", ctx.tableId, ctx.roomCode);
	}

	private int findOpenSeat(GameSession session) {
		for (int i = 0; i < GameSession.MAX_SEATS; i++) {
			if (session.getPlayer(i) == null) {
				return i;
			}
		}
		return -1;
	}

	private PlayerConnection findConnectionBySeat(TableContext ctx, int seat) {
		for (PlayerConnection conn : ctx.connections.values()) {
			if (conn.getSeat() == seat) {
				return conn;
			}
		}
		return null;
	}

	void broadcastGameState(TableContext ctx) {
		GameStateMsg stateMsg = buildGameStateMsg(ctx);
		broadcastToTable(ctx, stateMsg);
	}

	void broadcastToTable(TableContext ctx, ServerMessage msg) {
		for (PlayerConnection conn : ctx.connections.values()) {
			sendMessage(conn.getSession(), msg);
		}
	}

	void sendMessage(WebSocketSession session, ServerMessage msg) {
		try {
			StringW sw = new StringW();
			msg.writeTo(new JsonWriter(sw));
			session.sendMessage(new TextMessage(sw.toString()));
		} catch (IOException e) {
			LOG.warn("Failed to send message to session {}: {}", session.getId(), e.getMessage());
		}
	}

	void sendError(WebSocketSession session, String code, String message) {
		sendMessage(session, ErrorMsg.create().setCode(code).setMessage(message));
	}

	GameStateMsg buildGameStateMsg(TableContext ctx) {
		GameSession gs = ctx.gameSession;
		GameStateMsg msg = GameStateMsg.create()
				.setTableId(ctx.tableId)
				.setHandNumber(gs.getHandNumber())
				.setPhase(gs.getPhase())
				.setPot(gs.getPotTotal())
				.setCurrentPlayerSeat(gs.getCurrentPlayerSeat())
				.setDealerSeat(gs.getDealerSeat())
				.setMinRaise(gs.getMinRaise())
				.setCommunityCards(gs.getCommunityCards());

		for (Pot.PotInfo pi : gs.getSidePots()) {
			SidePot sp = SidePot.create().setAmount(pi.amount());
			for (int s : pi.eligibleSeats()) {
				sp.addEligibleSeat(s);
			}
			msg.addSidePot(sp);
		}

		if (ctx.turnStartedAtMillis > 0 && ctx.turnTimeoutSeconds > 0) {
			long elapsed = (System.currentTimeMillis() - ctx.turnStartedAtMillis) / 1000;
			int remaining = (int) Math.max(0, ctx.turnTimeoutSeconds - elapsed);
			msg.setTurnTimeRemaining(remaining);
		}

		for (int i = 0; i < GameSession.MAX_SEATS; i++) {
			PlayerSession ps = gs.getPlayer(i);
			if (ps != null) {
				msg.addPlayer(buildPlayerState(ps));
			}
		}

		return msg;
	}

	private PlayerState buildPlayerState(PlayerSession ps) {
		PlayerState state = PlayerState.create()
				.setSeat(ps.getSeat())
				.setDisplayName(ps.getDisplayName())
				.setChips(ps.getChips())
				.setCurrentBet(ps.getCurrentBet())
				.setStatus(ps.getStatus());
		if (ps.getLastAction() != null) {
			state.setLastAction(ps.getLastAction());
		}
		return state;
	}

	private String generateRoomCode() {
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(ROOM_CODE_CHARS.charAt(random.nextInt(ROOM_CODE_CHARS.length())));
		}
		String code = sb.toString();
		if (roomCodeToTableId.containsKey(code)) {
			return generateRoomCode();
		}
		return code;
	}

	static class TableContext {
		final String tableId;
		final String roomCode;
		final GameSession gameSession;
		final int turnTimeoutSeconds;
		final Map<String, PlayerConnection> connections = new ConcurrentHashMap<>();
		volatile ScheduledFuture<?> turnTimerFuture;
		volatile long turnStartedAtMillis;
		boolean listenerSet;
		final Set<String> pendingConfirmations = new HashSet<>();

		TableContext(String tableId, String roomCode, GameSession gameSession, int turnTimeoutSeconds) {
			this.tableId = tableId;
			this.roomCode = roomCode;
			this.gameSession = gameSession;
			this.turnTimeoutSeconds = turnTimeoutSeconds;
		}
	}
}
