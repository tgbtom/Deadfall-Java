package com.novaclangaming.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.Structure;

public class JPAStructureDao {

	public Structure findById(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		Structure result = em.find(Structure.class, id);
		em.close();
		return result;
	}
	
	public static List<Structure> findAll() {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAll", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findAllDefence(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllDefence", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findAllSupply(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllSupply", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findAllProduction(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllProduction", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
}
