package com.capgemini.boot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capgemini.config.JavaSpringConfig;
import com.capgemini.service.IProductService;

public class MainForProduct {
	
	public static void main(String[] args) throws ParseException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaSpringConfig.class);
		IProductService productService = context.getBean(IProductService.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.getClass();
//		Product product1 = new Product("Product 1", "Testing product service 1", 1, 50.1, sdf.parse("09-11-2017"));
//		Product product2 = new Product("Product 2", "Testing product service 2", 1, 60.2, sdf.parse("08-10-2017"));
//		Product product3 = new Product("Product 3", "Testing product service 3", 1, 70.3, sdf.parse("07-09-2017"));
//		Product product4 = new Product("Product 4", "Testing product service 4", 1, 80.4, sdf.parse("06-08-2017"));
//		Product product5 = new Product("Product 5", "Testing product service 5", 1, 90.5, sdf.parse("05-07-2017"));
//		
//		List<Product> products = new ArrayList<>();
//		products.add(product1);
//		products.add(product2);
//		products.add(product3);
//		products.add(product4);
//		products.add(product5);
//		
//		productService.persistAllProducts(products);
		
//		Product update = new Product("Product 33", "Testing product service 33", 1, 50.1, sdf.parse("03-11-2017"));
//		update.setProductId(3);
//		productService.updateProduct(update);
		
//		for (Product p : productService.findAllProducts()) {
//			System.out.println(p);
//		}
		
//		for (Product p : productService.findByProductName("Product 1")) {
//			System.out.println(p);
//		}
		
//		for (Product p : productService.findAllProductsDesc()) {
//			System.out.println(p);
//		}
		
		for (String pName : productService.findAllProductNames()) {
			System.out.println(pName);
		}
		
		context.close();
	}

}
