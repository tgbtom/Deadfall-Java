package com.novaclangaming.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownBulletin;

public class JPATownDao implements ITownDao{

	public void create(Town town) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(town);
		em.getTransaction().commit();
		em.close();
		
		//Create 2 tables for town_map and town_map_items
		Connection conn = JDBCConnection.openConnection();
		String query = "CREATE TABLE df_town_" + town.getTownId() + "(" + 
				"zone_id NUMBER NOT NULL PRIMARY KEY," + 
				"x NUMBER," + 
				"y NUMBER," + 
				"lootability NUMBER DEFAULT 10," + 
				"zeds NUMBER DEFAULT 0," + 
				"special_zone VARCHAR2(50) DEFAULT NULL" + 
				");";
		
		String query2 = "CREATE TABLE df_town_items_" + town.getTownId() + "(" + 
				"zone_id NUMBER," + 
				"item_id NUMBER,"
				+ "PRIMARY KEY(zone_id)" +
				");";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			PreparedStatement ps2 = conn.prepareStatement(query2);
			ps.execute();
			ps2.execute();
			ps.close();
			ps2.close();
		} catch (SQLException e) { e.printStackTrace();}
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
		} catch (NoResultException e) {}
		return Optional.ofNullable(result);
	}

	public void addBulletin(TownBulletin tb) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(tb);
		em.getTransaction().commit();
		em.close();
	}

}
