package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>{

	public PublisherDAO(Connection connIn) {
		super(connIn);
	}
	
	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_publisher VALUES (0, ?, ?, ?)", new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPhone()});
	}
	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?", new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPhone(), publisher.getId()});
	}
	public void deletePublisher(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] {id});		
	}
	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_publisher", null);
		
	}
	
	public Publisher readPublisherByID(int id) throws ClassNotFoundException, SQLException{
		List<Publisher> ret = read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Publisher(0, "NOT FOUND", "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}
	public Publisher readPublisherByName(String name) throws ClassNotFoundException, SQLException{
		List<Publisher> ret = read("SELECT * FROM tbl_publisher WHERE publisherName = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Publisher(0, "NOT FOUND", "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setId(rs.getInt("publisherId"));
			publisher.setName(rs.getString("publisherName"));
			publisher.setAddress(rs.getString("publisherAddress"));
			publisher.setPhone(rs.getString("publisherPhone"));
			
			publishers.add(publisher);
			
		}
		return publishers;
	}
	
	
	
	

}
