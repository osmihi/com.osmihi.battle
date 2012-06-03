package com.osmihi.battle.mechanics;
//
// References:
// http://stackoverflow.com/questions/4595836/get-a-random-element-from-a-sequential-collection
// http://www.java-forums.org/threads-synchronization/528-how-use-sleep-method.html

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Generator {
	
	private static Random dice = new Random();
	
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
}
