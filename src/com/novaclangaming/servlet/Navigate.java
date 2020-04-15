package com.novaclangaming.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.novaclangaming.model.User;
import com.novaclangaming.dao.ICharacterDao;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.model.Character;

/**
 * Servlet implementation class Navigate
 */
@WebServlet("/Navigate")
public class Navigate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Navigate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String loc = request.getParameter("loc");
		if(user == null){
			response.sendRedirect("index.jsp");
		}
		else {
			RequestDispatcher dispatcher;
			if(loc.equals("dashboard")) {
				ICharacterDao charDao = new JPACharacterDao();
				List<Character> characters = charDao.findByUserId(user.getId());
				request.getSession().setAttribute("characters", characters);
				dispatcher = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
			}
			else {
				dispatcher = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
			}
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
