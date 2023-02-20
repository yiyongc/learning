package com.capgemini.service;

import java.util.List;

import com.capgemini.dao.IListAllDao;
import com.capgemini.dao.ListAllDaoImpl;
import com.capgemini.model.Product;

public class ListAllServiceImpl implements IListAllService {

	IListAllDao listDao = new ListAllDaoImpl();
	
	@Override
	public List<Product> listAll() {
		return listDao.getAllProducts();
	}

}
