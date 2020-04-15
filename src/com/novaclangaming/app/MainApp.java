package com.novaclangaming.app;

import com.novaclangaming.dao.JPAUserDao;
import com.novaclangaming.model.User;

public class MainApp {
	public static void main(String[] args) {
		User user = new User("Thomas", "Baldwin", "salt salt", "thomas@gmail.com");
		System.out.println(user.toString());
		JPAUserDao jud = new JPAUserDao();
		jud.create(user);
	}
}
