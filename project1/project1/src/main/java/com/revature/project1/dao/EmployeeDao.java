package com.revature.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.project1.connectionUtil.ConnectionUtil;
import com.revature.project1.transportObjects.Employee;

public class EmployeeDao implements EmployeeDaoInterface {

	@Override
	public Employee getEmployeeById(int Id) throws SQLException {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
			
			String sql = "SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp where e_id = ?";
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, Id);
			
			ResultSet results = p.executeQuery();
			if(results.next()) {
				Employee e = new Employee(results.getInt("e_id"), 
										  results.getString("e_un"),
										  results.getString("e_fn"),
										  results.getString("e_ln"),
										  results.getString("e_em"),
										  results.getString("e_pw"));
				con.close();
				return e;
			} else {
				con.close();
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Employee getEmployeeByUsername(String username) throws SQLException {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
			
			String sql = "SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp where e_un = ?";
			PreparedStatement p = con.prepareStatement(sql);
			p.setString(1, username);
			
			ResultSet results = p.executeQuery();
			if(results.next()) {
				Employee e = new Employee(results.getInt("e_id"), 
										  results.getString("e_un"),
										  results.getString("e_fn"),
										  results.getString("e_ln"),
										  results.getString("e_em"),
										  results.getString("e_pw"));
				con.close();
				return e;
			} else {
				con.close();
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
