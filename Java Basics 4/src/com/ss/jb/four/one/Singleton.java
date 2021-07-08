package com.ss.jb.four.one;
/*
* First assignment in JavaBasics 4 
* A double locked singleton
* Henry Arjet 
* July 2021 Cloud Engineering
*/
public class Singleton {

	private Singleton() {}
	
	private static Singleton instance = null;
	
	public Singleton getInstance() {
		if (instance == null) { //checks if already instantiated
			synchronized (instance) {//locks instance
				if (instance == null) {//checks that the singleton hasn't been instantiated between the last check and the lock
						instance = new Singleton();
					
				}
			}
		}
		return instance;
	}

}
