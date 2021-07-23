package com.ss.lms.ui;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//This class handles the UI for the Borrower menu
//It uses a light state machine, assigning functions to currentState and then continually executing currentState

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ss.lms.domains.Book;
import com.ss.lms.domains.Branch;
import com.ss.lms.service.BookLoanService;
import com.ss.lms.service.BookService;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.BranchService;
import com.ss.lms.service.DarkVoid;

public class BorrowerUI extends BaseUI {

	HashMap<String, DarkVoid> states = null; //state
	private Branch branch = new Branch(); //more state
	private int cardNum = 0; //even more state
	
	DarkVoid borrowerEntryPoint = () -> {
		populateHashMap();
		BorrowerService serv = new BorrowerService();
		
		String prompt = "Enter your card number:";
		boolean goodInput = false;//input loop
		while(!goodInput) {
			String newNumCopiesStr = takeInputString(prompt);
			try {
				cardNum = Integer.parseInt(newNumCopiesStr);
				if(serv.verifyCardNum(cardNum)) goodInput = true;
				else System.out.println("Invalid card number");

			}catch(Exception e) {
				System.out.println("Please enter an integer");
			}
		}
		currentState = states.get("borrowerOptionSelect");

	};
	
	private void populateHashMap() {
		states = new HashMap<String, DarkVoid>();
		states.put("return", () ->{
			BookService serv = new BookService();
			List<Book> books = serv.readLoanedBooks(cardNum);
			
			String prompt = "Select the book you wish to return:";
			int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < books.size(); i++) {
				options.add(new InputOption(i+1,books.get(i).getTitle() + " by " + books.get(i).getAuthor() + "\r\n    From " + books.get(i).getBranch()));				
				booksMap[i] = books.get(i).getId();
			}
			options.add(new InputOption(books.size() + 1, "Quit to options menu"));
			int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value
			
			if (bookIndex == books.size() + 1) { //quit to previous
				currentState = states.get("borrowerOptionSelect");
				return;
			}
			
			Book book = books.get(bookIndex - 1);
			
			BookLoanService loanServ = new BookLoanService();
			loanServ.returnBook(book, cardNum);
			currentState = states.get("borrowerOptionSelect");
			
		});
		states.put("bookSelectCheckout", () ->{
			BookService serv = new BookService();
			List<Book> books = serv.readBookBranch(branch);
			
			String prompt = "Select the book you wish to check out:";
			int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < books.size(); i++) {
				options.add(new InputOption(i+1,books.get(i).getTitle() + " by " + books.get(i).getAuthor()));				
				booksMap[i] = books.get(i).getId();
			}
			options.add(new InputOption(books.size() + 1, "Quit to options menu"));
			int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value
			
			if (bookIndex == books.size() + 1) { //quit to previous
				currentState = states.get("borrowerOptionSelect");
				return;
			}
			
			Book book = books.get(bookIndex - 1);
			
			BookLoanService loanServ = new BookLoanService();
			loanServ.checkout(book, branch, cardNum);
			currentState = states.get("borrowerOptionSelect");
			
		});
		states.put("libSelectCheckout", () ->{                         //SELECT LIBRARY BRANCH 
			
			
			BranchService serv = new BranchService();
			List<Branch> branchs = serv.read();
				
			String prompt = "Select the branch from which you wish to check out:";
			int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < branchs.size(); i++) {
				options.add(new InputOption(i+1,branchs.get(i).getName() + " | " + branchs.get(i).getAddress()));				
				branchsMap[i] = branchs.get(i).getId();
			}
			options.add(new InputOption(branchs.size() + 1, "Quit to previous menu"));
			int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value
			
			if (branchIndex == branchs.size() + 1) { //quit to previous
				currentState = states.get("borrowerOptionSelect");
				return;
			}
							
			branch = branchs.get(branchIndex - 1); //set the branch state
			currentState = states.get("bookSelectCheckout");
							
		});
		
		states.put("borrowerOptionSelect", () -> {
			
			String prompt = "Welcome, borrower\nWhich operation would you like to perform?";
			InputOption[] options = {new InputOption(1, "Check out a book"), new InputOption(2, "Return a book"), new InputOption(3, "Quit to previous menu")};
			int option = takeInputOption(prompt, options);	
			switch (option) {
			case 1:
				currentState = states.get("libSelectCheckout");
				break;
			case 2:
				currentState = states.get("return");
				break;
			case 3:
				executionLoopShouldStop = true;
				break;
			}
		});
	}
}
