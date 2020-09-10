package com.novaclangaming.dao;

import java.util.List;
import java.util.Optional;

import com.novaclangaming.model.Character;
import com.novaclangaming.model.CharacterStatus;
import com.novaclangaming.model.Item;
import com.novaclangaming.model.Status;

public interface ICharacterDao {
	public void create(Character character);
	public Character findById(int id);
	public List<Character> findByUserId(int id);
	public List<Character> findByTownId(int id);
	public Optional<Character> findByCharName(int user_id, String charName);
	public Character update(Character character);
	public void delete(Character character);
	public void addItem(int characterId, Item item, int qty);
	public Status findStatusById(int statusId);
	public void addStatus(CharacterStatus charStatus);
	public CharacterStatus removeStatus(CharacterStatus charStatus);
	public Character clearStatus(Character character);
}
