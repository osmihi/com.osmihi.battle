/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://stackoverflow.com/questions/1627028/how-to-set-auto-scrolling-of-jtextarea-in-java-gui
 * http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html#alignment
 * http://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
 * in-class program showing use of bitwise operator to determine odd/even
 * http://www.velocityreviews.com/forums/t133901-outer-class-keyword.html
 *
 * ----------------------
 * GUI_BattleScreen.java
 * ----------------------
 * The BattleScreen is the most complicated part of the GUI currently. That being said, it still has a long
 * way to go. It contains a handful of inner classes, which it may be beneficial to split up in the future
 * as these things expand. In any case, this screen displays all the combatants on the hero team and on the
 * enemy team, and the section where a user chooses their action & target each turn. Also, it animates the
 * frames on heroes and enemies when they take their turns. Also, it contains a console-like text view that
 * prints out battle details. I don't think this will be kept in the long run, since it will be much nicer
 * to display these things graphically, but for the time being it is useful so we know what is happening
 * during the battle. The instance of this screen is created inside of a Combat, which triggers its various
 * public methods at different times corresponding to events in the battle.
 * 
 *********************************************************************************************************/

package com.osmihi.battle.ui;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class GUI_BattleScreen extends GUI_GenericWindow {
	private static final long serialVersionUID = 1L;

	private final int FRAME_RATE = 150;
	
	Combat bc;
	
	BgPanel battlePicPanel;
	Map<Creature,Avatar> creaturePanels;
	Map<Creature,StatBox> statBoxes;
	
	JPanel choicePanel;
	CardLayout cardSet;
	Map<Creature,TurnPanel> turnPanels;
	private JPanel blankPanel;
	
	private JPanel bottomPanel;
	
	JPanel battleTextPanel;
	JTextArea battleText;
	
	Creature selectedTarget = null;
	Action selectedAction = null;
	
	public GUI_BattleScreen(Combat bClock) {
		super();
		bc = bClock;
		creaturePanels = new HashMap<Creature,Avatar>();
		statBoxes = new HashMap<Creature,StatBox>();
		turnPanels = new HashMap<Creature,TurnPanel>();
		
		cardSet = new CardLayout();
		choicePanel = new JPanel(cardSet);
		choicePanel.setOpaque(false);
		
		blankPanel = new JPanel();
		blankPanel.setOpaque(false);
		
		bottomPanel = new JPanel(new GridLayout(1,2,0,0));
		bottomPanel.setOpaque(false);
		bottomPanel.add(choicePanel);
		
		makePicPanel();
		makeTextPanel();
		makeTurnPanels();

		mainPanel.add(battlePicPanel, BorderLayout.NORTH);
		//mainPanel.add(choicePanel, BorderLayout.SOUTH);
		mainPanel.add(bottomPanel);
	}
	
	protected void populateWindow() {
		setTitle("Battle!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void makePicPanel() {
		battlePicPanel = new BgPanel("res/img/Battle/dungeon.png");
		battlePicPanel.setPreferredSize(new Dimension(720,342));
		//battlePicPanel.setLayout(new BorderLayout());
		battlePicPanel.setLayout(new GridLayout(1,2,50,50));
		battlePicPanel.setBorder(new MatteBorder(0,0,4,0,colors[2]));
		battlePicPanel.setOpaque(false);
		
		JPanel heroPanel = new JPanel();
		heroPanel.setLayout(new GridLayout(1,3));
		heroPanel.setOpaque(false);
		
		JPanel heroColumnFront = new JPanel();
		heroColumnFront.setLayout(new BoxLayout(heroColumnFront, BoxLayout.Y_AXIS));
		heroColumnFront.setOpaque(false);
		
		JPanel heroColumnMiddle = new JPanel();
		heroColumnMiddle.setLayout(new BoxLayout(heroColumnMiddle, BoxLayout.Y_AXIS));
		heroColumnMiddle.setOpaque(false);
		
		JPanel heroColumnBack = new JPanel();
		heroColumnBack.setLayout(new BoxLayout(heroColumnBack, BoxLayout.Y_AXIS));
		heroColumnBack.setOpaque(false);
		
		heroPanel.add(heroColumnBack);
		heroPanel.add(heroColumnMiddle);
		heroPanel.add(heroColumnFront);
		
		heroColumnFront.setBorder(new EmptyBorder(110,0,0,0));
		heroColumnMiddle.setBorder(new EmptyBorder(110,0,0,0));
		heroColumnBack.setBorder(new EmptyBorder(96,0,0,0));
		
		int i = 0;
		for (Creature c : bc.getHeroes()) {
			final Creature cf = c;
			Avatar av = new Avatar(cf);
			av.animateOnClick(false);
			av.addPicClickListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for (TurnPanel tp : turnPanels.values()) {
						selectedTarget = cf;
						tp.selectTarget(cf);
					}
				}
			});

			if ((i & 1) == 0) {
				av.setBorder(new EmptyBorder(0,0,32,0));
				heroColumnFront.add(av);
			} else {
				av.setBorder(new EmptyBorder(32,0,0,0));
				heroColumnMiddle.add(av);
			}

			creaturePanels.put(c, av);
			
			StatBox cInfo = new StatBox(c);
			statBoxes.put(c, cInfo);
			heroColumnBack.add(cInfo);
			i++;
		}
		
		JPanel enemyPanel = new JPanel();
		enemyPanel.setLayout(new GridLayout(1,4));
		enemyPanel.setOpaque(false);
		
		JPanel[] enemyCols = new JPanel[4];
		
		for (int j = 0; j < enemyCols.length; j++) {
			enemyCols[j] = new JPanel();
			enemyCols[j].setLayout(new BoxLayout(enemyCols[j], BoxLayout.Y_AXIS));
			enemyCols[j].setOpaque(false);
			enemyCols[j].setBorder(new EmptyBorder(110,0,0,0));
			
			enemyPanel.add(enemyCols[j]);
		}
		
		i = 0;
		for (Creature c : bc.getEnemies()) {
			final Creature cf = c;
			Avatar av = new Avatar(cf);
			av.animateOnClick(false);
			av.addPicClickListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for (TurnPanel tp : turnPanels.values()) {
						selectedTarget = cf;
						tp.selectTarget(cf);
					}
				}
			});

			if ((i < 2) || (i > 3 && i < 6)) {
				av.setBorder(new EmptyBorder(0,0,32,0));
			} else {
				av.setBorder(new EmptyBorder(32,0,0,0));
			}
			enemyCols[i / 2].add(av);

			creaturePanels.put(c, av);
			i++;
		}
		
		battlePicPanel.add(heroPanel);
		battlePicPanel.add(enemyPanel);
	}
	
	private void makeTextPanel() {
		battleTextPanel = new JPanel(); 
		battleTextPanel.setPreferredSize(new Dimension(310,248));
		battleTextPanel.setLayout(new BorderLayout(4,4));
		battleText = new JTextArea("");
		battleText.setFont(new Font("Consolas", Font.PLAIN, 12));
		battleText.setEditable(false);
		battleText.setLineWrap(true);
		battleText.setWrapStyleWord(true);
		
		JScrollPane btScroll = new JScrollPane(battleText);
		btScroll.setPreferredSize(battleTextPanel.getPreferredSize());
		btScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		battleTextPanel.add(btScroll, BorderLayout.CENTER);
		//choicePanel.add(battleTextPanel, "battleText");
		bottomPanel.add(battleTextPanel);
	}
	
	private void makeTurnPanels() {
		for (Creature h : bc.getHeroes()) {
			turnPanels.put(h, new TurnPanel(h));
			choicePanel.add(turnPanels.get(h), h.getName());
			choicePanel.add(blankPanel, "blank");
		}
	}
	
	public Map<Creature,Action> turnView(Creature c) {
		turnPanels.get(c).refresh();
		cardSet.show(choicePanel, c.getName());
		Map<Creature,Action> choice = new HashMap<Creature,Action>();
		while (selectedTarget == null || selectedAction == null) {
			Generator.delay(50);
		}
		choice.put(selectedTarget, selectedAction);
		selectedTarget = null;
		selectedAction = null;
		for (TurnPanel tp : turnPanels.values()) {
			tp.resetTarget();
		}
		return choice;
	}
	
	public void turnView() {
		cardSet.show(choicePanel, "blank");
	}
	
	private class StatBox extends JPanel {
		private static final long serialVersionUID = -8372897647070831052L;
		private Creature c;
		private CardLayout sCards;
		
		private JLabel hpv;
		private JLabel mhpv;
		private JLabel mpv;
		private JLabel mmpv;
		
		private JTextArea statusList;
		
		public StatBox(Creature cre) {
			c = cre;
						
			this.setOpaque(false);
			this.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), c.getName(), TitledBorder.RIGHT, TitledBorder.CENTER, new Font("Verdana",Font.BOLD,12), Color.BLACK));
			sCards = new CardLayout();
			this.setLayout(sCards);

			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					sCards.next(StatBox.this);
				}
			});
			
			JPanel hpmp = new JPanel();
			hpmp.setLayout(new GridLayout(2,0));
			hpmp.setOpaque(false);
			
			hpv = new JLabel("" + c.getHp());
			mhpv = new JLabel("" + c.getMaxHp());
			mpv = new JLabel("" + c.getMp());
			mmpv = new JLabel("" + c.getMaxMp());
			
			JLabel[] lbls = {new JLabel("HP"), new JLabel(" "), hpv, new JLabel(" / "), mhpv, new JLabel("MP"), new JLabel(" "), mpv, new JLabel(" / "), mmpv,};
			for (int j = 0; j < lbls.length; j++) {
				lbls[j].setForeground(Color.BLACK);
				if (j % 5 == 0) {lbls[j].setFont(new Font("Verdana", Font.BOLD, 11));}
				else {lbls[j].setFont(new Font("Verdana", Font.PLAIN, 11));}
				hpmp.add(lbls[j]);
			}
			
			JPanel status = new JPanel();
			status.setOpaque(false);
			statusList = new JTextArea("");
			statusList.setFont(new Font("Verdana", Font.PLAIN, 11));
			statusList.setOpaque(false);
			statusList.setEditable(false);
			status.add(statusList, BorderLayout.CENTER);
			
			this.add(hpmp, "hpmp");
			this.add(status, "status");
			
			sCards.show(this,"hpmp");
			
			refreshStats();
		}
				
		public void refreshStats() {
			hpv.setText("" + c.getHp());
			mhpv.setText("" + c.getMaxHp());
			mpv.setText("" + c.getMp());
			mmpv.setText("" + c.getMaxMp());
			
			statusList.setText("");
			String delim = "";
			for (Condition con : c.getStatus().keySet()) {
				statusList.append(delim + con.getName() + "(" + c.getStatusDuration(con) + ")");
				delim = ", ";
			}
			if (statusList.getText().equals("")) {
				this.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), c.getName(), TitledBorder.RIGHT, TitledBorder.CENTER, new Font("Verdana",Font.BOLD,12), Color.BLACK));
			} else {
				this.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), c.getName(), TitledBorder.RIGHT, TitledBorder.CENTER, new Font("Verdana",Font.BOLD,12), Color.GREEN));
			}
		}
	}
	
	public void animate(Creature c, int rpt) {
		if ((rpt & 1) != 0) {rpt++;} // make sure it's an even number of repeats
		for (int i= 0; i < rpt; i++) {
			if (creaturePanels.containsKey(c)) {creaturePanels.get(c).flip();}
			//if (turnPanels.containsKey(c)) {turnPanels.get(c).flip();}
			Generator.delay(FRAME_RATE);
		}
	}
	
	public void animate(Creature c) {
		animate(c,6);
	}
	
	public void animate(Collection<Creature> col) {
		animate(col, 6);
	}
	
	public void animate(Collection<Creature> col, int rpt) {
		for (int i = 0; i < rpt; i++) {
			for (Creature c : col) {
				if (creaturePanels.containsKey(c)) {creaturePanels.get(c).flip();}
				//if (turnPanels.containsKey(c)) {turnPanels.get(c).flip();}
			}
			Generator.delay(FRAME_RATE);
		}
	}
	
	public void battleText(String bt) {
		battleText.append("\n" + bt);
		battleText.setCaretPosition(battleText.getDocument().getLength());
	}
	
	public void refresh(Creature cre) {
			if (cre instanceof Hero) {
				statBoxes.get(cre).refreshStats();
			}
			if (cre.getHp() < 1) {
				creaturePanels.get(cre).die();
			}
			creaturePanels.get(cre).setHealthy();
			for (Condition con : cre.getStatus().keySet()) {
				creaturePanels.get(cre).setAffected();
			}
	}	
	
	
	private class TurnPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Creature c;
		private Avatar picPane;
		private JPanel infoPane;
		private JPanel targetPane;
		private CardLayout tCards;
		private JPanel actionPane;
		
		private JLabel hpLabel;
		private JLabel mpLabel;
		private JLabel conLabel;
		
		private Map<Action,JButton> actionBtns;

		public TurnPanel(Creature cre) {
			c = cre;
			hpLabel = new JLabel();
			mpLabel = new JLabel();
			conLabel = new JLabel();
			
			targetPane = new JPanel();
			actionBtns = new HashMap<Action,JButton>();
			
			refresh();
			picPane = new Avatar(c);
			makeInfoPane();
			makeTargetPane();
			makeActionPane();
			
			infoPane.setOpaque(false);
			targetPane.setOpaque(false);
			actionPane.setOpaque(false);
			
			JPanel topSection = new JPanel();
			topSection.setOpaque(false);
			topSection.setLayout(new GridLayout(0,3,5,5));
			topSection.setBorder(new EmptyBorder(20,0,0,0));
			topSection.add(infoPane);
			topSection.add(picPane);
			topSection.add(targetPane);
			
			setOpaque(false);
			setLayout(new GridLayout(2,1,0,0));
			
			add(topSection);
			add(actionPane);
		}
		
		private void makeInfoPane() {
			infoPane = new JPanel();
			infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
			
			JLabel hl = new JLabel("Lv " + ((Hero)c).getLevel() + " " + ((Hero)c).getHeroType().getName());
			hl.setAlignmentX(Component.CENTER_ALIGNMENT);
			hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			infoPane.add(hl);
			infoPane.add(new JLabel(" "));
			infoPane.add(hpLabel);
			infoPane.add(mpLabel);
			infoPane.add(new JLabel(" "));

			infoPane.add(conLabel);
			for (Condition con : c.getStatus().keySet()) {
				infoPane.add(new JLabel(con.getName() + " (" + c.getStatus().get(con).intValue() + ")"));
			}
		}
		
		private void makeTargetPane() {
			tCards = new CardLayout();
			targetPane.setLayout(tCards);
			targetPane.setOpaque(false);
			targetPane.add(new JLabel("Select Target"), "blank");
			
			for (Creature e : bc.getCombatants()) {	
				targetPane.add(new Avatar(e), e.getId().toString());
			}
		}

		private void makeActionPane() {
			actionPane = new JPanel(new GridLayout(3,0));
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
				tCards.show(targetPane, c.getId().toString());
			}
		}
		
		public void resetTarget() {
			tCards.show(targetPane, "blank");
		}
		
		public void flip() {
			picPane.flip();
		}
	}
}
