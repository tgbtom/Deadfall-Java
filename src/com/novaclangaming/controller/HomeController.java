package com.novaclangaming.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.novaclangaming.dao.JPAAuthentication;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status, HttpServletRequest request) {
		status.setComplete();
		request.getSession().invalidate();
		return "index";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request) {
		JPAAuthentication authDao = new JPAAuthentication();
		if(authDao.loggedUser(request) != null) {
			return "dashboard";
		}
		else {
			return "redirect: ./";
		}
	}
}
