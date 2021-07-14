package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
	public static Connection conn = null;
	public BaseDAO(Connection connIn) {
		BaseDAO.conn = connIn;
		
	}
	
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (vals!= null) {
			int ct = 1;
			for(Object o: vals) {
				pstmt.setObject(ct,  o);
				ct++;
			}
		}
		pstmt.executeUpdate();
	}
	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException {
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
