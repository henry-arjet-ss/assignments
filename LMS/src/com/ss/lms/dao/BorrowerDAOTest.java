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

import com.ss.lms.domains.Borrower;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class BorrowerDAOTest {
	
	static ConnectionUtil connUtil;
	static BorrowerDAO test;
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
			test = new BorrowerDAO(conn);
			test.addBorrower(new Borrower(0, "Fidel Castro", "Havana, Cuba", "+53 (7) 555-1959"));
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
			test = new BorrowerDAO(conn);
			Borrower castro = test.readBorrowerByName("Fidel Castro");
			assertEquals("Fidel Castro", castro.getName());
		} catch(Exception e) {
			e.printStackTrace();
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
			test = new BorrowerDAO(conn);
			List<Borrower> borrowers = test.readAllBorrowers();
			assertFalse(borrowers.isEmpty());
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
			test = new BorrowerDAO(conn);
			Borrower castro = test.readBorrowerByName("Fidel Castro");
			int castroID = castro.getCardNo();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			conn = connUtil.getConnection();
			test = new BorrowerDAO(conn);
			Borrower borrower = new Borrower(castroID, "Fidel CastBro", "Havana, Cuba", "+53 (7) 555-1959");
			test.updateBorrower(borrower);
			conn.commit();
			Borrower castroNew = test.readBorrowerByName("Fidel CastBro");
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
			test = new BorrowerDAO(conn);
			Borrower castro = test.readBorrowerByName("Fidel CastBro");
			int castroID = castro.getCardNo();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			test.deleteBorrower(castroID);
			conn.commit();
			Borrower castroNew = test.readBorrowerByName("Fidel CastBro");
			assertEquals(0, castroNew.getCardNo()); //borrowerId 0 means not found
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
