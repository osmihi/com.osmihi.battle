package com.osmihi.battle.game;

import com.osmihi.battle.mechanics.*;
import com.osmihi.battle.realm.*;

import java.util.ArrayList;

public class Game {

	public static void main(String[] args) {	
		Census.populate();
		
		// Test battle.
		
		ArrayList<Creature> hTeam = new ArrayList<Creature>();
		ArrayList<Creature> eTeam = new ArrayList<Creature>();
		
		Hero hero1 = new Hero("Ugg", Census.getHeroType("Barbarian"));
		hero1.addAction(Census.getAttack("Club"));
		hero1.addAction(Census.getSkill("Rage"));
		hTeam.add(hero1);
		
		Hero hero2 = new Hero("Art", Census.getHeroType("Archer"));
		hero2.addAction(Census.getAttack("Shortbow"));
		hero2.addAction(Census.getSkill("Poison Dart"));
		hTeam.add(hero2);
		
		Hero hero3 = new Hero("Takashi", Census.getHeroType("Ninja"));
		hero3.addAction(Census.getAttack("Shortsword"));
		hero3.addAction(Census.getSkill("Trip"));
		hero3.addAction(Census.getSkill("Poison Dart"));
		hero3.addAction(Census.getSpell("Haste"));
		hTeam.add(hero3);
		
		Hero hero5 = new Hero("Virgil", Census.getHeroType("Knight"));
		hero5.addAction(Census.getAttack("Longsword"));
		hero5.addAction(Census.getAttack("Shield Bash"));
		
		hTeam.add(hero5);
		
		Hero hero6 = new Hero("Hedge", Census.getHeroType("Wizard"));
		hero6.addAction(Census.getAttack("Knife"));
		hero6.addAction(Census.getSpell("Slow"));
		hero6.addAction(Census.getSpell("Stone"));
		hero6.addAction(Census.getSpell("Bolt"));
		hTeam.add(hero6);
		
		Creature foe1 = Census.getMonster("Wolf");
		Creature foe2 = Census.getMonster("Wolf");
		Creature foe3 = Census.getMonster("Goblin");
		Creature foe4 = Census.getMonster("Zombie");
		Creature foe5 = Census.getMonster("Ogre");
		eTeam.add(foe1);
		eTeam.add(foe2);
		eTeam.add(foe3);
		eTeam.add(foe4);
		eTeam.add(foe5);
		
		Combat bc = new Combat(hTeam, eTeam);
		
	}
}
