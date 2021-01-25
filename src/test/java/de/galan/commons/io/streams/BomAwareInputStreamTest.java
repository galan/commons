package de.galan.commons.io.streams;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.galan.commons.util.BOM;


/**
 * CUT BomAwareInputStream
 */
public class BomAwareInputStreamTest {

	@ParameterizedTest
	@ValueSource(strings = {"äüöß€s", "abc", "ab", "a", ""})
	public void test(String value) throws IOException {
		BomAwareInputStream isPlain = new BomAwareInputStream(new ByteArrayInputStream(value.getBytes(UTF_8)));
		BomAwareInputStream isBom = new BomAwareInputStream(new ByteArrayInputStream((BOM.getBOM() + value).getBytes(UTF_8)));
		assertThat(IOSupport.inputstreamToString(isPlain, UTF_8)).isEqualTo(value);
		assertThat(IOSupport.inputstreamToString(isBom, UTF_8)).isEqualTo(value);
	}

}
