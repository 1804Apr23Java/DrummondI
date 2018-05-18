package com.revature.project1.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.customExceptions.RequestException;
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
	 * Inserts a request into the database.
	 * @param employeId the unique employeeId of the employee making the request.
	 * @param amount the amount the request is for.
	 * @param image the image of the request receipt.
	 * @return a Request object representing the request.
	 * @exception RequestException if the request violates one of the database constraints.
	 */
	@Override
	public Request createRequest(int employeeId, double amount, FileInputStream image) throws SQLException {
		PreparedStatement p = null;
		if(image == null) {
			con.prepareStatement("INSERT INTO requests(e_id, r_amt) VALUES (?, ?)");
			p.setInt(1, employeeId);
			p.setDouble(2, amount);
		} else {
			con.prepareStatement("INSERT INTO requests(e_id, r_amt, r_img) VALUES (?, ?, ?)");
			p.setInt(1, employeeId);
			p.setDouble(2, amount);
			p.setBinaryStream(3, image);
		}
		
		try {
			p.executeUpdate();
		} catch(SQLException e) {
			throw new RequestException("Could not create request.\n"
									 + "EmployeeId must be a valid employee Id\n"
									 + "Request amount must be greater than 0\n");
		}
		
		//Retrieve RequestId from the database.
		p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE e_id = ? ORDER BY r_date ASC");
		p.setInt(1, employeeId);
		
		try {
			ResultSet rs = p.executeQuery();
			if(rs.next()) {
				return new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
										rs.getString("r_stat"),	rs.getDate("r_date"), rs.getBinaryStream("r_img"));
			} else {
				throw new RequestException("Could not retrieve request from the Datbase");
			}
		} catch(SQLException e) {
			throw new RequestException("Could not retrieve request from the Datbase");
		}
	}

	/**
	 * Retrieves a request from the database.
	 * @param requestId the unique request identifier.
	 * @return a Request object representing the request.
	 * @exception RequestException if the requestId is invalid.
	 */
	@Override
	public Request getRequest(int requestId) throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE r_id = ?");
		p.setInt(1, requestId);
		
		try {
			ResultSet rs = p.executeQuery();
			if(rs.next()) {
				return new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
										rs.getString("r_stat"),	rs.getDate("r_date"), rs.getBinaryStream("r_img"));
			} else {
				throw new RequestException("RequestId Invalid");
			}
		} catch(SQLException e) {
			throw new RequestException("RequestId Invalid");
		}
	}

	@Override
	public List<Request> getEmployeesRequests(int employeeId) throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests WHERE e_id = ?");
		p.setInt(1, employeeId);
		
		try {
			
			ArrayList<Request> list = new ArrayList<>();
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				list.add(new Request(rs.getInt("r_id"), rs.getInt("e_id"), rs.getDouble("r_amt"), 
										rs.getString("r_stat"),	rs.getDate("r_date"), rs.getBinaryStream("r_img")));
			}
			
			return list;
		} catch(SQLException e) {
			throw new RequestException("EmployeeId Invalid");
		}
	}

	/**
	 * Returns a list of all requests for all employees.
	 * @return a List containing a list or requests for each employee in the database.
	 * @Exception if there is an error connecting with the database.
	 */
	@Override
	public List<ArrayList<Request>> getAllEmployeeRequests() throws SQLException {
		PreparedStatement p = con.prepareStatement("SELECT r_id, e_id, r_amt, r_date, r_img, r_stat FROM requests ORDER BY e_id ASC");
		
		try {
			ResultSet rs = p.executeQuery();
			ArrayList<ArrayList<Request>> bigList = new ArrayList<>();
			ArrayList<Request> l = new ArrayList<>();
			
			int e_id = -1;
			if(rs.next()) {
				e_id = rs.getInt("e_id");
			}
			
			while(rs.next()) {
				e_id = rs.getInt("e_id");
				
				if(e_id == rs.getInt("e_id")) {
					l.add(new Request(rs.getInt("r_id"), e_id, rs.getDouble("r_amt"), rs.getString("r_stat"), rs.getDate("r_date"), rs.getBinaryStream("r_img")));
				} else {
					bigList.add(l);
					l = new ArrayList<Request>();
					l.add(new Request(rs.getInt("r_id"), e_id, rs.getDouble("r_amt"), rs.getString("r_stat"), rs.getDate("r_date"), rs.getBinaryStream("r_img")));
				}
			}
			
			return bigList;
		} catch(SQLException e) {
			throw new RequestException("List could not be retrieved from the database.");
		}
	}

	/**
	 * Updates a request in the database.
	 * @param requestId the unique request identifier.
	 * @param amount the amount that the request will be updated to.
	 * @param image the new image for the request.
	 * @return true if the request was updated successfully.
	 * @exception RequestException if the query violated the database constraints.
	 */
	@Override
	public boolean updateRequest(int requestId, double amount, FileInputStream image) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_amt = ?, r_img = ? WHERE r_id = ?");
		p.setDouble(1, amount);
		p.setBinaryStream(2, image);
		p.setInt(3, requestId);
		
		try {
			p.executeQuery();
			return true;
		} catch(SQLException e) {
			throw new RequestException("Could not update request.\n"
					 + "RequestId must be a valid employee Id\n"
					 + "Request amount must be greater than 0\n");
		}
	}

	/**
	 * Convenience method for updating the amount a request is for.
	 * @param requestId the unique identifier for the request.
	 * @param amount the amount the request will be set to.
	 * @return true if the update was successful.
	 * @exception RequestException if the query violated one of the database constraints.
	 */
	@Override
	public boolean updateAmount(int requestId, double amount) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_amt = ? WHERE r_id = ?");
		p.setDouble(1, amount);
		p.setInt(2, requestId);
		
		try {
			p.executeQuery();
			return true;
		} catch(SQLException e) {
			throw new RequestException("Could not update request.\n"
					 + "RequestId must be a valid employee Id\n"
					 + "Request amount must be greater than 0\n");
		}
	}

	/**
	 * Convenience method for updating a request image.
	 * @param requestId the unique request identifier.
	 * @param image the new image for the request.
	 * @return true if the update was successful.
	 * @exception RequestException if the query violated a database constraint.
	 */
	@Override
	public boolean updateImage(int requestId, FileInputStream image) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_img = ? WHERE r_id = ?");
		p.setBinaryStream(1, image);
		p.setInt(2, requestId);
		
		try {
			p.executeQuery();
			return true;
		} catch(SQLException e) {
			throw new RequestException("Could not update request.\n"
					 + "RequestId must be a valid employee Id\n");
		}
	}
	
	/**
	 * A convenience method for updating the request status.
	 * @param requestId the unique request identifier.
	 * @param status the status to set the request to. Must be one of ['Pending', 'Approved', 'Denied'].
	 * @return true if the request update was successful.
	 * @exception RequestException if the query violated a constraint.
	 */
	@Override
	public boolean updateStatus(int requestId, String status) throws SQLException {
		PreparedStatement p = con.prepareStatement("UPDATE requests SET r_stat = ? WHERE r_id = ?");
		p.setString(1, status);
		p.setInt(2, requestId);
		
		try {
			p.executeQuery();
			return true;
		} catch(SQLException e) {
			throw new RequestException("Could not update request.\n"
					 + "Status must be one of ['Pending', 'Approved', 'Denied']");
		}
	}

	/**
	 * Deletes a request from the database.
	 * @param requestId the unique request identifier.
	 * @return true if the delete was successful.
	 * @exception RequestException if the query violated a database constraint.
	 */
	@Override
	public boolean deleteRequest(int requestId) throws SQLException {
		if(requestId <= 0) {
			throw new RequestException("Could not Delete request.\n"
					 + "RequestId must be a valid Id in the range 1 to 2,147,483,648"); 
		}
		
		PreparedStatement p = con.prepareStatement("DELETE FROM requests WHERE r_id = ?");
		p.setInt(1, requestId);
		
		try {
			int deletedRows = p.executeUpdate();
			
			if(deletedRows < 1) {
				throw new RequestException("Could not Delete request.\n"
						 + "Id provided was not an Id assigned to an active request."
						 + "RequestId must be a valid id in the range 0 to 2,147,483,648");
			} else {
				return true;
			}
		} catch(SQLException e) {
			throw new RequestException("Could not Delete request.\n"
					 + "RequestId must be a valid id.");
		}
	}
}
