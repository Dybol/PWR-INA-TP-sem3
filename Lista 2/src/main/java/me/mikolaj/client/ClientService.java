package me.mikolaj.client;

import me.mikolaj.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//stosujemy tutaj zasade pure fabrication - tworzymy osobna klase odpowiedzialna za operacje na klientach - dzieki temu
//latwo bedzie dodac obsluge bazy danych - tutaj moglyby byc zwracani klienci z bazy
@Service
public class ClientService {

	//pobieramy wszystkie faktury dla danego klienta
	public List<Invoice> findAllClientInvoices(final Integer clientId) {
		return findById(clientId).getInvoices();
	}

	//znajdujemy klienta poprzez podane id
	public Client findById(final Integer clientId) {
		final List<Client> clients = Client.getAllClients();
		clients.removeIf(client -> !Objects.equals(client.getId(), clientId));
		return clients.size() == 0 ? null : clients.get(0);
	}

	//nie zostalo to jeszcze zaimplementowane, wykorzysamy to do mockservice
	public Client findByIdFromDatabase(final Integer clientId) {
		return null;
	}

}
