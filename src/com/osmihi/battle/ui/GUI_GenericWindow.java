/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * 
 * ----------------------
 * GUI_GenericWindow.java
 * ----------------------
 * This class is an abstract superclass for the other GUI screens used in the game. It sets up some default
 * formatting options, including a color palette, and specifies that each subclass will have a method named
 * populateWindow() to fill it up with the UI components. As other features that will be common to all
 * screens are added in the future, such as a title or menu bar, volume control, etc, they can be added here
 * rather than trying to implement on every single screen individually.
 * 
 *********************************************************************************************************/


package com.osmihi.battle.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.LineBorder;

public abstract class GUI_GenericWindow extends JFrame {

	private static final long serialVersionUID = 4006379455597743657L;

	protected Color[] colors = {
		new Color(125,42,53),
		new Color(204,146,88),
		new Color(145,122,86),
		new Color(180,186,108),
		new Color(254,255,194)
	};
	
	protected JPanel mainPanel;
	protected JPanel fill;
	
	public GUI_GenericWindow() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(720,600));
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBorder(new LineBorder(colors[2],4));
		mainPanel.setBackground(colors[3]);
		fill = new JPanel();
		fill.setBackground(colors[3]);
		
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		populateWindow();
		setResizable(false);
		setVisible(true);
	}
	
	protected abstract void populateWindow();
}
