package gearFamily;
import java.util.ArrayList;

import skills.*;

public class Weapon extends Gear {
	
	public Weapon (String gearName, Slot[] gearSlots) {
		this.gearName = gearName;
		this.gearSlots = gearSlots;
		this.gearRating = 0;
		gearDecos = new ArrayList<Deco>();
	}

	
}
