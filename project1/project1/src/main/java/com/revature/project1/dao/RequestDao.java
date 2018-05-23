package com.revature.project1.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.transportObjects.Request;
import com.revature.project1.utilityClasses.ConnectionUtil;

/**
 * DAO pattern object for querying and accessing Requests table.
 * @author Ian
 *
 */
public class RequestDao implements RequestDaoInterface {
	private Connection con;
	
	/**
	 * Private constructor for RequestDao
	 */
	private RequestDao() {
		con = null;
	}
	
	/**
	 * Gets a RequestDao
	 * @return the RequestDao, or null if there were errors establishing the database connection.
	 */
	public static RequestDao getRequestDao() {
		try {
			RequestDao r = new RequestDao();
			r.con = ConnectionUtil.getConnectionFromFile("connection.properties");
			r.con.setAutoCommit(true);
			return r;
		} catch(IOException ex) {
			return null;
		} catch(SQLException ex) {
			return null;
		}
	}
	
	/**
	 * Gets a RequestDao
	 * @return the RequestDao, or null if there were errors establishing the database connection.
	 */
	public static RequestDao getRequestDao(InputStream f) {
		try {
			RequestDao r = new RequestDao();
			r.con = ConnectionUtil.getConnectionFromFileInputStream(f);
			r.con.setAutoCommit(true);
			return r;
		} catch(IOException ex) {
			return null;
		} catch(SQLException ex) {
			return null;
		}
	}

	/**
	 * Inserts a request into the database.
	 * @param employeId the unique employeeId of the employee making the request.
	 * @param amount the amount the request is for.
	 * @param image the image of the request receipt.
	 * @return a Request object representing the request.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public Request createRequest(int employeeId, double amount, InputStream image) throws SQLException {
		PreparedStatement p = null;
		if(image == null) {
			p = con.prepareStatement("INSERT INTO requests(e_id, r_amt) VALUES (?, ?)");
			p.setInt(1, employeeId);
			p.setDouble(2, amount);
		} else {
			p = con.prepareStatement("INSERT INTO requests(e_id, r_amt, r_img) VALUES (?, ?, ?)");
			p.setInt(1, employeeId);
			p.setDouble(2, amount);
			p.setBinaryStream(3, image);
		}
		
		int updatedRows = p.executeUpdate();
		
		if(updatedRows != 1) return null;
		
		//Retrieve RequestId from the database.
		p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE e_id = ? ORDER BY r_id DESC");
		p.setInt(1, employeeId);
		
		ResultSet rs = p.executeQuery();
		if(rs.next()) {
			return new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
							   rs.getString("r_stat"),	rs.getDate("r_date"), rs.getBinaryStream("r_img"));
		}
		return null;
	}

	/**
	 * Retrieves a request from the database.
	 * @param requestId the unique request identifier.
	 * @return a Request object representing the request.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public Request getRequest(int requestId) throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE r_id = ?");
		p.setInt(1, requestId);
		
		ResultSet rs = p.executeQuery();
		if(rs.next()) {
			return new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
							   rs.getString("r_stat"), rs.getDate("r_date"), rs.getBinaryStream("r_img"));
		}
		return null;
	}

	/**
	 * Returns a list of all requests made by a single employee.
	 * @param employeeId the unique identifier for the employee
	 * @return a list of the employee's requests.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public List<Request> getEmployeesRequests(int employeeId) throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE e_id = ?");
		p.setInt(1, employeeId);
		
		ArrayList<Request> list = new ArrayList<>();
		ResultSet rs = p.executeQuery();
		
		while(rs.next()) {
			list.add(new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
									rs.getString("r_stat"),	rs.getDate("r_date"), rs.getBinaryStream("r_img")));
		}
		return list;
	}

	/**
	 * Returns a list of all requests for all employees.
	 * @return a List containing a list or requests for each employee in the database.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public ArrayList<ArrayList<Request>> getAllEmployeeRequests() throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT DISTINCT e_id FROM requests");
		ResultSet rs = p.executeQuery();
		
		ArrayList<ArrayList<Request>> bigList = new ArrayList<>();
		while(rs.next()) {
			ArrayList<Request> list = new ArrayList<>();
			
			p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_stat, r_img FROM requests WHERE e_id = ?");
			p.setInt(1,  rs.getInt("e_id"));
			ResultSet res = p.executeQuery();
			
			while(res.next()) {
				list.add(new Request(res.getInt("r_id"), res.getInt("e_id"), res.getDouble("r_amt"),
									 res.getString("r_stat"),  res.getDate("r_date"), res.getBinaryStream("r_img")));
									 
				
			}
			
			bigList.add(list);
		}
		
		return bigList;
	}

	/**
	 * Updates a request in the database.
	 * @param requestId the unique request identifier.
	 * @param amount the amount that the request will be updated to.
	 * @param image the new image for the request.
	 * @return true if the request was updated successfully.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public boolean updateRequest(int requestId, double amount, InputStream image) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_amt = ?, r_img = ? WHERE r_id = ?");
		p.setDouble(1, amount);
		p.setBinaryStream(2, image);
		p.setInt(3, requestId);
		
		int rowsUpdated = p.executeUpdate();
		return(rowsUpdated == 1) ? true : false;
	}

	/**
	 * Convenience method for updating the amount a request is for.
	 * @param requestId the unique identifier for the request.
	 * @param amount the amount the request will be set to.
	 * @return true if the update was successful.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public boolean updateAmount(int requestId, double amount) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_amt = ? WHERE r_id = ?");
		p.setDouble(1, amount);
		p.setInt(2, requestId);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}

	/**
	 * Convenience method for updating a request image.
	 * @param requestId the unique request identifier.
	 * @param image the new image for the request.
	 * @return true if the update was successful.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public boolean updateImage(int requestId, InputStream image) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_img = ? WHERE r_id = ?");
		p.setBinaryStream(1, image);
		p.setInt(2, requestId);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true : false;
	}
	
	/**
	 * A convenience method for updating the request status.
	 * @param requestId the unique request identifier.
	 * @param status the status to set the request to. Must be one of ['Pending', 'Approved', 'Denied'].
	 * @return true if the request update was successful.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public boolean updateStatus(int requestId, String status) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_stat = ? WHERE r_id = ?");
		p.setString(1, status);
		p.setInt(2, requestId);
		
		int rowsUpdated = p.executeUpdate();
		return (rowsUpdated == 1) ? true: false;
	}

	/**
	 * Deletes a request from the database.
	 * @param requestId the unique request identifier.
	 * @return true if the delete was successful.
	 * @exception SQLException if the request violates one of the database constraints.
	 */
	@Override
	public boolean deleteRequest(int requestId) throws SQLException {
		PreparedStatement p = con.prepareStatement("DELETE FROM requests WHERE r_id = ?");
		p.setInt(1, requestId);
		
		int deletedRows = p.executeUpdate();
		return (deletedRows == 1) ? true : false;
	}
}
