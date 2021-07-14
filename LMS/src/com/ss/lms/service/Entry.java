package com.ss.lms.service;

public class Entry extends UserService {
	
	
	public static void main(String[] args) {
	
		UserService entryService = new UserService();
		AdminService aminS = new AdminService();
		
		while (true) {
			String prompt = "Welcome to the SS Library Management System. Which category of user are you?";
			InputOption[] options = {new InputOption(1, "Librarian"), new InputOption(2, "Administrator"), new InputOption(3, "Borrower")};
			int option = entryService.takeInputOption(prompt, options);
			while(option != 2) { //force them to pick admin
				System.out.println("Please pick Admin");
				option = entryService.takeInputOption(prompt, options);
			}
			
			switch(option) {
			case 2:
				aminS.currentState = aminS.adminEntryPoint; //tells the admin service to start from the beginning
				aminS.executionLoopShouldStop = false; //only necessary if we have already exited admin mode 
				aminS.executionLoop(); //tells the admin service to start the state machine
			}
			
			
		}
		
		
	}
}