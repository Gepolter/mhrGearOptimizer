 package gearFamily;
 import skills.*;
import sorting.Build;
import sorting.Wishlist;

import java.util.*;
abstract class Gear {

	protected String gearName;
	protected Skill[] gearSkills;
	protected Slot[] gearSlots;
	protected ArrayList<Deco> gearDecos;
	protected int gearRating;
	protected String gearType;

	
	public enum EnumTypes{
		HEAD("head"),
		CHEST("chest"),
		ARMS("arms"),
		WAIST("waist"),
		LEGS("legs"),
		WPN("weapon"),
		TAL("talisman");

		String value;
		private EnumTypes(String value) {
			this.value = value;
		}
		
	}
	
//	getter
	public String getGearName() {
		return this.gearName;
	}
	
	public ArrayList<ArrayList<Object>> getGearSkills() {
		ArrayList<ArrayList<Object>> skillList = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < gearSkills.length; i++) {
			skillList.add(new ArrayList<Object>());
			skillList.get(i).add(gearSkills[i].getSkillName());
			skillList.get(i).add(gearSkills[i].getSkillSlot());
			skillList.get(i).add(gearSkills[i].getMaxLvl());
		}
		return skillList;
	}
	
	public Skill[] getGearSkillsCopy() {
		return gearSkills;
	}
	
	public ArrayList<Integer> getGearSlots() {
		ArrayList<Integer> slotList = new ArrayList<Integer>();
		for (Slot i : gearSlots) {
			slotList.add(i.getSlotLvl());
		}
		return slotList;
	}
	public Slot[] getGearSlotsCopy() {
		Slot[] copy = new Slot[gearSlots.length];
		System.arraycopy(this.gearSlots, 0, copy, 0, copy.length);
		return copy;
	}
	
	public ArrayList<ArrayList<Object>> getGearDecos(){
		ArrayList<ArrayList<Object>> decoList = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < gearDecos.size(); i++) {
			decoList.add(new ArrayList<Object>());
			decoList.get(i).add(gearDecos.get(i).getGearName());
			decoList.get(i).add(gearDecos.get(i).getDecoSkill().get(0));
			decoList.get(i).add(gearDecos.get(i).getDecoSkill().get(1));
			decoList.get(i).add(gearDecos.get(i).getDecoSkill().get(2));

		}
		return decoList;
	}
	
	public ArrayList<Deco> getGearDecoCopy(){
		ArrayList<Deco> copy = new ArrayList<Deco>();
		for(Deco d : gearDecos) {
			copy.add(new Deco (d.getGearName(), d.getDecoSkillRaw()));
		}
		return copy;
	}
	
	public String getGearType() {
		return this.gearType;
	}
	
	//	setter
	public void setGearName(String newGearName) {
		this.gearName = newGearName;
	}
	public void setGearSkills(Skill[] newGearSkills) {
		this.gearSkills = newGearSkills;
	}
	
	public void setGearDecos(ArrayList<Deco> newGearDecos) {
		this.gearDecos = newGearDecos;
	}
	
	public void setGearDecosElement(Deco newGearDeco) {
		this.gearDecos.add(newGearDeco);
	}
	
	public void setGearType(String newGearType) {
		this.gearType = newGearType;
	}
	
	
	
//  further Methods
	
