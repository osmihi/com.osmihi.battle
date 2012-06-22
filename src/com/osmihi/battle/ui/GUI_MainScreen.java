/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://www.javamex.com/tutorials/threads/invokelater.shtml
 * http://stackoverflow.com/questions/2545214/how-to-set-a-transparent-background-of-jpanel
 * 
 * -------------------
 * GUI_MainScreen.java
 * -------------------
 * This class is the opening GUI screen of the game. It is basically just a gateway to the other screens
 * in the game. It includes buttons to access the other screens and methods to create instances of those
 * screens and access them.
 * 
 *********************************************************************************************************/

package com.osmihi.battle.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import com.osmihi.battle.game.Game;
import com.osmihi.battle.mechanics.Combat;

public class GUI_MainScreen extends GUI_GenericWindow {
	private static final long serialVersionUID = 4006379455597743657L;
	
	private Game game;
	private GUI_CharScreen gui_cs;
	private GUI_PartyScreen gui_ps;
	
	private JPanel buttonPanel;
	private JButton charScreenButton;
	private JButton partyScreenButton;
	private JButton battleScreenButton;

	public GUI_MainScreen(Game g) {
		super();
		game = g;
	}
	
	protected void populateWindow() {
		charScreenButton = new JButton("Create / Edit Characters");
		charScreenButton.addActionListener(new CharScreenListener());
		partyScreenButton = new JButton("Manage Adventuring Party");
		partyScreenButton.addActionListener(new PartyScreenListener());
		partyScreenButton.setEnabled(false);
		battleScreenButton = new JButton("Battle!");
		battleScreenButton.addActionListener(new BattleScreenListener());
		battleScreenButton.setEnabled(false);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1,10,10));
		buttonPanel.setOpaque(false);
		buttonPanel.add(charScreenButton);
		buttonPanel.add(partyScreenButton);
		buttonPanel.add(battleScreenButton);
		
		mainPanel.setLayout(new GridLayout(3,3,10,10));
		for (int i = 0; i < 4; i++) {mainPanel.add(new JLabel(""));}
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		for (int i = 0; i < 4; i++) {mainPanel.add(new JLabel(""));}
		
		setTitle("Adventure Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class CharScreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gui_cs = new GUI_CharScreen(game);
			partyScreenButton.setEnabled(true);
		}
	}
	
	private class PartyScreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (gui_cs != null && gui_cs.done() && gui_cs.getGame() != null) {
				game = gui_cs.getGame();
				gui_cs.dispose();

				gui_ps = new GUI_PartyScreen(game);
				battleScreenButton.setEnabled(true);
			} else {System.out.println("Create characters first.");}
		}
	}
	
	private class BattleScreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (gui_ps != null && gui_ps.done() && gui_ps.getGame() != null) {
				game = gui_ps.getGame();
				gui_ps.dispose();

				//do the battle!
				Thread battleThread = new Thread() {
					public void run() {
				    	  Combat bc = new Combat(game.getHeroTeam(), game.getEnemyTeam());
				      }
				};
				battleThread.start();
				
			} else {System.out.println("Create party first.");}
		}
	}
	
}
