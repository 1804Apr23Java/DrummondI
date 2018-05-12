package com.revature.project1.transportObjects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

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
		assertEquals("Firstname", e.getFirstname());
	}
	
	@Test
	public void testGetLastname() {
		assertEquals("Lastname", e.getLastname());
	}
	
	@Test
	public void testGetEmail() {
		assertEquals("Email", e.getEmail());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("Password", e.getPassword());
	}
	
	@Test
	public void testGetEmployeeId() {
		assertEquals(1, e.getEmployeeId());
	}
	
	@Test
	public void testToString() {
		String expected = "Employee Lastname, Firstname\n"
						+ "EmployeeID: 1\n"
						+ "Email: Email\n";
		assertEquals(expected, e.toString());
	}
	
	@Test
	public void testToStringWithParam() {
		String expected = "Employee # 1\n"
						+ "\tEmployee Lastname, Firstname\n"
						+ "\tEmployeeID: 1\n"
						+ "\tEmail: Email\n";
		assertEquals(expected, e.toString(1));
	}
}