package com.ss.jb.wk1.sing;

import java.sql.Connection;
//remove unused imports


public class SampleSingleton { //Singletons don't need the static keyword

	private Connection conn = null; //nothing needs to be explicitly static as there is only one instance
	
	private volatile SampleSingleton instance = null; //set as volatile for thread safety
	
	public SampleSingleton getInstance() {
		if (instance == null) {//check if it hasn't already been instantiated
									//using a double checked pattern because I don't like wasteful code and avoid it where I can
			synchronized(instance) {//lock the instance
				if (instance == null) { //check that nobody else has instantiated before this thread got a lock
					instance = new SampleSingleton();//after all these guards, finally the mission is over 
				}
			}
		} 
		return instance;
	}
	
}
