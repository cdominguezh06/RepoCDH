package tests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import model.SupermarketShelf;

class ClientTest {

	@Test
	void test() throws InterruptedException, ExecutionException {
		
		ExecutorService sv = Executors.newCachedThreadPool();
		SupermarketShelf mn = new SupermarketShelf(sv, 2);
		sv.awaitTermination(4, TimeUnit.SECONDS);
		
	}

}
