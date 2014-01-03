package de.galan.commons.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.DateDsl;
import de.galan.commons.time.NowDateSupplier;


/**
 * Test parent for unit tests.
 * 
 * @author daniel
 */
public class AbstractTestParent {

	private static final Logger LOG = Logr.get();

	@Rule
	public TestName name = new TestName();


	@BeforeClass
	public static void ensureNowDateSupplier() {
		DateDsl.setDateSupplier(new NowDateSupplier());
	}


	@Before
	public void setupParent() {
		String method = "" + getClass().getSimpleName() + "." + name.getMethodName() + "()";
		LOG.info("Executing test {}", method);
	}


	@After
	public void teardownParent() {
		//
	}

}
