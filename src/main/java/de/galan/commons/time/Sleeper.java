package de.galan.commons.time;

/**
 * Encapsulates the InterruptedException from Thread.sleep(ms).
 *
 * @author galan
 */
public class Sleeper {

	/**
	 * Sleep a given time (time is parsed with HumanTime)
	 *
	 * @param humanTime The time in a format such as 30m10s10ms
	 */
	public static void sleep(String humanTime) {
		sleep(Durations.dehumanize(humanTime));
	}


	/**
	 * Sleep the given milliseconds, swallowing the exception
	 *
	 * @param millis time in milliseconds
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
