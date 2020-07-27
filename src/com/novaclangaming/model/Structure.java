package com.novaclangaming.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "Structure.findAll", query = "SELECT s FROM Structure s"),
		@NamedQuery(name = "Structure.findAllDefence", query = "SELECT s FROM Structure s WHERE s.Category LIKE 'Defence'"),
		@NamedQuery(name = "Structure.findAllSupply", query = "SELECT s FROM Structure s WHERE s.Category LIKE 'Supply'"),
		@NamedQuery(name = "Structure.findAllProduction", query = "SELECT s FROM Structure s WHERE s.Category LIKE 'Production'")
		})
@Entity
@Table(name="DF_STRUCTURES")
public class Structure {

	@Id
	@Column(name="STRUCTURE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int structureId;
	
	@Column
	private String name;
	
	@Column
	private String Category;
	
	@Column
	private String description;
	
	@Column
	private int defence;
	
	@Column(name="AP_COST")
	private int apCost;
	
	@Column
	private int levels;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "structure")
	private List<StructureCost> costs;
	
	@OneToMany
	private List<StructureRequirements> requirements;

	public Structure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Structure(String name, String category, String description, int defence, int apCost,
			int levels, List<StructureCost> costs, List<StructureRequirements> requirements) {
		super();
		this.name = name;
		Category = category;
		this.description = description;
		this.defence = defence;
		this.apCost = apCost;
		this.levels = levels;
		this.costs = costs;
		this.requirements = requirements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getApCost() {
		return apCost;
	}

	public void setApCost(int apCost) {
		this.apCost = apCost;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public List<StructureCost> getCosts() {
		return costs;
	}

	public void setCosts(List<StructureCost> costs) {
		this.costs = costs;
	}

	public List<StructureRequirements> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<StructureRequirements> requirements) {
		this.requirements = requirements;
	}

	public int getStructureId() {
		return structureId;
	}
	
}
