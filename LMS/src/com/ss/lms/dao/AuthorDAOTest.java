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

import com.ss.lms.domains.Author;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class AuthorDAOTest {
	
	static ConnectionUtil connUtil;
	static AuthorDAO test;
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
			test = new AuthorDAO(conn);
			test.create(new Author(0, "Fidel Castro"));
			conn.commit();
		} catch (Exception e) {
			//e.printStackTrace();
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
			test = new AuthorDAO(conn);
			Author castro = test.readAuthorByName("Fidel Castro");
			assertEquals(castro.getName(), "Fidel Castro");
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
			test = new AuthorDAO(conn);
			List<Author> authors = test.read();
			assertFalse(authors.isEmpty());
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
			test = new AuthorDAO(conn);
			Author castro = test.readAuthorByName("Fidel Castro");
			int castroID = castro.getId();
			
			conn = connUtil.getConnection();
			test = new AuthorDAO(conn);
			Author author = new Author(castroID, "Fidel CastBro");
			test.update(author);
			conn.commit();
			Author castroNew = test.readAuthorByName("Fidel CastBro");
			assertEquals("Fidel CastBro", castroNew.getName());
		} catch(Exception e) {
			//e.printStackTrace();
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
			test = new AuthorDAO(conn);
			Author castro = test.readAuthorByName("Fidel CastBro");
			
			test.delete(castro);
			conn.commit();
			Author castroNew = test.readAuthorByName("Fidel CastBro");
			assertEquals(0, castroNew.getId()); //AuthorId 0 means not found
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
