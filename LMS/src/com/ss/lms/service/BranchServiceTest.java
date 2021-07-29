package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ss.lms.domains.Book;
import com.ss.lms.domains.BookCopies;
import com.ss.lms.domains.Branch;

class BranchServiceTest {

	@Test
	void testBranchService() {//I can't check setBookCopies without using readBookCopies so I'm doing this as a single function
		BranchService serv = new BranchService();
		Book book = new Book();
		book.setId(23); //Watership Down
		Branch branch = new Branch();
		branch.setId(1); //Austin Central
		BookCopies bc = serv.readBookCopies(branch, book);
		assertEquals(0, bc.getNumCopies()); //Austin Central should start with 0 copies of Watership Down
		bc.setNumCopies(18);
		serv.setBookCopies(bc, false);
		BookCopies bc2 = serv.readBookCopies(branch, book);
		assertEquals(18, bc2.getNumCopies());
		bc.setNumCopies(0);//set it back to 0
		serv.setBookCopies(bc, false); 
		bc = serv.readBookCopies(branch, book);
		assertEquals(0, bc.getNumCopies());
	}

}
