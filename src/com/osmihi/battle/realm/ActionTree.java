/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * Class discussion & lectures
 * Data Structures textbook by Michael Main, p458
 *
 * --------------
 * ActionTree.java
 * --------------
 * The ActionTree class is used to create a structured order in which a Hero can gain new Actions. A hero
 * will only be allowed to gain new Actions for which they already know the prerequisite. This is intended
 * to make the HeroTypes different while still allowing players to customize. The ActionTree is not a
 * binary tree like the ones we've studied in class, but rather a general tree which is a lot simpler (and
 * probably less useful overall, but it seems appropriate for this situation). An ActionTree is not ordered, 
 * and must have an ActionTreeNode for its root. An ActionTreeNode in the Action tree may have any number 
 * of child ActionTreeNodes. 
 * 
 *********************************************************************************************************/

package com.osmihi.battle.realm;

public class ActionTree {
	private ActionTreeNode root;
	
	public ActionTree(Action rt) {
		ActionTreeNode newNode = new ActionTreeNode(rt);
		root = newNode;
	}

	public ActionTree() {
		root = null;
	}
	
	public ActionTreeNode locate(Action toFind) {
		return locate(root, toFind);
	}
	
	public ActionTreeNode locate(ActionTreeNode atn, Action toFind) {
		if (atn == null || toFind == atn.getData()) {
			// return null if provided with null
			// check this node.
			return atn;
		} else {
			for (ActionTreeNode aNode : atn.getChildren()) {
				ActionTreeNode f = locate(aNode,toFind);
				if (f != null && f.getData() == toFind) {
					return f;
				}
			}
			return null;
		}
	}
	
	public void insert(ActionTreeNode atn, Action toAdd) {
		if (atn != null && toAdd != null) {
			atn.addChild(toAdd);
		} 		
	}
	
	public void insert(Action parent, Action child) {
		insert(locate(parent), child);
	}
	
	public void print(ActionTreeNode atn, int count) {
		if (atn == null) {return;}
		String margin = "";
		for (int i = 0; i < count; i++) {margin += "  ";}
		System.out.println(margin + atn);
		for (ActionTreeNode an : atn.getChildren()) {print(an, count+1);}
	}
	
	public void print() {
		print(root,0);
	}
	
	public ActionTreeNode getRoot() {return root;}

	public void setRoot(ActionTreeNode root) {this.root = root;}
	
//	public static void main(String[] args) {
//		Census.populate();
//		ActionTree at = new ActionTree(Census.getAction("Knife"));
//		at.insert(at.getRoot(), Census.getAction("Club"));
//		at.insert(at.locate(Census.getAction("Knife")), Census.getAction("Shortsword"));
//		at.insert(at.locate(Census.getAction("Shortsword")), Census.getAction("Longsword"));
//		at.insert(at.locate(Census.getAction("Club")), Census.getAction("Hide"));
//		at.insert(at.locate(Census.getAction("Hide")), Census.getAction("Haste"));
//		at.insert(at.locate(Census.getAction("Club")), Census.getAction("Rage"));
//		
//		at.print();
//	}
	
}
