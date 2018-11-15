package de.galan.commons.io.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;


/**
 * Wraps an InputStream and checks if the stream has been compressed
 */
public class GzipAwareInputStream extends InputStream {

	boolean compressed = false;
	private PushbackInputStream pushback;
	private InputStream selected;
	private boolean decompress;


	public GzipAwareInputStream(InputStream in) {
		this(in, true);
	}


	public GzipAwareInputStream(InputStream in, boolean decompress) {
		pushback = new PushbackInputStream(in, 2);
		this.decompress = decompress;
	}


	@Override
	public int read() throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return selected.read();
	}


	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return selected.read(b, off, len);
	}


	private void checkHeader() throws IOException {
		byte[] header = new byte[] {-1, -1};
		int read = pushback.read(header, 0, 2);
		compressed = (header[0] == (byte)GZIPInputStream.GZIP_MAGIC) && (header[1] == (byte)(GZIPInputStream.GZIP_MAGIC >> 8));

		if (read > 0) {
			pushback.unread(header, 0, read);
		}
		selected = (compressed && decompress) ? new GZIPInputStream(pushback) : pushback;
	}


	public boolean isCompressed() throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return compressed;
	}


	@Override
	public int read(byte[] b) throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return selected.read(b);
	}


	@Override
	public long skip(long n) throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return selected.skip(n);
	}


	@Override
	public int available() throws IOException {
		if (selected == null) {
			checkHeader();
		}
		return selected.available();
	}


	@Override
	public void close() throws IOException {
		selected.close();
	}

}
