package com.novaclangaming.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Character;

public class JPACharacterDao implements ICharacterDao{

	public void create(Character character) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(character);
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
		// TODO Auto-generated method stub
		
	}

}
