package com.ss.jb.wk1.rec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RecursionTest {

	Recursion recky = new Recursion();
	
	@Test
	void testProcessList() {
		assertEquals(recky.processList(Arrays.asList(3, 6, 6, 9, 6, 2, 2, 2, 2, 1)), Arrays.asList(3, 12, 9, 6, 8, 1));
		assertEquals(recky.processList(Arrays.asList(2, 4, 2, 2, 2, 8)), Arrays.asList(2, 4, 6, 8));
		assertEquals(recky.processList(Arrays.asList(2, 3, 2, 2, 5, 2, 2, 2)), Arrays.asList(2, 3, 4, 5, 6));
		assertEquals(recky.processList(Arrays.asList(2, 4, 2, 2, 2, 8)), Arrays.asList(2, 4, 6, 8));
		
	}
	
	@Test
	void testGroupSumHelper() {
		assertEquals(recky.groupSumHelper( Arrays.asList(8), 8), true);//target is only element
		assertEquals(recky.groupSumHelper( Arrays.asList(1, 2), 3), true);//no distractions
		assertEquals(recky.groupSumHelper( Arrays.asList(1, 4, 2), 3), true);//distraction
		assertEquals(recky.groupSumHelper( Arrays.asList(2, 4, 6, 8), 9), false);//false
		assertEquals(recky.groupSumHelper( Arrays.asList(2, 4, 6, 8), 5), false);//false with smaller number
		assertEquals(recky.groupSumHelper( Arrays.asList(2, 3, 4, 5, 6), 12), true);//three-layer
		
	}
	
	@Test
	void testGroupSumClump() {
		//uses the same test cases as testGroupSumHelper but with some of them split into clumps
		assertEquals(recky.groupSumClump(0, Arrays.asList(2, 2, 1,  2), 3), true);//distraction
		assertEquals(recky.groupSumClump(0, Arrays.asList(2, 4, 2, 2, 2, 8), 9), false);//false
		assertEquals(recky.groupSumClump(0, Arrays.asList(1, 1, 4, 6, 8), 5), false);//false with smaller number
		assertEquals(recky.groupSumClump(0, Arrays.asList(2, 3, 2, 2, 5, 2, 2, 2), 12), true);//three-layer
	}

}
