 package com.ss.lms.dao;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime; 
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection connIn) {
		super(connIn);
	}
	public int create(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?, null)", new Object[] {bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo(),
				Timestamp.valueOf(bookLoans.getDateOut().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()), //OffsetDateTime to Timestamp
				Timestamp.valueOf(bookLoans.getDueDate().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
				//Timestamp.valueOf(bookLoans.getDateIn().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()), no date in on create
		});
	}
	public void update(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		//this should never be hit;
	}
	public void delete(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo()});		
	}
	public List<BookLoans> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_book_loans", null);
		
	}
	
	public void returnBook(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
			save("UPDATE tbl_book_loans SET dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {
					Timestamp.valueOf(bookLoans.getDateIn().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),	
					bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo()});
	}
	public void updateDueDate(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {
				Timestamp.valueOf(bookLoans.getDueDate().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),	
				bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo()});
}
	
	public BookLoans readBookLoan(int card, int branch, int book) throws ClassNotFoundException, SQLException{
		List<BookLoans> ret = pull("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND branchId = ? AND bookId = ?", new Object[] {card, branch, book});
		if (ret.size() == 0) { //couldn't find a match
			return new BookLoans(0, 0, 0, null, null, null);
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<BookLoans> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoansList = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoans = new BookLoans();
			bookLoans.setBookID(rs.getInt("bookId"));
			bookLoans.setBranchID(rs.getInt("branchId"));
			bookLoans.setCardNo(rs.getInt("cardNo"));
			bookLoans.setDateOut(OffsetDateTime.ofInstant(rs.getTimestamp("dateOut").toInstant(), ZoneOffset.UTC)); //some messing around to get from epoch time to java time
			bookLoans.setDueDate(OffsetDateTime.ofInstant(rs.getTimestamp("dueDate").toInstant(), ZoneOffset.UTC));
			try {
				bookLoans.setDateIn(OffsetDateTime.ofInstant(rs.getTimestamp("dateIn").toInstant(), ZoneOffset.UTC));
			}
			catch(Exception e) {bookLoans.setDateIn(null);}
			bookLoansList.add(bookLoans);
			
		}
		return bookLoansList;
	}

}
