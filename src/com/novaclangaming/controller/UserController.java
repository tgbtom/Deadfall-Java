package com.novaclangaming.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.novaclangaming.dao.IAuthenticationDao;
import com.novaclangaming.dao.IUserDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPAUserDao;
import com.novaclangaming.model.Password;
import com.novaclangaming.model.User;

@Controller
@SessionAttributes("message")
public class UserController {

	JPAUserDao jud = new JPAUserDao();
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public ModelAndView register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {

		ModelAndView mv = new ModelAndView("index");
		IUserDao userDao = new JPAUserDao();
		IAuthenticationDao authDao = new JPAAuthentication();
		Optional<User> user = userDao.findByName(username);
		if(user.isEmpty()) {
			Password convertedPass = authDao.hashPassword(password);
			String hashedPass = convertedPass.getHashedPass();
			String salt = convertedPass.getSalt();
			
			User newUser = new User(username, hashedPass, salt, email);
			authDao.register(newUser);
			mv.addObject("message", "Registration Successful");
		}else {
			mv.addObject("message", "Registration Failed");
		}
		return mv;
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
		
		IAuthenticationDao authDao = new JPAAuthentication();
		Optional<User> user = authDao.authenticate(username, password);
		if(user.isPresent()) {
			request.getSession().setAttribute("message", "You are logged in as: "+ username);
			request.getSession().setAttribute("user", user.get());
			return "redirect: ../dashboard";
		}
		else {
			request.getSession().setAttribute("message", "Invalid credentials");
			return "redirect: ../";
		}
	}
	
}
