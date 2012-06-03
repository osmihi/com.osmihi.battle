package com.osmihi.battle.ui;
// references:
// http://stackoverflow.com/questions/1627028/how-to-set-auto-scrolling-of-jtextarea-in-java-gui

import com.osmihi.battle.mechanics.*;
import com.osmihi.battle.realm.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI_BattleScreen extends JFrame {
	private static final long serialVersionUID = 1L;

	BattleClock bc;
	
	// Color theme
	Color[] colors = {
		new Color(125,42,53),
		new Color(204,146,88),
		new Color(145,122,86),
		new Color(180,186,108),
		new Color(254,255,194)
	};
	
	JPanel battlePicPanel;
	Map<Creature,CreaturePanel> creaturePanels;
	
	JPanel battleTextPanel;
	JTextArea battleText;
	
	public GUI_BattleScreen(BattleClock bClock) {
		bc = bClock;
		creaturePanels = new HashMap<Creature,CreaturePanel>();
		
		makePicPanel();
		makeTextPanel();

		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(720,480));
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBorder(new LineBorder(colors[2],4));
		mainPanel.setBackground(colors[3]);
		mainPanel.add(battlePicPanel, BorderLayout.NORTH);
		mainPanel.add(battleTextPanel, BorderLayout.SOUTH);
		
		add(mainPanel);	
		setTitle("Battle!");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void makePicPanel() {
		battlePicPanel = new JPanel();
		battlePicPanel.setPreferredSize(new Dimension(720,342));
		battlePicPanel.setLayout(new BorderLayout());
		battlePicPanel.setBorder(new LineBorder(colors[2],1));
		
		JPanel heroPanel = new JPanel();
		heroPanel.setLayout(new GridLayout(0,2));
		
		JPanel enemyPanel = new JPanel();
		enemyPanel.setLayout(new GridLayout(0,2));
		
		for (Creature c : bc.getHeroes()) {
			creaturePanels.put(c, new CreaturePanel(c,true));
			heroPanel.add(creaturePanels.get(c));
		}
		
		for (Creature c : bc.getEnemies()) {
			creaturePanels.put(c, new CreaturePanel(c,false));
			enemyPanel.add(creaturePanels.get(c));
		}
		
		battlePicPanel.add(heroPanel,BorderLayout.WEST);
		battlePicPanel.add(enemyPanel,BorderLayout.EAST);
		
	}
	
	private void makeTextPanel() {
		battleTextPanel = new JPanel(); 
		battleTextPanel.setPreferredSize(new Dimension(620,128));
		battleTextPanel.setLayout(new BorderLayout(4,4));
		battleText = new JTextArea("");
		battleText.setFont(new Font("Consolas", Font.PLAIN, 12));
		battleText.setEditable(false);
		JScrollPane btScroll = new JScrollPane(battleText);
		btScroll.setPreferredSize(battleTextPanel.getPreferredSize());
		btScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		battleTextPanel.add(btScroll, BorderLayout.CENTER);
	}
	
	public void setBattleText(String bt) {
		battleText.append(bt);
		battleText.setCaretPosition(battleText.getDocument().getLength());
	}
	
	public void refresh(Creature cre) {
			creaturePanels.get(cre).refreshStats();
	}
	
	private class CreaturePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Creature c;
		private ImageIcon avatar;
		
		private JPanel stats;
		
		private JLabel hpLabel;
		private JLabel mpLabel;
		private JLabel conLabel;
		
		private int valIndex = 0;
		private int[] tbSide = {TitledBorder.LEFT, TitledBorder.RIGHT};
		private String[] picSide = {BorderLayout.EAST, BorderLayout.WEST};
		private String[] statSide = {BorderLayout.WEST, BorderLayout.EAST};
		private int[] labelSide = {JLabel.LEFT, JLabel.RIGHT};
		
		public CreaturePanel(Creature cre, boolean forHero) {
			if (forHero) {valIndex = 0;} else {valIndex = 1;}
			c = cre;
			if (c.getImageFile() != null) {avatar = new ImageIcon(c.getImageFile());}
			else {avatar = new ImageIcon("res/img/_blank.png");}
			
			JLabel pic = new JLabel(avatar);
			
			stats = makeStatsPanel();
			
			setPreferredSize(new Dimension(160,100));
			setLayout(new BorderLayout(4,4));
			setBorder(new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), c.getName(), tbSide[valIndex], TitledBorder.CENTER));
			add(pic, picSide[valIndex]);
			add(stats, statSide[valIndex]);
		}
		
		public void refreshStats() {
			hpLabel.setText(c.getHp() + " / " + c.getMaxHp());
			mpLabel.setText(c.getMp() + " / " + c.getMaxMp());
			String conText = "";
			for (Condition con : c.getStatus().keySet()) {
				conText += con.getName() + " (" + c.getStatus().get(con).intValue() + ") ";
			}
			if (c.getHp() <= 0) {conText = "DEAD";}
			conLabel.setText(conText);
		}
		
		protected JPanel makeStatsPanel() {
			JPanel sp = new JPanel();
			sp.setLayout(new GridLayout(0,1,0,0));

			JPanel hpPanel = new JPanel();
			hpPanel.setLayout(new GridLayout(0,2,0,0));
			hpLabel = new JLabel(c.getHp() + " / " + c.getMaxHp(), labelSide[valIndex]);
			hpPanel.add(hpLabel);
			hpPanel.add(new JLabel("hp", labelSide[valIndex]));

			JPanel mpPanel = new JPanel();
			mpPanel.setLayout(new GridLayout(0,2,0,0));
			mpLabel = new JLabel(c.getMp() + " / " + c.getMaxMp(), labelSide[valIndex]);
			mpPanel.add(mpLabel);
			mpPanel.add(new JLabel("mp", labelSide[valIndex]));
			
			JPanel hpmpPanel = new JPanel();
			hpmpPanel.setLayout(new GridLayout(0,1,0,0));
			hpmpPanel.add(hpPanel);
			hpmpPanel.add(mpPanel);
			
			JPanel conPanel = new JPanel();
			conPanel.setLayout(new GridLayout(0,1,0,0));
			String conText = "";
			for (Condition con : c.getStatus().keySet()) {
				conText += con.getName() + " (" + c.getStatus().get(con).intValue() + ")";
			}
			conLabel = new JLabel(conText, labelSide[valIndex]);
			conPanel.add(conLabel);
			
			sp.add(hpmpPanel);
			sp.add(conPanel);
			
			return sp;
		}
	}
	
}
