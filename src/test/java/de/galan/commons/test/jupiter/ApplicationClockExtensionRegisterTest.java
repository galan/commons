package de.galan.commons.test.jupiter;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import de.galan.commons.time.Instants;


/**
 * CUT ApplicationClockExtension
 */
public class ApplicationClockExtensionRegisterTest {

	private static final Instant INSTANT = instantUtc("2018-07-05T15:00:00Z");

	@RegisterExtension
	ApplicationClockExtension ace = ApplicationClockExtension.builder().instant(INSTANT).build();


	@Test
	public void checkSet() {
		assertThat(Instants.now()).isEqualTo(INSTANT);
	}

}
