package com.capgemini.service;

import com.capgemini.dao.IUpdateDao;
import com.capgemini.dao.UpdateDaoImpl;
import com.capgemini.model.Product;

public class UpdateProductServiceImpl implements IUpdateProductService {
	
	private IUpdateDao updateDao = new UpdateDaoImpl();
	
	@Override
	public Product getProductForUpdate(int productId) {		
		return updateDao.getProductForUpdate(productId);
	}

	@Override
	public boolean updateProduct(Product product) {
		return updateDao.updateProduct(product);
	}

}
