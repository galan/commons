package de.galan.commons.net.flux;

/**
 * Exception thrown while executing a remote http call.
 * 
 * @author daniel
 */
public class HttpClientException extends Exception {

	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}

}
