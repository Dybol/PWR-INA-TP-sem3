package me.mikolaj.client;

import me.mikolaj.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//klasa odpowiadajaca tylko za klienta - moze byc traktowana jak encja
public class Client {

	//lista wszystkich klientów
	private static final List<Client> allClients = new ArrayList<>();

	private Integer id;
	private String firstName;
	private String lastName;

	//tworzymy nowego klienta i dodajemy go do listy wszystkich klientow
	public Client(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = allClients.size() + 1;
		allClients.add(this);
	}

	private final List<Invoice> invoices = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	//dodajemy fakture dla danego klienta
	public void addInvoice(final Invoice invoice) {
		allClients.remove(this);
		this.invoices.add(invoice);
		allClients.add(this);
	}


	//zwracamy wszystkich klientow
	public static List<Client> getAllClients() {
		return allClients;
	}

	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", invoices=" + invoices.size();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Client client = (Client) o;
		return Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}
}
