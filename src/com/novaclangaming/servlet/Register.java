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
import com.novaclangaming.dao.IUserDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPAUserDao;
import com.novaclangaming.model.Password;
import com.novaclangaming.model.User;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		IUserDao userDao = new JPAUserDao();
		IAuthenticationDao authDao = new JPAAuthentication();
		Optional<User> user = userDao.findByName(username);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		if(user.isEmpty()) {
			Password convertedPass = authDao.hashPassword(password);
			String hashedPass = convertedPass.getHashedPass();
			String salt = convertedPass.getSalt();
			
			User newUser = new User(username, hashedPass, salt, email);
			authDao.register(newUser);
			request.getSession().setAttribute("message", "Registration Successful!");
			dispatcher.forward(request, response);
		}
		else {
			request.getSession().setAttribute("message", "Registration Failed");
			dispatcher.forward(request, response);
		}
	}

}
