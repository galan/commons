package de.galan.commons.util;

import java.lang.reflect.Array;
import java.util.List;


/**
 * Helpermethods, that simplify working with generics.
 *
 * @author daniel
 */
public class GenericUtil {

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


	/**
	 * Converts the given <code>List&lt;T&gt;</code> to a an array of <code>T[]</code>.
	 *
	 * @param clz the Class object of the items in the list
	 * @param list the list to convert
	 */
	public static <T> T[] toArray(Class<T> clz, List<T> list) {
		@SuppressWarnings("unchecked")
		T[] result = (T[])Array.newInstance(clz, list.size());

		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

}
