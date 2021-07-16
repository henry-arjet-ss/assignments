package com.ss.lms.service;

import java.sql.Connection;

import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.domains.Branch;

public class BranchService extends BaseService<Branch> {
	protected BaseDAO<Branch> getDAO(Connection conn) {
		return new BranchDAO(conn);
	}
	
}
