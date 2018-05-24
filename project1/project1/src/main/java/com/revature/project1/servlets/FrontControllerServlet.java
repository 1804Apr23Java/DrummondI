package com.revature.project1.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.project1.dao.EmployeeDao;
import com.revature.project1.transportObjects.Employee;

/**
 * Servlet implementation class FrontControllerServlet
 */
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<String, String> map;

    /**
     * Default constructor. 
     */
    public FrontControllerServlet() {
    	map = new HashMap<String, String>();
    	map.put("/login", "/views/homepage.html");
    	map.put("/signup", "/views/signuppage.html");
    	map.put("/ehome", "/views/employeepage.html");
    	map.put("/ecreate", "/views/createemployee.html");
    	map.put("/eview", "/views/employeerequests.html");
    	map.put("/ecancel", "/views/employeepage.html");
    	map.put("/logout", "/logout");
    	map.put("/create", "/create");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = map.get(request.getPathInfo());
		System.out.println("forward = " + forward);
		forward = (forward == null) ? "/views/404.html" : forward;
		
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmployeeDao empDao = EmployeeDao.getEmployeeDao(getServletContext().getResourceAsStream("connection.properties"));
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			Employee e = empDao.getEmployeeByUsername(username);
			
			if(e == null || !e.getPassword().equals(password)) {
				response.sendRedirect("/project1/front/login");
				return;
			}
		
			String manager = (empDao.isManager(e.getEmployeeId())) ? "yes" : "no";
			
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("e_id", new Integer(e.getEmployeeId()));
			session.setAttribute("password", password);
			session.setAttribute("manager", manager);
			
			if(manager.equals("yes")) {
				response.sendRedirect("/project1/front/login");
			} else {
				response.sendRedirect("/project1/front/ehome");
			}
		} catch(SQLException ex) {
			response.sendRedirect("/project1/front/login");
		}
	}
}
