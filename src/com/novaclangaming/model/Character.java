package com.novaclangaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Character.findByUser", query = "SELECT c FROM character c WHERE user_id = :user")
})
@Entity(name = "character")
@Table(name = "df_characters")
public class Character {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "char_seq_gen")
	@SequenceGenerator(name = "char_seq_gen", sequenceName = "df_char_id_seq", allocationSize = 1)
	@Id
	@Column(name = "char_id")
	private int charId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "town_id", referencedColumnName = "town_id")
	private Town town;
	
	@Column
	private String name;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CharacterClass classification;

	public Character() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Character(User user, Town town, String name, CharacterClass classification) {
		super();
		this.user = user;
		this.town = town;
		this.name = name;
		this.classification = classification;
	}
	
	public Character(User user, String name, CharacterClass classification) {
		super();
		this.user = user;
		this.name = name;
		this.classification = classification;
	}

	public int getCharId() {
		return charId;
	}

	public void setCharId(int charId) {
		this.charId = charId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharacterClass getClassification() {
		return classification;
	}

	public void setClassification(CharacterClass classification) {
		this.classification = classification;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
	
}
