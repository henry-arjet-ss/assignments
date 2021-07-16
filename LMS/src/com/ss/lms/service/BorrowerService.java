package com.ss.lms.service;

import java.sql.Connection;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.domains.Borrower;

public class BorrowerService extends BaseService<Borrower> {
	protected BaseDAO<Borrower> getDAO(Connection conn) {
		return new BorrowerDAO(conn);
	}
	
}
