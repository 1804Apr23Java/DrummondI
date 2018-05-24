package com.revature.project1.servlets;

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
import javax.servlet.http.Part;

import com.revature.project1.dao.EmployeeDao;
import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Employee;
import com.revature.project1.transportObjects.Request;

/**
 * Servlet implementation class ImgUploadServlet
 */
public class ImgUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
		if(session == null)  {
			RequestDispatcher rd = request.getRequestDispatcher("../front/login");
			rd.forward(request, response);
			return;
		}
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"employees\" : [");
		
		EmployeeDao r = EmployeeDao.getEmployeeDao(getServletContext().getResourceAsStream("connection.properties"));
		
		
		List<Employee> reqs = new ArrayList<>();
		try {
			reqs = r.getAllEmployees();
		} catch (SQLException e) {
			//TODO add error handling
		}
		
		int i = 0;
		
		for(i = 0; i < reqs.size() - 1; i++) {
			Employee re = reqs.get(i);
			json.append("{");
			json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
			json.append("\"e_un\" : \"" + re.getUsername() + "\",");
			json.append("\"e_fn\" : \"" + re.getFirstname() + "\",");
			json.append("\"e_ln\" : \"" + re.getLastname() + "\",");
			json.append("\"e_em\" : \"" + re.getEmail() + "\"");
			json.append("},");
		}
		
		Employee re = reqs.get(i);
		json.append("{");
		json.append("\"e_id\" : \"" + re.getEmployeeId() + "\",");
		json.append("\"e_un\" : \"" + re.getUsername() + "\",");
		json.append("\"e_fn\" : \"" + re.getFirstname() + "\",");
		json.append("\"e_ln\" : \"" + re.getLastname() + "\",");
		json.append("\"e_em\" : \"" + re.getEmail() + "\"");
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
			response.sendRedirect("/project1/front/login");
		}
		
		String req_id = request.getPathInfo().substring(1);
		System.out.println(req_id);
		int r_id = Integer.parseInt(req_id);
		
		
		Part fp = request.getPart("image");
		InputStream f = fp.getInputStream();
		    
		RequestDao r = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		try {
			r.updateImage(r_id, f);
			response.sendRedirect("/project1/front/ehome");
		} catch (SQLException e) {
			//TODO add error handling
		}
	}
}
