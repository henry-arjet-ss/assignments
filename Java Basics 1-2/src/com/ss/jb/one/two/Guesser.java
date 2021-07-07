package com.ss.jb.one.two;

import java.util.Scanner;
import java.util.Random;

/**
 * Second assignment in Java Basics 1
 * A guessing game where the user must guess a number between 1-100 with a margin of +-10
 * Henry Arjet
 * July 2021 Cloud Engineering
 */


public class Guesser {
	//this is also going to be a static class
	private static int number; //the number the user is trying to guess
	private static int attempt = 1; //user only has five guesses - this keeps track
	private static int guess; //the user's guess
	private static boolean victory = false; //if the user has won
	
	
	private static boolean guess() { //this method processes a user's guess and returns if correct
		
		//input
		boolean validInput = false; //we want to keep prompting the user until they give input we can use, thus the while loop
		while(!validInput) {
			Scanner input = new Scanner(System.in);
			if (input.hasNextInt()) { //check for valid int
				guess = input.nextInt();
				if (guess >= 1 && guess <= 100) { //check int is in range
					validInput = true;

				}
			}
			if (!validInput) {
				System.out.println("Please enter an integer between 1 and 100:");
			}
		} //end input
		
		//check the guess
		int distance = Math.abs(number - guess);
				
		//correct answers
		if (distance == 0) {
			System.out.println("Exactly right!");
			return true;
		} 
		if (distance <= 10) {
			System.out.printf ("Close enough. The answer was %d.%n", number);			
			return true;
		} 
		
		//incorrect answer
		if (attempt == 5) {//the user is out of attempts
			System.out.printf("Sorry, the answer was %d.%n", number);
			return false;
		}
		attempt++; //you have one fewer guesses  
		System.out.println("I'm afraid not. Please guess again:"); //user is not out of attempts
		return false; 
	}
	
	public static void main(String[] args) {
		Random randy = new Random();
		number = randy.nextInt(100) + 1;//nextInt(100) will give us 0-99, so lets shift that range over to the right
		boolean victory = false; //if the user has won
		System.out.println("Welcome! Please enter a number between 1 and 100:");
		
		while (attempt <= 5 && !victory) { //loop that keeps guessing until the user wins or runs out of guesses 
			victory = guess();
		}
		
		//if the user won or lost that is handled by the guess function
		//all that is left to do is exit
		return;
	}

}
