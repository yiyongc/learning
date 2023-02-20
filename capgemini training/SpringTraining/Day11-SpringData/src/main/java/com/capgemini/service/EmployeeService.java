package com.capgemini.service;

import java.util.Date;
import java.util.List;

import com.capgemini.pojo.Employee;

public interface EmployeeService {
	
	public void save(Employee employee);
	
	public void delete(int empId);
	
	public void delete(Employee employee);
	
	public void deleteAll();
	
	public void update(Employee employee);

	public void saveAll(List<Employee> employees);

	public Employee findOne(int id);
	
	public List<Employee> findAll();
	
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

	public List<Employee> findByFirstNameContainsOrAddressContainsAllIgnoreCaseOrderBySalaryDesc(String firstName, String address);

	public List<Employee> findByUsingQuery(double salary);
	
	public List<Employee> findByDateOfBirth(Date dateOfBirth);
	
	public List<Employee> searchForEmployee(double salary);
}
