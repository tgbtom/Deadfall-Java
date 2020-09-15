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
@Table(name="DF_TOWN_ZONE_BULLETINS")
public class ZoneBulletin {

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="BULLETIN_ID")
	private int bulletinId;
	
	@Column
	private String content;
	
	@Column(name="posted_time")
	private Date postedTime;
	
	@ManyToOne
	@JoinColumn(name = "ZONE_ID", referencedColumnName = "ZONE_ID")
	private Zone zone;

	public ZoneBulletin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZoneBulletin(String content, Date postedTime) {
		super();
		this.content = content;
		this.postedTime = postedTime;
	}
	
	public ZoneBulletin(String content, Date postedTime, Zone zone) {
		super();
		this.content = content;
		this.postedTime = postedTime;
		this.zone = zone;
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

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
}
