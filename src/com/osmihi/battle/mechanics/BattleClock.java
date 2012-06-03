package com.osmihi.battle.mechanics;
//
// References:
// http://stackoverflow.com/questions/683041/java-how-do-i-use-a-priorityqueue
//

import com.osmihi.battle.realm.*;
import com.osmihi.battle.ui.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BattleClock {
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

	/*
	 *  start of a battle:
	 *  make data structure containing order of turns
	 *  
	 *  a person's turn:
	 *  remove any conditions from their condition queue where the duration = 0	
	 *  	cre.endStatus(c), where c is the condition whose duration has reached 0
	 *  apply any other conditions they have:
	 *  	for (Condition c : cre.getStatus()) {cre.sustainStatus(c);} *** this will also decrement the duration
	 *  ask for an action
	 *  apply the action
	 *  go to next person's turn
	 *  
	 *  end of battle:
	 *  expire any conditions on characters.
	 *  restore hp/mp?
	 *  grant experience
	 *  
	 */
	
	public BattleClock(Collection<Creature> heroTeam, Collection<Creature> enemyTeam) {
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
			for (Creature c : combatants) {c.rollInitiative();}
			turnOrder.addAll(combatants);
			roundNum++;
			gui_bs.setBattleText(battleMsg(0) + "\n");
		} while (round());

		if (winner == heroes) {gui_bs.setBattleText("Hooray! The heroes are victorious!\n");}
		else {gui_bs.setBattleText("Alas, the heroes have fallen.\n");}
	}
	
	private boolean round() {
		do {
			Generator.delay(1500);
		} while (turn());
		
		boolean hDead = true;
		boolean eDead = true;
		for (Creature h : heroes) {
			if (h.getHp() > 0) {
				hDead = false;
			} else {
				deadHeroes.add(h);
			}
		}
		for (Creature e : enemies) {
			if (e.getHp() > 0) {
				eDead = false;
			} else {
				deadEnemies.add(e);
			}
		}
		for (Creature h : deadHeroes) {
			heroes.remove(h);
			combatants.remove(h);
		}
		for (Creature e : deadEnemies) {
			enemies.remove(e);
			combatants.remove(e);
		}
		if (eDead || hDead) {
			winner = (!hDead) ? heroes : enemies;
		}
		return !(hDead || eDead);
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
		if (c.getHp() > 0) {
			gui_bs.setBattleText("\n[" + c.getInitiative() + "] " + c.getName() + "'s turn.\n");
			for (Condition con : c.getStatus().keySet()) {c.sustainStatus(con);}
			for (Condition con : c.getStatus().keySet()) {if (c.getStatusDuration(con) == 0) {c.endStatus(con);}}
			Action rAction;
			Creature rTarget;
			if (heroes.contains(c)) {
				// Hero prompt / choice here
				do {
					rAction = Generator.random(c.getActions());
				} while (rAction.getType() == Action.ActionType.SPELL && c.getMp() < rAction.getMpCost());
				rTarget = Generator.random(enemies);
			} else {
				// Enemy AI here
				rAction = Generator.random(c.getActions());
				rTarget = Generator.random(heroes);						
			}
			c.act(rAction,rTarget);
			gui_bs.setBattleText(c.getMessage());
			gui_bs.refresh(c);
			gui_bs.setBattleText(rTarget.getMessage());
			gui_bs.refresh(rTarget);
			//gui_bs.setBattleText("\n" + battleMsg(1) + "\n");
		}
	}
	
	private String battleMsg(int msg) {
		// Header
		String battleMsg = "";
		String roundHead = "========= Round " + roundNum + " =========";
		String roundLine = Generator.txtLine('=', roundHead);
		battleMsg += "\n" + roundLine + "\n" + roundHead + "\n" + roundLine + "\n";
		if (msg == 0) {return battleMsg;}
		else {battleMsg = "";}
		for (Creature c : heroes) {
			String cStr = "";
			cStr += c.getName() + ": " + Generator.txtLine(' ', 10 - c.getName().length());
			cStr += Generator.txtLine(' ', ( 3 - ("" + c.getHp()).length()) ) + c.getHp() + " /" + Generator.txtLine(' ', ( 3 - ("" + c.getMaxHp()).length()) ) + c.getMaxHp() + " hp \t";
			battleMsg += cStr;
		}
		battleMsg += "\n";
		for (Creature c : enemies) {
			String cStr = "";
			cStr += c.getName() + ": " + Generator.txtLine(' ', 10 - c.getName().length());
			cStr += Generator.txtLine(' ', ( 3 - ("" + c.getHp()).length()) ) + c.getHp() + " /" + Generator.txtLine(' ', ( 3 - ("" + c.getMaxHp()).length()) ) + c.getMaxHp() + " hp \t";
			battleMsg += cStr;
		}
		battleMsg += "\n" + roundLine;
		return battleMsg;
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
		
}
