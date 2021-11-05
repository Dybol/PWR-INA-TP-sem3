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
		//add the behavior of calc service to add two numbers

		Mockito.when(clientService.findByIdFromDatabase(1)).thenReturn(new Client("Mikołaj", "Dyblik"));
		//when(calcService.add(10.0,20.0)).thenReturn(30.00);

		//test the add functionality
		Assert.assertEquals(clientService.findByIdFromDatabase(1), new Client("Mikołaj", "Dyblik"));
		//assertEquals(mathApplication.add(10.0, 20.0),30.0,0);

	}
}