//	special getter method for sum of all accumulated skills through means of inherent and decoskills
	
	public ArrayList<ArrayList<Object>> calcTotalGearSkills() {
		ArrayList<ArrayList<Object>> toBeAddedSkillList = new ArrayList<ArrayList<Object>>();
		//add inherent skills
		if(gearSkills != null) {
			for (int i = 0; i < gearSkills.length; i++) {
				toBeAddedSkillList.add(new ArrayList<Object>());
				//add names
				toBeAddedSkillList.get(i).add(gearSkills[i].getSkillName());
				//add maxLvl?				
			}
		}
		//add deco skills
		for (int j = 0; j < gearDecos.size(); j++) {
			toBeAddedSkillList.add(new ArrayList<Object>());
			//add names
			toBeAddedSkillList.get(toBeAddedSkillList.size() - 1).add(gearDecos.get(j).getDecoSkill().get(0));
		}
		ArrayList<ArrayList<Object>> addedSkillList = new ArrayList<ArrayList<Object>>();
		ArrayList<Integer> deleteMemory = new ArrayList<Integer>();
		
		for (int k = 0; toBeAddedSkillList.size() != 0; k++) {
			addedSkillList.add((ArrayList<Object>)  toBeAddedSkillList.get(0).clone());
			addedSkillList.get(k).add(1);
			deleteMemory.add(0);
			//loop through all skills except k and add skills = k to deletionMemoty
			for (int l = 1; l+1 <= toBeAddedSkillList.size();l++) {
				if(toBeAddedSkillList.get(0).get(0) == toBeAddedSkillList.get(l).get(0)) {
					addedSkillList.get(k).set(1, (int) addedSkillList.get(k).get(1) + 1);
					deleteMemory.add(l);
				}
			}
			//deletion loop
			for (int m = deleteMemory.size()-1; m >= 0; m--) {
				toBeAddedSkillList.remove(deleteMemory.get(m).intValue());
			}
			deleteMemory.clear();
		}
		return addedSkillList;
	}
	public ArrayList<ArrayList<Object>> calcTotalNatSkills() {
		ArrayList<ArrayList<Object>> toBeAddedSkillList = new ArrayList<ArrayList<Object>>();
		//add inherent skills
		if(this.gearSkills != null) {
			for (int i = 0; i < gearSkills.length; i++) {
				toBeAddedSkillList.add(new ArrayList<Object>());
				//add names
				toBeAddedSkillList.get(i).add(gearSkills[i].getSkillName());
				//add maxLvl?
				
			}			
		}
		
		ArrayList<ArrayList<Object>> addedSkillList = new ArrayList<ArrayList<Object>>();
		ArrayList<Integer> deleteMemory = new ArrayList<Integer>();
		
		for (int k = 0; toBeAddedSkillList.size() != 0; k++) {
			addedSkillList.add((ArrayList<Object>)  toBeAddedSkillList.get(0).clone());
			addedSkillList.get(k).add(1);
			deleteMemory.add(0);
			//loop through all skills except k and add skills = k to deletionMemory
			for (int l = 1; l+1 <= toBeAddedSkillList.size();l++) {
				if(toBeAddedSkillList.get(0).get(0) == toBeAddedSkillList.get(l).get(0)) {
					addedSkillList.get(k).set(1, (int) addedSkillList.get(k).get(1) + 1);
					deleteMemory.add(l);
				}
			}
			//deletion loop
			for (int m = deleteMemory.size()-1; m >= 0; m--) {
				toBeAddedSkillList.remove(deleteMemory.get(m).intValue());
			}
			deleteMemory.clear();
		}
		return addedSkillList;
	}

	public ArrayList<ArrayList<Object>> calcTotalDecoSkills() {
		ArrayList<ArrayList<Object>> toBeAddedSkillList = new ArrayList<ArrayList<Object>>();
		
		//add deco skills
		for (int j = 0; j < gearDecos.size(); j++) {
			toBeAddedSkillList.add(new ArrayList<Object>());
			//add names
			toBeAddedSkillList.get(toBeAddedSkillList.size() - 1).add(gearDecos.get(j).getDecoSkill().get(0));
		}
		ArrayList<ArrayList<Object>> addedSkillList = new ArrayList<ArrayList<Object>>();
		ArrayList<Integer> deleteMemory = new ArrayList<Integer>();
		
		for (int k = 0; toBeAddedSkillList.size() != 0; k++) {
			addedSkillList.add((ArrayList<Object>)  toBeAddedSkillList.get(0).clone());
			addedSkillList.get(k).add(1);
			deleteMemory.add(0);
			//loop through all skills except k and add skills = k to deletionMemoty
			for (int l = 1; l+1 <= toBeAddedSkillList.size();l++) {
				if(toBeAddedSkillList.get(0).get(0) == toBeAddedSkillList.get(l).get(0)) {
					addedSkillList.get(k).set(1, (int) addedSkillList.get(k).get(1) + 1);
					deleteMemory.add(l);
				}
			}
			//deletion loop
			for (int m = deleteMemory.size()-1; m >= 0; m--) {
				toBeAddedSkillList.remove(deleteMemory.get(m).intValue());
			}
			deleteMemory.clear();
		}
		return addedSkillList;
	}
	
	public void rateGear(Wishlist w) {
		this.gearRating = 0;
		//calctotalGearSkills counts all skill lvls of a skill as 1 entry [skill, lvl] the lvls have to be counted to matter
		for (int i = 0 ; i < this.calcTotalGearSkills().size(); i++) {
			
				for (int j = 0; j < w.getMergedList().size(); j++) {
					
					if( this.calcTotalGearSkills().get(i).get(0) == w.getMergedList().get(j).get(0)) {
						//TODO here the 1 should be multiplied by some factor according to priority of skill in merged list : take the prio into account (mergedlist.get(j).get(3))
						//prio 1 everywhere??
						//ratingbonus must be invers to prio 
						//multiply rating bonus by lvl of checked skill (calcTotalGearSkills.get(i).get(1)
						gearRating += (100 / (int)w.getMergedList().get(j).get(3) * (int)this.calcTotalGearSkills().get(i).get(1));
						int z =  (int)w.getMergedList().get(j).get(3);
						int k =(100 / (int)w.getMergedList().get(j).get(3));
					}
				}							
		}
	}
	
