package controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import model.Checkout;
import model.Client;
import model.CustomQueue;
import model.Product;

public class Supermarket implements Runnable {

	private List<Checkout> checkouts;
	private Map<Client, Future<List<Product>>> clients;
	private List<Client> doneClients;
	private List<Client> processedClients;
	private List<CustomQueue> queues;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	private void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public Supermarket(ExecutorService sv, int checkoutsNumber) {
		super();
		checkouts = new ArrayList<Checkout>();
		clients = new HashMap<Client, Future<List<Product>>>();
		queues = new ArrayList<CustomQueue>();
		processedClients = new ArrayList<Client>();
		doneClients = new ArrayList<Client>();

		IntStream.range(0, checkoutsNumber).forEach(i -> {
			CustomQueue queue = new CustomQueue();
			queues.add(queue);
			Checkout newCheckout = new Checkout(queue, this);
			checkouts.add(newCheckout);
			sv.execute(newCheckout);
		});

		IntStream.range(0, 200).forEach(i -> {
			Client newClient = new Client(i, this);
			Future<List<Product>> submit = sv.submit(newClient);
			clients.put(newClient, submit);
		});

	}

	@Override
	public void run() {
		do {
			for (Client client : clients.keySet()) {
				Future<List<Product>> future = clients.get(client);
				if (future.isDone()) {
					try {
						if (!doneClients.contains(client)) {
							doneClients.add(client);
							if (!future.get().isEmpty()) {
								addToQueue(client);
							} else {
								System.out.println("CLIENT " + client.getId() + " HAS AN EMPTY CART");
								process(client);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		} while (processedClients.size() < clients.size());
			queues.forEach(q -> q.closeQueue());

	}

	private void addToQueue(Client client) {
		int nextInt = new Random().nextInt(0, queues.size());
		queues.get(nextInt).add(client);
	}

	public void process(Client client) {
		processedClients.add(client);
	}

	public PropertyChangeSupport getPcs() {
		return this.pcs;
	}

}
