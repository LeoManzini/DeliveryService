package br.com.leomanzini.delivery.entities;

public class Product {
	
	private Long id;
	private String name;
	private double price;
	private String description;
	private String imageUrl;

	public Product() {

	}

	public Product(Long id, String name, double price, String description, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Product id: " + id + ", name: " + name + ", price: " + price + ", description: " + description
				+ ", imageUrl: " + imageUrl;
	}
}
