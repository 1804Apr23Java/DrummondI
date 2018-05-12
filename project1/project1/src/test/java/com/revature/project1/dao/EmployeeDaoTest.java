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
	
	@Before
	public void setupDatabaseForTests() {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile("connection.properties");
			con.setAutoCommit(true);
			PreparedStatement p = con.prepareStatement("DELETE FROM EMPLOYEE");
			
			boolean affectedRows = p.execute();
			System.out.println("affected rows  = " + affectedRows);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In setupDatabaseForTests");
		} catch(IOException e) {
			System.out.println(e.getMessage());
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
