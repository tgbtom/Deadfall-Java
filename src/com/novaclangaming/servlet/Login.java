package com.novaclangaming.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.novaclangaming.dao.IAuthenticationDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		IAuthenticationDao authDao = new JPAAuthentication();
		RequestDispatcher dispatcher;
		Optional<User> user = authDao.authenticate(username, password);
		if (user.isPresent()) {
			request.getSession().setAttribute("message", "You are logged in as: "+ username);
			request.getSession().setAttribute("user", user.get());
			dispatcher = request.getRequestDispatcher("Navigate?loc=dashboard");
		}
		else {
			request.getSession().setAttribute("message", "Credentials incorrect");
			dispatcher = request.getRequestDispatcher("index.jsp");
		}
		dispatcher.forward(request, response);
	}

}
