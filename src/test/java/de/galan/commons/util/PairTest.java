package de.galan.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Pair
 *
 * @author galan
 */
public class PairTest extends AbstractTestParent {

	@Test
	public void defaultConstructor() throws Exception {
		Pair<String, String> p = new Pair<>();
		assertNull(p.getKey());
		assertNull(p.getValue());
		p.setKey("key");
		assertEquals("key", p.getKey());
		assertNull(p.getValue());
		p.setValue("value");
		assertEquals("key", p.getKey());
		assertEquals("value", p.getValue());
		p.setKey(null);
		assertNull(p.getKey());
		assertEquals("value", p.getValue());
		p.setValue(null);
		assertNull(p.getKey());
		assertNull(p.getValue());
	}


	@Test
	public void settingConstructor() throws Exception {
		Pair<String, String> p = new Pair<>("key", "value");
		assertEquals("key", p.getKey());
		assertEquals("value", p.getValue());
		p.setKey(null);
		assertNull(p.getKey());
		assertEquals("value", p.getValue());
		p.setValue(null);
		assertNull(p.getKey());
		assertNull(p.getValue());
	}


	@Test
	public void testToString() throws Exception {
		Pair<String, String> p = new Pair<>("key", "value");
		assertEquals("key/value", p.toString());
	}

}
