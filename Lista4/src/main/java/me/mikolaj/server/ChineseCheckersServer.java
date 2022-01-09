package me.mikolaj.server;

import me.mikolaj.logic.Player;

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
	 * @param args - program arguments
	 */
	public static void main(final String[] args) {
		final ChineseCheckersServer server = new ChineseCheckersServer();
		server.createServer(58901);
	}

	/**
	 * Creates a server
	 *
	 * @param port - port for the server
	 */
	public void createServer(final int port) {

		try {
			final ServerSocket listener = new ServerSocket(port);
			System.out.println("Chinese Checkers server is running...");
			final ExecutorService pool = Executors.newFixedThreadPool(6);
			// Adding players
			while (true) {
				if (Player.getPlayers().size() > 6)
					throw new IllegalStateException("There are too many players!");
				pool.execute(new Player(listener.accept(), Player.getPlayers().size() + 1));
			}
		} catch (final Exception ex) {
			System.out.println(ex);
			System.out.println("There were problem with the server!");
		}
	}
}
