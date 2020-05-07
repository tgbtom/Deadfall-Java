package com.novaclangaming.dao;

import com.novaclangaming.model.Item;

public interface IItemDao {
	public Item findById(int id);
	public Item findByName(String name);
}
