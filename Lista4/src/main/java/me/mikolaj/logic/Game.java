package me.mikolaj.logic;

import me.mikolaj.client.Player;

/**
 * Game representation
 */
public class Game {

	/**
	 * Singleton - there is only one game instance
	 */
	private static volatile Game gameInstance = null;

	/**
	 * Which player moves now
	 */
	private Player currentPlayer;

	/**
	 * Counter for players moves
	 */
	private int counter = 1;

	/**
	 * State of the game
	 */
	private GameState gameState = GameState.WAITING_FOR_PLAYERS;

	/**
	 * Makes a move
	 *
	 * @param player - Current player
	 */
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


	/**
	 * Gets the current player
	 *
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the current player
	 *
	 * @param currentPlayer - current player
	 */
	public void setCurrentPlayer(final Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Gets current state of the game
	 *
	 * @return current game state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Sets current state of the game
	 *
	 * @param gameState - sets current game state
	 */
	public void setGameState(final GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * Double-checked locking
	 *
	 * @return
	 */
	public static Game getGameInstance() {
		if (gameInstance == null) {
			synchronized (Game.class) {
				if (gameInstance == null)
					gameInstance = new Game();
			}
		}
		return gameInstance;
	}
}
