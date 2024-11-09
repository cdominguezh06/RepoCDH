package model.observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.Product;
import model.SupermarketShelf;

public class Spectator implements PropertyChangeListener {

	private static Spectator singleton = new Spectator();
	
	
	public static Spectator getSingleton() {
		return singleton;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Product product = (Product)evt.getOldValue();
		System.out.println("SYSTEM OBSERVER : PRODUCT " +product.getName().toUpperCase() + " HAS NO STOCK");
		SupermarketShelf.getSingleton().set(product, 10);
	}

}
