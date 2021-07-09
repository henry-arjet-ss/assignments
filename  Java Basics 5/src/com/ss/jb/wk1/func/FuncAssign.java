package com.ss.jb.wk1.func;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
* Functional assignments for JavaBasics 5
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class FuncAssign {
	
	//Assignment 2:
	public List<Integer> rightDigit(List<Integer> list){
		List<Integer> ret = new ArrayList<Integer>();//return list
		list.forEach(i -> ret.add(i % 10)); //add the rightmost digit of each int to the return list
		return ret;
	}
	
	//Assignment 3:
	public List<Integer> doubling(List<Integer> list){
		List<Integer> ret = new ArrayList<Integer>();//return list
		list.forEach(i -> ret.add(i<<1)); //add the doubled int (leftshifted 1) to the return list
		return ret;
	}
	
	//Assignment 4:
	public List<String> noX(List<String> list){
		List<String> ret = new ArrayList<String>();//return list
		list.forEach(s -> {
			StringBuilder s2 = new StringBuilder(""); //X-free string to add back to the list
			for (char c : s.toCharArray()) {
				if (c != 'x') {
					s2.append(c);
					}
			}

			ret.add(s2.toString());
		}); 
		return ret;
	}
	
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,03,267,30,692,01,3,17,460,27,1,959,3029,273,99,28109,131,66,2302,0,61,3150,2849,074,6561,647,5,494,404,4,8); 
			//sha256 hash for Batter my Heart by John Donne, converted to dec and randomly broken up
		FuncAssign funky = new FuncAssign();
		List<Integer> list2 = funky.rightDigit(list);
		System.out.println(list2);
		List<Integer> list3 = funky.doubling(list2);
		System.out.println(list3);
		
		List<String> listS = Arrays.asList("Rastsvetali", "yabloni", "i", "grushi","Poplyli", "tumany", "nad", "rekoy", "pox", "addax", "relax", "oxygen"); 
		System.out.println(funky.noX(listS));
		
	}

}
