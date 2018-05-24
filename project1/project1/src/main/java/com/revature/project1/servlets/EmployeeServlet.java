package com.revature.project1.servlets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialException;

import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Request;

/**
 * Servlet implementation class EmployeeServlet
 */
@MultipartConfig
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
		
		double amt = Double.parseDouble(request.getParameter("amount"));
		
		//Part fp = request.getPart("image");
	   // InputStream f = fp.getInputStream();
	    
		///RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		//try {
			//r.createRequest(e_id, amt, f);
		//} catch (SQLException e) {
			//TODO add error handling
		//}
		
		
		RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		try {
			Request req = r.createRequest(e_id, amt, null);
			
			request.setAttribute("r_id", req.getRequestId());
			RequestDispatcher rd = request.getRequestDispatcher("/views/imgForm.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			//TODO add error handling
		}
	}

}
