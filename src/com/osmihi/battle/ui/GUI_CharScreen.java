/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://stackoverflow.com/questions/5328405/jpanel-padding-in-java
 * http://docs.oracle.com/javase/1.4.2/docs/api/javax/swing/border/TitledBorder.html
 * http://stackoverflow.com/questions/668952/clearest-way-to-comma-delimit-a-list-java
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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.osmihi.battle.game.Game;
import com.osmihi.battle.mechanics.Generator;
import com.osmihi.battle.realm.Census;
import com.osmihi.battle.realm.Condition;
import com.osmihi.battle.realm.Hero;
import com.osmihi.battle.realm.HeroType;

public class GUI_CharScreen extends GUI_GenericWindow {
	private static final long serialVersionUID = -7107347498741097308L;

	private Game game;
	private boolean done = false;
	private Hero newHero = new Hero("New Hero", Census.getHeroType("Archer"));
	
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
	private JPanel charPanel;
	private JPanel headerPanel;
	private JPanel midPanel;
	private JPanel footerPanel;
	private JPanel ctrlPanel;
	
	private JPanel picPanel;
	private CardLayout pCards;
	
	private JTextField heroName;
	private JTextField heroLvl;
	private JComboBox<String> htChoice;
	private ArrayList<String> alphabetic;
	
	StatRow hpRow;
	StatRow mpRow;
	StatRow xpRow;
	StatRow gpRow;
	StatRow initRow;
	
	StatRow strRow;
	StatRow intRow;
	StatRow spdRow;
	StatRow offRow;
	StatRow defRow;
	
	private JTextArea statusList;
	private JTextArea immunityList;
	
	public GUI_CharScreen(Game g) {
		super();
		game = g;
		refresh();
	}
	
	protected void populateWindow() {
		setTitle("Create and Edit Characters");
	
		makeCharPanel();
		makeCtrlPanel();
		fill.setBackground(colors[2]);
		
		mainPanel.setLayout(new BorderLayout(0,0));
		mainPanel.add(charPanel, BorderLayout.WEST);
		mainPanel.add(fill, BorderLayout.CENTER);
		mainPanel.add(ctrlPanel, BorderLayout.EAST);
	}
	
	public void refresh() {
		if (newHero == null) {return;}
		
		heroName.setText(newHero.getName());
		heroLvl.setText("Level " + newHero.getLevel());
		pCards.show(picPanel, newHero.getHeroType().getName());
		
		hpRow.setValue(newHero.getHp() + " / " + newHero.getMaxHp());
		mpRow.setValue(newHero.getMp() + " / " + newHero.getMaxMp());
		xpRow.setValue(newHero.getXp());
		gpRow.setValue(newHero.getGp());
		initRow.setValue(newHero.getInitiative());
		
		strRow.setValue(newHero.getStrength());
		intRow.setValue(newHero.getIntelligence());
		spdRow.setValue(newHero.getSpeed());
		offRow.setValue(newHero.getOffense());
		defRow.setValue(newHero.getDefense());
		
		statusList.setText("");
		String delim = "";
		for (Condition c : newHero.getStatus().keySet()) {
			statusList.append(delim + c.getName() + "(" + newHero.getStatusDuration(c) + ")");
			delim = ", ";
		}
		
		immunityList.setText("");
		delim = "";
		for (Condition c : newHero.getImmunities()) {
			immunityList.append(delim + c.getName());
			delim = ", ";
		}
	}
	
	private void makeCharPanel() {
		charPanel = new JPanel();
		charPanel.setPreferredSize(new Dimension(480,600));
		charPanel.setLayout(new BorderLayout());
		charPanel.setBorder(new LineBorder(colors[2],4));
		charPanel.setOpaque(false);
		
		// note: makeCharMiddle() must be called before makeCharHeader()
		// this is because the drop-down calls refresh, and refresh references the StatRows, which are null until makeCharMiddle() is called.
		makeCharMiddle();
		makeCharFooter();
		makeCharHeader();
		
		charPanel.add(headerPanel, BorderLayout.NORTH);
		charPanel.add(midPanel, BorderLayout.CENTER);
		charPanel.add(footerPanel, BorderLayout.SOUTH);
	}
	
