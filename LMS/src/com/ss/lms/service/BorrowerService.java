package com.ss.lms.service;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

import java.sql.Connection;
import com.ss.lms.dao.BaseDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.domains.Borrower;

public class BorrowerService extends BaseService<Borrower> {
	protected BaseDAO<Borrower> getDAO(Connection conn) {
		return new BorrowerDAO(conn);
	}
	
	public boolean verifyCardNum(int cardNum) {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BorrowerDAO dao = new BorrowerDAO(conn);
			
			Borrower b = dao.readBorrowerByCardNo(cardNum);
			if (b.getCardNo() == cardNum) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}//else is handled by the finally and the ending return false
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading record");

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	
	}
	
}
