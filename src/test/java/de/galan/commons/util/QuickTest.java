package de.galan.commons.util;

import org.junit.Ignore;
import org.junit.Test;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.Sleeper;


/**
 * CUT JvmUtils
 *
 * @author galan
 */
public class QuickTest extends AbstractTestParent {

	@Test
	@Ignore
	public void terminate() throws Exception {
		//JvmUtils.terminate().message("kaboom").threaded(false).in("5s");
		JvmUtil.terminate().message("BAM").threaded(true).now();
		for (;;) {
			Sleeper.sleep("1s");
			Logr.get().info("running");
		}
	}

}
