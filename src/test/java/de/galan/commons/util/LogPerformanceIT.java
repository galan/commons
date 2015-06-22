package de.galan.commons.util;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Stopwatch;

import de.galan.commons.logging.Say;
import de.galan.commons.time.Durations;


/**
 * Some benchmarks
 *
 * @author galan
 */
@Ignore
public class LogPerformanceIT {

	int loops = 100_000;


	// practically identical
	@Test
	public void simple() throws Exception {
		String simpleMessage = "Hello World";
		Stopwatch watchSay = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			Say.info(simpleMessage);
		}
		watchSay.stop();

		final Logger LOG = LogManager.getLogger();
		Stopwatch watchLog = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			LOG.info(simpleMessage);
		}
		watchLog.stop();

		Say.info("Simple Say {} took {}", loops, Durations.humanizeTime(watchSay.elapsed(TimeUnit.MILLISECONDS)));
		Say.info("Simple Log {} took {}", loops, Durations.humanizeTime(watchLog.elapsed(TimeUnit.MILLISECONDS)));
	}


	// Test seems to have same performance :D (sometimes)
	@Ignore
	@Test
	public void parameter() throws Exception {
		String message = "Hello {}, Bye {}";
		Stopwatch watchSay = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			Say.info(message, "world", 42L);
		}
		watchSay.stop();

		final Logger LOG = LogManager.getLogger();
		Stopwatch watchLog = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			LOG.info(message, "world", 42L);
		}
		watchLog.stop();

		Say.info("Parameter Say {} took {}", loops, Durations.humanizeTime(watchSay.elapsed(TimeUnit.MILLISECONDS)));
		Say.info("Parameter Log {} took {}", loops, Durations.humanizeTime(watchLog.elapsed(TimeUnit.MILLISECONDS)));
	}


	// Log much faster then say, but is probably still affordable for non-realtime application
	@Ignore
	@Test
	public void disabled() throws Exception {
		String message = "Hello {}, Bye {}";
		Stopwatch watchSay = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			Say.debug(message, "world", 42L);
		}
		watchSay.stop();

		final Logger LOG = LogManager.getLogger();
		Stopwatch watchLog = Stopwatch.createStarted();
		for (int i = 0; i < loops; i++) {
			LOG.debug(message, "world", 42L);
		}
		watchLog.stop();

		Say.info("Simple Say {} took {}", loops, Durations.humanizeTime(watchSay.elapsed(TimeUnit.MILLISECONDS)));
		Say.info("Simple Log {} took {}", loops, Durations.humanizeTime(watchLog.elapsed(TimeUnit.MILLISECONDS)));
	}

}
