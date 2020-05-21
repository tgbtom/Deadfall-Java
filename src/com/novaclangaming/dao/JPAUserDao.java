package com.novaclangaming.dao;

import com.novaclangaming.model.User;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JPAUserDao implements IUserDao{

	public JPAUserDao() {
		super();
	}

	public Optional<User> findByName(String username) {
		User user = null;
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<User> query = em.createNamedQuery("User.findByName", User.class);
		query.setParameter("name", username);
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {}
		return Optional.ofNullable(user);
	}

	public User findById(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		em.close();
		return user;
	}

	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
