package com.ss.lms.service;

import java.sql.Connection;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BaseDAO;
import com.ss.lms.domains.Author;

public class AuthorService extends BaseService<Author> {
	
	protected BaseDAO<Author> getDAO(Connection conn) {
		return new AuthorDAO(conn);
	}
		
	
}
