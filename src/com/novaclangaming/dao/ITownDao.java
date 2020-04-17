package com.novaclangaming.dao;

import java.util.List;
import java.util.Optional;

import com.novaclangaming.model.Town;

public interface ITownDao {

	public void create(Town town);
	public Town findById(int townId);
	public Optional<Town> findByName(String townName);
	public List<Town> findAllOpenTowns();
	public Town update(Town town);
	public void delete(Town town);
	
}
