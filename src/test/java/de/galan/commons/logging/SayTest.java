package de.galan.commons.logging;

import org.junit.Test;

import de.galan.commons.test.vintage.AbstractTestParent;


/**
 * CUT Say
 */
public class SayTest extends AbstractTestParent {

	@Test
	public void saySimple() throws Exception {
		Say.info("abc");
		Say.info("abc def ghi");
		Say.please();
	}


	@Test
	public void sayFields() throws Exception {
		Say.info("abc {}", "x1");
		Say.info("abc {} {}", "x2", "x3");
	}


	@Test
	public void sayFieldsMeta() throws Exception {
		Say.f("a1", "A").f("a2", "B").f("a3", "C").info("fields");
		Say.f("n", 123L).info("Hello {location}", "world");
	}


	@Test
	public void sayWithException() throws Exception {
		Say.info("a b c", new Exception("abc"));
		Say.info("a {b} c", "value", new Exception("abc"));
	}


	@Test
	public void saySubstituteExceptionToParamsPlain() throws Exception {
		Say.info("A {} C", new Exception("abc"));
	}


	@Test
	public void saySubstituteExceptionToParamsMeta() throws Exception {
		Say.info("A {B} C", new Exception("abc"));
	}

}
