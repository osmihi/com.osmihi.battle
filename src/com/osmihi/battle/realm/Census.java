package com.osmihi.battle.realm;
/**************************************************************************************************
 * Census.java
 * 
 * ref:
 * - Instructor Dorin suggested I use Hashtables to store the various Creatures, etc 
 * 
 **************************************************************************************************/

import java.util.Collection;
import java.util.Hashtable;


public class Census {
	// The Census class instantiates all the hero types, creature types, and actions. 
	// Is it better to divide this up and put into static methods in the corresponding class defs?
	// for example, HeroType.getHeroTypeList(), HeroType.getHeroType(), or maybe just HeroType.ARCHER, etc
	// that almost makes more sense...
	
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
		conditions.get("Poison").setRoundDam(2);
		conditions.get("Poison").setDuration(4);
		
		conditions.put("Slow", new Condition("Slow"));
		conditions.get("Slow").setSpeedMod(-6);
		conditions.get("Slow").setDuration(2);
		
		conditions.put("Stone", new Condition("Stone"));
		conditions.get("Stone").setSpeedMod(-50);
		conditions.get("Stone").setDefenseMod(50);
		conditions.get("Stone").setDuration(3);
		
		conditions.put("Haste", new Condition("Haste"));
		conditions.get("Haste").setSpeedMod(20);
		conditions.get("Haste").setDefenseMod(5);
		conditions.get("Haste").setDuration(3);
		
		conditions.put("Might", new Condition("Might"));
		conditions.get("Might").setStrengthMod(20);
		conditions.get("Might").setIntelligenceMod(-5);
		conditions.get("Might").setDuration(3);
		
		// Actions
		// Attacks, Skills, Spells
		actions.putAll(attacks);
		actions.putAll(skills);
		actions.putAll(spells);

		attacks.put("Club", new Action("Club", Action.ActionType.ATTACK));
		attacks.get("Club").setMpCost(0);
		attacks.get("Club").setSuccessChance(8);
		attacks.get("Club").setMinDamage(4);
		attacks.get("Club").setMaxDamage(14);
		attacks.get("Club").setStatusChance(0);
		attacks.get("Club").setStatusEffect(null);
		
		attacks.put("Knife", new Action("Knife", Action.ActionType.ATTACK));
		attacks.get("Knife").setMpCost(0);
		attacks.get("Knife").setSuccessChance(7);
		attacks.get("Knife").setMinDamage(0);
		attacks.get("Knife").setMaxDamage(6);
		attacks.get("Knife").setStatusChance(0);
		attacks.get("Knife").setStatusEffect(null);
		
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
		attacks.get("Shortbow").setMinDamage(0);
		attacks.get("Shortbow").setMaxDamage(12);
		attacks.get("Shortbow").setStatusChance(0);
		attacks.get("Shortbow").setStatusEffect(null);
		
		attacks.put("Shield Bash", new Action("Shield Bash", Action.ActionType.ATTACK));
		attacks.get("Shield Bash").setMpCost(0);
		attacks.get("Shield Bash").setSuccessChance(5);
		attacks.get("Shield Bash").setMinDamage(0);
		attacks.get("Shield Bash").setMaxDamage(6);
		attacks.get("Shield Bash").setStatusChance(8);
		attacks.get("Shield Bash").setStatusEffect(conditions.get("Slow"));
		
		
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
		skills.get("Rage").setStatusEffect(conditions.get("Might"));
		
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
		spells.get("Haste").setSuccessChance(6);
		spells.get("Haste").setMinDamage(0);
		spells.get("Haste").setMaxDamage(0);
		spells.get("Haste").setStatusChance(10);
		spells.get("Haste").setStatusEffect(conditions.get("Haste"));
		
