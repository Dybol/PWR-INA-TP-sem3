package me.mikolaj.client;

import me.mikolaj.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client's representation
 */
public class ChineseCheckersClient {

	/**
	 * Frame instance
	 */
	private final JFrame frame = new JFrame("Chinese Checkers");
	/**
	 * JLabel for messages
	 */
	private final JLabel messageLabel = new JLabel("...");

	/**
	 * JButton for starting the game
	 */
	private final JButton startButton = new JButton("start");

	/**
	 * Representation of the board
	 */
	private final Square[][] board = new Square[17][25];

	/**
	 * Current square (player moves from a previous square to the current square)
	 */
	private Square currentSquare;

	/**
	 * Previous square (player moves from a previous square to the current square)
	 */
	private Square previousSquare;

	/**
	 * Socket for communication between client and server
	 */
	private final Socket socket;

	/**
	 * Scanner for reading input
	 */
	private final Scanner input;

	/**
	 * PrintWriter for writing output
	 */
	private final PrintWriter output;

	/**
	 * Constructor for ChineseCheckersClient
	 * We are creating the board
	 *
	 * @param serverAddress - server IP address
	 * @throws Exception
	 */
	public ChineseCheckersClient(final String serverAddress) throws Exception {

		// Connecting with the server
		socket = new Socket(serverAddress, 58901);
		input = new Scanner(socket.getInputStream());
		output = new PrintWriter(socket.getOutputStream(), true);

		messageLabel.setBackground(Color.lightGray);

		frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

		// Drawing the board
		final JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.black);
		boardPanel.setLayout(new GridLayout(17, 25, 2, 2));
		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.OFFSETS[i]; j++) {
				board[i][j] = new Square();
				board[i][j].setBackground(Color.WHITE);
				boardPanel.add(board[i][j]);
			}

			for (int j = Constants.OFFSETS[i]; j < Constants.OFFSETS[i] + Constants.WIDTHS[i] * 2 - 1; j += 2) {
				if (j < 25) {
					board[i][j] = new Square();
					board[i][j].setBackground(Color.GREEN);
					board[i][j].addMouseListener(createAdapter(i, j));
					boardPanel.add(board[i][j]);
				}
				if (j < 24) {
					board[i][j + 1] = new Square();
					board[i][j + 1].setBackground(Color.WHITE);
					boardPanel.add(board[i][j + 1]);
				}
			}
			for (int j = Constants.OFFSETS[i] + Constants.WIDTHS[i] * 2 - 1; j < Constants.WIDTH; j++) {
				if (board[i][j] != null)
					boardPanel.remove(board[i][j]);
				board[i][j] = new Square();
				board[i][j].setBackground(Color.WHITE);
				boardPanel.add(board[i][j]);
			}

			frame.getContentPane().add(boardPanel, BorderLayout.CENTER);

		}
		frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
		fillHomes();
	}

	/**
	 * Drawing player's homes - player's pawns
	 */
	private void fillHomes() {
		for (int i = 0; i < Constants.HEIGHT; i++) {
			for (int j = 0; j < Constants.WIDTH; j++) {
				if (board[i][j].getBackground() == Color.green) {
					if (i < 4)
						board[i][j].setBackground(Constants.PLAYER_1_COLOR);
					if (i > 12)
						board[i][j].setBackground(Constants.PLAYER_2_COLOR);

					fillForComplexHomes(Constants.HOME_3, Constants.PLAYER_3_COLOR, i, j);
					fillForComplexHomes(Constants.HOME_4, Constants.PLAYER_4_COLOR, i, j);
					fillForComplexHomes(Constants.HOME_5, Constants.PLAYER_5_COLOR, i, j);
					fillForComplexHomes(Constants.HOME_6, Constants.PLAYER_6_COLOR, i, j);
					board[i][j].repaint();
				}
			}
		}
	}

	//metoda pomocnicza do rysowania bardziej zlozonych domow

	/**
	 * Helper method for drawing more complex player's homes
	 *
	 * @param HOME_X - Coordinates of player's pawns
	 * @param color  - Color of player's pawns
	 * @param i      - Current x coordinate
	 * @param j      - Current y coordinate
	 */
	private void fillForComplexHomes(final Integer[][] HOME_X, final Color color, final int i, final int j) {
		for (final Integer[] x : HOME_X) {
			for (int y = 0; y < x.length - 1; y += 2) {
				if (x[y] == i && x[y + 1] == j)
					board[i][j].setBackground(color);
			}
		}
	}

	/**
	 * A method for creating an adapter to handle moves
	 *
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return an object of mouse adapter with given properties
	 */
	private MouseAdapter createAdapter(final int x, final int y) {
		return new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				previousSquare = board[x][y];
				currentSquare = null;
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				calculateSquare(x, y, e.getX(), e.getY());
			}
		};
	}

	/**
	 * Calculates a square's coordinates that player has moved on
	 *
	 * @param squareX - previous X location
	 * @param squareY - previous Y location
	 * @param movedX  - new X location
	 * @param movedY  - new Y location
	 */
	private void calculateSquare(final int squareX, final int squareY, final int movedX, final int movedY) {
		int changeX = 0;
		int changeY = 0;

		if (movedX > 50 || movedX < 0 || movedY > 50 || movedY < 0) {
			if (movedX > 0)
				changeX = movedX / 50;
			else
				changeX = movedX / 50 - 1;

			if (movedY > 0)
				changeY = movedY / 50;
			else
				changeY = movedY / 50 - 1;
		}
		try {
			if (board[squareX + changeY][squareY + changeX].getBackground() != Color.white) {
				currentSquare = board[squareX + changeY][squareY + changeX];

				//Verification is done by the server
				output.println("MOVE " + squareX + " " + squareY + " " + (squareX + changeY) + " " + (squareY + changeX) + " "
						+ previousSquare.getBackground().getRGB());
			}
		} catch (final Throwable t) {
			t.printStackTrace();
			System.out.println("err");
		}
	}

	/**
	 * The main thread of the client will listen for messages from the server. The
	 * first message will be a "WELCOME" message in which we receive our mark. Then
	 * we go into a loop listening for any other messages, and handling each
	 * message appropriately.
	 */
	public void play() throws Exception {
		try {
			//Message from the server
			String response = input.nextLine();
			final int mark = Integer.parseInt(String.valueOf(response.charAt(8)));

			frame.setTitle("Chinese Checkers: Player " + mark);

			//Only first player can start the game
			if (mark == 1) {
				startButton.addActionListener(e -> {
					output.println("START");
					frame.getContentPane().remove(startButton);
					frame.repaint();
				});
				frame.getContentPane().add(startButton, BorderLayout.NORTH);
			}

			while (input.hasNextLine()) {
				response = input.nextLine();
				if (response.startsWith("VALID_MOVE")) {
					messageLabel.setText("Valid move, please wait");
					if (currentSquare != null) {
						currentSquare.setBackground(Constants.getPlayerColor(mark));
						currentSquare.repaint();
						previousSquare.setBackground(Color.green);
						previousSquare.repaint();

					}
				} else if (response.startsWith("OPPONENT_MOVED")) {
					final String[] locations = response.split(" ");
					final int previousLocationX = Integer.parseInt(locations[1]);
					final int previousLocationY = Integer.parseInt(locations[2]);
					final int locationX = Integer.parseInt(locations[3]);
					final int locationY = Integer.parseInt(locations[4]);
					final int playerNumber = Integer.parseInt(locations[5]);
					final int howManyPlayers = Integer.parseInt(locations[6]);
					final int previousPlayer = playerNumber == 1 ? howManyPlayers : playerNumber - 1;

					board[previousLocationX][previousLocationY].setBackground(Color.green);
					board[previousLocationX][previousLocationY].repaint();
					board[locationX][locationY].setBackground(Constants.getPlayerColor(previousPlayer));
					board[locationX][locationY].repaint();
					if (mark == playerNumber)
						messageLabel.setText("Opponent moved, your turn");
					else
						messageLabel.setText("Opponent moved, please wait");
				} else if (response.startsWith("MESSAGE")) {
					messageLabel.setText(response.substring(8));
				} else if (response.startsWith("VICTORY")) {
					JOptionPane.showMessageDialog(frame, "Winner Winner");
					break;
				} else if (response.startsWith("DEFEAT")) {
					JOptionPane.showMessageDialog(frame, "Sorry you lost");
					break;
				} else if (response.startsWith("TIE")) {
					JOptionPane.showMessageDialog(frame, "Tie");
					break;
				} else if (response.startsWith("OTHER_PLAYER_LEFT")) {
					JOptionPane.showMessageDialog(frame, "Other player left");
					break;
				}
			}
			output.println("QUIT");
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
			frame.dispose();
		}
	}

	/**
	 * A class that represents a square on the board
	 */
	private static class Square extends JPanel {
		JLabel label = new JLabel();

		public Square() {
			setBackground(Color.white);
			setLayout(new GridBagLayout());
			add(label);
		}
	}

	/**
	 * Main method that runs the client
	 *
	 * @param args - server IP
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Pass the server IP as the sole command line argument");
			return;
		}
		final ChineseCheckersClient client = new ChineseCheckersClient(args[0]);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setSize(3 * 470, 3 * 320);
		client.frame.setVisible(true);
		client.frame.setResizable(false);
		client.play();
	}
}
