package de.galan.commons.net.flux;

/**
 * Exception thrown while executing a remote http call.
 */
public class HttpClientException extends Exception {

	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}

}
