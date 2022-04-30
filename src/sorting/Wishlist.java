package sorting;
import skills.*;
import java.util.ArrayList;

import gearFamily.*;

public class Wishlist {
	
	private Skill[] wishSkills;
	private int[] prio;
	private int[] wantedLvl;
	private ArrayList<ArrayList<Object>> mergedList = new ArrayList<ArrayList<Object>>();
	private String wlName;
	
	public Wishlist (Skill[] wishSkills, int[] prio, int[] wantedLvl, String wlName) {
//	    if(buildSkills.length != prio.length) throw new IllegalArgumentException("Size of Skill- and Prioarray needs to be equal");
		this.wishSkills = wishSkills;
		this.prio = prio;
		this.wantedLvl = wantedLvl;		
		this.setWlName(wlName);
		
		for (int i = 0; i < wishSkills.length; i++) {
			mergedList.add(new ArrayList<Object>());
			mergedList.get(i).add(wishSkills[i].getSkillName());
			mergedList.get(i).add(wishSkills[i].getSkillSlot());
			mergedList.get(i).add(wishSkills[i].getMaxLvl());
			mergedList.get(i).add(prio[i]);
			mergedList.get(i).add(wantedLvl[i]);
		}
	}
	
	public Wishlist (Wishlist w) {
		this.wishSkills = new Skill[w.getWishSkillsCopy().length];
		System.arraycopy(w.getWishSkillsCopy(), 0, this.wishSkills, 0, this.wishSkills.length);
		this.prio = new int[w.getWishSkillsCopy().length];
		System.arraycopy(w.getPrioCopy(), 0, this.prio, 0, this.prio.length);
		this.wantedLvl = new int[w.getWantedLvlCopy().length];
		System.arraycopy(w.getWantedLvlCopy(), 0, this.wantedLvl, 0, this.wantedLvl.length);
		
		for (int i = 0; i < wishSkills.length; i++) {
			mergedList.add(new ArrayList<Object>());
			mergedList.get(i).add(wishSkills[i].getSkillName());
			mergedList.get(i).add(wishSkills[i].getSkillSlot());
			mergedList.get(i).add(wishSkills[i].getMaxLvl());
			mergedList.get(i).add(prio[i]);
			mergedList.get(i).add(wantedLvl[i]);
		}
	}
	
//	getter       --- Merge both Arrays?
	//selbe getter wie gear Klasse?
	public ArrayList<ArrayList<Object>> getWishSkills() {
		ArrayList<ArrayList<Object>> skillList = new ArrayList<ArrayList<Object>>();
		for (Skill i : wishSkills) {
			skillList.add(new ArrayList<Object>());
			skillList.get(skillList.size() - 1).add(i.getSkillName());
			skillList.get(skillList.size() - 1).add(i.getSkillSlot());			
		}
		return skillList;
		
	}
	
	public Skill[] getWishSkillsCopy() {
		Skill[] copy = new Skill[this.wishSkills.length];
		System.arraycopy(wishSkills, 0, copy, 0, wishSkills.length);
		return copy;
	}
	
	public ArrayList<Object> getPrio() {
		ArrayList<Object> prioList = new ArrayList<Object>();
		for (int i : prio) {
			prioList.add(i);
		}
		return prioList;
	}
	public int[] getPrioCopy() {
		int[] copy = new int[this.prio.length];
		System.arraycopy(prio, 0, copy, 0, prio.length);
		return copy;
	}
	
	public int[] getWantedLvlCopy() {
		int[] copy = new int[this.wantedLvl.length];
		System.arraycopy(wantedLvl, 0, copy, 0, wantedLvl.length);
		return copy;
	}
	public void adjustWantedLvl(int toBeSubtracted, int placeInArray) {
		this.wantedLvl[placeInArray] = this.wantedLvl[placeInArray] - toBeSubtracted;
	}
	
	public ArrayList<ArrayList<Object>> getMergedList() {
		return this.mergedList;
	}
	
	//add skillset info parameter arrays cant be changed... problem in calculation?
	public void addSkillsetInfo(Skill addSkill,  int addPrio, int addWantedLvl) {
		this.mergedList.add(new ArrayList<Object>());
		mergedList.get(mergedList.size()-1).add(addSkill.getSkillName());
		mergedList.get(mergedList.size()-1).add(addSkill.getSkillSlot());
		mergedList.get(mergedList.size()-1).add(addSkill.getMaxLvl());
		mergedList.get(mergedList.size()-1).add(addPrio);
		mergedList.get(mergedList.size()-1).add(addWantedLvl);
		
		
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
}
