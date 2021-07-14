package com.ss.lms.domains;

public class BookAuthor {
	
	public BookAuthor() {
	}
	public BookAuthor(int bookID, int authorID) {
		this.bookID = bookID;
		this.authorID = authorID;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	private int bookID;
	private int authorID;
	
}
