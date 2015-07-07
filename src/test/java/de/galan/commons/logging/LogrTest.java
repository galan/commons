package de.galan.commons.logging;

import static org.assertj.core.api.StrictAssertions.*;

import org.apache.logging.log4j.Logger;
import org.junit.Test;


/**
 * CUT Logr
 *
 * @author daniel
 */
public class LogrTest {

	@Test
	public void get() throws Exception {
		assertThat(Logr.get()).isInstanceOf(org.apache.logging.log4j.Logger.class);
		assertThat(Logr.get().getName()).isEqualTo(getClass().getName());
	}


	@Test
	public void otherClass() throws Exception {
		assertThat(new OtherLogrClass().getLogger()).isInstanceOf(org.apache.logging.log4j.Logger.class);
		assertThat(new OtherLogrClass().getLogger().getName()).isEqualTo(OtherLogrClass.class.getName());
	}

}


/** test class */
class OtherLogrClass {

	private final static Logger LOG = Logr.get();


	public Logger getLogger() {
		LOG.info("test");
		return LOG;
	}

}
