package com.revature.project1.dao;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.revature.project1.connectionUtil.ConnectionUtil;
import com.revature.project1.transportObjects.Employee;

public class EmployeeDaoTest {
	private static final EmployeeDao e = new EmployeeDao();
	Connection con;
	
	public EmployeeDaoTest() {
		try {
			con = ConnectionUtil.getConnectionFromFile("connection.properties");
		} catch(Exception e) {
			
		}
	}
	
	@Before
	public void setupDatabaseForTests() {
		try {
			con.setAutoCommit(true);
			String sql = "DELETE FROM all_emp WHERE e_id <> 1";
			PreparedStatement p = con.prepareStatement(sql);
			int affectedRows = p.executeUpdate();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetEmployeeById() throws SQLException {
		assertEquals(1, e.getEmployeeById(1).getEmployeeId());
	}
	
	@Test
	public void testGetEmployeeByIdValidUsername() throws SQLException {
		Employee em = e.getEmployeeById(1);
		assertTrue(em.getUsername().equals("Username") 
				   && em.getFirstname().equals("Firstname") 
				   && em.getLastname().equals("Lastname") 
				   && em.getEmail().equals("Email") 
				   && em.getPassword().equals("Password"));
	}
	
	@Test
	public void testGetEmployeeByUsername() throws SQLException {
		assertEquals(1, e.getEmployeeByUsername("Username").getEmployeeId());
	}
	
	@Test
	public void testGetEmployeeByUsernameValidFields() throws SQLException {
		Employee em = e.getEmployeeByUsername("Username");
		assertTrue(em.getUsername().equals("Username") 
				   && em.getFirstname().equals("Firstname") 
				   && em.getLastname().equals("Lastname") 
				   && em.getEmail().equals("Email") 
				   && em.getPassword().equals("Password"));
	}	
}