public int getGearRating() {
	    return this.gearRating;
	}

	public void setGearRating(int newGearRating) {
	this.gearRating = newGearRating;
}

	//	set optimal decos for one gear piece according to wishlist
	public void decoAlgorithm(Wishlist j, Build b, Deco[] d) {
		
		// empty Decos from previous decoAlgorithms
		this.gearDecos.clear();
		//loop durch gearSLots von klein zu groß
		for (int k = this.gearSlots.length; k > 0; k--) {
			
			boolean repeatBcNoFit = true;
			// a = prio
			for (int a = 1; repeatBcNoFit == true && a <= j.getMergedList().size(); a++) {	
				OuterLoop:
				//l = skill
				for(ArrayList<Object> l : j.getMergedList()) {
				
					// is prio = a?
					if(a == (int) l.get(3)) {						
						
						//is this the method used for choice of first build part? if so, totalSkillLevels will return error, and is to be skipped
						if (b.getBuildArmor().get(0) != null || b.getBuildArmor().get(1) != null || b.getBuildArmor().get(2) != null || b.getBuildArmor().get(3) != null || b.getBuildArmor().get(4) != null || b.getBuildTalisman() != null) {
							//is selected Skill maxed already, or is wantedLvl reached? if yes skip to next prio
							for(ArrayList<Object> o : b.totalSkillLevels()) {
								if(l.get(0).toString() == o.get(0).toString() && ((int) l.get(2) <= (int) o.get(2) || (int) l.get(4) == 0)) {
									break OuterLoop;
								}
							}
						}
						
						//do inherent gearSkills max out maxLvl or wanted Lvls? then also skip to next skill on wishlist
						for(ArrayList<Object> p : this.calcTotalGearSkills()) {
							if(l.get(0).toString() == p.get(0).toString() &&((int) l.get(2) <= (int) p.get(1) || (int) l.get(4) == 0)) {
								break OuterLoop;
							}
						}
						
						// check, if skill with prio a fits slotLvl of getGearSlots[k]
						if(this.gearSlots[k-1].getSlotLvl() >= (int) l.get(1)) {
							//Skill fits -> set Deco for Armor, next Slot
							
							  for(Deco x : d) {
							  		if(x.getDecoSkill().get(0) == l.get(0)) {
							  			this.gearDecos.add(x);
							  			
							  		}
							  } 
							repeatBcNoFit = false;
							break;
						}else {
							repeatBcNoFit = true;
							break;
						}
						
					}
				}			
			}
			
			
		}
	}
	
}
