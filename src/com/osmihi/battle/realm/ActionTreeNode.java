/*********************************************************************************************************
 * Fantasy Adventure Game - by Othman Smihi
 * ICS 240 - Metropolitan State University - Summer 2012
 * 
 * I pledge that the contents of this file represent my own work, except as noted below.
 * References:
 * Class discussion & lectures
 * Data Structures textbook by Michael Main
 *
 * --------------
 * ActionTreeNode.java
 * --------------
 * The ActionTreeNode class is used as a node containing an Action and a collection of children for use 
 * as part of a general tree (unordered tree where nodes have many children).
 * 
 *********************************************************************************************************/

package com.osmihi.battle.realm;

import java.util.ArrayList;
import java.util.Collection;

public class ActionTreeNode {
	private Action data;
	private Collection<ActionTreeNode> children;
	
	public ActionTreeNode(Action a) {
		if (a == null) {throw new IllegalArgumentException("Can't have null for data.");}
		data = a;
		children = new ArrayList<ActionTreeNode>();
	}
	
	public Action getData() {return data;}
	public Collection<ActionTreeNode> getChildren() {return children;}
	public boolean hasChild(ActionTreeNode checkChild) {return children.contains(checkChild);}
	public boolean hasChild(Action checkChild) {
		boolean has = false;
		for (ActionTreeNode atn : children) {if (atn.getData() == checkChild) has = true;}
		return has;
	}
	
	public void setData(Action data) {this.data = data;}
	public void setChildren(Collection<ActionTreeNode> allKids) {children = allKids;}
	public void addChild(ActionTreeNode newKid) {children.add(newKid);}
	public void addChild(Action newKid) {
		ActionTreeNode nk = new ActionTreeNode(newKid);
		children.add(nk);
	}
	public void removeChild(ActionTreeNode kidToRemove) {children.remove(kidToRemove);}
	public void removeChild(Action kidToRemove) {
		for (ActionTreeNode atn : children) {if (atn.getData() == kidToRemove) children.remove(atn);}
	}
	
	@Override
	public String toString() {
		String toStr = "";
		if (data != null) {
			toStr = data.getName();
		}
		return toStr;
	}
}
