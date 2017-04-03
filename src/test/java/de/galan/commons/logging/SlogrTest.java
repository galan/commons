package de.galan.commons.logging;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.slf4j.Logger;


/**
 * CUT Slogr
 */
public class SlogrTest {

	@Test
	public void get() throws Exception {
		assertThat(Slogr.get()).isInstanceOf(org.slf4j.Logger.class);
		assertThat(Slogr.get().getName()).isEqualTo(getClass().getName());
	}


	@Test
	public void otherClass() throws Exception {
		assertThat(new OtherSlogrClass().getLogger()).isInstanceOf(org.slf4j.Logger.class);
		assertThat(new OtherSlogrClass().getLogger().getName()).isEqualTo(OtherSlogrClass.class.getName());
	}

}


/** test class */
class OtherSlogrClass {

	private final static Logger LOG = Slogr.get();


	public Logger getLogger() {
		LOG.info("test");
		return LOG;
	}

}
