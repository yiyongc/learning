package com.capgemini.boot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

//import com.capgemini.lifecycle.Employee;

public class MainForLifeCycle {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.getClass();
		
//		Employee employee = new Employee("Harry", "potter", 2000, sdf.parse("01-01-1999"), 
//				sdf.parse("03-07-2017"),"a@a.com", true, "capgemini");
//		Employee employee2 = new Employee("fruit", "cake", 5000, sdf.parse("15-09-1995"), 
//				sdf.parse("03-07-2017"),"b@b.com", false, "capgemini2");
//		Employee employee3 = new Employee("cupcake", "man", 2350, sdf.parse("17-08-1993"), 
//				sdf.parse("03-07-2017"),"c@c.com", true, "whatthehell");
//		Employee employee4 = new Employee("Monster", "Chicken", 4000, sdf.parse("09-11-1994"), 
//				sdf.parse("03-07-2017"),"d@d.com", true, "monsterchix");
//		Employee employee5 = new Employee("Dog", "Han", 6000, sdf.parse("11-02-1992"), 
//				sdf.parse("03-07-2017"),"e@e.com", false, "zenna");
//		Employee employee6 = new Employee("Kuku", "Bird", 2350, sdf.parse("10-04-1993"), 
//				sdf.parse("03-07-2017"),"f@f.com", true, "givemehole");
		
//		employee.getPhoneNumbers().add("99991111");
//		employee.getPhoneNumbers().add("88882222");
//		employee.getPhoneNumbers().add("77773333");
		
		et.begin();
		
//		em.persist(employee);
//		em.persist(employee2);
//		em.persist(employee3);
//		em.persist(employee4);
//		em.persist(employee5);
//		em.persist(employee6);
//		
//		Employee empToChange = em.find(Employee.class, 1);
//		empToChange.setFirstName("JOKE1");
//		
//		Employee empToRemove = em.find(Employee.class, 2);
//		em.remove(empToRemove);
//		
//		Employee e = em.find(Employee.class, 1);
//		System.out.println(e.getPhoneNumbers());

//		Query query = em.createQuery("select e from Employee e where salary > ?1");
//		List<Employee> empList = query.setParameter(1, 3000.0).getResultList();
//		Query query = em.createQuery("select e from Employee e where e.firstName = :myName");
//		List<Employee> empList = query.setParameter("myName", "Monster").getResultList();
//		Query query = em.createQuery("select e from Employee e order by e.firstName");
//		List<Employee> empList = query.getResultList();
//		Query query = em.createQuery("select new Map(COUNT(empId), isPermanent) from Employee e group by e.isPermanent");
//		List<Object> empList = query.getResultList();
		Query query = em.createQuery("select new Map(e.firstName, CASE WHEN e.salary = 2000 THEN 'poorest' WHEN e.salary = 4000 THEN '4k' WHEN e.salary > 4000 THEN 'RICH AF' ELSE 'POOR' END) from Employee e");
		List<Object> empList = query.getResultList();
		
//		for (Employee e : empList)
//			System.out.println(e);
		for (Object e : empList)
			System.out.println(e);
		
		
		et.commit();
	}
}
