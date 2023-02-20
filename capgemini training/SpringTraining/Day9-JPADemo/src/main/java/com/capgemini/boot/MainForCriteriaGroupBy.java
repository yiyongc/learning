package com.capgemini.boot;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.capgemini.onetomany.Company;
import com.capgemini.onetomany.EmployeeOTM;

public class MainForCriteriaGroupBy {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		
		//Populate tables to perform join
//		EntityTransaction et = em.getTransaction();
//		
//		et.begin();
//		Company company1 = new Company(1002, "Capgemini Company");
//		EmployeeOTM employee1 = new EmployeeOTM("yiyong", "bb", company1);
//		EmployeeOTM employee2 = new EmployeeOTM("zzz", "ss", company1);
//		EmployeeOTM employee3 = new EmployeeOTM("aaaa", "cc", company1);
//		
//		em.persist(company1);
//		em.persist(employee1);
//		em.persist(employee2);
//		em.persist(employee3);
//		et.commit();
		
		//CriteriaBuilder
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
		Root<Company> company = query.from(Company.class);
		Join<Company, EmployeeOTM> employees = company.join("employees");
		
		query.multiselect(company.get("companyName"), cb.count(employees)).groupBy(company.get("companyName"));

		TypedQuery<Object[]> typedQuery = em.createQuery(query);
		
		List<Object[]> results = typedQuery.getResultList();
		
		for(Object[] resultArr : results) {
			for (int i = 0; i < resultArr.length; i++) {
				System.out.print(resultArr[i] + " ");
			}
			System.out.println("");
		}
	}

}
