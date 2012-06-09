package com.osmihi.battle.ui;
// references:
// http://stackoverflow.com/questions/1627028/how-to-set-auto-scrolling-of-jtextarea-in-java-gui
// http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html#alignment
// http://docs.oracle.com/javase/tutorial/uiswing/layout/card.html

import com.osmihi.battle.mechanics.*;
import com.osmihi.battle.realm.*;
import com.osmihi.battle.realm.Action;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI_BattleScreen extends JFrame {
	private static final long serialVersionUID = 1L;

	Combat bc;
	
	// Color theme
	Color[] colors = {
		new Color(125,42,53),
		new Color(204,146,88),
		new Color(145,122,86),
		new Color(180,186,108),
		new Color(254,255,194)
	};
	
	JPanel mainPanel;
	
	JPanel battlePicPanel;
	Map<Creature,CreaturePanel> creaturePanels;
	
	JPanel bottomPanel;
	CardLayout cardSet;
	Map<Creature,TurnPanel> turnPanels;
		
	JPanel battleTextPanel;
	JTextArea battleText;
	
	Creature selectedTarget = null;
	Action selectedAction = null;
	
	public GUI_BattleScreen(Combat bClock) {
		bc = bClock;
		creaturePanels = new HashMap<Creature,CreaturePanel>();
		turnPanels = new HashMap<Creature,TurnPanel>();
		
		cardSet = new CardLayout();
		bottomPanel = new JPanel(cardSet);
		
		makePicPanel();
		makeTextPanel();
		makeTurnPanels();
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(720,480));
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBorder(new LineBorder(colors[2],4));
		mainPanel.setBackground(colors[3]);
		mainPanel.add(battlePicPanel, BorderLayout.NORTH);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
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
		bottomPanel.add(battleTextPanel, "battleText");
	}
	
	private void makeTurnPanels() {
		for (Creature h : bc.getHeroes()) {
			turnPanels.put(h, new TurnPanel(h));
			bottomPanel.add(turnPanels.get(h), h.getName());
		}
	}
	
	public Map<Creature,Action> turnView(Creature c) {
		turnPanels.get(c).refresh();
		cardSet.show(bottomPanel, c.getName());
		Map<Creature,Action> choice = new HashMap<Creature,Action>();
		while (selectedTarget == null || selectedAction == null) {
			Generator.delay(500);
		}
		choice.put(selectedTarget, selectedAction);
		selectedTarget = null;
		selectedAction = null;
		for (TurnPanel tp : turnPanels.values()) {tp.resetTarget();}
		return choice;
	}
	
	public void turnView() {
		cardSet.show(bottomPanel, "battleText");
	}
	
	public void battleText(String bt) {
		battleText.append("\n" + bt);
		battleText.setCaretPosition(battleText.getDocument().getLength());
	}
	
	public void refresh(Creature cre) {
			creaturePanels.get(cre).refreshStats();
	}
	
	private class CreaturePanel extends JPanel implements MouseListener {
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
			addMouseListener(this);
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

		@Override
		public void mouseClicked(MouseEvent e) {
			for (TurnPanel tp : turnPanels.values()) {
				selectedTarget = c;
				tp.selectTarget(this.c);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	private class TurnPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Creature c;
		private JPanel picPane;
		private JPanel infoPane;
		private JPanel targetPane;
		private JPanel targetPics;
		private CardLayout tCards;
		private JPanel actionPane;
		
		private JLabel hpLabel;
		private JLabel mpLabel;
		private JLabel conLabel;
		
		private JLabel tName;
		private JLabel tStat;
		private Map<Action,JButton> actionBtns;
		
		public TurnPanel(Creature cre) {
			c = cre;
			hpLabel = new JLabel();
			mpLabel = new JLabel();
			conLabel = new JLabel();
			targetPane = new JPanel();
			targetPics = new JPanel();
			tName = new JLabel(" ");
			tStat = new JLabel(" ");
			actionBtns = new HashMap<Action,JButton>();
			
			refresh();
			makePicPane();
			makeInfoPane();
			makeTargetPane();
			makeActionPane();
			
			setLayout(new GridLayout(0,4,5,5));
			add(infoPane);
			add(picPane);
			add(targetPane);
			add(actionPane);
		}
		
		private void makePicPane() {
			ImageIcon icon;
			if (c.getImageFile() != null) {icon = new ImageIcon(c.getImageFile());}
			else {icon = new ImageIcon("res/img/_blank.png");}
			
			picPane = new JPanel();
			picPane.setLayout(new BoxLayout(picPane, BoxLayout.Y_AXIS));
						
			JLabel picl = new JLabel(icon, JLabel.CENTER);
			picl.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel naml = new JLabel(c.getName(), JLabel.CENTER);
			naml.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel hrol = new JLabel("Lv " + ((Hero)c).getLevel() + " " + ((Hero)c).getHeroType().getName());
			hrol.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			picPane.add(naml);
			picPane.add(new JLabel(" "));
			picPane.add(picl);
			picPane.add(new JLabel(" "));
			picPane.add(hrol);
		}
		
		private void makeInfoPane() {
			infoPane = new JPanel();
			infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
			
			JLabel hl = new JLabel("Lv " + ((Hero)c).getLevel() + " " + ((Hero)c).getHeroType().getName());
			hl.setAlignmentX(Component.CENTER_ALIGNMENT);
			hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			conLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			infoPane.add(new JLabel(" "));
			infoPane.add(hl);
			infoPane.add(new JLabel(" "));
			infoPane.add(hpLabel);
			infoPane.add(mpLabel);
			infoPane.add(conLabel);
			for (Condition con : c.getStatus().keySet()) {
				infoPane.add(new JLabel(con.getName() + " (" + c.getStatus().get(con).intValue() + ")"));
			}
		}
		
		private void makeActionPane() {
			actionPane = new JPanel();
			//actionPane.setLayout(new BoxLayout(actionPane, BoxLayout.Y_AXIS));
			actionPane.setLayout(new GridLayout(0,1));
			for (final Action a : c.getActions()) {
				JButton btn = new JButton(a.getName() + " (" + a.getType().toString() + ")");
				btn.setAlignmentX(CENTER_ALIGNMENT);
				btn.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							selectedAction = a;
						}
					});
				actionBtns.put(a,btn);
			}
			for (JButton btn : actionBtns.values()) {actionPane.add(btn);}
		}
		
		private void makeTargetPane() {
			tCards = new CardLayout();
			targetPics.setLayout(tCards);
			targetPics.add(new JLabel("Select Target"), "blank");
			
			for (Creature e : bc.getCombatants()) {	
				JLabel pLbl = new JLabel(new ImageIcon(e.getImageFile()), JLabel.CENTER);
				targetPics.add(pLbl, e.getId().toString());
			}
			
			targetPane.setLayout(new BoxLayout(targetPane, BoxLayout.Y_AXIS));
			tName.setAlignmentX(CENTER_ALIGNMENT);
			tStat.setAlignmentX(CENTER_ALIGNMENT);
			
			targetPane.add(tName);
			targetPane.add(targetPics);
			targetPane.add(tStat);
		}
		
		public void refresh() {
			refreshStats();
			refreshActions();
		}
		
		public void refreshActions() {
			for (Action a : actionBtns.keySet()) {
				if (a.getMpCost() > c.getMp()) {
					actionBtns.get(a).setEnabled(false);
				}
			}
		}
		
		private void refreshStats() {
			hpLabel.setText(c.getHp() + " / " + c.getMaxHp() + " hp");
			mpLabel.setText(c.getMp() + " / " + c.getMaxMp() + " mp");
			String conText = "";
			for (Condition con : c.getStatus().keySet()) {
				conText += con.getName() + " (" + c.getStatus().get(con).intValue() + ") ";
			}
			conLabel.setText(conText);
		}
		
		public void selectTarget(Creature c) {
			if (c != null) {
				tName.setText(c.getName());
				tCards.show(targetPics, c.getId().toString());
				tStat.setText(c.getHp() + " hp");
			}
		}
		
		public void resetTarget() {
			tName.setText("");
			tCards.show(targetPics, "blank");
			tStat.setText("");
		}
	}
}
