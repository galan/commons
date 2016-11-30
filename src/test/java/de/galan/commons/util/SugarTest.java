package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;


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

}
