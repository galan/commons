package de.galan.commons.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import de.galan.commons.logging.Logr;


/**
 * Test parent for unit tests.
 */
public class AbstractTestParent {

	@Rule
	public TestName ruleTestName = new TestName();

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
		String method = "" + getClass().getSimpleName() + "." + ruleTestName.getMethodName() + "()";
		Logr.get().info("Executing test {}", method);
	}


	@After
	public void teardownParent() {
		//
	}

}
