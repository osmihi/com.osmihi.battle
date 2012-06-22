/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * 
 * -------------------
 * GUI_PartyScreen.java
 * -------------------
 * This class is the GUI screen of the game where users assemble a battle party consisting of some of
 * the characters that they've created. Right now it's just a stub basically, since characted creation is
 * not implemented yet.  
 * 
 *********************************************************************************************************/

package com.osmihi.battle.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.osmihi.battle.game.Game;
import com.osmihi.battle.realm.Census;
import com.osmihi.battle.realm.Creature;

public class GUI_PartyScreen extends GUI_GenericWindow {
	private static final long serialVersionUID = -7702947718399321418L;

	private Game game;
	private boolean done = false;
	
	private JButton btn1;
	private JButton btn2;
	
	public GUI_PartyScreen(Game g) {
		super();
		game = g;
	}
	
	protected void populateWindow() {
		setTitle("Manage Adventuring Party");
		
		btn1 = new JButton("Assemble Hero Party (stock)");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPartyStuff();
				btn1.setEnabled(false);
				btn2.setEnabled(true);
			}
		});
		
		btn2 = new JButton("Done");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done = true;
				setVisible(false);
			}
		});
		
		mainPanel.setLayout(new GridLayout(3,3,10,10));
		for (int i = 0; i < 3; i++) {mainPanel.add(new JLabel(""));}
		mainPanel.add(btn1, BorderLayout.CENTER);
		mainPanel.add(new JLabel(""));
		mainPanel.add(btn2, BorderLayout.CENTER);
		for (int i = 0; i < 3; i++) {mainPanel.add(new JLabel(""));}
	}
	
	public Game getGame() {return game;}
	public boolean done() {return done;}
	
	private void doPartyStuff() {
		for (Creature c : game.getPlayerCharacters()) {
			game.addHeroToTeam(c);
		}
		
		Creature foe1 = Census.getMonster("Wolf");
		Creature foe2 = Census.getMonster("Wolf");
		Creature foe3 = Census.getMonster("Goblin");
		Creature foe4 = Census.getMonster("Zombie");
		Creature foe5 = Census.getMonster("Ogre");
		game.addEnemy(foe1);
		game.addEnemy(foe2);
		game.addEnemy(foe3);
		game.addEnemy(foe4);
		game.addEnemy(foe5);
	}
}
