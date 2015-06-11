package de.galan.commons.logging;

import org.junit.Test;


/**
 * CUT Say
 *
 * @author galan
 */
public class SayTest { //extends AbstractTestParent {

	@Test(expected = NullPointerException.class)
	public void rethrow() throws NullPointerException {
		Say.traceThrows(new NullPointerException("BAM"));
	}

}
