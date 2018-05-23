package com.revature.project1.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Request;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null)  {
			RequestDispatcher rd = request.getRequestDispatcher("../front/login");
			rd.forward(request, response);
			return;
		}
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"requests\" : [");
		
		RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		
		int e_id = (Integer) session.getAttribute("e_id");
		
		List<Request> reqs = new ArrayList<>();
		try {
			reqs = r.getEmployeesRequests(e_id);
		} catch (SQLException e) {
			//TODO add error handling
		}
		
		int i = 0;
		
		for(i = 0; i < reqs.size() - 1; i++) {
			Request re = reqs.get(i);
			json.append("{");
			json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
			json.append("\"amt\" : \"" + re.getAmount() + "\",");
			json.append("\"date\" : \"" + re.getDate().toString() + "\",");
			json.append("\"stat\" : \"" + re.getStatus() + "\"");
			json.append("},");
		}
		
		Request re = reqs.get(i);
		json.append("{");
		json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
		json.append("\"amt\" : \"" + re.getAmount() + "\",");
		json.append("\"date\" : \"" + re.getDate().toString() + "\",");
		json.append("\"stat\" : \"" + re.getStatus() + "\"");
		json.append("}");
		
		json.append("] }");
		
		response.setContentType("application/json");
		PrintWriter p = response.getWriter();
		p.write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("../front/login");
			rd.forward(request, response);
			return;
		}
		
		//TODO add error checking
		Integer e_id = (Integer) session.getAttribute("e_id");
		
		double amt = 0.0;
		try {
			amt = Double.parseDouble(request.getParameter("amount"));
		} catch(NumberFormatException ex) {
			//TODO error handing
			return;
		}
		
		
		InputStream image = new ByteArrayInputStream(request.getParameter("image").getBytes());
		
		RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		try {
			r.createRequest(e_id, amt, image);
		} catch (SQLException e) {
			//TODO add error handling
		}
	}

}
