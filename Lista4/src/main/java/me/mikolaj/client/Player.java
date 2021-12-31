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
			//TODO: Handle player leaves
			if (opponents != null) {

			}
			try {
				socket.close();
			} catch (final IOException e) {
			}
		}
	}

	/**
	 * Sets a beginning of the game
	 *
	 * @throws IOException
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
						case 1:
							firstPlayer.output.println("MESSAGE Please wait for opponents");
							canStart = false;
							break;
						case 3:
							final Queue<Integer> queue = new PriorityQueue<>();
							queue.addAll(Arrays.asList(2, 3, 4));
							//we have 3 players to it cannot produce null pointer ex
							getPlayers().forEach(player -> player.setNumber(queue.poll()));
						case 5:
							players.forEach(player -> player.output.println("MESSAGE You cannot start the game as 5!"));
							canStart = false;
							break;
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
			}
		}
	}


// Obsluga komendy po zaznaczeniu czegos

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

			if (!valid && !valid2) {
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

			//TODO: implement logic - victory, tie etc.
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

