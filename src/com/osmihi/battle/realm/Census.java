/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * Michael Dorin's suggestion to use Hashtables to store the objects created in the Census class.
 *
 * --------------
 * Census.java
 * --------------
 * Census is a static-only class that instantiates and holds various objects that inhabit the realm of the 
 * game. These can be thought of as the definitions for the different types of heroes, monsters, actions,
 * and status conditions. The static method populate() creates all the instances of these various objects,
 * which are then accessible via the various getter methods.
 * 
 * As you can see, right now these definitions are all hard-coded into the populate() method. In the future,
 * I'd like to store this information in one or more xml files and then simply have the populate method
 * create instances for each. This would make it easier to maintain, and would enable other people to create
 * new actions and monsters without requiring them to directly access the program code.
 * 
 *********************************************************************************************************/

package com.osmihi.battle.realm;

import java.util.Collection;
import java.util.Hashtable;

public class Census {
	// The Census class instantiates all the hero types, creature types, and actions. 
	// Is it better to divide this up and put into static methods in the corresponding class defs?
	// for example, HeroType.getHeroTypeList(), HeroType.getHeroType(), or maybe just HeroType.ARCHER, etc
	// maybe, maybe not....
	
	private static Hashtable<String,Condition> conditions = new Hashtable<String,Condition>();
	private static Hashtable<String,Action> actions = new Hashtable<String,Action>();
	private static Hashtable<String,Action> attacks = new Hashtable<String,Action>();
	private static Hashtable<String,Action> skills = new Hashtable<String,Action>();
	private static Hashtable<String,Action> spells = new Hashtable<String,Action>();
	private static Hashtable<String,HeroType> heroTypes = new Hashtable<String,HeroType>();
	private static Hashtable<String,Creature> monsters = new Hashtable<String,Creature>();
	
	private Census() {}
	
