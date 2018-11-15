package de.galan.commons.io.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import de.galan.commons.util.BOM;


/**
 * Wraps an InputStream and removes a potential bom.
 */
public class BomAwareInputStream extends PushbackInputStream {

	byte[] header;


	public BomAwareInputStream(InputStream in) {
		super(in, 3);
	}


	@Override
	public int read() throws IOException {
		if (header == null) {
			checkHeader();
		}
		return super.read();
	}


	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (header == null) {
			checkHeader();
		}
		return super.read(b, off, len);
	}


	private void checkHeader() throws IOException {
		header = new byte[] {-1, -1, -1};
		int nRead = read(header);

		if (nRead > 0 && !BOM.isUTF8(header)) {
			unread(header, 0, nRead);
		}
	}

}
