package model;

public class Product {

	private int id;
	private String name;
	private double price;
	
	public Product(int id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public String getName() {
		return name;
	}
	
	
}
