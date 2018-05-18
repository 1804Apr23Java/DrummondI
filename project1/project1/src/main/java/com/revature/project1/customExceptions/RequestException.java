package com.revature.project1.customExceptions;

import java.sql.SQLException;

public class RequestException extends SQLException {
	
	public RequestException(String msg) {
		super(msg);
	}
}
