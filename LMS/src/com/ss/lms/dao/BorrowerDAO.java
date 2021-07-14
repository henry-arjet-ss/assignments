package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection connIn) {
		super(connIn);
	}
	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_borrower VALUES (0, ?, ?, ?)", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
	}
	public void deleteBorrower(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] {id});		
	}
	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_borrower", null);
		
	}
	
	public Borrower readBorrowerByCardNo(int id) throws ClassNotFoundException, SQLException{
		List<Borrower> ret = read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Borrower(0, "NOT FOUND", "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}
	public Borrower readBorrowerByName(String name) throws ClassNotFoundException, SQLException{
		List<Borrower> ret = read("SELECT * FROM tbl_borrower WHERE name = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Borrower(0, "NOT FOUND", "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}
	
	//required for BaseDAO read method
	public List<Borrower> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
			
		}
		return borrowers;
	}

}
