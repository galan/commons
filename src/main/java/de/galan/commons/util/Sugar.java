package de.galan.commons.util;

import java.util.Optional;


/**
 * Simple syntax sugar/shortener
 */
public class Sugar {

	/** Shortens the Optional.ofNullable(..) syntax. */
	public static <T> Optional<T> optional(T value) {
		return Optional.ofNullable(value);
	}


	public <T> T fallback(T val, T fallback) {
		return val != null ? val : fallback;
	}

}
