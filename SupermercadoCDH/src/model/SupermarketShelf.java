package model;

import java.util.List;
import java.util.Map;
import java.util.Random;

import model.om.Products;

public class SupermarketShelf {

	private Map<Product, Integer> stock;
	private static SupermarketShelf singleton = new SupermarketShelf();
	
	public SupermarketShelf() {
		super();
		this.stock = Products.getProducts();
	}

	public static SupermarketShelf getSingleton() {
		return singleton;
	}

	public synchronized Product getRandomProduct() {
		List<Product> products = stock.keySet().stream().toList();
		return products.get(new Random().nextInt(0,products.size()));
	}

	public synchronized boolean decreaseCuantity(Product product) {
		int productStock = stock.get(product);
		if (productStock <= 0) {
			return false;
		}

		stock.put(product, stock.get(product) - 1);
		return true;
	}

	public synchronized int getCuantity(Product product) {
		return stock.get(product);
	}
	public synchronized void set(Product product, int cuantity) {
		stock.put(product, cuantity);
	}
}
