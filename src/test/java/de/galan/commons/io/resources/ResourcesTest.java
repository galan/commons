package de.galan.commons.io.resources;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;


public class ResourcesTest {

	@Test
	public void read() throws IOException {
		assertThat(Resources.read(getClass(), "read-test.txt")).isEqualTo("abc\n123");
	}


	@Test
	public void readLines() throws IOException {
		assertThat(Resources.readLines(getClass(), "readLines-test.txt")).containsExactly("abc", "123");
	}

}
