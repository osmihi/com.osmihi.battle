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

