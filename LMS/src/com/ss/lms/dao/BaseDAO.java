package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {
	public static Connection conn = null;
	public BaseDAO(Connection connIn) {
		BaseDAO.conn = connIn;
		
	}
	
	public abstract int create(T input) throws SQLException, ClassNotFoundException; //returns PK if applicable
	public abstract List<T> read() throws SQLException, ClassNotFoundException;
	public abstract void update(T input) throws SQLException, ClassNotFoundException;
	public abstract void delete(T input) throws SQLException, ClassNotFoundException;
	
	public int save(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if (vals!= null) {
			int ct = 1;
			for(Object o: vals) {
				pstmt.setObject(ct,  o);
				ct++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		} else return 0;
	}
	
	public List<T> pull(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (vals!= null) {
			int ct = 1;
			for(Object o: vals) {
				pstmt.setObject(ct,  o);
				ct++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	
	
	public abstract List<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException;
}
