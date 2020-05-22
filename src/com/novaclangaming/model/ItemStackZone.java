package com.novaclangaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(
		@NamedQuery(name="ItemStackZone.findByZoneItem", query="SELECT i FROM ItemStackZone i WHERE zone = :zone AND item = :item"))
@Entity
@Table(name = "DF_TOWN_ZONE_ITEMS")
public class ItemStackZone implements ItemStack {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="stack_seq")
	@SequenceGenerator(name="stack_seq", sequenceName="DF_STACK_SEQ", allocationSize = 1)
	@Id
	@Column(name = "STACK_ID")
	private int stackId;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
	private Item item;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "ZONE_ID", referencedColumnName = "ZONE_ID")
	private Zone zone;
	
	public ItemStackZone() {
		super();
	}

	public ItemStackZone(Item item, int quantity, Zone zone) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.zone = zone;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int qty) {
		this.quantity = qty;
	}

	public int addToStack(int qtyToAdd) {
		this.quantity += qtyToAdd;
		return this.quantity;
	}

	public int removeFromStack(int qtyToRemove) {
		this.quantity -= qtyToRemove;
		return this.quantity;
	}
	
}
