package com.ss.jb.five.lambda;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* Lambda assignments for JavaBasics 5 
* A collection of lambda methods
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class Lamdas {
	//Assignment 1
	public List<String> shortToLong(List<String> in){
		return in.stream().sorted((s1, s2) -> s1.length()-s2.length()).collect(Collectors.toList());
	}
	public List<String> longToShort(List<String> in){
		return in.stream().sorted((s1, s2) -> s2.length()-s1.length()).collect(Collectors.toList());
	}
	public List<String> alphabetic(List<String> in){
		return in.stream().sorted((s1, s2) -> s1.charAt(0) - s2.charAt(0)).collect(Collectors.toList());
	}
	public List<String> e(List<String> in){
		//checks if each string starts with e and converts that bool to an int so they can be compared
		return in.stream().sorted((s1, s2) -> {
			int s1i = (s1.charAt(0) == 'e')?1:0;
			int s2i = (s2.charAt(0) == 'e')?1:0;
			return s2i-s1i;
			}).collect(Collectors.toList());
	}
	
	public List<String> eWithHelper(List<String> in){
		return in.stream().sorted((s1, s2) -> eHelper(s1, s2)).collect(Collectors.toList());
	}
	
	public static int eHelper(String s1, String s2) {
		int s1i = (s1.charAt(0) == 'e')?1:0;
		int s2i = (s2.charAt(0) == 'e')?1:0;
		return s2i-s1i;
	}
	
	//Assignment 2
	public String evenOdd(List<Integer> ints) {
		StringBuilder sb = new StringBuilder();
		ints.forEach((i) -> sb.append( i%2 == 0 ? 'e' : 'o').append(i).append(","));
		sb.deleteCharAt(sb.length() - 1);//delete the trailing comma
		return sb.toString();
	}
	
	//Assignment 3
	public List<String> a3(List<String> strings){
		//outputs a list of strings with but three characters, the first of which being 'a'
		List<String> ret = new ArrayList<String>(); //return value
		strings.forEach(s -> {
			if (s.charAt(0) == 'a' && s.length() == 3) {
				ret.add(s);
			} 
		});
		return ret;
	}
	
	public static void main (String[] args) {
		Lamdas lamdas = new Lamdas();
		List<String> birds = Arrays.asList("blackbird", "eagle", "falcon", "raptor", "lightning", "dragon lady", "frogfoot", "flanker");
		System.out.println(lamdas.shortToLong(birds));
		System.out.println(lamdas.longToShort(birds));
		System.out.println(lamdas.alphabetic(birds));
		System.out.println(lamdas.e(birds));
		System.out.println(lamdas.eWithHelper(birds));
		List<Integer> ints = Arrays.asList(7, 6, 11, 5, 29, 4, 11, 17, 32 );
		System.out.println(lamdas.evenOdd(ints));
		List<String> strs = Arrays.asList("bad", "ace", "answer", "apt", "as", "bro", "all", "ilk", "arc", "arm", "ark", "axe", "Sukhoi Su-25 Frogfoot");
		System.out.println(lamdas.a3(strs));
		
	}
	
	
}