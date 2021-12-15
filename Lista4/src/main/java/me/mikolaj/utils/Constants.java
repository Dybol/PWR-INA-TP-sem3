package me.mikolaj.utils;

import java.awt.*;

public class Constants {
	public static final Integer WIDTH = 25;
	public static final Integer HEIGHT = 17;
	public static final Integer[] WIDTHS = {
			1, 2, 3, 4, 13, 12, 11, 10, 9, 10, 11, 12, 13, 4, 3, 2, 1
	};
	public static final int[] OFFSETS = {
			12, 11, 10, 9, 0, 1, 2, 3, 4, 3, 2, 1, 0, 9, 10, 11, 12
	};

	public static final Color PLAYER_1_COLOR = Color.RED;

	public static final Color PLAYER_2_COLOR = Color.BLUE;

	public static final Color PLAYER_3_COLOR = Color.ORANGE;

	public static final Color PLAYER_4_COLOR = Color.YELLOW;

	public static final Color PLAYER_5_COLOR = Color.MAGENTA;

	public static final Color PLAYER_6_COLOR = Color.DARK_GRAY;

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

	public static final Integer[][] HOME_3 = {
			{4, 0}, {4, 2}, {4, 4}, {4, 6},
			{5, 1}, {5, 3}, {5, 5},
			{6, 2}, {6, 4},
			{7, 3}
	};


	public static final Integer[][] HOME_4 = {
			{4, 18}, {4, 20}, {4, 22}, {4, 24},
			{5, 19}, {5, 21}, {5, 23},
			{6, 20}, {6, 22},
			{7, 21}
	};

	public static final Integer[][] HOME_5 = {
			{9, 3},
			{10, 2}, {10, 4},
			{11, 1}, {11, 3}, {11, 5},
			{12, 0}, {12, 2}, {12, 4}, {12, 6}
	};


	public static final Integer[][] HOME_6 = {
			{9, 21},
			{10, 20}, {10, 22},
			{11, 19}, {11, 21}, {11, 23},
			{12, 18}, {12, 20}, {12, 22}, {12, 24}
	};

}
