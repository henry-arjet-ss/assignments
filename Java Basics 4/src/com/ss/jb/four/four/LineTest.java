package com.ss.jb.four.four;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineTest {
	Line line1 = new Line(0,0, 1, 1);
	Line line2 = new Line(4.6, 3.8, 5.1, 4.3); //slope of 1
	Line line3 = new Line(-2, 3, 8.6, -4.356); //slope of -0.694

	@Test
	void testGetSlope() {
		assertEquals(1.0, line1.getSlope(), 0.001);
		assertEquals(1.0, line2.getSlope(), 0.001);
		assertEquals(-0.69396226415, line3.getSlope(), 0.001);//I should have used something simpler
		assertNotEquals(1.01, line1.getSlope(), 0.001);//make sure its outside the delta
	}
	
	@Test
	void testGetDistance() {
		assertEquals(1.414214/*sqrt(2)*/, line1.getDistance(), 0.001);
		assertEquals(0.707107/*sqrt(2)/2*/, line2.getDistance(), 0.001);
		assertEquals(12.902354, line3.getDistance(), 0.001);
		assertNotEquals(1.0, line1.getDistance(), 0.001);
	}
	
	@Test
	void testparallelTo() {
		assertTrue(line1.parallelTo(line2));
		assertFalse(line1.parallelTo(line3));
		assertTrue(line2.parallelTo(line1));
		assertFalse(line2.parallelTo(line3));
		assertFalse(line3.parallelTo(line1));
		assertFalse(line3.parallelTo(line2));
		assertTrue(line3.parallelTo(line3));
	}

}
