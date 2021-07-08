package com.ss.jb.four.two;
/*
* Second assignment in JavaBasics 4 
* This class creates a deadlock between two business people,
* one will not hand over the goods until she gets the money,
* the other will not hand over the money until he gets the goods
* Lyrics taken from "The road goes on forever" by Robert Earl Keen
* 
* I promise you, this will be the worst one. My other assignments will be less Henryish.
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class Deadlock {
	
	public static void main(String[] args) {
		Integer money = 1000000; //in usd
		Integer goods = 200; //in kilos 

		Runnable sonny = new Runnable() {
			public void run() {
				synchronized (money) {
					try {
						Thread.sleep(100);
						System.out.println("Sonny met the Cubans in a house just off the route\r\n"
								+ "With a briefcase full of money and a pistol in his boot");
					}catch(Exception e) {System.out.println("Whoops!");}
					
					synchronized (goods) {
						//Sonny looked at Sherry and said
						System.out.println("Let's get on out of here");
					}
				}	
			}
		};
		
		Runnable veronica = new Runnable() {//A cuban refugee who delt in contraband
			public void run() {
				synchronized (goods) {//The Cubans grabbed the goodies and Sonny grabbed the Jack
					System.out.println("They soon ran out of money but Sonny knew a man\r\n"
							+ "Who knew some Cuban refugees that delt in contraband");
					try {
						Thread.sleep(100);
					}catch(Exception e) {System.out.println("Whoops!");}
					
					synchronized (money) {
						System.out.println("The party has ended");
					}
				}
			}
		};
		
		new Thread(sonny).start();
		new Thread(veronica).start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			System.out.println("Whoops!");
		}
		System.out.println("The cards were on the table when the law came bustin' in");
		System.out.println("The road goes on forever and the party never ends");
		
	}

}
