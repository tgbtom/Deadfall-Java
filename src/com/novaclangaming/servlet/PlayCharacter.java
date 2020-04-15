package com.novaclangaming.servlet;

import java.io.IOException;

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
 * Servlet implementation class PlayCharacter
 */
@WebServlet("/PlayCharacter")
public class PlayCharacter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayCharacter() {
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
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect("index.jsp");
		}
		else {
			//check if char is in town, if so go to town, ELSE go to join page
			RequestDispatcher dispatcher;
			ICharacterDao charDao = new JPACharacterDao();
			int charId = Integer.parseInt(request.getParameter("char-id"));
			Character c = charDao.findById(charId);
			if (c != null && c.getUser().getId() == user.getId()) {
				if(c.getTown() == null) {
					dispatcher = request.getRequestDispatcher("Navigate?loc=joinTown");
				}
				else {
					////THIS WILL GO TO IN TOWN
					request.getSession().setAttribute("message", "The character is in Town");
					dispatcher = request.getRequestDispatcher("Navigate?loc=dashboard");
				}
			}
			else {
				//character does not exist for this user
				request.getSession().setAttribute("message", "Requested Character does not belong to this user");
				dispatcher = request.getRequestDispatcher("Navigate?loc=dashboard");
			}
			dispatcher.forward(request, response);
		}
	}

}
