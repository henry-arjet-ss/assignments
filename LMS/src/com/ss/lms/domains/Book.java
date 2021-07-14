package com.ss.lms.domains;

public class Book {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public Book() {
	}
	public Book(int id, String title, Publisher publisher) {
		this.id = id;
		this.title = title;
		this.publisher = publisher;
	}
	private int id;
	private String title;
	private Publisher publisher;
}
