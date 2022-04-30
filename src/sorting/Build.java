package sorting;
import skills.*;

import java.util.ArrayList;
import gearFamily.*;


public class Build {
	
	private Weapon buildWpn;
	private ArrayList<Armor> buildArmor;
	private Talisman buildTalisman;
	
	
	
	
	public Build (Weapon wpn, ArrayList<Armor> buildArmor, Talisman buildTalisman) {
		this.buildWpn = wpn;
		this.buildArmor = buildArmor;
		this.buildTalisman = buildTalisman;
	}
	
	//copy Constructor for deep copy
	public Build (Build b) {
		this.buildWpn = new Weapon (b.getbuildWpn().getGearName(), b.getbuildWpn().getGearSlotsCopy());
		this.buildArmor = new ArrayList<Armor>();
		for(Armor i : b.buildArmor) {
			//armor may be null, but buildArmor needs to maintain it´s 5 entries even if some are null
			if(i != null) {
				this.buildArmor.add(new Armor (i.getGearName(), i.getGearType(), i.getGearSkillsCopy(), i.getGearSlotsCopy()));
				this.buildArmor.get(buildArmor.size() - 1).setGearDecos(i.getGearDecoCopy());
			}else {
				this.buildArmor.add(null);
			}
			
			
		}
		if(b.buildTalisman != null) {
			this.buildTalisman = new Talisman (b.getBuildTalisman().getGearName(), b.getBuildTalisman().getGearSkillsCopy(), b.getBuildTalisman().getGearSlotsCopy());
		}else {
			this.buildTalisman = null;
		}
	}

	
	public ArrayList<Armor> getBuildArmor(){
		return buildArmor;
	}
	public void setBuildArmor(ArrayList<Armor> newBuildArmor) {
		this.buildArmor = newBuildArmor;
	}
	
	public Weapon getbuildWpn() {
		return this.buildWpn;
	}
	public void setBuildWpn(Weapon newBuildWpn) {
		this.buildWpn = newBuildWpn;
	}
	
	public Talisman getBuildTalisman() {
		return this.buildTalisman;
	}
	public void setBuildTalisman(Talisman newBuildTalisman) {
		this.buildTalisman = newBuildTalisman;
	}
	
	//Methods: 
	
