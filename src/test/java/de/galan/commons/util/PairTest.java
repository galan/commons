package de.galan.commons.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;


/**
 * CUT Pair
 */
public class PairTest {

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


	@Test
	public void equality() throws Exception {
		Pair<String, String> p1 = new Pair<>("key1", "value");
		Pair<String, String> p2 = new Pair<>("key1", "value");
		Pair<String, String> p3 = new Pair<>("key3", "value");

		assertThat(p1).isEqualTo(p2);
		assertThat(p1.hashCode()).isEqualTo(p2.hashCode());

		assertThat(p1).isNotEqualTo(p3);
		assertThat(p1.hashCode()).isNotEqualTo(p3.hashCode());

	}

}
