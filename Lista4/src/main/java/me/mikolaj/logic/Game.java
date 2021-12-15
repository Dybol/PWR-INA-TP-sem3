package me.mikolaj.logic;

import me.mikolaj.client.Player;

import java.util.Arrays;
import java.util.Objects;

public class Game {

	//singleton
	private static final Game gameInstance = new Game();

	// Board cells numbered 0-8, top to bottom, left to right; null if empty
	private final Player[][] board = new Player[17][25];

	private Player currentPlayer;
	private final int counter = 0;

	// Kiedy zwyciestwo
	public boolean hasWinner() {
		return (board[0] != null && board[0] == board[1] && board[0] == board[2])
				|| (board[3] != null && board[3] == board[4] && board[3] == board[5])
				|| (board[6] != null && board[6] == board[7] && board[6] == board[8])
				|| (board[0] != null && board[0] == board[3] && board[0] == board[6])
				|| (board[1] != null && board[1] == board[4] && board[1] == board[7])
				|| (board[2] != null && board[2] == board[5] && board[2] == board[8])
				|| (board[0] != null && board[0] == board[4] && board[0] == board[8])
				|| (board[2] != null && board[2] == board[4] && board[2] == board[6]);
	}

	public boolean boardFilledUp() {
		return Arrays.stream(board).allMatch(Objects::nonNull);
	}

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
