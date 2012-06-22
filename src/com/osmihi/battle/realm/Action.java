/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 *
 * --------------
 * Action.java
 * --------------
 * The Action class represents an action taken by a Creature during a battle. It can be one of three
 * categories: ATTACK, SKILL, or SPELL. These labels are mostly for organizational purposes, since all three
 * operate in the same way, which is why we use one Action class rather than subclassing for each type. In
 * the future, if this is needed, we could make Action an abstract superclass and add functionality that
 * is specific to each type in its subclass. In any case, an action has fields that determine its chance of 
 * success, its cost in magic points, minimum and maximum damage, and a Condition that will affect the target
 * if the Action hits and passes a second check on its statusChance. Also, there are a couple String fields
 * that determine certain words to be used in messages displayed to the user during battle. These are 
 * initialized with default values for each ActionType, but can be overwritten for individual Actions, if
 * so desired. 
 * 
 *********************************************************************************************************/

package com.osmihi.battle.realm;

// The action class applies to Attacks, Skills, and Spells

public class Action {
	public static enum ActionType {ATTACK, SKILL, SPELL}
	
	private String name;
	private ActionType type;			// attack, skill or spell
	private int mpCost;					// amount to deduct from mp
	private int successChance;			// from 1 to 10, success multiplier. lower = harder to land
	private int minDamage;
	private int maxDamage;
	private int statusChance;
	private Condition statusEffect;
	
	private String verb;
	private String preposition;
	
	public Action(String n, ActionType t) {
		name = n;
		type = t;
		// default values
		mpCost = 0;
		successChance = 0;
		minDamage = 0;
		maxDamage = 0;
		statusChance = 0;
		// statusEffect = new Condition();
		
		switch (t) {
			case ATTACK:
				verb = "attacks with";
				preposition = "against";
				break;
			case SKILL:
				verb = "uses";
				preposition = "against";
				break;
			case SPELL:
				verb = "casts";
				preposition = "upon";
				break;
			default:
				break;
		}
	}

	// "Getter" methods
	public String getName() {return name;}
	public ActionType getType() {return type;}
	public int getMpCost() {return mpCost;}
	public int getSuccessChance() {return successChance;}
	public int getMinDamage() {return minDamage;}
	public int getMaxDamage() {return maxDamage;}
	public int getStatusChance() {return statusChance;}
	public Condition getStatusEffect() {return statusEffect;}
	public String getVerb() {return verb;}
	public String getPreposition() {return preposition;}

	// "Setter" methods
	public void setName(String name) {this.name = name;}
	public void setType(ActionType type) {this.type = type;}
	public void setMpCost(int mpCost) {this.mpCost = mpCost;}
	public void setSuccessChance(int successChance) {this.successChance = successChance;}
	public void setMinDamage(int minDamage) {this.minDamage = minDamage;}
	public void setMaxDamage(int maxDamage) {this.maxDamage = maxDamage;}
	public void setStatusChance(int statusChance) {this.statusChance = statusChance;}
	public void setStatusEffect(Condition statusEffect) {this.statusEffect = statusEffect;}
	public void setVerb(String v) {this.verb = v;}
	public void setPreposition(String p) {this.preposition = p;}
}

