package de.galan.commons.test;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import de.galan.commons.time.ApplicationClock;


/**
 * Resets the Application before and after a test-method for you.<br/>
 * Deprecated: Use the JUnit5 ApplicationClockExtension instead.
 */
@Deprecated
public class ApplicationClockResetRule extends TestWatcher {

	@Override
	protected void starting(Description d) {
		ApplicationClock.reset();
	}


	@Override
	protected void finished(Description description) {
		ApplicationClock.reset();
	}

}
