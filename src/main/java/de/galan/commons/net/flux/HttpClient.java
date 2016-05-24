package de.galan.commons.net.flux;

import java.util.List;
import java.util.Map;


/**
 * Contract for a HTTP request
 *
 * @author daniel
 */
public interface HttpClient {

	public Response request(String resource, Method method, Map<String, String> extraHeader, Map<String, List<String>> parameters, byte[] body, HttpOptions options) throws HttpClientException;

}
