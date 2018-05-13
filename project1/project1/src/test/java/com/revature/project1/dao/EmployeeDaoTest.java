package com.revature.project1.dao;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.project1.transportObjects.Employee;
import com.revature.project1.transportObjects.EmployeeComparator;
import com.revature.project1.utilityClasses.ConnectionUtil;

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
	public void setupDatabaseForTest() {
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
	public void getEmployeeByIdTest() throws SQLException {
		assertEquals(1, e.getEmployeeById(1).getEmployeeId());
	}
	
	@Test
	public void getEmployeeByIdValidUsernameTest() throws SQLException {
		Employee em = e.getEmployeeById(1);
		assertTrue(em.getUsername().equals("Username") 
				   && em.getFirstname().equals("Firstname") 
				   && em.getLastname().equals("Lastname") 
				   && em.getEmail().equals("Email") 
				   && em.getPassword().equals("Password"));
	}
	
	@Test
	public void getEmployeeByUsernameTest() throws SQLException {
		assertEquals(1, e.getEmployeeByUsername("Username").getEmployeeId());
	}
	
	@Test
	public void getEmployeeByUsernameValidFieldsTest() throws SQLException {
		Employee em = e.getEmployeeByUsername("Username");
		assertTrue(em.getUsername().equals("Username") 
				   && em.getFirstname().equals("Firstname") 
				   && em.getLastname().equals("Lastname") 
				   && em.getEmail().equals("Email") 
				   && em.getPassword().equals("Password"));
	}
	
	@Test
	public void createEmployeeTest() throws SQLException {
		e.createEmployee("J1000", "Molly", "Moon", "d", "sdfsfdsd");
		assertEquals(e.getEmployeeByUsername("J1000").getFirstname(), "Molly");
	}
	
	@Test
	public void getAllEmployeesTest() {
		List<Employee> eList = new ArrayList<Employee>();
		eList.add(new Employee(1, "Username", "Firstname", "Lastname", "Email", "Password"));
		
		for(int i = 0; i < 10; i++) {
			eList.add(new Employee(i, "Username" + i, "Firstname" + i, "Lastname" + i, "Email" + i, "Password" + i));
			try {
				e.createEmployee("Username" + i, "Firstname" + i, "Lastname" + i, "Email" + i, "Password" + i);
			} catch(SQLException e) {
				
			}
		}
		
		List<Employee> allEmployees = e.getAllEmployees();
		//allEmployees.sort(new EmployeeComparator<Employee>());
		//eList.sort(new EmployeeComparator<Employee>());
		assertTrue(allEmployees.equals(eList));
	}
}
