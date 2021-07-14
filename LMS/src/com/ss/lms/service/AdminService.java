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
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.domains.Author;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookAuthor;
import com.ss.lms.domains.BookGenre;
import com.ss.lms.domains.Borrower;
import com.ss.lms.domains.Genre;
import com.ss.lms.domains.Publisher;

public class AdminService extends UserService {
	//Logic flows down to up by necessity of Java lacking forward dec support

	private Map<String, DarkVoid> states;
	
	
	DarkVoid create = () -> {
		String prompt = "What type of record would you like to create?";
		InputOption[] options = {new InputOption(1, "Author"), new InputOption(2, "Book"), new InputOption(3, "Genre"), new InputOption(4, "Publisher"), 
				new InputOption(5, "Borrower"), new InputOption(6, "Back to operation select") };
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
		case 6: //go back
			currentState = states.get("adminEntryPoint");
			break;
		}	
	};
	
	DarkVoid adminEntryPoint = () -> {
		populateHashMap();
		String prompt = "Welcome, Admin \nWhich operation would you like to perform?";
		InputOption[] options = {new InputOption(1, "Create"), new InputOption(2, "Read"), new InputOption(3, "Update"), new InputOption(4, "Delete"), 
				new InputOption(5, "Override Due Date"), new InputOption(6, "Show all books"), new InputOption(7, "Back to Main Menu") };
		int option = takeInputOption(prompt, options);	
		switch (option) {
		case 1:
			currentState = create;
			
			break;
		case 7:
			executionLoopShouldStop = true;
		}
	};
	
	
	private void populateHashMap() {
		states = new HashMap<String, DarkVoid>();
		states.put("createBook", () -> {
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
				prompt = "Pick one or more publishers:";
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
				bdao.addBook(book);
				//conn.commit(); I don't know if I have to commit this
				book = bdao.readBookByName(book.getTitle()); //gives us the id it just assigned
				
				BookGenreDAO bgdao = new BookGenreDAO(conn);
				for (int i : genreIDs ) {
					BookGenre bg = new BookGenre(i, book.getId());
					bgdao.addBookGenre(bg);
				}

				//conn.commit(); I don't know if I have to commit this
				
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
		states.put("createAuthor", () -> {
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
		states.put("createGenre", () ->{
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
		states.put("createPublisher", () ->{
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
		states.put("createBorrower", () ->{
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
		states.put("create", create);
		states.put("adminEntryPoint", adminEntryPoint);
	}
	
	DarkVoid c = () -> System.out.println("case 3");
	DarkVoid x = () -> System.out.println("case 4");
	DarkVoid y = () -> System.out.println("case 5");
	DarkVoid z = () -> System.out.println("case 6");
	
	
	
	protected void read() {}
	protected void update() {}
	protected void delete() {}
	protected void overwrite() {}
	
	
	
	
	protected void addAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO authorDAO = new AuthorDAO(conn);
			authorDAO.addAuthor(author);
		}catch(Exception e) {
			conn.rollback();
			System.out.println("Error at Admin.addAuthor");
		} finally {
			conn.close();
		}
	}
	
	protected List<Author> readAuthors() throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO authorDAO = new AuthorDAO(conn);
			List<Author> authors = authorDAO.readAllAuthors();
			return authors;
		}catch(Exception e) {
			conn.rollback();
			System.out.println("Error at Admin.addAuthor");
		} finally {
			conn.close();
		}

		return null;
	}
}
