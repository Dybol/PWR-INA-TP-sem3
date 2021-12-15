package me.mikolaj.server;

import me.mikolaj.client.Player;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class ChineseCheckersServer {
	public static void main(final String[] args) throws Exception {
		try (final var listener = new ServerSocket(58901)) {
			System.out.println("Chinese Checkers server is running...");
			final var pool = Executors.newFixedThreadPool(6);
			// Dodawanie graczy
			while (true) {
				if (Player.getPlayers().size() > 6)
					throw new IllegalStateException("Za duzo graczy!");
				pool.execute(new Player(listener.accept(), Player.getPlayers().size() + 1));
				System.out.println("loop");
			}
		}
	}
}
