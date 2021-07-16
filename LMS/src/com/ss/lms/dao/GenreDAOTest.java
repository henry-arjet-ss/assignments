/*package com.ss.lms.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ss.lms.domains.Genre;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class GenreDAOTest {
	
	static ConnectionUtil connUtil;
	static GenreDAO test;
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
			test = new GenreDAO(conn);
			test.addGenre(new Genre(0, "Castroism"));
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
			test = new GenreDAO(conn);
			Genre castro = test.readGenreByName("Castroism");
			assertEquals(castro.getName(), "Castroism");
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
			test = new GenreDAO(conn);
			List<Genre> genres = test.readAllGenres();
			assertFalse(genres.isEmpty());
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
			test = new GenreDAO(conn);
			Genre castro = test.readGenreByName("Castroism");
			int castroID = castro.getId();
			
			conn = connUtil.getConnection();
			test = new GenreDAO(conn);
			Genre genre = new Genre(castroID, "CastBroism");
			test.updateGenre(genre);
			conn.commit();
			Genre castroNew = test.readGenreByName("CastBroism");
			assertEquals("CastBroism", castroNew.getName());
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
			test = new GenreDAO(conn);
			Genre castro = test.readGenreByName("CastBroism");
			int castroID = castro.getId();
			
			test.deleteGenre(castroID);
			conn.commit();
			Genre castroNew = test.readGenreByName("CastBroism");
			assertEquals(0, castroNew.getId()); //GenreId 0 means not found
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
*/