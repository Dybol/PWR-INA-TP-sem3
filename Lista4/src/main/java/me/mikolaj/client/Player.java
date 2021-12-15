package me.mikolaj.client;

import me.mikolaj.logic.Game;
import me.mikolaj.utils.Constants;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Runnable {

	private static final Game gameInstance = Game.getGameInstance();
	private static final List<Player> players = new ArrayList<>();

	private int number;
	private Color color;
	private List<Player> opponents = new ArrayList<>();
	private Socket socket;
	private Scanner input;
	private PrintWriter output;

	public Player(final Socket socket, final int number) {
		this.socket = socket;
		this.number = number;
		this.color = Constants.getPlayerColor(number);
		players.add(this);
	}

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

	private void setup() throws IOException {
		// Poczatek gry
		input = new Scanner(socket.getInputStream());
		output = new PrintWriter(socket.getOutputStream(), true);
		output.println("WELCOME " + number);
		if (number == 1) {
			gameInstance.setCurrentPlayer(this);
			output.println("MESSAGE Waiting for opponent to connect");
		} else {
			opponents.add(gameInstance.getCurrentPlayer());
			for (final Player p : getPlayers()) {
				if (p != this) {
					//TODO: Hardcoded for only 2 players
					p.getOpponents().add(this);
					p.output.println("MESSAGE Your move");
				}
			}
		}
	}

	private void processCommands() {
		while (input.hasNextLine()) {
			// Obsluga komendy od klienta
			final var command = input.nextLine();
			if (command.startsWith("QUIT")) {
				return;
			} else if (command.startsWith("MOVE")) {
				//rozdzielamy na MOVE prevX, prevY, X, Y
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
	private void processMoveCommand(final int previousLocationX, final int previousLocationY,
									final int locationX, final int locationY) {
		try {
			gameInstance.move(locationX, locationY, this);
			output.println("VALID_MOVE");
			opponents.forEach(opponent -> opponent.output.println("OPPONENT_MOVED " + previousLocationX + " "
					+ previousLocationY + " " + locationX + " " + locationY));
			//TODO: implement logic - victory, tie etc.

		} catch (final IllegalStateException e) {
			output.println("MESSAGE " + e.getMessage());
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public List<Player> getOpponents() {
		return opponents;
	}

	public void setOpponents(final List<Player> opponents) {
		this.opponents = opponents;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(final Socket socket) {
		this.socket = socket;
	}

	public Scanner getInput() {
		return input;
	}

	public void setInput(final Scanner input) {
		this.input = input;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public void setOutput(final PrintWriter output) {
		this.output = output;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public static List<Player> getPlayers() {
		return players;
	}
}

