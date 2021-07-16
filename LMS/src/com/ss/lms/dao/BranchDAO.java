package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domains.Branch;

public class BranchDAO extends BaseDAO<Branch>{

	public BranchDAO(Connection connIn) {
		super(connIn);
	}
	
	public int create(Branch branch) throws SQLException, ClassNotFoundException {
		return save("INSERT INTO tbl_library_branch VALUES (0, ?, ?)", new Object[] {branch.getName(), branch.getAddress()});
	}
	public void update(Branch branch) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?", new Object[] {branch.getName(), branch.getAddress(), branch.getId()});
	}
	public void delete(Branch branch) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] {branch.getId()});		
	}
	public List<Branch> read() throws ClassNotFoundException, SQLException {
		return pull("SELECT * FROM tbl_library_branch", null);
		
	}
	
	public Branch readBranchByID(int id) throws ClassNotFoundException, SQLException{
		List<Branch> ret = pull("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] {id});
		if (ret.size() == 0) { //couldn't find a match
			return new Branch(0, "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}
	public Branch readBranchByName(String name) throws ClassNotFoundException, SQLException{
		List<Branch> ret = pull("SELECT * FROM tbl_library_branch WHERE branchName = ?", new Object[] {name});
		if (ret.size() == 0) { //couldn't find a match
			return new Branch(0, "NOT FOUND", "NOT FOUND");
		}
		return ret.get(0);
	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch branch = new Branch();
			branch.setId(rs.getInt("branchId"));
			branch.setName(rs.getString("branchName"));
			branch.setAddress(rs.getString("branchAddress"));
			
			branchs.add(branch);
			
		}
		return branchs;
	}
	
	
	
	

}
