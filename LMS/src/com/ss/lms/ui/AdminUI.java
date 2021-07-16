package com.ss.lms.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ss.lms.domains.Author;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.Borrower;
import com.ss.lms.domains.Branch;
import com.ss.lms.domains.Genre;
import com.ss.lms.domains.Publisher;
import com.ss.lms.service.AuthorService;
import com.ss.lms.service.BookService;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.BranchService;
import com.ss.lms.service.DarkVoid;
import com.ss.lms.service.GenreService;
import com.ss.lms.service.PublisherService;

public class AdminUI extends BaseUI {
	private Map<String, DarkVoid> states;
	
	DarkVoid update = () -> {
		String prompt = "What type of record would you like to update?";
		InputOption[] options = {new InputOption(1, "Author"), new InputOption(2, "Book"), new InputOption(3, "Genre"), new InputOption(4, "Publisher"), 
				new InputOption(5, "Borrower"), new InputOption(6, "Library Branch"), new InputOption(7, "Back to operation select") };
		int option = takeInputOption(prompt, options);
		switch (option) {
		case 1:
			currentState = states.get("updateAuthor");
			break;
		case 2:
			currentState = states.get("updateBook");
			break;
		case 3:
			currentState = states.get("updateGenre");
			break;
		case 4:
			currentState = states.get("updatePublisher");
			break;
		case 5:
			currentState = states.get("updateBorrower");
			break;
		case 6:
			currentState = states.get("updateBranch");
			break;
		case 7: //go back
			currentState = states.get("adminEntryPoint");
			break;
		}
	};
	
	
	DarkVoid read = () -> {
		String prompt = "What type of record would you like to retrieve?";
		InputOption[] options = {new InputOption(1, "Author"), new InputOption(2, "Book"), new InputOption(3, "Genre"), new InputOption(4, "Publisher"), 
				new InputOption(5, "Borrower"), new InputOption(6, "Library Branch"), new InputOption(7, "Back to operation select") };
		int option = takeInputOption(prompt, options);
		switch (option) {
		case 1:
			currentState = states.get("readAuthor");
			break;
		case 2:
			currentState = states.get("readBook");
			break;
		case 3:
			currentState = states.get("readGenre");
			break;
		case 4:
			currentState = states.get("readPublisher");
			break;
		case 5:
			currentState = states.get("readBorrower");
			break;
		case 6:
			currentState = states.get("readBranch");
			break;
		case 7: //go back
			currentState = states.get("adminEntryPoint");
			break;
		}	
	};
	
	DarkVoid create = () -> {
		String prompt = "What type of record would you like to create?";
		InputOption[] options = {new InputOption(1, "Author"), new InputOption(2, "Book"), new InputOption(3, "Genre"), new InputOption(4, "Publisher"), 
				new InputOption(5, "Borrower"), new InputOption(6, "Library Branch"), new InputOption(7, "Back to operation select") };
		int option = takeInputOption(prompt, options);
		switch (option) {
		case 1:
			currentState = states.get("createAuthor");
			break;
		case 2:
			currentState = states.get("createBook");
			break;
		case 3:
			currentState = states.get("createGenre");
			break;
		case 4:
			currentState = states.get("createPublisher");
			break;
		case 5:
			currentState = states.get("createBorrower");
			break;
		case 6:
			currentState = states.get("createBranch");
			break;
		case 7: //go back
			currentState = states.get("adminEntryPoint");
			break;
		}	
	};
	
	DarkVoid delete = () -> {
		String prompt = "What type of record would you like to delete?";
		InputOption[] options = {new InputOption(1, "Author"), new InputOption(2, "Book"), new InputOption(3, "Genre"), new InputOption(4, "Publisher"), 
				new InputOption(5, "Borrower"), new InputOption(6, "Library Branch"), new InputOption(7, "Back to operation select") };
		int option = takeInputOption(prompt, options);
		switch (option) {
		case 1:
			currentState = states.get("deleteAuthor");
			break;
		case 2:
			currentState = states.get("deleteBook");
			break;
		case 3:
			currentState = states.get("deleteGenre");
			break;
		case 4:
			currentState = states.get("deletePublisher");
			break;
		case 5:
			currentState = states.get("deleteBorrower");
			break;
		case 6:
			currentState = states.get("deleteBranch");
			break;
		case 7: //go back
			currentState = states.get("adminEntryPoint");
			break;
		}	
	};
	
