package com.revature.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.transportObjects.Employee;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class EmployeeDao implements EmployeeDaoInterface {
	
	/**
	 * 
	 * 
	 */
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
		ArrayList<Employee> list = new ArrayList<>();
		
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
			
			String sql = "SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp";
			PreparedStatement p = con.prepareStatement(sql);
			
			ResultSet results = p.executeQuery();
			while(results.next()) {
				Employee e = new Employee(results.getInt("e_id"), 
										  results.getString("e_un"),
										  results.getString("e_fn"),
										  results.getString("e_ln"),
										  results.getString("e_em"),
										  results.getString("e_pw"));
				list.add(e);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Employee createEmployee(String username, String firstname, String lastname, String email, String password) throws SQLException {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
			
			String sql = "INSERT INTO all_emp(e_un, e_pw, e_fn, e_ln, e_em) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement p = con.prepareStatement(sql);
			p.setString(1, username);
			p.setString(2, password);
			p.setString(3, firstname);
			p.setString(4, lastname);
			p.setString(5, email);
			
			
			int result = p.executeUpdate();
			if(result != 1) {
				//TODO
			}
			
			sql = "SELECT e_id, e_un, e_fn, e_ln, e_em, e_pw FROM all_emp WHERE e_un = ?";
			p = con.prepareStatement(sql);
			p.setString(1, username);
			
			ResultSet results = p.executeQuery();
			if(results.next()) {
				Employee e = new  Employee(results.getInt("e_id"), 
						  results.getString("e_un"),
						  results.getString("e_fn"),
						  results.getString("e_ln"),
						  results.getString("e_em"),
						  results.getString("e_pw"));
				con.close();
				return e;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateEmployee(int Id, String username, String firstname, String lastname, String email, String password) throws SQLException {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
				
			String sql = "UPDATE all_emp SET e_un = ?, e_fn = ?, e_ln = ?, e_em = ?, e_pw = ? WHERE e_id = ?";
			
			PreparedStatement p = con.prepareStatement(sql);
			p.setString(1, username);
			p.setString(2, firstname);
			p.setString(3, lastname);
			p.setString(4, email);
			p.setString(5, password);
			p.setInt(6, Id);
			
			
			int result = p.executeUpdate();
			if(result != 1) {
				return false;
			}
			
			con.close();
			return true;
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteEmployee(int Id) throws SQLException {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
				
			String sql = "DELETE FROM all_emp WHERE e_id = ?";
			
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, Id);
			
			int result = p.executeUpdate();
			if(result != 1) {
				return false;
			}
			
			con.close();
			return true;
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
}
