package com.capgemini.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Employee;

@Repository("employeeDao")
public interface EmployeeDao extends CrudRepository<Employee, Integer>{

	public List<Employee> findByFirstName(String firstName);

	public List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

	public List<Employee> findByFirstNameIgnoreCase(String firstName);

	public List<Employee> findByFirstNameOrSalary(String firstName, double salary);

	public Employee getByEmpId(int empId);

	public List<Employee> readByGender(String gender);

	public long countByGender(String gender);

	public List<Employee> findDistinctByGender(String gender);
	
	public List<Employee> findFirstBySalary(double salary);

	public List<Employee> findFirst3BySalary(double salary);

	public List<Employee> findFirst5ByGenderOrderBySalaryDesc(String gender);
	
	public List<Employee> findTop3ByGenderOrderBySalaryAsc(String gender);

	public List<Employee> findByGenderOrderBySalaryDescFirstNameAsc(String gender);

	public List<Employee> findByFirstNameContainsIgnoreCase(String firstName);

	public List<Employee> findByFirstNameContainsOrAddressContainsAllIgnoreCaseOrderBySalaryDesc(String firstName,
			String address);
	
	@Query("select e from Employee e where e.salary > :salValue")
	public List<Employee> findByUsingQuery(@Param("salValue") double salary);
	
	public List<Employee> findByDateOfBirth(@Param("empDateOfBirth") Date empDob);
	
	@Query(nativeQuery=true)
	public List<Employee> searchForEmployee(@Param("sal") double salary);
}
