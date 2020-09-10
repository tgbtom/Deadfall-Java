package com.novaclangaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="DF_WEAPONS")
public class Weapon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne
	@JoinColumn(name="ITEM_ID", referencedColumnName = "ITEM_ID")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="AMMO_ID", referencedColumnName = "ITEM_ID")
	private Item ammo;
	
	@Column(name="MIN_KILLS")
	private int minKills;
	
	@Column(name="MAX_KILLS")
	private int maxKills;
	
	@Column(name="CHANCE_OF_INJURY")
	private int chanceOfInjury;
	
	@Column(name="CHANCE_OF_BREAK")
	private int chanceOfBreak;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ON_BREAK", referencedColumnName = "ITEM_ID")
	private Item itemOnBreak;
	
	@Column(name="AP_COST")
	private int apCost;
	
	@Column(name="CHANCE_OUTPUT")
	private int chanceOutput;
	
	@ManyToOne
	@JoinColumn(name="AMMO_OUTPUT", referencedColumnName = "ITEM_ID")
	private Item ammoOutput;

	public Weapon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Weapon(Item item, Item ammo, int minKills, int maxKills, int chanceOfInjury, int chanceOfBreak,
			Item itemOnBreak, int apCost, int chanceOutput, Item ammoOutput) {
		super();
		this.item = item;
		this.ammo = ammo;
		this.minKills = minKills;
		this.maxKills = maxKills;
		this.chanceOfInjury = chanceOfInjury;
		this.chanceOfBreak = chanceOfBreak;
		this.itemOnBreak = itemOnBreak;
		this.apCost = apCost;
		this.chanceOutput = chanceOutput;
		this.ammoOutput = ammoOutput;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getAmmo() {
		return ammo;
	}

	public void setAmmo(Item ammo) {
		this.ammo = ammo;
	}

	public int getMinKills() {
		return minKills;
	}

	public void setMinKills(int minKills) {
		this.minKills = minKills;
	}

	public int getMaxKills() {
		return maxKills;
	}

	public void setMaxKills(int maxKills) {
		this.maxKills = maxKills;
	}

	public int getChanceOfInjury() {
		return chanceOfInjury;
	}

	public void setChanceOfInjury(int chanceOfInjury) {
		this.chanceOfInjury = chanceOfInjury;
	}

	public int getChanceOfBreak() {
		return chanceOfBreak;
	}

	public void setChanceOfBreak(int chanceOfBreak) {
		this.chanceOfBreak = chanceOfBreak;
	}

	public Item getItemOnBreak() {
		return itemOnBreak;
	}

	public void setItemOnBreak(Item itemOnBreak) {
		this.itemOnBreak = itemOnBreak;
	}

	public int getApCost() {
		return apCost;
	}

	public void setApCost(int apCost) {
		this.apCost = apCost;
	}

	public int getChanceOutput() {
		return chanceOutput;
	}

	public void setChanceOutput(int chanceOutput) {
		this.chanceOutput = chanceOutput;
	}

	public Item getAmmoOutput() {
		return ammoOutput;
	}

	public void setAmmoOutput(Item ammoOutput) {
		this.ammoOutput = ammoOutput;
	}
	
	
	
}