	public static void populate() {
		// Conditions
		// poison, slow, stone, haste, mighty		
		conditions.put("Poison", new Condition("Poison"));
		conditions.get("Poison").setStrengthMod(-3);
		conditions.get("Poison").setSpeedMod(-2);
		conditions.get("Poison").setRoundDam(1);
		conditions.get("Poison").setDuration(4);
		
		conditions.put("Bleed", new Condition("Bleed"));
		conditions.get("Bleed").setStrengthMod(-2);
		conditions.get("Bleed").setRoundDam(5);
		conditions.get("Bleed").setDuration(4);
		
		conditions.put("Slow", new Condition("Slow"));
		conditions.get("Slow").setSpeedMod(-10);
		conditions.get("Slow").setDuration(2);
		
		conditions.put("Stone", new Condition("Stone"));
		conditions.get("Stone").setSpeedMod(-50);
		conditions.get("Stone").setIntelligenceMod(-50);
		conditions.get("Stone").setStrengthMod(-50);
		conditions.get("Stone").setOffenseMod(-50);
		conditions.get("Stone").setDefenseMod(50);
		conditions.get("Stone").setDuration(3);
		
		conditions.put("Protected", new Condition("Protected"));
		conditions.get("Protected").setIntelligenceMod(10);
		conditions.get("Protected").setDefenseMod(20);
		conditions.get("Protected").setDuration(1);
		
		conditions.put("Haste", new Condition("Haste"));
		conditions.get("Haste").setSpeedMod(25);
		conditions.get("Haste").setDefenseMod(15);
		conditions.get("Haste").setDuration(3);
		
		conditions.put("Might", new Condition("Might"));
		conditions.get("Might").setStrengthMod(20);
		conditions.get("Might").setIntelligenceMod(-5);
		conditions.get("Might").setDuration(3);

		conditions.put("Enraged", new Condition("Enraged"));
		conditions.get("Enraged").setStrengthMod(20);
		conditions.get("Enraged").setDefenseMod(-10);
		conditions.get("Enraged").setIntelligenceMod(-10);
		conditions.get("Enraged").setDuration(3);
		
		conditions.put("Cripple", new Condition("Cripple"));
		conditions.get("Cripple").setDefenseMod(-20);
		conditions.get("Cripple").setSpeedMod(-10);
		conditions.get("Cripple").setDuration(1);
		
		conditions.put("Hide", new Condition("Hide"));
		conditions.get("Hide").setOffenseMod(-50);
		conditions.get("Hide").setDefenseMod(50);
		conditions.get("Hide").setDuration(1);
		
		conditions.put("Aided", new Condition("Aided"));
		conditions.get("Aided").setOffenseMod(10);
		conditions.get("Aided").setDefenseMod(10);
		conditions.get("Aided").setDuration(1);
		
		conditions.put("Hemorrhage", new Condition("Hemorrhage"));
		conditions.get("Hemorrhage").setRoundDam(40);
		conditions.get("Hemorrhage").setDuration(1);
		
		// Actions
		// Attacks, Skills, Spells

		attacks.put("Club", new Action("Club", Action.ActionType.ATTACK));
		attacks.get("Club").setMpCost(0);
		attacks.get("Club").setSuccessChance(6);
		attacks.get("Club").setMinDamage(4);
		attacks.get("Club").setMaxDamage(10);
		attacks.get("Club").setStatusChance(0);
		attacks.get("Club").setStatusEffect(null);
				
		attacks.put("Mace", new Action("Mace", Action.ActionType.ATTACK));
		attacks.get("Mace").setMpCost(0);
		attacks.get("Mace").setSuccessChance(8);
		attacks.get("Mace").setMinDamage(4);
		attacks.get("Mace").setMaxDamage(14);
		attacks.get("Mace").setStatusChance(0);
		attacks.get("Mace").setStatusEffect(null);
		
		attacks.put("Knife", new Action("Knife", Action.ActionType.ATTACK));
		attacks.get("Knife").setMpCost(0);
		attacks.get("Knife").setSuccessChance(7);
		attacks.get("Knife").setMinDamage(4);
		attacks.get("Knife").setMaxDamage(8);
		attacks.get("Knife").setStatusChance(1);
		attacks.get("Knife").setStatusEffect(conditions.get("Bleed"));

		attacks.put("Staff", new Action("Staff", Action.ActionType.ATTACK));
		attacks.get("Staff").setMpCost(0);
		attacks.get("Staff").setSuccessChance(8);
		attacks.get("Staff").setMinDamage(2);
		attacks.get("Staff").setMaxDamage(6);
		attacks.get("Staff").setStatusChance(0);
		attacks.get("Staff").setStatusEffect(null);
		
		attacks.put("Lacerate", new Action("Lacerate", Action.ActionType.ATTACK));
		attacks.get("Lacerate").setMpCost(0);
		attacks.get("Lacerate").setSuccessChance(9);
		attacks.get("Lacerate").setMinDamage(2);
		attacks.get("Lacerate").setMaxDamage(6);
		attacks.get("Lacerate").setStatusChance(9);
		attacks.get("Lacerate").setStatusEffect(conditions.get("Bleed"));
		
		attacks.put("Shortsword", new Action("Shortsword", Action.ActionType.ATTACK));
		attacks.get("Shortsword").setMpCost(0);
		attacks.get("Shortsword").setSuccessChance(8);
		attacks.get("Shortsword").setMinDamage(4);
		attacks.get("Shortsword").setMaxDamage(10);
		attacks.get("Shortsword").setStatusChance(0);
		attacks.get("Shortsword").setStatusEffect(null);
		
		attacks.put("Longsword", new Action("Longsword", Action.ActionType.ATTACK));
		attacks.get("Longsword").setMpCost(0);
		attacks.get("Longsword").setSuccessChance(7);
		attacks.get("Longsword").setMinDamage(6);
		attacks.get("Longsword").setMaxDamage(12);
		attacks.get("Longsword").setStatusChance(0);
		attacks.get("Longsword").setStatusEffect(null);
		
		attacks.put("Shortbow", new Action("Shortbow", Action.ActionType.ATTACK));
		attacks.get("Shortbow").setMpCost(0);
		attacks.get("Shortbow").setSuccessChance(8);
		attacks.get("Shortbow").setMinDamage(3);
		attacks.get("Shortbow").setMaxDamage(12);
		attacks.get("Shortbow").setStatusChance(0);
		attacks.get("Shortbow").setStatusEffect(null);
		
		attacks.put("Sureshot", new Action("Sureshot", Action.ActionType.ATTACK));
		attacks.get("Sureshot").setMpCost(0);
		attacks.get("Sureshot").setSuccessChance(14);
		attacks.get("Sureshot").setMinDamage(2);
		attacks.get("Sureshot").setMaxDamage(6);
		attacks.get("Sureshot").setStatusChance(0);
		attacks.get("Sureshot").setStatusEffect(null);

		attacks.put("Longbow", new Action("Longbow", Action.ActionType.ATTACK));
		attacks.get("Longbow").setMpCost(0);
		attacks.get("Longbow").setSuccessChance(8);
		attacks.get("Longbow").setMinDamage(6);
		attacks.get("Longbow").setMaxDamage(16);
		attacks.get("Longbow").setStatusChance(0);
		attacks.get("Longbow").setStatusEffect(null);
		
		attacks.put("Ankle Shot", new Action("Ankle Shot", Action.ActionType.ATTACK));
		attacks.get("Ankle Shot").setMpCost(0);
		attacks.get("Ankle Shot").setSuccessChance(8);
		attacks.get("Ankle Shot").setMinDamage(3);
		attacks.get("Ankle Shot").setMaxDamage(12);
		attacks.get("Ankle Shot").setStatusChance(0);
		attacks.get("Ankle Shot").setStatusEffect(conditions.get("Cripple"));
		
		attacks.put("Shield Bash", new Action("Shield Bash", Action.ActionType.ATTACK));
		attacks.get("Shield Bash").setMpCost(0);
		attacks.get("Shield Bash").setSuccessChance(5);
		attacks.get("Shield Bash").setMinDamage(0);
		attacks.get("Shield Bash").setMaxDamage(6);
		attacks.get("Shield Bash").setStatusChance(8);
		attacks.get("Shield Bash").setStatusEffect(conditions.get("Slow"));

		attacks.put("Throat Cut", new Action("Throat Cut", Action.ActionType.ATTACK));
		attacks.get("Throat Cut").setMpCost(0);
		attacks.get("Throat Cut").setSuccessChance(8);
		attacks.get("Throat Cut").setMinDamage(6);
		attacks.get("Throat Cut").setMaxDamage(10);
		attacks.get("Throat Cut").setStatusChance(6);
		attacks.get("Throat Cut").setStatusEffect(conditions.get("Hemorrhage"));
		
		attacks.put("Wolf Bite", new Action("Wolf Bite", Action.ActionType.ATTACK));
		attacks.get("Wolf Bite").setMpCost(0);
		attacks.get("Wolf Bite").setSuccessChance(6);
		attacks.get("Wolf Bite").setMinDamage(0);
		attacks.get("Wolf Bite").setMaxDamage(4);
		attacks.get("Wolf Bite").setStatusChance(2);
		attacks.get("Wolf Bite").setStatusEffect(conditions.get("Poison"));
		
		attacks.put("Zombie Grab", new Action("Zombie Grab", Action.ActionType.ATTACK));
		attacks.get("Zombie Grab").setMpCost(0);
		attacks.get("Zombie Grab").setSuccessChance(8);
		attacks.get("Zombie Grab").setMinDamage(3);
		attacks.get("Zombie Grab").setMaxDamage(10);
		attacks.get("Zombie Grab").setStatusChance(2);
		attacks.get("Zombie Grab").setStatusEffect(conditions.get("Poison"));
		
		skills.put("Trip", new Action("Trip", Action.ActionType.SKILL));
		skills.get("Trip").setMpCost(0);
		skills.get("Trip").setSuccessChance(6);
		skills.get("Trip").setMinDamage(0);
		skills.get("Trip").setMaxDamage(4);
		skills.get("Trip").setStatusChance(6);
		skills.get("Trip").setStatusEffect(conditions.get("Slow"));
		
		skills.put("Aid", new Action("Aid", Action.ActionType.SKILL));
		skills.get("Aid").setMpCost(0);
		skills.get("Aid").setSuccessChance(14);
		skills.get("Aid").setMinDamage(0);
		skills.get("Aid").setMaxDamage(0);
		skills.get("Aid").setStatusChance(8);
		skills.get("Aid").setStatusEffect(conditions.get("Aided"));
		
		skills.put("Poison Dart", new Action("Poison Dart", Action.ActionType.SKILL));
		skills.get("Poison Dart").setMpCost(0);
		skills.get("Poison Dart").setSuccessChance(6);
		skills.get("Poison Dart").setMinDamage(0);
		skills.get("Poison Dart").setMaxDamage(4);
		skills.get("Poison Dart").setStatusChance(8);
		skills.get("Poison Dart").setStatusEffect(conditions.get("Poison"));
		
		skills.put("Rage", new Action("Rage", Action.ActionType.SKILL));
		skills.get("Rage").setMpCost(0);
		skills.get("Rage").setSuccessChance(5);
		skills.get("Rage").setMinDamage(0);
		skills.get("Rage").setMaxDamage(0);
		skills.get("Rage").setStatusChance(10);
		skills.get("Rage").setStatusEffect(conditions.get("Enraged"));
		
		skills.put("Cripple", new Action("Cripple", Action.ActionType.SKILL));
		skills.get("Cripple").setMpCost(0);
		skills.get("Cripple").setSuccessChance(5);
		skills.get("Cripple").setMinDamage(0);
		skills.get("Cripple").setMaxDamage(6);
		skills.get("Cripple").setStatusChance(10);
		skills.get("Cripple").setStatusEffect(conditions.get("Cripple"));

		skills.put("Hide", new Action("Hide", Action.ActionType.SKILL));
		skills.get("Hide").setMpCost(0);
		skills.get("Hide").setSuccessChance(8);
		skills.get("Hide").setMinDamage(0);
		skills.get("Hide").setMaxDamage(0);
		skills.get("Hide").setStatusChance(10);
		skills.get("Hide").setStatusEffect(conditions.get("Hide"));
		
		skills.put("Protect", new Action("Protect", Action.ActionType.SKILL));
		skills.get("Protect").setMpCost(0);
		skills.get("Protect").setSuccessChance(14);
		skills.get("Protect").setMinDamage(0);
		skills.get("Protect").setMaxDamage(0);
		skills.get("Protect").setStatusChance(6);
		skills.get("Protect").setStatusEffect(conditions.get("Protected"));
		
		
		spells.put("Slow", new Action("Slow", Action.ActionType.SPELL));
		spells.get("Slow").setMpCost(4);
		spells.get("Slow").setSuccessChance(6);
		spells.get("Slow").setMinDamage(0);
		spells.get("Slow").setMaxDamage(0);
		spells.get("Slow").setStatusChance(10);
		spells.get("Slow").setStatusEffect(conditions.get("Slow"));
		
		spells.put("Stone", new Action("Stone", Action.ActionType.SPELL));
		spells.get("Stone").setMpCost(8);
		spells.get("Stone").setSuccessChance(6);
		spells.get("Stone").setMinDamage(0);
		spells.get("Stone").setMaxDamage(0);
		spells.get("Stone").setStatusChance(10);
		spells.get("Stone").setStatusEffect(conditions.get("Stone"));
		
		spells.put("Haste", new Action("Haste", Action.ActionType.SPELL));
		spells.get("Haste").setMpCost(6);
		spells.get("Haste").setSuccessChance(7);
		spells.get("Haste").setMinDamage(0);
		spells.get("Haste").setMaxDamage(0);
		spells.get("Haste").setStatusChance(10);
		spells.get("Haste").setStatusEffect(conditions.get("Haste"));
		
		spells.put("Bolt", new Action("Bolt", Action.ActionType.SPELL));
		spells.get("Bolt").setMpCost(6);
		spells.get("Bolt").setSuccessChance(8);
		spells.get("Bolt").setMinDamage(6);
		spells.get("Bolt").setMaxDamage(20);
		spells.get("Bolt").setStatusChance(0);
		spells.get("Bolt").setStatusEffect(null);
		
		actions.putAll(attacks);
		actions.putAll(skills);
		actions.putAll(spells);
		
		// HeroTypes
		heroTypes.put("Barbarian", new HeroType("Barbarian"));
		heroTypes.get("Barbarian").setStrengthMod(10);
		heroTypes.get("Barbarian").setIntelligenceMod(-5);
		heroTypes.get("Barbarian").setSpeedMod(0);
		heroTypes.get("Barbarian").setOffenseMod(10);
		heroTypes.get("Barbarian").setDefenseMod(0);
		heroTypes.get("Barbarian").setHpUnit(20);
		heroTypes.get("Barbarian").setMpUnit(0);
		heroTypes.get("Barbarian").setGp(20);
		heroTypes.get("Barbarian").addImmunity(conditions.get("Poison"));
		// actions
		heroTypes.get("Barbarian").setStartingActionPoints(2);
		ActionTree barbarianTree = new ActionTree(actions.get("Club"));
		barbarianTree.insert(actions.get("Club"), actions.get("Mace"));
		barbarianTree.insert(actions.get("Mace"), actions.get("Shield Bash"));
		barbarianTree.insert(actions.get("Mace"), actions.get("Rage"));
		barbarianTree.insert(actions.get("Club"), actions.get("Poison Dart"));
		barbarianTree.insert(actions.get("Poison Dart"), actions.get("Hide"));
		barbarianTree.insert(actions.get("Poison Dart"), actions.get("Trip"));
		heroTypes.get("Barbarian").setActionTree(barbarianTree);
		
		heroTypes.put("Archer", new HeroType("Archer"));
		heroTypes.get("Archer").setStrengthMod(0);
		heroTypes.get("Archer").setIntelligenceMod(0);
		heroTypes.get("Archer").setSpeedMod(0);
		heroTypes.get("Archer").setOffenseMod(0);
		heroTypes.get("Archer").setDefenseMod(15);
		heroTypes.get("Archer").setHpUnit(16);
		heroTypes.get("Archer").setMpUnit(0);
		heroTypes.get("Archer").setGp(80);
		heroTypes.get("Archer").addImmunity(conditions.get("Cripple"));
		// actions
		heroTypes.get("Archer").setStartingActionPoints(3);
		ActionTree archerTree = new ActionTree(actions.get("Shortbow"));
		archerTree.insert(actions.get("Shortbow"),actions.get("Poison Dart"));
		archerTree.insert(actions.get("Poison Dart"), actions.get("Hide"));
		archerTree.insert(actions.get("Hide"), actions.get("Ankle Shot"));
		archerTree.insert(actions.get("Shortbow"), actions.get("Sureshot"));
		archerTree.insert(actions.get("Sureshot"), actions.get("Longbow"));
		heroTypes.get("Archer").setActionTree(archerTree);
		
		heroTypes.put("Ninja", new HeroType("Ninja"));
		heroTypes.get("Ninja").setStrengthMod(0);
		heroTypes.get("Ninja").setIntelligenceMod(0);
		heroTypes.get("Ninja").setSpeedMod(10);
		heroTypes.get("Ninja").setOffenseMod(5);
		heroTypes.get("Ninja").setDefenseMod(0);
		heroTypes.get("Ninja").setHpUnit(16);
		heroTypes.get("Ninja").setMpUnit(6);
		heroTypes.get("Ninja").setGp(60);
		heroTypes.get("Ninja").addImmunity(conditions.get("Enraged"));
		// actions
		heroTypes.get("Ninja").setStartingActionPoints(3);
		ActionTree ninjaTree = new ActionTree(actions.get("Knife"));
		ninjaTree.insert(actions.get("Knife"), actions.get("Poison Dart"));
		ninjaTree.insert(actions.get("Knife"), actions.get("Shortsword"));
		ninjaTree.insert(actions.get("Knife"), actions.get("Trip"));
		ninjaTree.insert(actions.get("Trip"), actions.get("Haste"));
		ninjaTree.insert(actions.get("Trip"), actions.get("Slow"));
		ninjaTree.insert(actions.get("Poison Dart"), actions.get("Hide"));
		heroTypes.get("Ninja").setActionTree(ninjaTree);
		
		heroTypes.put("Thief", new HeroType("Thief"));
		heroTypes.get("Thief").setStrengthMod(-6);
		heroTypes.get("Thief").setIntelligenceMod(5);
		heroTypes.get("Thief").setSpeedMod(10);
		heroTypes.get("Thief").setOffenseMod(-4);
		heroTypes.get("Thief").setDefenseMod(10);
		heroTypes.get("Thief").setHpUnit(12);
		heroTypes.get("Thief").setMpUnit(12);
		heroTypes.get("Thief").setGp(200);
//		heroTypes.get("Thief").addImmunity(conditionToAdd);
//		add actions here
		heroTypes.get("Thief").setStartingActionPoints(5);
		ActionTree thiefTree = new ActionTree(actions.get("Knife"));
		thiefTree.insert(actions.get("Knife"), actions.get("Poison Dart"));
		thiefTree.insert(actions.get("Knife"), actions.get("Trip"));
		thiefTree.insert(actions.get("Knife"), actions.get("Hide"));
		thiefTree.insert(actions.get("Trip"), actions.get("Cripple"));
		thiefTree.insert(actions.get("Haste"), actions.get("Bolt"));
		thiefTree.insert(actions.get("Poison Dart"), actions.get("Lacerate"));
		thiefTree.insert(actions.get("Lacerate"), actions.get("Throat Cut"));
		thiefTree.insert(actions.get("Hide"), actions.get("Aid"));
		thiefTree.insert(actions.get("Hide"), actions.get("Haste"));
		heroTypes.get("Thief").setActionTree(thiefTree);
		
		heroTypes.put("Knight", new HeroType("Knight"));
		heroTypes.get("Knight").setStrengthMod(10);
		heroTypes.get("Knight").setIntelligenceMod(-5);
		heroTypes.get("Knight").setSpeedMod(-5);
		heroTypes.get("Knight").setOffenseMod(5);
		heroTypes.get("Knight").setDefenseMod(10);
		heroTypes.get("Knight").setHpUnit(18);
		heroTypes.get("Knight").setMpUnit(0);
		heroTypes.get("Knight").setGp(100);
//		heroTypes.get("Knight").addImmunity(conditionToAdd);
		// actions
		heroTypes.get("Knight").setStartingActionPoints(2);
		ActionTree knightTree = new ActionTree(actions.get("Longsword"));
		knightTree.insert(actions.get("Longsword"), actions.get("Shield Bash"));
		knightTree.insert(actions.get("Shield Bash"), actions.get("Mace"));
		knightTree.insert(actions.get("Longsword"), actions.get("Protect"));
		heroTypes.get("Knight").setActionTree(knightTree);

		heroTypes.put("Wizard", new HeroType("Wizard"));
		heroTypes.get("Wizard").setStrengthMod(-7);
		heroTypes.get("Wizard").setIntelligenceMod(17);
		heroTypes.get("Wizard").setSpeedMod(7);
		heroTypes.get("Wizard").setOffenseMod(-7);
		heroTypes.get("Wizard").setDefenseMod(5);
		heroTypes.get("Wizard").setHpUnit(9);
		heroTypes.get("Wizard").setMpUnit(24);
		heroTypes.get("Wizard").setGp(120);
//		heroTypes.get("Wizard").addImmunity(conditionToAdd);
		// actions
		heroTypes.get("Wizard").setStartingActionPoints(4);
		ActionTree wizardTree = new ActionTree(actions.get("Staff"));
		wizardTree.insert(actions.get("Staff"), actions.get("Slow"));
		wizardTree.insert(actions.get("Slow"), actions.get("Stone"));
		wizardTree.insert(actions.get("Staff"), actions.get("Haste"));
		wizardTree.insert(actions.get("Haste"), actions.get("Bolt"));
		heroTypes.get("Wizard").setActionTree(wizardTree);

		// Monsters
		monsters.put("Wolf", new Creature("Wolf"));
		monsters.get("Wolf").setStrength(4);
		monsters.get("Wolf").setIntelligence(2);
		monsters.get("Wolf").setSpeed(10);
		monsters.get("Wolf").setOffense(8);
		monsters.get("Wolf").setDefense(8);
		monsters.get("Wolf").setMaxHp(12);
		monsters.get("Wolf").setMaxMp(0);
		monsters.get("Wolf").setHp(12);
		monsters.get("Wolf").setMp(0);
		monsters.get("Wolf").setGp(20);
		monsters.get("Wolf").setXpValue(50);
//		monsters.get("Wolf").addImmunity(conditionToAdd);
		monsters.get("Wolf").addAction(attacks.get("Wolf Bite"));
		//monsters.get("Wolf").setImageFile("res/img/Wolf.png");
		//monsters.get("Wolf").setImageAlt("res/img/Wolf2.png");
		
		monsters.put("Goblin", new Creature("Goblin"));
		monsters.get("Goblin").setStrength(6);
		monsters.get("Goblin").setIntelligence(6);
		monsters.get("Goblin").setSpeed(10);
		monsters.get("Goblin").setOffense(12);
		monsters.get("Goblin").setDefense(10);
		monsters.get("Goblin").setMaxHp(25);
		monsters.get("Goblin").setMaxMp(0);
		monsters.get("Goblin").setHp(25);
		monsters.get("Goblin").setMp(0);
		monsters.get("Goblin").setGp(40);
		monsters.get("Goblin").setXpValue(100);
		monsters.get("Goblin").addImmunity(conditions.get("Poison"));
		monsters.get("Goblin").addAction(attacks.get("Shortsword"));
//		monsters.get("Goblin").addAction(actionToAdd);
		//monsters.get("Goblin").setImageFile("res/img/Goblin.png");
		//monsters.get("Goblin").setImageAlt("res/img/Goblin2.png");
		
		monsters.put("Zombie", new Creature("Zombie"));
		monsters.get("Zombie").setStrength(10);
		monsters.get("Zombie").setIntelligence(2);
		monsters.get("Zombie").setSpeed(20);
		monsters.get("Zombie").setOffense(10);
		monsters.get("Zombie").setDefense(14);
		monsters.get("Zombie").setMaxHp(40);
		monsters.get("Zombie").setMaxMp(0);
		monsters.get("Zombie").setHp(40);
		monsters.get("Zombie").setMp(0);
		monsters.get("Zombie").setGp(50);
		monsters.get("Zombie").setXpValue(150);
		monsters.get("Zombie").addImmunity(conditions.get("Poison"));
		monsters.get("Zombie").addImmunity(conditions.get("Slow"));
		monsters.get("Zombie").addAction(attacks.get("Zombie Grab"));
		//monsters.get("Zombie").setImageFile("res/img/Zombie.png");
		//monsters.get("Zombie").setImageAlt("res/img/Zombie2.png");
		
		monsters.put("Ogre", new Creature("Ogre"));
		monsters.get("Ogre").setStrength(15);
		monsters.get("Ogre").setIntelligence(6);
		monsters.get("Ogre").setSpeed(10);
		monsters.get("Ogre").setOffense(15);
		monsters.get("Ogre").setDefense(20);
		monsters.get("Ogre").setMaxHp(60);
		monsters.get("Ogre").setMaxMp(30);
		monsters.get("Ogre").setHp(60);
		monsters.get("Ogre").setMp(30);
		monsters.get("Ogre").setGp(120);
		monsters.get("Ogre").setXpValue(250);
		monsters.get("Ogre").addImmunity(conditions.get("Slow"));
		monsters.get("Ogre").addAction(attacks.get("Club"));
		monsters.get("Ogre").addAction(skills.get("Trip"));
		monsters.get("Ogre").addAction(spells.get("Slow"));
		//monsters.get("Ogre").setImageFile("res/img/Ogre.png");
		//monsters.get("Ogre").setImageAlt("res/img/Ogre2.png");
		
		actions.putAll(attacks);
		actions.putAll(skills);
		actions.putAll(spells);
	}

	public static Collection<Condition> getConditions() {return conditions.values();}
	public static Collection<Action> getActions() {return actions.values();}
	public static Collection<Action> getAttacks() {return attacks.values();}
	public static Collection<Action> getSkills() {return skills.values();}
	public static Collection<Action> getSpells() {return spells.values();}
	public static Collection<HeroType> getHeroTypes() {return heroTypes.values();}
	public static Collection<Creature> getMonsters() {return monsters.values();}
	
	public static Condition getCondition(String c) {return conditions.get(c);}
	public static Action getAction(String a) {return actions.get(a);}
	public static Action getAttack(String a) {return attacks.get(a);}
	public static Action getSkill(String sk) {return skills.get(sk);}
	public static Action getSpell(String sp) {return spells.get(sp);}
	public static HeroType getHeroType(String ht) {return heroTypes.get(ht);}
	public static Creature getMonster(String m) {return new Creature(monsters.get(m));}
}

