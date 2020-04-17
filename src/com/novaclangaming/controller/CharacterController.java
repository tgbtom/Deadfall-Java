package com.novaclangaming.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.dao.JPAUserDao;
import com.novaclangaming.model.CharacterClass;
import com.novaclangaming.model.User;
import com.novaclangaming.model.Character;

@Controller
public class CharacterController {

	JPACharacterDao charDao = new JPACharacterDao();
	JPAUserDao userDao = new JPAUserDao();
	JPAAuthentication auth = new JPAAuthentication();
	
	@RequestMapping(value = "/character/create", method = RequestMethod.POST)
	public String create(@RequestParam String charName, @RequestParam CharacterClass charClass, HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null) {
			List<Character> characters = charDao.findByUserId(user.getId());
			if(characters.size() < 20 && charDao.findByCharName(user.getId(), charName).isEmpty() && charName.length() < 25) {
				request.getSession().setAttribute("message", "Character: "+ charName +" was created successfully");
				Character character = new Character(user, charName, charClass);
				charDao.create(character);
			}
			else if (charDao.findByCharName(user.getId(), charName).isPresent()) {
				request.getSession().setAttribute("message", "You already have a character with that name");
			}
			else if(charName.length() >= 25) {
				request.getSession().setAttribute("message", "Name must be shorter than 25 characters");
			}
			else {
				request.getSession().setAttribute("message", "You already have the maximum of 20 characters");
			}
			return "redirect: ../dashboard";
		}
		else {
			return "redirect: ../";
		}
	}
	
	@RequestMapping(value = "/character/check", method = RequestMethod.POST)
	public String checkTown(@RequestParam int charId, HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null) {
			
			Character c = charDao.findById(charId);
			if(c != null && c.getUser().getId() == user.getId()) {
				request.getSession().setAttribute("character", c);
				if(c.getTown() == null) {
					return "redirect: ./join";
				}
				else {
					request.getSession().setAttribute("message", "Char is in a town");
					return "redirect: ../dashboard";
				}
			}
			else {
				request.getSession().setAttribute("message", "Requested char does not belong to this user");
				return "redirect: ../dashboard";
			}	
		}
		else {
			return "redirect: ../";
		}
	}
	
	@RequestMapping(value = "/character/join", method = RequestMethod.GET)
	public String showJoin(HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null) {
			Character character = (Character) request.getSession().getAttribute("character");
			if(character == null) {
				return "redirect: ../dashboard";
			}
			else {
				return "joinTown";
			}
		}
		else {
			return "redirect: ../";
		}
	}
	
	@RequestMapping(value = "/character/{id}", method = RequestMethod.GET)
	public ModelAndView showCharacter(@PathVariable int id, HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null ) {
			Character c = charDao.findById(id);
			ModelAndView mv;
			if(c.getUser().getId() == user.getId()) {
				mv = new ModelAndView("character/show");
				mv.addObject("charToView", c);
			}
			else {
				mv = new ModelAndView("dashboard");
			}
			return mv;
		}else {
			return new ModelAndView("index");
		}
	}
	
	@RequestMapping(value = "/character/delete", method = RequestMethod.POST)
	public String deleteCharacter(@RequestParam int charId, HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null) {
			Character character = charDao.findById(charId);
			if(character.getUser().getId() == user.getId() && character.getTown() == null) {
				charDao.delete(character);
				request.getSession().setAttribute("message", character.getName() + " has been deleted");
				return "redirect: ../dashboard";
			}
			else {
				request.getSession().setAttribute("message", "Character can not be deleted");
				return "redirect: .";
			}
		}
		else {
			return "redirect: ../";
		}
	}
	
	@RequestMapping(value = "/character/JoinTownAjax", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCall(HttpServletRequest request, @RequestParam int charId) {
		
		User user = (User) request.getSession().getAttribute("user");
		List<Integer> charIds = (List<Integer>) request.getSession().getAttribute("character-ids");
		if(! charIds.contains(charId)) {
			charIds.add(charId);
		}
		List<Character> allChars = charDao.findByUserId(user.getId());
		List<Character> outChars = new ArrayList<Character>();
		
		for (Character c: allChars) {
			if(! charIds.contains(c.getCharId())) {
				outChars.add(c);
			}
		}
		
		request.getSession().setAttribute("character-ids", charIds);
		String result = "<select id=\"charOption\">";
		for(Character c : outChars){
      	  result += "<option value=\"" + c.getCharId() + "\">"+ c.getName() +"</option>";
        }
		result += "</select>&";
		
		for(int curId : charIds){
			Character c = charDao.findById(curId);
      	  result += "<div class=\"row\">"
      	  		+ "<div class=\"sub-7 text-bold\">"+ c.getName() +"</div>"
      	  		+ "<div class=\"sub-5\">"+ c.getClassification() +"</div>"
      	  		+ "</div>";
        }
		
		return result;
	}
	
}
