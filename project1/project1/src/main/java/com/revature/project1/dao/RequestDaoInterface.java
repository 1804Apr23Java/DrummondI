package com.revature.project1.dao;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.transportObjects.Request;

public interface RequestDaoInterface {
	public Request createRequest(int employeeId, double amount, FileInputStream image);
	
	public Request getRequest(int requestId);
	public List<Request> getEmployeesRequests(int employeeId);
	public List<ArrayList<Request>> getAllEmployeeRequests();
	
	public boolean updateRequest(int requestId, double amount, FileInputStream image);
	public boolean updateAmount(int requestId, double amount);
	public boolean updateImage(int requestId, FileInputStream image);
	
	public boolean deleteRequest(int requestId);
}
