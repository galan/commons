package de.galan.commons.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.ApplicationClock;


/**
 * Test parent for unit tests.
 *
 * @author galan
 */
public class AbstractTestParent {

	@Rule
	public TestName ruleTestName = new TestName();


	@BeforeClass
	public static void ensureNowDateSupplier() {
		//Dates.setDateSupplier(new NowDateSupplier());
	}


	@BeforeClass
	public static void resetSystemClock() {
		ApplicationClock.reset();
	}


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
