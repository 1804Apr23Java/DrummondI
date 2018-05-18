package com.revature.project1.dao;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.transportObjects.Request;

public interface RequestDaoInterface {
	public Request createRequest(int employeeId, double amount, FileInputStream image) throws SQLException;
	
	public Request getRequest(int requestId) throws SQLException;
	public List<Request> getEmployeesRequests(int employeeId) throws SQLException;
	public List<ArrayList<Request>> getAllEmployeeRequests() throws SQLException;
	
	public boolean updateRequest(int requestId, double amount, FileInputStream image) throws SQLException;
	public boolean updateAmount(int requestId, double amount) throws SQLException;
	public boolean updateImage(int requestId, FileInputStream image) throws SQLException;
	public boolean updateStatus(int requestId, String status) throws SQLException;
	
	public boolean deleteRequest(int requestId) throws SQLException;
}