	DarkVoid adminEntryPoint = () -> {
		populateHashMap();
		String prompt = "Welcome, Admin \nWhich operation would you like to perform?";
		InputOption[] options = {new InputOption(1, "Create"), new InputOption(2, "Read"), new InputOption(3, "Update"), new InputOption(4, "Delete"), 
				new InputOption(5, "Override Due Date"), new InputOption(6, "Back to Main Menu") };
		int option = takeInputOption(prompt, options);	
		switch (option) {
		case 1:
			currentState = create;
			break;
		case 2:
			currentState = read;
			break;
		case 3:
			currentState = update;
			break;
		case 4:
			currentState = delete;
			break;
		case 6:
			executionLoopShouldStop = true;
		}
	};
	
	private void populateHashMap() {
		states = new HashMap<String, DarkVoid>();
		
		states.put("createBook", () -> {                             //CREATE BOOK
			Book book = new Book ();
			book.setId(0);
			String prompt = "Enter Book Title:";
			book.setTitle(takeInputString(prompt));
			
			//get the available authors, then prompt the user to chose one of them
			prompt = "Pick one or more authors:";		
			AuthorService aServe = new AuthorService();
			List<Author> authors = aServe.read();

					
			boolean authorSelectLoopShouldClose = false;
			int[] authorsMap = new int[authors.size()]; //maps from optionIndex - 1 to authorID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < authors.size(); i++) {
				options.add(new InputOption(i+1,authors.get(i).getName()));				
				authorsMap[i] = authors.get(i).getId();
			}
			List<Integer>authorIndices = new ArrayList<Integer>(); //stores the return values
			authorIndices.add(takeInputOption(prompt, options.toArray(new InputOption[options.size()]))); //we know that there must be at least one author
			
			options.add(new InputOption(authors.size() + 1, "No more authors"));
			
			
			while (!authorSelectLoopShouldClose) {
				int option = takeInputOption(prompt, (InputOption[])options.toArray(new InputOption[options.size()]));
				if (option == authors.size() + 1) { //if the user says no more authors
					authorSelectLoopShouldClose = true;
				} else authorIndices.add(option);
			}
			
			List<Integer> authorIDs = new ArrayList<Integer>();
			for (int i = 0; i < authorIndices.size(); i++) {
				authorIDs.add(authorsMap[authorIndices.get(i)-1]); //because the options are 1-indexed
			}
			//Same thing with the genres
			prompt = "Pick one or more genres:";
			GenreService gServe = new GenreService();
			List<Genre> genres = gServe.read();
			
			boolean genreSelectLoopShouldClose = false;
			int[] genresMap = new int[genres.size()]; //maps from optionIndex - 1 to genreID
			options = new ArrayList<InputOption>();
			for (int i = 0; i < genres.size(); i++) {
				options.add(new InputOption(i+1,genres.get(i).getName()));				
				genresMap[i] = genres.get(i).getId();
			}
			List<Integer>genreIndices = new ArrayList<Integer>(); //stores the return values
			genreIndices.add(takeInputOption(prompt, options.toArray(new InputOption[options.size()]))); //we know that there must be at least one genre
			
			options.add(new InputOption(genres.size() + 1, "No more genres"));
			
			
			while (!genreSelectLoopShouldClose) {
				int option = takeInputOption(prompt, (InputOption[])options.toArray(new InputOption[options.size()]));
				if (option == genres.size() + 1) { //if the user says no more genres
					genreSelectLoopShouldClose = true;
				} else genreIndices.add(option);
			}
			
			List<Integer> genreIDs = new ArrayList<Integer>();
			for (int i = 0; i < genreIndices.size(); i++) {
				genreIDs.add(genresMap[genreIndices.get(i)-1]); //because the options are 1-indexed
			}
			
			//And pick a publisher
			prompt = "Pick a publisher:";
			PublisherService pServe = new PublisherService();
			List<Publisher> publishers = pServe.read();
			
			int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
			options = new ArrayList<InputOption>();
			for (int i = 0; i < publishers.size(); i++) {
				options.add(new InputOption(i+1,publishers.get(i).getName()));				
				publishersMap[i] = publishers.get(i).getId();
			}
			
			int publisherIndex;  //stores the return values
			publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //we know that there must be at least one publisher
			book.setPublisher(new Publisher(publishersMap[publisherIndex-1], "", "", "")); //because the options are 1-indexed
			
			BookService serv = new BookService();
			serv.addBookFull(book, genreIDs, authorIDs);
			currentState = states.get("adminEntryPoint");

			
		});
		
