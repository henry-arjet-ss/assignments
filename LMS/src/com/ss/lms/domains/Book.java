package com.ss.lms.domains;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering
//Book is used heavily in UI, and so I have all these different fields so I can display them all in one neat little package

public class Book {
	private int id;
	private String title;
	private Publisher publisher;
	private String author; //the reason I have these last three vars is so I can use them as input options
	private String branch; //    while displaying their author/branch
	private int branchID = 0;
		
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
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getBranchID() {
		return branchID;
	}
	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}
	public Book(int id, String title, Publisher publisher) { //author/branch are left off this constructor as they are rarely needed at instantiation
		this.id = id;
		this.title = title;
		this.publisher = publisher;
	}

	
}
