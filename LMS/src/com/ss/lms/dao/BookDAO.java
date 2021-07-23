package com.ss.lms.dao;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//Books are used heavily in the UI, which is why there's so many different functions that join so many different tables, and why extractData is so complex

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
	public int create(Book book) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book VALUES (0, ?, ?)", new Object[] {book.getTitle(), book.getPublisher().getId()});
	}
	public void update(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getId(), book.getId()});
	}
	public void delete(Book input) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {input.getId()});		
	}
	public List<Book> read() throws ClassNotFoundException, SQLException { //includes author string
		return pull("SELECT book.*, author.authorName FROM tbl_book book\r\n"
				+ "INNER JOIN tbl_book_authors ba ON book.bookId = ba.bookId\r\n"
				+ "INNER JOIN tbl_author author ON ba.authorId = author.authorId\r\n"
				+ "ORDER BY book.bookId", null);		//creates duplicate results for multiple authors. Ordering by book id makes processing easier
	}
	
	public Book readBookByID(int id) throws ClassNotFoundException, SQLException{
		List<Book> ret = pull("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Book(0, "NOT FOUND", new Publisher (0, "DEFAULT", "DEFAULT", "DEFAULT"));
		}
		return ret.get(0);
	}
	public Book readBookByName(String name) throws ClassNotFoundException, SQLException{ 
		List<Book> ret = pull("SELECT * FROM tbl_book WHERE title = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Book(0, "NOT FOUND", new Publisher (0, "DEFAULT", "DEFAULT", "DEFAULT"));
		}
		return ret.get(0);
	}
	
	public List<Book> readLoanedBooks(int cardNum) throws ClassNotFoundException, SQLException{
		return pull("SELECT book.*, author.authorName, branch.branchName, branch.branchId FROM tbl_book_loans loans\r\n"
				+ "INNER JOIN tbl_book book ON loans.bookId = book.bookId\r\n"
				+ "INNER JOIN tbl_book_authors ba ON ba.bookId = loans.bookId\r\n"
				+ "INNER JOIN tbl_author author ON author.authorId = ba.authorId\r\n"
				+ "INNER JOIN tbl_library_branch branch ON loans.branchId = branch.branchId\r\n"
				+ "WHERE loans.cardNo = ? AND loans.dateIn IS NULL\r\n" //only want to pull current loans
				+ "ORDER BY book.bookId, branch.branchId", new Object[] {cardNum});
	}
	public List<Book> readBooksBranch(int branchID) throws ClassNotFoundException, SQLException{
		return pull("SELECT book.*, author.authorName FROM tbl_book book\r\n"
				+ "INNER JOIN tbl_book_authors ba ON book.bookId = ba.bookId\r\n"
				+ "INNER JOIN tbl_author author ON ba.authorId = author.authorId\r\n"
				+ "INNER JOIN tbl_book_copies bc ON bc.bookId = book.bookId\r\n"
				+ "WHERE bc.branchId = ? AND bc.noOfCopies > 0\r\n"
				+ "ORDER BY book.bookID"
				, new Object[] {branchID});
	}
	
	//required for BaseDAO read method
	//I have many different result sets that could go into this, so I need to take a lot of extra steps
	public List<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<>();
		int previousID = 0; //used to see if we have a duplicate which would indicate multiple authors
		int previousBranch = 0;
		while (rs.next()) {
			 int branchID = 0; //handle if we're also pulling the branch this book is loaned out from
			try {
				branchID = rs.getInt("branchId");
			}catch (Exception e) {}
			if (rs.getInt("bookId") == previousID && branchID == previousBranch) { //duplicate, so we need to add another author
				books.get(books.size() - 1).setAuthor(books.get(books.size() - 1).getAuthor() + " and " + rs.getString("authorName"));
			}else { //just add the book like normal
				Book book = new Book();
				book.setId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));
				book.setPublisher(new Publisher(rs.getInt("pubID"), "DEFAULT", "DEFAULT", "DEFAULT"));
				try {
					book.setAuthor(rs.getString("authorName"));
				}catch (Exception e) {}//many queries won't have author name, so that is expected behavior
				try {
					book.setBranch(rs.getString("branchName"));
					book.setBranchID(branchID);
				}catch (Exception e) {}
				books.add(book);
				previousID = book.getId();
				previousBranch = book.getBranchID();
			}
				
		}
		return books;
	}

}