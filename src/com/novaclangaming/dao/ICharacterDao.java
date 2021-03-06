package com.novaclangaming.dao;

import java.util.List;
import java.util.Optional;

import com.novaclangaming.model.Character;

public interface ICharacterDao {
	public void create(Character character);
	public Character findById(int id);
	public List<Character> findByUserId(int id);
	public List<Character> findByTownId(int id);
	public Optional<Character> findByCharName(int user_id, String charName);
	public Character update(Character character);
	public void delete(Character character);
}
