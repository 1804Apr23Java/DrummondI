package com.revature.project1.transportObjects;

import java.util.Comparator;

/**
 * Comparator for the Employee class.
 * @author Ian
 */
public class EmployeeComparator<T extends Employee> implements Comparator<T> {

	/**
	 * Compares two employees.
	 * @param e1 The first employee to compare.
	 * @param e2 The second employee to compare.
	 * @return -1 if the first employee's user name is lexicographically smaller, 0 if equal, and 1 if larger.
	 */
	@Override
	public int compare(Employee e1, Employee e2) {
		int r = e1.getUsername().compareToIgnoreCase(e2.getUsername());
		return(r < 0) ? -1 : ((r > 0) ? 1 : 0);
	}
}
