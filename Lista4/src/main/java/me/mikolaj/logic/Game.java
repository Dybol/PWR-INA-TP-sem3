package me.mikolaj.logic;

import me.mikolaj.client.Player;

public class Game {

	//singleton
	private static final Game gameInstance = new Game();

	private Player currentPlayer;
	private int counter = 1;

	private GameState gameState = GameState.WAITING_FOR_PLAYERS;

	// Metoda obslugujaca ruch
	public synchronized void move(final Player player) {
		if (player != currentPlayer) {
			throw new IllegalStateException("Not your turn");
		} else if (player.getOpponents() == null || player.getOpponents().size() == 0) {
			throw new IllegalStateException("You don't have an opponent yet");
		}

		if (counter == Player.getPlayers().size())
			counter = 0;
		currentPlayer = Player.getPlayers().get(counter);
		counter++;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(final Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(final GameState gameState) {
		this.gameState = gameState;
	}

	public static Game getGameInstance() {
		return gameInstance;
	}
}
