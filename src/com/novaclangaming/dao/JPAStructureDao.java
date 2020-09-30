package com.novaclangaming.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.novaclangaming.model.ItemStackZone;
import com.novaclangaming.model.Structure;
import com.novaclangaming.model.StructureCost;
import com.novaclangaming.model.StructureProgress;
import com.novaclangaming.model.StructureRequirements;
import com.novaclangaming.model.Town;
import com.novaclangaming.model.Zone;

public class JPAStructureDao {

	private Map<Integer, Boolean> affordability;
	private JPATownDao townDao;
	private JPAItemStackZoneDao stackDao;
	
	public Structure findById(int id) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		Structure result = em.find(Structure.class, id);
		em.close();
		return result;
	}
	
	public static List<Structure> findAll() {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAll", Structure.class);
		List<Structure> results = query.getResultList();
		return results;
	}
	
	public static List<Structure> findAllDefence(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllDefence", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findAllSupply(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllSupply", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findAllProduction(){
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<Structure> query = em.createNamedQuery("Structure.findAllProduction", Structure.class);
		List<Structure> results = query.getResultList();
		em.close();
		return results;
	}
	
	public static List<Structure> findUnlockedStructures(Town town){
		List<Structure> structures = getStructuresWithMetRequirements(town);
		Collections.sort(structures, new Comparator<Structure>() {
			@Override
			public int compare(Structure o1, Structure o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		return structures;
	}
	
	public static Optional<StructureProgress> findProgress(Town town, Structure structure) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		TypedQuery<StructureProgress> query = em.createNamedQuery("StructureProgress.findByTownStructure", StructureProgress.class);
		query.setParameter("town", town);
		query.setParameter("structure", structure);
		Optional<StructureProgress> result = Optional.empty();
		try {
			result = Optional.ofNullable(query.getSingleResult());
		} catch (Exception e) {}
		em.clear();
		return result;
	}
	
	public void updateProgress(StructureProgress progress) {
		EntityManager em = JPAConnection.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.merge(progress);
		em.getTransaction().commit();
		em.close();
	}
	
	private static List<Structure> getStructuresWithMetRequirements(Town town){
		List<Structure> structures = findAll();
		List<Structure> metRequires = new ArrayList<Structure>();
		for(Structure struc : structures) {
			boolean met = true;
			for(StructureRequirements req : struc.getRequirements()) {
				//if req.structure and req.level are met, add to metRequires
				if(!isStructureRequirementsMet(town, req.getRequiredStructure(), req.getRequiredLevel())) {
					met = false;
				}
			}
			if(met) {
				metRequires.add(struc);
			}
		}
		return metRequires;
	}
	
	private void checkUnlockedAffordability(int townId) {
		if(this.townDao == null) {
			this.townDao = new JPATownDao();
			this.stackDao = new JPAItemStackZoneDao(this.townDao);
		}
		List<Structure> structures = findUnlockedStructures(townDao.findById(townId));
		Zone storage = this.townDao.findStorageZone(townId);
	
		for(Structure struc: structures) {
			boolean afford = true;
			//check if affordable
			for(StructureCost cost: struc.getCosts()) {
				Optional<ItemStackZone> stack = this.stackDao.findByZoneItem(storage.getZoneId(), cost.getItem().getItemId());
				if(stack.isPresent()) {
					if(stack.get().getQuantity() < cost.getQuantity()) {
						afford = false;
					}
				}
				else {
					afford = false;
				}
			}
			this.affordability.put(struc.getStructureId(), afford);//affordable
		}
	}
	
	public boolean isStructureAffordable(int townId, int structureId) {
		if(this.affordability == null) {
			this.affordability = new HashMap<Integer, Boolean>();
			this.checkUnlockedAffordability(townId);
		}
		return this.affordability.get(structureId);
	}
	
	private static boolean isStructureRequirementsMet(Town town, Structure structure, int level) {
//		Optional<StructureProgress> progress = findProgress(town, structure);
//		if(progress.isPresent()) {
//			if(progress.get().getLevel() < level) {
//				return false;
//			}
//		}
//		else {
//			return false;
//		}
		
		StructureProgress progress = town.findProgress(structure.getStructureId());
		if (progress != null) {
			if(progress.getLevel() < level) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	/*This function is called to determine what actions occur now that a structure has been leveled up, 
	 * the level change is persisted to the database separately*/
	public void structureLevelUp(Town town, Structure structure, int newLevel) {
		//Is defence granted?
		if(this.townDao == null) {
			this.townDao = new JPATownDao();
		}
		if(structure.getDefence() > 0) {
			town.addDefence(structure.getDefence());
			townDao.update(town);
		}
	}
	
}
