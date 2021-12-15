package me.mikolaj.logic;

import me.mikolaj.client.Player;

public class Game {

	//singleton
	private static final Game gameInstance = new Game();

	// Board cells numbered [vertically] [horizontally]
	private final Player[][] board = new Player[17][25];

	private Player currentPlayer;
	private final int counter = 0;

	// Metoda obslugujaca ruch
	public synchronized void move(final int locationX, final int locationY, final Player player) {
		if (player != currentPlayer) {
			throw new IllegalStateException("Not your turn");
		} else if (player.getOpponents() == null || player.getOpponents().size() == 0) {
			throw new IllegalStateException("You don't have an opponent yet");
		} else if (board[locationX][locationY] != null) {
			throw new IllegalStateException("Cell already occupied");
		}

		board[locationX][locationY] = currentPlayer;
		//TODO: Hardcoded for two players
		currentPlayer = player.getOpponents().get(0);
//		counter++;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(final Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public static Game getGameInstance() {
		return gameInstance;
	}
}
