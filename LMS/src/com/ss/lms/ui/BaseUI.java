package com.ss.lms.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.service.DarkVoid;

public class BaseUI {

	protected boolean executionLoopShouldStop = false;
	

	protected DarkVoid currentState; //the currentState is the foundation of my UI state machine. It points to a largely self-contained function 
					//that will take user input, handle database interactions, and then place another function in currentState to continue
	protected void executionLoop() { //runs an interface until forced to stop
		while(!executionLoopShouldStop) {
			currentState.execute();
		}
	}
	
	
	
	
	protected List<String> testIn = null; //input and output for junit tests
	protected int inputIndex = -1; //which string the class should take for input. Increments every time. Negative value means not in test mode 
	protected List<String> testOut = new ArrayList<String>();
	

	//Scanner input = new Scanner(System.in);
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); //Scanner is a giant PITA
	
	
	protected String takeInputString(String prompt) {
		if (inputIndex >= 0) { //if we're running this from inside a test
			testOut.add(prompt); //redirect output to testOut
			return testIn.get(inputIndex++); //returns the next input from the test case 
		}
				
		String ret = null;
		
		boolean validInput = false; //used to keep prompting the user until good input is given
		while(!validInput) {		
			System.out.println(prompt);
			try {
				ret = input.readLine();
			} catch (IOException e) {
				System.out.println("Error taking string input\n");
			}
			if (ret.length() == 0) {
				System.out.println("Please type something\n");
			} else if (ret.length() > 45) {
				System.out.println("Input must be no more than 45 characters.\nPlease enter a shorter input\n");
			}else validInput = true; //has passed both checks
		}

		return ret;
	}	
	
	protected String takeInputString(String prompt, boolean allow0) { //I hate that Java doesn't allow default parameters
		if (inputIndex >= 0) { //if we're running this from inside a test
			testOut.add(prompt); //redirect output to testOut
			return testIn.get(inputIndex++); //returns the next input from the test case 
		}
		
		
		
		String ret = null;
		
		boolean validInput = false; //used to keep prompting the user until good input is given
		while(!validInput) {		
			System.out.println(prompt);
			try {
				ret = input.readLine();
			} catch (IOException e) {
				System.out.println("Error taking string input\n");
			}
			if (ret.length() == 0 && !allow0) {
				System.out.println("Please type something\n");
			} else if (ret.length() > 45) {
				System.out.println("Input must be no more than 45 characters.\nPlease enter a shorter input\n");
			}else validInput = true; //has passed both checks
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
