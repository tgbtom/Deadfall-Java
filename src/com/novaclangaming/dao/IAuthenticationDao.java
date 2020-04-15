package com.novaclangaming.dao;

import java.util.Optional;

import com.novaclangaming.model.Password;
import com.novaclangaming.model.User;

public interface IAuthenticationDao {
	public void register(User user);
	public Optional<User> authenticate(String username, String password);
	public Password hashPassword(String password);
	public Password hashPassword(String password, byte[] salt);
}
