package de.galan.commons.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;


/**
 * Clock that acts as time emitter for the application. Uses a java.time.clock.SystemClock (UTC) by default.
 *
 * @author daniel
 */
public class ApplicationClock {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static Clock clock = Clock.systemUTC();


	/** Returns the configured application Clock instance. */
	public static Clock getClock() {
		return clock;
	}


	/** Sets the application clock. This should be done only at startup (usually not necessary), or in unit tests. */
	public static void setClock(Clock clock) {
		ApplicationClock.clock = clock;
	}


	/** Resets the application clock to the default. */
	public static void reset() {
		clock = Clock.systemUTC();
	}


	/** Creates a fixed Clock. */
	public static void setIso(Instant time) {
		setClock(Clock.fixed(time, ZoneId.of("UTC")));
	}


	/** Creates a fixed Clock. */
	public static void setUtc(Date time) {
		setClock(Clock.fixed(time.toInstant(), ZoneId.of("UTC")));
	}


	/** Creates a fixed Clock with the input format "yyyy-MM-dd'T'HH:mm:ss'Z'" using UTC as timezone. */
	public static void setUtc(String time) {
		Instant instant = Instant.parse(time);
		setClock(Clock.fixed(instant, ZoneId.of("UTC")));
	}


	/** Creates a fixed Clock with the input format "yyyy-MM-dd HH:mm:ss" using the local (systemDefault) timezone. */
	public static void setLocal(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date result = null;
		try {
			result = sdf.parse(time);
		}
		catch (ParseException pex) {
			throw new RuntimeException("Time is not parseable (" + time + ")", pex);
		}
		setClock(Clock.fixed(result.toInstant(), ZoneId.systemDefault()));
	}

}
