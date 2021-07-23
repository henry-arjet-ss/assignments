package com.ss.lms.dao;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Author;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection connIn) {
		super(connIn);
	}
	public int create(Author author) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_author VALUES (0, ?)", new Object[] {author.getName()});
	}
	public void update(Author author) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?", new Object[] {author.getName(), author.getId()});
	}
	public void delete(Author input) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] {input.getId()});		
	}
	//public void deleteAuthorByName(String name) throws ClassNotFoundException, SQLException {
	//	save("DELETE FROM tbl_author WHERE authorName = ?", new Object[] {name});		
	//}
	public List<Author> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_author", null);
		
	}
	
	public Author readAuthorByID(int id) throws ClassNotFoundException, SQLException{
		List<Author> ret = pull("SELECT * FROM tbl_author WHERE authorId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Author(0, "NOT FOUND");
		}
		return ret.get(0);
	}
	public Author readAuthorByName(String name) throws ClassNotFoundException, SQLException{
		List<Author> ret = pull("SELECT * FROM tbl_author WHERE authorName = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Author(0, "NOT FOUND");
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<Author> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setId(rs.getInt("authorId"));
			author.setName(rs.getString("authorName"));
			authors.add(author);
			
		}
		return authors;
	}

}
