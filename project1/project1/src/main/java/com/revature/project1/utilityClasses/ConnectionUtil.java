package com.revature.project1.utilityClasses;

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
	public static Connection getConnectionFromFile(InputStream filename) throws IOException, SQLException {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
		Properties pr = new Properties();
		System.out.println("df");
		InputStream inputstream = filename;
		System.out.println("dsfdsfasasdffddfsafwefjkkkerlerlelelerlerl;;;;;;;");
		pr.load(inputstream);
		
		System.out.println(pr.getProperty("url"));
		System.out.println(pr.getProperty("username"));
		System.out.println(pr.getProperty("password"));
		
		System.out.println("QWEQWEQWEQWE");
		
		return DriverManager.getConnection(pr.getProperty("url"), pr.getProperty("username"), pr.getProperty("password"));
	}
}
