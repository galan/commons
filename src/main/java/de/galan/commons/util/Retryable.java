package de.galan.commons.util;

import static de.galan.commons.util.Sugar.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import de.galan.commons.func.exceptional.ExceptionalRunnable;
import de.galan.commons.logging.Say;
import de.galan.commons.time.Durations;
import de.galan.commons.time.Sleeper;


/**
 * Invokes a Callable/ExceptionalRunnable until it runs without Exception the specified times of retries.
 */
public class Retryable {

	public static final String DEFAULT_WAIT_TIME = "1s";
	public static final long INFINITE = -1;

	private long numberOfRetries; // total number of tries
	private long numberOfTriesLeft; // number left
	private String timeToWait; // wait interval
	private String message; // message to identify retry


	Retryable(long numberOfRetries) {
		retries(numberOfRetries);
	}


	public static Retryable retry(long numberOfRetries) {
		return new Retryable(numberOfRetries);
	}


	public static Retryable infinite() {
		return new Retryable(INFINITE);
	}


	public Retryable timeToWait(String timeToWaitBetween) {
		timeToWait = timeToWaitBetween;
		return this;
	}


	public Retryable timeToWait(long millis) {
		timeToWait = Durations.humanize(millis);
		return this;
	}


	public Retryable retries(long retries) {
		numberOfRetries = retries;
		numberOfTriesLeft = numberOfRetries + 1;
		return this;
	}


	public Retryable message(String msg) {
		message = msg;
		return this;
	}


	public <V> V call(Callable<V> callable) throws Exception {
		while(Thread.currentThread().isAlive()) {
			try {
				return callable.call();
			}
			catch (InterruptedException e) {
				throw e;
			}
			catch (CancellationException e) {
				throw e;
			}
			catch (Exception e) {
				numberOfTriesLeft--;
				if (numberOfRetries != INFINITE) {
					if (numberOfTriesLeft == 0) {
						throw new RetryException(numberOfRetries + " attempts to retry, failed at " + timeToWait + " interval for " + message, e,
								numberOfRetries, timeToWait, message);
					}
					if (isBlank(message)) {
						Say.info("Retrying {numberOfRetriesLeft}/{numberOfRetries} in {timeToWait}",
							numberOfRetries - numberOfTriesLeft + 1, numberOfRetries, timeToWait);
					}
					else {
						Say.info("Retrying {numberOfRetriesLeft}/{numberOfRetries} in {timeToWait} for {message}",
							numberOfRetries - numberOfTriesLeft + 1, numberOfRetries, timeToWait, message);
					}
				}
				else {
					if (isBlank(message)) {
						Say.info("Retrying {} in {}", Math.abs(numberOfTriesLeft + 1), timeToWait);
					}
					else {
						Say.info("Retrying {} in {} for {}", Math.abs(numberOfTriesLeft + 1), timeToWait, message);
					}
				}
				Sleeper.sleep(optional(timeToWait).orElse(DEFAULT_WAIT_TIME));
			}
		}
		throw new InterruptedException("Thread with retry is not longer alive"); // Edge-case where Thread was killed and the Exception was catched by the caller
	}


	public void run(ExceptionalRunnable runnable) throws Exception {
		call(() -> {
			runnable.run();
			return null;
		});
	}

}
