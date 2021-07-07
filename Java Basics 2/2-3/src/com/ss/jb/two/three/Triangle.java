package com.ss.jb.two.three;

public class Triangle implements Shape {

	private double base;
	private double height;
	
	public Triangle(double b, double h) {
		base = b;
		height = h;
	}
	
	@Override
	public double calculateArea() {	
		return base * height * 0.5;
	}

	@Override
	public void display() {
		System.out.printf("This triangle has a base of %.2f, a height of %.2f, and an area of %.3f%n", base, height, calculateArea());
	}

}
