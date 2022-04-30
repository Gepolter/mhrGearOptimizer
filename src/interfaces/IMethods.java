package interfaces;
import skills.*;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import UI.UiAssembly;
import gearFamily.*;
import sorting.*;

public interface IMethods {
	
	public static void armorInfo(Armor i) {
		ArrayList<Object> infoList = new ArrayList<Object>();
		infoList.add("Name:");
		infoList.add(i.getGearName());
		infoList.add("Type:");
		infoList.add(i.getGearType());
		infoList.add("Skills:");
		infoList.add(i.getGearSkills());
		infoList.add("Slots:");
		infoList.add(i.getGearSlots());
		infoList.add("Decos:");
		infoList.add(i.getGearDecos());
		for (Object j : infoList) {
			System.out.println(j);
		}
	}
	
//	Wishlist Info
	
	public static void wishtlistInfo(Wishlist i) {
		System.out.println("Wishlist:");
		ArrayList<Object> infoList = new ArrayList<Object>();
		for (int j = 0; j < i.getMergedList().size(); j++) {
			
			infoList.add("Wishlist entry " + j);
			infoList.add(i.getMergedList().get(j).get(0));	
			infoList.add("SlotLvl: " + i.getMergedList().get(j).get(1));
			infoList.add("Prio: " + i.getMergedList().get(j).get(2));	

		}
		for (Object k : infoList) {
			System.out.println(k);
		}
	}
	
	public static void buildSkillInfo (Build b) {
		System.out.println("Total Build Skills");
		for (ArrayList<Object> i : b.totalSkillLevels()) {
			System.out.println("Name: " + i.get(0));
			System.out.println("maxLvl: " + i.get(1));
			System.out.println("BuildLvl :" + i.get(2));
		}
	}
	
