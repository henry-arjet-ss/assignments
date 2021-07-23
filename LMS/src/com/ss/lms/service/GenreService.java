package com.ss.lms.service;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

import java.sql.Connection;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.domains.Genre;

public class GenreService extends BaseService<Genre> {
	
	protected BaseDAO<Genre> getDAO(Connection conn) {
		return new GenreDAO(conn);
	}
		
	
}