		spells.put("Bolt", new Action("Bolt", Action.ActionType.SPELL));
		spells.get("Bolt").setMpCost(4);
		spells.get("Bolt").setSuccessChance(8);
		spells.get("Bolt").setMinDamage(6);
		spells.get("Bolt").setMaxDamage(12);
		spells.get("Bolt").setStatusChance(0);
		spells.get("Bolt").setStatusEffect(null);
		
		
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
//		add actions here
		heroTypes.get("Barbarian").setImageFile("res/img/Barbarian.png");
		
		heroTypes.put("Archer", new HeroType("Archer"));
		heroTypes.get("Archer").setStrengthMod(0);
		heroTypes.get("Archer").setIntelligenceMod(0);
		heroTypes.get("Archer").setSpeedMod(5);
		heroTypes.get("Archer").setOffenseMod(0);
		heroTypes.get("Archer").setDefenseMod(10);
		heroTypes.get("Archer").setHpUnit(16);
		heroTypes.get("Archer").setMpUnit(0);
		heroTypes.get("Archer").setGp(80);
//		heroTypes.get("Archer").addImmunity(conditionToAdd);
//		add actions here
		heroTypes.get("Archer").setImageFile("res/img/Archer.png");
		
		heroTypes.put("Ninja", new HeroType("Ninja"));
		heroTypes.get("Ninja").setStrengthMod(0);
		heroTypes.get("Ninja").setIntelligenceMod(0);
		heroTypes.get("Ninja").setSpeedMod(10);
		heroTypes.get("Ninja").setOffenseMod(5);
		heroTypes.get("Ninja").setDefenseMod(0);
		heroTypes.get("Ninja").setHpUnit(16);
		heroTypes.get("Ninja").setMpUnit(4);
		heroTypes.get("Ninja").setGp(60);
//		heroTypes.get("Ninja").addImmunity(conditionToAdd);
//		add actions here
		heroTypes.get("Ninja").setImageFile("res/img/Ninja.png");
		
		heroTypes.put("Thief", new HeroType("Thief"));
		heroTypes.get("Thief").setStrengthMod(-6);
		heroTypes.get("Thief").setIntelligenceMod(8);
		heroTypes.get("Thief").setSpeedMod(10);
		heroTypes.get("Thief").setOffenseMod(-7);
		heroTypes.get("Thief").setDefenseMod(10);
		heroTypes.get("Thief").setHpUnit(12);
		heroTypes.get("Thief").setMpUnit(6);
		heroTypes.get("Thief").setGp(200);
//		heroTypes.get("Thief").addImmunity(conditionToAdd);
//		add actions here
		heroTypes.get("Thief").setImageFile("res/img/Thief.png");
		
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
//		add actions here
		heroTypes.get("Knight").setImageFile("res/img/Knight.png");

		heroTypes.put("Wizard", new HeroType("Wizard"));
		heroTypes.get("Wizard").setStrengthMod(-7);
		heroTypes.get("Wizard").setIntelligenceMod(17);
		heroTypes.get("Wizard").setSpeedMod(7);
		heroTypes.get("Wizard").setOffenseMod(-7);
		heroTypes.get("Wizard").setDefenseMod(5);
		heroTypes.get("Wizard").setHpUnit(9);
		heroTypes.get("Wizard").setMpUnit(18);
		heroTypes.get("Wizard").setGp(120);
//		heroTypes.get("Wizard").addImmunity(conditionToAdd);
//		add actions here
		heroTypes.get("Wizard").setImageFile("res/img/Wizard.png");

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
		monsters.get("Wolf").setImageFile("res/img/Wolf.png");
		
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
		monsters.get("Goblin").setImageFile("res/img/Goblin.png");
		
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
		monsters.get("Zombie").setImageFile("res/img/Zombie.png");
		
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
		monsters.get("Ogre").addImmunity(conditions.get("Poison"));
		monsters.get("Ogre").addImmunity(conditions.get("Slow"));
		monsters.get("Ogre").addAction(attacks.get("Club"));
		monsters.get("Ogre").addAction(skills.get("Trip"));
		monsters.get("Ogre").setImageFile("res/img/Ogre.png");
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

