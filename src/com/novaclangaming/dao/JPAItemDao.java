package com.novaclangaming.dao;

import java.util.ArrayList;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemRarity;
import com.novaclangaming.model.Weapon;

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

	public Weapon findWeaponById(Item item) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		Weapon weapon = em.find(Weapon.class, item);
		em.close();
		return weapon;
	}
	
	public ArrayList<Item> findByRarity(ItemRarity rarity){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Item> query = em.createNamedQuery("Item.findByRarity", Item.class);
		query.setParameter("rarity", rarity);
		ArrayList<Item> results = (ArrayList<Item>) query.getResultList();
		em.close();
		return results;
	}
	
	public Item getRandom(ItemRarity rarity) {
		ArrayList<Item> items = findByRarity(rarity);
		int randomIndex = new Random().nextInt(items.size());
		Item result = items.get(randomIndex);
		return result;
	}
	
}
