package com.osmihi.battle.realm;

import com.osmihi.battle.mechanics.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Creature {
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
	
	protected Queue<String> message = new LinkedBlockingQueue<String>();
	
	protected Map<Condition,Integer> status;
	protected ArrayList<Condition> immunities;
	
	protected ArrayList<Action> actions;
	
	protected String imageFile = null;
	
	public Creature(String n) {
		// initialize values
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
	}
	
	// Action-related methods
	public void act(Action ac, Creature target) {
		if (hasAction(ac) && getMp() >= ac.getMpCost()) {
			String[] verb = {"",""};
			switch (ac.getType()) {
				case ATTACK:
					verb[0] = "attacks with";
					verb[1] = "against";
					break;
				case SKILL:
					verb[0] = "uses";
					verb[1] = "against";
					break;
				case SPELL:
					verb[0] = "casts";
					verb[1] = "upon";
					break;
				default:
					break;
			}
			
			loseMp(ac.getMpCost());
			
			setMessage(getName() + " " + verb[0] + " " + ac.getName() + " " + verb[1] + " " + target.getName() + "!");
			target.react(ac, this);
		}
	}
	public void react(Action ac, Creature attacker) {
		int aStr = attacker.getStrength();
		int aInt = attacker.getIntelligence();
		int aSpd = attacker.getSpeed();
		int aOff = attacker.getOffense();
		int aDef = attacker.getDefense();
		
		double hitRoll = 0;
		double defRoll = 0;
		int dam = 0;
		boolean lands = ( ac.getStatusChance() >= Generator.random(10) );
		int hitStat = 0;
		int defStat = 0;
		int damStat = 0;
		
		switch (ac.getType()) {
			case ATTACK:
				hitStat = aOff;
				defStat = getSpeed();
				damStat = aStr;
				break;
			case SKILL:
				hitStat = (aInt + aSpd) / 2;
				defStat = ( getIntelligence() + getSpeed() ) / 2;
				damStat = (aInt + aSpd) / 2;
				break;
			case SPELL:
				hitStat = aInt;
				defStat = getIntelligence();
				damStat = aInt;
				
				break;
			default:
				break;
		}
		
		hitRoll = Generator.random(hitStat) * (ac.getSuccessChance() / 10.0);
		defRoll = Generator.random(defStat);
		if (damStat <= 0) {damStat = 1;}		
		dam = Generator.random( ac.getMinDamage(), ac.getMaxDamage() - ac.getMinDamage() );
		dam = dam + ( damStat / 4 ) - ( getDefense() / 4 );
		if (dam < 1) {dam = 1;}
		
		if ((int)hitRoll > (int)defRoll) {
			setMessage(attacker.getName() + "'s " + ac.getName() + " hits " + getName() + " for " + dam + " damage.");
			loseHp(dam);
			if (lands && getHp() > 0 && !(getImmunities().contains(ac.getStatusEffect()))) {
				setMessage(getName() + " is affected by " + ac.getStatusEffect().getName() + ".");
				beginStatus(ac.getStatusEffect());
			}
		} else {setMessage(attacker.getName() + "'s " + ac.getName() + " misses " + getName() + ".");}
	}
	
	// Status-related methods
	public void beginStatus(Condition c) {
		if (!hasStatus(c)) {
			addStatus(c, c.getDuration());
			setStrength(getStrength() + c.getStrengthMod());
			setIntelligence(getIntelligence() + c.getIntelligenceMod());
			setSpeed(getSpeed() + c.getSpeedMod());
			setOffense(getOffense() + c.getOffenseMod());
			setDefense(getDefense() + c.getDefenseMod());
		}
	}
	public void sustainStatus(Condition c) {
		if (hasStatus(c)) {
			int left = getStatus().get(c).intValue();
			if (left <= 0) {
				endStatus(c);
			} else{
				int rDam = Generator.random(c.getRoundDam());
				int rDrain = Generator.random(c.getRoundDrain());
				loseHp(rDam);
				loseMp(rDrain);
				String msg = getName();
				if (rDam >= 0) {msg += " loses ";}
				else {msg += " gains ";}
				msg += rDam + " hp ";
				if (rDrain != 0) {
					msg += "and mp is affected by " + rDrain + " ";
				}
				msg += "due to " + c.getName() + ".";
				if (rDam > 0 || rDrain != 0) setMessage(msg);
				status.put(c, new Integer( left - 1 ) );
			}
		}
	}
	public void endStatus(Condition c) {
		if (hasStatus(c)) {
			setStrength(getStrength() - c.getStrengthMod());
			setIntelligence(getIntelligence() - c.getIntelligenceMod());
			setSpeed(getSpeed() - c.getSpeedMod());
			setOffense(getOffense() - c.getOffenseMod());
			setDefense(getDefense() - c.getDefenseMod());
			dropStatus(c);
			setMessage(getName() + " is no longer affected by " + c.getName() + ".");
		}
	}
	
	// Combat-related methods
	public int rollInitiative() {
		initiative = Generator.random(1,getSpeed());
		return initiative;
	}
	
	public void loseHp(int dam) {
		if (dam < 0) {dam = 0;}
		setHp(getHp() - dam);
		if (hp <= 0) {
			setHp(0);
			setMessage(getName() + " has perished.");
		}
	}
	public void gainHp(int amount) {
		if (amount < 0) {amount = 0;}
		setHp(getHp() + amount);
		if (hp > maxHp) {setHp(maxHp);}
	}
	public void loseMp(int drain) {
		if (drain < 0) {drain = 0;}
		setMp(getMp() - drain);
		if (mp < 0) {setMp(0);}
	}
	public void gainMp(int amount) {
		if (amount < 0) {amount = 0;}
		setMp(getMp() + amount);
		if (mp > maxMp) {setMp(maxMp);}
	}

	// "Getter" methods
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
	public String getMessage() {
		String m = "";
		for(String s : message) {m += s + "\n";}
		message.removeAll(message);
		return m;
	}
	
	public Map<Condition,Integer> getStatus() {return status;}
	public int getStatusDuration(Condition c) {return status.get(c).intValue();}
	public boolean hasStatus(Condition c) {return status.containsKey(c);}
	
	public ArrayList<Condition> getImmunities() {return immunities;}
	public boolean isImmune(Condition conditionToCheck) {return immunities.contains(conditionToCheck);}
	
	public ArrayList<Action> getActions() {return actions;}	
	public boolean hasAction(Action a) {return actions.contains(a);}
	
	public String getImageFile() {return imageFile;}

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
	public void setMessage(String m) {if (m != "") message.add(m);}
	
	public void setStatus(Map<Condition,Integer> newStatus) {status = newStatus;}
	private void addStatus(Condition conditionToAdd, int dur) {status.put(conditionToAdd, new Integer(dur));}
	private void dropStatus(Condition conditionToDrop) {status.remove(conditionToDrop);}
	
	public void setImmunities(ArrayList<Condition> newImmunities) {immunities = newImmunities;}
	public void addImmunity(Condition conditionToAdd) {immunities.add(conditionToAdd);}
	public void dropImmunity(Condition conditionToDrop) {immunities.remove(conditionToDrop);}
	
	public void addAction(Action newAction) {actions.add(newAction);}
	public void dropAction(Action actionToDrop) {actions.remove(actionToDrop);}
	public void clearActions() {actions.clear();}

	public void setImageFile(String f) {imageFile = f;}
	
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
