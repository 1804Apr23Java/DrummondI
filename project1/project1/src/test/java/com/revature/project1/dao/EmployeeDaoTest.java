package com.revature.project1.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.project1.transportObjects.Employee;
import com.revature.project1.transportObjects.EmployeeComparator;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class EmployeeDaoTest {
	private EmployeeDao e = null;
	private Connection con = null;
	
	private static final String bigErrorMessage = "Employee could not be updated due to constraints.\n"
			  + "Username must be unique and below 51 characters.\n"
			  + "Password must be less than 51 characters.\n"
			  + "First and Last name must be less than 101 characters.\n"
			  + "Email must be less than 101 characters.";
	
	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	public EmployeeDaoTest() {
		try {
			con = ConnectionUtil.getConnectionFromFile("connection.properties");
			
			PreparedStatement p = con.prepareStatement("DELETE FROM all_emp");
			p.executeUpdate();
			
			e = EmployeeDao.getEmployeeDao("connection.properties");
		} catch(IOException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setupDatabaseForTest() {
		try {
			PreparedStatement p = con.prepareStatement("DELETE FROM all_emp");
			p.executeUpdate();
			
			p = con.prepareStatement("DELETE FROM managers");
			p.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getEmployeeByIdTest() throws SQLException {
		Employee emp = e.createEmployee("Username", "Firstname", "Lastname", "Email", "Password");
		Employee test = e.getEmployeeById(emp.getEmployeeId());
		
		assertEquals(emp.getEmployeeId(), test.getEmployeeId());
		assertEquals(emp.getUsername(), test.getUsername());
		assertEquals(emp.getFirstname(), test.getFirstname());
		assertEquals(emp.getLastname(), test.getLastname());
		assertEquals(emp.getEmail(), test.getEmail());
		assertEquals(emp.getPassword(), test.getPassword());
	}
	
	@Test
	public void getEmployeeByInvalidIdTest() throws SQLException {
		assertNull(e.getEmployeeById(-1));
	}
	
	@Test
	public void getEmployeeByValidUsernameTest() throws SQLException {
		Employee emp = e.createEmployee("Username", "Firstname", "Lastname", "Email", "Password");
		Employee test = e.getEmployeeByUsername(emp.getUsername());
		
		assertEquals(test.getUsername(), emp.getUsername());
		assertEquals(test.getFirstname(), emp.getFirstname());
		assertEquals(test.getLastname(), emp.getLastname());
		assertEquals(test.getEmail(), emp.getEmail());
		assertEquals(test.getPassword(), emp.getPassword());
	}
	
	@Test
	public void getEmployeeByInvalidUsernameTest() throws SQLException {
		assertNull(e.getEmployeeByUsername("Fake Username"));
	}
	
	@Test
	public void createEmployeeTest() throws SQLException {
		e.createEmployee("J1000", "Molly", "Moon", "d", "sdfsfdsd");
		assertEquals(e.getEmployeeByUsername("J1000").getFirstname(), "Molly");
	}
	
	@Test
	public void createEmployeeViolatesConstraintUsernameTooLongTest() throws SQLException {
		exception.expect(SQLException.class);
		e.createEmployee("J1000ffffffffffffffffffffffffffffffffffffffdsffffffffffffffsdfsdfsfs", "Molly", "Moon", "d", "sdfsfdsd");
	}
	
	@Test
	public void createEmployeeViolatesConstraintUsernameNotUniqueTest() throws SQLException {
		exception.expect(SQLException.class);
		e.createEmployee("Molly", "moon", "beam", "blue", "moon");
		e.createEmployee("Molly", "Molly", "Moon", "d", "sdfsfdsd");
	}
	
	@Test
	public void getAllEmployeesTest() throws SQLException {
		List<Employee> eList = new ArrayList<Employee>();
		
		for(int i = 0; i < 10; i++) {
			eList.add(new Employee(i, "Username" + i, "Firstname" + i, "Lastname" + i, "Email" + i, "Password" + i));
			try {
				e.createEmployee("Username" + i, "Firstname" + i, "Lastname" + i, "Email" + i, "Password" + i);
			} catch(SQLException e) {
				
			}
		}
		
		List<Employee> allEmployees = e.getAllEmployees();
		allEmployees.sort(new EmployeeComparator<Employee>());
		eList.sort(new EmployeeComparator<Employee>());
		assertTrue(allEmployees.equals(eList));
	}
	
	@Test
	public void updateEmployeeTest() throws SQLException {
		e.createEmployee("Lalala", "La", "LaLa", "La", "lala");
		
		Employee em = e.getEmployeeByUsername("Lalala");
		assertTrue(e.updateEmployee(em.getEmployeeId(), "Bla", "Bla", "Bla", "dd", "sdf"));
		
		Employee e2 = e.getEmployeeById(em.getEmployeeId());
		assertEquals("Bla", e2.getUsername());
	}
	
	@Test
	public void updateEmployeeInvalidUpdateTest() throws SQLException {
		assertFalse(e.updateEmployee(-1, "Bla", "Bla", "Bla", "dd", "sdf"));
	}
	
	@Test
	public void updateEmployeeInvalidUpdateUsernameNotUniqueTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("Lalala", "La", "LaLa", "La", "lala");
		e.createEmployee("Lalala2", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("Lalala");
		e.updateEmployee(em.getEmployeeId(), "Lalala2", "Bla", "Bla", "dd", "sdf");
	}
	
	@Test
	public void deleteEmployeeTest() throws SQLException {
		e.createEmployee("Lalala", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("Lalala");
		assertTrue(e.deleteEmployee(em.getEmployeeId()));
	}
	
	@Test
	public void invalidDeleteEmployeeTest() throws SQLException {
		assertFalse(e.deleteEmployee(-1));
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceTest() throws SQLException {
		e.createEmployee("Tasj", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("Tasj");
		e.updateEmployeeUsername(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getUsername().equals("C"));
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceBadIdTest() throws SQLException {
		assertFalse(e.updateEmployeeUsername(-1, "C"));
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeUsername(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void updateEmployeeFirstnameConvenienceTest() throws SQLException {
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeFirstname(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getFirstname().equals("C"));
	}
	
	@Test
	public void updateEmployeeFirstnameConvenienceBadIdTest() throws SQLException {
		assertFalse(e.updateEmployeeFirstname(-1, "C"));
	}
	
	@Test
	public void updateEmployeeFirstnameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeFirstname(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCsdfsdfsdfsddsfdsfsdfsddfsdfsdfd" 
				+ "sdfdsfsdsfdsfdsdfdsfdsfCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void updateEmployeeLastnameConvenienceTest() throws SQLException {
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeLastname(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getLastname().equals("C"));
	}
	
	@Test
	public void updateEmployeeLastnameConvenienceBadIdTest() throws SQLException {
		assertFalse(e.updateEmployeeLastname(-1, "C"));
	}
	
	@Test
	public void updateEmployeeLastnameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeLastname(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCsdfsdfsdfsddsfdsfsdfsddfsdfsdfd" 
												   + "sdfdsfsdsfdsfdsdfdsfdsfCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void updateEmployeeEmailConvenienceTest() throws SQLException {
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeEmail(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getEmail().equals("C"));
	}
	
	@Test
	public void updateEmployeeEmailConvenienceBadIdTest() throws SQLException {
		assertFalse(e.updateEmployeeUsername(-1, "C"));
	}
	
	@Test
	public void updateEmployeeEmailConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeEmail(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCsdfsdfsdfsddsfdsfsdfsddfsdfsdfd" 
												+ "sdfdsfsdsfdsfdsdfdsfdsfCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void updateEmployeePasswordConvenienceTest() throws SQLException {
		e.createEmployee("QED", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("QED");
		e.updateEmployeePassword(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getPassword().equals("C"));
	}
	
	@Test
	public void updateEmployeePasswordConvenienceBadIdTest() throws SQLException {
		assertFalse(e.updateEmployeePassword(-1, "C"));
	}
	
	@Test
	public void updateEmployeePasswordConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(SQLException.class);
		
		e.createEmployee("TTTT", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("TTTT");
		e.updateEmployeePassword(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void isManagerTest() throws SQLException {
		Employee emp = e.createEmployee("A", "A", "A", "A", "A");
		assertTrue(e.addManager(emp.getEmployeeId()));
		assertTrue(e.isManager(emp.getEmployeeId()));
	}
	
	@Test
	public void isManagerFakeEmployeeTest() throws SQLException {
		assertFalse(e.isManager(-1));
	}
	
	@Test
	public void deleteManagerTest() throws SQLException {
		Employee emp = e.createEmployee("A", "A", "A", "A", "A");
		assertTrue(e.addManager(emp.getEmployeeId()));
		assertTrue(e.deleteManager(emp.getEmployeeId()));
	}
	
	@Test
	public void fakeEmployeeDeleteTest() throws SQLException {
		assertFalse(e.deleteManager(-1));
	}
}
