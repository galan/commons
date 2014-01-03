package de.galan.commons.test;

import static de.galan.commons.time.DateDsl.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import de.galan.commons.time.HumanTime;


/**
 * Common helper methods that are helpful for the AbstractTestParent and Test-Classes via static imports.
 * 
 * @author daniel
 */
public class TestHelper {

	public static byte[] readFileBinary(Class<?> clazz, String filename) throws IOException {
		return Resources.toByteArray(Resources.getResource(clazz, filename));
	}


	public static String readFile(String filename) throws IOException {
		File file = new File(filename);
		return readFile(new FileInputStream(file));
	}


	public static String readFile(Class<?> clazz, String filename) throws IOException {
		return readFile(clazz.getResourceAsStream(filename));
	}


	public static String readFile(InputStream is) throws IOException {
		return IOUtils.toString(is, Charsets.UTF_8);
	}


	public static void assertFileEqualsToString(String filename, Class<?> clz, String actual) throws IOException {
		assertEquals(readFile(clz, filename), actual);
	}


	public static void assertBetween(Long expectedLow, Long expectedHigh, Long actual) {
		if (actual < expectedLow || actual > expectedHigh) {
			fail("expected between:<" + expectedLow + "> and <" + expectedHigh + "> but was:<" + actual + ">");
		}
	}


	public static void assertListEquals(List<?> expected, List<?> actual) {
		assertEquals(expected.size(), expected.size());
		for (int i = 0; i < actual.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}


	public static void assertBetween(Date expectedFrom, Date expectedTo, Date actual) {
		assertBetween(expectedFrom, expectedTo, actual, false);
	}


	public static void assertBetween(Date expectedFrom, Date expectedTo, Date actual, boolean ignoreFragments) {
		int factor = (ignoreFragments) ? 1000 : 1;
		assertBetween(expectedFrom.getTime() / factor, expectedTo.getTime() / factor, actual.getTime() / factor);
	}


	public static void assertBetween(Double expectedLower, Double expectedUpper, Double actual) {
		assertTrue("Value smaller then expected", actual >= expectedLower);
		assertTrue("Value larger then expected", actual <= expectedUpper);
	}


	public static void assertDateNear(long msThreshold, Date actual) {
		assertDateNear(msThreshold, actual, false);
	}


	public static void assertDateNear(long msThreshold, Date actual, boolean ignore) {
		assertBetween(date(now().getTime() - msThreshold), date(now().getTime() + msThreshold), actual, ignore);
	}


	public static void assertDateNear(String timeThreshold, Date actual) {
		assertDateNear(timeThreshold, actual, false);
	}


	public static void assertDateNear(String timeThreshold, Date actual, boolean ignore) {
		assertDateNear(HumanTime.dehumanizeTime(timeThreshold), actual, ignore);
	}


	// helper for hamcrest
	public static <T extends Comparable<T>> Matcher<T> between(T lower, T upper) {
		return allOf(greaterThanOrEqualTo(lower), lessThanOrEqualTo(upper));
	}


	@SuppressWarnings("unchecked")
	public static <T> Matcher<java.lang.Iterable<? extends T>> containsInOrder(T... items) {
		return IsIterableContainingInOrder.<T> contains(items);
	}

}
