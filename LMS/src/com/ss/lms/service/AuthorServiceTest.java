package com.ss.lms.service;

//This test serves mostly to test the basic CRUD operations of BaseService

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.ss.lms.domains.Author;

@TestMethodOrder(OrderAnnotation.class) //The tests must be ordered so that I may manipulate and then read from the DB in different tests
class AuthorServiceTest {
	
	private Author extract(List<Author> results, String name) { //finds Castro
		for (Author a : results) {
			if (name.equals(a.getName())) return a;
		}
		return null;
	}
	
	AuthorService test = null;
	@Test
	@Order(1)
	void testInsert() throws SQLException {
		test = new AuthorService();
		int ret = test.create(new Author(0, "Fidel Castro"));
		assertNotEquals(0, ret); //create returns the primary key, or 0 if none
	}


	@Test
	@Order(2)
	void testRead() throws SQLException { 
		test = new AuthorService();
		List<Author> authors = test.read();
		assertFalse(authors.isEmpty());
	}
	
	@Test
	@Order(3)
	void testUpdate() throws SQLException, ClassNotFoundException { 

		test = new AuthorService();
		List<Author> ret = test.read();
		Author castro = extract(ret, "Fidel Castro");
		int castroID = castro.getId();
		Author author = new Author(castroID, "Fidel CastBro");
		test.update(author);

		ret = test.read();
		Author castroNew = extract(ret, "Fidel CastBro");
		assertEquals("Fidel CastBro", castroNew.getName());
	}
	
	@Test
	@Order(4)
	void testDelete() throws SQLException { 
		test = new AuthorService();
		List<Author> ret = test.read();
		Author castro = extract(ret, "Fidel CastBro");
		test.delete(castro);
		ret = test.read();
		Author castroNew = extract(ret, "Fidel CastBro");
		assertEquals(null, castroNew);
	}
}
