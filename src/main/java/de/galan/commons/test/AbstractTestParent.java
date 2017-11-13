package de.galan.commons.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


/**
 * Test parent for unit tests.
 */
public class AbstractTestParent {

	@Rule
	public ApplicationClockResetRule clock = new ApplicationClockResetRule();


	/*
	public void setupSnake() {
		// Get the working directory, which is in eclipse/maven the project folder
		String baseDirectory = new File("").getAbsolutePath();
	
		System.setProperty("snake.base", baseDirectory);
		//TODO externalize new SnakeBootstrap().initialize();
	}
	 */

	@Before
	public void setupParent() {
		//setupSnake();
	}


	@After
	public void teardownParent() {
		//
	}

}
