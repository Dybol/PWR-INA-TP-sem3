package me.mikolaj.client;

import me.mikolaj.logic.Game;
import me.mikolaj.logic.GameState;
import me.mikolaj.utils.Constants;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a player
 */
public class Player implements Runnable {

	/**
	 * Singleton of Game class
	 */
	private static final Game gameInstance = Game.getGameInstance();

	/**
	 * List of all players
	 */
	private static final List<Player> players = new ArrayList<>();

	/**
	 * Player's number
	 */
	private int number;

	/**
	 * List of player's opponents
	 */
	private List<Player> opponents = new ArrayList<>();

	/**
	 * Socket for communication between player and server
	 */
	private final Socket socket;

	/**
	 * Scanner for reading input
	 */
	private Scanner input;

	/**
	 * PrintWriter for writing output
	 */
	private PrintWriter output;

	/**
	 * Constructor for a new player
	 *
	 * @param socket - instance of a Socket
	 * @param number - player's number
	 */
	public Player(final Socket socket, final int number) {
		this.socket = socket;
		this.number = number;
		players.add(this);
	}

	/**
	 * Repeating method for handling setup and processing commands
	 */
	@Override
	public void run() {
		try {
			setup();
			processCommands();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (opponents != null) {
				opponents.forEach(opponent -> {
					if (!opponent.equals(this))
						opponent.output.println("OTHER_PLAYER_LEFT");
				});
			}
			try {
				socket.close();
			} catch (final IOException e) {
			}
		}
	}

	/**
	 * Sets a beginning of the game
	 */
	private void setup() throws IOException {
		input = new Scanner(socket.getInputStream());
		output = new PrintWriter(socket.getOutputStream(), true);
		output.println("WELCOME " + number);
		if (number == 1)
			gameInstance.setCurrentPlayer(this);

		output.println("MESSAGE Waiting for opponent to connect");
	}

	/**
	 * A method that processed commands
	 */
	private void processCommands() {
		while (input.hasNextLine()) {

			// Handle client's command
			final String command = input.nextLine();

			if (gameInstance.getGameState() == GameState.WAITING_FOR_PLAYERS) {
				if (command.startsWith("START")) {
					final Player firstPlayer = getPlayers().get(0);
					boolean canStart = true;
					switch (getPlayers().size()) {
						case 1 -> {
							firstPlayer.output.println("MESSAGE Please wait for opponents");
							canStart = false;
						}
						case 3 -> {
							final Queue<Integer> queue = new PriorityQueue<>();
							queue.addAll(Arrays.asList(2, 3, 4));
							//we have 3 players to it cannot produce null pointer ex
							getPlayers().forEach(player -> player.setNumber(queue.poll()));
						}
						case 5 -> {
							players.forEach(player -> player.output.println("MESSAGE You cannot start the game as 5!"));
							canStart = false;
						}
					}
					if (!canStart)
						continue;

					gameInstance.fillGameBoardWithHomes();
					gameInstance.setGameState(GameState.STARTED);

					firstPlayer.output.println("MESSAGE Your move");
					for (final Player player : getPlayers()) {
						player.setOpponents(getPlayers().stream().filter(p -> !p.equals(player)).collect(Collectors.toList()));
					}
					firstPlayer.getOpponents().forEach(opponent -> opponent.output.println("MESSAGE Please wait for your move"));
				}
				continue;
			}

			if (command.startsWith("QUIT")) {
				return;

			} else if (command.startsWith("MOVE")) {
				//splitting into prevX, prevY, X, Y
				final String[] locations = command.split(" ");
				processMoveCommand(Integer.parseInt(locations[1]),
						Integer.parseInt(locations[2]),
						Integer.parseInt(locations[3]),
						Integer.parseInt(locations[4]),
						new Color(Integer.parseInt(locations[5])));
			} else if (command.startsWith("WAIT")) {
				if (gameInstance.getCurrentPlayer().equals(this)) {
					final Player player = gameInstance.nextPlayer();
					output.println("MESSAGE You have decided not to move in this turn");
					player.output.println("MESSAGE Opponent decided not to move in this turn, so it is your turn now");
				}
			}
		}
	}

