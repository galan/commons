package de.galan.commons.net.flux;

import static org.apache.commons.lang3.StringUtils.*;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.ObjectUtils;

import de.galan.commons.net.CommonProxy;
import de.galan.commons.time.Durations;


/**
 * Creating fluent http requests (simplified fluent interface for HttpClient). Can be used directly when using eg. DI
 * such as Guice, or by using the static Factory class called Flux.
 *
 * @author daniel
 */
public class FluentHttpClient {

	private HttpClient client;


	public FluentHttpClient() {
		// nada
	}


	public FluentHttpClient(HttpClient client) {
		setClient(client);
	}


	public void setClient(HttpClient client) {
		this.client = client;
	}


	HttpClient getClient(boolean create) {
		if (client == null && create) {
			client = new CommonHttpClient();
		}
		return client;
	}


	public HttpBuilder request(String protocol, String host, Integer port, String path) {
		String resource = UrlConstruction.constructResource(protocol, host, port, path);
		return request(resource);
	}


	public HttpBuilder request(String resource) {
		return new HttpBuilder(getClient(true), resource);
	}


	public HttpBuilder request(URL resource) {
		return new HttpBuilder(getClient(true), resource.toString());
	}

	/** Builder */
	public static class HttpBuilder {

		String builderResource;
		byte[] builderBody;
		Long builderTimeout;
		Long builderTimeoutConnection;
		Long builderTimeoutRead;
		CommonProxy builderProxy;
		String builderUsername;
		String builderPassword;
		boolean builderFollowRedirects = true;
		Long builderRetries;
		String builderRetriesTimebetween;
		boolean builderTimeoutThread = false;
		Map<String, String> builderHeader;
		Map<String, List<String>> builderParameter;
		HttpClient builderClient;


		protected HttpBuilder(HttpClient client, String resource) {
			builderClient = client;
			builderResource = resource;
		}


		public HttpBuilder proxy(String proxy) {
			if (isNotBlank(proxy)) {
				builderProxy = CommonProxy.parse(proxy);
			}
			return this;
		}


		public HttpBuilder proxy(CommonProxy proxy) {
			if (proxy != null) {
				builderProxy = proxy;
			}
			return this;
		}


		public HttpBuilder proxy(String ip, int port) {
			if (isNotBlank(ip)) {
				builderProxy = new CommonProxy(ip, port);
			}
			return this;
		}


		/** Has to be called after setting a proxy via ip/port */
		public HttpBuilder proxyAuthentication(String username, String password) {
			builderProxy = new CommonProxy(builderProxy.getIp(), builderProxy.getPort(), username, password);
			return this;
		}


		public HttpBuilder authentication(String username, String password) {
			builderUsername = username;
			builderPassword = password;
			return this;
		}


		public HttpBuilder timeout(String timeout) {
			return timeout(Durations.dehumanize(timeout));
		}


		public HttpBuilder timeout(Integer timeout) {
			return timeout((timeout == null) ? null : timeout.longValue());
		}


		public HttpBuilder timeout(Long timeout) {
			builderTimeout = timeout;
			return this;
		}


		public HttpBuilder timeoutConnection(String timeoutConnection) {
			return timeoutConnection(Durations.dehumanize(timeoutConnection));
		}


		public HttpBuilder timeoutConnection(Integer timeoutConnection) {
			return timeoutConnection((timeoutConnection == null) ? null : timeoutConnection.longValue());
		}


		public HttpBuilder timeoutConnection(Long timeoutConnection) {
			builderTimeoutConnection = timeoutConnection;
			return this;
		}


		public HttpBuilder timeoutRead(String timeoutRead) {
			return timeoutRead(Durations.dehumanize(timeoutRead));
		}


		public HttpBuilder timeoutRead(Integer timeoutRead) {
			return timeoutRead((timeoutRead == null) ? null : timeoutRead.longValue());
		}


		public HttpBuilder timeoutRead(Long timeoutRead) {
			builderTimeoutRead = timeoutRead;
			return this;
		}


		public HttpBuilder followRedirects() {
			builderFollowRedirects = true;
			return this;
		}


		public HttpBuilder unfollowRedirects() {
			builderFollowRedirects = false;
			return this;
		}


		public HttpBuilder retries(Long retries, String timeBetween) {
			builderRetries = retries;
			builderRetriesTimebetween = timeBetween;
			return this;
		}


