/**
 * 
 */
package com.ss.jb.one.one;

/**
 * First assignment in JavaBasics 1
 * Henry Arjet
 * July 2021 Cloud Engineering
 */
public class Printer {
	//everything is static because I never need to instantiate it
	private static int starCount = 1;
	private static int spaceCount = 5;
	private static int dotCount = 9;
	private static int sectionCount = 1; //for the "1)" section divider 
	
	private static void endl() { //because I don't want to type the whole thing every time and I don't know how to use macros in Java 
		System.out.println();
	}
	
	private static void starPrint() { //prints starCount stars
		for (int i = 0; i < starCount; i++) {
			System.out.print('*');
		}
	}
	private static void spacePrint() { //prints spaceCount spaces
		for (int i = 0; i < spaceCount; i++) {
			System.out.print(' ');
		}
	}
	private static void printSection() { //prints sectonCount) and new line then increments section count 
		System.out.println(Integer.toString(sectionCount)+')');
		sectionCount++;
	}
	private static void printDots() { //prints the dots, a new line, then increments the dots;
		for (int i = 0; i < dotCount; i++) {
			System.out.print('.');
		}
		endl();
		dotCount++;
	}
	public static void main(String[] args) {//I tried to do this the clever way, and while it is clever, it's worse than hardcoding it in almost every way. 
		printSection();
		while (starCount <= 4) {
			starPrint();
			starCount++;
			endl();
		}
		printDots();

		printSection();
		printDots();
		
		starCount = 4; //because of my increments it's at 5 right now
		while (starCount >= 1) {
			starPrint();
			starCount--;
			endl();
		}

		printSection();
		starCount = 1;//again we must reset
		while(spaceCount >= 2) {
			spacePrint();
			spaceCount--;
			starPrint();
			starCount+=2;
			endl();
		}
		printDots();
		
		printSection();
		printDots();
		spaceCount = 2;
		starCount = 7;
		while (starCount >=1) {
			spacePrint();
			spaceCount++;
			starPrint();
			starCount-=2;
			endl();
		}
	}
}