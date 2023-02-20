package com.capgemini.boot;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
//import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import com.capgemini.lifecycle.Employee;

public class MainForCriteria {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		
		//CriteriaBuilder
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
//		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
//		Root<Employee> employee = query.from(Employee.class);
//		
//		//JPA ParameterExpression (another way of specifying criteria)
//		ParameterExpression<String> firstNameToFind = cb.parameter(String.class, "firstName");
//		
//		//Typed Query
//		query.select(employee).where(cb.equal(employee.get("firstName"), "fruit"));
//		query.select(employee).where(cb.greaterThan(employee.get("salary"), 3000), 
//				cb.equal(employee.get("isPermanent"), false),
//				cb.equal(employee.get("firstName"), firstNameToFind));
//	
//		TypedQuery<Employee> typedQuery = em.createQuery(query);
//		
//		//Set parameter expression values
//		typedQuery.setParameter("firstName", "fruit");
//		
//		List<Employee> employees = (List<Employee>)typedQuery.getResultList();
//		
//		for(Employee e : employees)
//			System.out.println(e);
		
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		Root<Employee> employee = query.from(Employee.class);
		query.select(cb.tuple(employee.get("firstName"), 
								employee.get("lastName"),
								employee.get("salary")))
				.where(cb.greaterThan(employee.get("salary"), 2500))
				.orderBy(cb.asc(employee.get("salary")));
		
		List<Tuple> results = em.createQuery(query).getResultList();
		for (Tuple result : results) {
			System.out.println("First Name: " + result.get(0) + 
								"  Last Name: " + result.get(1) + 
								"  Salary: " + result.get(2));
		}
		
		
	}
}
