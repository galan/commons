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


	/** Returns the given value, or the fallback if the value is null. */
	public static <T> T fallback(T val, T fallback) {
		return val != null ? val : fallback;
	}


	/** Returns the first non-null value. */
	public static <T> T first(T... values) {
		if (values == null) {
			return null;
		}
		for (T x : values) {
			if (x != null) {
				return x;
			}
		}
		return null;
	}


	/**
	 * Negates the given Predicate
	 *
	 * @deprecated Use Predicate.not(..) instead (since Java 11)
	 */
	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}


	/**
	 * Casts an object by type inference. see also
	 * http://weblogs.java.net/blog/emcmanus/archive/2007/03/getting_rid_of.html See {@link Generics}
	 *
	 * @param x Object that will be casted
	 */
	public static <T> T cast(Object x) {
		return Generics.cast(x);
	}

}
