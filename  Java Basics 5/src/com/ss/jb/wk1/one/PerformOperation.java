package com.ss.jb.wk1.one;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface PerformOperation {
	Predicate<Integer> isOdd = (i) -> {
			if (i%2 == 0)
				return false; 
			return true;
	};
	
	Predicate<Integer> isPrime = (i) -> {
		//hardcode 2
		if (i==2) return true; //the only even prime
		
		if (i%2 == 0) return false; //even
		for (int j = 3; j <=  (int)Math.sqrt(i); j+=2) { //tests division by all odds above one
			if (i%j == 0) return false;
		}
		return true;
	};
	
	Predicate<Integer> isPalindrome = (i) -> {
		//harcode length 1 ints
		if (i / 10 == 0) {
			return true;
		}
		String s = Integer.toString(i);//we don't care about the actual number, just its digits. Manipulating it via 
		int len = s.length();
		int checkLen = len/2; //how many numbers we have to check. 
			//Will leave out the middle for an odd number of digits but that's a okay 
		for (int j = 0; j < checkLen; j++) {//check the first and last, then second and second to last, etc. 
			if (s.charAt(j) != s.charAt(len - j - 1)) return false; //the extra -1 is there to convert from length to index 
		}
		return true;
		
	};
	
	//default Function<Integer, Boolean>
	public Boolean operate(int i);
}
