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

import com.revature.project1.dao.RequestDao;
import com.revature.project1.transportObjects.Request;

/**
 * Servlet implementation class RequestServlet
 */
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println(request.getPathInfo());

		if(session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/front/login");
			rd.forward(request, response);
		} else {
			
			RequestDao d = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
			try {
				System.out.println(request.getRequestURI());
				System.out.println("shghgh " + request.getParameter("id"));
				Request req = d.getRequest(Integer.parseInt(request.getParameter("id")));
				
				if(req.getEmployeeId() == (Integer) session.getAttribute("e_id")) {
				
					request.setAttribute("stat", req.getStatus());
					request.setAttribute("date", req.getDate().toString());
					request.setAttribute("amt", req.getAmount());
					request.setAttribute("e_id", req.getEmployeeId());
					
					RequestDispatcher rd = request.getRequestDispatcher("/views/requestview.jsp");
					rd.forward(request, response);
					return;
				}
				
			} catch (NumberFormatException e) {
				// TODO Error
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Error
				e.printStackTrace();
			}
			
			//User trying to view request that does not belong to them or other error
			RequestDispatcher rd = request.getRequestDispatcher("front/login");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println(request.getPathInfo());

		if(session == null) {
			response.sendRedirect("/project1/front/login");
		}
		
		int req_id = Integer.parseInt(request.getParameter("id"));
		String stat = request.getParameter("stat");
		
		RequestDao d = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		
		try {
			stat = (stat.equals("a")) ? "Approved" : "Denied";
			d.updateStatus(req_id, stat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setStatus(200);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int req_id = Integer.parseInt(request.getParameter("id"));
		RequestDao d = RequestDao.getRequestDao(getServletContext().getResourceAsStream("connection.properties"));
		System.out.println("here");
		try {
			d.deleteRequest(req_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setStatus(200);
	}
}
