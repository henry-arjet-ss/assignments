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
	public int create(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {bookAuthor.getBookID(), bookAuthor.getAuthorID()});
	}
	public void update(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_authors SET authorId = ? WHERE bookId = ?", new Object[] {bookAuthor.getAuthorID(), bookAuthor.getBookID()});
	}
	public void delete(BookAuthor input) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[] {input.getBookID()});		
	}
	public List<BookAuthor> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_book_authors", null);
		
	}
	
	public List<BookAuthor> readBookAuthorByID(int id) throws ClassNotFoundException, SQLException{
		List<BookAuthor> ret = pull("SELECT * FROM tbl_book_authors WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return null;
		}
		return ret;
	}
	
	public void deleteByBook(int id) throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[] {id});
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
