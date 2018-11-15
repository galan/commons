package de.galan.commons.io.streams;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * CUT GzipAwareInputStream
 */
public class GzipAwareInputStreamTest {

	public static Stream<TestItem> input() throws IOException {
		byte[] helloGz = IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("hello.gz"));
		byte[] hGz = IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("h.gz"));
		byte[] aum = IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("aum.png"));
		return Stream.of(
			new TestItem("hello".getBytes(), "hello".getBytes(), false, true),
			new TestItem("h".getBytes(), "h".getBytes(), false, true),
			new TestItem("".getBytes(), "".getBytes(), false, true),
			new TestItem(new byte[0], new byte[0], false, true),
			new TestItem(aum, aum, false, true),
			new TestItem(helloGz, "hello\n".getBytes(), true, true),
			new TestItem(hGz, "h".getBytes(), true, true),
			new TestItem(helloGz, helloGz, true, false));
	}


	@ParameterizedTest
	@MethodSource("input")
	public void checkCompressed(TestItem item) throws Exception {
		GzipAwareInputStream gais = new GzipAwareInputStream(new ByteArrayInputStream(item.input), item.decompress);
		assertThat(gais.isCompressed()).isEqualTo(item.compressed);
		assertThat(IOUtils.toByteArray(gais)).isEqualTo(item.expected);
		gais.close();
	}

}


class TestItem {

	public TestItem(byte[] input, byte[] expected, boolean compressed, boolean decompress) {
		this.input = input;
		this.expected = expected;
		this.compressed = compressed;
		this.decompress = decompress;
	}

	byte[] input;
	byte[] expected;
	boolean compressed;
	boolean decompress;

}
