package com.revature.project1.transportObjects;

/**
 * Transport object (DAO design pattern) for employee information.
 * 
 * @author Ian
 */
public class Employee {
	private int employeeId;
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	/**
	 * Constructs an Employee object.
	 * 
	 * @param userId the employee's unique id
	 * @param username the employee's unique username
	 * @param firstname the employee's first name
	 * @param lastname the employee's last name
	 * @param email the employee's email
	 * @param password the employee's password hash
	 */
	public Employee(int userId, String username, String firstname, String lastname, String email, String password) {
		super();
		this.employeeId = userId;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}

	/**
	 * Constructs an Employee object without the employee's Id.
	 * 
	 * @param username the employee's unique username
	 * @param firstname the employee's first name
	 * @param lastname the employee's last name
	 * @param email the employee's email
	 * @param password the employee's password hash
	 */
	public Employee(String username, String firstname, String lastname, String email, String password) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}

	/**
	 * @return employee's unique Id.
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @return employee's unique username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return employee's firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return employee's lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @return employee's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return employee's password hash
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns a String representation of the employee.
	 * 
	 * @return the String representation of the employee.
	 */
	@Override
	public String toString() {
		return "Employee " + lastname + ", " + firstname + "\n"
			 + "Username: " + username + "\n"
			 + "EmployeeID: " + employeeId + "\n"
			 + "Email: " + email + "\n";
	}
	
	/**
	 * Returns a String representation of the employee with a list position index; meant
	 * for printing employee's in list
	 * 
	 * @param i the list position index.
	 * @return the String representation of the employee.
	 */
	public String toString(int i) {
		return "Employee # " + i + "\n"
				+ "\tUsername: " + username + "\n"
				+ "\tEmployee " + lastname + ", " + firstname + "\n"
				+ "\tEmployeeID: " + employeeId + "\n"
				+ "\tEmail: " + email + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
