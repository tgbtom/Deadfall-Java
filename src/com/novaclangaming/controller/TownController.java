package com.novaclangaming.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaclangaming.dao.ITownDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPATownDao;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownStatus;
import com.novaclangaming.model.User;

@Controller
public class TownController {
	
	JPAAuthentication auth = new JPAAuthentication();
	ITownDao townDao = new JPATownDao();
	
	@RequestMapping(value = "/town/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, @RequestParam String townName, 
			@RequestParam int townSize, @RequestParam int mapSize, @RequestParam String townMode) {
		User user = auth.loggedUser(request);
		if(user != null) {
			if(townDao.findByName(townName).isEmpty()) {
				Town town = new Town(townName, townSize, 300, 350, mapSize, townMode, TownStatus.New);
				townDao.create(town);
				request.getSession().setAttribute("message", "Town has been created");
			}
			else {
				request.getSession().setAttribute("message", "The town name is already in use, try something else.");
			}
			return "redirect: ../character/join";
		}
		else {
			return "redirect: ../";
		}
	}
}
