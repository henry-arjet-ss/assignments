package com.ss.lms.service;

import java.sql.Connection;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BookLoansDAO;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookLoans;
import com.ss.lms.domains.Branch;

public class BookLoanService extends BaseService<BookLoans> {

	@Override
	protected BaseDAO<BookLoans> getDAO(Connection conn) {
		return new BookLoansDAO(conn);
	}
	
	public void checkout(Book book, Branch branch) {
		
		
	}

}