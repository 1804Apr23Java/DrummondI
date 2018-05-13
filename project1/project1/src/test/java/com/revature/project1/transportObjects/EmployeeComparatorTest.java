package com.revature.project1.transportObjects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmployeeComparatorTest {
	
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
}