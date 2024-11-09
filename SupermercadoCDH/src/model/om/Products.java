package model.om;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Product;

public class Products {

	public static Map<Product, Integer> getProducts() {
		  Map<Product, Integer> products = new HashMap<>();
	        Random random = new Random();
	       
	        List<String> productNames = List.of(
	            "Milk", "Eggs", "Bread", "Butter", "Cheese", 
	            "Chicken", "Beef", "Pasta", "Rice", "Apples", 
	            "Bananas", "Oranges", "Tomatoes", "Potatoes", "Carrots"
	        );

	        int productCount = random.nextInt(productNames.size()) + 1;
	        for (int i = 0; i < productCount; i++) {
	           
	            String name = productNames.get(i);
	            
	            int id = random.nextInt(100) + 1;                
	            double price = Math.round((random.nextDouble() * 20 + 1) * 100.0) / 100.0; 
	            int quantity = random.nextInt(10)+1;             
	            
	            Product product = new Product(id, name, price);
	            products.put(product, quantity);
	        }
	        return products;
	}

}
