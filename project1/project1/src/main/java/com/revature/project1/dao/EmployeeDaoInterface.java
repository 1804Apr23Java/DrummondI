package com.revature.project1.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.project1.transportObjects.Employee;

public interface EmployeeDaoInterface {
	public Employee getEmployeeById(int Id) throws SQLException;
	public Employee getEmployeeByUsername(String username) throws SQLException;
	public List<Employee> getAllEmployees();
	public Employee createEmployee(String username, String firstname, 
								   String lastname, String email, String password) throws SQLException;
	public Employee updateEmployee(int Id) throws SQLException;
	public boolean deleteEmployee(int Id) throws SQLException;
}
