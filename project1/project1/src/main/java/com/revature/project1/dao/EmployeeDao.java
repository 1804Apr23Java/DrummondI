package com.revature.project1.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.project1.transportObjects.Employee;

public class EmployeeDao implements EmployeeDaoInterface {

	@Override
	public Employee getEmployeeById(int Id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeByUsername(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee createEmployee(String username, String firstname, String lastname, String email, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateEmployee(int Id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteEmployee(int Id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
