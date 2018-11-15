package de.galan.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;


/**
 * Invokes a Callable until it runs without Exception the specified times of retries.<br/>
 * Deprecated, use Retryable instead.
 */
@Deprecated
public class RetriableTask<T> implements Callable<T> {

	private static final Logger LOG = Logr.get();

	private Callable<T> task;
	public static final long DEFAULT_NUMBER_OF_RETRIES = 0;
	public static final String DEFAULT_WAIT_TIME = "1s";

	public static final long INFINITE = -1;

	private long numberOfRetries; // total number of tries
	private long numberOfTriesLeft; // number left
	private String timeToWait; // wait interval
	private String message; // message to identify retry


	public RetriableTask(Callable<T> task) {
		this(DEFAULT_NUMBER_OF_RETRIES, null, task);
	}


	public RetriableTask(long numberOfRetries, Callable<T> task) {
		this(numberOfRetries, null, task);
	}


	public RetriableTask(long numberOfRetries, String timeToWaitBetween, Callable<T> task) {
		this.task = task;
		retries(numberOfRetries);
		timeToWait(timeToWaitBetween);
	}


	public RetriableTask<T> retries(long retries) {
		this.numberOfRetries = retries;
		numberOfTriesLeft = numberOfRetries + 1;
		return this;
	}


	public RetriableTask<T> infinite() {
		return retries(INFINITE);
	}


	public RetriableTask<T> timeToWait(String timeToWaitBetween) {
		this.timeToWait = ObjectUtils.defaultIfNull(timeToWaitBetween, DEFAULT_WAIT_TIME);
		return this;
	}


	public RetriableTask<T> message(String msg) {
		this.message = msg;
		return this;
	}


	@Override
	public T call() throws Exception {
		while(true) {
			try {
				return task.call();
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
						LOG.info("Retrying {}/{} in {}", numberOfRetries - numberOfTriesLeft + 1, numberOfRetries, timeToWait);
					}
					else {
						LOG.info("Retrying {}/{} in {} for {}", numberOfRetries - numberOfTriesLeft + 1, numberOfRetries, timeToWait, message);
					}
				}
				else {
					if (isBlank(message)) {
						LOG.info("Retrying {} in {}", Math.abs(numberOfTriesLeft + 1), timeToWait);
					}
					else {
						LOG.info("Retrying {} in {} for {}", Math.abs(numberOfTriesLeft + 1), timeToWait, message);
					}
				}
				Sleeper.sleep(timeToWait);
			}
		}
	}

}
