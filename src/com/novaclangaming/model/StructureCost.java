package com.novaclangaming.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DF_STRUCTURE_COSTS")
public class StructureCost {

	@Id @GeneratedValue
	 long id;
	
	@ManyToOne
	@JoinColumn(name = "STRUCTURE_ID", referencedColumnName = "STRUCTURE_ID")
	private Structure structure;

	@ManyToOne
	@JoinColumn(name="ITEM_ID", referencedColumnName = "ITEM_ID")
	private Item item;
	
	@Column(name="item_quantity")
	private int quantity;

	public StructureCost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureCost(long id, Structure structure, Item item, int quantity) {
		super();
		this.id = id;
		this.structure = structure;
		this.item = item;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
