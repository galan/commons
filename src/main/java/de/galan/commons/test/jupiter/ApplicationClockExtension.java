package de.galan.commons.test.jupiter;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.galan.commons.time.ApplicationClock;


/**
 * Resets the ApplicationClock after each test. A Builder can be used to initialize it for all tests.
 */
public class ApplicationClockExtension implements BeforeEachCallback, AfterEachCallback {

	private Clock clock;

	public static ApplicationClockExtensionBuilder builder() {
		return new ApplicationClockExtensionBuilder();
	}

	/** Builder */
	public static class ApplicationClockExtensionBuilder {

		/** Creates a fixed Clock. */
		public ApplicationClockExtensionBuilder clock(Clock clock) {
			ApplicationClock.setClock(clock);
			return this;
		}


		/** Resets the clock after each test to the system UTC Clock. */
		public ApplicationClockExtensionBuilder systemUTC() {
			ApplicationClock.setClock(Clock.systemUTC());
			return this;
		}


		/** Creates a fixed Clock with the given Instant. */
		public ApplicationClockExtensionBuilder instant(Instant instant) {
			ApplicationClock.setIso(instant);
			return this;
		}


		/**
		 * Creates a fixed Clock with the input format "yyyy-MM-dd HH:mm:ss" using the local (systemDefault) timezone.
		 */
		public ApplicationClockExtensionBuilder local(String time) {
			ApplicationClock.setLocal(time);
			return this;
		}


		/** Creates a fixed Clock with the given date using the utc timezone. */
		public ApplicationClockExtensionBuilder utc(Date time) {
			ApplicationClock.setUtc(time);
			return this;
		}


		/** Creates a fixed Clock with the input format "yyyy-MM-dd'T'HH:mm:ss'Z'" using UTC as timezone. */
		public ApplicationClockExtensionBuilder utc(String time) {
			ApplicationClock.setUtc(time);
			return this;
		}


		public ApplicationClockExtension build() {
			return new ApplicationClockExtension(ApplicationClock.getClock());
		}

	}

	public ApplicationClockExtension() {
		this(null);
	}


	public ApplicationClockExtension(Clock clock) {
		this.clock = clock == null ? Clock.systemUTC() : clock;
	}


	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		ApplicationClock.setClock(clock);
	}


	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		ApplicationClock.reset();
	}

}
