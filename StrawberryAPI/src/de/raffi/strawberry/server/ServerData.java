package de.raffi.strawberry.server;

public class ServerData {
	
	
	private String MOTD;
	private int playerCount;
	private int maximumPlayers;
	public ServerData(String mOTD, int playerCount, int maximumPlayers) {
		MOTD = mOTD;
		this.playerCount = playerCount;
		this.maximumPlayers = maximumPlayers;
	}
	public String getMOTD() {
		return MOTD;
	}
	public int getPlayerCount() {
		return playerCount;
	}
	public int getMaximumPlayers() {
		return maximumPlayers;
	}
	
	
	
	
	
}
