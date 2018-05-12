package com.revature.project1.connectionUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Contains a single utility method used to establish a connection to a database using the JDBC API.
 * @author Ian
 */
public class ConnectionUtil {
	
	/**
	 * Utility method for connecting to a database using a properties file.
	 * @param filename The properties file containing the database url, username, and password.
	 * @return A connection to the database.
	 * @throws IOException
	 * @throws SQLException
	 */
	public static Connection getConnectionFromFile(String filename) throws IOException, SQLException {
		Properties pr = new Properties();
		InputStream inputstream = new FileInputStream(filename);
		pr.load(inputstream);
		return DriverManager.getConnection(pr.getProperty("url"), pr.getProperty("username"), pr.getProperty("password"));
	}
}
