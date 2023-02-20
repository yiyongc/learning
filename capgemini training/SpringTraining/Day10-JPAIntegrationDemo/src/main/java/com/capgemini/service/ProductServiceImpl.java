package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.IProductDao;
import com.capgemini.pojo.Product;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;

	@Override
	@Transactional
	public void createProduct(Product product) {
		productDao.createProduct(product);
	}

	@Override
	@Transactional
	public void persistAllProducts(List<Product> products) {
		productDao.persistAllProducts(products);
		
	}

	@Override
	@Transactional
	public Product findProduct(int productId) {
		return productDao.findProduct(productId);
	}

	@Override
	@Transactional
	public void deleteProduct(int productId) {
		productDao.deleteProduct(productId);
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

	@Override
	@Transactional
	public List<Product> findAllProducts() {
		return productDao.findAllProducts();
	}

	@Override
	@Transactional
	public List<Product> findByProductName(String productName) {
		return productDao.findByProductName(productName);
	}

	@Override
	@Transactional
	public List<Product> findAllProductsDesc() {
		return productDao.findAllProductsDesc();
	}

	@Override
	public List<String> findAllProductNames() {
		return productDao.findAllProductNames();
	}

}
