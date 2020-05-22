package com.novaclangaming.dao;

import java.util.Optional;

import com.novaclangaming.model.ItemStackZone;

public interface IItemStackZoneDao {
	
	public ItemStackZone findById(int id);
	public Optional<ItemStackZone> findByZoneItem(int zoneId, int itemId);
	
}
