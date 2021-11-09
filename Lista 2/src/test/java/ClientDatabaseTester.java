import me.mikolaj.client.Client;
import me.mikolaj.client.ClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientDatabaseTester {

	private final ClientService clientService = Mockito.mock(ClientService.class);

	@Test
	public void testDatabase() {

		//imitujemy dzialanie metody findByIdFromDatabase
		Mockito.when(clientService.findByIdFromDatabase(1)).thenReturn(new Client("Mikołaj", "Dyblik"));

		Assert.assertEquals(clientService.findByIdFromDatabase(1), new Client("Mikołaj", "Dyblik"));

	}
}