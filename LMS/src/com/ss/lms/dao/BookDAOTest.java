package com.ss.lms.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ss.lms.domains.Book;
import com.ss.lms.domains.Publisher;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class BookDAOTest {
	
	static ConnectionUtil connUtil;
	static BookDAO test;
	static Connection conn = null;
	@BeforeAll
	public static void setUp() {
		connUtil = new ConnectionUtil();
	}
	
	@Test
	@Order(1)
	void testInsert() throws SQLException { 
		//System.out.println("Test Insert running");
		try {
			conn = connUtil.getConnection();
			test = new BookDAO(conn);
			test.addBook(new Book(0, "Fidel Castro: My Life", new Publisher(1, "UNDEF", "UNDEF", "UNDEF")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("failed to add Castro");
			fail("failed to add Castro");
		} finally{conn.close();}
	}
	
	

	@Test
	@Order(2)
	void testRead() throws SQLException { 
		//System.out.println("Test Read running");
		try {
			conn = connUtil.getConnection();
			test = new BookDAO(conn);
			Book castro = test.readBookByName("Fidel Castro: My Life");
			assertEquals(castro.getTitle(), "Fidel Castro: My Life");
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("failed to pull Castro");
			fail("failed to pull Castro");
		}finally {
			conn.close();
		}
	}

	@Test
	@Order(3)
	void testReadAll() throws SQLException { 
		//System.out.println("Test ReadAll running");
		try {

			conn = connUtil.getConnection();
			test = new BookDAO(conn);
			List<Book> books = test.readAllBooks();
			assertFalse(books.isEmpty());
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("failed to pull Castro");
			fail("failed to pull All");
		}finally {
			conn.close();
		}
	}
	
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
			test.updateBook(book);
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
			int castroID = castro.getId();
			
			test.deleteBook(castroID);
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
	
	
	
	

}
