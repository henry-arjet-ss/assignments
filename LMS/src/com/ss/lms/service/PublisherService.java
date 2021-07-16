package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.domains.Publisher;

public class PublisherService extends BaseService<Publisher> {
	protected BaseDAO<Publisher> getDAO(Connection conn) {
		return new PublisherDAO(conn);
	}
	
	public Publisher readByID(int id){
		Connection conn = null;
		Publisher ret = null;
		try {
			conn = cUtil.getConnection();
			PublisherDAO dao = new PublisherDAO(conn);
			
			ret = dao.readPublisherByID(id);
		} catch (Exception e) {
			System.out.println("Error reading record");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
}
