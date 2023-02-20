package com.capgemini.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Table(name="employees")
@Entity
@SecondaryTable(name="employee_information")
public class Employee {
		
		@Id
		@GeneratedValue
		private int empId;
		
		@Column(nullable = false, length = 100)
		private String firstName;
		
		private String lastName;
		
		@Column(table="employee_information")
		private double salary;
		@Temporal(TemporalType.DATE)
		private Date dateOfBirth;
		@Temporal(TemporalType.DATE)
		@Column(table="employee_information")
		private Date dateOfJoining;
		private String email;
		@Column(table="employee_information")
		private boolean isPermanent;
		@Transient //Does not store in db (THEN FOR WHAT?)
		private String empPassword;
		
		public Employee() {}

		public Employee(String firstName, String lastName, double salary) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.salary = salary;
		}
		
		public Employee(String firstName, String lastName, double salary, Date dateOfBirth,
				Date dateOfJoining, String email, boolean isPermanent, String empPassword) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.salary = salary;
			this.dateOfBirth = dateOfBirth;
			this.dateOfJoining = dateOfJoining;
			this.email = email;
			this.isPermanent = isPermanent;
			this.empPassword = empPassword;
		}

		
		
		@Override
		public String toString() {
			return "Employee [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", salary="
					+ salary + ", dateOfBirth=" + dateOfBirth + ", dateOfJoining=" + dateOfJoining + ", email=" + email
					+ ", isPermanent=" + isPermanent + ", empPassword=" + empPassword + "]";
		}

		public int getEmpId() {
			return empId;
		}

		public void setEmpId(int empId) {
			this.empId = empId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public double getSalary() {
			return salary;
		}

		public void setSalary(double salary) {
			this.salary = salary;
		}

		public Date getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public Date getDateOfJoining() {
			return dateOfJoining;
		}

		public void setDateOfJoining(Date dateOfJoining) {
			this.dateOfJoining = dateOfJoining;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean isPermanent() {
			return isPermanent;
		}

		public void setPermanent(boolean isPermanent) {
			this.isPermanent = isPermanent;
		}

		public String getEmpPassword() {
			return empPassword;
		}

		public void setEmpPassword(String empPassword) {
			this.empPassword = empPassword;
		}
		
		
		

}