		states.put("readBook", () -> {                               //READ BOOK
			
			BookService serv = new BookService();
			List<Book> books = serv.read();		
			System.out.println();
			for (Book a : books) {
				System.out.println(a.getTitle() + " by " + a.getAuthor()); //the author field also handles multiple authors on the DAO side								
			}
			
			
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updateBook", () ->{                         	   //UPDATE BOOK
			
			BookService serv = new BookService();
				
			String prompt = "Pick a book to update:";
			List<Book> books = serv.read();
			int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < books.size(); i++) {
				options.add(new InputOption(i+1,books.get(i).getTitle()));				
				booksMap[i] = books.get(i).getId();
			}
			options.add(new InputOption(books.size() + 1, "Quit to previous menu"));
			int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
			
			if (bookIndex == books.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Book book = books.get(bookIndex - 1);
			
			prompt = "Current title is " + book.getTitle()  
			+ "\r\nEnter new book name or press enter for no change:";
			String name = takeInputString(prompt, true);
			
			if (name.length() != 0) { //only update if the user wants it to change i.e. if their input is not ""
				book.setTitle(name);
			}
			
					//GET AUTHORS
			List<Author> authors = serv.readBookAuthors(book); //used in final step
			
			StringBuilder authorString = new StringBuilder(authors.get(0).getName()); //a string to display the names of the authors
			
			for (int i = 1; i < authors.size(); i++) {
				authorString.append(authors.get(i).getName());
			}
			
			prompt = "Current author" + (authors.size()>1?"s are":" is") + " "+ authorString.toString()  
					+ "\r\nWould you like to change this?";
			InputOption[] optionsArr = {new InputOption(1, "No Change"), new InputOption(2, "New Author(s)")};
			int authorsShouldChange = takeInputOption(prompt, optionsArr); //1 is no, 2 is yes
			
			List<Integer> authorIDs= null;
			
			if(authorsShouldChange == 2) {
				
				prompt = "Select one or more new authors:";
				
				AuthorService aServe = new AuthorService();
				
				List<Author> allAuthors = aServe.read();
				
				boolean authorSelectLoopShouldClose = false;
				int[] allAuthorsMap = new int[allAuthors.size()]; //maps from optionIndex - 1 to authorID
				options = new ArrayList<InputOption>();
				for (int i = 0; i < allAuthors.size(); i++) {
					options.add(new InputOption(i+1,allAuthors.get(i).getName()));				
					allAuthorsMap[i] = allAuthors.get(i).getId();
				}
				List<Integer>authorIndices = new ArrayList<Integer>(); //stores the return values
				authorIndices.add(takeInputOption(prompt, options.toArray(new InputOption[options.size()]))); //we know that there must be at least one author


				options.add(new InputOption(allAuthors.size() + 1, "No More Authors"));
				
				
				while (!authorSelectLoopShouldClose) {
					int option = takeInputOption(prompt, (InputOption[])options.toArray(new InputOption[options.size()]));
					if (option == allAuthors.size() + 1) { //if the user says no more allAuthors
						authorSelectLoopShouldClose = true;
					} else authorIndices.add(option);
				}
				
				authorIDs = new ArrayList<Integer>();
				for (int i = 0; i < authorIndices.size(); i++) {
					authorIDs.add(allAuthorsMap[authorIndices.get(i)-1]); //because the options are 1-indexed
				}
			} 
			
			//GET GENRE
			
			List<Genre> genres = serv.readBookGenres(book);
			
			
			StringBuilder genreString = new StringBuilder(genres.get(0).getName()); //a string to display the names of the genres
			
			for (int i = 1; i < genres.size(); i++) {
				genreString.append(" and ");
				
				genreString.append(genres.get(i).getName());
			}
			
			prompt = "Current genre" + (genres.size()>1?"s are":" is") + " "+ genreString.toString()  
			+ "\r\nWould you like to change this?";

			optionsArr[1] = new InputOption(2, "New Genre");
			int genresShouldChange = takeInputOption(prompt, optionsArr); //1 is no, 2 is yes
			
			List<Integer> genreIDs = null;
			
			if(genresShouldChange == 2) {
				GenreService gServ = new GenreService();
				List<Genre> allGenres = gServ.read();
				
				prompt = "Select one or more new genre:";
		
				
				boolean genreSelectLoopShouldClose = false;
				int[] allGenresMap = new int[allGenres.size()]; //maps from optionIndex - 1 to genreID
				options = new ArrayList<InputOption>();
				for (int i = 0; i < allGenres.size(); i++) {
					options.add(new InputOption(i+1,allGenres.get(i).getName()));				
					allGenresMap[i] = allGenres.get(i).getId();
				}
				List<Integer>genreIndices = new ArrayList<Integer>(); //stores the return values
				genreIndices.add(takeInputOption(prompt, options.toArray(new InputOption[options.size()]))); //we know that there must be at least one genre
		
		
				options.add(new InputOption(allGenres.size() + 1, "No More Genres"));
				
				
				while (!genreSelectLoopShouldClose) {
					int option = takeInputOption(prompt, (InputOption[])options.toArray(new InputOption[options.size()]));
					if (option == allGenres.size() + 1) { //if the user says no more allGenres
						genreSelectLoopShouldClose = true;
					} else genreIndices.add(option);
				}
				
				genreIDs = new ArrayList<Integer>();
				for (int i = 0; i < genreIndices.size(); i++) {
					genreIDs.add(allGenresMap[genreIndices.get(i)-1]); //because the options are 1-indexed
				}
			}
			
			//GET PUBLISHER
			PublisherService pServ = new PublisherService();
			
			prompt = "Current publisher is " + pServ.readByID(book.getPublisher().getId()).getName()
			//prompt = "Current publisher is " + pdao.readPublisherByID(book.getPublisher().getId()).getName()
			+ "\r\nSelect a new publisher or select \"No Change\"";
			List<Publisher> publishers = pServ.read();
			
			int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
			options = new ArrayList<InputOption>();
			for (int i = 0; i < publishers.size(); i++) {
				options.add(new InputOption(i+1,publishers.get(i).getName()));				
				publishersMap[i] = publishers.get(i).getId();
			}
			options.add(new InputOption(options.size()+1, "No Change"));
			
			int publisherIndex;  //stores the return values
			publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //we know that there must be at least one publisher
			if (publisherIndex != publishers.size() + 1) {
				book.setPublisher(new Publisher(publishersMap[publisherIndex-1], "", "", "")); //because the options are 1-indexed
			} //else publisher stays the same
			
			serv.updateBookFull(book, genreIDs, authorIDs); //if unchanged, genreIDs and authorIDs will be null; That will be handled by the service							
			currentState = states.get("adminEntryPoint");
			
			
		});
		
