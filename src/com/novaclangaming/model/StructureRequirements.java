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
	
}
