package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Qusai Elwazir
 *
 */
public class MsgTree {
	/*
	 * stores the character being held by this MsgTree
	 */
	public char payloadChar = '\u0000';
	/**
	 *  Child references of this MsgTree
	 */
	public MsgTree left, right;
	
	/**
	 * Iteratively creates a binary tree with left branches representing an encoded 0, and right branches representing an encoded 1
	 * from a preOrder String representation of the tree
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) { 
		
		MsgTree curr = this;
		MsgTree currOther = null;
		MsgTree prev = null;
		
		ArrayList<MsgTree> currOtherList = new ArrayList<MsgTree>(); // stores unserved right MsgTrees
		
		for(int i = 0; i < encodingString.length(); ++i) {
			if(this.getSatisfiedChilderen()) { // checks base case if tree is complete
				return;
			}
			if(curr.getSatisfiedChilderen()) { // checks if this current branch is complete // needs to check one time for each step up	
				curr = currOtherList.get(currOtherList.size() - 1); // after moving up in the tree
				currOtherList.remove(currOtherList.size() - 1);		// curr is set to the deepest
			}
			
			if(encodingString.charAt(i) == '^') { // case where new tree starts // also case for left child to be addressed
				prev = curr;
				curr = new MsgTree();
				currOther = new MsgTree();
				if(prev.left == null) { // issue with 2 ^ in a row doesn't go down far enough,
					prev.left = curr;   // think the issue is that ^ condition only steps down once and does nothing else
					prev.right = currOther;
					currOtherList.add(prev.right);
				}
				
			} else if(encodingString.charAt(i) != '^') { // case where the value of string is a leaf (left)
				curr.payloadChar = encodingString.charAt(i);
			}
			
		}
		
	} 

	/**
	 * Constructor for leaf MsgTrees
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}
	
	/**
	 * default constructor
	 */
	public MsgTree() {
		
	}
	
	/**
	 * Check to ensure this branch is complete
	 * 
	 * @return if this current MsgTree's children are complete
	 */
	private boolean getSatisfiedChilderen() {
		if(this.payloadChar != '\u0000') {
			return true;
		}else if(this.left == null || this.right == null) {
			return false;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Recursively prints the codes associated as MsgTree
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		if(root.payloadChar == '\u0000') { // recursive case
			printCodes(root.left, code + '0');
			printCodes(root.right, code + '1');
		} else {
			if(root.payloadChar == ' ') {
				System.out.print("  " + "\" \"" + "     "); // fringe case for space character
				System.out.println(code);
			} else if(root.payloadChar == '\n') {
				System.out.print("   " + "\\n" + "     "); // fringe case for newline character
				System.out.println(code);
			} else {
			System.out.print("   " + root.payloadChar + "      "); // base case for all char that are not formatting based
			System.out.println(code);
			}
		}
	}
}
