package skills;

public class Slot {

	private int slotLvl;
	
//	constructor
	public Slot (int slotLvl) {
		if(0 > slotLvl || slotLvl > 3) {
			throw new IllegalArgumentException("0 <= slotLvl <= 3");
		}
		this.slotLvl = slotLvl;	
	}
		
	
//	getter
	public int getSlotLvl() {
		return this.slotLvl;
	}
	
//	setter
	
	public void setSlotLvl(int newSlotLvl) {
		this.slotLvl = newSlotLvl;
	}
}

