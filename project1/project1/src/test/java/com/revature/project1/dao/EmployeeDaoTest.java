package com.revature.project1.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.project1.customExceptions.EmployeeException;
import com.revature.project1.transportObjects.Employee;
import com.revature.project1.transportObjects.EmployeeComparator;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class EmployeeDaoTest {
	private static final EmployeeDao e = EmployeeDao.getEmployeeDao();
	Connection con;
	
	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	public EmployeeDaoTest() {
		try {
			con = ConnectionUtil.getConnectionFromFile("connection.properties");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setupDatabaseForTest() {
		try {
			PreparedStatement p = con.prepareStatement("DELETE FROM all_emp WHERE e_id <> 1");
			p.executeUpdate();
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
	public void getEmployeeByInvalidIdTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("EmployeeId Invalid");
		e.getEmployeeById(-1);
	}
	
	@Test
	public void getEmployeeByIdValidUsernameTest() throws SQLException {
		Employee employee = e.getEmployeeById(1);
		Employee em = e.getEmployeeByUsername(employee.getUsername());
		
		assertTrue(em.getUsername().equals("Username") 
				   && em.getFirstname().equals("Firstname") 
				   && em.getLastname().equals("Lastname") 
				   && em.getEmail().equals("Email") 
				   && em.getPassword().equals("Password"));
	}
	
	@Test
	public void getEmployeeByInvalidUsernameTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Username Invalid");
		e.getEmployeeByUsername("Fake Username");
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
	public void createEmployeeViolatesConstraintUsernameTooLongTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Username must be unique and below 51 characters.\n"
									  + "Password must be less than 51 characters.\n"
									  + "First and Last name must be less than 101 characters.\n"
									  + "Email must be less than 101 characters.");
		e.createEmployee("J1000ffffffffffffffffffffffffffffffffffffffdsffffffffffffffsdfsdfsfs", "Molly", "Moon", "d", "sdfsfdsd");
	}
	
	@Test
	public void createEmployeeViolatesConstraintUsernameNotUniqueTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Username must be unique and below 51 characters.\n"
									  + "Password must be less than 51 characters.\n"
									  + "First and Last name must be less than 101 characters.\n"
									  + "Email must be less than 101 characters.");
		e.createEmployee("Molly", "moon", "beam", "blue", "moon");
		e.createEmployee("Molly", "Molly", "Moon", "d", "sdfsfdsd");
	}
	
	@Test
	public void getAllEmployeesTest() throws SQLException {
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
		exception.expect(EmployeeException.class);
		exception.expectMessage("EmployeeId does not exist or employee could not be updated due to constraints.\n"
					  				  + "Username must be unique and below 51 characters.\n"
					  				  + "Password must be less than 51 characters.\n"
					  				  + "First and Last name must be less than 101 characters.\n"
					  				  + "Email must be less than 101 characters.");
		e.updateEmployee(-1, "Bla", "Bla", "Bla", "dd", "sdf");
	}
	
	@Test
	public void updateEmployeeInvalidUpdateUsernameNotUniqueTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("EmployeeId does not exist or employee could not be updated due to constraints.\n"
					  				  + "Username must be unique and below 51 characters.\n"
					  				  + "Password must be less than 51 characters.\n"
					  				  + "First and Last name must be less than 101 characters.\n"
					  				  + "Email must be less than 101 characters.");
		
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
		exception.expect(EmployeeException.class);
		exception.expectMessage("Invalid EmployeeId");
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceTest() throws SQLException {
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeUsername(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getUsername().equals("C"));
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceBadIdTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Invalid EmployeeId");
		e.updateEmployeeUsername(-1, "C");
	}
	
	@Test
	public void updateEmployeeUsernameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		
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
		exception.expect(EmployeeException.class);
		exception.expectMessage("Invalid EmployeeId");
		e.updateEmployeeFirstname(-1, "C");
	}
	
	@Test
	public void updateEmployeeFirstnameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		
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
		exception.expect(EmployeeException.class);
		exception.expectMessage("Invalid EmployeeId");
		e.updateEmployeeLastname(-1, "C");
	}
	
	@Test
	public void updateEmployeeLastnameConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeLastname(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCsdfsdfsdfsddsfdsfsdfsddfsdfsdfd" 
				+ "sdfdsfsdsfdsfdsdfdsfdsfCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
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
		exception.expect(EmployeeException.class);
		exception.expectMessage("Invalid EmployeeId");
		e.updateEmployeeUsername(-1, "C");
	}
	
	@Test
	public void updateEmployeeEmailConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeeEmail(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCsdfsdfsdfsddsfdsfsdfsddfsdfsdfd" 
													+ "sdfdsfsdsfdsfdsdfdsfdsfCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
	
	@Test
	public void updateEmployeePasswordConvenienceTest() throws SQLException {
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeePassword(em.getEmployeeId(), "C");
		
		assertTrue(e.getEmployeeById(em.getEmployeeId()).getPassword().equals("C"));
	}
	
	@Test
	public void updateEmployeePasswordConvenienceBadIdTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		e.updateEmployeePassword(-1, "C");
	}
	
	@Test
	public void updateEmployeePasswordConvenienceConstraintsViolatedTest() throws SQLException {
		exception.expect(EmployeeException.class);
		exception.expectMessage("Employee could not be updated due to constraints.\n"
				  + "Username must be unique and below 51 characters.\n"
				  + "Password must be less than 51 characters.\n"
				  + "First and Last name must be less than 101 characters.\n"
				  + "Email must be less than 101 characters.");
		
		e.createEmployee("T", "La", "LaLa", "La", "lala");
		Employee em = e.getEmployeeByUsername("T");
		e.updateEmployeePassword(em.getEmployeeId(), "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc");
	}
}
