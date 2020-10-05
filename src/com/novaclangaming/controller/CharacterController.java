package com.novaclangaming.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novaclangaming.dao.ICharacterDao;
import com.novaclangaming.dao.ITownDao;
import com.novaclangaming.dao.JPAAuthentication;
import com.novaclangaming.dao.JPACharacterDao;
import com.novaclangaming.dao.JPAItemDao;
import com.novaclangaming.dao.JPAItemStackCharacterDao;
import com.novaclangaming.dao.JPATownDao;
import com.novaclangaming.dao.JPAUserDao;
import com.novaclangaming.model.CharacterClass;
import com.novaclangaming.model.CharacterStatus;
import com.novaclangaming.model.Consumable;
import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemRarity;
import com.novaclangaming.model.ItemStackCharacter;
import com.novaclangaming.model.Status;
import com.novaclangaming.model.StructureProgress;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownBulletin;
import com.novaclangaming.model.TownStatus;
import com.novaclangaming.model.User;
import com.novaclangaming.model.Weapon;
import com.novaclangaming.model.Zone;
import com.novaclangaming.model.ZoneBulletin;
import com.novaclangaming.model.Character;

@Controller
public class CharacterController {

	JPACharacterDao charDao = new JPACharacterDao();
	JPAUserDao userDao = new JPAUserDao();
	JPATownDao townDao = new JPATownDao();
	JPAItemDao itemDao = new JPAItemDao();
	JPAAuthentication auth = new JPAAuthentication();
	
