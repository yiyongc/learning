package com.capgemini.service;

import com.capgemini.dao.DeleteDaoImpl;
import com.capgemini.dao.IDeleteDao;

public class DeleteServiceImpl implements IDeleteService {

	@Override
	public void deleteProduct(int productId) {
	
		IDeleteDao deleteDao = new DeleteDaoImpl();
		
		deleteDao.deleteProduct(productId);
	}

}
