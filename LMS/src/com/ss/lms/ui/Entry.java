package com.ss.lms.ui;

public class Entry extends BaseUI {
	
	
	public static void main(String[] args) {
	
		
		AdminUI aminui = new AdminUI();
		Entry entry = new Entry();
		
		while (true) {
			String prompt = "Welcome to the SS Library Management System. Which category of user are you?";
			InputOption[] options = {new InputOption(1, "Librarian"), new InputOption(2, "Administrator"), new InputOption(3, "Borrower")};
			int option = entry.takeInputOption(prompt, options);
			while(option != 2) { //force them to pick admin
				System.out.println("Please pick Admin");
				option = entry.takeInputOption(prompt, options);
			}
			
			switch(option) {
			case 1:
			case 2:
				aminui.currentState = aminui.adminEntryPoint; //tells the admin service to start from the beginning
				aminui.executionLoopShouldStop = false; //only necessary if we have already exited admin mode 
				aminui.executionLoop(); //tells the admin service to start the state machine
				break;
			}
			
			
		}
		
		
	}
}