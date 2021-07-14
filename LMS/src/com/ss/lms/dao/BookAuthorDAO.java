 package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.BookAuthor;

public class BookAuthorDAO extends BaseDAO<BookAuthor> {

	public BookAuthorDAO(Connection connIn) {
		super(connIn);
	}
	public void addBookAuthor(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {bookAuthor.getBookID(), bookAuthor.getAuthorID()});
	}
	public void updateBookAuthor(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_authors SET authorId = ? WHERE bookId = ?", new Object[] {bookAuthor.getAuthorID(), bookAuthor.getBookID()});
	}
	public void deleteBookAuthor(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[] {id});		
	}
	public List<BookAuthor> readAllBookAuthors() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_authors", null);
		
	}
	
	public BookAuthor readBookAuthorByID(int id) throws ClassNotFoundException, SQLException{
		List<BookAuthor> ret = read("SELECT * FROM tbl_book_authors WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new BookAuthor(0, 0);
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<BookAuthor> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookAuthor> bookAuthors = new ArrayList<>();
		while (rs.next()) {
			BookAuthor bookAuthor = new BookAuthor();
			bookAuthor.setBookID(rs.getInt("bookId"));
			bookAuthor.setAuthorID(rs.getInt("authorId"));
			bookAuthors.add(bookAuthor);
			
		}
		return bookAuthors;
	}

}
