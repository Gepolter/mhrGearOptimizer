package gearFamily;
import skills.*;
import java.util.ArrayList;

public class Armor extends Gear{	
	
//	constructor
	public Armor(String gearName, String gearType, Skill[] gearSkills, Slot[] gearSlots) {
		this.gearName = gearName;
		this.gearType = gearType;
		this.gearSkills= gearSkills;
		this.gearSlots = gearSlots;
		this.gearRating = 0;
		gearDecos = new ArrayList<Deco>();
		if (gearType != EnumTypes.HEAD.value && gearType != EnumTypes.CHEST.value && gearType != EnumTypes.ARMS.value && gearType != EnumTypes.WAIST.value && gearType != EnumTypes.LEGS.value){
			throw new IllegalArgumentException("gearType for armor must be head, chest, arms, waist or legs");
			
		}
	}
	
	
	
}
