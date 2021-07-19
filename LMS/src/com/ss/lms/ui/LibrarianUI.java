package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookCopies;
import com.ss.lms.domains.Branch;
import com.ss.lms.service.BookService;
import com.ss.lms.service.BranchService;
import com.ss.lms.service.DarkVoid;

public class LibrarianUI extends BaseUI {
	HashMap<String, DarkVoid> states = null; //state
	private Branch branch = new Branch(); //more state
	

	DarkVoid libEntryPoint = () -> {
		populateHashMap();
		String prompt = "Welcome, librarian\nWhich operation would you like to perform?";
		InputOption[] options = {new InputOption(1, "Select the branch you manage"), new InputOption(2, "Quit to previous menu")};
		int option = takeInputOption(prompt, options);	
		switch (option) {
		case 1:
			currentState = states.get("libSelect");
			break;
		case 2:
			executionLoopShouldStop = true;
			break;
		}
	};
	
	private void populateHashMap() {
		states = new HashMap<String, DarkVoid>();
		
		states.put("libEntryPoint", libEntryPoint);
		
		states.put("optionSelect", () ->{                         //SELECT OPTION 
			
			String prompt = "";
			InputOption[] options = {new InputOption(1, "Update the details of the library"), new InputOption(2, "Add copies of a book to the branch"), new InputOption(3, "Quit to previous menu")};
			int option = takeInputOption(prompt, options);	
			switch (option) {
			case 1:
				currentState = states.get("updateBranch");
				break;
			case 2:
				currentState = states.get("updateCopies");
				break;
			case 3:
				currentState = states.get("libSelect");
				break;
			}
		});
		
		states.put("libSelect", () ->{                         //SELECT LIBRARY BRANCH 
			
			
			BranchService serv = new BranchService();
			List<Branch> branchs = serv.read();
				
			String prompt = "Select your branch:";
			int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < branchs.size(); i++) {
				options.add(new InputOption(i+1,branchs.get(i).getName() + " | " + branchs.get(i).getAddress()));				
				branchsMap[i] = branchs.get(i).getId();
			}
			options.add(new InputOption(branchs.size() + 1, "Quit to previous menu"));
			int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value
			
			if (branchIndex == branchs.size() + 1) { //quit to previous
				currentState = states.get("libEntryPoint");
				return;
			}
							
			branch = branchs.get(branchIndex - 1); //set the branch state
			currentState = states.get("optionSelect");
							
		});
	
		states.put("updateCopies", () ->{                         //UPDATE BRANCH'S COPIES 
			BookService bookServ = new BookService();
			
			List<Book> books = bookServ.read();
			
			String prompt = "Select the book of which you wish to add copies to your branch:";
			int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < books.size(); i++) {
				options.add(new InputOption(i+1,books.get(i).getTitle() + " by " + books.get(i).getAuthor()));				
				booksMap[i] = books.get(i).getId();
			}
			options.add(new InputOption(books.size() + 1, "Quit to previous menu"));
			
			int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
										
			if (bookIndex == books.size() + 1) { //quit to previous
				currentState = states.get("optionSelect");
				return;
			}
			Book book = books.get(bookIndex - 1);
			
			BranchService serv = new BranchService();
			BookCopies bc = serv.readBookCopies(branch, book);
			
			boolean bcShouldCreate = false; //if the record doesn't exist, it must be added
			if (bc.getBookID() == 0) { 
				bcShouldCreate = true;
				bc.setBookID(book.getId()); //if we try to pull a non-existent record, it will show up as all 0s
				bc.setBranchID(branch.getId()); //so we must manually set the book and branch IDs
			}
			
			
			prompt = "Current number of copies: " + bc.getNumCopies()
				+ "\r\nEnter new number of copies:";
			
			int newNumCopies = 0;
			boolean goodInput = false;//input loop
			while(!goodInput) {
				String newNumCopiesStr = takeInputString(prompt);
				try {
					newNumCopies = Integer.parseInt(newNumCopiesStr);
					if(newNumCopies >= bc.getNumCopies()) goodInput = true;
					else System.out.println("Please enter an integer greater than or equal to " + bc.getNumCopies());

				}catch(Exception e) {
					System.out.println("Please enter an integer");
				}
			}
			bc.setNumCopies(newNumCopies);
			serv.setBookCopies(bc, bcShouldCreate);	
			
			currentState = states.get("optionSelect");
		});
		
		states.put("updateBranch", () ->{                         	   //UPDATE LIBRARY BRANCH
			
			BranchService serv = new BranchService();
					
			String prompt = "Current name is " + branch.getName()  
			+ "\r\nEnter new branch name or press enter for no change:";
			String name = takeInputString(prompt, true);
			
			prompt = "Current address is " + branch.getAddress()  
			+ "\r\nEnter new branch address or press enter for no change:";
			String address = takeInputString(prompt, true);
			
			if (name.length() != 0) { //only update if the user wants it to change i e if their input is not ""
				branch.setName(name);
			}
			if (address.length() != 0) {
				branch.setAddress(address);
			}
			
			serv.update(branch);							
			currentState = states.get("optionSelect");
		});
	}
}