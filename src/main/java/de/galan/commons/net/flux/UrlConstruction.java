package de.galan.commons.net.flux;

import static com.google.common.base.Charsets.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.galan.commons.net.UrlUtil;


/**
 * URL constructing helper methods.
 *
 * @author daniel
 */
public class UrlConstruction {

	public static String appendParameters(String resource, Map<String, List<String>> parameters) {
		StringBuilder builder = new StringBuilder(resource);
		if (parameters != null && !parameters.isEmpty()) {
			boolean first = true;
			for (Entry<String, List<String>> entry: parameters.entrySet()) {
				for (String value: entry.getValue()) {
					if (entry.getValue() != null) {
						if (first) {
							builder.append(contains(resource, "?") ? "&" : "?");
							first = false;
						}
						else {
							builder.append("&");
						}
						builder.append(entry.getKey());
						if (value != null) {
							builder.append("=");
							builder.append(UrlUtil.encode(value.toString(), UTF_8));
						}
					}
				}
			}
		}
		return builder.toString();
	}


	public static String constructResource(String protocol, String host, Integer port, String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(defaultIfBlank(protocol, "http"));
		builder.append("://");
		builder.append(host);
		if ((port != null) && (port > 0)) {
			builder.append(":");
			builder.append(port);
		}
		builder.append(startsWith(path, "/") ? path : "/" + path);
		return builder.toString();
	}

}
