package com.ss.lms.service;

import java.sql.Connection;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.domains.Genre;

public class GenreService extends BaseService<Genre> {
	
	protected BaseDAO<Genre> getDAO(Connection conn) {
		return new GenreDAO(conn);
	}
		
	
}
