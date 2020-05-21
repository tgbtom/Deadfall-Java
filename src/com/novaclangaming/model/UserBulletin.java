package com.novaclangaming.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DF_USER_BULLETINS")
public class UserBulletin {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_bull_seq")
	@SequenceGenerator(name = "user_bull_seq", sequenceName = "df_user_bulletin_seq", allocationSize = 1)
	@Id
	@Column(name="BULLETIN_ID")
	private int bulletinId;
	
	@Column
	private String content;
	
	@Column(name="POSTED_TIME")
	private Date postedTime;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User user;

	public UserBulletin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBulletin(String content, Date postedTime, User user) {
		super();
		this.content = content;
		this.postedTime = postedTime;
		this.user = user;
	}

	public UserBulletin(String content, Date postedTime) {
		super();
		this.content = content;
		this.postedTime = postedTime;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