	/**
	 * Processing move command - getting the right location
	 *
	 * @param previousLocationX - X coordinate of a previous location
	 * @param previousLocationY - Y coordinate of a previous location
	 * @param locationX         - X coordinate of a new location
	 * @param locationY         - Y coordinate of a new location
	 * @param color             - color of a moved square
	 */
	private void processMoveCommand(final int previousLocationX, final int previousLocationY,
									final int locationX, final int locationY, final Color color) {
		try {
			if (!color.equals(Constants.getPlayerColor(number))) {
				output.println("MESSAGE You cannot move with not your color!");
				return;
			}

			final Color colorAtGivenLocation = gameInstance.getBoard()[locationX][locationY];


			//checking move to adjacent squares
			final boolean valid = Math.abs(previousLocationX - locationX) == 1 && Math.abs(previousLocationY - locationY) == 1
					&& colorAtGivenLocation.equals(Color.green);


			//checking move to further squares
			final boolean valid2 = complexMove(previousLocationX, previousLocationY, locationX, locationY, colorAtGivenLocation);

			if (!valid && !valid2 || (!checkIfPawnIsInOtherHome(previousLocationX, previousLocationY, locationX, locationY))) {
				output.println("MESSAGE This move is not valid!");
				return;
			}

			gameInstance.move(this);
			gameInstance.getBoard()[locationX][locationY] = color;
			gameInstance.getBoard()[previousLocationX][previousLocationY] = Color.green;
			output.println("VALID_MOVE " + number);
			opponents.forEach(opponent -> opponent.output.println("OPPONENT_MOVED " + previousLocationX + " "
					+ previousLocationY + " " + locationX + " " + locationY + " "
					+ gameInstance.getCurrentPlayer().getNumber() + " " + players.size()));

			if (hasWinner(number)) {
				output.println("VICTORY");
				opponents.forEach(opponent -> opponent.output.println("DEFEAT"));
			}

		} catch (final IllegalStateException e) {
			output.println("MESSAGE " + e.getMessage());
		}
	}

