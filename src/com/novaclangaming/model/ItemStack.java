package com.novaclangaming.model;

public interface ItemStack {
	
	public Item getItem();
	public void setItem(Item item);
	public int getQuantity();
	public void setQuantity(int qty);
	public int addToStack(int qtyToAdd);
	public int removeFromStack(int qtyToRemove);
	
}
