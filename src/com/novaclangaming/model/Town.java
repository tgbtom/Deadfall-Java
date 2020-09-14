package com.novaclangaming.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Town.findByName", query = "SELECT t FROM town t WHERE t.name = :name"),
	@NamedQuery(name = "Town.findOpen", query = "SELECT t FROM town t WHERE t.status LIKE 'New'")
})
@Entity(name = "town")
@Table(name = "df_towns")
public class Town {
	
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "town_seq")
//	@SequenceGenerator(name = "town_seq", sequenceName = "df_town_id_seq", allocationSize = 1)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "town_id")
	private int townId;
	
	@Column
	private String name;
	
	@Column(name = "max_chars")
	private int townSize;
	
	@Column(name = "horde_size")
	private int hordeSize;
	
	@Column
	private int defence;
	
	@Column(name = "map_size")
	private int mapSize;
	
	@Column(name = "game_mode")
	private String gameMode;
	
	@Column(name = "day_number")
	private int dayNumber;
	
	@OneToMany(mappedBy = "town")
	private List<Character> characters;

	@Column
	@Enumerated(EnumType.STRING)
	private TownStatus status;

	@OneToMany(mappedBy = "town")
	private List<TownBulletin> bulletins;
	
	@OneToMany(mappedBy = "town")
	private List<Zone> zones;
	
	@OneToMany(mappedBy = "town")
	private List<StructureProgress> structuresInProgress;
	
	public Town() {
		super();
		this.zones = new ArrayList<Zone>();
	}

	public Town(String name, int townSize, int hordeSize, int defence, int mapSize, String gameMode, TownStatus status) {
		this();
		this.name = name;
		this.townSize = townSize;
		this.hordeSize = hordeSize;
		this.defence = defence;
		this.mapSize = mapSize;
		this.gameMode = gameMode;
		this.status = status;
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

	public int getTownSize() {
		return townSize;
	}

	public void setTownSize(int townSize) {
		this.townSize = townSize;
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

	public int getMapSize() {
		return mapSize;
	}

	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public TownStatus getStatus() {
		return status;
	}

	public void setStatus(TownStatus status) {
		this.status = status;
	}

	public List<TownBulletin> getBulletins() {
		return bulletins;
	}
	
	public void addBulletin(TownBulletin bulletin) {
		this.bulletins.add(bulletin);
	}
	
	public List<TownBulletin> getOrderedBulletins(){
		this.bulletins.sort(new Comparator<TownBulletin>() {
			public int compare(TownBulletin o1, TownBulletin o2) {
				return o2.getBulletinId() - o1.getBulletinId();
			}
		});
		return this.bulletins;
	}
	
	public List<Character> getOrderedCharacters(){
		this.characters.sort(new Comparator<Character>() {
			public int compare(Character o1, Character o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return this.characters;
	}
	
	public List<Character> getReverseOrderedCharacters(){
		this.characters.sort(new Comparator<Character>() {
			public int compare(Character o1, Character o2) {
				return o2.getName().compareTo(o1.getName());
			}
		});
		return this.characters;
	}

	public void addZone(Zone zone) {
		this.zones.add(zone);
	}

	public List<StructureProgress> getStructuresInProgress() {
		return structuresInProgress;
	}

	public void setStructuresInProgress(List<StructureProgress> structuresInProgress) {
		this.structuresInProgress = structuresInProgress;
	}
	
	public void addDefence(int amount) {
		this.defence += amount;
	}
	
}
