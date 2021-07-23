package com.ss.lms.domains;

//Smoothstack Essentials LMS project
//Henry Arjet - July Cloud Engineering

public class Borrower {
	

	public Borrower(int cardNo, String name, String address, String phone) {
		this.cardNo = cardNo;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	public Borrower() {}
	
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	private int cardNo;
	private String name;
	private String address;
	private String phone;
	
	
}
