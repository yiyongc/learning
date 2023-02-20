package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.pojo.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public void save(Employee employee) {
		employeeDao.save(employee);
	}
	
	@Override
	public void saveAll(List<Employee> employees) {
		employeeDao.save(employees);
	}

	@Override
	public void delete(int empId) {
		employeeDao.delete(empId);
	}

	@Override
	public void delete(Employee employee) {
		employeeDao.delete(employee);
	}

	@Override
	public void update(Employee employee) {
		Employee emp = findOne(employee.getEmpId());
		
		if (emp == null)
			return;
		
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setDateOfBirth(employee.getDateOfBirth());
		emp.setAddress(employee.getAddress());
		emp.setEmail(employee.getEmail());
		emp.setSalary(employee.getSalary());
		emp.setGender(employee.getGender());
	}

	@Override
	public Employee findOne(int id) {
		return employeeDao.findOne(id);
	}

	@Override
	public List<Employee> findAll() {
		return (List<Employee>) employeeDao.findAll();
	}

	@Override
	public void deleteAll() {
		employeeDao.deleteAll();
	}

	@Override
	public List<Employee> findByFirstName(String firstName) {
		return employeeDao.findByFirstName(firstName);
	}

	@Override
	public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
		return employeeDao.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public List<Employee> findByFirstNameIgnoreCase(String firstName) {
		return employeeDao.findByFirstNameIgnoreCase(firstName);
	}

	@Override
	public List<Employee> findByFirstNameOrSalary(String firstName, double salary) {
		return employeeDao.findByFirstNameOrSalary(firstName, salary);
	}

	@Override
	public Employee getByEmpId(int empId) {
		return employeeDao.getByEmpId(empId);
	}

	@Override
	public List<Employee> readByGender(String gender) {
		return employeeDao.readByGender(gender);
	}

	@Override
	public long countByGender(String gender) {
		return employeeDao.countByGender(gender);
	}

	@Override
	public List<Employee> findDistinctByGender(String gender) {
		return employeeDao.findDistinctByGender(gender);
	}

	@Override
	public List<Employee> findFirstBySalary(double salary) {
		return employeeDao.findFirstBySalary(salary);
	}

	@Override
	public List<Employee> findFirst3BySalary(double salary) {
		return employeeDao.findFirst3BySalary(salary);
	}

	@Override
	public List<Employee> findFirst5ByGenderOrderBySalaryDesc(String gender) {
		return employeeDao.findFirst5ByGenderOrderBySalaryDesc(gender);
	}

	@Override
	public List<Employee> findTop3ByGenderOrderBySalaryAsc(String gender) {
		return employeeDao.findTop3ByGenderOrderBySalaryAsc(gender);
	}

	@Override
	public List<Employee> findByGenderOrderBySalaryDescFirstNameAsc(String gender) {
		return employeeDao.findByGenderOrderBySalaryDescFirstNameAsc(gender);
	}

	@Override
	public List<Employee> findByFirstNameContainsIgnoreCase(String firstName) {
		return employeeDao.findByFirstNameContainsIgnoreCase(firstName);
	}

	@Override
	public List<Employee> findByFirstNameContainsOrAddressContainsAllIgnoreCaseOrderBySalaryDesc(String firstName,
			String address) {
		return employeeDao.findByFirstNameContainsOrAddressContainsAllIgnoreCaseOrderBySalaryDesc(firstName, address);
	}
	
	@Override
	public List<Employee> findByUsingQuery(double salary) {
		return employeeDao.findByUsingQuery(salary);
	}

	@Override
	public List<Employee> findByDateOfBirth(Date dateOfBirth) {
		return employeeDao.findByDateOfBirth(dateOfBirth);
	}

	@Override
	public List<Employee> searchForEmployee(double salary) {
		return employeeDao.searchForEmployee(salary);
	}
	

	
}
