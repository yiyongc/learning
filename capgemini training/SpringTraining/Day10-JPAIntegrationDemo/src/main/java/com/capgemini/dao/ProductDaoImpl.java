package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Product;

@Repository
public class ProductDaoImpl implements IProductDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createProduct(Product product) {
		em.persist(product);
	}

	@Override
	public void persistAllProducts(List<Product> products) {
		for(Product product : products) {
			em.persist(product);
		}
	}

	@Override
	public Product findProduct(int productId) {
		return em.find(Product.class, productId);
	}

	@Override
	public void deleteProduct(int productId) {
		em.remove(findProduct(productId));
	}

	@Override
	public void updateProduct(Product product) {
		Product productToUpdate = findProduct(product.getProductId());
		
		productToUpdate.setProductName(product.getProductName());
		productToUpdate.setPrice(product.getPrice());
		productToUpdate.setExpiryDate(product.getExpiryDate());
		productToUpdate.setQuantity(product.getQuantity());
		productToUpdate.setDescription(product.getDescription());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAllProducts() {
		return em.createQuery("FROM Product").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByProductName(String productName) {
		Query query = em.createQuery("FROM Product p WHERE p.productName = :name");
		query.setParameter("name", productName);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAllProductsDesc() {
		return em.createQuery("FROM Product p ORDER BY p.productName DESC").getResultList();
	}

	@Override
	public List<String> findAllProductNames() {
//		return em.createQuery("SELECT p.productName FROM Product p").getResultList();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> query = cb.createQuery(String.class);
		
		//This has to be the product class
		Root<Product> product = query.from(Product.class);
		
		return em.createQuery(query.select(product.get("productName"))).getResultList();
		
	}

}
