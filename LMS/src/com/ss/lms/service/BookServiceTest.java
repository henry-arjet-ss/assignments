package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ss.lms.domains.Author;
import com.ss.lms.domains.Book;
import com.ss.lms.domains.Branch;
import com.ss.lms.domains.Genre;
import com.ss.lms.domains.Publisher;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


class BookServiceTest {
	
	static BookService serv = new BookService();
	static int bookID = 0;
	
	@Test
	void testReadLoanedBooks() {
		List<Book> books = serv.readLoanedBooks(6); //my books
		boolean pass = false;
		for (Book b : books) {
			if (b.getId() == 24) pass = true; //make sure I have Rats of NIMH checked out
		}
		assertTrue(pass);
	}
	
	@Test
	void testReadBookBranch() {
		Branch branch = new Branch();
		branch.setId(1); //Austin Central
		List<Book> books = serv.readBookBranch(branch);  //has 28 and not 25
		boolean hasOnWar = false;
		boolean hasEgo = false;
		for (Book b : books) {
			if (b.getId() == 28) hasOnWar = true; //austin central has On War
			else if (b.getId() == 25) hasEgo = true; //Austin central does not have The Ego and its Own, so this should remain false
		}
		assertTrue(hasOnWar);
		assertFalse(hasEgo);
	}


	@BeforeAll
	@Test
	static void testAddBookFull() {
		Book book = new Book(0, "Fidel Castro: My Life", new Publisher(1, "UNDEF", "UNDEF", "UNDEF")); 
		List<Integer> genreID = Arrays.asList(new Integer[] {4, 10}); //nonfic and fantasy
		List<Integer> authorID = Arrays.asList(new Integer[] {10}); //John Donne, famous ghostwriter of Fidel Castro's 'autobiography'
		bookID = serv.addBookFull(book, genreID, authorID);
		assertNotEquals(0, bookID);
	}
	@Test
	void testReadBookGenres() {
		Book book = new Book();
		book.setId(bookID);
		List<Genre> genre = serv.readBookGenres(book);
		boolean hasNonFic = false;
		boolean hasFantasy = false;
		for (Genre g : genre) {
			if (g.getId() == 4) hasNonFic = true;
			else if (g.getId() == 10) hasFantasy = true;
		}
		assertTrue(hasNonFic);
		assertTrue(hasFantasy);
	}
	@Test
	void testReadBookAuthors() {
		Book book = new Book();
		book.setId(bookID);
		List<Author> authors = serv.readBookAuthors(book);
		assertEquals(10, authors.get(0).getId()); //assert that Castro's autobiography was, in fact, written by 17th century English poet John Donne 
	}
	@Test
	void testUpdateBookFull() {
		Book book = new Book(bookID, "Fidel CastBro: My Life", new Publisher(1, "UNDEF", "UNDEF", "UNDEF"));
		List<Integer> genreID = Arrays.asList(new Integer[] {4, 10}); //nonfic and fantasy
		List<Integer> authorID = Arrays.asList(new Integer[] {10}); //John Donne, famous ghostwriter of Fidel Castro's 'autobiography'
		serv.updateBookFull(book, genreID, authorID);
		List<Book> books = serv.read();
		boolean pass = false;
		for (Book b : books) {
			if (b.getId() == bookID && "Fidel CastBro: My Life".equals(b.getTitle())) pass = true; //make sure the book has been updated
		}
		assertTrue(pass);
	}
	
	
	@AfterAll
	static void deleteBook() {
		serv.delete(new Book(bookID, null, null));
	}
	
	

}
/*

@Test
@Order(4)
void testUpdate() throws SQLException { 
	//System.out.println("Test Update running");
	try {
		conn = connUtil.getConnection();
		test = new BookDAO(conn);
		Book castro = test.readBookByName("Fidel Castro: My Life");
		int castroID = castro.getId();
		
		conn = connUtil.getConnection();
		test = new BookDAO(conn);
		Book book = new Book(castroID, "Fidel CastBro: My Life", new Publisher(1, "UNDEF", "UNDEF", "UNDEF"));
		test.update(book);
		conn.commit();
		Book castroNew = test.readBookByName("Fidel CastBro: My Life");
		assertEquals("Fidel CastBro: My Life", castroNew.getTitle());
	} catch(Exception e) {
		e.printStackTrace();
		conn.rollback();
		System.out.println("failed to pull Castro");
		fail("failed to pull All");
	}finally {
		conn.close();
	}
}

@Test
@Order(5)
void testDelete() throws SQLException { 
	//System.out.println("Test Delete running");
	try {
		conn = connUtil.getConnection();
		test = new BookDAO(conn);
		Book castro = test.readBookByName("Fidel CastBro: My Life");
		
		test.delete(castro);
		conn.commit();
		Book castroNew = test.readBookByName("Fidel CastBro: My Life");
		assertEquals(0, castroNew.getId()); //BookId 0 means not found
	} catch(Exception e) {
		conn.rollback();
		//e.printStackTrace();
		System.out.println("failed to pull Castro");
		fail("failed to pull All");
	}finally {
		conn.close();
	}
}





}*/