import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCentre {
	
	
	HashMap<String, List<Product>> productsByNameArray = new HashMap<>();
	
	
    public String addProduct(String name, double price, String producer) {
    	Product product = new Product(name, price, producer);
    	List<Product> proByName = productsByNameArray.get(name);
    	if(proByName == null) {
    		proByName = new ArrayList<Product>();
    	}
    	proByName.add(product);
    	productsByNameArray.put(name, proByName);
        return "Product added";
    }
    
    public String delete(String name, String producer) {
    	List<Product> deleteFrom = productsByNameArray.get(name);
    	if(deleteFrom == null) return "No products found";
    	
        return "";
    }

    public String delete(String producer) {
        return "";
    }

    public String findProductsByName(String name) {
    	String resultStr = "";
    	List<Product> result = productsByNameArray.get(name);
    	result.stream().sorted(Comparator.comparing(Product::getName).thenComparing(Product::getProducer).thenComparing(Product::getPrice)).collect(Collectors.toList());
    	for(Product product : result) {
    		resultStr += "{" + product.getName() + ";" + product.getProducer() + ";" + product.getPrice() + "}/n";
    	}
        return resultStr;
    }

    public String findProductsByProducer(String producer) {
        return "";
    }

    public String findProductsByPriceRange(double priceFrom, double priceTo) {
        return "";
    }
}
