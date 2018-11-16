package de.galan.commons.time;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;


/**
 * CUT Sleeper
 */
public class SleeperTest {

	@Test
	public void sleepLong() {
		long start = System.currentTimeMillis();
		Sleeper.sleep(500);
		long end = System.currentTimeMillis();
		assertTrue(end >= start + 500);
	}


	@Test
	public void sleepHuman() {
		long start = System.currentTimeMillis();
		Sleeper.sleep("500ms");
		long end = System.currentTimeMillis();
		assertTrue(end >= start + 500);
	}

}
