package com.ss.lms.service;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//Books are used heavily in the UI

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BookAuthorDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookGenreDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.domains.Author;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookAuthor;
import com.ss.lms.domains.BookGenre;
import com.ss.lms.domains.Branch;
import com.ss.lms.domains.Genre;

public class BookService extends BaseService<Book> {
	
	protected BaseDAO<Book> getDAO(Connection conn) {
		return new BookDAO(conn);
	}
	
	public List<Book> readLoanedBooks(int cardNum){ //pulls every book that has at least one copy at a given branch
		List<Book> ret = null;
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			ret = bdao.readLoanedBooks(cardNum);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading record");
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
		}
		return ret;
	}
	
	public List<Book> readBookBranch(Branch branch){ //pulls every book that has at least one copy at a given branch
		List<Book> ret = null;
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			ret = bdao.readBooksBranch(branch.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading record");
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
		}
		return ret;
	}
	
	public void addBookFull(Book book, List<Integer> genreIDs, List<Integer> authorIDs) {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			book.setId(0); //auto inc
			book.setId(bdao.create(book)); //add the book, get the primary key, and set it to the book variable
			
			BookGenreDAO bgdao = new BookGenreDAO(conn);
			for (int i : genreIDs ) {
				BookGenre bg = new BookGenre(i, book.getId());
				bgdao.create(bg);
			}
			
			BookAuthorDAO badao = new BookAuthorDAO(conn);
			for (int i : authorIDs ) {
				BookAuthor ba = new BookAuthor(book.getId(), i);
				badao.create(ba);
			}
			
			conn.commit();
			System.out.println("Record Created");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding record");
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
		}
		
	}
	
	public void updateBookFull(Book book, List<Integer> genreIDs, List<Integer> authorIDs) {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.update(book); //add the book, get the primary key, and set it to the book variable
			
			if(authorIDs != null) { //if we have new authors to add
				BookAuthorDAO badao = new BookAuthorDAO(conn);
				badao.deleteByBook(book.getId());
				for (int i : authorIDs ) {
					BookAuthor ba = new BookAuthor(book.getId(), i);
					badao.create(ba);
				}
			}
			if(genreIDs != null) { //if we have new genre to add
				BookGenreDAO bgdao = new BookGenreDAO(conn);
				bgdao.deleteByBook(book.getId());
				for (int i : genreIDs ) {
					BookGenre bg = new BookGenre(i, book.getId());
					bgdao.create(bg);
				}
			}
			
			
			conn.commit();
			System.out.println("Record Updated");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error updating record");
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
		}
		
	}
	
	public List<Author> readBookAuthors(Book book) {
		Connection conn = null;
		List<Author> authors = null;
		try {
			conn = cUtil.getConnection();
			BookAuthorDAO badao = new BookAuthorDAO(conn);
			List<BookAuthor> bookAuthors = badao.readBookAuthorByID(book.getId());
			
			AuthorDAO adao = new AuthorDAO(conn);
			authors = new ArrayList<Author>();
			
			for (BookAuthor ba : bookAuthors) {
				authors.add(adao.readAuthorByID(ba.getAuthorID()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding record");
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
		}
		return authors;
		
	}
	
	public List<Genre> readBookGenres(Book book) {
		Connection conn = null;
		List<Genre> genres = null;
		try {
			conn = cUtil.getConnection();
			BookGenreDAO bgdao = new BookGenreDAO(conn);
			List<BookGenre> bookGenres = bgdao.readBookGenreByID(book.getId());
			
			GenreDAO gdao = new GenreDAO(conn);
			genres = new ArrayList<Genre>();
			
			for (BookGenre ba : bookGenres) {
				genres.add(gdao.readGenreByID(ba.getGenreID()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding record");
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
		}
		return genres;
		
	}
}
