// References:
// http://darksleep.com/player/threads/

package com.osmihi.battle.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.osmihi.battle.mechanics.Generator;
import com.osmihi.battle.realm.Creature;
import com.osmihi.battle.realm.HeroType;

public class Avatar extends JPanel {
	private static final long serialVersionUID = 9178134598465577303L;

	private boolean bigSize;
	
	private ImageIcon icon;
	private ImageIcon action;
	private JLabel pic;
	
	public Avatar(Creature c, boolean bigSize) {
		this.bigSize = bigSize;
		icon = new ImageIcon(c.getImageFile());
		action = new ImageIcon(c.getImageAlt());
		make();
	}
	
	public Avatar(HeroType ht, boolean bigSize) {
		this.bigSize = bigSize;
		icon = new ImageIcon(ht.getImageFile());
		action = new ImageIcon(ht.getImageAlt());
		make();
	}
	
	public Avatar(Creature c) {
		this(c, false);
	}
	
	public Avatar(HeroType ht) {
		this(ht, false);
	}
	
	private void make() {
		if (bigSize) {
			Image iconImage = icon.getImage();
			Image actionImage = action.getImage();
			Image bigIconImage = iconImage.getScaledInstance(128, 128, java.awt.Image.SCALE_FAST);
			Image bigActionImage = actionImage.getScaledInstance(128, 128, java.awt.Image.SCALE_FAST);
			icon = new ImageIcon(bigIconImage);
			action = new ImageIcon(bigActionImage);
		}
		
		pic = new JLabel(icon, JLabel.CENTER);
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		pic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {animate();}
		});
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		this.add(pic);
	}
	
	public void flip() {
		if (pic.getIcon() == icon) {pic.setIcon(action);}
		else if (pic.getIcon() == action) {pic.setIcon(icon);}
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
}
