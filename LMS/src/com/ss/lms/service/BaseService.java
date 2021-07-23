package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BaseDAO;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//Base class for all services
//Acts as a template, allowing CRUD functions to be carried through to child objects by passing their DAO as type
//Relies on DAO polymorphism implemented through BaseDAO class to guarantee functions such as the constructor and CRUD

public abstract class BaseService<T> {
	
	ConnectionUtil cUtil = new ConnectionUtil();
	
	protected abstract BaseDAO<T> getDAO(Connection conn); //used to return a DAO of the correct type
	
	public int create(T input) {
		Connection conn = null;
		int ret = 0;
		try {
			conn = cUtil.getConnection();
			BaseDAO<T> dao = getDAO(conn);
			ret = dao.create(input);
			conn.commit();
			System.out.println("Record Created");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding record");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public List<T> read(){
		Connection conn = null;
		List<T> ret = null;
		try {
			conn = cUtil.getConnection();
			BaseDAO<T> dao = getDAO(conn);
			
			ret = dao.read();
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
	
	public void update(T input) {
		Connection conn = null;
		
		try {
			conn = cUtil.getConnection();
			BaseDAO<T> dao = getDAO(conn);		
			dao.update(input);
			conn.commit();
			System.out.println("Record Updated");
		} catch (Exception e) {
			System.out.println("Error updating record");
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
	}
	
	public void delete(T input) {
		Connection conn = null;
		
		try {
			conn = cUtil.getConnection();
			BaseDAO<T> dao = getDAO(conn);
			dao.delete(input);
			conn.commit();
			System.out.println("Record Deleted");
		} catch (Exception e) {
			System.out.println("Error updating record");
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
	}
	
}
