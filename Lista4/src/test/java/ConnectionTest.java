import me.mikolaj.client.ChineseCheckersClient;
import me.mikolaj.logic.Player;
import me.mikolaj.server.ChineseCheckersServer;
import org.junit.Assert;
import org.junit.Test;

import java.net.Socket;

/**
 * Test class for connections
 */
public class ConnectionTest {

	/**
	 * Checks if connecting with server was successful
	 *
	 * @throws Exception - if anything went wrong
	 */
	@Test
	public synchronized void testConnection() throws Exception {
		final ChineseCheckersServer server = new ChineseCheckersServer();
		final Thread t = new Thread(() -> {
			try {
				server.createServer(58902);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		t.start();
		new Socket("localhost", 58902);
	}

	/**
	 * Checks if players can join successfully
	 *
	 * @throws Exception - if anything went wrong
	 */
	@Test
	public synchronized void testPlayerJoin() throws Exception {
		final ChineseCheckersServer server = new ChineseCheckersServer();
		final Thread t = new Thread(() -> {
			try {
				server.createServer(58901);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		t.start();
		final Thread t2 = new Thread(() -> {
			try {
				final ChineseCheckersClient client = new ChineseCheckersClient("localhost");
				client.play();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		t2.start();
		final Thread t3 = new Thread(() -> {
			try {
				final ChineseCheckersClient client2 = new ChineseCheckersClient("localhost");
				client2.play();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		t3.start();

		//we add delay so that everything got synchronized
		Thread.sleep(1000);

		//as we have already one player in because of the previous method
		Assert.assertEquals(3, Player.getPlayers().size());
	}
}
