package de.galan.commons.util;

import java.util.Collection;


/**
 * Simple utility class, that checks if an element is in an (vararg) array.
 * 
 * @author daniel
 */
public class Enclosed {

	// Note, overloading not possible due to bug in JVM/Spec:
	// http://stackoverflow.com/questions/2521293/bug-with-varargs-and-overloading

	public static boolean inInt(int element, int... list) {
		for (int item: list) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}


	public static boolean inLong(long element, long... list) {
		for (long item: list) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}


	public static boolean inFloat(float element, float... list) {
		for (float item: list) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}


	public static boolean inDouble(double element, double... list) {
		for (double item: list) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}


	public static boolean inBool(boolean element, boolean... list) {
		for (boolean item: list) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}


	public static boolean inObj(Object element, Object... list) {
		for (Object item: list) {
			if (element.equals(item)) {
				return true;
			}
		}
		return false;
	}


	@SafeVarargs
	public static <T> boolean inCollection(Collection<T> elements, T... list) {
		for (T item: list) {
			if (elements.contains(item)) {
				return true;
			}
		}
		return false;
	}

	/*
	public static <T> T[] $(T... params) {
		return params;
	}


	private static void test() {
		int a = $("a", "b").length;
	}
	*/

}
