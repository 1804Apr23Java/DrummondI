package com.revature.project1.dao;

import java.util.List;

import com.revature.project1.transportObjects.Employee;

public interface EmployeeDaoInterface {
	public Employee getEmployeeById(int Id);
	public Employee getEmployeeByUsername(String username);
	
	public List<Employee> getAllEmployees();
	
	public Employee createEmployee(String username, String firstname, String lastname, String email, String password);
	public Employee updateEmployee(int Id);
	public boolean deleteEmployee(int Id);
}
