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
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name="characterStatus.FindByCharacter", query="SELECT cs FROM CharacterStatus cs WHERE character = :character")
})
@Entity
@Table(name="DF_CHARACTERS_STATUS")
public class CharacterStatus {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int id;
	
	@ManyToOne
	@JoinColumn(name="STATUS_ID", referencedColumnName = "STATUS_ID")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="CHAR_ID", referencedColumnName = "CHAR_ID")
	private Character character;

	public CharacterStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CharacterStatus(Status status, Character character) {
		super();
		this.status = status;
		this.character = character;
	}

	public int getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	
}
