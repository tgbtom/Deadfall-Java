package com.novaclangaming.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Item;

public class JPAItemDao implements IItemDao {

	public Item findById(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Item item = em.find(Item.class, id);
		em.close();
		return item;
	}

	public Item findByName(String name) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Item> query = em.createNamedQuery("Item.findByName", Item.class);
		query.setParameter("name", name);
		Item item = query.getSingleResult();
		em.close();
		return item;
		}

}
