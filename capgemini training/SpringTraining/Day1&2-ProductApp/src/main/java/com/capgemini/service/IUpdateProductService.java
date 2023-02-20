package com.capgemini.service;

import com.capgemini.model.Product;

public interface IUpdateProductService {
	
	public Product getProductForUpdate(int productId);
	
	public boolean updateProduct(Product product);

}
