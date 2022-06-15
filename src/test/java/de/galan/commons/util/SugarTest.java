package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;


/**
 * CUT Optionals.
 */
public class SugarTest {

	@Test
	public void optPresent() throws Exception {
		Optional<String> optional = Sugar.optional("aaa");
		assertThat(optional).isPresent();
	}


	@Test
	public void optNull() throws Exception {
		Optional<String> optional = Sugar.optional(null);
		assertThat(optional).isNotPresent();
	}


	@Test
	public void firstSingle() throws Exception {
		assertThat(Sugar.first("a")).isEqualTo("a");
	}


	@Test
	public void firstOnlyNull() throws Exception {
		assertThat(Sugar.first((String)null)).isNull();
	}


	@Test
	public void firstOnlyNulls() throws Exception {
		assertThat(Sugar.first((String)null, (String)null)).isNull();
	}


	@Test
	public void firstTwo() throws Exception {
		assertThat(Sugar.first("a", "b")).isEqualTo("a");
	}


	@Test
	public void firstThreeTwo() throws Exception {
		assertThat(Sugar.first(null, "b", "c")).isEqualTo("b");
	}

}
