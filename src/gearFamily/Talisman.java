package gearFamily;
import skills.*;
import java.util.*;

public class Talisman extends Gear{
	
	public Talisman (String gearName, Skill[] gearSkills,Slot[] gearSlots) {
		this.gearName = gearName;
		this.gearSkills = gearSkills;
		this.gearSlots = gearSlots;
		this.gearRating = 0;
		this.gearType = "talisman";
		gearDecos = new ArrayList<Deco>();
		if(gearType != EnumTypes.TAL.value) {
			throw new IllegalArgumentException("gearType for talisman must be talisman");
		}
	}
	
	
	
}
