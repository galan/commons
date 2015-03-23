package de.galan.commons.util;

import org.junit.Test;

import de.galan.commons.logging.Say;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class SayTest {//extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		Say.info("a {x} b {y}", new NullPointerException("yyy"), 1, 2);
		Say.info("a", new NullPointerException("ccc"));
		Say.info("{}", new NullPointerException("xxx"));
		//LogManager.getLogger().info(new PayloadMessage("hello", null, false, new NullPointerException("abc")));
		//LogManager.getLogger().info("abc", new NullPointerException("abc"));
		//Logger log = LogManager.getLogger();
		//String[] args = new String[] {"log4j", "slf4j"};
		//ParameterizedMessage xxx = new MyMessage("a {} b {}", args);
		//log.info(xxx);
	}


	@Test
	public void normalParameterized() throws Exception {
		//Logger log = LogManager.getLogger();
		//log.info("hello {}, bye {}", "log4j");
		//log.info("hello {}, bye {}", "log4j", "slf4j", "next");
	}


	@Test
	public void saytest() throws Exception {
		//Say.info("a {} b {}", "x", "y");
	}


	@Test
	public void factory() throws Exception {
		//Say.error("a", new NullPointerException());
		//Logger log = LogManager.getLogger(PayloadMessageFactory.INSTANCE);
		//Logger log = LogManager.getLogger();
		//log.error("a {} b {}", new NullPointerException(), "x", "y");
		//log.error("a {}", new NullPointerException(), "a", "b");
		//log.error("a {} b {}", new NullPointerException(), "a", "b");
		//log.error("a {} b {}", "x", "y", "z", new NullPointerException());
		//log.error("a {} b {}", new NullPointerException(), "x", "y", "z");
		//log.error("a {}", "a", "b", new NullPointerException());
	}

}
