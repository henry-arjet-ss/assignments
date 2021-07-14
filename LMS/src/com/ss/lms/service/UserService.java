package com.ss.lms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//parent class for librarian, administrator, and borrower services
//handles user I/O and a few other common things
public class UserService {

	protected boolean executionLoopShouldStop = false;
	protected void executionLoop() { //runs a given service until forced to stop
		while(!executionLoopShouldStop) {
			currentState.execute();
		}
	}
	
	
	
	DarkVoid currentState; //the currentState is the foundation of my UI state machine. It points to a largely self-contained function 
					//that will take user input, handle database interactions, and then place another function in currentState to continue
	
	protected List<String> testIn = null; //input and output for junit tests
	protected int inputIndex = -1; //which string the class should take for input. Increments every time. Negative value means not in test mode 
	protected List<String> testOut = new ArrayList<String>();
	
	protected ConnectionUtil connUtil = new ConnectionUtil();

	//Scanner input = new Scanner(System.in);
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); //Scanner is a giant PITA
	
	
	protected String takeInputString(String prompt) {
		if (inputIndex >= 0) { //if we're running this from inside a test
			testOut.add(prompt); //redirect output to testOut
			return testIn.get(inputIndex++); //returns the next input from the test case 
		}
		System.out.println(prompt);
		String ret = null;
		try {
			ret = input.readLine();
		} catch (IOException e) {
			System.out.println("Error taking string input");
		} 
		return ret;
	}
	protected int takeInputOption(String prompt, InputOption[] options) {
		
		if (inputIndex >= 0) { //if we're running this from inside a test
			testOut.add(prompt); //redirect output to testOut
			for (InputOption io : options) {
				testOut.add(String.format("%d) %s%n", io.index, io.text));//prints 'X) Option Text' 
			}
			return Integer.valueOf(testIn.get(inputIndex++)); //returns the next input from the test case 
		}  
		
		boolean validInput = false; //used to keep prompting the user until good input is given
		int lastOptionIndex = options.length; //used to determine what is valid input - assumes all options start at 1 and continue in exact series
		int option = 0; //the option the user has chosen
		
		while(!validInput) {			
			
			System.out.println(prompt);
			for (InputOption io : options) {
				System.out.printf("%d) %s%n", io.index, io.text);//prints 'X) Option Text' 
			}
			try { //check for valid int
				option = Integer.parseInt(input.readLine());
				
				if (option >= 1 && option <= lastOptionIndex) { //check int is in range
					validInput = true;

				}
			}catch (Exception e) {}//already handled by validInput remaining false
			if (!validInput) {
				System.out.printf("Please enter an integer between 1 and %d%n", lastOptionIndex);
			}
		}
		return option;
	} 

}
