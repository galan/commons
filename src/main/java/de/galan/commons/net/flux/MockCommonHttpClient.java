package de.galan.commons.net.flux;

import static java.nio.charset.StandardCharsets.*;
import static java.util.stream.Collectors.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;


/**
 * Test helper for collecting requests and responding to http clients.
 *
 * @author daniel
 */
public class MockCommonHttpClient implements HttpClient {

	private Iterator<MockResponse> responses;
	private List<Request> requests = new ArrayList<Request>();


	public MockCommonHttpClient() {
		// nada
	}


	public MockCommonHttpClient(MockResponse response) {
		setResponse(response);
	}


	public void setResponse(MockResponse response) {
		setResponses(true, response);
	}


	public MockCommonHttpClient response(MockResponse response) {
		setResponse(response);
		return this;
	}


	public void setResponses(boolean repeat, MockResponse... response) {
		responses = repeat ? Iterators.cycle(createRewindableResponses(response)) : Iterators.forArray(response);
	}


	private Iterable<MockResponse> createRewindableResponses(MockResponse... response) {
		return Lists.newArrayList(response).stream().map(MockResponse::convertToReplayableStream).collect(toList());
	}


	public MockCommonHttpClient responses(boolean repeat, MockResponse... response) {
		setResponses(repeat, response);
		return this;
	}


	public List<Request> getRequests() {
		return requests;
	}


	public void reset() {
		requests.clear();
	}


	protected Response getNextResponse() {
		if (responses == null || !responses.hasNext()) {
			return null;
		}
		return responses.next();
	}


	@Override
	public Response request(String resource, Method method, Map<String, String> extraHeader, Map<String, List<String>> parameters, byte[] body, HttpOptions options) throws HttpClientException {
		requests.add(new Request(method, extraHeader, body, resource));
		// add request to list
		return getNextResponse();
	}

	/** Collected request. */
	public static class Request {

		public Method method;
		public Map<String, String> extraHeader;
		public byte[] body;
		public String resource;


		public Request(Method method, Map<String, String> extraHeader, byte[] body, String resource) {
			this.method = method;
			this.extraHeader = extraHeader;
			this.body = body;
			this.resource = resource;
		}


		public String getBody() {
			return new String(body, UTF_8);
		}

	}

	/** Mocks the body and metadata of a http response. */
	public static class MockResponse extends Response {

		// We store the stream independently, in order to be able to override the getStream() method and create a replayable stream
		// that will be used when the responses(true,...) method is used to create infinite result stream, to be able to reset the stream.
		private InputStream mockStream;


		public MockResponse(String body, int statusCode) {
			this(body, statusCode, "text/html;charset=UTF-8");
		}


		public MockResponse(byte[] body, int statusCode, String contentType) {
			this(null, new ByteArrayInputStream(body), statusCode, null, contentType, null);
		}


		public MockResponse(String body, int statusCode, String contentType) {
			this(null, new ByteArrayInputStream(body.getBytes(UTF_8)), statusCode, UTF_8.toString(), contentType, null);
		}


		public MockResponse(HttpURLConnection connection, InputStream dataStream, int statusCode, String contentEncoding, String contentType, Map<String, String> headerFields) {
			super(connection, null, statusCode, contentEncoding, contentType, headerFields);
			mockStream = dataStream;
		}


		@Override
		public InputStream getStream() {
			return mockStream;
		}


		MockResponse convertToReplayableStream() {
			mockStream = new ReplayableInputStream(getStream());
			return this;
		}

	}

	/**
	 * Repeats it's stream after it has been closed. Since it caches the whole InputStream, it should be used only for
	 * testing with small or medium-size data streams.
	 */
	static class ReplayableInputStream extends InputStream {

		private InputStream current;
		private ByteArrayOutputStream output;


		public ReplayableInputStream(InputStream input) {
			try {
				output = new ByteArrayOutputStream();
				output.write(input);
				reset();
			}
			catch (IOException ex) {
				throw new RuntimeException("Unable to read inputstream", ex);
			}
		}


		@Override
		public void close() throws IOException {
			reset();
		}


		@Override
		public synchronized void reset() throws IOException {
			current = new ByteArrayInputStream(output.toByteArray());
		}


		@Override
		public int read() throws IOException {
			return current.read();
		}

	}

}
