package me.mikolaj.logic;

import me.mikolaj.utils.Constants;

import java.awt.*;
import java.util.Arrays;

/**
 * Game representation
 */
public class Game {

	/**
	 * Singleton - there is only one game instance
	 */
	private static volatile Game gameInstance = null;

	/**
	 * Represents the game board
	 */
	private Color[][] board = new Color[17][25];

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

		nextPlayer();
	}

	public synchronized Player nextPlayer() {
		if (counter == Player.getPlayers().size())
			counter = 0;
		currentPlayer = Player.getPlayers().get(counter);
		counter++;
		return currentPlayer;
	}

	/**
	 * Fills double array board with appropriate colors
	 */
	public void fillGameBoardWithHomes() {
		final Color[][] colors = new Color[17][25];

		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.OFFSETS[i]; j++) {
				colors[i][j] = Color.white;
			}

			for (int j = Constants.OFFSETS[i]; j < Constants.OFFSETS[i] + Constants.WIDTHS[i] * 2 - 1; j += 2) {
				if (j < 25)
					colors[i][j] = Color.green;

				if (j < 24)
					colors[i][j + 1] = Color.white;

			}
			for (int j = Constants.OFFSETS[i] + Constants.WIDTHS[i] * 2 - 1; j < Constants.WIDTH; j++) {
				colors[i][j] = Color.white;
			}
		}

		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.WIDTH; j++) {
				if (colors[i][j] == Color.green) {
					if (i < 4)
						colors[i][j] = Constants.PLAYER_1_COLOR;
					if (i > 12)
						colors[i][j] = Constants.PLAYER_2_COLOR;
				}
			}
		}

		setBoard(colors);
		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.WIDTH; j++) {
				fillForComplexHomes(Constants.HOME_3, Constants.PLAYER_3_COLOR, i, j);
				fillForComplexHomes(Constants.HOME_4, Constants.PLAYER_4_COLOR, i, j);
				fillForComplexHomes(Constants.HOME_5, Constants.PLAYER_5_COLOR, i, j);
				fillForComplexHomes(Constants.HOME_6, Constants.PLAYER_6_COLOR, i, j);
			}
		}
	}

	/**
	 * Helper method for filling double array - board[][] with colors
	 *
	 * @param HOME_X - Coordinates of player's pawns
	 * @param color  - Color of player's pawns
	 * @param i      - Current x coordinate
	 * @param j      - Current y coordinate
	 */
	private void fillForComplexHomes(final Integer[][] HOME_X, final Color color, final int i, final int j) {
		for (final Integer[] x : HOME_X) {
			for (int y = 0; y < x.length - 1; y += 2) {
				if (x[y] == i && x[y + 1] == j) {
					board[i][j] = color;
					break;
				}
			}
		}
	}

	/**
	 * Checks if the game is over
	 *
	 * @param number - number of a player for which we are checking if he won
	 * @return true if there is a winner, false otherwise
	 */
	public boolean hasWinner(final int number) {
		final Color[][] board = getBoard();
		final Color playersColor = Constants.getPlayerColor(number);
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;

		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.WIDTH; j++) {
				if (board[i][j].equals(playersColor)) {
					final Integer[] t = {i, j};
					if (i < 4 && number != 1)
						count1++;
					else if (i > 12 && number != 2)
						count2++;
					else if (Arrays.stream(Constants.HOME_3).anyMatch(m -> Arrays.equals(m, t)) && number != 3)
						count3++;
					else if (Arrays.stream(Constants.HOME_4).anyMatch(m -> Arrays.equals(m, t)) && number != 4)
						count4++;
					else if (Arrays.stream(Constants.HOME_5).anyMatch(m -> Arrays.equals(m, t)) && number != 5)
						count5++;
					else if (Arrays.stream(Constants.HOME_6).anyMatch(m -> Arrays.equals(m, t)) && number != 6)
						count6++;
				}
			}
		}

		return count1 == 10 || count2 == 10 || count3 == 10 || count4 == 10 || count5 == 10 || count6 == 10;
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

	public Color[][] getBoard() {
		return board;
	}

	public void setBoard(final Color[][] board) {
		this.board = board;
	}

	/**
	 * Double-checked locking
	 *
	 * @return gameInstance
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
