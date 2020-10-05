package com.novaclangaming.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@NamedQueries({
		@NamedQuery(name="Zone.findStorageByTownId", query="SELECT z FROM Zone z WHERE z.town = :town AND z.x LIKE '0' AND z.y LIKE '0'"),
		@NamedQuery(name="Zone.findZoneByCoords", query="SELECT z FROM Zone z WHERE z.town = :town AND z.x LIKE :x AND z.y LIKE :y")
})
@Entity
@Table(name="DF_TOWN_ZONES")
public class Zone {

//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_seq")
//	@SequenceGenerator(name = "zone_seq", sequenceName="DF_ZONE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="ZONE_ID")
	private int zoneId;
	
	@Column
	private int x;
	
	@Column
	private int y;
	
	@Column
	private int lootability;
	
	@Column
	private int zeds;
	
	@Column
	private int danger;
	
	@Column(name="SPECIAL_ZONE")
	private String specialZone;
	
	@OneToMany(mappedBy = "zone")
	private List<ItemStackZone> itemStacks;
	
	@OneToMany(mappedBy = "zone")
	private List<Character> characters;
	
	@ManyToOne
	@JoinColumn(name="TOWN_ID", referencedColumnName = "TOWN_ID")
	private Town town;
	
	@OneToMany(mappedBy = "zone")
	private List<ZoneBulletin> bulletins;

	public Zone() {
		super();
	}

	public Zone(int x, int y, int lootability, int zeds, String specialZone, int danger) {
		this();
		this.x = x;
		this.y = y;
		this.lootability = lootability;
		this.zeds = zeds;
		this.specialZone = specialZone;
		this.danger = danger;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLootability() {
		return lootability;
	}

	public void setLootability(int lootability) {
		this.lootability = lootability;
	}

	public int getZeds() {
		return zeds;
	}

	public Zone setZeds(int zeds) {
		this.zeds = zeds;
		return this;
	}

	public String getSpecialZone() {
		return specialZone;
	}

	public void setSpecialZone(String specialZone) {
		this.specialZone = specialZone;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
	
	public ItemStackZone addItem(Item item, int qty) {
		if(this.itemStacks == null || this.itemStacks.isEmpty()) {
			this.itemStacks = new ArrayList<ItemStackZone>();
		}
		ItemStackZone result;
		for(int i = 0; i < itemStacks.size(); i++) {
			if(itemStacks.get(i).getItem().getItemId() == item.getItemId()) {
				itemStacks.get(i).addToStack(qty);
				result = itemStacks.get(i);
				return result;
			}
		}
	
		result = new ItemStackZone(item, qty, this);
		itemStacks.add(result);
		return result;
	}
	
	public ItemStackZone removeItem(Item item, int qty) {
		if(this.itemStacks == null || this.itemStacks.isEmpty()) {
			return null;
		}
		for(int i =0; i < itemStacks.size(); i++){
			ItemStackZone current = itemStacks.get(i);
			if(current.getItem().getItemId() == item.getItemId()) {
				if(current.getQuantity() >= qty) {
					current.removeFromStack(qty);
					return current;
				}
				else {
					return null;
				}
			}
		}
		return null;
	}

	public List<ItemStackZone> getItemStacks() {
		return itemStacks;
	}
	
	public int numOfItemHere(int itemId) {
		for(ItemStackZone stack : itemStacks) {
			if(stack.getItem().getItemId() == itemId) {
				return stack.getQuantity();
			}
		}
		return 0;
	}
	
	public ItemStackZone findStackHere(int itemId) {
		for(ItemStackZone stack : itemStacks) {
			if(stack.getItem().getItemId() == itemId) {
				return stack;
			}
		}
		return null;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	
	public String getCharacterNames() {
		String result = "";
		for(Character character : this.characters) {
			result += result != "" ? ", " + character.getName() : character.getName();
		}
		return result;
	}

	public int getDanger() {
		return danger;
	}

	public Zone setDanger(int danger) {
		this.danger = danger;
		return this;
	}

	public List<ZoneBulletin> getBulletins() {
		return bulletins;
	}

	public void setBulletins(List<ZoneBulletin> bulletins) {
		this.bulletins = bulletins;
	}
	
	public List<ZoneBulletin> getOrderedBulletins(){
		this.bulletins.sort(new Comparator<ZoneBulletin>() {
			public int compare(ZoneBulletin o1, ZoneBulletin o2) {
				return o2.getBulletinId() - o1.getBulletinId();
			}
		});
		return this.bulletins;
	}
	
}
