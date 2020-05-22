package com.novaclangaming.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.ItemStackCharacter;

public class JPAItemStackCharacterDao implements IItemStackCharacterDao{

	private ICharacterDao charDao;
	private IItemDao itemDao;
	
	public JPAItemStackCharacterDao(JPACharacterDao charDao) {
		super();
		this.charDao = charDao;
		itemDao = new JPAItemDao();
	}

	public ItemStackCharacter findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<ItemStackCharacter> findByCharItem(int charId, int itemId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<ItemStackCharacter> query = em.createNamedQuery("ItemStackCharacter.findByCharItem", ItemStackCharacter.class);
		query.setParameter("character", charDao.findById(charId));
		query.setParameter("item", itemDao.findById(itemId));
		
		Optional<ItemStackCharacter> result;
		if(query.getResultList().isEmpty()) {
			result = Optional.empty();
		}
		else {
			result = Optional.ofNullable(query.getResultList().get(0));
		}
		em.close();
		return result;
	}

}
