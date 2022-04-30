package skills;

public class Skill {
	
	private String skillName;
	private Slot skillSlot;
	private int maxLvl;
	

	
//	Constructor
	public Skill(String skillName, Slot skillSlot, int maxLvl) {
		this.skillName = skillName;
		this.skillSlot = skillSlot;
		this.maxLvl = maxLvl;
	}
//	Getter
	public String getSkillName() {
		return this.skillName;
	}
	
	public int getSkillSlot() {
		return this.skillSlot.getSlotLvl();
	}	
	
	
	public int getMaxLvl() {
		return this.maxLvl;
	}
	
// Setter
	public void setSkillName(String newSkillName) {
		this.skillName = newSkillName;
	}
	

	public void setSkillSlot(Slot newSkillSlot) {
		this.skillSlot = newSkillSlot;
	}
	
	public void setMaxLvl(int newMaxLvl) {
		this.maxLvl = newMaxLvl;
	}

}
