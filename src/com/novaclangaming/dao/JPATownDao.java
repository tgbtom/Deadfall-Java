package com.novaclangaming.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Status;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownBulletin;
import com.novaclangaming.model.Zone;
import com.novaclangaming.model.ZoneBulletin;
import com.novaclangaming.model.Character;

public class JPATownDao implements ITownDao {

	private IItemDao itemDao;
	private IItemStackZoneDao stackDao;
	private JPACharacterDao charDao;
	
	public JPATownDao() {
		super();
		itemDao = new JPAItemDao();
		stackDao = new JPAItemStackZoneDao(this);
		charDao = new JPACharacterDao(this);
	}
	
	public JPATownDao(JPACharacterDao charDao) {
		super();
		itemDao = new JPAItemDao();
		stackDao = new JPAItemStackZoneDao(this);
		this.charDao = charDao;
	}

	public void create(Town town) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		town = em.merge(town);
		em.getTransaction().commit();

		// populate df_town_zones with all of the necessary rows for this town size
		em.getTransaction().begin();
		int zedCount = 0;
		int x = 6, y = 5;
		Zone zone;
		for (int i = 0; i < town.getMapSize() * town.getMapSize(); i++) {
			if (x > -5) {
				x--;
			} else {
				x = 5;
				y--;
			}
			zone = new Zone(x, y, 10, this.getRandomZeds(Math.abs(x) + Math.abs(y)), "");
			zone.setTown(town);
			em.persist(zone);
			zedCount += zone.getZeds();
		}
		em.getTransaction().commit();

		// fill storage with starter items
		Zone storage = this.findStorageZone(town.getTownId());
		ItemStackZone stack;
		
		 em.getTransaction().begin();
		 
		 Optional<ItemStackZone> storedLoc = stackDao.findByZoneItem(storage.getZoneId(), itemDao.findByName("Water Ration").getItemId());
		 if(storedLoc.isPresent()) {
			 storage.addItem(itemDao.findByName("Water Ration"), 30);
			 stack = storedLoc.get();
			 stack.addToStack(15);
		 }
		 else {
			 stack = storage.addItem(itemDao.findByName("Water Ration"), 30);
		 }
		 em.merge(storage);
		 em.merge(stack);

		 storedLoc = stackDao.findByZoneItem(storage.getZoneId(), itemDao.findByName("Bits of Food").getItemId());
		 if(storedLoc.isPresent()) {
			 storage.addItem(itemDao.findByName("Bits of Food"), 15);
			 stack = storedLoc.get();
			 stack.addToStack(15);
		 }
		 else {
			 stack = storage.addItem(itemDao.findByName("Bits of Food"), 15);
		 }
		 em.merge(storage);
		 em.merge(stack);
		 
		 town.setHordeSize(zedCount);
		 update(town);
		 
