package ee.andres.cafeteria;

import ee.andres.cafeteria.pojo.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class CafeteriaEventApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testLocale(){
		Locale locale = new Locale("et","ee");
		System.out.println(locale);
		ResourceBundle bundle = ResourceBundle.getBundle("labels", locale);
		System.out.println(bundle.getString("FOOD"));
	}
	@Test
	public void testRemove(){
		List<Product> products = new ArrayList<>();
		Product p = new Product();
		p.setId(1L);
		products.add(p);
		p = new Product();
		p.setId(2L);
		products.add(p);
		Iterator<Product> it = products.iterator();
		while(it.hasNext()){
			Product product = it.next();
			if(Long.valueOf(1).equals(product.getId())){
				it.remove();
			}
		}
		System.out.println("products : " + products.size() + "products.get: " + products.get(0).getId());
	}
}
