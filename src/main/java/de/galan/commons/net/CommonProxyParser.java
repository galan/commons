package de.galan.commons.net;

import static org.apache.commons.lang3.StringUtils.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;

import de.galan.commons.logging.Logr;


/**
 * Parses a given proxy in form [username[:password]@](ip|hostname)[:port]
 *
 * @author daniel
 */
public class CommonProxyParser {

	private static final Logger LOG = Logr.get();


	//private static final Pattern PROXY_PATTERN = Pattern.compile("^([\\w+-]+(:[^@:]+)?@)?[0-9.]+(:[0-9]+([0-9]+)*)?$");

	public CommonProxy parse(String proxy) {
		return parse(proxy, CommonProxy.DEFAULT_PORT);
	}


	public CommonProxy parse(String proxy, Integer defaultPort) {
		/* better approach for ips as well as hostnames*/
		try {
			URI uri = new URI("http", "//" + proxy, null);
			URL url = uri.toURL();
			String host = url.getHost();
			if (isBlank(host)) {
				throw new MalformedURLException("host could not be parsed");
			}
			int portUrl = url.getPort();
			int port = (portUrl < 1) || (portUrl > 65535) ? defaultPort : portUrl;
			String userInfo = url.getUserInfo();
			String username = null;
			String password = null;
			if (isNotBlank(userInfo)) {
				List<String> splits = Splitter.on(":").trimResults().splitToList(userInfo);
				if (splits.size() > 2) {
					throw new MalformedURLException("URL Authentication (UserInfo) invalid");
				}
				if (splits.size() >= 1) {
					username = trimToNull(splits.get(0));
					if (splits.size() == 2) {
						password = trimToNull(splits.get(1));
					}
				}
			}
			return new CommonProxy(host, port, username, password);
		}
		catch (URISyntaxException | MalformedURLException ex) {
			LOG.info("Invalid proxy given: {}", proxy);
		}
		return null;
	}

	/*
		public CommonProxy parse(String proxy, Integer defaultPort) {
			CommonProxy result = null;
			String p = trimToNull(proxy);
			if (isNotBlank(p) && PROXY_PATTERN.matcher(p).matches()) {
				try {
					int indexAuth = StringUtils.indexOf(p, "@");
					String username = null;
					String password = null;
					String host = null;
					if (indexAuth > 0) {
						String auth = StringUtils.substring(p, 0, indexAuth);
						host = StringUtils.substring(p, indexAuth + 1, p.length());
						String[] authSplit = StringUtils.split(auth, ":", 2);
						username = authSplit[0];
						if (authSplit.length == 2) {
							password = authSplit[1];
						}
					}
					else {
						host = p;
					}
					String[] hostSplit = StringUtils.split(host, ":", 2);
					String ip = hostSplit[0];
					Integer port = defaultPort;
					if (hostSplit.length == 2) {
						port = Integer.valueOf(hostSplit[1]);
					}
					result = new CommonProxy(ip, port, username, password);
				}
				catch (Exception ex) {
					LOG.warn("Parsing CommonProxy failed {input}", ex, p);
				}
			}
	
			return result;
		}
	 */

}