	/**
	 * Validates move
	 *
	 * @param previousLocationX    - X coordinate of a previous location
	 * @param previousLocationY    - Y coordinate of a previous location
	 * @param locationX            - X coordinate of a new location
	 * @param locationY            - Y coordinate of a new location
	 * @param colorAtGivenLocation - Color of a square at new Location
	 * @return true if move is valid, false otherwise
	 */
	private boolean complexMove(final int previousLocationX, final int previousLocationY,
								final int locationX, final int locationY,
								final Color colorAtGivenLocation) {
		if (!colorAtGivenLocation.equals(Color.green)) {
			return false;
		}

		if (previousLocationX == locationX && previousLocationY == locationY) {
			return true;
		}


		if (locationX - previousLocationX > 1 && locationY - previousLocationY > 1) {
			final int tempXLoc = previousLocationX + 1;
			final int tempYLoc = previousLocationY + 1;
			final Color tempColor = gameInstance.getBoard()[tempXLoc][tempYLoc];

			if (!tempColor.equals(Color.green) && !tempColor.equals(Color.white)) {
				return complexMove(previousLocationX + 2, previousLocationY + 2, locationX, locationY, colorAtGivenLocation);
			} else {
				return false;
			}
		} else if (locationX - previousLocationX > 1 && locationY - previousLocationY < -1) {
			final int tempXLoc = previousLocationX + 1;
			final int tempYLoc = previousLocationY - 1;
			final Color tempColor = gameInstance.getBoard()[tempXLoc][tempYLoc];

			if (!tempColor.equals(Color.green) && !tempColor.equals(Color.white)) {
				return complexMove(previousLocationX + 2, previousLocationY - 2, locationX, locationY, colorAtGivenLocation);
			} else {
				return false;
			}
		} else if (locationX - previousLocationX < -1 && locationY - previousLocationY < -1) {
			final int tempXLoc = previousLocationX - 1;
			final int tempYLoc = previousLocationY - 1;
			final Color tempColor = gameInstance.getBoard()[tempXLoc][tempYLoc];

			if (!tempColor.equals(Color.green) && !tempColor.equals(Color.white)) {
				return complexMove(previousLocationX - 2, previousLocationY - 2, locationX, locationY, colorAtGivenLocation);
			} else {
				return false;
			}
		} else if (locationX - previousLocationX < -1 && locationY - previousLocationY > 1) {
			final int tempXLoc = previousLocationX - 1;
			final int tempYLoc = previousLocationY + 1;
			final Color tempColor = gameInstance.getBoard()[tempXLoc][tempYLoc];

			if (!tempColor.equals(Color.green) && !tempColor.equals(Color.white)) {
				return complexMove(previousLocationX - 2, previousLocationY + 2, locationX, locationY, colorAtGivenLocation);
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Checks if a player doesn't want to leave other home - he cannot do that
	 *
	 * @param previousLocationX - previous X location
	 * @param previousLocationY - previous Y location
	 * @param locationX         - current X location
	 * @param locationY         - current Y location
	 * @return true if the move is valid, false otherwise
	 */
	private boolean checkIfPawnIsInOtherHome(final int previousLocationX, final int previousLocationY,
											 final int locationX, final int locationY) {
		//for player 1
		if (previousLocationX < 4) {
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_1_COLOR) && locationX >= 4) {
				return false;
			}
			//for player 2
		} else if (previousLocationX > 12) {
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_2_COLOR) && locationX <= 12) {
				return false;
			}
		} else {
			//for other players
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_3_COLOR))
				if (!checkForComplexHomes(Constants.HOME_3, previousLocationX, previousLocationY, locationX, locationY))
					return false;
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_4_COLOR))
				if (!checkForComplexHomes(Constants.HOME_4, previousLocationX, previousLocationY, locationX, locationY))
					return false;
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_5_COLOR))
				if (!checkForComplexHomes(Constants.HOME_5, previousLocationX, previousLocationY, locationX, locationY))
					return false;
			if (!Constants.getPlayerColor(this.number).equals(Constants.PLAYER_6_COLOR))
				if (!checkForComplexHomes(Constants.HOME_6, previousLocationX, previousLocationY, locationX, locationY))
					return false;
		}
		return true;
	}

	/**
	 * @param HOME_X   - Coordinates of player's pawns
	 * @param prevLocX - previous X location
	 * @param prevLocY - previous Y location
	 * @param locX     - target X location
	 * @param locY     - target Y location
	 * @return if this move is okay
	 */
	private boolean checkForComplexHomes(final Integer[][] HOME_X, final int prevLocX,
										 final int prevLocY, final int locX, final int locY) {
		boolean flag1 = false;
		boolean flag2 = false;
		for (final Integer[] x : HOME_X) {
			for (int y = 0; y < x.length - 1; y += 2) {
				if (x[y] == prevLocX && x[y + 1] == prevLocY) {
					flag1 = true;
				}
				if (x[y] == locX && x[y + 1] == locY) {
					flag2 = true;
				}
			}
		}
		//flag1= true && flag2 = false -> player tries to leave other home
		return !flag1 || flag2;
	}

	/**
	 * Checks if the game is over
	 *
	 * @param number - number of a player for which we are checking if he won
	 * @return true if there is a winner, false otherwise
	 */
	private boolean hasWinner(final int number) {
		final Color[][] board = gameInstance.getBoard();
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
	 * Gets player's number
	 *
	 * @return player's number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets player's number
	 *
	 * @param number player's number
	 */
	public void setNumber(final int number) {
		this.number = number;
	}

	/**
	 * Gets player's opponents
	 *
	 * @return player's opponents
	 */
	public List<Player> getOpponents() {
		return opponents;
	}

	/**
	 * Sets player's opponents
	 *
	 * @param opponents - List of the player's opponents
	 */
	public void setOpponents(final List<Player> opponents) {
		this.opponents = opponents;
	}

	/**
	 * Gets all players
	 *
	 * @return all players
	 */
	public static List<Player> getPlayers() {
		return players;
	}

	@Override
	public String toString() {
		return "Player{" +
				"number=" + number +
				'}';
	}
}

