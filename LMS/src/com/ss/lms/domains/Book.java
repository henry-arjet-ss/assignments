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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Book(int id, String title, Publisher publisher) { //author is left off this constructor as it is rarely needed at instantiation
		this.id = id;
		this.title = title;
		this.publisher = publisher;
	}
	private int id;
	private String title;
	private Publisher publisher;
	private String author;
	
}
