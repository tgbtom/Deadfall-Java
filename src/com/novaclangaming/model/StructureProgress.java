package com.novaclangaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="StructureProgress.findByTownStructure", query="SELECT s FROM StructureProgress s WHERE s.town = :town AND s.structure = :structure")
})
@Entity
@Table(name="DF_TOWNS_STRUCTURES")
public class StructureProgress{

	@Id @GeneratedValue
	 long id;
	
	@ManyToOne
	@JoinColumn(name="TOWN_ID", referencedColumnName = "TOWN_ID")
	private Town town;
	
	@ManyToOne
	@JoinColumn(name="STRUCTURE_ID", referencedColumnName = "STRUCTURE_ID")
	private Structure structure;
	
	@Column(name="CURRENT_LEVEL")
	private int level;
	
	@Column(name="CURRENT_AP")
	private int ap;

	public StructureProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureProgress(Town town, Structure structure, int level, int ap) {
		super();
		this.town = town;
		this.structure = structure;
		this.level = level;
		this.ap = ap;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public long getId() {
		return id;
	}
	
}
