package com.ss.jb.wk1.one;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class PerformOperationTest {

	List<Integer> intList = Arrays.asList(7,6,11,5,29,
											4,11,17,32,563,
											214,18,66,424,8998,
											15351,101);
	PerformOperationImp perfy = new PerformOperationImp();
	
	private List<Boolean> fillList (Predicate<Integer> p) { //creates a list of bools from a predicate applied to the object's intList
		List<Boolean>  processedList = new ArrayList(intList.size()); //the list I will fill from intList;
		intList.forEach(i -> processedList.add(p.test(i))); // for each element in intlist, apply the predicate, 
				//then push the resulting bool to the processed list
		return processedList;
		
	}
	@Test
	void testIsOdd() {
		assertEquals(fillList(perfy.isOdd), Arrays.asList(true,false,true,true,true,
															false,true,true,false,true,
															false,false,false,false,false,
															true,true));
	}
	@Test
	void testIsPrime() {
		assertEquals(fillList(perfy.isPrime), Arrays.asList(true,false,true,true,true,
																false,true,true,false,true,
																false,false,false,false,false,
																false,true)); //15351 is the only non prime odd
	}
	@Test
	void testIsPalindrome() {
		assertEquals(fillList(perfy.isPalindrome), Arrays.asList(true,true,true,true,false,
																	true, true, false, false, false,
																	false,false,true,true,true,
																	true,true)); //15351 is the only non prime odd
	}
}
