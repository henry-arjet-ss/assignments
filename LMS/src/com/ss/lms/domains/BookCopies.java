package com.ss.lms.domains;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

public class BookCopies {
	
	public BookCopies() {
	}
	public BookCopies(int bookID, int branchID, int numCopies) {
		this.bookID = bookID;
		this.branchID = branchID;
		this.numCopies = numCopies;
		
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
	private int bookID;
	private int branchID;
	private int numCopies;
	public int getNumCopies() {
		return numCopies;
	}
	public void setNumCopies(int numCopies) {
		this.numCopies = numCopies;
	}
	
}
