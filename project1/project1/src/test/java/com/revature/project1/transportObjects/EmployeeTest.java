package com.revature.project1.transportObjects;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
	
	private static Employee e = new Employee(1, "Username", "Firstname", "Lastname", "Email", "Password");
	
	@Test
	public void testGetUsername() {
		assertEquals("Username", e.getUsername());		
	}
	
	@Test
	public void testGetFirstname() {
		assertEquals("firstname", e.getFirstname());
	}
	
	@Test
	public void testGetLastname() {
		assertEquals("lastname", e.getLastname());
	}
	
	@Test
	public void testGetEmail() {
		assertEquals("email", e.getEmail());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("password", e.getPassword());
	}
	
	@Test
	public void testGetEmployeeId() {
		assertEquals(1, g.getEmployeeId());
	}
	
	@Test
	public void testToString() {
		String expected = "Employee Lastname, Firstname\n"
						+ "EmployeeID: 1\n"
						+ "Email: Email\n";
		assertEquals(expected, e.toString());
	}
}