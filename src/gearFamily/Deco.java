package gearFamily;
import java.util.ArrayList;

import skills.*;


public class Deco extends Gear{

	private Skill decoSkill;
	
	public Deco(String gearName, Skill decoSkill) {
		this.gearName = gearName;
		this.decoSkill = decoSkill;
	}
	
	public ArrayList<Object> getDecoSkill() {
		 ArrayList<Object> decoSkillList = new ArrayList<Object>();
		 decoSkillList.add(decoSkill.getSkillName());
		 decoSkillList.add(decoSkill.getSkillSlot());
		 decoSkillList.add(decoSkill.getMaxLvl());
		 return decoSkillList;
	}
	
	public Skill getDecoSkillRaw() {
		return decoSkill;
	}
	
	public void setDecoSkill(Skill newDecoSkill) {
		this.decoSkill = newDecoSkill;
	}
}
