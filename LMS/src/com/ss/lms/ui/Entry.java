package com.ss.lms.ui;

public class Entry extends BaseUI {
	
	
	public static void main(String[] args) {
	
		
		AdminUI adminUI = new AdminUI();
		LibrarianUI libUI = new LibrarianUI();
		BorrowerUI borrowerUI = new BorrowerUI();
		Entry entry = new Entry();
		
		while (true) {
			String prompt = "Welcome to the SS Library Management System. Which category of user are you?";
			InputOption[] options = {new InputOption(1, "Librarian"), new InputOption(2, "Administrator"), new InputOption(3, "Borrower")};
			int option = entry.takeInputOption(prompt, options);
			
			switch(option) {
			case 1:
				libUI.currentState = libUI.libEntryPoint;
				libUI.executionLoopShouldStop = false;
				libUI.executionLoop();
				break;
			case 2:
				adminUI.currentState = adminUI.adminEntryPoint; //tells the admin service to start from the beginning
				adminUI.executionLoopShouldStop = false; //only necessary if we have already exited admin mode 
				adminUI.executionLoop(); //tells the admin service to start the state machine
				break;
			case 3:
				borrowerUI.currentState = borrowerUI.borrowerEntryPoint;
				borrowerUI.executionLoopShouldStop = false;
				borrowerUI.executionLoop();
				break;
			}
			
			
		}
		
		
	}
}