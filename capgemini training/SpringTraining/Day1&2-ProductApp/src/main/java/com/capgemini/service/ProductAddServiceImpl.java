package com.capgemini.service;

import com.capgemini.dao.IProductDao;
import com.capgemini.dao.ProductDaoImpl;
import com.capgemini.model.Product;

public class ProductAddServiceImpl implements IProductAddService {

	private IProductDao productDao = new ProductDaoImpl();
	
	@Override
	public boolean saveProduct(Product product) {
		System.out.println(product);
		return productDao.createProduct(product);
	}

}
