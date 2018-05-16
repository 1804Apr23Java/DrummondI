package com.revature.project1.customExceptions;

import java.sql.SQLException;

public class EmployeeException extends SQLException {
	
	public EmployeeException(String msg) {
		super(msg);
	}
}
