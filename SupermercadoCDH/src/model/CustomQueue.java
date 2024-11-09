package model;

import java.util.ArrayDeque;
import java.util.Optional;

public class CustomQueue {

	private ArrayDeque<Client> clients;
	private boolean closed;
	
	public CustomQueue() {
		super();
		this.clients = new ArrayDeque<Client>();
		this.closed = false;
	}

	public void add(Client client) {
		clients.add(client);
	}

	public Optional<Client> get(){
		return clients.stream().findFirst();
	}
	
	public void remove(Client client) {
		clients.remove(client);
	}

	public boolean isClosed() {
		return closed;
	}
	
	public void closeQueue() {
		closed = true;
	}

	public boolean isEmpty() {
		return clients.isEmpty();
	}
}
