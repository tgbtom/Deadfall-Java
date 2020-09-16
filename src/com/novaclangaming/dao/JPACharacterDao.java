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
import com.novaclangaming.model.CharacterStatus;
import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemStackCharacter;
import com.novaclangaming.model.Status;
import com.novaclangaming.model.TownBulletin;

public class JPACharacterDao implements ICharacterDao{

	private IItemStackCharacterDao stackDao;
	private IItemDao itemDao;
	private ITownDao townDao;
	
	public JPACharacterDao() {
		super();
		stackDao = new JPAItemStackCharacterDao(this);
		itemDao = new JPAItemDao();
		townDao = new JPATownDao(this);
	}
	
	public JPACharacterDao(JPATownDao townDao) {
		super();
		stackDao = new JPAItemStackCharacterDao(this);
		itemDao = new JPAItemDao();
		this.townDao = townDao;
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
		em.close();
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
		em.close();
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
	
	public void removeItem(int charId, Item item, int qty) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		Optional<ItemStackCharacter> stack = stackDao.findByCharItem(charId, item.getItemId());
		
		if(stack.isPresent() && stack.get().getQuantity() >= qty) {
			ItemStackCharacter charStack = stack.get();
			charStack.removeFromStack(qty);
			
			if(charStack.getQuantity() > 0 ) {
				em.merge(charStack);
			}
			else {
				em.remove(em.merge(charStack));
			}
			
		}
		
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
				em.remove(em.merge(charStack));
			}
			
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	public Status findStatusById(int statusId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Status s = em.find(Status.class, statusId);
		em.close();
		return s;
	}
	
	public Status findStatusByName(String statusName) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Status> query = em.createNamedQuery("Status.findByName", Status.class);
		query.setParameter("name", statusName);
		Status s = query.getSingleResult();
		em.close();
		return s;
	}
	
	public CharacterStatus findCharacterStatusById(int charStatusId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		CharacterStatus result = em.find(CharacterStatus.class, charStatusId);
		em.close();
		return result;
	}
	
	public void addStatus(CharacterStatus charStatus) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(charStatus);
		em.getTransaction().commit();
		em.close();
	}

	public void removeStatus(CharacterStatus charStatus){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(charStatus));
		em.getTransaction().commit();
		em.close();
	}

	public Character clearStatus(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<CharacterStatus> query = em.createNamedQuery("characterStatus.FindByCharacter", CharacterStatus.class);
		query.setParameter("character", character);
		List<CharacterStatus> allStatus = query.getResultList();
		for(CharacterStatus curr : allStatus) {
			em.remove(curr);
		}
		em.getTransaction().commit();
		em.close();
		return character;
	}
	
	public CharacterStatus findCharacterStatus(Character character, Status status) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<CharacterStatus> query = em.createNamedQuery("CharacterStatus.findByCharacterAndStatus", CharacterStatus.class);
		query.setParameter("character", character);
		query.setParameter("status", status);
		CharacterStatus result = null;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		em.close();
		return result;
	}
	
	public Character increaseInjury(Character character) {
		//Check if we have an injury status or not
		boolean increased = character.hasStatusByName("Dead");
		for(CharacterStatus status : character.getStatus()) {
			String statusName = status.getStatus().getName();
			if(statusName.equals("Minor Injury")) {
				removeStatus(status);
				addStatus(new CharacterStatus(findStatusByName("Moderate Injury"), character));		
				increased = true;
				break;
			}else if (statusName.equals("Moderate Injury")) {
				removeStatus(status);
				addStatus(new CharacterStatus(findStatusByName("Severe Injury"), character));	
				increased = true;
				break;
			}else if (statusName.equals("Severe Injury")) {
				removeStatus(status);
				addStatus(new CharacterStatus(findStatusByName("Infected"), character));	
				increased = true;
				break;
			}else if (statusName.equals("Infected")) {
				removeStatus(status);
				addStatus(new CharacterStatus(findStatusByName("Dead"), character));
				townDao.addBulletin(new TownBulletin(character.getName() + " died of infection.", new Date(), character.getTown()));
				increased = true;
				break;
			}
		}
		if(!increased) {
			addStatus(new CharacterStatus(findStatusByName("Minor Injury"), character));
		}
		return character;
	}
	
	public Character decreaseInjury(Character character) {
		//check if we have an injury status or not
		return character;
	}
	
	public Character increaseHunger(Character character) {
		//Check if we have an injury status or not
		boolean increased = character.hasStatusByName("Dead");
		if(increased == false) {
			for(CharacterStatus status : character.getStatus()) {
				String statusName = status.getStatus().getName();
				if(statusName.equals("Full")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Hungry"), character));		
					increased = true;
					break;
				}else if (statusName.equals("Hungry")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Very Hungry"), character));	
					increased = true;
					break;
				}else if (statusName.equals("Very Hungry")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Starving"), character));	
					increased = true;
					break;
				}else if (statusName.equals("Starving")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Dead"), character));
					townDao.addBulletin(new TownBulletin(character.getName() + " died of starvation.", new Date(), character.getTown()));
					increased = true;
					break;
				}
			}
		}
		if(!increased) {
			addStatus(new CharacterStatus(findStatusByName("Starving"), character));
		}
		return character;
	}
	
	public Character increaseThirst(Character character) {
		//Check if we have an injury status or not
		boolean increased = character.hasStatusByName("Dead");
		if(increased == false) {
			for(CharacterStatus status : character.getStatus()) {
				String statusName = status.getStatus().getName();
				if(statusName.equals("Quenched")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Thirsty"), character));		
					increased = true;
					break;
				}else if (statusName.equals("Thirsty")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Very Thirsty"), character));	
					increased = true;
					break;
				}else if (statusName.equals("Very Thirsty")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Dehydrated"), character));	
					increased = true;
					break;
				}else if (statusName.equals("Dehydrated")) {
					removeStatus(status);
					addStatus(new CharacterStatus(findStatusByName("Dead"), character));
					townDao.addBulletin(new TownBulletin(character.getName() + " died of dehydration.", new Date(), character.getTown()));
					increased = true;
					break;
				}
			}
		}
		if(!increased) {
			addStatus(new CharacterStatus(findStatusByName("Dehydrated"), character));
		}
		return character;
	}
	
}
