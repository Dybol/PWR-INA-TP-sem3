import me.mikolaj.client.Client;
import me.mikolaj.Invoice;
import me.mikolaj.LineItem;
import me.mikolaj.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestAssertions extends Assert {

	@Test
	public void testAssertions() {
		Client client = new Client("Mikołaj", "Dyblik");
		Product product1 = new Product("p1", 15.00);
		Product product2 = new Product("p2", 33.20);
		LineItem lineItem1 = new LineItem(10, product1);
		LineItem lineItem2 = new LineItem(5, product2);
		Invoice invoice = new Invoice(Arrays.asList(lineItem1, lineItem2), client);
		Assert.assertEquals(316.00, invoice.getTotalPrice(), 0);
	}
}
