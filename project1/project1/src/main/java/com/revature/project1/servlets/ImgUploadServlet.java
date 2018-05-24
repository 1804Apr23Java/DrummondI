package com.revature.project1.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.revature.project1.dao.RequestDao;

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
