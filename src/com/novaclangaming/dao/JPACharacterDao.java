package com.novaclangaming.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

}
