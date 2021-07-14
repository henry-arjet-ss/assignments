package com.ss.lms.domains;

public class BookGenre {
	
	public BookGenre() {
	}
	public BookGenre(int bookID, int genreID) {
		this.bookID = bookID;
		this.genreID = genreID;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public int getGenreID() {
		return genreID;
	}
	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}
	private int bookID;
	private int genreID;
	
}
