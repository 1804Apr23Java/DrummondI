package com.revature.project1.dao;

import com.revature.project1.transportObjects.Request;
import com.revature.project1.transportObjects.RequestComparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
	
			e = EmployeeDao.getEmployeeDao();
			
			u = e.getEmployeeByUsername("TestUser1");
			if(u == null) {
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
			
			p = con.prepareStatement("DELETE FROM all_emp WHERE e_un <> 'TestUser1'");
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
	
	/* Test creating a single request with no image.
	 */
	@Test
	public void createRequestNoImageTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 345.00, null);
		
		assertTrue(345.00 - req.getAmount() < 0.01);
		assertEquals(u.getEmployeeId(), req.getEmployeeId());
		assertEquals("Pending", req.getStatus());
	}
	
	/* Test creating a request with a constraint violating Id.
	 */
	@Test
	public void badCreateRequestTest1() throws SQLException {
		exception.expect(SQLException.class);
		d.createRequest(-1, 345.00, fileImage);
	}
	
	/* Test creating a request with a invalid amount.
	 */
	@Test
	public void badCreateRequestTest() throws SQLException {
		exception.expect(SQLException.class);
		d.createRequest(u.getEmployeeId(), -2.00, fileImage);
	}
	
	/* Test retrieving a request.
	 */
	@Test
	public void getRequestByIdTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 55.00, fileImage);
		Request req2 = d.getRequest(req.getRequestId());
		
		assertEquals(req.getEmployeeId(), req2.getEmployeeId());
		assertEquals(req.getRequestId(), req2.getRequestId());
		assertTrue(req.getAmount() - req2.getAmount() < 0.01);
		assertEquals(req.getImage(), req2.getImage());
	}
	
	/* Test retrieving a request with a constraint violating id
	 */
	@Test
	public void getRequestByBadIdTest() throws SQLException {
		assertNull(d.getRequest(-1));
	}
	
	
	/* Test getting all requests for an employee
	 */
	@Test
	public void getEmployeesRequestsTest() throws SQLException {
		ArrayList<Request> list = new ArrayList<>();
		
		for(int i = 1; i < 11; i++) {
			list.add(d.createRequest(u.getEmployeeId(), i, fileImage));
		}
		
		ArrayList<Request> list1 = (ArrayList<Request>) d.getEmployeesRequests(u.getEmployeeId());
		
		list.sort(new RequestComparator<Request>());
		list1.sort(new RequestComparator<Request>());
		
		assertEquals(list, list1);
	}
	
	/* Test getting all requests for a nonexistent employee.
	 */
	@Test
	public void getFakeEmployeesRequestsTest() throws SQLException {
		assertTrue(d.getEmployeesRequests(-1).size() == 0);
	}
	
	/* Test constraint violating getEmployeesRequests
	 */
	public void getEmployeesRequestsConstraintViolatedTest() throws SQLException {
		assertEquals(new ArrayList<Request>(), d.getEmployeesRequests(-1));
	}
	

	/* Test getting all requests in the database.
	 */
	@Test
	public void getAllEmployeesRequestsTest() throws SQLException {
		Employee m = e.createEmployee("MMM", "MM", "mm", "mm", "mm");
		Employee o = e.createEmployee("oo", "oo", "oo", "oo", "oo");
		Employee p = e.createEmployee("pp", "pp", "pp", "pp", "pp");
		
		HashSet<Request> set1 = new HashSet<Request>();
		
		for(int i = 1; i < 10; i++) {
			set1.add(d.createRequest(u.getEmployeeId(), i, fileImage));
		}
		
		for(int i = 1; i < 10; i++) {
			set1.add(d.createRequest(m.getEmployeeId(), i, fileImage));
		}
		
		for(int i = 1; i < 10; i++) {
			set1.add(d.createRequest(o.getEmployeeId(), i, fileImage));
		}
		
		for(int i = 1; i < 10; i++) {
			set1.add(d.createRequest(m.getEmployeeId(), (double) i, fileImage));
		}
		
		ArrayList<ArrayList<Request>> list2 = (ArrayList<ArrayList<Request>>) d.getAllEmployeeRequests();
		
		HashSet<Request> set2 = new HashSet<Request>();
		for(int i = 0; i < list2.size(); i++) {
			List<Request> l = list2.get(i);
			for(int j = 0; j < l.size(); j++) {
				set2.add(l.get(j));
			}
		}
		
		assertTrue(set1.size() == set2.size());
	}
	
	/* Test getting all requests in the database.
	 */
	@Test
	public void getAllEmployeesRequestsEmptyDatabaseTest() throws SQLException {	
		ArrayList<ArrayList<Request>> list1 = new ArrayList<ArrayList<Request>>();
		assertEquals(list1, d.getAllEmployeeRequests());
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
	
	/* Test updating amount with a constraint violating request
	 */
	@Test 
	public void updateRequestBadTest() throws SQLException {
		exception.expect(SQLException.class);
		
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		d.updateRequest(req.getRequestId(), -5.0, fileImage);
	}
	
	/* Test updating amount with a fake user.
	 */
	@Test 
	public void updateRequestFakeUserTest() throws SQLException {
		assertFalse(d.updateRequest(-1, -5.0, fileImage));
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
		exception.expect(SQLException.class);
		
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
		req = d.getRequest(req.getRequestId());
		assertEquals(req.getStatus(), "Approved");
	}
	
	@Test
	public void badUpdateStatusTest() throws SQLException {
		exception.expect(SQLException.class);
		
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		d.updateStatus(req.getRequestId(), "sdfsfsdf");
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
		assertFalse(d.deleteRequest(-1));
	}
}
