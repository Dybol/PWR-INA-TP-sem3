package me.mikolaj;

//encja - reprezentuje produkt
public class Product {

	private String name;
	private Double price;

	public Product(final String name, final Double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	//zwracamy cene produtku
	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" +
				"name='" + name + '\'' +
				", price=" + price +
				'}';
	}
}
