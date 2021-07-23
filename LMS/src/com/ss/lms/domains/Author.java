package com.ss.lms.domains;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

public class Author {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Author(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public Author() {} //default constructor
	private int id;
	private String name;
}
