/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * 
 * -------------------
 * GUI_CharScreen.java
 * -------------------
 * This class is the GUI screen of the game where users create new Heroes. Right now it just creates  
 * some pre-fab hard-coded heroes, since character creation is not implemented yet. In the future, it will
 * display information like a Hero's attributes and actions etc, to be used when creating or leveling up
 * a Hero.  
 * 
 *********************************************************************************************************/

package com.osmihi.battle.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.osmihi.battle.game.Game;
import com.osmihi.battle.realm.Census;
import com.osmihi.battle.realm.Creature;
import com.osmihi.battle.realm.Hero;

public class GUI_CharScreen extends GUI_GenericWindow {
	private static final long serialVersionUID = -7107347498741097308L;

	private Game game;
	private boolean done = false;
	
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
	public GUI_CharScreen(Game g) {
		super();
		game = g;
	}
	
	protected void populateWindow() {
		setTitle("Create and Edit Characters");
		
		btn1 = new JButton("Make characters (stock)");
		btn2 = new JButton("Print characters");
		btn3 = new JButton("Done");
		//btn3.setEnabled(false);
		
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeChars();
				btn1.setEnabled(false);
				btn2.setEnabled(true);
				btn3.setEnabled(true);
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Creature h : game.getPlayerCharacters()) {System.out.println(h);}
			}
		});
		
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done = true;
				setVisible(false);
			}
		});
		
		mainPanel.setLayout(new GridLayout(3,3,10,10));
		for (int i = 0; i < 3; i++) {mainPanel.add(new JLabel(""));}
		mainPanel.add(btn1);
		mainPanel.add(btn2);
		mainPanel.add(btn3);
		for (int i = 0; i < 3; i++) {mainPanel.add(new JLabel(""));}
	}
	
	public Game getGame() {return game;}
	public boolean done() {return done;}
	
	private void makeChars() {
		Hero hero1 = new Hero("Ugg", Census.getHeroType("Barbarian"));
		hero1.addAction(Census.getAttack("Club"));
		hero1.addAction(Census.getSkill("Rage"));
		game.addHero(hero1);
		
		Hero hero2 = new Hero("Art", Census.getHeroType("Archer"));
		hero2.addAction(Census.getAttack("Shortbow"));
		hero2.addAction(Census.getSkill("Poison Dart"));
		game.addHero(hero2);
		
		Hero hero3 = new Hero("Takashi", Census.getHeroType("Ninja"));
		hero3.addAction(Census.getAttack("Shortsword"));
		hero3.addAction(Census.getSkill("Trip"));
		hero3.addAction(Census.getSkill("Poison Dart"));
		hero3.addAction(Census.getSpell("Haste"));
		game.addHero(hero3);
		
		Hero hero4 = new Hero("Fuji", Census.getHeroType("Thief"));
		hero4.addAction(Census.getAttack("Knife"));
		hero4.addAction(Census.getSkill("Poison Dart"));
		hero4.addAction(Census.getSkill("Trip"));
		hero4.addAction(Census.getSpell("Haste"));
		hero4.addAction(Census.getSkill("Cripple"));
		hero4.addAction(Census.getSkill("Hide"));
		game.addHero(hero4);
		
		Hero hero5 = new Hero("Virgil", Census.getHeroType("Knight"));
		hero5.addAction(Census.getAttack("Longsword"));
		hero5.addAction(Census.getAttack("Shield Bash"));
		game.addHero(hero5);
		
		Hero hero6 = new Hero("Hedge", Census.getHeroType("Wizard"));
		hero6.addAction(Census.getAttack("Knife"));
		hero6.addAction(Census.getSpell("Slow"));
		hero6.addAction(Census.getSpell("Stone"));
		hero6.addAction(Census.getSpell("Bolt"));
		game.addHero(hero6);
	}
}
