package com.osmihi.battle.realm;


import java.util.ArrayList;

public class HeroType {
	private String name;
	
	private int strengthMod;
	private int intelligenceMod;
	private int speedMod;
	
	private int offenseMod;
	private int defenseMod;
	
	private int hpUnit;
	private int mpUnit;
	
	private int gp;
	
	private ArrayList<Condition> immunities;

	// TODO add data structure for actions.
	// private /**/ actions;
	// actions at levels -- hashmap?
	// private HashMap<Integer, ArrayList<Action>> actionTable;
	// Actually, what we should do is have a linked list or tree!
	// Each heroType has an 'entry-level' action with no pre-reqs,
	// then each after that has one or more pre-reqs, etc.... some kind of tree

	private String imageFile;

	public HeroType(String n) {
		// Default values
		name = n;
		strengthMod = 0;
		intelligenceMod = 0;
		speedMod = 0;
		offenseMod = 0;
		defenseMod = 0;
		hpUnit = 4;
		mpUnit = 4;
		gp = 20;
		immunities = new ArrayList<Condition>();	
		//actions = new /**/;
		imageFile = null;
	}
	
	// "Getter" methods
	public String getName() {return name;}
	public int getStrengthMod() {return strengthMod;}
	public int getIntelligenceMod() {return intelligenceMod;}
	public int getSpeedMod() {return speedMod;}
	public int getOffenseMod() {return offenseMod;}
	public int getDefenseMod() {return defenseMod;}
	public int getHpUnit() {return hpUnit;}
	public int getMpUnit() {return mpUnit;}
	public int getGp() {return gp;}
	public ArrayList<Condition> getImmunities() {return immunities;}
	public String getImageFile() {return imageFile;}

	public void setImageFile(String f) {imageFile = f;}

	public boolean isImmune(Condition conditionToCheck) {return immunities.contains(conditionToCheck);}
	//	public /**/ getActions() {}
	//	public boolean hasAction() {}
	
	// "Setter" methods
	public void setName(String name) {this.name = name;}
	public void setStrengthMod(int strengthMod) {this.strengthMod = strengthMod;}
	public void setIntelligenceMod(int intelligenceMod) {this.intelligenceMod = intelligenceMod;}
	public void setSpeedMod(int speedMod) {this.speedMod = speedMod;}
	public void setOffenseMod(int offenseMod) {this.offenseMod = offenseMod;}
	public void setDefenseMod(int defenseMod) {this.defenseMod = defenseMod;}
	public void setHpUnit(int hpUnit) {this.hpUnit = hpUnit;}
	public void setMpUnit(int mpUnit) {this.mpUnit = mpUnit;}
	public void setGp(int gp) {this.gp = gp;}
	public void setImmunities(ArrayList<Condition> immunities) {this.immunities = immunities;}
	public void addImmunity(Condition conditionToAdd) {immunities.add(conditionToAdd);}
	public void dropImmunity(Condition conditionToDrop) {immunities.remove(conditionToDrop);}
	//	public void setActions() {}
	//	public void addAction() {}
	//	public void dropAction() {}

	@Override
	public String toString() {
		String toStr = "";
		String imStr = "";
		String actStr = "";
		toStr += "[HeroType]\t" + getName() + "    \t\t" + 
				"\tHP unit: " + getHpUnit() + "\tMP unit: " + getMpUnit() + "\n";
		toStr += "Str Mod: " + getStrengthMod() + "\tInt Mod: " + getIntelligenceMod() + "\tSpd Mod: " + getSpeedMod() +
				"\tOff Mod: " + getOffenseMod() + "\tDef Mod: " + getDefenseMod() + "\n";
		for (Condition imm : immunities) {
			imStr += imm.getName();
			imStr += immunities.indexOf(imm) < immunities.size() -1 ? ", " : "";
		}
//		for (Action ac : actions) {
//			actStr += ac.getName();
//			actStr += actions.indexOf(ac) < actions.size() -1 ? ", " : "";
//		}
		toStr += "Immunities: \t" + imStr + "\n";
		toStr += "Actions: \t" + actStr + "\n"; // TODO more complex print to suit data
		return toStr;
	}
}
