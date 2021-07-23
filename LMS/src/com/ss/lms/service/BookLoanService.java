package com.ss.lms.service;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//Although tbl_book_loans has no primary key, it is still important enough to merit it's own service
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookLoansDAO;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookLoans;
import com.ss.lms.domains.Branch;

public class BookLoanService extends BaseService<BookLoans> {

	@Override
	protected BaseDAO<BookLoans> getDAO(Connection conn) {
		return new BookLoansDAO(conn);
	}
	
	public void checkout(Book book, Branch branch, int cardNum) {

		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookCopiesDAO copiesDAO = new BookCopiesDAO(conn);
			BookLoansDAO loansDAO = new BookLoansDAO(conn);
			
			//First decrement bookCopies
			copiesDAO.decrement(book.getId(), branch.getId());
			
			BookLoans loan = new BookLoans();
			loan.setBookID(book.getId());
			loan.setBranchID(branch.getId());
			loan.setCardNo(cardNum);
			loan.setDateOut(OffsetDateTime.now());
			loan.setDueDate(OffsetDateTime.now().plusWeeks(1));
			loan.setDateIn(null);
			loansDAO.delete(loan);//delete if already existing i.e. if this user has checked this book out before
			loansDAO.create(loan);
			conn.commit();
			
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
	}
	
	public OffsetDateTime getDueDate(Book book, int cardNum) {
		OffsetDateTime ret = null;
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookLoansDAO dao = new BookLoansDAO(conn);
			BookLoans loan = dao.readBookLoan(cardNum, book.getBranchID(), book.getId()); 
			ret = loan.getDueDate();
			
			
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
	
	public void extendDate(Book book, int cardNum, OffsetDateTime newDate) {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookLoansDAO loansDAO = new BookLoansDAO(conn);
			
			BookLoans loan = new BookLoans();
			loan.setBookID(book.getId());
			loan.setBranchID(book.getBranchID());
			loan.setCardNo(cardNum);
			loan.setDueDate(newDate);
			loansDAO.updateDueDate(loan);
			conn.commit();
			
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
	}
	public void returnBook(Book book, int cardNum) {

		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookCopiesDAO copiesDAO = new BookCopiesDAO(conn);
			BookLoansDAO loansDAO = new BookLoansDAO(conn);
			
			//First increment bookCopies
			copiesDAO.increment(book.getId(), book.getBranchID());
			
			BookLoans loan = new BookLoans();
			loan.setBookID(book.getId());
			loan.setBranchID(book.getBranchID());
			loan.setCardNo(cardNum);
			loan.setDateIn(OffsetDateTime.now());
			loansDAO.returnBook(loan);
			conn.commit();
			
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
	}
	
}