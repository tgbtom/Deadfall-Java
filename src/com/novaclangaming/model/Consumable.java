package com.novaclangaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="DF_CONSUMABLES")
public class Consumable implements Serializable{

	private static final long serialVersionUID = 2L;
	
	@Id
	@OneToOne
	@JoinColumn(name="ITEM_ID", referencedColumnName = "ITEM_ID")
	private Item item;
	
	@Column(name="AP_GAIN")
	private int apGain;
	
	@Column(name="CONSUME_TYPE")
	private String consumeType;
	
	@Column(name="HEALTH_GAIN")
	private int healthGain;

	public Consumable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Consumable(Item item, int apGain, String consumeType, int healthGain) {
		super();
		this.item = item;
		this.apGain = apGain;
		this.consumeType = consumeType;
		this.healthGain = healthGain;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getApGain() {
		return apGain;
	}

	public void setApGain(int apGain) {
		this.apGain = apGain;
	}

	public String getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}

	public int getHealthGain() {
		return healthGain;
	}

	public void setHealthGain(int healthGain) {
		this.healthGain = healthGain;
	}
	
}
