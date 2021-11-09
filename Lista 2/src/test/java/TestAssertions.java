import me.mikolaj.Invoice;
import me.mikolaj.LineItem;
import me.mikolaj.Product;
import me.mikolaj.client.Client;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestAssertions extends Assert {

	//sprawdzamy, czy kwota na fakturze jest dobrze zliczana
	@Test
	public void testAssertions() {
		final Client client = new Client("Mikołaj", "Dyblik");
		final Product product1 = new Product("p1", 15.00);
		final Product product2 = new Product("p2", 33.20);
		final LineItem lineItem1 = new LineItem(10, product1);
		final LineItem lineItem2 = new LineItem(5, product2);
		final Invoice invoice = new Invoice(Arrays.asList(lineItem1, lineItem2), client);
		Assert.assertEquals(316.00, invoice.getTotalPrice(), 0);
	}
}
