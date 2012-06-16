package com.osmihi.battle.realm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.osmihi.battle.mechanics.Generator;

public class Creature {
	private Long id;
	
	protected String name;
	protected int strength;
	protected int intelligence;
	protected int speed;
	
	protected int offense;
	protected int defense;
	
	protected int maxHp;
	protected int maxMp;
	protected int hp;
	protected int mp;
	protected int initiative;
	
	protected int gp;
	private int xpValue;
	
	protected Map<Condition,Integer> status;
	protected ArrayList<Condition> immunities;
	
	protected ArrayList<Action> actions;
	
	protected String imageFile = null;
	protected String imageAlt = null;
	
	public Creature(String n) {
		// initialize values
		id = Generator.id();
		name = n;
		strength = 10;
		intelligence = 10;
		speed = 10;
		defense = 10;
		offense = 10;
		maxHp = 20;
		maxMp = 20;
		
		hp = maxHp;
		mp = maxMp;
		initiative = 0;
		
		gp = 0;
		xpValue = 0;
				
		status = new HashMap<Condition,Integer>();
		immunities = new ArrayList<Condition>();
		
		actions = new ArrayList<Action>();
	}
	
	public Creature(Creature cre) {
		// Copy constructor!
		id = Generator.id();
		name = cre.getName();
		strength = cre.getStrength();
		intelligence = cre.getIntelligence();
		speed = cre.getSpeed();
		defense = cre.getDefense();
		offense = cre.getOffense();
		maxHp = cre.getMaxHp();
		maxMp = cre.getMaxMp();
		
		hp = maxHp;
		mp = maxMp;
		
		gp = cre.getGp();
		xpValue = cre.getXpValue();
		
		status = cre.getStatus();
		immunities = cre.getImmunities();
		
		actions = cre.getActions();
		
		imageFile = cre.getImageFile();
		imageAlt = cre.getImageAlt(); 
	}

	public int loseHp(int dam) {
		if (dam < 0) {dam = 0;}
		hp -= dam;
		if (hp <= 0) {
			hp = 0;
		}
		return hp;
	}
	public int gainHp(int amount) {
		if (amount < 0) {amount = 0;}
		hp += amount;
		if (hp > maxHp) {hp = maxHp;}
		return hp;
	}
	public int loseMp(int drain) {
		if (drain < 0) {drain = 0;}
		mp -= drain;
		if (mp < 0) {mp = 0;}
		return mp;
	}
	public int gainMp(int amount) {
		if (amount < 0) {amount = 0;}
		mp += amount;
		if (mp > maxMp) {mp = maxMp;}
		return mp;
	}

	// "Getter" methods
	public Long getId() {return id;}
	public String getName() {return name;}
	public int getStrength() {return strength;}
	public int getIntelligence() {return intelligence;}
	public int getSpeed() {return speed;}
	public int getOffense() {return offense;}
	public int getDefense() {return defense;}
	public int getHp() {return hp;}	
	public int getMaxHp() {return maxHp;}
	public int getMp() {return mp;}
	public int getMaxMp() {return maxMp;}
	public int getInitiative() {return initiative;} 
	public int getGp() {return gp;}
	public int getXpValue() {return xpValue;}
	
	public Map<Condition,Integer> getStatus() {return status;}
	public int getStatusDuration(Condition c) {return status.get(c).intValue();}
	public boolean hasStatus(Condition c) {return status.containsKey(c);}
	
	public ArrayList<Condition> getImmunities() {return immunities;}
	public boolean isImmune(Condition conditionToCheck) {return immunities.contains(conditionToCheck);}
	
	public ArrayList<Action> getActions() {return actions;}	
	public boolean hasAction(Action a) {return actions.contains(a);}
	
	public String getImageFile() {return imageFile;}
	public String getImageAlt() {return imageAlt;}

	// "Setter" methods
	public void setName(String newName) {name = newName;}
	public void setStrength(int newStrength) {strength = newStrength;}
	public void setIntelligence(int newIntelligence) {intelligence = newIntelligence;}
	public void setSpeed(int newSpeed) {speed = newSpeed;}
	public void setOffense(int newOffense) {offense = newOffense;}
	public void setDefense(int newDefense) {defense = newDefense;}
	public void setHp(int newHp) {hp = newHp;}
	public void setMaxHp(int newMaxHp) {maxHp = newMaxHp;}
	public void setMp(int newMp) {mp = newMp;}
	public void setMaxMp(int newMaxMp) {maxMp = newMaxMp;}
	public void setInitiative(int newInitiative) {initiative = newInitiative;}
	public void setGp(int newGp) {gp = newGp;}
	public void setXpValue(int newXpValue) {xpValue = newXpValue;}

	public void setStatus(Map<Condition,Integer> newStatus) {status = newStatus;}
	public void addStatus(Condition conditionToAdd, int dur) {status.put(conditionToAdd, new Integer(dur));}
	public void dropStatus(Condition conditionToDrop) {status.remove(conditionToDrop);}
	
	public void setImmunities(ArrayList<Condition> newImmunities) {immunities = newImmunities;}
	public void addImmunity(Condition conditionToAdd) {immunities.add(conditionToAdd);}
	public void dropImmunity(Condition conditionToDrop) {immunities.remove(conditionToDrop);}
	
	public void addAction(Action newAction) {actions.add(newAction);}
	public void dropAction(Action actionToDrop) {actions.remove(actionToDrop);}
	public void clearActions() {actions.clear();}

	public void setImageFile(String f) {imageFile = f;}
	public void setImageAlt(String f) {imageAlt = f;}
	
	@Override
	public String toString() {
		String toStr = "";
		String statStr = "";
		String imStr = "";
		String actStr = "";
		toStr += nameToString();
		toStr += "Str: " + getStrength() + "\t\tInt: " + getIntelligence() + "\t\tSpd: " + getSpeed() + 
				"\t\tOff: " + getOffense() + "\t\tDef: " + getDefense() + "\n";
		for (Condition stat : status.keySet()) {
			statStr += stat.getName() + "(" + status.get(stat).intValue()  + ") ";
		}
		toStr += "Status: \t" + statStr + "\n";
		for (Condition imm : immunities) {
			imStr += imm.getName();
			imStr += immunities.indexOf(imm) < immunities.size() -1 ? ", " : ""; 
		}
		toStr += "Immunities: \t" + imStr + "\n";
		for (Action act : actions) {
			actStr += act.getName();
			actStr += actions.indexOf(act) < actions.size() -1 ? ", " : ""; 
		}
		toStr += "Actions: \t" + actStr + "\n";
		return toStr;
	}
	
	protected String nameToString() {
		String nameStr = "";
		nameStr += "[Creature]\t" + getName() + "\n";
		nameStr += "XP Value: " + getXpValue() + "\tGP Value: " + getGp() + "\t\t\tHP: " + getHp() + "/" + getMaxHp() + "  \tMP: " + getMp() + "/" + getMaxMp() + "\n";
		return nameStr;
	}
	
}
