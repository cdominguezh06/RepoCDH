package view;

import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.Supermarket;
import model.observer.Spectator;

public class app {

	public static void main(String[] args) {
		ExecutorService sv = Executors.newCachedThreadPool();
		PropertyChangeListener l = new Spectator();
		Supermarket mn = new Supermarket(sv, 2);
		sv.execute(mn);
		sv.shutdown();
	}

}
