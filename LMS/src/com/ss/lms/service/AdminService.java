package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookAuthorDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookGenreDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.domains.Author;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookAuthor;
import com.ss.lms.domains.BookGenre;
import com.ss.lms.domains.Borrower;
import com.ss.lms.domains.Branch;
import com.ss.lms.domains.Genre;
import com.ss.lms.domains.Publisher;

public class AdminService extends UserService {
	//Logic flows down to up by necessity of Java lacking forward declaration support

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
			currentState = states.get("updateEntryPoint");
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
		
		states.put("overrideDueDate", () ->{                         //OVERRIDE DUE DATE  - TODO 
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				System.out.println("This feature has not yet been implemented");
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error updating record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		
		
		states.put("updateBook", () ->{                         //UPDATE BOOK
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BookDAO bdao= new BookDAO(conn);
				List<Book> books = bdao.readBooksAuthors();
				
				String prompt = "Pick a book to update:";
				int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < books.size(); i++) {
					options.add(new InputOption(i+1,books.get(i).getTitle() + " by " + books.get(i).getAuthor()));				
					booksMap[i] = books.get(i).getId();
				}
				int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
				Book book = books.get(bookIndex - 1);
										
				prompt = "Current title is " + book.getTitle()  
				+ "\r\nEnter book name or press enter for no change";
				String name = takeInputString(prompt, true); //use the overload that allows for non input
		
				
						//GET AUTHORS
				List<Integer> authorIDs = null; //used in final step
				BookAuthorDAO badao = new BookAuthorDAO(conn);
				List<BookAuthor> bookAuthors = badao.readBookAuthorByID(book.getId());
				
				AuthorDAO adao = new AuthorDAO(conn);
				List<Author> authors = new ArrayList<Author>();
				
				for (BookAuthor ba : bookAuthors) {
					authors.add(adao.readAuthorByID(ba.getAuthorID()));
				}
				StringBuilder authorString = new StringBuilder(authors.get(0).getName()); //a string to display the names of the authors
				
				for (int i = 1; i < authors.size(); i++) {
					authorString.append(authors.get(i).getName());
				}
				
				prompt = "Current author" + (authors.size()>1?"s are":" is") + " "+ authorString.toString()  
						+ "\r\nWould you like to change this?";
				InputOption[] optionsArr = {new InputOption(1, "No Change"), new InputOption(2, "New Author(s)")};
				int authorsShouldChange = takeInputOption(prompt, optionsArr); //1 is no, 2 is yes
				
				if(authorsShouldChange == 2) {
					
					prompt = "Select one or more new authors:";
					
					
					List<Author> allAuthors = adao.readAllAuthors();
					
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
				
				List<Integer> genreIDs = null;
				BookGenreDAO bgdao = new BookGenreDAO(conn);
				List<BookGenre> bookGenres = bgdao.readBookGenreByID(book.getId());
				
				GenreDAO gdao = new GenreDAO(conn);
				List<Genre> genres = new ArrayList<Genre>();
				
				for (BookGenre ba : bookGenres) {
					genres.add(gdao.readGenreByID(ba.getGenreID()));
				}
				StringBuilder genreString = new StringBuilder(genres.get(0).getName()); //a string to display the names of the genres
				
				for (int i = 1; i < genres.size(); i++) {
					genreString.append(genres.get(i).getName());
				}
				
				prompt = "Current genre" + (genres.size()>1?"s are":" is") + " "+ genreString.toString()  
						+ "\r\nWould you like to change this?";

				optionsArr[1] = new InputOption(2, "New Genre");
				int genresShouldChange = takeInputOption(prompt, optionsArr); //1 is no, 2 is yes
				
				if(genresShouldChange == 2) {
					List<Genre> allGenres = gdao.readAllGenres();
					
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
				
				PublisherDAO pdao= new PublisherDAO(conn);
				//int pubID = book.getPublisher()
				prompt = "Current publisher is " + pdao.readPublisherByID(book.getPublisher().getId()).getName()
				//prompt = "Current publisher is " + pdao.readPublisherByID(book.getPublisher().getId()).getName()
				+ "\r\nSelect a new publisher or select \"No Change\"";
				List<Publisher> publishers = pdao.readAllPublishers();
				
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
				
						//UPDATE BOOK
				
				if (!"".equals(name)) {//the user hasn't selected a new title
					book.setTitle(name);
				} //else title stays the same
				
				if(authorsShouldChange == 2) { //if we have new authors to add
					badao.deleteBookAuthor(book.getId());
					for (int i : authorIDs ) {
						BookAuthor ba = new BookAuthor(book.getId(), i);
						badao.addBookAuthor(ba);
					}
				}
				if(genresShouldChange == 2) { //if we have new genre to add
					bgdao.deleteBookGenre(book.getId());
					for (int i : genreIDs ) {
						BookGenre bg = new BookGenre(i, book.getId());
						bgdao.addBookGenre(bg);
					}
				}
	
				bdao.updateBook(book);
				conn.commit();
				System.out.println("Record Updated\n");
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error updating record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("updateBranch", () ->{                         //UPDATE LIBRARY BRANCH
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BranchDAO bdao= new BranchDAO(conn);
				List<Branch> branchs = bdao.readAllBranchs();
				
				String prompt = "Pick a branch to update:";
				int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < branchs.size(); i++) {
					options.add(new InputOption(i+1,branchs.get(i).getName() + " | " + branchs.get(i).getAddress()));				
					branchsMap[i] = branchs.get(i).getId();
				}
				int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value			
				Branch branch = branchs.get(branchIndex - 1);
				
				prompt = "Current name is " + branch.getName()  
				+ "\r\nEnter branch name or press enter for no change";
				String name = takeInputString(prompt, true); //use the overload that allows for 0 input
		
				prompt = "Current address is " + branch.getAddress()  
						+ "\r\nEnter branch address or press enter for no change:";
				String address = takeInputString(prompt, true);
				
				if (name.length() != 0) { //only update if the user wants it to change i e if their input is not ""
					branch.setName(name);
				}
				if (address.length() != 0) {
					branch.setAddress(address);
				}
				
				bdao.updateBranch(branch);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("updateGenre", () ->{                         //UPDATE GENRE
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				GenreDAO bdao= new GenreDAO(conn);
				List<Genre> genres = bdao.readAllGenres();
				
				String prompt = "Pick a genre to update:";
				int[] genresMap = new int[genres.size()]; //maps from optionIndex - 1 to genreID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < genres.size(); i++) {
					options.add(new InputOption(i+1,genres.get(i).getName()));				
					genresMap[i] = genres.get(i).getId();
				}
				int genreIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //genreIndex stores the return value			
				Genre genre = genres.get(genreIndex - 1);
				
				prompt = "Current name is " + genre.getName()  
				+ "\r\nEnter new genre name:";
				genre.setName(takeInputString(prompt));
				
				
				bdao.updateGenre(genre);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("updateAuthor", () ->{                         //UPDATE AUTHOR
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				AuthorDAO bdao= new AuthorDAO(conn);
				List<Author> authors = bdao.readAllAuthors();
				
				String prompt = "Pick a author to update:";
				int[] authorsMap = new int[authors.size()]; //maps from optionIndex - 1 to authorID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < authors.size(); i++) {
					options.add(new InputOption(i+1,authors.get(i).getName()));				
					authorsMap[i] = authors.get(i).getId();
				}
				int authorIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //authorIndex stores the return value			
				Author author = authors.get(authorIndex - 1);
				
				prompt = "Current name is " + author.getName()  
				+ "\r\nEnter new author name:";
				author.setName(takeInputString(prompt));
				
				
				bdao.updateAuthor(author);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		
		states.put("updatePublisher", () ->{                         //UPDATE PUBLISHER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				PublisherDAO pdao= new PublisherDAO(conn);
				List<Publisher> publishers = pdao.readAllPublishers();
				
				String prompt = "Pick a publisher to update:";
				int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < publishers.size(); i++) {
					options.add(new InputOption(i+1,publishers.get(i).getName() + " | " + publishers.get(i).getAddress()));				
					publishersMap[i] = publishers.get(i).getId();
				}
				int publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //publisherIndex stores the return value			
				Publisher publisher = publishers.get(publisherIndex - 1);
				
				prompt = "Current name is " + publisher.getName()  
				+ "\r\nEnter publisher name or press enter for no change";
				String name = takeInputString(prompt, true); //use the overload that allows for 0 input
		
				prompt = "Current address is " + publisher.getAddress()  
						+ "\r\nEnter publisher address or press enter for no change:";
				String address = takeInputString(prompt, true);
				
				prompt = "Current phone number is " + publisher.getPhone()  
				+ "\r\nEnter publisher phone number or press enter for no change:";
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
				
				pdao.updatePublisher(publisher);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("updateBorrower", () ->{                         //UPDATE PUBLISHER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BorrowerDAO pdao= new BorrowerDAO(conn);
				List<Borrower> borrowers = pdao.readAllBorrowers();
				
				String prompt = "Pick a borrower to update:";
				int[] borrowersMap = new int[borrowers.size()]; //maps from optionIndex - 1 to borrowerID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < borrowers.size(); i++) {
					options.add(new InputOption(i+1,borrowers.get(i).getName() + " | " + borrowers.get(i).getAddress()));				
					borrowersMap[i] = borrowers.get(i).getCardNo();
				}
				int borrowerIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //borrowerIndex stores the return value			
				Borrower borrower = borrowers.get(borrowerIndex - 1);
				
				prompt = "Current name is " + borrower.getName()  
				+ "\r\nEnter borrower name or press enter for no change";
				String name = takeInputString(prompt, true); //use the overload that allows for 0 input
		
				prompt = "Current address is " + borrower.getAddress()  
						+ "\r\nEnter borrower address or press enter for no change:";
				String address = takeInputString(prompt, true);
				
				prompt = "Current phone number is " + borrower.getPhone()  
				+ "\r\nEnter borrower phone number or press enter for no change:";
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
				
				pdao.updateBorrower(borrower);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("updateBorrower", () ->{                         //UPDATE BORROWER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {
				conn = cUtil.getConnection();
				BorrowerDAO pdao= new BorrowerDAO(conn);
				List<Borrower> borrowers = pdao.readAllBorrowers();
				
				String prompt = "Pick a borrower to update:";
				int[] borrowersMap = new int[borrowers.size()]; //maps from optionIndex - 1 to borrowerID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < borrowers.size(); i++) {
					options.add(new InputOption(i+1,borrowers.get(i).getName() + " | " + borrowers.get(i).getAddress()));				
					borrowersMap[i] = borrowers.get(i).getCardNo();
				}
				int borrowerIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //borrowerIndex stores the return value			
				Borrower borrower = borrowers.get(borrowerIndex - 1);
				
				prompt = "Current name is " + borrower.getName()  
				+ "\r\nEnter borrower name or press enter for no change";
				String name = takeInputString(prompt, true); //use the overload that allows for 0 input
		
				prompt = "Current address is " + borrower.getAddress()  
						+ "\r\nEnter borrower address or press enter for no change:";
				String address = takeInputString(prompt, true);
				
				prompt = "Current phone number is " + borrower.getPhone()  
				+ "\r\nEnter borrower phone number or press enter for no change:";
				String phone = takeInputString(prompt, true);
				
				if (name.length() != 0) { //only update if the user wants it to change i.e. if their input is not ""
					borrower.setName(name);
				}
				if (address.length() != 0) {
					borrower.setAddress(address);
				}
				if (phone.length() != 0) {
					borrower.setPhone(phone);
				}
				
				pdao.updateBorrower(borrower);
				conn.commit();
				System.out.println("Record updated");
			} catch (Exception e) {
				System.out.println("error updating record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		
		states.put("deleteBranch", () -> {                             //DELETE LIBRARY BRANCH
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				BranchDAO bdao= new BranchDAO(conn);
				List<Branch> branchs = bdao.readAllBranchs();
				
				String prompt = "Pick a branch to delete:";
				int[] branchsMap = new int[branchs.size()]; //maps from optionIndex - 1 to branchID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < branchs.size(); i++) {
					options.add(new InputOption(i+1,branchs.get(i).getName() + " | " + branchs.get(i).getAddress()));				
					branchsMap[i] = branchs.get(i).getId();
				}
				int branchIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //branchIndex stores the return value			
				int branchID = branchsMap[branchIndex-1]; //because the options are 1-indexed								
				
				bdao.deleteBranch(branchID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("readBranch", () -> {                             //READ LIBRARY BRANCH
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				BranchDAO bdao= new BranchDAO(conn);
				List<Branch> branchs = bdao.readAllBranchs();
				for (int i = 0; i < branchs.size(); i++) {
					System.out.println(branchs.get(i).getName() + " | " + branchs.get(i).getAddress());					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("createBranch", () ->{                         //CREATE LIBRARY BRANCH
			String prompt = "Enter Branch Name:";
			String name = takeInputString(prompt);

			prompt = "Enter Branch Address:";
			String address = takeInputString(prompt);
			
			Branch branch = new Branch (0, name, address);
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			try {
				conn = cUtil.getConnection();
				BranchDAO bdao= new BranchDAO(conn);
				bdao.addBranch(branch);
				conn.commit();
				System.out.println("Record Created");
			} catch (Exception e) {
				System.out.println("error adding record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		
		states.put("readBorrower", () -> {                             //READ BORROWERS
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				BorrowerDAO adao= new BorrowerDAO(conn);
				List<Borrower> borrowers = adao.readAllBorrowers();
				for (int i = 0; i < borrowers.size(); i++) {
					System.out.println(borrowers.get(i).getName());					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("readPublisher", () -> {                             //READ PUBLISHER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				PublisherDAO adao= new PublisherDAO(conn);
				List<Publisher> publishers = adao.readAllPublishers();
				for (int i = 0; i < publishers.size(); i++) {
					System.out.println(publishers.get(i).getName());					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("readGenre", () -> {                             //READ GENRE
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				GenreDAO adao= new GenreDAO(conn);
				List<Genre> genres = adao.readAllGenres();
				for (int i = 0; i < genres.size(); i++) {
					System.out.println(genres.get(i).getName());					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("readBook", () -> {                             //READ BOOKS
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				BookDAO adao= new BookDAO(conn);
				List<Book> books = adao.readBooksAuthors();
				for (int i = 0; i < books.size(); i++) {
					System.out.println(books.get(i).getTitle() + " by " + books.get(i).getAuthor()); //the author field also handles multiple authors on the DAO side					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("readAuthor", () -> {                             //READ AUTHORS
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			System.out.println(); //spacing
			try {			
				conn = cUtil.getConnection();
				AuthorDAO adao= new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
				for (int i = 0; i < authors.size(); i++) {
					System.out.println(authors.get(i).getName());					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error retrieving records");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
				System.out.println(); //spacing
			}
			
		});
		
		states.put("deleteBook", () -> {                             //DELETE BOOK
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				BookDAO bdao= new BookDAO(conn);
				List<Book> books = bdao.readAllBooks();
				
				String prompt = "Pick a book to delete:";
				int[] booksMap = new int[books.size()]; //maps from optionIndex - 1 to bookID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < books.size(); i++) {
					options.add(new InputOption(i+1,books.get(i).getTitle()));				
					booksMap[i] = books.get(i).getId();
				}
				int bookIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //bookIndex stores the return value			
				int bookID = booksMap[bookIndex-1]; //because the options are 1-indexed								
				
				bdao.deleteBook(bookID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("deleteBorrower", () -> {                             //DELETE BORROWER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				BorrowerDAO bdao= new BorrowerDAO(conn);
				List<Borrower> borrowers = bdao.readAllBorrowers();
				
				String prompt = "Pick a borrower to delete:";
				int[] borrowersMap = new int[borrowers.size()]; //maps from optionIndex - 1 to borrowerID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < borrowers.size(); i++) {
					options.add(new InputOption(i+1,borrowers.get(i).getName()));				
					borrowersMap[i] = borrowers.get(i).getCardNo();
				}
				int borrowerIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //borrowerIndex stores the return value			
				int borrowerID = borrowersMap[borrowerIndex-1]; //because the options are 1-indexed								
				
				bdao.deleteBorrower(borrowerID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		
		states.put("deletePublisher", () -> {                             //DELETE PUBLISHER
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				PublisherDAO pdao= new PublisherDAO(conn);
				List<Publisher> publishers = pdao.readAllPublishers();
				
				String prompt = "Pick a publisher to delete:";
				int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < publishers.size(); i++) {
					options.add(new InputOption(i+1,publishers.get(i).getName()));				
					publishersMap[i] = publishers.get(i).getId();
				}
				int publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //publisherIndex stores the return value			
				int publisherID = publishersMap[publisherIndex-1]; //because the options are 1-indexed								
				
				pdao.deletePublisher(publisherID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("deleteGenre", () -> {                             //DELETE GENRE
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				GenreDAO gdao= new GenreDAO(conn);
				List<Genre> genres = gdao.readAllGenres();
						
				String prompt = "Pick a genre to delete:";
				int[] genresMap = new int[genres.size()]; //maps from optionIndex - 1 to genreID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < genres.size(); i++) {
					options.add(new InputOption(i+1,genres.get(i).getName()));				
					genresMap[i] = genres.get(i).getId();
				}
				int genreIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //genreIndex stores the return value			
				int genreID = genresMap[genreIndex-1]; //because the options are 1-indexed								
				
				gdao.deleteGenre(genreID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("deleteAuthor", () -> {                             //DELETE AUTHOR
			
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			
			try {			
				conn = cUtil.getConnection();
				AuthorDAO adao= new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
						
				String prompt = "Pick an author to delete:";
				int[] authorsMap = new int[authors.size()]; //maps from optionIndex - 1 to authorID
				List<InputOption> options = new ArrayList<InputOption>();
				for (int i = 0; i < authors.size(); i++) {
					options.add(new InputOption(i+1,authors.get(i).getName()));				
					authorsMap[i] = authors.get(i).getId();
				}
				int authorIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //authorIndex stores the return value			
				int authorID = authorsMap[authorIndex-1]; //because the options are 1-indexed								
				
				adao.deleteAuthor(authorID);
				conn.commit();
				System.out.println("Record Removed");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error removing record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("createBook", () -> {                         //CREATE BOOK
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			Book book = new Book();

			String prompt = "Enter book title:";
			book.setTitle(takeInputString(prompt));
			
			
			
			try {
				conn = cUtil.getConnection();
				
				//get the available authors, then prompt the user to chose one of them
				prompt = "Pick one or more authors:";
				AuthorDAO adao= new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
				
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
				GenreDAO gdao= new GenreDAO(conn);
				List<Genre> genres = gdao.readAllGenres();
				
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
				PublisherDAO pdao= new PublisherDAO(conn);
				List<Publisher> publishers = pdao.readAllPublishers();
				
				int[] publishersMap = new int[publishers.size()]; //maps from optionIndex - 1 to publisherID
				options = new ArrayList<InputOption>();
				for (int i = 0; i < publishers.size(); i++) {
					options.add(new InputOption(i+1,publishers.get(i).getName()));				
					publishersMap[i] = publishers.get(i).getId();
				}
				
				int publisherIndex;  //stores the return values
				publisherIndex = takeInputOption(prompt, options.toArray(new InputOption[options.size()])); //we know that there must be at least one publisher
				book.setPublisher(new Publisher(publishersMap[publisherIndex-1], "", "", "")); //because the options are 1-indexed
				
				BookDAO bdao = new BookDAO(conn);
				book.setId(0); //auto increment
				book.setId(bdao.addBook(book)); //add the book, get the primary key, and set it to the book variable
				
				BookGenreDAO bgdao = new BookGenreDAO(conn);
				for (int i : genreIDs ) {
					BookGenre bg = new BookGenre(i, book.getId());
					bgdao.addBookGenre(bg);
				}

				
				BookAuthorDAO badao = new BookAuthorDAO(conn);
				for (int i : authorIDs ) {
					BookAuthor ba = new BookAuthor(book.getId(), i);
					badao.addBookAuthor(ba);
				}
				conn.commit();
				
				System.out.println("Record Created");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error adding record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		states.put("createAuthor", () -> {                             //CREATE AUTHOR
			String prompt = "Enter Author Name:";
			Author author = new Author (0, takeInputString(prompt));
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			try {
				conn = cUtil.getConnection();
				AuthorDAO adao= new AuthorDAO(conn);
				adao.addAuthor(author);
				conn.commit();
				System.out.println("Record Created");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error adding record");
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentState = states.get("adminEntryPoint");
			}
		});
		states.put("createGenre", () ->{                             //CREATE GENRE
			String prompt = "Enter Genre Name:";
			String name = takeInputString(prompt);
			Genre genre = new Genre (0, name);
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			try {
				conn = cUtil.getConnection();
				GenreDAO pdao= new GenreDAO(conn);
				pdao.addGenre(genre);
				conn.commit();
				System.out.println("Record Created");
			} catch (Exception e) {
				System.out.println("error adding record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		states.put("createPublisher", () ->{                         //CREATE PUBLISHER
			String prompt = "Enter Publisher Name:";
			String name = takeInputString(prompt);

			prompt = "Enter Publisher Address:";
			String address = takeInputString(prompt);

			prompt = "Enter Publisher Phone:";
			String phone = takeInputString(prompt);
			
			Publisher publisher = new Publisher (0, name, address, phone);
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			try {
				conn = cUtil.getConnection();
				PublisherDAO pdao= new PublisherDAO(conn);
				pdao.addPublisher(publisher);
				conn.commit();
				System.out.println("Record Created");
			} catch (Exception e) {
				System.out.println("error adding record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		
		states.put("createBorrower", () ->{                         //CREATE BORROWER
			String prompt = "Enter Borrower Name:";
			String name = takeInputString(prompt);

			prompt = "Enter Borrower Address:";
			String address = takeInputString(prompt);

			prompt = "Enter Borrower Phone:";
			String phone = takeInputString(prompt);
			
			Borrower borrower = new Borrower (0, name, address, phone);
			ConnectionUtil cUtil = new ConnectionUtil();
			Connection conn = null;
			try {
				conn = cUtil.getConnection();
				BorrowerDAO dao= new BorrowerDAO(conn);
				dao.addBorrower(borrower);
				conn.commit();
				System.out.println("Record Created");
			} catch (Exception e) {
				System.out.println("error adding record");
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
				currentState = states.get("adminEntryPoint");
			}
		});
		states.put("adminEntryPoint", adminEntryPoint);
	}
}
