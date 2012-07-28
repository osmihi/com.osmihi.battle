/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 *  
 * ---------
 * Game.java
 * ---------
 * This is the "driver" class that contains the main() method to run the game. The main method first 
 * calls Census.populate() which creates the creature/action/etc objects required for the game. Then an
 * instance of Game is created and passed to an instance of the main GUI screen. The Game class contains 
 * collections keeping track of player characters, the current hero team, and current enemy team.  
 * 
 *********************************************************************************************************/

package com.osmihi.battle.game;

import com.osmihi.battle.realm.*;
import com.osmihi.battle.ui.GUI_MainScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Game {
	private Collection<Creature> playerCharacters;
	
	private Collection<Creature> hTeam;
	private Collection<Creature> eTeam;
	
	public Game() {
		Census.populate();
		playerCharacters = new HashSet<Creature>();
		hTeam = new ArrayList<Creature>();
		eTeam = new ArrayList<Creature>();
		
		GUI_MainScreen gui_ms = new GUI_MainScreen(this);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	}
	
	public Collection<Creature> getPlayerCharacters() {return playerCharacters;}
	public Collection<Creature> getHeroTeam() {return hTeam;}
	public Collection<Creature> getEnemyTeam() {return eTeam;}
	
	public void addHero(Creature c) {
		playerCharacters.add(c);
	}
	
	public void addHeroToTeam(Creature c) {
		hTeam.add(c);
	}
	
	public void dropHeroFromTeam(Creature c) {
		hTeam.remove(c);
	}
	
	public void addEnemy(Creature c) {
		eTeam.add(c);
	}
	
	public void clearEnemies() {
		eTeam.clear();
	}
	
}
