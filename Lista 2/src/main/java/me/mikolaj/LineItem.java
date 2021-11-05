package me.mikolaj;

//jest oczywiscie co dzieje sie w tej klasie - zawiera tylko produkty i ich ilosc
//niskie sprzezenie - nastepuje tylko i wylacznie odwolanie do produktu, co jest konieczne
public class LineItem {

	private Integer quantity;

	private Product product;

	public LineItem(final Integer quantity, final Product product) {
		this.quantity = quantity;
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	public Double getFullPrice() {
		return quantity * product.getPrice();
	}

	@Override
	public String toString() {
		return "LineItem{" +
				"quantity=" + quantity +
				", product=" + product +
				'}';
	}
}
