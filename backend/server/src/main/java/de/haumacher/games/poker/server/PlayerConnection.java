package de.haumacher.games.poker.server;

import org.springframework.web.socket.WebSocketSession;

public class PlayerConnection {

	private final WebSocketSession session;
	private String tableId;
	private int seat = -1;
	private String displayName;

	public PlayerConnection(WebSocketSession session) {
		this.session = session;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isSeated() {
		return tableId != null && seat >= 0;
	}
}
