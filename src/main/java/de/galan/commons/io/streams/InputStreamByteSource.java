package de.galan.commons.io.streams;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteSource;


/**
 * Using an InputStream as ByteSource
 */
public class InputStreamByteSource extends ByteSource {

	private InputStream stream;

	public InputStreamByteSource(InputStream stream) {
		this.stream = stream;
	}


	@Override
	public InputStream openStream() throws IOException {
		return stream;
	}

}
