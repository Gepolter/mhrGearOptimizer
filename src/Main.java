import gearFamily.*;
import interfaces.IMethods;
import skills.*;
import sorting.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.*;

import UI.UiAssembly;

public class Main {
	
	static Font mhFont = null;
	
	public static void main(String[] args) {
		
//		Slot Types
		Slot slot1 = new Slot (1);
		Slot slot2 = new Slot (2);
		Slot slot3 = new Slot (3);
//		possible Slot Arrays
		Slot[] slotA1 = {slot1};
		Slot[] slotA11 = {slot1, slot1};
		Slot[] slotA111 = {slot1, slot1, slot1};
		Slot[] slotA2 = {slot2};
		Slot[] slotA21 = {slot2, slot1};
		Slot[] slotA211 = {slot2, slot1, slot1};
		Slot[] slotA22 = {slot2, slot2};
		Slot[] slotA221 = {slot2, slot2, slot1};
		Slot[] slotA3 = {slot3};
		Slot[] slotA31 = {slot3, slot1};
		Slot[] slotA311 = {slot3, slot1, slot1};
		Slot[] slotA32 = {slot3, slot2};
		Slot[] slotA321 = {slot3,slot2, slot1};
		Slot[][] allSlot = {slotA1, slotA11,slotA111,slotA2,slotA21,slotA211,slotA22,slotA221,slotA3,slotA31,slotA311,slotA32,slotA321};

//		gear types
		String t0 = "head";
		String t1 = "chest";
		String t2 = "arms";
		String t3 = "waist";
		String t4 = "legs";	
		String t5 = "talisman";
			
//		Skills
		Skill divBless = new Skill ("Divine Blessing", slot2, 3);
		Skill wallRun = new Skill ("Wall Run", slot2, 3);
		Skill wirebugWhisp = new Skill("Wirebug Whisperer", slot2, 3);
		Skill itemProlonger = new Skill("Item Prolinger", slot2, 3);
		Skill recoverySpd = new Skill("Recovery Speed", slot1, 3);
		Skill sonorous = new Skill("Sonorous", slot1,1);
		Skill staminathief = new Skill("Stamina Thief", slot1, 3);
		Skill recUp = new Skill("Recovery Up", slot2, 3);
		Skill hornMaestro = new Skill("Horn Maestro", slot1, 1);
		Skill constitution = new Skill("Constitution", slot2, 5);
		Skill sliding = new Skill("Affinity Sliding", slot1, 1);
		Skill bombs = new Skill("Bombadier", slot1, 3);
		Skill carver = new Skill("Carving Pro", slot1, 1);
		Skill mounting = new Skill("Master Mounter", slot2, 1);
		Skill hungerRes = new Skill("Hunger Resistance", slot1, 3);
		Skill freeMeal = new Skill("Free Meal", slot1, 3);
		Skill muckRes = new Skill("Muck Resistance", slot1, 2);
		Skill diversion = new Skill("Diversion", slot1, 1);
		Skill leap = new Skill("Leap of Faith", slot1, 1);
		Skill fort = new Skill("Fortify", slot2,1);
		Skill geo = new Skill("Geologist", slot1,3);
		Skill bot = new Skill("Botanist",slot1,4);
		Skill blastRes = new Skill("Blast Resistance", slot1,3);
		Skill sleepRes = new Skill("Sleep Resistance", slot1, 3);
		Skill paraRes = new Skill ("Paralysis Resistance", slot1,3);
		Skill poisRes = new Skill ("Poison Resistance", slot1,3);
		Skill fireRes = new Skill ("Fire Resistance", slot1,3);
		Skill waterRes = new Skill ("Water Resistance", slot1,3);
		Skill thunRes = new Skill ("Thunder Resistance", slot1,3);
		Skill iceRes = new Skill ("Ice Resistance", slot1,3);
		Skill draRes = new Skill ("Dragon Resistance", slot1,3);
		Skill rapMorph = new Skill ("Rapid Morph", slot2,3);
		Skill recDwn = new Skill ("Recoil Down", slot1,3);
		Skill bludgeon = new Skill ("Bludgeoner",slot2,3);
		Skill reload = new Skill ("Reload Speed", slot1,3);
		Skill stunRes = new Skill ("Stun Resistance", slot1,3);
		Skill tremorRes = new Skill ("Tremor Resistance", slot2,3);
		Skill earplugs = new Skill ("Earplugs", slot3,5);
		Skill cs = new Skill ("Counterstrike", slot2,3);
		Skill bubbly = new Skill ("Bubbly Dance", slot2,3);
		Skill steadyness = new Skill ("Steadyness", slot1,2);
		Skill punishDraw = new Skill ("Punishing Draw", slot2,3);
		Skill evadeExt = new Skill ("Evade Extender", slot2,3);
		Skill evadeWind = new Skill ("Evade Window", slot2,5);
		Skill flinchFree = new Skill ("Flinch Free", slot1,3);
		Skill quickSheath = new Skill ("Quick Sheath", slot2,3);
		Skill windproof = new Skill ("Windproof", slot1,3);
		Skill slugger = new Skill ("Slugger", slot2,3);
		Skill wideRange = new Skill ("Wide-Range", slot2,5);
		Skill partbreaker = new Skill ("Partbreaker", slot2,3);
		Skill blightRes = new Skill ("Blight Resistance", slot2,3);
		Skill speedEating = new Skill ("Speed Eating", slot2,3);
		Skill divineBlessing = new Skill ("Divine Blessing", slot2,3);
		Skill SpeedSharpening = new Skill ("Speed Sharpening", slot1,3);






		
		Skill wEx = new Skill ("Weakness Exploit", slot2, 3);
		Skill fFree = new Skill ("Flich Free", slot1, 3);
		Skill critEye = new Skill("Critical eye", slot2, 7);
		Skill attBoost = new Skill("Attack Boost", slot2, 7);
		Skill artillery = new Skill("Artillery", slot2, 3);
		Skill offGuard = new Skill("Offensive Guard", slot3, 3);
		
		Skill[] allSkill = {wEx, fFree, critEye, attBoost, artillery, offGuard};
		
//		Decos
		Deco wExD = new Deco ("Weakness Exploit Deco", wEx);
		Deco fFreeD = new Deco("Flinch free Deco", fFree);
		Deco critEyeD = new Deco("Critical Eye Deco", critEye);
		Deco attBoostD = new Deco("Attack Boost Deco", attBoost);
		Deco artilleryD = new Deco("Artillery Deco", artillery);
		Deco offGuardD = new Deco("Offensive Guard Deco", offGuard);
		
		Deco[] allDeco = {wExD,fFreeD, critEyeD, attBoostD, artilleryD, offGuardD};
		
//		Armorsets = skillArray -> Parts
		
		//Skill[] saKamuraH { 
		//kamuraH = new Armor
		
		Skill[] skillArrayA = {wEx, wEx, attBoost, fFree};
		//Armor armorA = new Armor("armorName", t0, skillArrayA, slotA321);
		
		Skill[] skillArrayAX = {};
		Armor armorAX = new Armor("armorNameX", t0, skillArrayAX, slotA321);
		
        Skill[] skillArrayB = {fFree, fFree, fFree};
        //Armor armorB = new Armor("armorNameB", t1, skillArrayB, slotA22);
        
        Skill[] skillArrayBX = {};
        Armor armorBX = new Armor("armorNameBX", t1, skillArrayBX, slotA22);
        
        Skill[] skillArrayC = {fFree, artillery, offGuard};
       // Armor armorC = new Armor("armorNameC", t2, skillArrayC, slotA11);
        
        Skill[] skillArrayCX = {};
        Armor armorCX = new Armor("armorNameCX", t2, skillArrayCX, slotA11);
        
        Skill[] skillArrayD = {critEye, critEye, attBoost, attBoost};
       // Armor armorD = new Armor("armorNameD", t3, skillArrayD, slotA3);
        
        Skill[] skillArrayDX = {};
        Armor armorDX = new Armor("armorNameDX", t3, skillArrayDX, slotA3);
        
        Skill[] skillArrayE = {artillery, artillery, artillery};
       // Armor armorE = new Armor("armorNameE", t4, skillArrayE, slotA21);
        
        Skill[] skillArrayEX = {};
        Armor armorEX = new Armor("armorNameEX", t4, skillArrayEX, slotA21);
        
        //        Armor[] allArmor = {armorA, armorAX, armorB, armorBX, armorC, armorCX, armorD, armorDX, armorE, armorEX};

        Armor[] allArmor = {armorAX, armorBX, armorCX, armorDX, armorEX};

        
//		Talisman
		Talisman testTalisman = new Talisman("testTalisman", skillArrayA, slotA111);
		Talisman testTalismanB = new Talisman ("testTalismanB", skillArrayC, slotA1);
		
		Talisman[] allTalisman = {testTalisman, testTalismanB};
		
//		Weapons
		Weapon testWeapon = new Weapon("testWeapon", slotA1);
		
		Weapon[] allWeapon = {testWeapon};

		
//		Wishlist testing
		
	    int[] prio1 = {1, 1, 2, 3};
	    int[] wantedLvl1 = {3,3,7,3};
	    
	    int[] prio2 = {1, 3, 2};
	    int[] wantedLvl2 = {2, 3, 2};
	    
	    
		Wishlist wishl = new Wishlist(skillArrayA, prio1, wantedLvl1, "name1");
		
		Wishlist wishl2 = new Wishlist(skillArrayC, prio2, wantedLvl2, "name2");
		
		//test Algorithm
		
		//IMethods.buildAlgorithm(allArmor, allDeco, allTalisman, testWeapon, wishl);
		
		//IMethods.buildAlgorithm(allArmor, allDeco, allTalisman, testWeapon, wishl2);

		//UI SETUP
		//get Arrays for ComboBoxes
		String [] allWeaponNames = new String[allWeapon.length];
		for(int i = 0; i < allWeapon.length; i++) {
			allWeaponNames[i] = allWeapon[i].getGearName();
		}
		
		String [] allArmorNames = new String[allArmor.length];
		for(int i = 0; i < allArmor.length; i++) {
			allArmorNames[i] = allArmor[i].getGearName();
		}
		
		String [] allSkillNames = new String[allSkill.length];
		for(int i = 0; i < allSkill.length; i++) {
			allSkillNames[i] = allSkill[i].getSkillName();
		}
		
		String [] allTalismanNames = new String[allTalisman.length];
		for(int i = 0; i < allTalisman.length; i++) {
			allTalismanNames[i] = allTalisman[i].getGearName();
		}
		
		
		
		try {
			mhFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/monsterhunter.ttf")).deriveFont(14f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/monsterhunter.ttf")));
				
		}catch(IOException | FontFormatException e){	
		}
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            
				IMethods.createUI(allWeaponNames, allWeapon, allArmorNames, allArmor, allSkillNames, allSkill, allDeco, mhFont);
	        }
	    });
		
		
	}
	
}