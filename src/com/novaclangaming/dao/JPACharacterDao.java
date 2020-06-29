package com.novaclangaming.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.UserBulletin;
import com.novaclangaming.model.Zone;
import com.novaclangaming.model.Character;
import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemStackCharacter;
import com.novaclangaming.model.ItemStackZone;

public class JPACharacterDao implements ICharacterDao{

	private IItemStackCharacterDao stackDao;
	private IItemDao itemDao;
	private ITownDao townDao;
	
	public JPACharacterDao() {
		super();
		stackDao = new JPAItemStackCharacterDao(this);
		itemDao = new JPAItemDao();
		townDao = new JPATownDao();
	}

	public void create(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(character);
		UserBulletin bulletin = new UserBulletin(character.getName() + " ["+ character.getClassification() +"] has been created.", new Date(), character.getUser());
		em.persist(bulletin);
		em.getTransaction().commit();
		em.close();
	}

	public Character findById(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Character c = em.find(Character.class, id);
		em.close();
		return c;
	}

	public List<Character> findByUserId(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Character> query = em.createNamedQuery("Character.findByUser", Character.class);
		query.setParameter("user", id);
		List<Character> characters = query.getResultList();
		return characters;
	}

	public List<Character> findByTownId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Character update(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Character managedChar = em.merge(character);
		em.getTransaction().commit();
		em.close();
		return managedChar;
	}

	public void delete(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		character = em.merge(character);
		em.remove(character);
		em.getTransaction().commit();
		em.close();
	}

	public Optional<Character> findByCharName(int userId, String charName) {
		Character character = null;
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Character> query = em.createNamedQuery("Character.findByName", Character.class);
		query.setParameter("userId", userId);
		query.setParameter("charName", charName);
		try {
			character = query.getSingleResult();
		} catch (NoResultException e) {}
		return Optional.ofNullable(character);
	}

	public void addItem(int charId, Item item, int qty) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		
		Character character = this.findById(charId);
		ItemStackCharacter stack;
		
		 em.getTransaction().begin();
		 
		 Optional<ItemStackCharacter> storedLoc = stackDao.findByCharItem(character.getCharId(), item.getItemId());
		 if(storedLoc.isPresent()) {
			 character.addItem(itemDao.findById(item.getItemId()), qty);
			 stack = storedLoc.get();
			 stack.addToStack(qty);
		 }
		 else {
			 stack = character.addItem(itemDao.findById(item.getItemId()), qty);
		 }
		 em.merge(character);
		 em.merge(stack);
		 
		 em.getTransaction().commit();
		 em.close();
	}
	
	public void dropItem(int charId, Item item, int qty) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		Character character = this.findById(charId);
		Optional<ItemStackCharacter> stack = stackDao.findByCharItem(charId, item.getItemId());
		
		if(stack.isPresent() && stack.get().getQuantity() >= qty) {
			ItemStackCharacter charStack = stack.get();
			charStack.removeFromStack(qty);
			Zone zone = character.getZone();
			townDao.addItemToZone(zone.getZoneId(), item.getItemId(), qty);
			
			if(charStack.getQuantity() > 0 ) {
				em.merge(zone);
				em.merge(charStack);
			}
			else {
				em.merge(zone);
				em.remove(charStack);
			}
			
		}
		
		em.getTransaction().commit();
		em.close();
	}

}