	@RequestMapping(value = "/character/create", method = RequestMethod.POST)
	public String create(@RequestParam String charName, @RequestParam CharacterClass charClass, HttpServletRequest request) {
		User user = auth.loggedUser(request);
		if(user != null) {
			List<Character> characters = charDao.findByUserId(user.getId());
			if(characters.size() < 20 && !charDao.findByCharName(user.getId(), charName).isPresent() && charName.length() < 25) {
				request.getSession().setAttribute("message", "Character: "+ charName +" was created successfully");
				Character character = new Character(user, charName, charClass);
				if(charClass == CharacterClass.Survivor) {
					character.setCurrentAp(16);
					character.setMaxAp(16);
				}
				else {
					character.setCurrentAp(12);
					character.setMaxAp(12);
				}
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
					return "redirect: ../town/home";
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
	
	@RequestMapping(value = "/character/JoinTownAjax/{dir}", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCall(HttpServletRequest request, @RequestParam int charId, @PathVariable String dir) {
		
		User user = (User) request.getSession().getAttribute("user");
		
		@SuppressWarnings("unchecked")
		List<Integer> charIds = (List<Integer>) request.getSession().getAttribute("character-ids");
		
		if(dir.equals("add")) {
			if(! charIds.contains(charId)) {
				charIds.add(charId);
			}
		}
		else if(dir.equals("remove") && charIds.contains(charId)) {
			charIds.remove(charIds.indexOf(charId));
		}
		
		
		List<Character> allChars = charDao.findByUserId(user.getId());
		List<Character> outChars = new ArrayList<Character>();
		int charsIn = allChars.size();
		
		for (Character c: allChars) {
			if(! charIds.contains(c.getCharId())) {
				outChars.add(c);
				charsIn--;
			}
		}
		
		request.getSession().setAttribute("character-ids", charIds);
		String result = "<select id=\"charOption\">";
		for(Character c : outChars){
			if(c.getTown() == null) {
				result += "<option value=\"" + c.getCharId() + "\">"+ c.getName() +"</option>";
			}
        }
		result += "</select>&";
		
		for(int curId : charIds){
			Character c = charDao.findById(curId);
      	  result += "<div class=\"row\">"
      	  		+ "<div class=\"sub-7 text-bold\">"+ c.getName() +"</div>"
      	  		+ "<div class=\"sub-4\">"+ c.getClassification() +"</div>"
				+ "<div class=\"sub-1\"><img src=\""+ request.getContextPath() +"/resources/" + 
						"img/small-x-red.png\" alt=\"remove\" onclick=\"unselectChar("+ curId +")\" class=\"msg-close\"></div>"
      	  		+ "</div>";
        }
		
		result += "&";
		
		//towns
		List<Town> towns = townDao.findAllOpenTowns();
		for(Town t: towns) {
			if(t.getTownSize() - t.getCharacters().size() >= charsIn) {
				result += "<div class=\"row bb\">" + 
						"<div class=\"sub-5 text-bold\">"+ t.getName() +"</div>" + 
						"<div class=\"sub-5\">[ "+ t.getCharacters().size() +" / "+ t.getTownSize() +"]</div>" + 
						"<div class=\"sub-2\">" + 
						"<button class=\"btn-play join-town\" id=\"" + t.getTownId() + "\">Join</button>" + 
						"</div>" + 
						"</div>";
			}
		}
		return result;
	}
	
	@RequestMapping(value = "character/ajax/town/join", method = RequestMethod.POST)
	@ResponseBody
	public String join(HttpServletRequest request, @RequestParam int townId) {
		User user = auth.loggedUser(request);
		if(user != null) {
			ITownDao townDao = new JPATownDao();
			ICharacterDao charDao = new JPACharacterDao();
			String allSuccess = "success";
			
			//ensure there is space for all the selected chars. Loop through to add chars, double-checking that the char is not in a town already
			@SuppressWarnings("unchecked")
			List<Integer> charIds = (List<Integer>) request.getSession().getAttribute("character-ids");
			Town town = townDao.findById(townId);
			if (town.getTownSize() - town.getCharacters().size() >= 0) {
				for (int id : charIds) {
					Character c = charDao.findById(id);
					if(c.getTown() != null){
						//One or more selected characters is already in a town
						return "fail";
					}
					else {
						//Joined successfully
						//TODO clear status effects then add starting ones
						charDao.clearStatus(c);
						c = charDao.findById(c.getCharId());
						CharacterStatus added = c.addStatus(charDao.findStatusById(4));
							charDao.addStatus(added);
							
							System.out.println(c.getStatus().get(0).getId() + " . " + c.getStatus().get(0).getStatus().getName());
							
							charDao.update(c);
						added = c.addStatus(charDao.findStatusById(8));
							charDao.addStatus(added);
							charDao.update(c);
						added = c.addStatus(charDao.findStatusById(12));
							charDao.addStatus(added);
							charDao.update(c);
						c.setTown(town);
						charDao.update(c);
						town = townDao.findById(townId);
						request.getSession().setAttribute("character", charDao.findById(c.getCharId())); //get managed entity
						TownBulletin tb = new TownBulletin("<span class='bulletinChar'>"+ c.getName() + "</span> [" 
								+ "<img src=\""+ request.getContextPath() +"/resources/img/icons/"+c.getClassification()+".png\">" 
								+ c.getClassification() +"] has joined the town! "
								+ (town.getTownSize() - town.getCharacters().size()) +" spots remaining before the town can begin!", new Date(), town); 
						townDao.addBulletin(tb);
					}
				}
			}
			else {

				return "fail";
			}
	
			return allSuccess;
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "character/ajax/pickitem", method = RequestMethod.POST)
	@ResponseBody
	public String storagePickUp(HttpServletRequest request, @RequestParam int itemId) {
		User user = auth.loggedUser(request);
		if(user != null) {
			Character character = auth.activeCharacter(request);
			if(character != null) {
				Item item = itemDao.findById(itemId);
				//check characters available capacity and compare against item being picked up.
				if(character.getCapacity() >= item.getMass()) {
					if(townDao.removeItemFromZone(character.getZone(), item, 1)) {
						charDao.addItem(character.getCharId(), item, 1);
					}
				}
				else {
					return "fail";
				}
				String result = "";
				character = auth.activeCharacter(request); //refreshes with new item
				for (ItemStackCharacter stack : character.getItemStacks()) {				
					result += "<div class=\"character-item-group\" id=\"character-item-"+ stack.getItem().getItemId() +"\" title=\""+ stack.getItem().getName() +"\">" +
							"	<img src=\""+ request.getContextPath() +"/resources/img/items/rarity/"+ stack.getItem().getRarity() +".png\" class=\"item-rarity\" />" +
							"		<img src=\""+ request.getContextPath() +"/resources/img/items/"+ stack.getItem().getName() +".png\" alt=\""+ stack.getItem().getName() +"\" class=\"character-item\" />\r\n" + 
							"		<span class=\"character-item-counter\" id=\"character-stack-"+ stack.getItem().getItemId() +"\">"+ stack.getQuantity() +"</span>\r\n" + 
							"	</div>";
				}
				if(character.getItemStacks().size() < 1) {
					result = "<div class=\"card-content\">No Items</div>";
				}
				else {
					if(20 - character.getCapacity() >= 18) {
						result += "<span class=\"text-red\">";
					}
					else if(20 - character.getCapacity() >= 14) {
						result += "<span class=\"text-yellow\">";
					}
					else {
						result += "<span class=\"text-green\">";
					}
					result += (20 - character.getCapacity()) + "/20</span>";
				}
				return result;
			}
			return "no active character";
		}
		else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/item/action/{action}", method = RequestMethod.POST)
	@ResponseBody
	public String performItemAction(HttpServletRequest request, @RequestParam int itemId, @PathVariable String action) {
		User user = auth.loggedUser(request);
		if(user != null) {
			Character character = auth.activeCharacter(request);
			JPAItemStackCharacterDao itemStackDao = new JPAItemStackCharacterDao(charDao);
			ItemStackCharacter itemHeld = character.hasItemById(itemId);
			if(itemHeld != null) {
				if(action.equals("Drop")) {
					charDao.dropItem(character, itemHeld.getItem(), 1);
				} else if (action.equals("Attack")) {
					if(character.getZone().getZeds() > 0) {
						//Check if we require AND Have the ammo
						//ensure at least 1 zed in zone, make sure we have enough AP
						Weapon weapon = itemDao.findById(itemId).getWeapon();
						Zone zone = townDao.findZoneById(character.getZone().getZoneId());
						Town town = townDao.findById(character.getTown().getTownId());
						if(weapon.getApCost() <= character.getCurrentAp() && 
								weapon.getAmmo() == null || 
								itemStackDao.findByCharItem(character.getCharId(), weapon.getAmmo().getItemId()).isPresent() &&
								zone.getZeds() >= 1){
							//consume ammo -> chance of output to produce ammo, chance of injure, chance of break
							if(weapon.getAmmo() != null) {
								character.removeItem(weapon.getAmmo(), 1);
								character = charDao.removeItem(character, weapon.getAmmo(), 1);
							}	
							character.setCurrentAp(character.getCurrentAp() - weapon.getApCost());
							int kills = Math.min(zone.getZeds(), new Random().nextInt(weapon.getMaxKills() - weapon.getMinKills() + 1) + weapon.getMinKills());
							zone.setZeds( zone.getZeds() - kills);
							zone.setDanger(zone.getDanger() - kills);
							zone = townDao.updateZone(zone);
							character.setCurZedsKilled(character.getCurZedsKilled() + kills);
							character = charDao.update(character);
							String bulletinText = "<span class='bulletinChar'>"+character.getName() + "</span> killed <span class='bulletinKill'>" + kills + " zeds</span> using a <span class='bulletinItem'>" + weapon.getItem().getName() + "</span>.";
							town.setHordeSize(town.getHordeSize() - kills);
							
							if(new Random().nextInt(100) + 1 <= weapon.getChanceOfInjury()) {
								character = charDao.increaseInjury(character);
								bulletinText += " <span class='bulletinKill'>They were injured while fighting.</span>";
							}
							if(new Random().nextInt(100) + 1 <= weapon.getChanceOfBreak()) {
								//weapon breaks
								character = charDao.removeItem(character, weapon.getItem(), 1);
								if(weapon.getItemOnBreak() != null) {
									charDao.addItem(character.getCharId(), weapon.getItemOnBreak(), 1);
								}
								bulletinText += " <span class='bulletinKill'>The weapon broke on use.</span>";
							}
							
							ZoneBulletin zb = new ZoneBulletin(bulletinText, new Date(), zone);
							townDao.addBulletin(zb);
							town = townDao.update(town);
						}
					}
				} else if (action.equals("Consume")) {
					
					Consumable consumable = itemDao.findById(itemId).getConsumable();
					//If food, reduce hunger, if not ATE add ATE and give AP ELSE only reduce hunger and give health
					//If drink, reduce hunger, if not DRANK add DRANK and give AP ELSE only reduce thirst and give health
					if(consumable.getConsumeType().equals("Drink")) {
						if(!character.hasStatusByName("Drank")) {
							character.setCurrentAp(Math.min(character.getMaxAp(), character.getCurrentAp() + (character.getMaxAp() * consumable.getApGain() / 100)));
							charDao.update(character);
							charDao.addStatus(new CharacterStatus(charDao.findStatusByName("Drank"), character));
						} 
						character = charDao.decreaseThirst(character);
						character = charDao.removeItem(character, consumable.getItem(), 1);
					} else if (consumable.getConsumeType().equals("Eat")) {
						if(!character.hasStatusByName("Ate")) {
							character.setCurrentAp(Math.min(character.getMaxAp(), character.getCurrentAp() + (character.getMaxAp() * consumable.getApGain() / 100)));
							charDao.update(character);
							charDao.addStatus(new CharacterStatus(charDao.findStatusByName("Ate"), character));
						} 
						character = charDao.decreaseHunger(character);
						character = charDao.removeItem(character, consumable.getItem(), 1);
					}
					
				}
				
			}else {
				//Character had none of the requested item
			}
			
			
			return "results";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/character/change/{charId}/{dir}", method = RequestMethod.POST)
	@ResponseBody
	public String changeActiveCharacter(HttpServletRequest request, @PathVariable int charId, @PathVariable String dir) {
		//dir = Previous, Next, or None
		//charId = 0, or actual character's id
		//For dir, find the corresponding character in the given direction by Alphabetical sorting within the town
		//if CharId is specified then we ignore dir, if it is 0 then we check dir
		//if dir is "none" and charId is 0 then nothing should happen (unexpected input)
		User user = auth.loggedUser(request);
		Character currentChar = auth.activeCharacter(request);
		if(user != null) {
			if(charId != -1) {
				//specific character
				Character changedChar = charDao.findById(charId).getUser().getId() == user.getId() ? charDao.findById(charId) : null;
				if (changedChar != null) {
					auth.changeCharacter(request, charDao.findById(charId));
				}
			}
			else if (!dir.equals("none")) {
				if(dir.equals("Previous")) {
					auth.changeCharacter(request, townDao.getAdjacentChar(currentChar, "Previous"));
				}else if (dir.equals("Next")) {
					auth.changeCharacter(request, townDao.getAdjacentChar(currentChar, "Next"));
				}
			}else {
				//do nothing
			}
			return null;
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/character/loot", method = RequestMethod.POST)
	@ResponseBody
	public String lootFromActiveCharacter(HttpServletRequest request) {
		User user = auth.loggedUser(request);
		Character currentChar = auth.activeCharacter(request);
		if(user != null && currentChar != null) {
			Item looted;
			String bulletinString;
			if(currentChar.getCurrentAp() >= 1) {
				currentChar.setCurrentAp(currentChar.getCurrentAp() - 1);
				charDao.update(currentChar);
				if(currentChar.getZone().getLootability() > 0) {
					int randomNum = new Random().nextInt(200) + 1;
					if(randomNum > 0 && randomNum <= 120) {
						//loot common
						looted = itemDao.getRandom(ItemRarity.Common);
						bulletinString = currentChar.getName() + " found <span class='text-Common'>" + looted.getName() + "</span>";
					} else if (randomNum >= 121 && randomNum <= 170) {
						//loot uncommon
						looted = itemDao.getRandom(ItemRarity.UnCommon);
						bulletinString = currentChar.getName() + " found <span class='text-UnCommon'>" + looted.getName() + "</span>";
					}else if (randomNum >= 171 && randomNum <= 190) {
						//loot rare
						looted = itemDao.getRandom(ItemRarity.Rare);
						bulletinString = currentChar.getName() + " found <span class='text-Rare'>" + looted.getName() + "</span>";
					}else if (randomNum >= 191 && randomNum <= 197) {
						//loot Epic
						looted = itemDao.getRandom(ItemRarity.Epic);
						bulletinString = currentChar.getName() + " found <span class='text-Epic'>" + looted.getName() + "</span>";
					}else {
						//loot Legendary
						looted = itemDao.getRandom(ItemRarity.Legendary);
						bulletinString = currentChar.getName() + " found <span class='text-Legendary'>" + looted.getName() + "</span>";
					}
				}
				else {
					looted = itemDao.getRandom(ItemRarity.Scrap);
					bulletinString = currentChar.getName() + " found <span class='text-Scrap'>" + looted.getName() + "</span>.";
				}
				
				if(currentChar.getZone().getDanger() > 0) {
					int chanceOfInjury = currentChar.getZone().getDanger() * 10;
					int randomRoll = new Random().nextInt(100) + 1;
					if(randomRoll <= chanceOfInjury) {
						currentChar = charDao.increaseInjury(currentChar);
						bulletinString += " They were injured while searching.";
					}
				}
				
				if(currentChar.getCapacity() >= looted.getMass() && !currentChar.hasStatusByName("Dead")) {
					charDao.addItem(currentChar.getCharId(), looted, 1);
				}else {
					townDao.addItemToZone(currentChar.getZone().getZoneId(), looted.getItemId(), 1);
				}
				
				Zone thisZone = currentChar.getZone();
				if(thisZone.getLootability() == 1) {
					bulletinString += " <span class='text-bright-red'>The zone has been depleted</span>.";
				}
				thisZone.setLootability(Math.max(0, thisZone.getLootability() - 1));
				townDao.updateZone(thisZone);
				currentChar.setCurTimesLooted(currentChar.getCurTimesLooted() + 1);
				currentChar = charDao.update(currentChar);
				
				ZoneBulletin bulletin = new ZoneBulletin(bulletinString, new Date(), thisZone);
				townDao.addBulletin(bulletin);
				return looted.getName() + " - " + looted.getRarity();
				
			} else {
				return "fail: not enough ap";
			}
		} else {
			return "fail";
		}
	}
	
	@RequestMapping(value="/character/endday", method = RequestMethod.GET)
	public String endDay(HttpServletRequest request) {
		//update character status then redirect to the citizens page
		User user = auth.loggedUser(request);
		Character currentChar = auth.activeCharacter(request);
		if(user != null) {
			Status dayEnded = charDao.findStatusByName("Day Ended");
			Status notDone = charDao.findStatusByName("Not Done");
			Status dead = charDao.findStatusByName("Dead");
			if(currentChar.hasStatus(notDone)) {
				charDao.removeStatus(charDao.findCharacterStatus(currentChar, notDone));
				charDao.addStatus(new CharacterStatus(dayEnded, currentChar));
			}
			//Check if Dead characters + alive characters with status of Day Ended = towns max size
			int numOfCompleted = 0;
			for(Character townChar : currentChar.getTown().getCharacters()) {
				if(townChar.hasStatusByName("Day Ended") || townChar.hasStatusByName("Dead")) {
					numOfCompleted++;
				}
			}
			System.out.println("Done: " + numOfCompleted);
			if(numOfCompleted >= currentChar.getTown().getTownSize() - 1) {
				//DAY ENDS
				endTheDay(currentChar.getTown());
				
			}
			return "redirect: ../town/citizens";
		}else {
			return "redirect: ../";
		}
	}
	
	private void endTheDay(Town town) {
		//Midnight attack 
		//Death by Overrun
		//Nightly structure functions
		//Death by camping out of town ()
		ArrayList<Character> aliveOutOfTown = aliveAndOutOfTownCharacters(town);
		ArrayList<Character> aliveInTown = aliveAndInTownCharacters(town);
		if(town.getHordeSize() > town.getDefence()) {
			//OVERRUN
			//calculate number of deaths, and find a way to randomize deaths between alive survivors in TOWN, chars out of town cannot die from OVERRUN
			int overrun = town.getHordeSize() - town.getDefence();
			int numOfDeaths = overrun * 5 / 100;
			int chanceDeath = numOfDeaths * 100 % 100;
			if(new Random().nextInt(100) + 1 < chanceDeath) {
				numOfDeaths++;
			}
			Status dead = charDao.findStatusByName("Dead");
			if(aliveInTown.size() > 0) {
				for(int i = 0; i < numOfDeaths; i++) {
					//kill random citizen
					int randomIndex = new Random().nextInt(aliveInTown.size());
					charDao.addStatus(new CharacterStatus(dead, aliveInTown.get(randomIndex)));
					townDao.addBulletin(new TownBulletin(aliveInTown.get(randomIndex).getName() + " was killed by the zeds that broke through town defences.", new Date(), town));
					aliveInTown.remove(randomIndex);
					if(aliveInTown.size() <= 0) {
						break;
					}
				}
			}
			
			
			for(Character campingChar : aliveOutOfTown) {
				int chanceOfDeath = Math.min(30 + campingChar.getZone().getZeds() * 10, 95);
				if (new Random().nextInt(100) + 1 <= chanceOfDeath) {
					charDao.addStatus(new CharacterStatus(dead, campingChar));
					townDao.addBulletin(new TownBulletin(campingChar.getName() + " did not survive the night outside of town.", new Date(), town));
					aliveOutOfTown.remove(campingChar);
					if(aliveOutOfTown.size() <= 0) {
						break;
					}
				}
			}
			
			townDao.addBulletin(new TownBulletin("The town was overrun overnight and "+ numOfDeaths +" citizens have been killed!", new Date(), town));
		} else {
			//SAFE FROM ATTACK
			townDao.addBulletin(new TownBulletin("Defences held out and no zombies got into town overnight.", new Date(), town));
		}
		
		//Update Hunger Thirst, Injury, including deaths
		for(Character townChar : town.getCharacters()) {
			if(town.getDayNumber() % 2 == 1) {
				charDao.increaseHunger(townChar);
			}
			charDao.increaseThirst(townChar);
			if(townChar.hasStatusByName("Minor Injury") ||
					townChar.hasStatusByName("Moderate Injury")||
					townChar.hasStatusByName("Severe Injury")||
					townChar.hasStatusByName("Infected")) {
				if(new Random().nextInt(100) > 49) {
					charDao.increaseInjury(townChar);
				}
			}
		}
		
		town = townDao.findById(town.getTownId());
		aliveOutOfTown = aliveAndOutOfTownCharacters(town);
		aliveInTown = aliveAndInTownCharacters(town);
		
		if (aliveInTown.size() + aliveOutOfTown.size() <= 0) {
			//all characters are dead
			//Update all characters current stats to legacy stats and chenge town to null
			//change town Status to Ended
			town.setStatus(TownStatus.Ended);
			townDao.update(town);
			for (Character townChar : town.getCharacters()) {
				townChar.setLifetimeCamps(townChar.getLifetimeCamps() + townChar.getCurCamps());
				townChar.setLifetimeConstructionCont(townChar.getLifetimeConstructionCont() + townChar.getCurConstructionCont());
				townChar.setCurConstructionCont(0);
				townChar.setLifetimeDistanceTravelled(townChar.getLifetimeDistanceTravelled() + townChar.getCurDistanceTravelled());
				townChar.setCurDistanceTravelled(0);
				townChar.setLifetimeTimesLooted(townChar.getLifetimeTimesLooted() + townChar.getCurTimesLooted());
				townChar.setCurTimesLooted(0);
				townChar.setLifetimeZedsKilled(townChar.getLifetimeZedsKilled() + townChar.getCurZedsKilled());
				townChar.setCurZedsKilled(0);
				townChar.setTown(null);
				charDao.update(townChar);
			}
		} else {
			//Zed Spread if Day >= 4
			if(town.getDayNumber() >= 4) {
				spreadZeds(town);
			}
			town.setDayNumber(town.getDayNumber() + 1);
			performNightlyFunctions(town);
			townDao.update(town);
			
			ArrayList<Character> aliveChars = aliveInTown;
			aliveChars.addAll(aliveOutOfTown);
			for(Character aliveChar : aliveChars) {
				charDao.removeStatus(aliveChar.findCharacterStatusByName("Day Ended"));
				charDao.addStatus(new CharacterStatus(charDao.findStatusByName("Not Done"), aliveChar));
				aliveChar.setCurrentAp(aliveChar.getMaxAp());
				charDao.update(aliveChar);
			}
		}
	}
	
	private ArrayList<Character> aliveAndInTownCharacters(Town town){
		ArrayList<Character> results = new ArrayList<Character>();
		for(Character townChar : town.getCharacters()) {
			if(townChar.getZone().getX() == 0 && townChar.getZone().getY() == 0 && !townChar.hasStatusByName("Dead")) {
				results.add(townChar);
			}
		}
		return results;
	}
	
	private ArrayList<Character> aliveAndOutOfTownCharacters(Town town){
		ArrayList<Character> results = new ArrayList<Character>();
		for(Character townChar : town.getCharacters()) {
			if(townChar.getZone().getX() != 0 && townChar.getZone().getY() != 0 && !townChar.hasStatusByName("Dead")) {
				results.add(townChar);
			}
		}
		return results;
	}
	
	private void spreadZeds(Town town) {
		Map<Zone, Integer> newZeds = new HashMap<Zone, Integer>();
		for(Zone zone : town.getZones()) {
			int x = zone.getX();
			int y = zone.getY();
			int spacialDanger = zone.getZeds();
			if(x-1 > -6) {
				spacialDanger += town.getZone(x-1, y).getZeds();
			}
			if(x+1 < 6) {
				spacialDanger += town.getZone(x+1, y).getZeds();
			}
			if(y-1 > -6) {
				spacialDanger += town.getZone(x, y-1).getZeds();
			}
			if(y+1 < 6) {
				spacialDanger += town.getZone(x, y+1).getZeds();
			}
			
			int maxSpread = (int) Math.ceil(spacialDanger * 0.09);
			int minSpread = (int) Math.ceil(maxSpread * 0.2);
			if(maxSpread - minSpread > 0) {
				newZeds.put(zone, zone.getZeds() + new Random().nextInt(maxSpread - minSpread) + 1);
			}else {
				newZeds.put(zone, zone.getZeds());
			}
		}
		
		//update all zones that are in the MAP
		ArrayList<Zone> updateZones = new ArrayList<Zone>();
		int newHorde = 0;
		newZeds.forEach((key, value) -> updateZones.add(key.setZeds(value)));
		updateZones.replaceAll(zone -> zone.setDanger(zone.getZeds() - 3 * zone.getCharacters().size()));
		
		townDao.updateZones(updateZones);
	}
	
	private void performNightlyFunctions(Town town) {
		//Search for structures that perform special features overnight
		int found = 0;
		int itemGain = 0;
		for(StructureProgress struc : town.getStructuresInProgress()) {
			//Currently there are 2 nightly structure, if we found both exist we dont need to check any more records
			if(found >= 2) {
				break;
			} else {
				if(struc.getStructure().getName().equals("Water Reserve")) {
					itemGain = new Random().nextInt(3)+2;
					townDao.addItemToStorage(town.getTownId(), itemDao.findByName("Water Ration"), itemGain);
					townDao.addBulletin(new TownBulletin(itemGain + " water rations were gained from the Water Reserve. ", new Date(), town));
					found++;
				}
				else if(struc.getStructure().getName().equals("Vegetable Garden")) {
					itemGain = new Random().nextInt(4)+1;
					townDao.addItemToStorage(town.getTownId(), itemDao.findByName("Carrot"), itemGain);
					townDao.addBulletin(new TownBulletin(itemGain + " carrots were gained from the Vegetable Garden. ", new Date(), town));
					found++;
				}
			}
		}
	}

}
