package com.revature.project1.transportObjects;

import java.util.Date;

/**
 * Transport object for DAO pattern; represents a reimbursement request.
 * @author Ian
 */
public class Request {
	private int requestId;
	private int employeeId;
	private double amount;
	private String status;
	private Date date;
	private byte[] image;
	
	/**
	 * Constructor for request.
	 * @param requestId The unique request id.
	 * @param employeeId The id of the employee the request belongs to.
	 * @param amount The amount of money the request is for.
	 * @param status The status of the request.
	 * @param date The date the request was submitted.
	 * @param image A byte array representing the image of the request receipt.
	 */
	public Request(int requestId, int employeeId, double amount, String status, Date date, byte[] image) {
		super();
		this.requestId = requestId;
		this.employeeId = employeeId;
		this.amount = amount;
		this.status = status;
		this.date = date;
		this.image = image;
	}
	
	/**
	 * Constructor for request.
	 * @param employeeId The id of the employee the request belongs to.
	 * @param amount The amount of money the request is for.
	 * @param image A byte array representing the image of the request receipt.
	 */
	public Request(int employeeId, double amount, byte[] image) {
		super();
		this.employeeId = employeeId;
		this.amount = amount;
		this.image = image;
	}
	
	/**
	 * @return The unique request Id.
	 */
	public int getRequestId() {
		return requestId;
	}
	
	/**
	 * @return The Id of the employee who made the request.
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	
	/**
	 * @return The amount the request is for.
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * @return The status of the request.
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @return The date the request was made.
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return A byte array representing the request receipt.
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @return The String representation of the request.
	 */
	@Override
	public String toString() {
		return null;
	}
}
