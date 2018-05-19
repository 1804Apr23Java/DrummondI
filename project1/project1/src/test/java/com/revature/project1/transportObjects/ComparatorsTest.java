package com.revature.project1.transportObjects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComparatorsTest {
	
	@Test
	public void employeeComparatorTest() {
		EmployeeComparator<Employee> eComparator = new EmployeeComparator<>();
		
		Employee e1 = new Employee(1, "anna123", "F", "L", "E", "P");
		Employee e2 = new Employee(2, "bella", "F", "L", "E", "P");
		Employee e3 = new Employee(3, "Carter", "F", "L", "E", "P");
		Employee e4 = new Employee(4, "1992Jacob", "F", "L", "E", "P");
		Employee e5 = new Employee(5, "simon", "F", "L", "E", "P");
		
		assertEquals(-1, eComparator.compare(e1, e2));
		assertEquals(-1, eComparator.compare(e1, e3));
		assertEquals(1, eComparator.compare(e1, e4));
		assertEquals(0, eComparator.compare(e1, e1));
		assertEquals(1, eComparator.compare(e5, e2));
	}
	
	@Test
	public void requestComparatorTest() {
		RequestComparator<Request> rComparator = new RequestComparator<>();
		
		Request r1 = new Request(1, 1, 2.0, "stat", null, null);
		Request r2 = new Request(1, -1, 2.0, "stat", null, null);
		Request r3 = new Request(1, 2, 2.0, "stat", null, null);
		
		assertEquals(-1, rComparator.compare(r1, r3));
		assertEquals(-1, rComparator.compare(r2, r1));
		assertEquals(0, rComparator.compare(r1, r1));
		assertEquals(1, rComparator.compare(r1, r2));
	}
}