	private void makeCharHeader() {
		headerPanel = new JPanel();
		headerPanel.setPreferredSize(new Dimension(480,150));
		headerPanel.setOpaque(false);
		headerPanel.setLayout(new BorderLayout());
		
		picPanel = new JPanel();
		picPanel.setBorder(new EmptyBorder(20,20,2,22));
		picPanel.setOpaque(false);
		pCards = new CardLayout();
		picPanel.setLayout(pCards);
		for (HeroType ht : Census.getHeroTypes()) {picPanel.add(new Avatar(ht, true), ht.getName());}
		
		JPanel infoPanel = new JPanel();
		infoPanel.setOpaque(false);
		infoPanel.setPreferredSize(new Dimension(310,150));
		infoPanel.setBorder(new EmptyBorder(20,5,0,15));
		infoPanel.setLayout(new GridLayout(3,1,4,4));
		
		heroName = new JTextField(" ");
		heroName.setBorder(new EmptyBorder(0,2,0,2));
		heroName.setOpaque(false);
		heroName.setEditable(true);
		heroName.setFont(new Font("Verdana",Font.PLAIN,24));
		heroName.setHorizontalAlignment(JTextField.RIGHT);
		
		heroName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {newHero.setName(((JTextField)e.getSource()).getText());}

			@Override
			public void keyTyped(KeyEvent e) {newHero.setName(((JTextField)e.getSource()).getText());}

			@Override
			public void keyReleased(KeyEvent e) {newHero.setName(((JTextField)e.getSource()).getText());}
		});
		
		heroLvl = new JTextField(" ");
		heroLvl.setBorder(new EmptyBorder(0,2,6,2));
		heroLvl.setOpaque(false);
		heroLvl.setEditable(false);
		heroLvl.setFont(new Font("Verdana",Font.PLAIN,18));
		heroLvl.setHorizontalAlignment(JTextField.LEFT);
		
		JButton nameBtn = new JButton("Generate");
		
		JPanel headMidPanel = new JPanel();
		headMidPanel.setOpaque(false);
		headMidPanel.setLayout(new GridLayout(1,0,50,0));
		headMidPanel.setBorder(new EmptyBorder(10,0,7,5));
		headMidPanel.add(heroLvl);
		headMidPanel.add(nameBtn);
		
		htChoice = new JComboBox<String>();
		htChoice.setOpaque(false);
		htChoice.setFont(new Font("Verdana",Font.PLAIN,16));
		
		htChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				newHero = null;
				newHero = new Hero(heroName.getText(), Census.getHeroType(e.getItem().toString()));
				refresh();
			}
		});
		
		alphabetic = new ArrayList<String>();
		for (HeroType ht : Census.getHeroTypes()) {
			picPanel.add(new Avatar(ht, true), ht.getName());
			alphabetic.add(ht.getName());
		}
		Collections.sort(alphabetic);
		for (String s : alphabetic) {htChoice.addItem(s);}
		
		nameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				htChoice.setSelectedItem(Generator.random(alphabetic));
				newHero.setName(Generator.generateName());
				refresh();
			}
		});
		
		infoPanel.add(heroName);
		infoPanel.add(headMidPanel);
		infoPanel.add(htChoice);

		headerPanel.add(picPanel, BorderLayout.WEST);
		headerPanel.add(infoPanel, BorderLayout.EAST);
	}
	
	private void makeCharMiddle() {
		midPanel = new JPanel();
		midPanel.setPreferredSize(new Dimension(480,220));
		midPanel.setOpaque(false);
		midPanel.setBorder(new EmptyBorder(20,0,0,12));
		
		hpRow = new StatRow("Hit Points",0,true);
		mpRow = new StatRow("Magic Points",0,true);
		xpRow = new StatRow("Experience Points",0,true);
		gpRow = new StatRow("Gold Pieces",0,true);
		initRow = new StatRow("Initiative",0,true);
		
		strRow = new StatRow("Strength",0,true);
		intRow = new StatRow("Intelligence",0,true);
		spdRow = new StatRow("Speed",0,true);
		offRow = new StatRow("Offense",0,true);
		defRow = new StatRow("Defense",0,true);
		
		JPanel lStats = new JPanel();
		lStats.setOpaque(false);
		lStats.setLayout(new BoxLayout(lStats, BoxLayout.Y_AXIS));
		lStats.setPreferredSize(new Dimension(165,180));
		lStats.setBorder(new EmptyBorder(0,0,0,20));
		
		JPanel rStats = new JPanel();
		rStats.setOpaque(false);
		rStats.setLayout(new BoxLayout(rStats, BoxLayout.Y_AXIS));
		rStats.setPreferredSize(new Dimension(275,180));
		rStats.setBorder(new EmptyBorder(0,0,0,10));
		
		rStats.add(hpRow);
		rStats.add(mpRow);
		rStats.add(xpRow);
		rStats.add(gpRow);
		rStats.add(initRow);
		
		lStats.add(strRow);
		lStats.add(intRow);
		lStats.add(spdRow);
		lStats.add(offRow);
		lStats.add(defRow);
		
		midPanel.add(lStats, BorderLayout.WEST);
		midPanel.add(rStats, BorderLayout.EAST);
	}
	
	private void makeCharFooter() {
		footerPanel = new JPanel();
		footerPanel.setPreferredSize(new Dimension(480,230));
		footerPanel.setLayout(new BorderLayout());
		footerPanel.setOpaque(false);
		footerPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new TitledBorder(new LineBorder(colors[2],1),"Status",TitledBorder.RIGHT,TitledBorder.CENTER));
		statusPanel.setOpaque(false);
		statusPanel.setLayout(new BorderLayout());
		statusList = new JTextArea(" ");
		statusList.setOpaque(false);
		statusList.setEditable(false);
		statusList.setLineWrap(true);
		statusList.setWrapStyleWord(true);
		statusList.setFont(new Font("Verdana",Font.PLAIN,10));
		statusPanel.add(statusList, BorderLayout.CENTER);
		
		JPanel immunityPanel = new JPanel();
		immunityPanel.setBorder(new TitledBorder(new LineBorder(colors[2],1),"Immunities",TitledBorder.RIGHT,TitledBorder.CENTER));
		immunityPanel.setOpaque(false);
		immunityPanel.setLayout(new BorderLayout());
		immunityList = new JTextArea(" ");
		immunityList.setOpaque(false);
		immunityList.setEditable(false);
		immunityList.setLineWrap(true);
		immunityList.setWrapStyleWord(true);
		immunityList.setFont(new Font("Verdana",Font.PLAIN,10));
		immunityPanel.add(immunityList, BorderLayout.CENTER);
		
		JPanel conditionPanel = new JPanel();
		conditionPanel.setOpaque(false);
		conditionPanel.setPreferredSize(new Dimension(160,220));
		conditionPanel.setLayout(new GridLayout(0,1,0,0));
		//conditionPanel.setBorder(new EmptyBorder(5,5,5,5));
		conditionPanel.add(statusPanel, BorderLayout.NORTH);
		conditionPanel.add(immunityPanel, BorderLayout.SOUTH);
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBorder(new TitledBorder(new LineBorder(colors[2],1),"Actions",TitledBorder.RIGHT,TitledBorder.CENTER));
		actionsPanel.setOpaque(false);
		actionsPanel.setPreferredSize(new Dimension(300,220));
		
		
		footerPanel.add(conditionPanel, BorderLayout.WEST);
		footerPanel.add(actionsPanel, BorderLayout.EAST);
	}
	
	private class StatRow extends JPanel {
		private static final long serialVersionUID = 8280671977778227872L;
		private JLabel vLabel;
		
		public StatRow(String text, String value, boolean bigSize) {
			String imgLoc = "res/img/Stat/";
			switch(text) {
				case "Strength":
					imgLoc += "Strength.png";
					break;
				case "Intelligence":
					imgLoc += "Intelligence.png";
					break;
				case "Speed":
					imgLoc += "Speed.png";
					break;
				case "Offense":
					imgLoc += "Offense.png";
					break;
				case "Defense":
					imgLoc += "Defense.png";
					break;
				case "HP":
				case "Hit Points":
					imgLoc += "HP.png";
					break;
				case "MP":
				case "Magic Points":
					imgLoc += "MP.png";
					break;
				case "XP":
				case "Experience Points":
					imgLoc += "XP.png";
					break;
				case "GP":
				case "Gold Pieces":
					imgLoc += "GP.png";
					break;
				case "Init":
				case "Initiative":
					imgLoc += "Initiative.png";
					break;
				default:
					break;
			}
			
			setLayout(new BorderLayout(10,0));
			setOpaque(false);
						
			ImageIcon img = new ImageIcon(imgLoc);
			if (bigSize) {
				Image iconImage = img.getImage();
				Image bigIconImage = iconImage.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
				img = new ImageIcon(bigIconImage);
			}
			
			JLabel iLabel = new JLabel(img);
			JLabel tLabel = new JLabel(text);
			tLabel.setFont(new Font("Verdana",Font.PLAIN,13));
			vLabel = new JLabel(value);
			vLabel.setFont(new Font("Verdana",Font.PLAIN,13));
			add(iLabel, BorderLayout.WEST);
			add(tLabel, BorderLayout.CENTER);
			add(vLabel, BorderLayout.EAST);
		}
		
		public StatRow(String text, String value) {
			this(text,value,false);
		}
		
		public StatRow(String text, int value, boolean bigSize) {
			this(text,""+value,bigSize);
		}
		
		public StatRow(String text, int value) {
			this(text,""+value,false);
		}

		public void setValue(int value) {
			setValue("" + value);
		}
		
		public void setValue(String value) {
			vLabel.setText(value);
		}
	}
	
	private void makeCtrlPanel() {
		btn1 = new JButton("Make characters (stock)");
		btn2 = new JButton("Print character");
		btn3 = new JButton("Done");
		btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn3.setAlignmentX(Component.CENTER_ALIGNMENT);
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
				System.out.println(newHero.toString());
			}
		});
		
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done = true;
				setVisible(false);
			}
		});
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(3,1,10,10));
		btnPanel.setOpaque(false);
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		btnPanel.add(new JLabel(" "));
		btnPanel.add(new JLabel(" "));
		btnPanel.add(btn1);
		btnPanel.add(new JLabel(" "));
		btnPanel.add(btn2);
		btnPanel.add(new JLabel(" "));
		btnPanel.add(btn3);
		btnPanel.add(new JLabel(" "));
		
		ctrlPanel = new JPanel();
		ctrlPanel.setPreferredSize(new Dimension(240,600));
		ctrlPanel.setBorder(new LineBorder(colors[2],4));
		ctrlPanel.setLayout(new GridLayout(3,1,20,20));
		ctrlPanel.setOpaque(false);
		ctrlPanel.add(new JLabel(""));
		ctrlPanel.add(new JLabel(""));
		ctrlPanel.add(btnPanel);
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
