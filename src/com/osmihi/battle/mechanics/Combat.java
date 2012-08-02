/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://stackoverflow.com/questions/683041/java-how-do-i-use-a-priorityqueue
 *
 * --------------
 * Combat.java
 * --------------
 * The Combat class is in charge of combat-related functions for the battle portion of the game. When an
 * instance of Combat is created, it is passed heroTeam and enemyTeam collections. The way that combat
 * works is in a series of rounds, which consist of turns. Every round, "initiative" values are calculated
 * for Creatures based on their speed attribute, determining the order in which they will take turns. Then
 * each Creature takes its turn until the round is over, at which point a new round will begin again. This
 * process repeats until all Creatures on either the heroTeam or the enemyTeam are killed (their hit points
 * drop to 0). If the heroes win the battle, they are rewarded with the experience points (xp) and gold
 * pieces (gp) of the defeated enemies. 
 * 
 * On a hero's turn, the player is prompted to choose a target and an action. If it is an enemy's turn, a 
 * random target is chosen from the living heroes and a random action is chosen from that particular enemy's 
 * available actions. When an action and target are determined, the incident() method is called and passed
 * the attacker, action, and target. Inside this method, the success or failure of the action and any 
 * damage, magic point cost, resulting conditions, etc are determined.
 * 
 * Throughout this process, various messages and information are sent to the battle GUI so it can display
 * what is going on to the user.
 * 
 *********************************************************************************************************/

package com.osmihi.battle.mechanics;

