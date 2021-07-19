 package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection connIn) {
		super(connIn);
	}
	public int create(BookCopies bookCopies) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book_copies VALUES (?, ?, ?)", new Object[] {bookCopies.getBookID(), bookCopies.getBranchID(), bookCopies.getNumCopies()});
	}
	public void update(BookCopies bookCopies) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_copies SET branchId = ?, noOfCopies = ? WHERE bookId = ?", new Object[] {bookCopies.getBranchID(), bookCopies.getNumCopies(), bookCopies.getBookID()});
	}
	public void delete(BookCopies input) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] {input.getBookID(), input.getBranchID()});		
	}
	public List<BookCopies> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_book_copies", null);
	}
	
	public BookCopies readBookCopiesByID(int bookID, int branchID) throws ClassNotFoundException, SQLException{
		List<BookCopies> ret = pull("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] {bookID, branchID});
		if (ret.size() == 0) { //couldn't find a match
			return new BookCopies(0, 0, 0);
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<BookCopies> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookCopies> bookCopiess = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopies = new BookCopies();
			bookCopies.setBookID(rs.getInt("bookId"));
			bookCopies.setBranchID(rs.getInt("branchId"));
			bookCopies.setNumCopies(rs.getInt("noOfCopies"));
			bookCopiess.add(bookCopies);
			
		}
		return bookCopiess;
	}

}
