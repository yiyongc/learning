package com.capgemini.dao;

import com.capgemini.model.Product;

public interface IUpdateDao {
	
	public boolean updateProduct(Product product);

	public Product getProductForUpdate(int productId);
	
}
