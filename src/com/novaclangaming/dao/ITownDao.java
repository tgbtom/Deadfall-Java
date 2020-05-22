package com.novaclangaming.dao;

import java.util.List;
import java.util.Optional;

import com.novaclangaming.model.Item;
import com.novaclangaming.model.ItemCategory;
import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.TownBulletin;
import com.novaclangaming.model.Zone;

public interface ITownDao {

	public void create(Town town);
	public Town findById(int townId);
	public Optional<Town> findByName(String townName);
	public List<Town> findAllOpenTowns();
	public Town update(Town town);
	public void delete(Town town);
	public void addBulletin(TownBulletin tb);
	public Zone findStorageZone(int townId);
	public List<ItemStackZone> findItemsInStorage(int townId, ItemCategory category);
	public void addItemToStorage(int townId, Item item, int qty);
	
}
