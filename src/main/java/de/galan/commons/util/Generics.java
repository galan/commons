package de.galan.commons.util;

/**
 * Helpermethods, that simplify working with generics.
 */
public class Generics {

	/**
	 * Casts an object by type inference. see also
	 * http://weblogs.java.net/blog/emcmanus/archive/2007/03/getting_rid_of.html
	 *
	 * @param x Object that will be casted
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object x) {
		return (T)x;
	}


	/**
	 * Returns the generic Class object for a given argument.
	 *
	 * @param <T> Type the argument returns
	 * @param t object to check
	 */
	public static <T> Class<T> getClass(T t) {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>)t.getClass();
		return clazz;
	}

}
