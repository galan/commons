package de.galan.commons.util;

import java.util.Optional;
import java.util.function.Predicate;


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


	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

}
