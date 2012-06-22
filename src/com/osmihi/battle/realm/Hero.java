/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 *
 * --------------
 * Hero.java
 * --------------
 * The Hero class is a subclass of creature, which is used for player characters in the game. The 
 * features that are added for a Hero are HeroType, level and xp. After a battle, heroes are
 * awarded xp (experience points) and in the future I will implement that when the experience points pass
 * certain thresholds, the hero will gain a level and its attributes will increase and the user will have
 * the chance to choose new actions for it. 
 * 
 * The HeroType is like a template for a Hero. The HeroType's values are applied to a Hero when it is
 * created, and will affect options that will be available when the Hero gains a level. More info about
 * HeroType can be found in the header for that class.
 * 
 *********************************************************************************************************/

package com.osmihi.battle.realm;

import com.osmihi.battle.mechanics.*;

public class Hero extends Creature {
	private HeroType heroType;
	private int level;
	private int xp;
	
	public Hero(String n, HeroType ht) {
		super(n);
		name = n;
		heroType = ht;
		level = 1;
		xp = 0;
		
		applyHeroType();
	}
	
	private void applyHeroType() {
		strength += heroType.getStrengthMod();
		intelligence += heroType.getIntelligenceMod();
		speed += heroType.getSpeedMod();
		defense += heroType.getDefenseMod();
		offense += heroType.getOffenseMod();
		maxHp = heroType.getHpUnit();
		maxMp = heroType.getMpUnit();
		hp = maxHp;
		mp = maxMp;
		gp += (heroType.getGp() / 2) + Generator.random(heroType.getGp() / 2);
		immunities = heroType.getImmunities();
		setImageFile(heroType.getImageFile());
		setImageAlt(heroType.getImageAlt());
	}
	
	@Override
	protected String nameToString() {
		String nameStr = "";
		nameStr += "[" + getHeroType().getName() + " " + getLevel() + "]  \t" + getName() + "\n";
		nameStr += "XP: " + getXp() + "\t\tGP: " + getGp() + "\t\t\t\tHP: " + getHp() + "/" + getMaxHp() + "  \tMP: " + getMp() + "/" + getMaxMp() + "\n";
		return nameStr;
	}
	
	// "Getter" methods
	public String getName() {return name;}
	public HeroType getHeroType() {return heroType;}
	public int getLevel() {return level;}
	public int getXp() {return xp;}

	// "Setter" methods
	public void setName(String newName) {name = newName;}
	public void setHeroType(HeroType newHeroType) {heroType = newHeroType;}
	public void setLevel(int newLevel) {level = newLevel;}
	public void setXp(int newXp) {xp = newXp;}
}
