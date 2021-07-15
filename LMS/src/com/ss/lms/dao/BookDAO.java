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
	public int addBook(Book book) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book VALUES (0, ?, ?)", new Object[] {book.getTitle(), book.getPublisher().getId()});
	}
	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getId(), book.getId()});
	}
	public void deleteBook(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {id});		
	}
	public List<Book> readBooksAuthors() throws ClassNotFoundException, SQLException {
		return read("SELECT book.*, author.authorName FROM tbl_book book\r\n"
				+ "INNER JOIN tbl_book_authors ba ON book.bookId = ba.bookId\r\n"
				+ "INNER JOIN tbl_author author ON ba.authorId = author.authorId\r\n"
				+ "ORDER BY book.bookID", null);		//creates duplicate results for multiple authors. Ordering by book id makes processing easier
	}
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
		int previousID = 0; //used to see if we have a duplicate which would indicate multiple authors
		while (rs.next()) {
			if (rs.getInt("bookId") == previousID) { //duplicate, so we need to add another author
				books.get(books.size() - 1).setAuthor(books.get(books.size() - 1).getAuthor() + " and " + rs.getString("authorName"));
			}else { //just add the book like normal
				Book book = new Book();
				book.setId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));
				book.setPublisher(new Publisher(rs.getInt("pubID"), "DEFAULT", "DEFAULT", "DEFAULT"));
				try {
					book.setAuthor(rs.getString("authorName"));
				}catch (Exception e) {}//many queries won't have author name, so that is expected behavior
				books.add(book);
				previousID = book.getId();
			}
				
		}
		return books;
	}

}