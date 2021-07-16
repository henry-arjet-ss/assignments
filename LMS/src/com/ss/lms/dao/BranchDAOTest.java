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

import com.ss.lms.domains.Branch;
import com.ss.lms.service.ConnectionUtil;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class BranchDAOTest {
	
	static ConnectionUtil connUtil;
	static BranchDAO test;
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
			test = new BranchDAO(conn);
			test.addBranch(new Branch(0, "Havana Central", "Havana, Cuba"));
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
			test = new BranchDAO(conn);
			Branch castro = test.readBranchByName("Havana Central");
			assertEquals("Havana Central", castro.getName());
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
			test = new BranchDAO(conn);
			List<Branch> branchs = test.readAllBranchs();
			assertFalse(branchs.isEmpty());
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
			test = new BranchDAO(conn);
			Branch castro = test.readBranchByName("Havana Central");
			int castroID = castro.getId();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			conn = connUtil.getConnection();
			test = new BranchDAO(conn);
			Branch branch = new Branch(castroID, "Havana Outer", "Havana, Cuba");
			test.updateBranch(branch);
			conn.commit();
			Branch castroNew = test.readBranchByName("Havana Outer");
			assertEquals("Havana Outer", castroNew.getName());
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
			test = new BranchDAO(conn);
			Branch castro = test.readBranchByName("Havana Outer");
			int castroID = castro.getId();
			assertNotEquals(0, castroID);//would mean that record was not found
			
			test.deleteBranch(castroID);
			conn.commit();
			Branch castroNew = test.readBranchByName("Havana Outer");
			assertEquals(0, castroNew.getId()); //branchId 0 means not found
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
