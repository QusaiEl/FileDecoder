package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Qusai Elwazir
 *
 */
public class FileDecoder {
	/**
	 * holds the String to be used for the construction of a MsgTree
	 */
	private static String encodingString;
	
	/**
	 * holds the String of binary representing the encoded message
	 */
	private static String msgString;
	
	/**
	 * Continuously accepts user input for file decoding
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner userIn = new Scanner(System.in);
		System.out.print("Enter file to be decoded or type 0 to exit progam:");
		while(userIn.hasNext()) {
			String line = userIn.next();
			if(line.equals("0")) {
				System.out.println("Program ended.");
				System.exit(0);
			}
			try {
				decodeFromFile(line);
				System.out.println("End of message");
				System.out.println("");
			} catch(FileNotFoundException E) {
				System.out.println("file not found");
			}
			System.out.print("Enter file to be decoded or type 0 to exit progam:");
		}
		
		userIn.close();
	}
	
	/**
	 * Takes parameter of a String file name, constructing the file with the input string and prints the codes
	 * and message of the given file path
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	private static void decodeFromFile(String fileName) throws FileNotFoundException {
		encodingString = "";
		msgString = "";
		File f = new File(fileName);
		Scanner fileScnr = new Scanner(f);
		String line = fileScnr.nextLine();
		
		encodingString = line;
		if(fileScnr.hasNextLine()) {
			line = fileScnr.nextLine();
			if(line.charAt(0) != '0' && line.charAt(0) != '1') {
				encodingString += '\n'; // adds newline character if next line is binary
				encodingString += line;
				line = fileScnr.nextLine();
			}
		}
		
		MsgTree decodingTree = new MsgTree(encodingString);
		msgString = line;
		
		//print the codes used for message decoding
		System.out.println("character code\n" + "-------------------------");
		MsgTree.printCodes(decodingTree, "");
		System.out.println("");
		// decodes and prints the message
		System.out.println("Message:");
		decode(decodingTree, msgString);
		
	}
	
	/**
	 * Decodes String of binary into the message it is representing
	 * 
	 * @param codes
	 * @param msg
	 */
	public static void decode(MsgTree codes, String msg) {
		MsgTree current = codes;
		
		for(int i = 0; i < msg.length(); ++i) {
			if(current.payloadChar != '\u0000') { // null character
				System.out.print(current.payloadChar);
				current = codes;
			}
			if(msg.charAt(i) == '0') {
				current = current.left; // traverses left for 0
			} else {
				current = current.right; // traverses right for 1
			}
		}
		System.out.print(current.payloadChar); // prints last character not caught by the loop
	}
	
	
}
