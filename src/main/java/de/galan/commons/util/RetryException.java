package de.galan.commons.util;

/**
 * Exception is thrown, when retries didn't finished successfully.
 * 
 * @author daniel
 */
public class RetryException extends Exception {

	private long numberOfRetries;
	private String timeBetween;


	public RetryException(String message, Throwable cause, long numberOfRetries, String timeBetween) {
		super(message, cause);
		this.numberOfRetries = numberOfRetries;
		this.timeBetween = timeBetween;
	}


	public long getNumberOfRetries() {
		return numberOfRetries;
	}


	public String getTimeBetween() {
		return timeBetween;
	}

}
