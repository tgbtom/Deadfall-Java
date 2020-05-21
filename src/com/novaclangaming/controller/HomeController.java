package com.novaclangaming.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.novaclangaming.dao.IItemDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPAItemDao;
import com.novaclangaming.model.Item;

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
			request.getSession().setAttribute("bulletins", authDao.loggedUser(request).getOrderedUserBulletins());
			return "dashboard";
		}
		else {
			return "redirect: ./";
		}
	}
	
	@RequestMapping(value = "/featured/random", method = RequestMethod.GET)
	@ResponseBody
	public String featuredItem(HttpServletRequest request) {
		IItemDao jid = new JPAItemDao();
		List<String> itemNames = new ArrayList<String>();
		itemNames.add("Water Ration");
		itemNames.add("Bits of Food");
		itemNames.add("Assault Rifle");
		itemNames.add("Carrot");
		String result = request.getContextPath();
		result += "&";

		int random = (int) Math.round(Math.random() * (itemNames.size() - 1));
		Item item = jid.findByName(itemNames.get(random));
		result += item.ajaxString();
		
		return result;
	}
}
