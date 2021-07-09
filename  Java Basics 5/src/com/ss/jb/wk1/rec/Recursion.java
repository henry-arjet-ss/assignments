package com.ss.jb.wk1.rec;

import java.util.ArrayList;
import java.util.List;

/*
* Assignment 1 in Java Basics Week 1 
* Several predicate lambdas that take integer input
* Tested by the PerformanceOperationTest class 
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class Recursion {
/*Given an array of ints, is it possible to choose a group of some of the ints, such that the group sums to the given target, with this additional constraint: 
 * if there are numbers in the array that are adjacent and the identical value, they must either all be chosen, or none of them chosen. 
 * For example, with the array {1, 2, 2, 2, 5, 2}, either all three 2's in the middle must be chosen or not, all as a group. 
 * (one loop can be used to find the extent of the identical values).

groupSumClump(0, [2, 4, 8], 10) - true
groupSumClump(0, [1, 2, 4, 8, 1], 14) - true
groupSumClump(0, [2, 4, 4, 8], 14) - false

*/
	
	public List<Integer> processList (List<Integer> list){ //replaces adjacent identical numbers with their product
		List <Integer> ret = new ArrayList(); //return list
		for (int i = 0; i < list.size(); i++) {
			int product = list.get(i); //what's going to be pushed to return list - if adjacent numbers exist this accumulates their product
			if (i+1 >= list.size()){//check if we're at the end of the list
				ret.add(product);//aka list.get(i)
				return ret;
			}
			while (list.get(i) == list.get(i+1)) { //for as long as the next number is identical
				
				product += list.get(i); //accumulate the product
				i++; //and move on
				if (i+1 >= list.size()){//check if we're at the end of the list, again since the i++
					ret.add(product);//aka list.get(i)
					return ret;
				}
			}
			ret.add(product);
		}
		return ret;
		
	}
	
	public boolean groupSumClump(int uselessInt, List<Integer> rawList, int target ){//uselessInt is in the example, so I feel I need to accept it
		List <Integer> list = processList(rawList); //changes adjacent numbers to their products
		return groupSumHelper(list, target);
	}
	
	public boolean groupSumHelper(List<Integer> list, int target) {
		for (Integer i : list) {
			if (i == target) return true;//Ladies and Gentlemen, we got 'em
			if (target - i > 0) { //make sure target is bigger than the number we're trying to subtract from it
				List<Integer> copy = new ArrayList(list);//makes a copy of the list
				copy.remove(i);//takes away i since we're factoring it out
				if (groupSumHelper(copy, target-i)) return true; //if the remaining numbers can sum to the remaining target, we got it
			}
		}
		
		return false; //if nothing returns true, it is unsummable
	}
	

}
