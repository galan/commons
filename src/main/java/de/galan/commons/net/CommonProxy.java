package de.galan.commons.net;

import static org.apache.commons.lang3.StringUtils.*;

import org.apache.commons.lang3.StringUtils;


/**
 * Simple wrapper for a proxy in form [username[:password]]@host[:port]
 * 
 * @author daniel
 */
public class CommonProxy {

	static final Integer DEFAULT_PORT = 80;
	static final CommonProxyParser PARSER = new CommonProxyParser();

	private String username;
	private String password;
	private String ip;
	private Integer port;


	/** Convenience-method even thu it creates a cyclic dependency to CommonProxyParser */
	public static CommonProxy parse(String proxy) {
		return PARSER.parse(proxy, DEFAULT_PORT);
	}


	public CommonProxy(String ip) {
		this(ip, null, null, null);
	}


	public CommonProxy(String ip, Integer port) {
		this(ip, port, null, null);
	}


	public CommonProxy(String ip, Integer port, String username, String password) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public boolean hasAuthentication() {
		return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
	}


	public String getIp() {
		return ip;
	}


	public int getPort() {
		return port == null ? DEFAULT_PORT : port;
	}


	@Override
	public String toString() {
		String result = null;
		if (!isBlank(getIp())) {
			StringBuilder builder = new StringBuilder();
			if (StringUtils.isNotBlank(getUsername())) {
				builder.append(getUsername());
				if (StringUtils.isNotBlank(getPassword())) {
					builder.append(":");
					builder.append(getPassword());
				}
				builder.append("@");
			}
			builder.append(getIp());
			builder.append(":");
			builder.append(getPort());

			result = builder.toString();
		}
		return result;
	}

}
