package me.mikolaj.client;

import me.mikolaj.logic.Game;
import me.mikolaj.logic.GameState;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	private final int number;

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
					gameInstance.setGameState(GameState.STARTED);
					final Player firstPlayer = getPlayers().get(0);
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
				System.out.println("MOVE : " + locations[1] + " " + locations[2] + " " + locations[3] + " " + locations[4]);
				processMoveCommand(Integer.parseInt(locations[1]),
						Integer.parseInt(locations[2]),
						Integer.parseInt(locations[3]),
						Integer.parseInt(locations[4]));
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
	 */
	private void processMoveCommand(final int previousLocationX, final int previousLocationY,
									final int locationX, final int locationY) {
		try {
			gameInstance.move(this);
			output.println("VALID_MOVE");
			opponents.forEach(opponent -> opponent.output.println("OPPONENT_MOVED " + previousLocationX + " "
					+ previousLocationY + " " + locationX + " " + locationY + " "
					+ gameInstance.getCurrentPlayer().getNumber() + " " + players.size()));

			//TODO: implement logic - victory, tie etc.
		} catch (final IllegalStateException e) {
			output.println("MESSAGE " + e.getMessage());
		}
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
}

