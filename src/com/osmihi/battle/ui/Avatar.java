/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://darksleep.com/player/threads/
 *
 * ------------
 * Avatar.java
 * ------------
 * This class inherits from JPanel and provides a simple and reusable character icon for a Creature that
 * allows some animation functions and other convenient methods.
 *  
 *********************************************************************************************************/

package com.osmihi.battle.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.osmihi.battle.mechanics.Generator;
import com.osmihi.battle.realm.Creature;
import com.osmihi.battle.realm.HeroType;

public class Avatar extends JPanel {
	private static final long serialVersionUID = 9178134598465577303L;

	private String name;
	private boolean bigSize;
	
	private ImageIcon icon;
	private ImageIcon action;
	private ImageIcon grave;
	private boolean alive;
	private JLabel pic;
	private JLabel nameLbl;
	private MouseAdapter ani;
	
	public Avatar(Creature c, boolean bigSize) {
		name = c.getName();
		alive = true;
		this.bigSize = bigSize;
		icon = new ImageIcon(c.getImageFile());
		action = new ImageIcon(c.getImageAlt());
		grave = new ImageIcon("res/img/Creature/dead.png");
		make();
	}
	
	public Avatar(HeroType ht, boolean bigSize) {
		name = "";
		alive = true;
		this.bigSize = bigSize;
		icon = new ImageIcon(ht.getImageFile());
		action = new ImageIcon(ht.getImageAlt());
		grave = new ImageIcon("res/img/Creature/dead.png"); 
		make();
	}
	
	public Avatar(Creature c) {
		this(c, false);
	}
	
	public Avatar(HeroType ht) {
		this(ht, false);
	}
	
	private void make() {
		nameLbl = new JLabel(name);
		ani = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				animate();
			}
		};
		
		if (bigSize) {
			Image iconImage = icon.getImage();
			Image actionImage = action.getImage();
			Image graveImage = grave.getImage();
			Image bigIconImage = iconImage.getScaledInstance(128, 128, java.awt.Image.SCALE_FAST);
			Image bigActionImage = actionImage.getScaledInstance(128, 128, java.awt.Image.SCALE_FAST);
			Image bigGraveImage = graveImage.getScaledInstance(128, 128, java.awt.Image.SCALE_FAST);
			icon = new ImageIcon(bigIconImage);
			action = new ImageIcon(bigActionImage);
			grave = new ImageIcon(bigGraveImage);
		}
		
		pic = new JLabel(icon, JLabel.CENTER);
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		animateOnClick(true);

		nameLbl.setOpaque(false);
		nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameLbl.setFont(new Font("Verdana",Font.PLAIN,12));
		nameLbl.setForeground(Color.BLACK);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		this.add(pic);
		this.add(nameLbl);
	}
	
	public void flip() {
		if (alive) {
			if (pic.getIcon() == icon) {pic.setIcon(action);}
			else if (pic.getIcon() == action) {pic.setIcon(icon);}
		}
	}
	
	public void animate() {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 4; i++) {
					flip();
					Generator.delay(150);
				}
			}	
		}).start();			
	}
	
	public void die() {
		alive = false;
		pic.setIcon(grave);
		nameLbl.setForeground(Color.RED);
	}
	
	public void revive() {
		alive = true;
		pic.setIcon(icon);
		nameLbl.setForeground(Color.BLACK);
	}
	
	public void setAffected() {
		nameLbl.setForeground(Color.GREEN);
	}
	
	public void setHealthy() {
		nameLbl.setForeground(Color.BLACK);
	}
	
	public void animateOnClick(boolean b) {
		if (b) {
			pic.addMouseListener(ani);
		} else {
			pic.removeMouseListener(ani);
		}
	}
	
	public MouseListener addPicClickListener(MouseListener ml) {
		pic.addMouseListener(ml);
		return ml;
	}
}
