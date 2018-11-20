package de.galan.commons.test.jupiter;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.galan.commons.test.Tests;
import de.galan.commons.time.ApplicationClock;


/**
 * CUT ApplicationClockExtension
 */
@ExtendWith(ApplicationClockExtension.class)
public class ApplicationClockExtensionTest {

	private static final Instant INSTANT = instantUtc("2018-07-05T15:00:00Z");


	@Test
	public void checkChange() {
		ApplicationClock.setIso(INSTANT);
		assertThat(now()).isEqualTo(INSTANT);
	}


	@Test
	public void checkSet() {
		Tests.assertDateNear("2s", Instant.now());
	}

}
