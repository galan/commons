package de.galan.commons.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;


/**
 * Url-related helper functionality.
 *
 * @author galan
 */
public class UrlUtil {

	public static String encode(String value, Charset charset) {
		try {
			return URLEncoder.encode(value, charset.name());
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalArgumentException("Characterset is not supported.");
		}
	}


	public static String decode(String value, Charset charset) {
		try {
			return URLDecoder.decode(value, charset.name());
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalArgumentException("Characterset is not supported.");
		}
	}

}
