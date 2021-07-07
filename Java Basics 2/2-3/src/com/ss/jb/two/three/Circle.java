package com.ss.jb.two.three;

public class Circle implements Shape {
	private double radius;
	
	public Circle (double rad) {
		radius = rad;
	}
	
	@Override
	public double calculateArea() {
		return radius*radius*3.14159265;
	}

	@Override
	public void display() {
		System.out.printf("This circle has a radius of %.2f and an area of %.3f%n", radius, calculateArea());

	}

}
