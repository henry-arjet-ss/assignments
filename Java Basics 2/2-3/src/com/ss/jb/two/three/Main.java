package com.ss.jb.two.three;
/*
* Third assignment in JavaBasics 2 
* This class demonstrates the functionality of the shape classes 
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class Main {

	public static void main(String[] args) {
		Shape[] shapes = new Shape[] {new Rectangle (3.1, 41.5),new Circle (9), new Triangle (26.5, 3)};
		for (Shape s : shapes) {
			s.display();
		}
		
	}
	
}
