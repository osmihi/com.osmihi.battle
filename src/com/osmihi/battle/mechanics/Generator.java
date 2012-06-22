/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * http://stackoverflow.com/questions/4595836/get-a-random-element-from-a-sequential-collection
 * http://www.java-forums.org/threads-synchronization/528-how-use-sleep-method.html 
 *
 * --------------
 * Generator.java
 * --------------
 * Generator is a static-only utility class which is used in various places throughout the program to
 * perform functions like generating random numbers to determine combat outcomes, pausing the program via
 * the sleep() method, generating unique random IDs for some objects, and miscellaneous text formatting. 
 * 
 *********************************************************************************************************/

package com.osmihi.battle.mechanics;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Generator {
	
	private static Random dice = new Random();
	
	private static LinkedList<Long> usedIds = new LinkedList<Long>();
	
	private Generator() {}
	
	public static int random(int min, int max) {
		if (max <= 0) {return 0;}
		else {return dice.nextInt(max) + min;}
	}
	
	public static int random(int max) {
		return random(1,max);
	}
	
	public static <T> T random(Collection<T> coll) {
		// This method will randomly pick an element from a collection
		float num = 0;
		T chosen = null;
		
		Iterator<T> i = coll.iterator();
		
		while (i.hasNext()) {
			float ran = dice.nextFloat();
			T t = i.next();
			if (ran > num) {
				num = ran;
				chosen = t;
			}
		}
		return chosen;
	}
	
	public static void delay(int ms) {
		try {
			Thread.currentThread();
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String txtLine(char c, int num) {
		String theStr = "";
		for (int i = 0; i < num; i++) {theStr += c;}
		return theStr;
	}
	
	public static String txtLine(char c, String match) {
		return txtLine(c,match.length());
	}
	
	public static Long id() {
		Long id;
		do {
			id = new Long(dice.nextLong());
		} while (usedIds.contains(id));
		usedIds.add(id);
		return id;
	}
}
