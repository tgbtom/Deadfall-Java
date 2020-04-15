package com.novaclangaming.dao;

import java.util.Optional;

import com.novaclangaming.model.User;

public interface IUserDao {
	public void create(User user);
	public User findById(int id);
	public Optional<User> findByName(String username);
	public User update(User user);
}
