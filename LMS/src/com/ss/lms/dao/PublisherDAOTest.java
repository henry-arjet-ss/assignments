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

import com.ss.lms.domains.Publisher;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class PublisherDAOTest {
	
	static ConnectionUtil connUtil;
	static PublisherDAO test;
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
			test = new PublisherDAO(conn);
			test.addPublisher(new Publisher(0, "Fidel Castro Publishing", "Havana, Cuba", "+53 (7) 555-1959"));
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
			test = new PublisherDAO(conn);
			Publisher castro = test.readPublisherByName("Fidel Castro Publishing");
			assertEquals("Fidel Castro Publishing", castro.getName());
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
			test = new PublisherDAO(conn);
			List<Publisher> publishers = test.readAllPublishers();
			assertFalse(publishers.isEmpty());
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
			test = new PublisherDAO(conn);
			Publisher castro = test.readPublisherByName("Fidel Castro Publishing");
			int castroID = castro.getId();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			conn = connUtil.getConnection();
			test = new PublisherDAO(conn);
			Publisher publisher = new Publisher(castroID, "Fidel CastBro Publishing", "Havana, Cuba", "+53 (7) 555-1959");
			test.updatePublisher(publisher);
			conn.commit();
			Publisher castroNew = test.readPublisherByName("Fidel CastBro Publishing");
			assertEquals("Fidel CastBro Publishing", castroNew.getName());
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
			test = new PublisherDAO(conn);
			Publisher castro = test.readPublisherByName("Fidel CastBro Publishing");
			int castroID = castro.getId();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			test.deletePublisher(castroID);
			conn.commit();
			Publisher castroNew = test.readPublisherByName("Fidel CastBro Publishing");
			assertEquals(0, castroNew.getId()); //publisherId 0 means not found
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
