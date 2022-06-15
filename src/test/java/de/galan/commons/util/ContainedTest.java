package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class ContainedTest {

	@Test
	public void inInt() {
		assertThat(Contained.inInt(666, 1, 2, 4, 667, 1024)).isFalse();
		assertThat(Contained.inInt(666, 1, 2, 4, 666, 1024)).isTrue();
	}


	@Test
	public void inLong() {
		assertThat(Contained.inLong(666L, 1L, 2L, 4L, 667L, 1024L)).isFalse();
		assertThat(Contained.inLong(666L, 1L, 2L, 4L, 666L, 1024L)).isTrue();
	}


	@Test
	public void inFloat() {
		assertThat(Contained.inFloat(666f, 1f, 4f, 667f, 1024f)).isFalse();
		assertThat(Contained.inFloat(666f, 1f, 4f, 666f, 1024f)).isTrue();
	}


	@Test
	public void inDouble() {
		assertThat(Contained.inDouble(666d, 1d, 4d, 667d, 1024d)).isFalse();
		assertThat(Contained.inDouble(666d, 1d, 4d, 666d, 1024d)).isTrue();
	}


	@Test
	public void inBool() {
		assertThat(Contained.inBool(false, true, true, true)).isFalse();
		assertThat(Contained.inBool(false, true, false, true)).isTrue();
		assertThat(Contained.inBool(true, false, false, false)).isFalse();
		assertThat(Contained.inBool(true, false, true, false)).isTrue();
	}


	@Test
	public void inObj() {
		assertThat(Contained.inObj("hello", "a", "b", "c")).isFalse();
		assertThat(Contained.inObj("hello", "a", "hello", "c")).isTrue();
	}

}