import com.osmihi.battle.realm.*;
import com.osmihi.battle.ui.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class Combat {
	private GUI_BattleScreen gui_bs;
	
	private InitiativeComparator ic;
	private PriorityQueue<Creature> turnOrder;
	private Collection<Creature> heroes;
	private Collection<Creature> enemies;
	private Collection<Creature> deadHeroes;
	private Collection<Creature> deadEnemies;
	private HashSet<Creature> combatants;
	private Collection<Creature> winner;
	
	private int roundNum;

	private final int TICK = 1000;
	
	public Combat(Collection<Creature> heroTeam, Collection<Creature> enemyTeam) {
		// Initialize data structures and values		
		ic = new InitiativeComparator();
		turnOrder = new PriorityQueue<Creature>(20,ic);
		heroes = heroTeam;
		enemies = enemyTeam;
		deadHeroes = new HashSet<Creature>();
		deadEnemies = new HashSet<Creature>();
		combatants = new HashSet<Creature>();
		combatants.addAll(heroes);
		combatants.addAll(enemies);
		
		gui_bs = new GUI_BattleScreen(this);
		
		roundNum = 0;
		
		do {// Roll initiative and add all combatants to turn queue before each round
			for (Creature c : combatants) {rollInitiative(c);}
			turnOrder.addAll(combatants);
			roundNum++;
		} while (round());

		endBattle();
	}
	
	public int rollInitiative(Creature cre) {
		int init = Generator.random(1, cre.getSpeed());
		cre.setInitiative(init);
		return init;
	}
	
	private boolean round() {
		do {
			//Generator.delay(TICK); / no longer needed, since delay happens ONLY for enemies, and in the turn method
			//gui_bs.animate(combatants,2000); // just to watch animations
		} while (!enemies.isEmpty() && !heroes.isEmpty() && turn());
		
		if (enemies.isEmpty() || heroes.isEmpty()) {
			winner = (!heroes.isEmpty()) ? heroes : enemies;
		}
		return !(enemies.isEmpty() || heroes.isEmpty());
	}
	
	private boolean turn() {
		Creature c = turnOrder.poll();
		if (c == null) {return false;}
		int init = c.getInitiative();
		
		try {
			takeTurn(c);
		} catch (java.util.ConcurrentModificationException e) {}
		
		if (init > 20 && c.getHp() > 0) {
			int newInit = Generator.random(init - 20);
			c.setInitiative(newInit);
			turnOrder.add(c);
		}
		return true;
	}
	
	private void takeTurn(Creature c) {
		Creature rTarget = null;
		Action rAction = null;
		boolean enemyTurn = false;
		if (c.getHp() > 0) {
			for (Condition con : c.getStatus().keySet()) {sustainStatus(c,con);}
			for (Condition con : c.getStatus().keySet()) {if (c.getStatusDuration(con) == 0) {endStatus(c,con);}}
			if (checkLife(c)) {
				if (heroes.contains(c)) {
					// Hero prompt / choice here
					Map<Creature,Action> rChoice = gui_bs.turnView(c);
					for (Creature tar : rChoice.keySet()) {rTarget = tar;}
					for (Action actio : rChoice.values()) {rAction = actio;}
					gui_bs.turnView();
				} else {
					// Enemy AI here
					enemyTurn = true;
					rAction = Generator.random(c.getActions());
					while (rTarget == null) {rTarget = Generator.random(heroes);}						
				}
				incident(c,rAction,rTarget);
				gui_bs.animate(c);
				checkLife(c);
			}
			checkLife(rTarget);
			if (enemyTurn) {Generator.delay(TICK);}
		}
	}
	
	private boolean checkLife(Creature c) {
		if (c != null && c.getHp() <= 0) {
			if (heroes.contains(c)) {
				heroes.remove(c);
				deadHeroes.add(c);
			} else if (enemies.contains(c)) {
				enemies.remove(c);
				deadEnemies.add(c);
			}
			combatants.remove(c);
			setMessage(c.getName() + " has perished.");
			return false;
		}
		return true;
	}
	
	private void endBattle() {
		if (winner == heroes) {
			int totalGp = 0;
			int totalXp = 0;
			int gpEa = 0;
			int xpEa = 0;
			
			setMessage("Hooray! The heroes are victorious!\n");
			gui_bs.animate(heroes,12);
			heroes.addAll(deadHeroes);
			
			for (Creature e : deadEnemies) {
				totalGp += e.getGp();
				totalXp += e.getXpValue();
			}
			
			gpEa = totalGp / (heroes.size());
			xpEa = totalXp / (heroes.size());
			
			for (Creature h : heroes) {
				h.getStatus().clear();
				h.setHp(h.getMaxHp());
				h.setMp(h.getMaxMp());
				
				h.setGp( h.getGp() + gpEa );
				((Hero)h).setXp( ((Hero)h).getXp() + xpEa );
			}
			setMessage("Heroes receive " + gpEa + " gold pieces each.");
			setMessage("Heroes earn " + xpEa + " experience points each.");
			
		}
		else {
			setMessage("Alas, the heroes have fallen.\n");
			gui_bs.animate(enemies,12);
		}
		
		//heroes.clear();
		deadHeroes.clear();
		enemies.clear();
		deadEnemies.clear();
		combatants.clear();
		Generator.delay(TICK * 3);
		gui_bs.dispose();
	}
	
	private void incident(Creature actor, Action act, Creature target) {
		double hitRoll = 0;
		double defRoll = 0;
		int dam = 0;
		int hitStat = 0;
		int defStat = 0;
		int damStat = 0;
		boolean lands = ( act.getStatusChance() >= Generator.random(10) );
		
		if (actor.hasAction(act) && actor.getMp() >= act.getMpCost()) {
			actor.loseMp(act.getMpCost());
			
			setMessage(actor.getName() + " " + act.getVerb() + " " + act.getName() + " " + act.getPreposition() + " " + target.getName() + "!");
			gui_bs.refresh(actor);
			
			switch (act.getType()) {
				case ATTACK:
					hitStat = actor.getOffense();
					defStat = target.getSpeed();
					damStat = actor.getStrength();
					break;
				case SKILL:
					hitStat = ( actor.getIntelligence() + actor.getSpeed() ) / 2;
					defStat = ( target.getIntelligence() + target.getSpeed() ) / 2;
					damStat = ( actor.getIntelligence() + actor.getSpeed() ) / 2;
					break;
				case SPELL:
					hitStat = actor.getIntelligence();
					defStat = target.getIntelligence();
					damStat = actor.getIntelligence();
					break;
				default:
					break;
			}
			
			hitRoll = Generator.random(hitStat) * (act.getSuccessChance() / 10.0);
			defRoll = Generator.random(defStat) * ( (10 - act.getSuccessChance()) / 10.0);
			if (damStat < 1) {damStat = 1;}		
			dam = Generator.random( act.getMinDamage(), act.getMaxDamage() - act.getMinDamage() );
			dam = dam + ( damStat / 4 ) - ( target.getDefense() / 4 );
			if ((dam < 1 && act.getType() == Action.ActionType.ATTACK)) {dam = 1;}
			if (dam < 0) {dam = 0;}
			if (actor == target) {
				hitRoll = Generator.random(10);
				defRoll = ( (10 - act.getSuccessChance()) / 10.0 );
				dam = 0;
			}
			
			if ((int)hitRoll > (int)defRoll) {
				setMessage(actor.getName() + "'s " + act.getName() + " hits " + target.getName() + " for " + dam + " damage.");
				target.loseHp(dam);
				if (lands && target.getHp() > 0 && !(target.getImmunities().contains(act.getStatusEffect()))) {
					setMessage(target.getName() + " is affected by " + act.getStatusEffect().getName() + ".");
					beginStatus(target, act.getStatusEffect());
				}
			} else {setMessage(actor.getName() + "'s " + act.getName() + " misses " + target.getName() + ".");}
			gui_bs.refresh(target);
		}
	}

	public void beginStatus(Creature cre, Condition con) {
		if (!cre.hasStatus(con)) {
			cre.addStatus(con, con.getDuration());
			cre.setStrength(cre.getStrength() + con.getStrengthMod());
			cre.setIntelligence(cre.getIntelligence() + con.getIntelligenceMod());
			cre.setSpeed(cre.getSpeed() + con.getSpeedMod());
			cre.setOffense(cre.getOffense() + con.getOffenseMod());
			cre.setDefense(cre.getDefense() + con.getDefenseMod());
		}
	}

	public void sustainStatus(Creature cre, Condition con) {
		if (cre.hasStatus(con)) {
			int left = cre.getStatus().get(con).intValue();
			if (left <= 0) {
				endStatus(cre,con);
			} else{
				int rDam = Generator.random(con.getRoundDam());
				int rDrain = Generator.random(con.getRoundDrain());
				cre.loseHp(rDam);
				cre.loseMp(rDrain);
				String msg = cre.getName();
				if (rDam >= 0) {msg += " loses ";}
				else {msg += " gains ";}
				msg += rDam + " hp ";
				if (rDrain != 0) {
					msg += "and mp is affected by " + rDrain + " ";
				}
				msg += "due to " + con.getName() + ".";
				if (rDam > 0 || rDrain != 0) setMessage(msg);
				cre.addStatus(con, new Integer( left - 1 ) );
			}
		}
	}

	public void endStatus(Creature cre, Condition con) {
		if (cre.hasStatus(con)) {
			cre.setStrength(cre.getStrength() - con.getStrengthMod());
			cre.setIntelligence(cre.getIntelligence() - con.getIntelligenceMod());
			cre.setSpeed(cre.getSpeed() - con.getSpeedMod());
			cre.setOffense(cre.getOffense() - con.getOffenseMod());
			cre.setDefense(cre.getDefense() - con.getDefenseMod());
			cre.dropStatus(con);
			setMessage(cre.getName() + " is no longer affected by " + con.getName() + ".");
		}
	}

	private class InitiativeComparator implements Comparator<Creature> {
		// This class is used when adding Creatures to the priority queue
		@Override
		public int compare(Creature cre1, Creature cre2) {
			return cre2.getInitiative() - cre1.getInitiative();
		}
	}
	
	// "Getter" methods
	public PriorityQueue<Creature> getTurnOrder() {return turnOrder;}
	public Collection<Creature> getHeroes() {return heroes;}
	public Collection<Creature> getEnemies() {return enemies;}
	public Collection<Creature> getDeadHeroes() {return deadHeroes;}
	public Collection<Creature> getDeadEnemies() {return deadEnemies;}
	public HashSet<Creature> getCombatants() {return combatants;}
	public Collection<Creature> getWinner() {return winner;}
	public int getRoundNum() {return roundNum;}
	
	public void setMessage(String m) {
		String r = "";
		int sp = 3 - ("" + getRoundNum()).length();
		for (int i = 0; i < sp; i++) {r += " ";}
		r +=  getRoundNum() + " | " + m;
		gui_bs.battleText(r);
	}
}