		states.put("deleteBook", () ->{                         	   //DELETE BOOK
			
			BookService serv = new BookService();
				
				String prompt = "Pick a book to delete:";
				List<Book> books = serv.read();
				int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < books.size(); i++) {
					options.add(new InputOption(i+1,books.get(i).getTitle()));				
					booksMap[i] = books.get(i).getId();
				}
				options.add(new InputOption(books.size() + 1, "Quit to previous menu"));
				int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
				if (bookIndex == books.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Book book = books.get(bookIndex - 1);
											
				serv.delete(book);	//I don't have to do anything to take care of the join tables because ON CASCADE DELETE takes care of it for me
				currentState = states.get("adminEntryPoint");	
		});
		
		states.put("createBranch", () -> {                             //CREATE LIBRARY BRANCH
			Branch branch = new Branch ();
			branch.setId(0);
			String prompt = "Enter Branch Name:";
			branch.setName(takeInputString(prompt));
			
			prompt = "Enter Branch Address:";
			branch.setAddress(takeInputString(prompt));
			
			BranchService serv = new BranchService();
			serv.create(branch);			
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("readBranch", () -> {                               //READ LIBRARY BRANCH
			
			BranchService serv = new BranchService();
			List<Branch> branchs = serv.read();		
			System.out.println();
			for (Branch a : branchs) {
				System.out.println(a.getName());					
			}
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updateBranch", () ->{                         	   //UPDATE LIBRARY BRANCH
			
			BranchService serv = new BranchService();
				
			String prompt = "Pick a branch to update:";
			List<Branch> branchs = serv.read();
			int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < branchs.size(); i++) {
				options.add(new InputOption(i+1,branchs.get(i).getName()));				
				branchsMap[i] = branchs.get(i).getId();
			}
			options.add(new InputOption(branchs.size() + 1, "Quit to previous menu"));
			int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value			
			
			if (branchIndex == branchs.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Branch branch = branchs.get(branchIndex - 1);
			
			prompt = "Current name is " + branch.getName()  
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
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("deleteBranch", () ->{                         	   //DELETE LIBRARY BRANCH
			
			BranchService serv = new BranchService();
				
				String prompt = "Pick a branch to delete:";
				List<Branch> branchs = serv.read();
				int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < branchs.size(); i++) {
					options.add(new InputOption(i+1,branchs.get(i).getName()));				
					branchsMap[i] = branchs.get(i).getId();
				}
				options.add(new InputOption(branchs.size() + 1, "Quit to previous menu"));
				int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value			
				if (branchIndex == branchs.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Branch branch = branchs.get(branchIndex - 1);
											
				serv.delete(branch);
				currentState = states.get("adminEntryPoint");	
		});
		
		states.put("createBorrower", () -> {                             //CREATE BORROWER
			Borrower borrower = new Borrower ();
			borrower.setCardNo(0);
			String prompt = "Enter Borrower Name:";
			borrower.setName(takeInputString(prompt));
			
			prompt = "Enter Borrower Address:";
			borrower.setAddress(takeInputString(prompt));
			
			prompt = "Enter Borrower Phone:";
			borrower.setPhone(takeInputString(prompt));
			
			BorrowerService serv = new BorrowerService();
			serv.create(borrower);			
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("readBorrower", () -> {                               //READ BORROWER
			
			BorrowerService serv = new BorrowerService();
			List<Borrower> borrowers = serv.read();		
			System.out.println();
			for (Borrower a : borrowers) {
				System.out.println(a.getName());					
			}
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updateBorrower", () ->{                         	   //UPDATE BORROWER
			
			BorrowerService serv = new BorrowerService();
				
			String prompt = "Pick a borrower to update:";
			List<Borrower> borrowers = serv.read();
			int[] borrowersMap = new int[borrowers.size()]; //maps from optionIndex - 1 to borrowerID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < borrowers.size(); i++) {
				options.add(new InputOption(i+1,borrowers.get(i).getName()));				
				borrowersMap[i] = borrowers.get(i).getCardNo();
			}
			options.add(new InputOption(borrowers.size() + 1, "Quit to previous menu"));
			int borrowerIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //borrowerIndex stores the return value			
			
			if (borrowerIndex == borrowers.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Borrower borrower = borrowers.get(borrowerIndex - 1);
			
			prompt = "Current name is " + borrower.getName()  
			+ "\r\nEnter new borrower name or press enter for no change:";
			String name = takeInputString(prompt, true);
			
			prompt = "Current address is " + borrower.getAddress()  
			+ "\r\nEnter new borrower address or press enter for no change:";
			String address = takeInputString(prompt, true);
			
			prompt = "Current phone number is " + borrower.getName()  
			+ "\r\nEnter new borrower phone number or press enter for no change:";
			String phone = takeInputString(prompt, true);
			
			if (name.length() != 0) { //only update if the user wants it to change i e if their input is not ""
				borrower.setName(name);
			}
			if (address.length() != 0) {
				borrower.setAddress(address);
			}
			if (phone.length() != 0) {
				borrower.setPhone(phone);
			}
			
			serv.update(borrower);							
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("deleteBorrower", () ->{                         	   //DELETE BORROWER
			
			BorrowerService serv = new BorrowerService();
				
				String prompt = "Pick a borrower to delete:";
				List<Borrower> borrowers = serv.read();
				int[] borrowersMap = new int[borrowers.size()]; //maps from optionIndex - 1 to borrowerID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < borrowers.size(); i++) {
					options.add(new InputOption(i+1,borrowers.get(i).getName()));				
					borrowersMap[i] = borrowers.get(i).getCardNo();
				}
				options.add(new InputOption(borrowers.size() + 1, "Quit to previous menu"));
				int borrowerIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //borrowerIndex stores the return value			
				if (borrowerIndex == borrowers.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Borrower borrower = borrowers.get(borrowerIndex - 1);
											
				serv.delete(borrower);
				currentState = states.get("adminEntryPoint");	
		});
		
		states.put("createPublisher", () -> {                             //CREATE PUBLISHER
			Publisher publisher = new Publisher ();
			publisher.setId(0);
			String prompt = "Enter Publisher Name:";
			publisher.setName(takeInputString(prompt));
			
			prompt = "Enter Publisher Address:";
			publisher.setAddress(takeInputString(prompt));
			
			prompt = "Enter Publisher Phone:";
			publisher.setPhone(takeInputString(prompt));
			
			PublisherService serv = new PublisherService();
			serv.create(publisher);			
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("readPublisher", () -> {                               //READ PUBLISHER
			
			PublisherService serv = new PublisherService();
			List<Publisher> publishers = serv.read();		
			System.out.println();
			for (Publisher a : publishers) {
				System.out.println(a.getName());					
			}
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updatePublisher", () ->{                         	   //UPDATE PUBLISHER
			
			PublisherService serv = new PublisherService();
				
			String prompt = "Pick a publisher to update:";
			List<Publisher> publishers = serv.read();
			int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < publishers.size(); i++) {
				options.add(new InputOption(i+1,publishers.get(i).getName()));				
				publishersMap[i] = publishers.get(i).getId();
			}
			options.add(new InputOption(publishers.size() + 1, "Quit to previous menu"));
			int publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //publisherIndex stores the return value			
			
			if (publisherIndex == publishers.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Publisher publisher = publishers.get(publisherIndex - 1);
			
			prompt = "Current name is " + publisher.getName()  
			+ "\r\nEnter new publisher name or press enter for no change:";
			String name = takeInputString(prompt, true);
			
			prompt = "Current address is " + publisher.getAddress()  
			+ "\r\nEnter new publisher address or press enter for no change:";
			String address = takeInputString(prompt, true);
			
			prompt = "Current phone number is " + publisher.getName()  
			+ "\r\nEnter new publisher phone number or press enter for no change:";
			String phone = takeInputString(prompt, true);
			
			if (name.length() != 0) { //only update if the user wants it to change i e if their input is not ""
				publisher.setName(name);
			}
			if (address.length() != 0) {
				publisher.setAddress(address);
			}
			if (phone.length() != 0) {
				publisher.setPhone(phone);
			}
			
			serv.update(publisher);							
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("deletePublisher", () ->{                         	   //DELETE PUBLISHER
			
			PublisherService serv = new PublisherService();
				
				String prompt = "Pick a publisher to delete:";
				List<Publisher> publishers = serv.read();
				int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < publishers.size(); i++) {
					options.add(new InputOption(i+1,publishers.get(i).getName()));				
					publishersMap[i] = publishers.get(i).getId();
				}
				options.add(new InputOption(publishers.size() + 1, "Quit to previous menu"));
				int publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //publisherIndex stores the return value			
				if (publisherIndex == publishers.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Publisher publisher = publishers.get(publisherIndex - 1);
											
				serv.delete(publisher);
				currentState = states.get("adminEntryPoint");							
		});
		
		states.put("createGenre", () -> {                             //CREATE GENRE
			String prompt = "Enter Genre Name:";
			Genre genre = new Genre (0, takeInputString(prompt));
			GenreService gserv = new GenreService();
			gserv.create(genre);			
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("readGenre", () -> {                               //READ GENRE
			
			GenreService gserv = new GenreService();
			List<Genre> genres = gserv.read();		
			System.out.println();
			for (Genre a : genres) {
				System.out.println(a.getName());					
			}
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updateGenre", () ->{                         	   //UPDATE GENRE
			
			GenreService gserv = new GenreService();
				
			String prompt = "Pick a genre to update:";
			List<Genre> genres = gserv.read();
			int[] genresMap = new int[genres.size()]; //maps from optionIndex - 1 to genreID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < genres.size(); i++) {
				options.add(new InputOption(i+1,genres.get(i).getName()));				
				genresMap[i] = genres.get(i).getId();
			}
			options.add(new InputOption(genres.size() + 1, "Quit to previous menu"));
			int genreIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //genreIndex stores the return value			
			
			if (genreIndex == genres.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Genre genre = genres.get(genreIndex - 1);
			
			prompt = "Current name is " + genre.getName()  
			+ "\r\nEnter new genre name:";
			genre.setName(takeInputString(prompt));
			
			gserv.update(genre);							
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("deleteGenre", () ->{                         	   //DELETE GENRE
			
			GenreService gserv = new GenreService();
				
				String prompt = "Pick a genre to delete:";
				List<Genre> genres = gserv.read();
				int[] genresMap = new int[genres.size()]; //maps from optionIndex - 1 to genreID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < genres.size(); i++) {
					options.add(new InputOption(i+1,genres.get(i).getName()));				
					genresMap[i] = genres.get(i).getId();
				}
				options.add(new InputOption(genres.size() + 1, "Quit to previous menu"));
				int genreIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //genreIndex stores the return value			
				if (genreIndex == genres.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Genre genre = genres.get(genreIndex - 1);
											
				gserv.delete(genre);
				currentState = states.get("adminEntryPoint");							
		});
		
		states.put("createAuthor", () -> {                             //CREATE AUTHOR
			String prompt = "Enter Author Name:";
			Author author = new Author (0, takeInputString(prompt));
			AuthorService aserv = new AuthorService();
			aserv.create(author);			
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("readAuthor", () -> {                               //READ AUTHOR
			
			AuthorService aserv = new AuthorService();
			List<Author> authors = aserv.read();		
			System.out.println();
			for (Author a : authors) {
				System.out.println(a.getName());					
			}
			System.out.println();

			currentState = states.get("adminEntryPoint");
		});
				
		states.put("updateAuthor", () ->{                         	   //UPDATE AUTHOR
			
			AuthorService aserv = new AuthorService();
				
			String prompt = "Pick a author to update:";
			List<Author> authors = aserv.read();
			int[] authorsMap = new int[authors.size()]; //maps from optionIndex - 1 to authorID
			List<InputOption> options = new ArrayList<InputOption>();
			for (int i = 0; i < authors.size(); i++) {
				options.add(new InputOption(i+1,authors.get(i).getName()));				
				authorsMap[i] = authors.get(i).getId();
			}
			options.add(new InputOption(authors.size() + 1, "Quit to previous menu"));
			int authorIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //authorIndex stores the return value			
			
			if (authorIndex == authors.size() + 1) { //quit to previous
				currentState = states.get("update");
				return;
			}
			Author author = authors.get(authorIndex - 1);
			
			prompt = "Current name is " + author.getName()  
			+ "\r\nEnter new author name:";
			author.setName(takeInputString(prompt));
			
			aserv.update(author);							
			currentState = states.get("adminEntryPoint");
		});
		
		states.put("deleteAuthor", () ->{                         	   //DELETE AUTHOR
			
			AuthorService aserv = new AuthorService();
				
				String prompt = "Pick a author to delete:";
				List<Author> authors = aserv.read();
				int[] authorsMap = new int[authors.size()]; //maps from optionIndex - 1 to authorID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < authors.size(); i++) {
					options.add(new InputOption(i+1,authors.get(i).getName()));				
					authorsMap[i] = authors.get(i).getId();
				}
				options.add(new InputOption(authors.size() + 1, "Quit to previous menu"));
				int authorIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //authorIndex stores the return value			
				if (authorIndex == authors.size() + 1) { //quit to previous
					currentState = states.get("update");
					return;
				}
				Author author = authors.get(authorIndex - 1);
											
				aserv.delete(author);
				currentState = states.get("adminEntryPoint");							
		});
		
		states.put("overrideDueDate", () ->{                        //OVERRIDE DUE DATE  - TODO 
			System.out.println("This feature has not yet been implemented");
			
			currentState = states.get("adminEntryPoint");
			
		});
		

		states.put("adminEntryPoint", adminEntryPoint);
		states.put("create", create);
		states.put("read", read);
		states.put("update", update);
		states.put("delete", delete);
		
	
	}
	
	
}
