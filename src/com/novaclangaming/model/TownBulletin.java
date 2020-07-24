package com.novaclangaming.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DF_TOWN_BULLETINS")
public class TownBulletin {

//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "town_bul_seq")
//	@SequenceGenerator(name = "town_bul_seq", sequenceName = "DF_TOWN_BULLETIN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="BULLETIN_ID")
	private int bulletinId;
	
	@Column
	private String content;
	
	@Column(name="posted_time")
	private Date postedTime;
	
	@ManyToOne
	@JoinColumn(name = "TOWN_ID", referencedColumnName = "TOWN_ID")
	private Town town;

	public TownBulletin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TownBulletin(String content, Date postedTime) {
		super();
		this.content = content;
		this.postedTime = postedTime;
	}
	
	public TownBulletin(String content, Date postedTime, Town town) {
		super();
		this.content = content;
		this.postedTime = postedTime;
		this.town = town;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(Date postedTime) {
		this.postedTime = postedTime;
	}

	public int getBulletinId() {
		return bulletinId;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
	
}
