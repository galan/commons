package de.galan.commons.net.flux;

import de.galan.commons.net.CommonProxy;
import de.galan.commons.time.Durations;


/**
 * Additional behaviour options for the HttpClient
 */
public class HttpOptions {

	private static final long DEFAULT_TIMEOUT = Durations.dehumanize("5s");

	private Long timeoutConnection;
	private Long timeoutRead;
	private String authorizationUsername;
	private String authorizationPassword;
	private CommonProxy proxy;
	private Long retriesCount;
	private Long timeBetweenRetries;
	private Boolean followRedirects = true;
	private Boolean timeoutThread = false;

	public Long getTimeoutConnection() {
		return timeoutConnection == null ? DEFAULT_TIMEOUT : timeoutConnection;
	}


	public void setTimeoutConnection(Long timeoutConnection) {
		this.timeoutConnection = timeoutConnection;
	}


	public Long getTimeoutRead() {
		return timeoutRead == null ? DEFAULT_TIMEOUT : timeoutRead;
	}


	public void setTimeoutRead(Long timeoutRead) {
		this.timeoutRead = timeoutRead;
	}


	public CommonProxy getProxy() {
		return proxy;
	}


	public void enableProxy(CommonProxy commonProxy) {
		proxy = commonProxy;
	}


	public boolean isProxyEnabled() {
		return proxy != null;
	}


	public long getRetriesCount() {
		return retriesCount == null ? 0L : retriesCount;
	}


	public Long getTimeBetweenRetries() {
		return timeBetweenRetries;
	}


	public void enableRetries(Long retries, Long timeBetween) {
		if (retries != null) {
			retriesCount = retries;
			timeBetweenRetries = timeBetween;
		}
	}


	public void enableRetries(Long retries, String timeBetween) {
		enableRetries(retries, Durations.dehumanize(timeBetween));
	}


	public Boolean getFollowRedirects() {
		return followRedirects;
	}


	public void enableFollowRedirects(Boolean follow) {
		followRedirects = follow;
	}


	public Boolean getTimeoutThread() {
		return timeoutThread;
	}


	public void enableTimeoutThread(Boolean thread) {
		timeoutThread = thread;
	}


	public void enableAuthorization(String username, String password) {
		authorizationUsername = username;
		authorizationPassword = password;
	}


	public String getAuthorizationUsername() {
		return authorizationUsername;
	}


	public String getAuthorizationPassword() {
		return authorizationPassword;
	}

}
