package com.revature.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.transportObjects.Employee;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class EmployeeDao implements EmployeeDaoInterface {
	private Connection con;
	
	/**
	 * Private constructor for the EmployeeDao class.
	 */
	private EmployeeDao() {
		con = null;
	}
	
	/**
	 * Returns an EmployeeDao object.
	 * @return an EmployeeDao.
	 */
	public static EmployeeDao getEmployeeDao() {
		try {
			EmployeeDao e = new EmployeeDao();
			e.con = ConnectionUtil.getConnectionFromFile("connection.properties");
			e.con.setAutoCommit(true);
			return e;
		} catch(IOException ex) {
			return null;
		} catch(SQLException ex) {
			return null;
		}
	}
	
	
	/**
	 * Retrieve an Employee based on their unique EmployeeId.
	 * @param Id the unique employee Id belonging to the employee.
	 * @return an Employee object representing the employee.
	 * @Exception EmployeeException if employeeId does not exist
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public Employee getEmployeeById(int Id) throws SQLException {
		return getEmployee(1, "" + Id);
	}

	/**
	 * Retrieves an Employee based on their unique username.
	 * @param username the employee's unique username - case sensitive.
	 * @return the employee object that represents the employee.
	 * @exception SQLException if the SQL query causes an error.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public Employee getEmployeeByUsername(String username) throws SQLException {
		return getEmployee(2, username);
	}
	
	/**
	 * Private method used to query the all_emp table based on a single column and value.
	 * @param column the table column in all_emp table; 1 for e_id and anything else for e_un.
	 * @param value the value of the table column in all_emp.
	 * @return the Employee object representing the employee.
	 * @exception SQLException if a database constraint was violated.
	 */
	private Employee getEmployee(int column, String value) throws SQLException {
		String sql = (column == 1) ? "SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp where e_id = ?" :
									 "SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp where e_un = ?";
		PreparedStatement p = con.prepareStatement(sql);
		p.setString(1, value);
		
		ResultSet r = p.executeQuery();
		if(r.next()) {
			Employee e = new Employee(r.getInt("e_id"),  r.getString("e_un"), r.getString("e_fn"), 
									  r.getString("e_ln"), r.getString("e_em"), r.getString("e_pw"));
			return e;
		}
		
		return null;
	}

	/**
	 * Returns a list of every employee in the all_emp table.
	 * @return a list of all employees.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public List<Employee> getAllEmployees() throws SQLException {
		ArrayList<Employee> list = new ArrayList<>();
		PreparedStatement p = con.prepareStatement("SELECT e_id, e_un, e_pw, e_fn, e_ln, e_em FROM all_emp");
			
		ResultSet r = p.executeQuery();
		while(r.next()) {
			Employee e = new Employee(r.getInt("e_id"), r.getString("e_un"), r.getString("e_fn"),
									  r.getString("e_ln"), r.getString("e_em"), r.getString("e_pw"));
			list.add(e);
		}
			
		return list;
	}

	/**
	 * Creates an employee and inserts the employee into the database.
	 * @param username the unique employee username.
	 * @param firstname the employee's first name.
	 * @param lastname the employee's last name.
	 * @param email the employee's email.
	 * @param password the employee's password.
	 * @return an Employee object representing the employee.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public Employee createEmployee(String username, String firstname, String lastname, String email, String password) throws SQLException {
		PreparedStatement p = con.prepareStatement("INSERT INTO all_emp(e_un, e_pw, e_fn, e_ln, e_em) VALUES (?, ?, ?, ?, ?)");
		p.setString(1, username);
		p.setString(2, password);
		p.setString(3, firstname);
		p.setString(4, lastname);
		p.setString(5, email);
		
		p.executeUpdate();
		
		p = con.prepareStatement("SELECT e_id, e_un, e_fn, e_ln, e_em, e_pw FROM all_emp WHERE e_un = ?");
		p.setString(1, username);
		
		ResultSet r = p.executeQuery();
		if(r.next()) {
			Employee e = new  Employee(r.getInt("e_id"), r.getString("e_un"), r.getString("e_fn"), 
									   r.getString("e_ln"), r.getString("e_em"), r.getString("e_pw"));
			return e;
		}
		
		return null;
	}
	
	/**
	 * Updates an employee's information in the employee all_emp table.
	 * @param Id the employee's unique identifier.
	 * @param username the employee's username.
	 * @param firstname the employee's firstname.
	 * @param lastname the employee's lastname.
	 * @param email the employee's email.
	 * @param password the employee's password.
	 * @return true if the employee updated successfully, will throw an error otherwise.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployee(int Id, String username, String firstname, String lastname, String email, String password) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_un = ?, e_fn = ?, e_ln = ?, e_em = ?, e_pw = ? WHERE e_id = ?");
		p.setString(1, username);
		p.setString(2, firstname);
		p.setString(3, lastname);
		p.setString(4, email);
		p.setString(5, password);
		p.setInt(6, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}

	/**
	 * Deletes an employee from the employees table.
	 * @param Id the unique identifier for the employee to be deleted.
	 * @return true if the employee was deleted.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean deleteEmployee(int Id) throws SQLException {
		PreparedStatement p = con.prepareStatement("DELETE FROM all_emp WHERE e_id = ?");
		p.setInt(1, Id);
		
		int rowsDeleted = p.executeUpdate();
		return (rowsDeleted == 1) ? true : false;
	}

	/**
	 * Convenience method for updating employee username.
	 * @param Id the unique employee identifier.
	 * @param username The new username for the employee.
	 * @return true if the update was successful.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployeeUsername(int Id, String username) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_un = ? WHERE e_id = ?");
		p.setString(1, username);
		p.setInt(2, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}

	/**
	 * Convenience method for updating employee firstname.
	 * @param Id the unique employee identifier.
	 * @param firstname The new firstname for the employee.
	 * @return true if the update was successful.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployeeFirstname(int Id, String firstname) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_fn = ? WHERE e_id = ?");
		p.setString(1, firstname);
		p.setInt(2, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}

	/**
	 * Convenience method for updating employee lastname.
	 * @param Id the unique employee identifier.
	 * @param lastname The new lastname for the employee.
	 * @return true if the update was successful.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployeeLastname(int Id, String lastname) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_ln = ? WHERE e_id = ?");
		p.setString(1, lastname);
		p.setInt(2, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true: false;
	}

	/**
	 * Convenience method for updating employee email.
	 * @param Id the unique employee identifier.
	 * @param email The new email for the employee.
	 * @return true if the update was successful.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployeeEmail(int Id, String email) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_em = ? WHERE e_id = ?");
		p.setString(1, email);
		p.setInt(2, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}

	/**
	 * Convenience method for updating employee password.
	 * @param Id the unique employee identifier.
	 * @param password The new password for the employee.
	 * @return true if the update was successful.
	 * @exception SQLException if a database constraint was violated.
	 */
	@Override
	public boolean updateEmployeePassword(int Id, String password) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE all_emp SET e_pw = ? WHERE e_id = ?");
		p.setString(1, password);
		p.setInt(2, Id);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}
}
