package com.revature.project1.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.project1.transportObjects.Employee;

/**
 * DAO pattern interface for accessing the employee information in a database.
 * 
 * @author Ian
 */
public interface EmployeeDaoInterface {
	public Employee getEmployeeById(int Id) throws SQLException;
	public Employee getEmployeeByUsername(String username) throws SQLException;
	public List<Employee> getAllEmployees() throws SQLException;
	public Employee createEmployee(String username, String firstname, 
								   String lastname, String email, String password) throws SQLException;
	public boolean updateEmployee(int Id, String username, String firstname, String lastname, String email,
								   String password) throws SQLException;
	public boolean updateEmployeeUsername(int Id, String username) throws SQLException;
	public boolean updateEmployeeFirstname(int Id, String firstname) throws SQLException;
	public boolean updateEmployeeLastname(int Id, String lastname) throws SQLException;
	public boolean updateEmployeeEmail(int Id, String email) throws SQLException;
	public boolean updateEmployeePassword(int Id, String password) throws SQLException;
	public boolean deleteEmployee(int Id) throws SQLException;
	
	public boolean isManager(int employeeId) throws SQLException;
	public boolean addManager(int employeeId) throws SQLException;
	public boolean deleteManager(int employeeId) throws SQLException;
}
