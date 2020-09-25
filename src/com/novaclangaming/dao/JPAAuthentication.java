package com.novaclangaming.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.novaclangaming.model.Password;
import com.novaclangaming.model.User;
import com.novaclangaming.model.UserBulletin;
import com.novaclangaming.model.Character;

public class JPAAuthentication implements IAuthenticationDao{
	
	private IUserDao userDao = new JPAUserDao();
	private ICharacterDao charDao = new JPACharacterDao();
	
	public JPAAuthentication() {
		super();
	}
	
	public User loggedUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			User freshUser = userDao.findById(user.getId());
			return freshUser;
		}
		return user;	
	}
	
	public Character activeCharacter(HttpServletRequest request) {
		Character character = (Character) request.getSession().getAttribute("character");
		Character result = null;
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		if(character != null) {
			result = charDao.findById(character.getCharId());
			if(result.hasStatusByName("Dead")) {
				request.getSession().setAttribute("character", null); //refreshes the session variable to match db
				return null;
			}else {
				request.getSession().setAttribute("character", result); //refreshes the session variable to match db
			}
		}
		
		em.close();
		return result;
	}
	
	public Character changeCharacter(HttpServletRequest request, Character character) {
		character = charDao.update(character);
		request.getSession().setAttribute("character", character);
		return character;
	}
	
	public void register(User user) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		UserBulletin bulletin = new UserBulletin("Account has been created. Welcome to Deadfall", new Date(), user);
		em.persist(bulletin);
		em.getTransaction().commit();
		em.close();
	}

	public Optional<User> authenticate(String username, String password) {
		Optional<User> user = userDao.findByName(username);
		if(user.isPresent() && user.get().getPassword().equals(hashPassword(password, toByteArray(user.get().getSalt())).getHashedPass())) {
			return user;
		}
		else {
			return Optional.empty();
		}
	}

	public Password hashPassword(String password) {
		MessageDigest md;
		Password output;
		try {
			md = MessageDigest.getInstance("SHA-256");
			
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			
			md.update(salt);
			
			byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedPassword) {
				sb.append(String.format("%02x", b));
			}
			
			output = new Password(sb.toString(), Arrays.toString(salt));
			return output;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Password hashPassword(String password, byte[] salt) {
		MessageDigest md;
		Password output;
		try {
			md = MessageDigest.getInstance("SHA-256");
			
			md.update(salt);
			
			byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedPassword) {
				sb.append(String.format("%02x", b));
			}
			
			output = new Password(sb.toString(), Arrays.toString(salt));
			return output;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static byte[] toByteArray(String salt) {
		String brokenSalt = salt.replace('[', 'A');
		brokenSalt = brokenSalt.replace(']', 'A');
		brokenSalt = brokenSalt.replaceAll("[A ]", "");
		
		String[] bytes = brokenSalt.split(",");
		byte[] newBytes = new byte[bytes.length];
		
		for (int i = 0; i < bytes.length; i++) {
			newBytes[i] = (byte) Integer.parseInt(bytes[i]);
		}
		return newBytes;
	}
	
}
