package com.ss.lms.domains;

import java.time.OffsetDateTime;

public class BookLoans {
	
	public BookLoans() {
	}
	
	public BookLoans(int bookID, int branchID, int cardNo, OffsetDateTime dateOut, OffsetDateTime dueDate,
			OffsetDateTime dateIn) {
		this.bookID = bookID;
		this.branchID = branchID;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateIn = dateIn;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public int getBranchID() {
		return branchID;
	}
	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public OffsetDateTime getDateOut() {
		return dateOut;
	}
	public void setDateOut(OffsetDateTime dateOut) {
		this.dateOut = dateOut;
	}
	public OffsetDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(OffsetDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public OffsetDateTime getDateIn() {
		return dateIn;
	}
	public void setDateIn(OffsetDateTime dateIn) {
		this.dateIn = dateIn;
	}

	private int bookID;
	private int branchID;
	private int cardNo;
	private OffsetDateTime dateOut;
	private OffsetDateTime dueDate;
	private OffsetDateTime dateIn;
	
	
	
}
