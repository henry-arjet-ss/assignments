package com.ss.jb.two.three;

public class Rectangle implements Shape {
	private double length;
	private double width;
	
	public Rectangle(double l, double w) {
		length = l;
		width = w;
	}
	
	@Override
	public double calculateArea() {	
		return length * width;
	}

	@Override
	public void display() {
		System.out.printf("This rectangle has a length of %.2f, a width of %.2f, and an area of %.3f%n", length, width, calculateArea());
	}

}
