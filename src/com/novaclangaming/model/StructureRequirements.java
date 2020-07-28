package com.novaclangaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DF_STRUCTURE_REQUIREMENTS")
public class StructureRequirements {

	@Id @GeneratedValue
	 long id;
	
	@ManyToOne
	@JoinColumn(name="STRUCTURE_ID", referencedColumnName = "STRUCTURE_ID")
	private Structure structure;
	
	@ManyToOne
	@JoinColumn(name="REQUIRED_ID", referencedColumnName = "STRUCTURE_ID")
	private Structure requiredStructure;
	
	@Column(name="REQUIRED_LEVEL")
	private int requiredLevel;

	public StructureRequirements() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureRequirements(long id, Structure structure, Structure requiredStructure, int requiredLevel) {
		super();
		this.id = id;
		this.structure = structure;
		this.requiredStructure = requiredStructure;
		this.requiredLevel = requiredLevel;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Structure getRequiredStructure() {
		return requiredStructure;
	}

	public void setRequiredStructure(Structure requiredStructure) {
		this.requiredStructure = requiredStructure;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public long getId() {
		return id;
	}
	
}
