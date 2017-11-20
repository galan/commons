package de.galan.commons.time;

import static org.junit.Assert.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Sleeper
 */
public class SleeperTest extends AbstractTestParent {

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
