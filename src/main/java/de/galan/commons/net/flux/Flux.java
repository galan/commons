package de.galan.commons.net.flux;

import static org.apache.commons.lang3.StringUtils.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import de.galan.commons.net.flux.FluentHttpClient.HttpBuilder;
import de.galan.commons.time.Durations;


/**
 * Static accessor to FluentHttpClient with option to set default configuration.
 *
 * @author daniel
 */
public class Flux {

	private static Long defaultTimeout;
	private static Long defaultTimeoutConnection;
	private static Long defaultTimeoutRead;
	private static Map<String, String> defaultHeader;
	private static Supplier<HttpClient> clientFactory = createDefaultHttpClientSupplier();


	/**
	 * If no timeout is passed via the builder, a default timeout can be set for read and connection timeouts with this
	 * method for further requests.
	 *
	 * @param timeout Timeout in milliseconds
	 */
	public static void setDefaultTimeout(long timeout) {
		defaultTimeout = timeout;
	}


	/**
	 * If no timeout is passed via the builder, a default timeout can be set for read and connection timeouts with this
	 * method for further requests.
	 *
	 * @param timeout Timeout in human time.
	 */
	public static void setDefaultTimeout(String timeout) {
		defaultTimeout = Durations.dehumanize(timeout);
	}


	/**
	 * If no connection timeout is passed via the builder, a default connection timeout can be set with this method for
	 * further requests.
	 *
	 * @param timeoutConnection Connection timeout in milliseconds
	 */
	public static void setDefaultTimeoutConnection(long timeoutConnection) {
		defaultTimeoutConnection = timeoutConnection;
	}


	/**
	 * If no connection timeout is passed via the builder, a default connection timeout can be set with this method for
	 * further requests.
	 *
	 * @param timeoutConnection Connection timeout in human time.
	 */
	public static void setDefaultTimeoutConnection(String timeoutConnection) {
		defaultTimeoutConnection = Durations.dehumanize(timeoutConnection);
	}


	/**
	 * If no read timeout is passed via the builder, a default read timeout can be set with this method for further
	 * requests.
	 *
	 * @param timeoutRead Read timeout in milliseconds
	 */
	public static void setDefaultTimeoutRead(long timeoutRead) {
		defaultTimeoutRead = timeoutRead;
	}


	/**
	 * If no read timeout is passed via the builder, a default read timeout can be set with this method for further
	 * requests.
	 *
	 * @param timeoutRead Read timeout in human time.
	 */
	public static void setDefaultTimeoutRead(String timeoutRead) {
		defaultTimeoutRead = Durations.dehumanize(timeoutRead);
	}


	/** Clears all default timeouts */
	public static void clearTimeouts() {
		defaultTimeout = null;
		defaultTimeoutConnection = null;
		defaultTimeoutRead = null;
	}


	/** Added default headers will always be send, can still be overriden using the builder. */
	public static void addDefaultHeader(String key, String value) {
		if (isNotBlank(key) && isNotBlank(value)) {
			if (defaultHeader == null) {
				defaultHeader = new HashMap<String, String>();
			}
			defaultHeader.put(key, value);
		}
	}


	/** Removes all default http-headers */
	public static void clearDefaultHeader() {
		defaultHeader = null;
	}


	/**
	 * Specifies the resource to be requested.
	 *
	 * @param resource The URL to request, including protocol.
	 * @return The HttpBuilder
	 */
	public static HttpBuilder request(String resource) {
		return defaults(createClient().request(resource));
	}


	/**
	 * Specifies the resource to be requested.
	 *
	 * @param resource The URL to request.
	 * @return The HttpBuilder
	 */
	public static HttpBuilder request(URL resource) {
		return defaults(createClient().request(resource));
	}


	/**
	 * Specifies the resource to be requested.
	 *
	 * @param protocol Protocol of the resource
	 * @param host Host of the resource
	 * @param port Port the host is listening on
	 * @param path Path for the resource
	 * @return The HttpBuilder
	 */
	public static HttpBuilder request(String protocol, String host, Integer port, String path) {
		return defaults(createClient().request(protocol, host, port, path));
	}


	protected static FluentHttpClient createClient() {
		return new FluentHttpClient(Flux.clientFactory.get());
	}


	/** Setting the static defaults before returning the builder */
	private static HttpBuilder defaults(HttpBuilder builder) {
		if (defaultTimeout != null) {
			builder.timeout(defaultTimeout);
		}
		if (defaultTimeoutConnection != null) {
			builder.timeoutConnection(defaultTimeoutConnection);
		}
		if (defaultTimeoutRead != null) {
			builder.timeoutRead(defaultTimeoutRead);
		}
		if (defaultHeader != null) {
			builder.headers(defaultHeader);
		}
		return builder;
	}


	public static void setHttpClientFactory(Supplier<HttpClient> clientFactory) {
		Flux.clientFactory = clientFactory;
	}


	public static void resetHttpClientFactory() {
		setHttpClientFactory(createDefaultHttpClientSupplier());
	}


	protected static Supplier<HttpClient> createDefaultHttpClientSupplier() {
		return CommonHttpClient::new;
	}

}
