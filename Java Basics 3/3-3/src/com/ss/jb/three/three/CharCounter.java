package com.ss.jb.three.three;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
* Third assignment in JavaBasics 3 
* This class counts the number of times a (command line specified) char occurs in a file 
* Specifically Much Ado About Nothing Act 4 Scene 2
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class CharCounter {
	
	public static void main(String[] args) throws IOException {
		//input validation
		if (args.length != 1) {
			System.out.println("Please give me a single character");
			return;
		}
		if (args[0].length()!=1) {
			System.out.println("Please give me a single character");
			return;
		}
		FileReader in = null;
		try {
			in = new FileReader("resources/input.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Character target = args[0].charAt(0);//turns the 'string' to a native char
		

		
		int acc = 0; //accumulator
		char c;
		while((c = (char) in.read()) != (char)-1) {
			if (target.equals(c)) {
				acc++;
			}
		}
		
		System.out.printf("The character %s occured %d times in Much Ado 4:2.%n", target, acc);
	}
}
