package com.novaclangaming.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "town")
@Table(name = "df_towns")
public class Town {
	
	@Id
	@Column(name = "town_id")
	private int townId;
	
	@Column
	private String name;
	
	@Column(name = "max_chars")
	private int maxSize;
	
	@Column(name = "horde_size")
	private int hordeSize;
	
	@Column
	private int defence;
	
	@Column(name = "day_number")
	private int dayNumber;
	
	@OneToMany(mappedBy = "town")
	private List<Character> characters;

	public Town() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTownId() {
		return townId;
	}

	public void setTownId(int townId) {
		this.townId = townId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getHordeSize() {
		return hordeSize;
	}

	public void setHordeSize(int hordeSize) {
		this.hordeSize = hordeSize;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
	
}
