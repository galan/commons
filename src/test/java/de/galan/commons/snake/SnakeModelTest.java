package de.galan.commons.snake;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT SystemPropertyAccess
 *
 * @author daniel
 */
public class SnakeModelTest extends AbstractTestParent {

	SystemPropertyAccess access;


	@Before
	public void before() {
		access = new SystemPropertyAccess();
		access.clear();
	}


	@Test
	public void empty() throws Exception {
		assertThat(access.getProperties()).isEmpty();
		assertThat(access.get("aaa.bbb")).isNull();
	}


	@Test
	public void setAndGet() throws Exception {
		access.set("aaa.bbb", "hello world");
		assertThat(access.getProperties()).containsOnly(entry("aaa.bbb", "hello world"));
		assertThat(access.get("aaa.bbb")).isEqualTo("hello world");
		assertThat(System.getProperty("snake:aaa.bbb")).isEqualTo("hello world");
	}

}
