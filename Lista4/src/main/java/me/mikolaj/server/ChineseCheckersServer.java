package me.mikolaj.server;

import me.mikolaj.client.Player;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server's class
 */
public class ChineseCheckersServer {

	/**
	 * Main method that runs the server and adds clients to the pool
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		try (final ServerSocket listener = new ServerSocket(58901)) {
			System.out.println("Chinese Checkers server is running...");
			final ExecutorService pool = Executors.newFixedThreadPool(6);

			// Adding players
			while (true) {
				if (Player.getPlayers().size() > 6)
					throw new IllegalStateException("There are too many players!");
				pool.execute(new Player(listener.accept(), Player.getPlayers().size() + 1));
			}
		}
	}
}
