package com.novaclangaming.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaclangaming.dao.ICharacterDao;
import com.novaclangaming.dao.IItemDao;
import com.novaclangaming.dao.ITownDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.dao.JPAItemDao;
import com.novaclangaming.dao.JPAStructureDao;
import com.novaclangaming.dao.JPATownDao;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Structure;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownStatus;
import com.novaclangaming.model.User;

@Controller
public class TownController {
	
	JPAAuthentication auth = new JPAAuthentication();
	ITownDao townDao = new JPATownDao();
	ICharacterDao charDao = new JPACharacterDao();
	IItemDao itemDao = new JPAItemDao();
	
	
	@RequestMapping(value = "/town/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, @RequestParam String townName, 
			@RequestParam int townSize, @RequestParam int mapSize, @RequestParam String townMode) {
		User user = auth.loggedUser(request);
		if(user != null) {
			if(!townDao.findByName(townName).isPresent()) {
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
	
	@RequestMapping(value = "/town/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				return "town/home";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/citizens", method = RequestMethod.GET)
	public String citizens(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				return "town/citizens";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/construction", method = RequestMethod.GET)
	public String construction(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				//find all structures that are unlocked/started/completed
				int townId = auth.activeCharacter(request).getTown().getTownId();
				List<Structure> defenceStructures = JPAStructureDao.findAllDefence();
				List<Structure> supplyStructures = JPAStructureDao.findAllSupply();
				List<Structure> productionStructures = JPAStructureDao.findAllProduction();
				request.getSession().setAttribute("defenceStructures", defenceStructures);
				request.getSession().setAttribute("supplyStructures", supplyStructures);
				request.getSession().setAttribute("productionStructures", productionStructures);
				return "town/construction";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/storage", method = RequestMethod.GET)
	public String storage(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				//Assign page scope values for different storage categories
				int townId = auth.activeCharacter(request).getTown().getTownId();
				List<ItemStackZone> consumables = townDao.findItemsInStorage(townId, ItemCategory.Consumable);
				List<ItemStackZone> resources = townDao.findItemsInStorage(townId, ItemCategory.Resource);
				List<ItemStackZone> weapons = townDao.findItemsInStorage(townId, ItemCategory.Weapon);
				List<ItemStackZone> ammo = townDao.findItemsInStorage(townId, ItemCategory.Ammo);
				request.getSession().setAttribute("consumables", consumables);
				request.getSession().setAttribute("resources", resources);
				request.getSession().setAttribute("weapons", weapons);
				request.getSession().setAttribute("ammo", ammo);
				return "town/storage";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/special", method = RequestMethod.GET)
	public String special(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				return "town/special";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/outside", method = RequestMethod.GET)
	public String outside(HttpServletRequest request) {
		if(auth.loggedUser(request) != null) {
			if(auth.activeCharacter(request) != null) {
				return "town/outside";
			}
		}
		return "redirect: ../";
	}
}
