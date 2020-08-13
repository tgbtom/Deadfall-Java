package com.novaclangaming.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaclangaming.dao.ICharacterDao;
import com.novaclangaming.dao.IItemDao;
import com.novaclangaming.dao.ITownDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.dao.JPAItemDao;
import com.novaclangaming.dao.JPAItemStackZoneDao;
import com.novaclangaming.dao.JPAStructureDao;
import com.novaclangaming.dao.JPATownDao;
import com.novaclangaming.model.Character;
import com.novaclangaming.model.CharacterClass;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Structure;
import com.novaclangaming.model.StructureCost;
import com.novaclangaming.model.StructureProgress;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownStatus;
import com.novaclangaming.model.User;

@Controller
public class TownController {
	
	JPAAuthentication auth = new JPAAuthentication();
	JPATownDao townDao = new JPATownDao();
	ICharacterDao charDao = new JPACharacterDao();
	IItemDao itemDao = new JPAItemDao();
	JPAStructureDao structureDao = new JPAStructureDao();
	
	
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
				Town town = auth.activeCharacter(request).getTown();
				List<Structure> unlockedStructures = JPAStructureDao.findUnlockedStructures(town);
				Map<Structure, StructureProgress> progressOfStructures = new TreeMap<Structure, StructureProgress>();
				for(Structure base : unlockedStructures) {
					//Check if it exists in town (meaning it was started)
					Optional<StructureProgress> progress = JPAStructureDao.findProgress(town, base);
					if(progress.isPresent()) {
						progressOfStructures.put(base, progress.get());
					}
					else {
						StructureProgress filler = new StructureProgress(town, base, 0, 0);
						progressOfStructures.put(base, filler);
					}
				}
				request.getSession().setAttribute("unlockedDefence", progressOfStructures);
				request.getSession().setAttribute("townId", town.getTownId());
				request.getSession().setAttribute("storageId", this.townDao.findStorageZone(town.getTownId()).getZoneId());
				request.getSession().setAttribute("structureDao", new JPAStructureDao());
				request.getSession().setAttribute("stackZoneDao", new JPAItemStackZoneDao(this.townDao));
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
				request.getSession().setAttribute("townDao", townDao);
				return "town/outside";
			}
		}
		return "redirect: ../";
	}
	
	@RequestMapping(value = "/town/construct/{structureId}/{apToAssign}", method = RequestMethod.GET)
	public String assignAp(HttpServletRequest request, @PathVariable int structureId, @PathVariable int apToAssign) {
		//1, check the user is logged in, then check if the current char has enough AP
		//2a, ensure that the structure requirements ARE indeed met IF structure has not began
		//2b, see if we need to remove resources to start construction, if so, clarify we have enough
		//3, remove required resources and assign AP. If AP exceeds the required amount, reduce the consumed AP accordingly. (BUILDER IS 2X)
		//4, if AP >= requirement, update the structure level for the current town, and grant defence accordingly
		//5, Update the character stats for construction contributed
		//6, TODO Set message to display before redirecting to structures page
		
		if(auth.loggedUser(request) != null) {
			Character character = auth.activeCharacter(request);
			Structure structure = structureDao.findById(structureId);
			Optional<StructureProgress> progress = JPAStructureDao.findProgress(character.getTown(), structure);
			//if character has enough ap AND the building is not already max level
			if(character.getCurrentAp() >= apToAssign && progress.isPresent() ? progress.get().getLevel() < structure.getLevels() : false) {
				//has the structure began yet?
				boolean transferAp = false;
				if(progress.isPresent() ? progress.get().getAp() > 0 : false) {
						//Only need to worry about AP
						transferAp = true;
				}
				else {
					//Need to worry about construction costs too
					if(structureDao.isStructureAffordable(character.getTown().getTownId(), structureId)) {
						//remove the costs, if successfull removeAp = true
						transferAp = true;
						List<StructureCost> costs = structure.getCosts();
						for (StructureCost cost : costs) {
							if(!townDao.removeItemFromStorage(character.getTown().getTownId(), cost.getItem(), cost.getQuantity())) {
								transferAp = false;
							}
						}
					}
				}
				
				if(transferAp) {
					StructureProgress currentProgress = progress.isPresent() ? progress.get() : new StructureProgress(character.getTown(), structure, 0, 0);
					
					//If character is Builder, double the assigned amount
					int apToRemove = apToAssign;
					if (character.getClassification() == CharacterClass.Builder) {
						apToAssign *= 2;
					}
					if(apToAssign >= structure.getApCost() - currentProgress.getAp()) {
						apToAssign = structure.getApCost() - currentProgress.getAp();
						apToRemove = character.getClassification() == CharacterClass.Builder ? (int) Math.ceil(apToAssign / 2) : apToAssign;
						currentProgress.setLevel(currentProgress.getLevel() + 1);
						currentProgress.setAp(0);
						
						//STRUCTURE LEVELS UP
						structureDao.structureLevelUp(character.getTown(), structure, currentProgress.getLevel());
						request.getSession().setAttribute("message", "You have successfully levelled up " + structure.getName() + " to level " + currentProgress.getLevel() + ".");
					}
					else {
						currentProgress.setAp(currentProgress.getAp() + apToAssign);
						request.getSession().setAttribute("message", "You have successfully assigned " + apToAssign + " AP to " + structure.getName() + ".");
					}
					structureDao.updateProgress(currentProgress);
					character.setCurrentAp(character.getCurrentAp() - apToRemove);
					character.setCurConstructionCont(character.getCurConstructionCont() + apToAssign);
					charDao.update(character);
				}
			}
			return "redirect: /Deadfall/town/construction";
		}
		return "redirect: ../";
	}
}
