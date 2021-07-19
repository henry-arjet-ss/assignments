package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookCopies;
import com.ss.lms.domains.Branch;

public class BranchService extends BaseService<Branch> {
	protected BaseDAO<Branch> getDAO(Connection conn) {
		return new BranchDAO(conn);
	}
	public BookCopies readBookCopies(Branch branch, Book book) {
		Connection conn = null;
		BookCopies ret = null;
		try {
			conn = cUtil.getConnection();
			BookCopiesDAO dao = new BookCopiesDAO(conn);
			
			ret = dao.readBookCopiesByID(book.getId(), branch.getId());
		} catch (Exception e) {
			System.out.println("Error reading record");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public void setBookCopies(BookCopies bc, boolean shouldCreate) {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookCopiesDAO dao = new BookCopiesDAO(conn);
			if (shouldCreate) dao.create(bc);
			else dao.update(bc);
			conn.commit();
		} catch (Exception e) {
			System.out.println("Error setting record");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
