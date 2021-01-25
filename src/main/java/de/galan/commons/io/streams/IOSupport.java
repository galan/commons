package de.galan.commons.io.streams;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.common.io.ByteSource;


/**
 * Supportive I/O methods.
 */
public class IOSupport {

	public static String inputstreamToString(InputStream is) throws IOException {
		return inputstreamToString(is, StandardCharsets.UTF_8);
	}


	public static String inputstreamToString(InputStream is, Charset charset) throws IOException {
		ByteSource byteSource = new ByteSource() {

			@Override
			public InputStream openStream() throws IOException {
				return is;
			}
		};
		return byteSource.asCharSource(charset).read();
	}

}
