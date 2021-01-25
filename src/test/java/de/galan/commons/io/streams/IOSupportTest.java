package de.galan.commons.io.streams;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;


/**
 * CUT IOSupport
 */
public class IOSupportTest {

	@Test
	void inputstreamToString() throws Exception {
		String input = "Hello, World! €ß";
		ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(UTF_8));
		assertThat(IOSupport.inputstreamToString(bais)).isEqualTo(input);
	}


	@Test
	void inputstreamToStringEncoding() throws Exception {
		String input = "Hello, World! €ß";
		ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes(UTF_8));
		assertThat(IOSupport.inputstreamToString(bais, UTF_8)).isEqualTo(input);
	}

}
