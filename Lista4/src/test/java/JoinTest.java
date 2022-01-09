import me.mikolaj.client.ChineseCheckersClient;
import me.mikolaj.logic.Player;
import me.mikolaj.server.ChineseCheckersServer;
import org.junit.Assert;
import org.junit.Test;


public class JoinTest {
	/**
	 * Checks if players can join successfully
	 */
	@Test
	public synchronized void testPlayerJoin() throws InterruptedException {
		Player.getPlayers().clear();
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
		
		Assert.assertEquals(2, Player.getPlayers().size());
	}
}
