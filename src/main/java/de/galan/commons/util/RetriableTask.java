package de.galan.commons.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;


/**
 * Runs a Callable until it runs without Exception the specified times of retries.
 * 
 * @author daniel
 * @param <T> Possible result of the repeating task
 */
public class RetriableTask<T> implements Callable<T> {

	private static final Logger LOG = Logr.get();

	private Callable<T> task;
	public static final long DEFAULT_NUMBER_OF_RETRIES = 0;
	public static final String DEFAULT_WAIT_TIME = "1s";

	public static final long INFINITE = -1;

	private long numberOfRetries; // total number of tries
	private long numberOfTriesLeft; // number left
	private String timeToWait; // wait interval


	public RetriableTask(Callable<T> task) {
		this(DEFAULT_NUMBER_OF_RETRIES, null, task);
	}


	public RetriableTask(long numberOfRetries, Callable<T> task) {
		this(numberOfRetries, null, task);
	}


	public RetriableTask(long numberOfRetries, String timeToWait, Callable<T> task) {
		this.numberOfRetries = numberOfRetries;
		numberOfTriesLeft = numberOfRetries + 1;
		this.timeToWait = ObjectUtils.defaultIfNull(timeToWait, DEFAULT_WAIT_TIME);
		this.task = task;
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
				if (numberOfRetries != -1) {
					if (numberOfTriesLeft == 0) {
						throw new RetryException(numberOfRetries + " attempts to retry failed at " + timeToWait + "ms interval", e, numberOfRetries, timeToWait);
					}
					LOG.info("Retrying {}/{} in {}", numberOfRetries - numberOfTriesLeft + 1, numberOfRetries, timeToWait);
				}
				else {
					LOG.info("Retrying {} in {}", Math.abs(numberOfTriesLeft + 1), timeToWait);
				}
				Sleeper.sleep(timeToWait);
			}
		}
	}

}
