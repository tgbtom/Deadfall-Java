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
		@NamedQuery(name="ItemStackCharacter.findByCharItem", query="SELECT i FROM ItemStackCharacter i WHERE character = :character AND item = :item"))
@Entity
@Table(name="DF_CHARACTERS_ITEMS")
public class ItemStackCharacter implements ItemStack{

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stack_seq")
	@SequenceGenerator(name="stack_seq", sequenceName="DF_STACK_SEQ", allocationSize = 1)
	@Id
	@Column(name="STACK_ID")
	private int stackId;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
	private Item item;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "CHAR_ID", referencedColumnName = "CHAR_ID")
	private Character character;
	
	public ItemStackCharacter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItemStackCharacter(Item item, int quantity, Character character) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.character = character;
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
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
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
