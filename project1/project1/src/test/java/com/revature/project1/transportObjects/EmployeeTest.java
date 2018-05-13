package com.revature.project1.transportObjects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
	
	private static Employee e = new Employee(1, "Username", "Firstname", "Lastname", "Email", "Password");
	
	@Test
	public void getUsernameTest() {
		assertEquals("Username", e.getUsername());		
	}
	
	@Test
	public void getFirstnameTest() {
		assertEquals("Firstname", e.getFirstname());
	}
	
	@Test
	public void getLastnameTest() {
		assertEquals("Lastname", e.getLastname());
	}
	
	@Test
	public void getEmailTest() {
		assertEquals("Email", e.getEmail());
	}
	
	@Test
	public void getPasswordTest() {
		assertEquals("Password", e.getPassword());
	}
	
	@Test
	public void getEmployeeIdTest() {
		assertEquals(1, e.getEmployeeId());
	}
	
	@Test
	public void toStringTest() {
		String expected = "Employee Lastname, Firstname\n"
						+ "Username: Username\n"
						+ "EmployeeID: 1\n"
						+ "Email: Email\n";
		assertEquals(expected, e.toString());
	}
	
	@Test
	public void toStringWithParamTest() {
		String expected = "Employee # 1\n"
						+ "\tUsername: Username\n"
						+ "\tEmployee Lastname, Firstname\n"
						+ "\tEmployeeID: 1\n"
						+ "\tEmail: Email\n";
		assertEquals(expected, e.toString(1));
	}
	
	@Test
	public void equalsTest() {
		Employee e1 = new Employee(1, "U", "F", "L", "E", "P");
		Employee e2 = new Employee(2, "U", "F", "L", "E", "P");
		Employee e3 = new Employee(2, "U2", "F", "L", "E", "P");
		
		assertTrue(e1.equals(e2));
		assertTrue(!e1.equals(e3));
	}
}