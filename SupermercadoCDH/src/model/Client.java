package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

import controller.Supermarket;
import model.observer.Spectator;
import model.unimplemented.PaymentMethod;

public class Client implements Callable<List<Product>> {

	private int id;
	private List<Product> shoppingCart;
	private Supermarket context;
	private PaymentMethod payment;

	public Client(int id, Supermarket context) {
		super();
		this.id = id;
		this.shoppingCart = new ArrayList<Product>();
		this.context = context;
		this.payment = new CreditCardPayment();
	}

	@Override
	public List<Product> call() throws Exception {
		int numberOfProducts = new Random().nextInt(0, 10);
		IntStream.range(0, numberOfProducts).forEach(i -> {
			addToCart();
		});
		return this.shoppingCart;
	}

	private void addToCart() {
		SupermarketShelf singleton = SupermarketShelf.getSingleton();
		Product found;
		boolean isInStock;
		found = singleton.getRandomProduct();
		isInStock = singleton.decreaseCuantity(found);
		if (isInStock) {
			shoppingCart.add(found);
		} else {
			if (context.getPcs().getPropertyChangeListeners().length == 0) {
				context.getPcs().addPropertyChangeListener(Spectator.getSingleton());
			}
			context.getPcs().firePropertyChange("PRODUCT", found, null);

		}
	}

	private void wasteTime() {
		try {
			Thread.sleep(new Random().nextInt(0, 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public double calculateShoppingCartTotal() {
		double total = 0;
		for (Product product : shoppingCart) {
			total += product.getPrice();
		}
		return total;
	}

	public int getId() {
		return id;
	}

	public List<Product> getShoppingCart() {
		return shoppingCart;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return id == other.id;
	}

	public PaymentMethod getPaymentMethod() {
		return this.payment;
	}

}
