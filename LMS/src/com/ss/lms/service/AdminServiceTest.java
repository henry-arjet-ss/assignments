package com.ss.lms.service;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class AdminServiceTest {
	private AdminService aminS = new AdminService();
	
	
	@Test
	void testIO() {
		aminS.inputIndex = 0; //tells the class that we're in test mode
		aminS.testIn = Arrays.asList("3");
		
		System.out.println(aminS.testOut);
	}

}
