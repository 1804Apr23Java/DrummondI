package com.revature.project1.servlets;

import java.io.IOException;
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

import com.revature.project1.dao.EmployeeDao;
import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Request;

/**
 * Servlet implementation class EmployeeCreate
 */
public class EmployeeCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeCreateServlet() {
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
		System.out.println("WEWERE");
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"users\" : [");
		
		RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		
		int e_id = (Integer) session.getAttribute("e_id");
		
		ArrayList<ArrayList<Request>> reqs = new ArrayList<ArrayList<Request>>();
		
		try {
			reqs = r.getAllEmployeeRequests();
		} catch (SQLException e) {
			//TODO add error handling
		}
		
		int l = 0;
		for(l = 0; l < reqs.size() - 1; l++) {
			json.append("{");
			json.append("\"requests\" : [");
			
			int i = 0;
			ArrayList<Request> reqs2 = reqs.get(l);
			for(i = 0; i < reqs2.size() - 1; i++) {
				Request re = reqs2.get(i);
				json.append("{");
				json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
				json.append("\"amt\" : \"" + re.getAmount() + "\",");
				json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
				json.append("\"date\" : \"" + re.getDate().toString() + "\",");
				json.append("\"stat\" : \"" + re.getStatus() + "\"");
				json.append("},");
			}
			
			Request re = reqs2.get(i);
			json.append("{");
			json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
			json.append("\"amt\" : \"" + re.getAmount() + "\",");
			json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
			json.append("\"date\" : \"" + re.getDate().toString() + "\",");
			json.append("\"stat\" : \"" + re.getStatus() + "\"");
			json.append("}");
			
			json.append("] },");
		}
		
		json.append("{");
		json.append("\"requests\" : [");
		
		int i = 0;
		ArrayList<Request> reqs2 = reqs.get(l);
		for(i = 0; i < reqs2.size() - 1; i++) {
			Request re = reqs2.get(i);
			json.append("{");
			json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
			json.append("\"amt\" : \"" + re.getAmount() + "\",");
			json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
			json.append("\"date\" : \"" + re.getDate().toString() + "\",");
			json.append("\"stat\" : \"" + re.getStatus() + "\"");
			json.append("},");
		}
		
		Request re = reqs2.get(i);
		json.append("{");
		json.append("\"r_id\" : \"" + re.getRequestId() + "\",");
		json.append("\"amt\" : \"" + re.getAmount() + "\",");
		json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
		json.append("\"date\" : \"" + re.getDate().toString() + "\",");
		json.append("\"stat\" : \"" + re.getStatus() + "\"");
		json.append("}");
		
		json.append("] }");
		json.append("] }");
		
		response.setContentType("application/json");
		PrintWriter p = response.getWriter();
		p.write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String un = request.getParameter("username");
		String fn = request.getParameter("firstname");
		String ln = request.getParameter("lastname");
		String em = request.getParameter("email");
		String pw = request.getParameter("password");
		
		EmployeeDao empDao = EmployeeDao.getEmployeeDao(getServletContext().getResourceAsStream("connection.properties"));
		
		try {
			empDao.createEmployee(un, fn, ln, em, pw);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/project1");
	}
}
