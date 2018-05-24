package com.revature.project1.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.project1.dao.EmployeeDao;
import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Employee;

/**
 * Servlet implementation class EmployeeHandle
 */
public class EmployeeHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeHandle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			response.sendRedirect("/project1/front/login");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/employeeView.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int e_id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id = " + e_id);
		EmployeeDao d = EmployeeDao.getEmployeeDao(getServletContext().getResourceAsStream("connection.properties"));
		
		try {
			d.deleteEmployee(e_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setStatus(200);
	}
}
