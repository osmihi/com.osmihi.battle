package com.osmihi.battle.game;

import com.osmihi.battle.realm.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Game {
	private Collection<Creature> playerCharacters;
	//private Collection<HeroParty> heroParties;
	
	private Collection<Creature> hTeam;
	private Collection<Creature> eTeam;
	
	public Game() {
		Census.populate();
		playerCharacters = new HashSet<Creature>();
		//heroParties = new HashSet<HeroParty>();
		hTeam = new ArrayList<Creature>();
		eTeam = new ArrayList<Creature>();
		
		//Combat bc = new Combat(hTeam, eTeam);
	}
	
	public Collection<Creature> getPlayerCharacters() {return playerCharacters;}
	public Collection<Creature> getHeroTeam() {return hTeam;}
	public Collection<Creature> getEnemyTeam() {return eTeam;}
	
	public void addHero(Creature c) {
		playerCharacters.add(c);
	}
	
	public void addHeroToTeam(Creature c) {
		// TODO temporary until we implement HeroParty class
		hTeam.add(c);
	}
	
	public void addEnemy(Creature c) {
		eTeam.add(c);
	}
}
