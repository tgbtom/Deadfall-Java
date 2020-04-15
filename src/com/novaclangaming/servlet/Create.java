package com.novaclangaming.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.novaclangaming.dao.ICharacterDao;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.model.User;
import com.novaclangaming.model.Character;
import com.novaclangaming.model.CharacterClass;

/**
 * Servlet implementation class Create
 */
@WebServlet("/Create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Create() {
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
		}else {
			RequestDispatcher dispatcher;
			String type = request.getParameter("type");
			String charName = request.getParameter("char-name");
			CharacterClass charClass = CharacterClass.valueOf(request.getParameter("char-class"));
			if(type.equals("character")) {
				//create character
				ICharacterDao charDao = new JPACharacterDao();
				if(charDao.findByUserId(user.getId()).size() >= 20) {
					request.getSession().setAttribute("message", "You already have the maximum of 20 Characters");
				}
				else {
					Character newChar = new Character(user, charName, charClass);
					charDao.create(newChar);
					request.getSession().setAttribute("message", "Character: "+ charName +" created successfully");
				}
				dispatcher = request.getRequestDispatcher("Navigate?loc=dashboard");
			}else {
				dispatcher = request.getRequestDispatcher("Navigate?loc=dashboard");
			}
			dispatcher.forward(request, response);
		}
	}

}