	public ArrayList<ArrayList<Object>> totalSkillLevels() {
		//listing all skills in build 
		//this ArrayList is filled, to add up identical skills, and later emptied
		ArrayList<ArrayList<Object>> toBeAddedSkillList = new ArrayList<ArrayList<Object>>();
		//first add all skills of talisman and its decos to the arraylist
		//Talisman skills
		if (this.buildTalisman != null) {
			
			for (int i = 0; i < buildTalisman.getGearSkills().size(); i++) {
				
				toBeAddedSkillList.add(new ArrayList<Object>());
				//names
				toBeAddedSkillList.get(i).add(buildTalisman.getGearSkills().get(i).get(0));
				//skillmaxLvl
				toBeAddedSkillList.get(i).add(buildTalisman.getGearSkills().get(i).get(2));
			}
			//Talisman decoslots
			for (int t = 0; t < buildTalisman.getGearDecos().size(); t++) {
				toBeAddedSkillList.add(new ArrayList<Object>());
				//names
				toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildTalisman.getGearDecos().get(t).get(1));
				//skills
				toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildTalisman.getGearDecos().get(t).get(2));
			}
		}
		
		//wpn skills
		if(this.buildWpn.getGearDecos().size() != 0) {
			//wpn decoslots
			for (int t = 0; t < buildWpn.getGearDecos().size(); t++) {
				toBeAddedSkillList.add(new ArrayList<Object>());
				//names
				toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildWpn.getGearDecos().get(t).get(1));
				//skills
				toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildWpn.getGearDecos().get(t).get(2));
			}
		}
		
		//for each armorpiece in build
		for (int i = 0; i < buildArmor.size(); i++) {
			if (buildArmor.get(i) != null) {
				//for each skill of armorpiece add arraylist for this one Skill to arraylist
				for(int a = 0
						; a < buildArmor.get(i).getGearSkills().size();a++) {
					toBeAddedSkillList.add(new ArrayList<Object>());
					//names
					toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildArmor.get(i).getGearSkills().get(a).get(0));
					//skillsmaxLvl
					toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildArmor.get(i).getGearSkills().get(a).get(2));
				}
				//decoSlots
				for (int d = 0; d < buildArmor.get(i).getGearDecos().size(); d++) {
					toBeAddedSkillList.add(new ArrayList<Object>());
					//names
					toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildArmor.get(i).getGearDecos().get(d).get(1));
					//skillmaxLvl
					toBeAddedSkillList.get(toBeAddedSkillList.size()-1).add(buildArmor.get(i).getGearDecos().get(d).get(3));
				}
			}
		}

		//add all identical skills in build
		ArrayList<ArrayList<Object>> addedSkillList = new ArrayList<ArrayList<Object>>();
		ArrayList<Integer> deleteMemory = new ArrayList<Integer>();
		
		for (int j = 0; toBeAddedSkillList.size() != 0; j++) {
			addedSkillList.add((ArrayList<Object>) toBeAddedSkillList.get(0).clone());
			addedSkillList.get(j).add(1);
			deleteMemory.add(0);
			//loop through all skills except j			
			for (int k = 1; k+1 <= toBeAddedSkillList.size(); k++) {
				if(toBeAddedSkillList.get(0).get(0) == toBeAddedSkillList.get(k).get(0)) {
					//addSkillLvls in added array
					addedSkillList.get(j).set(2, (int) addedSkillList.get(j).get(2) + 1);
					//save k to delete Element from toBeAddedSkillList
					deleteMemory.add(k);
				}
			}
			//deletion loop
			for (int l = deleteMemory.size()-1; l >= 0; l--) {
				toBeAddedSkillList.remove(deleteMemory.get(l).intValue());
			}
			deleteMemory.clear();
			
		}
		
		//TODO maybe add the skillprio to list
		return addedSkillList;
		//format: [Name, max lvl, current lvl]
	}
	
	public ArrayList<ArrayList<ArrayList<Object>>> buildGearDecoInfo (int gearType) {
		//PROBLEM: getGearSkills/Decos doesnt hold the lvl, just maxlvl of skill. lvl is known by amount of deco/skill Obj
		ArrayList<ArrayList<ArrayList<Object>>> skillDecoComboList = new ArrayList<ArrayList<ArrayList<Object>>>();
		switch(gearType){
		case 0:
			skillDecoComboList.add(this.buildWpn.calcTotalNatSkills());
			skillDecoComboList.add(this.buildWpn.calcTotalDecoSkills());	
			break;
		case 1:
			skillDecoComboList.add(this.buildArmor.get(0).calcTotalNatSkills());
			skillDecoComboList.add(this.buildArmor.get(0).calcTotalDecoSkills());	
			break;
		case 2:
			skillDecoComboList.add(this.buildArmor.get(1).calcTotalNatSkills());
			skillDecoComboList.add(this.buildArmor.get(1).calcTotalDecoSkills());	
			break;
		case 3:
			skillDecoComboList.add(this.buildArmor.get(2).calcTotalNatSkills());
			skillDecoComboList.add(this.buildArmor.get(2).calcTotalDecoSkills());	
			break;
		case 4:
			skillDecoComboList.add(this.buildArmor.get(3).calcTotalNatSkills());
			skillDecoComboList.add(this.buildArmor.get(3).calcTotalDecoSkills());	
			break;
		case 5:
			skillDecoComboList.add(this.buildArmor.get(4).calcTotalNatSkills());
			skillDecoComboList.add(this.buildArmor.get(4).calcTotalDecoSkills());		
			break;	
		case 6:
			skillDecoComboList.add(this.buildTalisman.calcTotalNatSkills());
			skillDecoComboList.add(this.buildTalisman.calcTotalDecoSkills());		
			break;
		}
		return skillDecoComboList;
	}
}
