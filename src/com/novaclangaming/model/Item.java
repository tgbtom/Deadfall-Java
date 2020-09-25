package com.novaclangaming.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Item.findByName", query = "SELECT i FROM item i WHERE i.name LIKE :name"),
	@NamedQuery(name="Item.findByRarity", query="SELECT i FROM item i WHERE i.rarity LIKE :rarity AND i.category NOT LIKE 'Junk'")
})
@Table(name = "df_items")
@Entity(name = "item")
public class Item {

	@Id
	@Column(name="item_id")
	private int itemId;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private int mass;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ItemRarity rarity;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ItemCategory category;
	
	@OneToOne(mappedBy = "item")
	private Weapon weapon;
	
	@OneToOne(mappedBy = "item")
	private Consumable consumable;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMass() {
		return mass;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}
	public ItemRarity getRarity() {
		return rarity;
	}
	public void setRarity(ItemRarity rarity) {
		this.rarity = rarity;
	}
	public ItemCategory getCategory() {
		return category;
	}
	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public String ajaxString() {
		return itemId + "&" + name + "&" + description + "&" + mass
				+ "&" + rarity + "&" + category;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Consumable getConsumable() {
		return consumable;
	}
	
}