	//buildBuild needs Arrays of all acessible Objects of Armor, Deco and specified Talismans to choose from
	public static Build buildAlgorithm(Armor[] allArmor, Deco[] allDeco, Talisman[] selectedTalisman, Weapon chosenWpn, Wishlist wList) {
		
		//Buildparts that need to have value to end loop
		//armorparts need to be passed as ArrayList<Armor>
		Talisman buildTalisman = null;
		
		ArrayList<Armor> buildArmor = new ArrayList<Armor>();
		buildArmor.add(null);
		buildArmor.add(null);
		buildArmor.add(null);
		buildArmor.add(null);
		buildArmor.add(null);
		
		Build build = new Build(chosenWpn, buildArmor, buildTalisman);
		//Array for tree in case of equal gain
		ArrayList<Build> buildList = new ArrayList<Build>();
		buildList.add(build);
		//Array for partbranches in case of equal gain. Entries will mirror evaluationList
		ArrayList<String> branchMemory = new ArrayList<String>();
		ArrayList<String> typeExclusion = new ArrayList<String>();
		
		//ArrayList containing all Objects to be rated in coming loop
		ArrayList<ArrayList<Object>> evaluationList = new ArrayList<ArrayList<Object>>();
		
		//there needs to be separate wipWishLists that store the original wishlists values at the time of the creation of a new build branch
		ArrayList<Wishlist> wipListList = new ArrayList<Wishlist>();
		wipListList.add(new Wishlist(wList));
		int wlCounter = 0;
		
		
		
		//loop for alternative builds. List is filled by inner loop in case of equal part ratings
		for (int b = 0; buildList.size() > b; b++) {
		//for (Build b : buildList) {
			
			//delete alternative from last branch
			if(branchMemory.isEmpty() != true) {
				for (String s : branchMemory) {
					for (int i = 0; i < evaluationList.size(); i++) {
						if (s == (String) evaluationList.get(i).get(0)) {
							evaluationList.remove(i);
							break;
						}
					}
				}
			}
			
				
			
			//loop till all buildParts are set
			while(buildList.get(b).getBuildArmor().get(0) == null || buildList.get(b).getBuildArmor().get(1) == null || buildList.get(b).getBuildArmor().get(2) == null || buildList.get(b).getBuildArmor().get(3) == null || buildList.get(b).getBuildArmor().get(4) == null || buildList.get(b).getBuildTalisman() == null) {
				
				//evaluationList need refilling here too, after each successive gear part is chosen
				evaluationList.clear();
				//addArmor
				for(int i = 0; i < allArmor.length; i++) {
					evaluationList.add(new ArrayList<Object>());
					evaluationList.get(i).add(allArmor[i].getGearName());
					evaluationList.get(i).add(allArmor[i].getGearRating());
					evaluationList.get(i).add(allArmor[i].getGearType());
				}
				//addTalismans
				for(int j = 0; j < selectedTalisman.length; j++) {
					evaluationList.add(new ArrayList<Object>());
					evaluationList.get(evaluationList.size() - 1).add(selectedTalisman[j].getGearName());
					evaluationList.get(evaluationList.size() - 1).add(selectedTalisman[j].getGearRating());
					evaluationList.get(evaluationList.size() - 1).add(selectedTalisman[j].getGearType());
				}				
				
				//exclude already set gearTypes
				if(typeExclusion.isEmpty() != true) {
					for (String s : typeExclusion) {
						for (int o = evaluationList.size() - 1; o > - 1; o--) {
							if(s == (String) evaluationList.get(o).get(2)) {
								evaluationList.remove(o);
							}
						}
					}
				}
				
				//set optimal decos for all Gear according to wList and rate Gear according to current wList. Also add rating to evaluationList. Overwrite past ratings.
				for (Armor a : allArmor) {
					a.decoAlgorithm(wipListList.get(wlCounter), buildList.get(b), allDeco);
					a.rateGear(wipListList.get(wlCounter));
					 
					for(ArrayList<Object> i : evaluationList) {
						if(i.get(0) == a.getGearName()) {
							i.set(1, a.getGearRating());
						}
					}
				}
				for (Talisman t : selectedTalisman) {
					t.decoAlgorithm(wipListList.get(wlCounter), buildList.get(b), allDeco);
					t.rateGear(wipListList.get(wlCounter));
					
					for(ArrayList<Object> i : evaluationList) {
						if(i.get(0) == t.getGearName()) {
							i.set(1, t.getGearRating());
						}
					}
				}
				
				
				//for loop wich gearpiece adds most skilllvls requested by wl? Careful of overshooting maxlvl
				for(int k = evaluationList.size(); k > 1; k = evaluationList.size()) {
					if ((int) evaluationList.get(k-1).get(1) > (int) evaluationList.get(k-2).get(1)) {
						evaluationList.remove(k-2);
					}else {
						if((int) evaluationList.get(k-1).get(1) < (int) evaluationList.get(k-2).get(1)) {
							evaluationList.remove(k-1);
						}else {
							//TODO in the end try other sizes, to see, if it impacts results
							//here the addition of new build to buildlist after completing the first build is prohibited
							if((int) evaluationList.size() == 2 && buildList.get(0) == buildList.get(b)) {
								//store deep copy in buildList
								buildList.add(new Build(buildList.get(b)));
								//store parts taken in branchMemory
								branchMemory.add((String) evaluationList.get(0).get(0));
								//store current state of values of wList to be accessed with next build
								wipListList.add(new Wishlist(wipListList.get(wlCounter)));
							}
							evaluationList.remove(k-1);
						}
		
					}
				}
				
				//set gearpiece
				//switch for setting right geartype and saving said type for exclusion from evaluationList
				
				switch((String) evaluationList.get(0).get(2)) {
					case "head":
						for (Armor a : allArmor) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).getBuildArmor().set(0, a);
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										//compare names
										if(wipListList.get(wlCounter).getWishSkills().get(i).get(0) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}
							}
						}
						typeExclusion.add("head");
						System.out.println("head gear set");
						break;
						
					case "chest":
						for (Armor a : allArmor) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).getBuildArmor().set(1, a);
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										if(wipListList.get(wlCounter).getWishSkills().get(i) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}
							}
						}
						typeExclusion.add("chest");
						System.out.println("chest gear set");
						break;
						
					case "arms":
						for (Armor a : allArmor) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).getBuildArmor().set(2, a);
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										if(wipListList.get(wlCounter).getWishSkills().get(i) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}

							}
						}
						typeExclusion.add("arms");
						System.out.println("arms gear set");
						break;
						
					case "waist":
						for (Armor a : allArmor) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).getBuildArmor().set(3, a);
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										if(wipListList.get(wlCounter).getWishSkills().get(i) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}

							}
						}
						typeExclusion.add("waist");
						System.out.println("waist gear set");
						break;
						
					case "legs":
						for (Armor a : allArmor) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).getBuildArmor().set(4, a);
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										if(wipListList.get(wlCounter).getWishSkills().get(i) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}

							}
						}
						typeExclusion.add("legs");
						System.out.println("legs gear set");
						break;
						
					case "talisman":
						for (Talisman a : selectedTalisman) {
							if(a.getGearName() == evaluationList.get(0).get(0)) {
								buildList.get(b).setBuildTalisman(a);	
								for(int i = 0; i < wipListList.get(wlCounter).getWishSkills().size(); i++) {
									for(int j = 0; j < a.calcTotalGearSkills().size(); j++) {
										if(wipListList.get(wlCounter).getWishSkills().get(i) == a.calcTotalGearSkills().get(j).get(0)) {
											wipListList.get(wlCounter).adjustWantedLvl((int) a.calcTotalGearSkills().get(j).get(1), i);
										}

									}
								}
							}
						}
						typeExclusion.add("talisman");
						System.out.println("Talisman gear set");
						break;
					
				}

				
			}
			chosenWpn.decoAlgorithm(wipListList.get(wlCounter), buildList.get(b), allDeco);
			buildList.get(b).setBuildWpn(chosenWpn);
			typeExclusion.clear();
			//with each new buildbranch the corresponding wipList needs to be accessed
			wlCounter = wlCounter + 1;
			
			//TODO reevalutate build in loop: remove all decos, of which the corresponding skilllvls are overshooting maxlvl 
			//and replace until either wl is fulfilled, or redo wouldnt change build
			
		}
		ArrayList<ArrayList<Object>> buildRatingList = new ArrayList<ArrayList<Object>>();
		//loop builds
		for (int b = 0; b < buildList.size(); ++b) {
			buildRatingList.add(new ArrayList<Object>());
			//add build
			buildRatingList.get(b).add(buildList.get(b));
			//add rating
			buildRatingList.get(b).add(0);
			
			/*
			//loop bSkills
			for(int s = 0; s < buildList.get(b).totalSkillLevels().size(); s++) {
				//loop wlSkills
				for(int w = 0; w < wList.getMergedList().size(); w++) {
					// if buildSkill == wListSkill add levels of said skill to buildrating
					if(buildList.get(b).totalSkillLevels().get(s).get(0) == wList.getMergedList().get(w).get(0)) {
						//make prio a factor
						buildRatingList.get(b).set(1, (int) buildRatingList.get(b).get(1) + ((int) buildList.get(b).totalSkillLevels().get(s).get(2) * (100 / (int)wList.getMergedList().get(w).get(3))));
					}
				}
			}
			*/
			
			//Alternative: add up all gearRatings of build
			
			for(int i = 0; i < buildList.get(b).getBuildArmor().size(); i++) {
				buildRatingList.get(b).set(1, (int) buildRatingList.get(b).get(1) + buildList.get(b).getBuildArmor().get(i).getGearRating());
			}
			//+ wpn + tal ratings
			buildRatingList.get(b).set(1, (int) buildRatingList.get(b).get(1) + buildList.get(b).getBuildTalisman().getGearRating() + buildList.get(b).getbuildWpn().getGearRating());
			
			
		}
		//delete all but highest rated build in List
		for (int x = buildRatingList.size(); x > 1; x = buildRatingList.size()) {
			if((int) buildRatingList.get(x-1).get(1) >= (int) buildRatingList.get(x-2).get(1)) {
				buildRatingList.remove(x-2);
			}else {
				buildRatingList.remove(x-1);
			}
		}
		
		IMethods.buildSkillInfo((Build)buildRatingList.get(0).get(0));
		return (Build)buildRatingList.get(0).get(0);

	}
	
	public static void createUI(String[] allWeaponNames, Weapon[] allWpn, String[] allArmorNames, Armor[] allArmor, String[] allSkillNames, Skill[] allSkill, Deco[] allDeco, Font mhFont) {
		UiAssembly ui = new UiAssembly(allWeaponNames, allWpn, allArmorNames, allArmor, allSkillNames, allSkill, allDeco, mhFont);
		
	}


}
