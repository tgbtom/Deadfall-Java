package com.novaclangaming.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DF_STATUS")
public class Status {

	@Id
	@Column(name="STATUS_ID")
	private int statusId;
	
	@Column(name="STATUS_NAME")
	private String name;
	
	@OneToMany(mappedBy = "status")
	private List<CharacterStatus> characters;
	
	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Status(int statusId, String name) {
		super();
		this.statusId = statusId;
		this.name = name;
	}

	public int getStatusId() {
		return statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
