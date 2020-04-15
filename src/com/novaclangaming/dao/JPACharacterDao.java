package com.novaclangaming.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Character;

public class JPACharacterDao implements ICharacterDao{

	@Override
	public void create(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(character);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Character findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Character> findByUserId(int id) {
		IUserDao userDao = new JPAUserDao();
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Character> query = em.createNamedQuery("Character.findByUser", Character.class);
		query.setParameter("user", id);
		List<Character> characters = query.getResultList();
		return characters;
	}

	@Override
	public List<Character> findByTownId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character update(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Character managedChar = em.merge(character);
		em.getTransaction().commit();
		em.close();
		return managedChar;
	}

	@Override
	public void delete(Character character) {
		// TODO Auto-generated method stub
		
	}

}
