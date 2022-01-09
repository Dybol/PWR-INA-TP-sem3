import me.mikolaj.logic.Player;
import me.mikolaj.server.ChineseCheckersServer;
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
		Player.getPlayers().clear();
	}
}
