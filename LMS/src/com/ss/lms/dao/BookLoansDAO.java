 package com.ss.lms.dao;

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
	public void addBookLoans(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?, ?)", new Object[] {bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo(),
				Timestamp.valueOf(bookLoans.getDateIn().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()), //OffsetDateTime to Timestamp
				Timestamp.valueOf(bookLoans.getDueDate().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
				Timestamp.valueOf(bookLoans.getDateOut().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
		});
	}
	public void updateBookLoans(BookLoans bookLoans) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_loans SET dateIn = ?, dueDate = ?, dateOut = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {
				Timestamp.valueOf(bookLoans.getDateIn().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
				Timestamp.valueOf(bookLoans.getDueDate().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
				Timestamp.valueOf(bookLoans.getDateOut().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),			
				bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo()});
	}
	public void deleteBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {bookLoans.getBookID(), bookLoans.getBranchID(), bookLoans.getCardNo()});		
	}
	public List<BookLoans> readAllBookLoans() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans", null);
		
	}
	
	public BookLoans readBookLoansByIDs(int id) throws ClassNotFoundException, SQLException{
		List<BookLoans> ret = read("SELECT * FROM tbl_book_loans WHERE bookId = ?", new Object[] {id});
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
			bookLoans.setDateIn(OffsetDateTime.ofInstant(rs.getTimestamp("dateIn").toInstant(), ZoneOffset.UTC));

			bookLoansList.add(bookLoans);
			
		}
		return bookLoansList;
	}

}
