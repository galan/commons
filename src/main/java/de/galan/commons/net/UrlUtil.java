package de.galan.commons.net;

import static java.nio.charset.StandardCharsets.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;


/**
 * Url-related helper functionality.
 */
public class UrlUtil {

	/** Uses URLEncoder to translate a string into application/x-www-form-urlencoded format, using the given Charset. */
	public static String encode(String value, Charset charset) {
		try {
			return URLEncoder.encode(value, charset.name());
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalArgumentException("Characterset is not supported.");
		}
	}


	/** Uses URLEncoder to translate a string into application/x-www-form-urlencoded format, using UTF-8 as Charset. */
	public static String encode(String value) {
		return encode(value, UTF_8);
	}


	/** Uses URLDecoder to decode a application/x-www-form-urlencoded string, using the given Charset. */
	public static String decode(String value, Charset charset) {
		try {
			return URLDecoder.decode(value, charset.name());
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalArgumentException("Characterset is not supported.");
		}
	}


	/** Uses URLDecoder to decode a application/x-www-form-urlencoded string, using UTF-8 as Charset. */
	public static String decode(String value) {
		return decode(value, UTF_8);
	}

}
