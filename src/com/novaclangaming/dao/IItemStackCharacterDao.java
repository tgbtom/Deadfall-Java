package com.novaclangaming.dao;

import java.util.Optional;

import com.novaclangaming.model.ItemStackCharacter;

public interface IItemStackCharacterDao {

	public ItemStackCharacter findById(int id);
	public Optional<ItemStackCharacter> findByCharItem(int charId, int itemId);
	
}
