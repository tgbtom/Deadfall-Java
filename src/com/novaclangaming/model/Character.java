package com.novaclangaming.model;

import java.awt.Cursor;

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
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Character.findByUser", query = "SELECT c FROM character c WHERE user_id = :user"),
	@NamedQuery(name = "Character.findByName", query = "SELECT c FROM character c WHERE user_id = :userId AND name = :charName")
})
@Entity(name = "character")
@Table(name = "df_characters")
@SecondaryTables({
	@SecondaryTable(name = "df_characters_stats"),
	@SecondaryTable(name = "df_characters_legacy")
})
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
	
	@Column(name = "construction_contributions", table = "df_characters_stats")
	private int curConstructionCont;
	
	@Column(name = "zeds_killed", table = "df_characters_stats")
	private int curZedsKilled;
	
	@Column(name = "distance_travelled", table = "df_characters_stats")
	private int curDistanceTravelled;
	
	@Column(name = "times_looted", table = "df_characters_stats")
	private int curTimesLooted;
	
	@Column(name = "successful_camps", table = "df_characters_stats")
	private int curCamps;
	
	@Column(name = "day_of_death", table = "df_characters_stats")
	private int dayOfDeath;
	
	@Column(name = "construction_contributions", table = "df_characters_legacy")
	private int lifetimeConstructionCont;
	
	@Column(name = "zeds_killed", table = "df_characters_legacy")
	private int lifetimeZedsKilled;

	@Column(name = "distance_travelled", table = "df_characters_legacy")
	private int lifetimeDistanceTravelled;
	
	@Column(name = "times_looted", table = "df_characters_legacy")
	private int lifetimeTimesLooted;
	
	@Column(name = "successful_camps", table = "df_characters_legacy")
	private int lifetimeCamps;
	
	@Column(name = "lvl", table = "df_characters_legacy")
	private int level;
	
	@Column(name = "current_xp", table = "df_characters_legacy")
	private int curXp;
	
	@Column(table = "df_characters_legacy")
	private int deaths;
	
	
	public Character() {
		super();
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

	public int getCurConstructionCont() {
		return curConstructionCont;
	}

	public void setCurConstructionCont(int curConstructionCont) {
		this.curConstructionCont = curConstructionCont;
	}

	public int getCurZedsKilled() {
		return curZedsKilled;
	}

	public void setCurZedsKilled(int curZedsKilled) {
		this.curZedsKilled = curZedsKilled;
	}

	public int getCurDistanceTravelled() {
		return curDistanceTravelled;
	}

	public void setCurDistanceTravelled(int curDistanceTravelled) {
		this.curDistanceTravelled = curDistanceTravelled;
	}

	public int getCurTimesLooted() {
		return curTimesLooted;
	}

	public void setCurTimesLooted(int curTimesLooted) {
		this.curTimesLooted = curTimesLooted;
	}

	public int getCurCamps() {
		return curCamps;
	}

	public void setCurCamps(int curCamps) {
		this.curCamps = curCamps;
	}

	public int getLifetimeConstructionCont() {
		return lifetimeConstructionCont;
	}

	public void setLifetimeConstructionCont(int lifetimeConstructionCont) {
		this.lifetimeConstructionCont = lifetimeConstructionCont;
	}

	public int getLifetimeZedsKilled() {
		return lifetimeZedsKilled;
	}

	public void setLifetimeZedsKilled(int lifetimeZedsKilled) {
		this.lifetimeZedsKilled = lifetimeZedsKilled;
	}

	public int getLifetimeDistanceTravelled() {
		return lifetimeDistanceTravelled;
	}

	public void setLifetimeDistanceTravelled(int lifetimeDistanceTravelled) {
		this.lifetimeDistanceTravelled = lifetimeDistanceTravelled;
	}

	public int getLifetimeTimesLooted() {
		return lifetimeTimesLooted;
	}

	public void setLifetimeTimesLooted(int lifetimeTimesLooted) {
		this.lifetimeTimesLooted = lifetimeTimesLooted;
	}

	public int getLifetimeCamps() {
		return lifetimeCamps;
	}

	public void setLifetimeCamps(int lifetimeCamps) {
		this.lifetimeCamps = lifetimeCamps;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurXp() {
		return curXp;
	}

	public void setCurXp(int curXp) {
		this.curXp = curXp;
	}

	public int getDayOfDeath() {
		return dayOfDeath;
	}

	public void setDayOfDeath(int dayOfDeath) {
		this.dayOfDeath = dayOfDeath;
	}
	
	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getTotalKills() {
		return curZedsKilled + lifetimeZedsKilled;
	}
	
	public int getTotalStructureCont() {
		return curConstructionCont + lifetimeConstructionCont;
	}
	
	public int getTotalDistanceTravelled() {
		return curDistanceTravelled + lifetimeDistanceTravelled;
	}
	
	public int getTotalLoots() {
		return curTimesLooted + lifetimeTimesLooted;
	}
	
	public int getTotalCamps() {
		return curCamps + lifetimeCamps;
	}
}