package com.ss.jb.three.three;

/*
* Third assignment in JavaBasics 3 
* This class counts the number of times a (command line specified) char occurs in a paragraph 
* Specifically the closing line of Much Ado About Nothing Act 4 Scene 2
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class CharCounter {
	
	public static void main(String[] args) {
		//input validation
		if (args.length != 1) {
			System.out.println("Please give me a single character");
			return;
		}
		if (args[0].length()!=1) {
			System.out.println("Please give me a single character");
			return;
		}
		Character target = args[0].charAt(0);//turns the 'string' to a native char
		
		//Away! you are an ass, you are an ass.
		final String text = "Dost thou not suspect my place? dost thou not "
				+ "suspect my years? O that he were here to write me "
				+ "down an ass! But, masters, remember that I am an "
				+ "ass; though it be not written down, yet forget not "
				+ "that I am an ass. No, thou villain, thou art full of "
				+ "piety, as shall be proved upon thee by good witness. "
				+ "I am a wise fellow, and, which is more, an officer, "
				+ "and, which is more, a householder, and, which is "
				+ "more, as pretty a piece of flesh as any is in "
				+ "Messina, and one that knows the law, go to; and a "
				+ "rich fellow enough, go to; and a fellow that hath "
				+ "had losses, and one that hath two gowns and every "
				+ "thing handsome about him. Bring him away. O that "
				+ "I had been writ down an ass!";
		char[] buff = text.toCharArray();
		
		int acc = 0; //accumulator
		for (char c : buff) {
			if (target.equals(c)) {
				acc++;
			}
		}
		
		System.out.printf("The character %s occured %d times in Dogberry's rant.%n", target, acc);
	}
}
