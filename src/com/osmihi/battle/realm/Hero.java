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

import java.util.ArrayList;

import com.osmihi.battle.mechanics.*;

public class Hero extends Creature {
	private static final long serialVersionUID = 4196793760598389670L;
	
	private HeroType heroType;
	private int level;
	private int xp;
	private int actionPoints;
	
	public Hero(String n, HeroType ht) {
		super(n);
		name = n;
		heroType = ht;
		level = 1;
		xp = 0;
		
		applyHeroType();
		randomMod();
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
		actionPoints = heroType.getStartingActionPoints();
		immunities = heroType.getImmunities();
		setImageFile(heroType.getImageFile());
		setImageAlt(heroType.getImageAlt());
	}
	
	private void randomMod() {
		strength += ( Generator.random(3) - Generator.random(3) ); 
		intelligence += ( Generator.random(3) - Generator.random(3) );
		speed += ( Generator.random(3) - Generator.random(3) );
		defense += ( Generator.random(3) - Generator.random(3) );
		offense += ( Generator.random(3) - Generator.random(3) );
		maxHp += ( Generator.random(heroType.getHpUnit()/3) - Generator.random(heroType.getHpUnit()/3) );
		maxMp += ( Generator.random(heroType.getMpUnit()/3) - Generator.random(heroType.getMpUnit()/3) );
		hp = maxHp;
		mp = maxMp;
	}
	
	@Override
	protected String nameToString() {
		String nameStr = "";
		nameStr += "[" + getHeroType().getName() + " " + getLevel() + "]  \t" + getName() + "\n";
		nameStr += "XP: " + getXp() + "\t\tAP: " + getActionPoints() + "\t\tGP: " + getGp() + "\t\t\t\tHP: " + getHp() + "/" + getMaxHp() + "  \tMP: " + getMp() + "/" + getMaxMp() + "\n";
		return nameStr;
	}
	
	// "Getter" methods
	public String getName() {return name;}
	public HeroType getHeroType() {return heroType;}
	public int getLevel() {return level;}
	public int getXp() {return xp;}
	public int getActionPoints() {return actionPoints;}
	
	public ArrayList<Action> getAvailableActions() {
		ArrayList<Action> avail = new ArrayList<Action>();
		if (!getActions().isEmpty()) {
			for (Action a : getActions()) {
				ActionTreeNode atn = heroType.getActionTree().locate(a);
				if (atn != null) {
					for (ActionTreeNode ak : atn.getChildren()) {
						if (!getActions().contains(ak.getData())) {
							avail.add(ak.getData());
						}
					}
				}
			}
		} else {
			if (heroType.getActionTree() != null && heroType.getActionTree().getRoot() != null && heroType.getActionTree().getRoot().getData() != null) {
				avail.add(heroType.getActionTree().getRoot().getData());
			}
		}
		return avail;
	}
	
	// "Setter" methods
	public void setName(String newName) {name = newName;}
	public void setHeroType(HeroType newHeroType) {heroType = newHeroType;}
	public void setLevel(int newLevel) {level = newLevel;}
	public void setXp(int newXp) {xp = newXp;}
	public void setActionPoints(int actionPoints) {this.actionPoints = actionPoints;}
}
