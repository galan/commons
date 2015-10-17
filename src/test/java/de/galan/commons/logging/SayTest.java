package de.galan.commons.logging;

import org.junit.Test;


/**
 * CUT Say
 *
 * @author galan
 */
public class SayTest { //extends AbstractTestParent {

	/*
	@Test(expected = NullPointerException.class)
	public void rethrow() throws NullPointerException {
		Say.traceThrows(new NullPointerException("BAM"));
	}
	*/

	@Test
	public void testName() throws Exception {
		Say.info("aa1");
		Say.f("a", "b").f("a2", "b").f("a3", "b").info("aa2");
		Say.f("a", "b").f("a2", "b").f("a3", "b").info("aa3");
		Say.f("a", "b").f("a2", "b").f("a3", "b").info("aa4");
		Say.please();
	}

}
