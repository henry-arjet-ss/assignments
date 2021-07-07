package com.ss.jb.two.one;

/**
 * First assignment in JavaBasics 2 
 * Accepts command line arguments in form of decimal ints and doubles and adds them 
 * Henry Arjet 
 * July 2021 Cloud Engineering
 */

public class Adder {

	public static void main(String[] args) {
		int workingValue = 0; //accumulator - all int for no fp error
		double workingValueD = 0.0; //backup accumulator for floating point numbers
		boolean isFloat = false; //lets us know if we must abandon int
		for (String s : args) {
			try {
				int i = Integer.parseInt(s);
				if (isFloat) workingValueD += i;
				else workingValue +=i;
			}catch(Exception e) {//if it's not an int, let's try a double
				try {
					double d = Double.parseDouble(s);
					if (!isFloat) { //this is the first float we've encountered so we gotta switch to a double-based working value
						workingValueD = workingValue;
						isFloat = true;
					}
					workingValueD += d;
				}
				catch(Exception f) {//not an int or a double
					System.out.println("Invalid input");
					return;
				}
			}
		}
		if(isFloat) {
			System.out.printf("Your total value is %.3f%n", workingValueD);
		}
		else System.out.printf("Your total value is %d%n", workingValue);
		
	}

}
