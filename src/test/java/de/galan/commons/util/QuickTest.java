package de.galan.commons.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;


/**
 * CUT JvmUtils
 */
public class QuickTest {

	@Test
	@Disabled
	public void terminate() throws Exception {
		//JvmUtils.terminate().message("kaboom").threaded(false).in("5s");
		JvmUtil.terminate().message("BAM").threaded(true).now();
		for (;;) {
			Sleeper.sleep("1s");
			Logr.get().info("running");
		}
	}

}
