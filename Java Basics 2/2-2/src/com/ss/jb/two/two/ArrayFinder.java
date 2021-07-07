package com.ss.jb.two.two;

/**
 * Second assignment in JavaBasics 2 
 * Accepts command line arguments in form of decimal ints and doubles and adds them 
 * Henry Arjet 
 * July 2021 Cloud Engineering
 */

public class ArrayFinder {
	
	public static void main(String[] args) {
		int[][] arr = new int[][] { //filled with random numbers
			new int[] { 44, 40, 74, -8, 16},
			new int[] { 75, -8, 16,-24, 94},
			new int[] {-10, 13, 21, 33, 10},
			new int[] { 19, 96, 77, 22, 56},
			new int[] {  2,-15, 57, 72, 37},
		};
		
		int workingValue = Integer.MIN_VALUE;//the current highest number found
		int x = 0;
		int y = 0;//the coordinates of that number
		
		for (int i = 0; i < arr.length; i++) { //can't use a for-each loop because I want to use i and j for x and y
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] > workingValue) {
					workingValue = arr[i][j];
					x = i; y=j; //keeping 0-indexed columnwise notation
				}
			}
		}
		
		System.out.printf("Maximum value of %d found at (%d, %d)%n", workingValue, x, y);
	}
}
