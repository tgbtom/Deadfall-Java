package com.novaclangaming.model;

public class ItemStackCharacter implements ItemStack{

	private Item item;
	private int quantity;
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
