/*package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.Branch;
import com.ss.lms.ui.InputOption;

public class LibrarianService extends UserService {
	
	HashMap<String, DarkVoid> states = null;
	private Branch branch = new Branch();
	

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
		
		states.put("updateCopies", () ->{                         //UPDATE BRANCH'S COPIES 
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			
			try {
				conn = cUtil.getConnection();
				BookDAO bdao= new BookDAO(conn);
				List<Book> books = bdao.readBooksAuthors();
				
				String prompt = "Select the book of which you wish to add copies to your branch:";
				int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < books.size(); i++) {
					options.add(new InputOption(i+1,books.get(i).getTitle() + " by " + books.get(i).getAuthor()));				
					booksMap[i] = books.get(i).getId();
				}
				options.add(new InputOption(books.size() + 1, "Quit to previous menu"));
				
				int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
				Book book = books.get(bookIndex - 1);
								
				if (bookIndex == books.size() + 1) { //quit to previous
					conn.close();
					currentState = states.get("optionSelect");
					return;
				}
				
				
				
			} catch (Exception e) {
				System.out.println("Error updating branch\r\nPlease see an administrator");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				currentState = states.get("optionSelect");
			}
		});
		
		states.put("updateBranch", () ->{                         //UPDATE BRANCH 
			
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BranchDAO bdao = new BranchDAO(conn);
				
				String prompt = "You have chosen to update the Branch with ID " + branch.getId() + " and name " + branch.getName()  
						+ "\r\nEnter ‘quit’ at any prompt to cancel operation."
						+ "\r\n\r\nCurrent name is " + branch.getName()  
						+ "\r\nEnter new name or press enter for no change:";
				String name = takeInputString(prompt, true); //use the overload that allows for 0 input
				
				if ("quit".equals(name)) {
					conn.close();
					currentState = states.get("optionSelect");
					return;
				}
				
				prompt = "Current address is " + branch.getAddress()  
						+ "\r\nEnter branch address or press enter for no change:";
				String address = takeInputString(prompt, true);
				
				if ("quit".equals(address)) {
					conn.close();
					currentState = states.get("optionSelect");
					return;
				}
				if (name.length() != 0) { //only update if the user wants it to change i.e. if their input is not ""
					branch.setName(name);
				}
				if (address.length() != 0) {
					branch.setAddress(address);
				}
				
				bdao.updateBranch(branch);
				conn.commit();
				System.out.println("Branch updated");
			} catch (Exception e) {
				System.out.println("Error updating branch\r\nPlease see an administrator");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				currentState = states.get("optionSelect");
			}
		});
		
		
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
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BranchDAO bdao= new BranchDAO(conn);
				List<Branch> branchs = bdao.readAllBranchs();
				
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
					conn.close();
					currentState = states.get("libEntryPoint");
					return;
				}
								
				branch = branchs.get(branchIndex - 1); //set the branch state
								
			} catch (Exception e) {
				System.out.println("error retrieving records");
				
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				currentState = states.get("optionSelect");
			}
		});
	}
	
}
*/