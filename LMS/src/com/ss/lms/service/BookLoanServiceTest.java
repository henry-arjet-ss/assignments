package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookCopies;
import com.ss.lms.domains.Branch;
@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class BookLoanServiceTest {

	static BookLoanService serv = new BookLoanService();


	
	private int getNumCopies(int bookID, int branchID) throws ClassNotFoundException, SQLException {
		ConnectionUtil cUtil = new ConnectionUtil();
		Connection conn = cUtil.getConnection();
		BookCopiesDAO dao = new BookCopiesDAO(conn);
		BookCopies bc = dao.readBookCopiesByID(bookID, branchID);		
		return bc.getNumCopies();
	}
	

	@Order(1)
	@Test
	void testCheckout() throws ClassNotFoundException, SQLException {
	
		serv.checkout(new Book(23, null, null), new Branch(3, null, null), 9); //have Fidel Castro check Watership Down out from Twin Oaks
		assertEquals(getNumCopies(23, 3), 3); //Starts with 4 copies, so it should now be 3
	}
	
	@Order(2)
	@Test
	void testGetDueDate() {
		Book book = new Book();
		book.setId(23);
		book.setBranchID(3);
		OffsetDateTime dueDate = serv.getDueDate(book, 9);
		assertNotNull(dueDate);
	}

	@Order(3)
	@Test
	void testExtendDate() {
		OffsetDateTime extendedDate = OffsetDateTime.now().plusDays(10);
		Book book = new Book();
		book.setId(23);
		book.setBranchID(3);
		serv.extendDate(book, 9, extendedDate);
		OffsetDateTime newDueDate = serv.getDueDate(book, 9);
		assertTrue(newDueDate.isAfter(OffsetDateTime.now().plusDays(9))); //I just need to check that it is extended a bit
	}

	@Order(4)
	@Test
	void testReturnBook() throws ClassNotFoundException, SQLException {
		Book book = new Book();
		book.setId(23);
		book.setBranchID(3);
		serv.returnBook(book, 9);	
		assertEquals(getNumCopies(23, 3), 4); //Starts with 4 copies, so it should be back to 4
	}

}
