package com.revature.project1.dao;

import com.revature.project1.transportObjects.Request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.project1.customExceptions.EmployeeException;
import com.revature.project1.customExceptions.RequestException;
import com.revature.project1.transportObjects.Employee;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class RequestDaoTest {
	
	private static final RequestDao d = RequestDao.getRequestDao();
	private Connection con;
	private EmployeeDao e;
	private Employee u;
	private FileInputStream fileImage;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	public RequestDaoTest() {
		try {
			con = ConnectionUtil.getConnectionFromFile("connection.properties");
			
			try {
				e = EmployeeDao.getEmployeeDao();
				u = e.getEmployeeByUsername("TestUser1");
			} catch(EmployeeException ex) {
				u = e.createEmployee("TestUser1", "t", "t", "t", "t");
			}
			
			FileInputStream fileImage = new FileInputStream("ReceiptSwiss.jpg");
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setupBeforeTest() {
		try {
			PreparedStatement p = con.prepareStatement("DELETE FROM requests");
			p.executeUpdate();
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Test creating a single request.
	 */
	@Test
	public void createRequestTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 345.00, fileImage);
		
		assertTrue(345.00 - req.getAmount() < 0.01);
		assertEquals(u.getEmployeeId(), req.getEmployeeId());
		assertEquals("Pending", req.getStatus());
	}
	
	/* Test creating a request with a constraint violating Id.
	 */
	@Test
	public void badCreateRequestTest1() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("Could not create request.\n"
				 + "EmployeeId must be a valid employee Id\n"
				 + "Request amount must be greater than 0\n");
		
		Request req = d.createRequest(-1, 345.00, fileImage);
	}
	
	/* Test creating a request with a invalid amount.
	 */
	@Test
	public void badCreateRequestTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("Could not create request.\n"
				 + "EmployeeId must be a valid employee Id\n"
				 + "Request amount must be greater than 0\n");
		
		Request req = d.createRequest(u.getEmployeeId(), -2.00, fileImage);
	}
	
	/* Test retrieving a request.
	 */
	@Test
	public void getRequestByIdTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 55.00, fileImage);
		Request req2 = d.getRequest(req.getRequestId());
		
		assertEquals(req.getEmployeeId(), req2.getEmployeeId());
		assertTrue(req.getAmount() - req2.getAmount() < 0.01);
		assertEquals(req.getImage(), req2.getImage());
	}
	
	/* Test retrieving a request with a constraint violating id
	 */
	@Test
	public void getRequestByBadIdTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("RequestId Invalid");
		
		d.getRequest(-1);
	}
	
	
	/* Test getting all requests for an employee
	 */
	@Test
	public void getEmployeesRequestsTest() throws SQLException {
		ArrayList<Request> list = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {
			list.add(d.createRequest(u.getEmployeeId(), i, fileImage));
		}
		
		assertEquals(list, d.getEmployeesRequests(u.getEmployeeId()));
	}
	
	/* Test constraint violating getEmployeesRequests
	 */
	public void getEmployeesRequestsConstraintViolatedTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("EmployeeId Invalid");
		
		d.getEmployeesRequests(-1);
	}
	

	/* Test getting all requests in the database.
	 */
	@Test
	public void getAllEmployeesRequestsTest() throws SQLException {
		Employee m = e.createEmployee("MMM", "MM", "mm", "mm", "mm");
		Employee o = e.createEmployee("oo", "oo", "oo", "oo", "oo");
		Employee p = e.createEmployee("pp", "pp", "pp", "pp", "pp");
		
		ArrayList<Request> l1 = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			l1.add(d.createRequest(u.getEmployeeId(), i, fileImage));
		}
		
		ArrayList<Request> l2 = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			l1.add(d.createRequest(m.getEmployeeId(), i, fileImage));
		}
		
		ArrayList<Request> l3 = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			l3.add(d.createRequest(o.getEmployeeId(), i, fileImage));
		}
		
		ArrayList<Request> l4 = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			l4.add(d.createRequest(m.getEmployeeId(), (double) i, fileImage));
		}
		
		ArrayList<ArrayList<Request>> list1 = new ArrayList<>();
		list1.add(l1);
		list1.add(l2);
		list1.add(l3);
		list1.add(l4);
		
		ArrayList<ArrayList<Request>> list2 = (ArrayList<ArrayList<com.revature.project1.transportObjects.Request>>) d.getAllEmployeeRequests();
		
		assertEquals(list1, list2);
	}
	
	/* Test updating a request.
	 */
	@Test
	public void updateRequestTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.updateRequest(req.getRequestId(), 500, fileImage));
		req = d.getRequest(req.getRequestId());
		
		assertTrue(500.0 - req.getAmount() < 0.1);
	}
	
	/* Test updating with a constraint violating request
	 */
	@Test 
	public void updateRequestBadTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("RequestId Invalid");
		
		d.updateRequest(-1, 5.0, fileImage);
	}
	
	/* Test updating the amount
	 */
	@Test
	public void updateAmountTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.updateAmount(req.getRequestId(), 500));
		
		req = d.getRequest(req.getRequestId());
		assertTrue(500.0 - req.getAmount() < 0.1);
	}
	
	/* Test updating with a constraint violating request
	 */
	@Test
	public void badUpdateAmountTest() throws SQLException{
		exception.expect(RequestException.class);
		exception.expectMessage("Amount Invalid");
		
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		d.updateAmount(req.getRequestId(), -2.0);
	}
	
	/* Test updating an image
	 */
	@Test
	public void updateImageTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.updateImage(req.getRequestId(), fileImage));
	}
	
	/* Test updating the status
	 */
	@Test
	public void updateStatusTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.updateStatus(req.getRequestId(), "Approved"));
	}
	
	@Test
	public void badUpdateStatusTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("RequestId invalid");
		
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		d.updateStatus(-1, "sdfsfsdf");
	}
	
	/* Test deleting a request
	 */
	@Test
	public void deleteRequestTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.deleteRequest(req.getRequestId()));
	}

	/* Test deleting with a constraint violating id.
	 */
	@Test
	public void badDeleteRequestTest() throws SQLException {
		exception.expect(RequestException.class);
		exception.expectMessage("Could not Delete request.\n"
				 + "RequestId must be a valid id.");
		
		d.deleteRequest(-1);
	}
}
