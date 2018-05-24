package com.revature.project1.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.dao.EmployeeDao;

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		System.out.println("Here");
		try {
			empDao.createEmployee(un, fn, ln, em, pw);
			System.out.println("afsd");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/project1");
	}

}
