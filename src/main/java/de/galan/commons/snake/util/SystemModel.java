package de.galan.commons.snake.util;

import com.google.common.primitives.Ints;


/**
 * Convenience access to SystemProperties
 *
 * @author daniel
 */
public class SystemModel {

	/** The platform dependent line separator */
	private final static String LINE_SEPERATOR = System.getProperty("line.separator");
	/** The platform dependent directory separator */
	private final static String FILE_SEPERATOR = System.getProperty("file.separator");
	/** The name of the current user */
	private final static String USER_NAME = System.getProperty("user.name");
	/** The home directory of the current user */
	private final static String USER_HOME = System.getProperty("user.home");
	/** HTTP proxy host */
	private final static String HTTP_PROXY_HOST = "http.proxyHost";
	/** HTTP proxy port */
	private final static String HTTP_PROXY_PORT = "http.proxyPort";
	/** HTTP Non proxy host */
	private final static String HTTP_NON_PROXY_HOST = "http.nonProxyHosts";


	/**
	 * Returns the currently defined ProxyHost
	 *
	 * @return The value from the SystemProperty "http.proxyHost"
	 */
	public String getProxyHost() {
		return get(HTTP_PROXY_HOST);
	}


	/**
	 * Sets the ProxyHost
	 *
	 * @param host The new host, which is used by the JVM as proxy
	 */
	public void setProxyHost(String host) {
		set(HTTP_PROXY_HOST, host);
	}


	/**
	 * Returns the currently defined ProxyPort
	 *
	 * @return The value from the SystemProperty "http.proxyPort"
	 */
	public Integer getProxyPort() {
		return Ints.tryParse(get(HTTP_PROXY_PORT));
	}


	/**
	 * Sets the ProxyPort
	 *
	 * @param host The new port, which is used by the JVM for the SystemProperty "http.proxyPort"
	 */
	public void setProxyPort(Integer port) {
		set(HTTP_PROXY_PORT, port != null ? port.toString() : null);
	}


	/**
	 * Returns the hosts, that do not use the defined proxy
	 *
	 * @return The value from the SystemProperty "http.nonProxyHosts"
	 */
	public String getNonProxyHosts() {
		return get(HTTP_NON_PROXY_HOST);
	}


	/**
	 * Sets the hosts, which should not use the proxy
	 *
	 * @param nonHosts The value for the SystemProperty "http.nonProxyHosts"
	 */
	public void setNonProxyHosts(String nonHosts) {
		set(HTTP_NON_PROXY_HOST, nonHosts);
	}


	/**
	 * Returns the platform-dependent directory seperator
	 *
	 * @return The value from the SystemProperty "file.seperator"
	 */
	public String getFileSeparator() {
		return FILE_SEPERATOR;
	}


	/**
	 * Returns the platform-dependent linebreak
	 *
	 * @return The value from the SystemProperty "line.seperator"
	 */
	public String getLineSeparator() {
		return LINE_SEPERATOR;
	}


	/**
	 * Returns the name of the user, that runs the process
	 *
	 * @return The value from the SystemProperty "user.name"
	 */
	public String getUserName() {
		return USER_NAME;
	}


	/**
	 * Returns the home directory of the user, that runs the process
	 *
	 * @return The value from the SystemProperty "user.home"
	 */
	public String getUserHome() {
		return USER_HOME;
	}


	/** Gets the system property for the specified key */
	public String get(String key) {
		return System.getProperty(key);
	}


	/** Sets the system property for the specified key */
	public void set(String key, String value) {
		System.setProperty(key, value);
	}

}
