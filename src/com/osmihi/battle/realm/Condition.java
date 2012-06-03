package com.osmihi.battle.realm;


public class Condition {
	private String name;						// name of condition
	private int duration;
	
	// duration effects
	private int strengthMod;
	private int intelligenceMod;
	private int speedMod;
	private int offenseMod;
	private int defenseMod;

	// TODO Conditions that affect actions and immunities
//	private Action grantedAction;
//	private Action barredAction;
//	private Condition grantedImmunity;
//	private Condition barredImmunity;
	
	// round effects
	private int roundDam;
	private int roundDrain;
		
	public Condition(String n) {
		name = n;
		duration = 0;
		strengthMod = 0;
		intelligenceMod = 0;
		speedMod = 0;
		offenseMod = 0;
		defenseMod = 0;
		roundDam = 0;
		roundDrain = 0;
	}

	// "Getter" methods
	public String getName() {return name;}
	public int getDuration() {return duration;}
	public int getStrengthMod() {return strengthMod;}
	public int getIntelligenceMod() {return intelligenceMod;}
	public int getSpeedMod() {return speedMod;}
	public int getOffenseMod() {return offenseMod;}
	public int getDefenseMod() {return defenseMod;}
	public int getRoundDam() {return roundDam;}
	public int getRoundDrain() {return roundDrain;}

	// "Setter" methods
	public void setName(String name) {this.name = name;}
	public void setDuration(int duration) {this.duration = duration;}
	public void setStrengthMod(int strengthMod) {this.strengthMod = strengthMod;}
	public void setIntelligenceMod(int intelligenceMod) {this.intelligenceMod = intelligenceMod;}
	public void setSpeedMod(int speedMod) {this.speedMod = speedMod;}
	public void setOffenseMod(int offenseMod) {this.offenseMod = offenseMod;}
	public void setDefenseMod(int defenseMod) {this.defenseMod = defenseMod;}
	public void setRoundDam(int roundDam) {this.roundDam = roundDam;}
	public void setRoundDrain(int roundDrain) {this.roundDrain = roundDrain;}
	
	@Override
	public String toString() {
		String toStr = "";
		toStr += "[Condition]\t" + getName() + "\n";
		toStr += "Str Mod: " + getStrengthMod() + "\tInt Mod: " + getIntelligenceMod() + "\tSpd Mod: " + getSpeedMod() + "\tDuration: " + getDuration() + "\n";
		toStr += "Off Mod: " + getOffenseMod() + "\tDef Mod: " + getDefenseMod() + "\tDamage: " + getRoundDam() + "\tDrain: " + getRoundDrain() + "\n";
		return toStr;
	}
}
