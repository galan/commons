package de.galan.commons.io.streams;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.galan.commons.util.Pair;


/**
 * CUT GzipAwareInputStream
 */
public class GzipAwareInputStreamTest {

	public static Stream<Pair<byte[], byte[]>> input() throws IOException {
		return Stream.of(
			new Pair<>("hello".getBytes(), "hello".getBytes()),
			new Pair<>("h".getBytes(), "h".getBytes()),
			new Pair<>("".getBytes(), "".getBytes()),
			new Pair<>(new byte[0], new byte[0]),
			new Pair<>(IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("aum.png")),
					IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("aum.png"))),
			new Pair<>(IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("hello.gz")),
					"hello\n".getBytes()),
			new Pair<>(IOUtils.toByteArray(GzipAwareInputStreamTest.class.getResourceAsStream("h.gz")),
					"h".getBytes()));
	}


	@ParameterizedTest
	@MethodSource("input")
	public void checkCompressed(Pair<byte[], byte[]> pair) throws Exception {
		GzipAwareInputStream gais = new GzipAwareInputStream(new ByteArrayInputStream(pair.getKey()));
		assertThat(IOUtils.toByteArray(gais)).isEqualTo(pair.getValue());
		gais.close();
	}

}
