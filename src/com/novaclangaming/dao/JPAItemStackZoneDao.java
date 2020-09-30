package com.novaclangaming.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.ItemStackZone;

public class JPAItemStackZoneDao implements IItemStackZoneDao{
	
	private JPATownDao townDao;
	private JPAItemDao itemDao;
	
	public JPAItemStackZoneDao(JPATownDao townDao) {
		super();
		this.townDao = townDao;
		this.itemDao = new JPAItemDao();
	}

	public ItemStackZone findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<ItemStackZone> findByZoneItem(int zoneId, int itemId) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<ItemStackZone> query = em.createNamedQuery("ItemStackZone.findByZoneItem", ItemStackZone.class);
		query.setParameter("zone", townDao.findZoneById(zoneId));
		query.setParameter("item", itemDao.findById(itemId));
		
		Optional<ItemStackZone> result;
		if(query.getResultList().isEmpty()) {
			result = Optional.empty();
		}
		else {
			result = Optional.ofNullable(query.getResultList().get(0));
		}
		return result;
	}

}
