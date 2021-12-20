package me.mikolaj.utils;

import java.awt.*;

/**
 * Utility class - keeping constants here
 */
public final class Constants {

	/**
	 * Private constructor so that we cannot create an instance of the utility class
	 */
	private Constants() {
	}

	/**
	 * Width of the board
	 */
	public static final Integer WIDTH = 25;

	/**
	 * Height of the board
	 */
	public static final Integer HEIGHT = 17;

	/**
	 * Numbers of appropriate fields in each row
	 */
	public static final Integer[] WIDTHS = {
			1, 2, 3, 4, 13, 12, 11, 10, 9, 10, 11, 12, 13, 4, 3, 2, 1
	};

	/**
	 * Numbers of inappropriate fields in each row - they are not used in the game
	 */
	public static final int[] OFFSETS = {
			12, 11, 10, 9, 0, 1, 2, 3, 4, 3, 2, 1, 0, 9, 10, 11, 12
	};

	/**
	 * Player's 1 color
	 */
	public static final Color PLAYER_1_COLOR = Color.RED;

	/**
	 * Player's 2 color
	 */
	public static final Color PLAYER_2_COLOR = Color.BLUE;

	/**
	 * Player's 3 color
	 */
	public static final Color PLAYER_3_COLOR = Color.ORANGE;

	/**
	 * Player's 4 color
	 */
	public static final Color PLAYER_4_COLOR = Color.YELLOW;

	/**
	 * Player's 5 color
	 */
	public static final Color PLAYER_5_COLOR = Color.MAGENTA;

	/**
	 * Player's 6 color
	 */
	public static final Color PLAYER_6_COLOR = Color.DARK_GRAY;

	/**
	 * Coordinates of player's 3 home
	 */
	public static final Integer[][] HOME_3 = {
			{4, 0}, {4, 2}, {4, 4}, {4, 6},
			{5, 1}, {5, 3}, {5, 5},
			{6, 2}, {6, 4},
			{7, 3}
	};

	/**
	 * Coordinates of player's 4 home
	 */
	public static final Integer[][] HOME_4 = {
			{4, 18}, {4, 20}, {4, 22}, {4, 24},
			{5, 19}, {5, 21}, {5, 23},
			{6, 20}, {6, 22},
			{7, 21}
	};

	/**
	 * Coordinates of player's 5 home
	 */
	public static final Integer[][] HOME_5 = {
			{9, 3},
			{10, 2}, {10, 4},
			{11, 1}, {11, 3}, {11, 5},
			{12, 0}, {12, 2}, {12, 4}, {12, 6}
	};

	/**
	 * Coordinates of player's 6 home
	 */
	public static final Integer[][] HOME_6 = {
			{9, 21},
			{10, 20}, {10, 22},
			{11, 19}, {11, 21}, {11, 23},
			{12, 18}, {12, 20}, {12, 22}, {12, 24}
	};

	/**
	 * Gets given player's color
	 *
	 * @param playerNo - number of player
	 * @return player's color
	 */
	public static Color getPlayerColor(final int playerNo) {
		return switch (playerNo) {
			case 1 -> PLAYER_1_COLOR;
			case 2 -> PLAYER_2_COLOR;
			case 3 -> PLAYER_3_COLOR;
			case 4 -> PLAYER_4_COLOR;
			case 5 -> PLAYER_5_COLOR;
			case 6 -> PLAYER_6_COLOR;
			default -> Color.BLACK;
		};
	}
}
