package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Book;
import com.ss.lms.domains.Publisher;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection connIn) {
		super(connIn);
	}
	public void addBook(Book book) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book VALUES (0, ?, ?)", new Object[] {book.getTitle(), book.getPublisher().getId()});
	}
	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getId(), book.getId()});
	}
	public void deleteBook(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {id});		
	}
	//public void deleteBookByName(String name) throws ClassNotFoundException, SQLException {
	//	save("DELETE FROM tbl_book WHERE title = ?", new Object[] {name});		
	//}
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null);
		
	}
	
	public Book readBookByID(int id) throws ClassNotFoundException, SQLException{
		List<Book> ret = read("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Book(0, "NOT FOUND", new Publisher (0, "DEFAULT", "DEFAULT", "DEFAULT"));
		}
		return ret.get(0);
	}
	public Book readBookByName(String name) throws ClassNotFoundException, SQLException{
		List<Book> ret = read("SELECT * FROM tbl_book WHERE title = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Book(0, "NOT FOUND", new Publisher (0, "DEFAULT", "DEFAULT", "DEFAULT"));
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(new Publisher(0, "DEFAULT", "DEFAULT", "DEFAULT"));
			
			books.add(book);
			
		}
		return books;
	}

}