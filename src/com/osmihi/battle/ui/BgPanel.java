/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://robbamforth.wordpress.com/2009/02/02/add-a-background-image-to-a-jpanel/
 *
 * ------------
 * BgPanel.java
 * ------------
 * This class simply extends JPanel to make it easy to add a centered background image.
 *  
 *********************************************************************************************************/

package com.osmihi.battle.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BgPanel extends JPanel {
	private static final long serialVersionUID = 6550685258659965806L;

	private Image bgImg;
	
	public BgPanel() {
		super();
	}
	
	public BgPanel(String imgLoc) {
		super();
		bgImg = new ImageIcon(imgLoc).getImage();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (bgImg != null) { // (image, location x, location y, size x, size y)
			g.drawImage(
				bgImg, 
				(this.getWidth() / 2) - (bgImg.getWidth(this) / 2),
				(this.getHeight() / 2) - (bgImg.getHeight(this) / 2),
				bgImg.getWidth(this),
				bgImg.getHeight(this),
				this
			);
		}
	}
}
