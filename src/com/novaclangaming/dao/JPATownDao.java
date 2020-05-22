package com.novaclangaming.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownBulletin;
import com.novaclangaming.model.Zone;

public class JPATownDao implements ITownDao {

	private IItemDao itemDao;
	private IItemStackZoneDao stackDao;
	
	public JPATownDao() {
		super();
		itemDao = new JPAItemDao();
		stackDao = new JPAItemStackZoneDao(this);
	}

	public void create(Town town) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		town = em.merge(town);
		em.getTransaction().commit();

		// populate df_town_zones with all of the necessary rows for this town size
		em.getTransaction().begin();
		int x = 6, y = 5;
		Zone zone;
		for (int i = 0; i < town.getMapSize() * town.getMapSize(); i++) {
			if (x > -5) {
				x--;
			} else {
				x = 5;
				y--;
			}
			zone = new Zone(x, y, 10, 0, "");
			zone.setTown(town);
			em.persist(zone);
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
		 
		 em.getTransaction().commit();
		 em.close();
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
		return Optional.ofNullable(result);
	}

	public void addBulletin(TownBulletin tb) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(tb);
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
	}

}