		 em.getTransaction().commit();
		 em.close();
	}

	private int getRandomZeds(int distance) {
		switch (distance) {
		case 0:
		case 1:
			return 0;
		case 2:
			return new Random().nextInt(2); //0-1
		case 3:
		case 4:
		case 5:
			return new Random().nextInt(4); //0-3
		case 6:
		case 7:
			return new Random().nextInt(5) + 1; //1-5
		case 8:
		case 9:
			return new Random().nextInt(4) + 3; //3-6
		default:
			return new Random().nextInt(6) + 5; //5-10
		}
	}
	
	public Zone findStorageZone(int townId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Zone> query = em.createNamedQuery("Zone.findStorageByTownId", Zone.class);
		query.setParameter("town", findById(townId));
		Zone storage = query.getSingleResult();
		em.close();
		return storage;
	}
	
	public Zone findZoneByCoords(int townId, int x, int y) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Zone> query = em.createNamedQuery("Zone.findZoneByCoords", Zone.class);
		query.setParameter("town", findById(townId));
		query.setParameter("x", x);
		query.setParameter("y", y);
		Zone result = query.getSingleResult();
		em.close();
		return result;
	}

	public Zone findZoneById(int zoneId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Zone zone = em.find(Zone.class, zoneId);
		em.close();
		return zone;
	}
	
	public Town findById(int townId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Town town = em.find(Town.class, townId);
		em.close();
		return town;
	}

	public Town update(Town town) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Town managedTown = em.merge(town);
		em.getTransaction().commit();
		em.close();
		return managedTown;
	}

	public void delete(Town town) {
		// TODO Auto-generated method stub

	}

	public List<Town> findAllOpenTowns() {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Town> query = em.createNamedQuery("Town.findOpen", Town.class);
		List<Town> towns = query.getResultList();
		em.close();
		return towns;
	}

	public Optional<Town> findByName(String townName) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Town> query = em.createNamedQuery("Town.findByName", Town.class);
		query.setParameter("name", townName);
		Town result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
		}
		em.close();
		return Optional.ofNullable(result);
	}

	public void addBulletin(TownBulletin tb) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(tb);
		em.getTransaction().commit();
		em.close();
	}
	
	public void addBulletin(ZoneBulletin zb) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(zb);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<ItemStackZone> findItemsInStorage(int townId, ItemCategory category) {
		Zone storage = findStorageZone(townId);
		List<ItemStackZone> categoryStacks = new ArrayList<ItemStackZone>();
		List<ItemStackZone> allStacks = storage.getItemStacks();
		for (ItemStackZone stack : allStacks) {
			if(stack.getItem().getCategory() == category) {
				categoryStacks.add(stack);
			}
		}
		return categoryStacks;
	}
	
	public void addItemToZone(int zoneId, int itemId, int qty) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		Zone zone = findZoneById(zoneId);
		Item item = itemDao.findById(itemId);
		Optional<ItemStackZone> foundZoneStack = stackDao.findByZoneItem(zoneId, itemId);
		
		ItemStackZone zoneStack;
		if(foundZoneStack.isPresent()) {
			zone.addItem(item, qty);
			zoneStack = foundZoneStack.get();
			zoneStack.addToStack(qty);
		}else {
			zoneStack = zone.addItem(item, qty);
		}
		
		em.merge(zone);
		em.merge(zoneStack);
		
		em.getTransaction().commit();
		em.close();
	}

	public void addItemToStorage(int townId, Item item, int qty) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		
		Zone storage = this.findStorageZone(townId);
		ItemStackZone stack;
		
		 em.getTransaction().begin();
		 
		 Optional<ItemStackZone> storedLoc = stackDao.findByZoneItem(storage.getZoneId(), item.getItemId());
		 if(storedLoc.isPresent()) {
			 storage.addItem(itemDao.findById(item.getItemId()), qty);
			 stack = storedLoc.get();
			 stack.addToStack(qty);
		 }
		 else {
			 stack = storage.addItem(itemDao.findById(item.getItemId()), qty);
		 }
		 em.merge(storage);
		 em.merge(stack);
		 
		 em.getTransaction().commit();
		 em.close();
	}
	
	public boolean removeItemFromZone(Zone zone, Item item, int qty) {
		ItemStackZone stack;

		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		Optional<ItemStackZone> storedLoc = stackDao.findByZoneItem(zone.getZoneId(), item.getItemId());
		if(storedLoc.isPresent()) {
			zone.removeItem(itemDao.findById(item.getItemId()), qty);
			stack = storedLoc.get();
			stack.removeFromStack(qty);
		}
		else {
			em.close();
			return false;
		}
		
		if(stack.getQuantity() > 0) {
			em.merge(zone);
			em.merge(stack);
		}
		else {
			em.merge(zone);
			stack = em.merge(stack);
			em.remove(stack);
		}
		
		em.getTransaction().commit();
		em.close();
		return true;
	}
	
	public Zone updateZone(Zone zone) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Zone managedZone = em.merge(zone);
		em.getTransaction().commit();
		em.close();
		return managedZone;
	}
	
	public ArrayList<Zone> updateZones(ArrayList<Zone> zones) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Zone> managedZones = em.merge(zones);
		em.getTransaction().commit();
		em.close();
		return managedZones;
	}
	
	public Character getAdjacentChar(Character currentChar, String dir) {
		List<Character> townCharacters;
		if(dir.equals("Next")) {
			townCharacters = currentChar.getTown().getOrderedCharacters();
		}
		else {
			townCharacters = currentChar.getTown().getReverseOrderedCharacters();
		}
		int currentCharIndex = -1;
		Character target = null;
		Status deadStatus = charDao.findStatusByName("Dead");
		for (Character iChar : townCharacters) {
			if(currentCharIndex >= 0 && iChar.getUser().getId() == currentChar.getUser().getId() && charDao.findCharacterStatus(iChar, deadStatus) == null) {
				target = iChar;
				break;
			} else if(iChar.getCharId() == currentChar.getCharId()) {
				currentCharIndex = townCharacters.indexOf(iChar);
			}
		}
		
		if(target == null) {
			//we need to search from index 0 to index of currentChar
			for(int i = 0; i < currentCharIndex; i++) {
				if(townCharacters.get(i).getUser().getId() == currentChar.getUser().getId() && charDao.findCharacterStatus(townCharacters.get(i), deadStatus) == null) {
					target = townCharacters.get(i);
					break;
				}
			}
		}
		return target;
	}

}
