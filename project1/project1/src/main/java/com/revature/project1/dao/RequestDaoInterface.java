package com.revature.project1.dao;

public interface RequestDaoInterface {
	public Request createRequest(int employeeId, double amount, FileInputStream image);
}
