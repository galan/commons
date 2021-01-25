package de.galan.commons.net.flux;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSink;
import com.google.common.io.Files;

import de.galan.commons.io.streams.IOSupport;
import de.galan.commons.logging.Logr;


/**
 * Represents the reponse to a http service.
 */
public class Response implements AutoCloseable {

	private static final Logger LOG = Logr.get();

	private final static Map<String, String> EMPTY_HEADERS = ImmutableMap.of();

	private final InputStream dataStream;
	private final int statusCode;
	private final String contentEncoding;
	private final String contentType;
	private final HttpURLConnection connection;
	private final Map<String, String> headerFields;

	public Response(HttpURLConnection connection, InputStream dataStream, int statusCode, String contentEncoding, String contentType, Map<String, String> headerFields) {
		this.connection = connection;
		this.dataStream = dataStream;
		this.statusCode = statusCode;
		this.contentEncoding = contentEncoding;
		this.contentType = contentType;
		this.headerFields = (headerFields != null) ? headerFields : EMPTY_HEADERS;
	}


	public InputStream getStream() {
		return dataStream;
	}


	/** Converts the inputstream to a string in UTF-8. Subsequent the inputstream will be empty/closed. */
	public byte[] getStreamAsBytearray() throws IOException {
		byte[] result = null;
		try {
			result = getStream().readAllBytes();
		}
		catch (NullPointerException npex) {
			throw new IOException("Timeout has been forced by CommonHttpClient", npex);
		}
		return result;
	}


	/** Converts the inputstream to a string in UTF-8. Subsequent the inputstream will be empty/closed. */
	public String getStreamAsString() throws IOException {
		return getStreamAsString("UTF-8");
	}


	/**
	 * Converts the inputstream to a string with the given encoding. Subsequent the inputstream will be empty/closed.
	 */
	public String getStreamAsString(String encoding) throws IOException {
		return getStreamAsString(Charset.forName(encoding));
	}


	/**
	 * Converts the inputstream to a string with the given encoding. Subsequent the inputstream will be empty/closed.
	 */
	public String getStreamAsString(Charset charset) throws IOException {
		String result = null;
		try {
			result = IOSupport.inputstreamToString(getStream(), charset);
		}
		catch (NullPointerException npex) {
			throw new IOException("Timeout has been forced by CommonHttpClient", npex);
		}
		return result;

	}


	public void storeStream(File file) throws FileNotFoundException, IOException {
		storeStream(file, false);
	}


	public void storeStream(File file, boolean gunzip) throws FileNotFoundException, IOException {
		InputStream is = getStream();
		if (gunzip) {
			is = new GZIPInputStream(is);
		}
		try (InputStream stream = is) {
			ByteSink sink = Files.asByteSink(file);
			sink.writeFrom(is);
		}
	}


	public int getStatusCode() {
		return statusCode;
	}


	public String getContentEncoding() {
		return contentEncoding;
	}


	public String getContentType() {
		return contentType;
	}


	/** Returns true if the http status code is not between 200 and 299 */
	public boolean isFailed() {
		return !isSucceded();
	}


	/** Returns true if the http status code is not between 100 and 199 */
	public boolean isInformational() {
		return getStatusCode() >= 100 && getStatusCode() <= 199;
	}


	/** Returns true if the http status code is between 200 and 299 */
	public boolean isSucceded() {
		return getStatusCode() >= 200 && getStatusCode() <= 299;
	}


	/** Returns true if the http status code is between 300 and 399 */
	public boolean isRedirection() {
		return getStatusCode() >= 300 && getStatusCode() <= 399;
	}


	/** Returns true if the http status code is between 400 and 499 */
	public boolean isClientError() {
		return getStatusCode() >= 400 && getStatusCode() <= 499;
	}


	/** Returns true if the http status code is between 500 and 599 */
	public boolean isServerError() {
		return getStatusCode() >= 500 && getStatusCode() <= 599;
	}


	public String getHeaderField(String name) {
		return isBlank(name) ? null : getHeaderFields().get(name);
	}


	public Map<String, String> getHeaderFields() {
		return headerFields;
	}


	@Override
	public void close() {
		if (getStream() != null) {
			try {
				getStream().close();
			}
			catch (IOException ex) {
				LOG.info("stream could not be closed");
			}
		}
		if (connection != null) {
			connection.disconnect();
		}
	}

}