		public HttpBuilder timeoutThread() {
			builderTimeoutThread = true;
			return this;
		}


		public HttpBuilder body(String body) {
			return body(body, Charsets.UTF_8);
		}


		public HttpBuilder body(String body, Charset charset) {
			builderBody = (body == null) ? null : body.getBytes(charset);
			return this;
		}


		public HttpBuilder body(byte[] body) {
			builderBody = body;
			return this;
		}


		public HttpBuilder header(String key, String value) {
			if (builderHeader == null) {
				builderHeader = new HashMap<String, String>();
			}
			builderHeader.put(key, value);
			return this;
		}


		public HttpBuilder headers(Map<String, String> headers) {
			if (builderHeader == null) {
				builderHeader = new HashMap<String, String>();
			}
			builderHeader.putAll(headers);
			return this;
		}


		public HttpBuilder parameter(String key, String... values) {
			List<String> list = getParameterList(key);
			for (String value: values) {
				list.add(value);
			}
			return this;
		}


		/* Short notation for parameter(..) */
		public HttpBuilder param(String key, String... values) {
			return parameter(key, values);
		}


		public HttpBuilder parameterMap(Map<String, String> parameters) {
			if (parameters != null) {
				for (Entry<String, String> entry: parameters.entrySet()) {
					getParameterList(entry.getKey()).add(entry.getValue());
				}
			}
			return this;
		}


		public HttpBuilder parameterList(Map<String, List<String>> parameters) {
			if (parameters != null) {
				for (Entry<String, List<String>> entry: parameters.entrySet()) {
					List<String> list = getParameterList(entry.getKey());
					for (String value: entry.getValue()) {
						list.add(value);
					}
				}
			}
			return this;
		}


		private List<String> getParameterList(String key) {
			if (builderParameter == null) {
				builderParameter = new HashMap<>();
			}
			List<String> values = builderParameter.get(key);
			if (values == null) {
				values = new ArrayList<>();
				builderParameter.put(key, values);
			}
			return values;
		}


		public Response get() throws HttpClientException {
			return method(Method.GET);
		}


		public Future<Response> getAsync() {
			return callAsync(this::get);
		}


		public Response put() throws HttpClientException {
			return method(Method.PUT);
		}


		public Future<Response> putAsync() {
			return callAsync(this::put);
		}


		public Response post() throws HttpClientException {
			return method(Method.POST);
		}


		public Future<Response> postAsync() {
			return callAsync(this::post);
		}


		public Response delete() throws HttpClientException {
			return method(Method.DELETE);
		}


		public Future<Response> deleteAsync() {
			return callAsync(this::delete);
		}


		public Response head() throws HttpClientException {
			return method(Method.HEAD);
		}


		public Future<Response> headAsync() {
			return callAsync(this::head);
		}


		public Response trace() throws HttpClientException {
			return method(Method.TRACE);
		}


		public Future<Response> traceAsync() {
			return callAsync(this::trace);
		}


		public Response options() throws HttpClientException {
			return method(Method.OPTIONS);
		}


		public Future<Response> optionsAsync() {
			return callAsync(this::options);
		}


		public Future<Response> methodAsync(Method method) {
			return callAsync(() -> method(method));
		}


		public Response method(Method method) throws HttpClientException {
			HttpClient client = (builderClient == null) ? new CommonHttpClient() : builderClient;
			HttpOptions options = new HttpOptions();
			options.setTimeoutConnection(ObjectUtils.defaultIfNull(builderTimeoutConnection, builderTimeout));
			options.setTimeoutRead(ObjectUtils.defaultIfNull(builderTimeoutRead, builderTimeout));
			options.enableAuthorization(builderUsername, builderPassword);
			options.enableProxy(builderProxy);
			options.enableRetries(builderRetries, builderRetriesTimebetween);
			options.enableFollowRedirects(builderFollowRedirects);
			options.enableTimeoutThread(builderTimeoutThread);
			return client.request(builderResource, method, builderHeader, builderParameter, builderBody, options);
		}


		protected Future<Response> callAsync(Callable<Response> callable) {
			FutureTask<Response> task = new FutureTask<>(callable);
			new Thread(task).start();
			return task;
		}


		/** Return the URL the builder would generate as String, does not include header, etc. */
		public String toUrlString() {
			return UrlConstruction.appendParameters(builderResource, builderParameter);
		}

	}

}
