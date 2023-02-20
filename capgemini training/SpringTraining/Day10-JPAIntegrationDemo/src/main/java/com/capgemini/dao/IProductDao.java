package com.capgemini.dao;

import java.util.List;

import com.capgemini.pojo.Product;

public interface IProductDao {

	public void createProduct(Product product);

	public void persistAllProducts(List<Product> products);

	public Product findProduct(int productId);

	public void deleteProduct(int productId);

	public void updateProduct(Product product);

	public List<Product> findAllProducts();

	public List<Product> findByProductName(String productName);

	public List<Product> findAllProductsDesc();

	public List<String> findAllProductNames();
	
}
