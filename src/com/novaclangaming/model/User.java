package com.novaclangaming.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "User.findByName", query = "SELECT u FROM user u WHERE u.username = :name")
})
@Entity(name = "user")
@Table(name = "df_users")
public class User {
	
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "df_user_seq")
//	@SequenceGenerator(name = "df_user_seq", sequenceName = "df_user_id_seq", allocationSize = 1)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String salt;
	
	@Column
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<Character> characters;
	
	@OneToMany(mappedBy = "user")
	private List<UserBulletin> bulletins;

	public User() {
		super();
	}

	public User(int id, String username, String password, String salt, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.email = email;
	}
	
	public User(String username, String password, String salt, String email) {
		super();
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public List<UserBulletin> getUserBulletins() {
		return bulletins;
	}
	
	public List<UserBulletin> getOrderedUserBulletins(){
		Collections.sort(this.bulletins, new Comparator<UserBulletin>() {
			public int compare(UserBulletin o1, UserBulletin o2) {
				return o2.getPostedTime().compareTo(o1.getPostedTime());
			}
		});
		return this.bulletins;
	}
	
	public void addUserBulletin(UserBulletin bulletin) {
		if(this.bulletins == null || this.bulletins.isEmpty()) {
			this.bulletins = new ArrayList<UserBulletin>();
		}
		this.bulletins.add(bulletin);
	}
	
}
