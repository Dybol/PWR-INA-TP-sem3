package me.mikolaj;

import me.mikolaj.client.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

//klasa odpowiadajaca za faktury - encja
//niskie sprzezenie - jest polaczona z innymi klasami tylko tam, gdzie to konieczne
public class Invoice {

	private List<LineItem> lineItems;

	private LocalDateTime createdAt;

	private Client client;

	public Invoice(final List<LineItem> lineItems, final Client client) {
		this.lineItems = lineItems;
		this.createdAt = LocalDateTime.now();
		this.client = client;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(final List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void addLineItem(final LineItem item) {
		this.lineItems.add(item);
	}

	//law od demeter - pobieramy pelna cene lineItemow - nie odwolujemy sie do produktu i jego jednostkowej ceny
	public double getTotalPrice() {
		final AtomicReference<Double> sum = new AtomicReference<>(0d);
		lineItems.forEach(lineItem -> sum.updateAndGet(v -> v + lineItem.getFullPrice()));
		return sum.get();
	}

	@Override
	public String toString() {
		return "Invoice{" +
				"lineItems=" + lineItems +
				", createdAt=" + createdAt +
				", fullPrice=" + getTotalPrice() +
				", client=" + client +
				'}';
	}
}
