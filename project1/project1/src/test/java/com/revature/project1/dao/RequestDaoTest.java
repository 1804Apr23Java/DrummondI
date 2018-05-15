package com.revature.project1.dao;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Request;

import com.revature.project1.transportObjects.Employee;
import com.revature.project1.utilityClasses.ConnectionUtil;

public class RequestDaoTest {
	
	private static final requestsDAO d = new requestsDao();
	private Connection con;
	private EmployeeDao e;
	private Employee u;
	private FileInputStream fileImage;
	
	
	public RequestDaoTest() {
		try {
			con = ConnectionUtil.getConnectionFromFile("connection.properties");
			
			e = new EmployeeDao();
			u = e.createEmployee("TestUser1", "TestName", "TestName", "TestEmail", "TestPassword");
			
			FileInputStream fileImage = new FileInputStream("/testImages/ReceiptSwiss.jpg");
		} catch(Exception e) {
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
	
	@Test
	public void createRequestTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 345.00, fileImage);
		
		assertEquals(345.00, req.getAmount());
		assertEquals(u.getEmployeeId(), req.getEmployeeId());
		assertEquals("Pending", req.getRequestStatus());
	}
	
	@Test
	public void getRequestByIdTest() throws SQLException {
		Request req = d.createRequest(u.getEmployeeId(), 55.00, fileImage);
		Request req2 = d.getRequestById(req.getRequestId());
		
		assertEquals(req.getEmployeeId(), req2.getEmployeeId());
		assertEquals(req.getAmount(), req2.getAmount());
		assertEquals(req.getImage(), req2.getImage());
	}
	
	@Test
	public void getEmployeesRequestsTest() {
		List<Request> list1 = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {
			list1.add(d.createRequest(u.getEmployeeId(), i, fileImage));
		}
		
		List<Request> list2 = g.getEmployeesRequests(u.getEmployeeId());
		
		//list1.sort(new RequestsComparator<Request>());
		//list2.sort(new RequestComparator<Request>());
		
		assertEquals(list1, list2);
	}
	
	@Test
	public void getAllEmployeesRequestsTest() {
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
			l4.add(p.createRequest(m.getEmployeeId(), i, fileImage));
		}
		
		ArrayList<ArrayList<Request>> list1 = new ArrayList<>();
		list1.add(l1);
		list1.add(l2);
		list1.add(l3);
		list1.add(l4);
		
		ArrayList<ArrayList<Request>> list2 = d.getAllEmployeesRequests();
		
		assertEquals(list1, list2);
	}
	
	@Test
	public void updateRequest() {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		Request req2 = d.updateRequest(u.getEmployeeId(), 500, fileImage);
		
		assertEquals(500.0, req2.getAmount());
	}
	
	@Test
	public void requestDeleteTest() {
		Request req = d.createRequest(u.getEmployeeId(), 100.0, fileImage);
		assertTrue(d.deleteRequest(req.getRequestId()));
	}
}
