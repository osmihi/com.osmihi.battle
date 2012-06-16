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
		
	public GUI_GenericWindow() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(720,600));
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBorder(new LineBorder(colors[2],4));
		mainPanel.setBackground(colors[3]);
		
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		populateWindow();
		setVisible(true);
	}
	
	protected abstract void populateWindow();
}
