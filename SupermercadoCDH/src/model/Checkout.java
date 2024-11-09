package model;

import java.util.Optional;

import controller.Supermarket;

public class Checkout implements Runnable{

	private Optional<Client> currentClient;
	private CustomQueue checkOutQueue;
	private int id;
	private static int checkoutsCuantity = 1;
	private Supermarket context;
	
	public Checkout(CustomQueue queue, Supermarket supermarket) {
		super();
		currentClient = Optional.empty();
		checkOutQueue = queue;
		id = checkoutsCuantity;
		checkoutsCuantity++;
		context = supermarket;
	}

	@Override
	public void run() {
		do {
			currentClient = checkOutQueue.get();			
			if(currentClient.isPresent()) {
				Client client = currentClient.get();
				double totalPrice = client.calculateShoppingCartTotal();
				if(client.getPaymentMethod().pay(totalPrice)) {
					System.out.println("CLIENT "+client.getId()+" SPENT "+Math.round(totalPrice)+"€ ON CHECKOUT "+id);
					
				}else{
					System.out.println("CLIENT "+client.getId()+"'S MONEY IS NOT ENOUGH"); 
					client.getShoppingCart().forEach(p ->{
						SupermarketShelf singleton = SupermarketShelf.getSingleton();
						singleton.set(p, singleton.getCuantity(p)+1);
					});
				}
				checkOutQueue.remove(client);
				currentClient = Optional.empty();
				context.process(client);
			}
		}while(!checkOutQueue.isClosed());
	}
	
	
}
