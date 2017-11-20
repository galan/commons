package de.galan.commons.util;

/**
 * Exception is thrown, when retries didn't finished successfully.
 */
public class RetryException extends Exception {

	private long numberOfRetries;
	private String timeBetween;
	private String retryMessage;


	public RetryException(String message, Throwable cause, long numberOfRetries, String timeBetween, String retryMessage) {
		super(message, cause);
		this.numberOfRetries = numberOfRetries;
		this.timeBetween = timeBetween;
		this.retryMessage = retryMessage;
	}


	public long getNumberOfRetries() {
		return numberOfRetries;
	}


	public String getTimeBetween() {
		return timeBetween;
	}


	public String getRetryMessage() {
		return retryMessage;
	}

}
