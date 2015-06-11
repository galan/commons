package de.galan.commons.test;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInOrder;

import com.google.common.io.Resources;

import de.galan.commons.time.HumanTime;


/**
 * Common helper methods that are helpful for the AbstractTestParent and Test-Classes via static imports.
 *
 * @author galan
 */
public class Tests {

	public static String getWorkingDirectory() {
		// Get the working directory, which is in eclipse/maven the project folder
		return System.getProperty("user.dir");
		//return new File("").getAbsolutePath();
	}


	public static File getTestDirectory(boolean cleanup) {
		File dir = new File(getWorkingDirectory(), "target/tests");
		if (cleanup) {
			FileUtils.deleteQuietly(dir);
		}
		dir.mkdirs();
		return dir;
	}


	public static File getTestDirectory() {
		return getTestDirectory(false);
	}


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
		return IOUtils.toString(is);
	}


	public static void assertFileEqualsToString(String filename, Class<?> clz, String actual) throws IOException {
		assertThat(actual).isEqualTo(readFile(clz, filename));
	}


	public static void assertBetween(Long expectedLow, Long expectedHigh, Long actual) {
		assertThat(actual).isBetween(expectedLow, expectedHigh);
	}


	public static void assertListEquals(List<?> expected, List<?> actual) {
		assertThat(actual).isEqualTo(expected);
	}


	public static void assertBetween(Date expectedFrom, Date expectedTo, Date actual) {
		assertBetween(expectedFrom, expectedTo, actual, false);
	}


	public static void assertBetween(Date expectedFrom, Date expectedTo, Date actual, boolean truncateMillis) {
		assertThat(truncate(actual, truncateMillis)).isBetween(truncate(expectedFrom, truncateMillis), truncate(expectedTo, truncateMillis), true, true);
	}


	private static Date truncate(Date actual, boolean truncateMillis) {
		return truncateMillis ? from(actual).truncate(millis()).toDate() : actual;
	}


	private static Date truncate(Instant actual, boolean truncateMillis) {
		return truncateMillis ? from(actual).truncate(millis()).toDate() : from(actual).toDate();
	}


	public static void assertBetween(Double expectedLower, Double expectedUpper, Double actual) {
		assertThat(actual).isBetween(expectedLower, expectedUpper);
	}


	public static void assertDateNear(long msThreshold, Date actual) {
		assertDateNear(msThreshold, actual, false);
	}


	public static void assertDateNear(long msThreshold, Date actual, boolean truncateMillis) {
		assertThat(truncate(actual, truncateMillis)).isCloseTo(truncate(now(), truncateMillis), msThreshold);
	}


	public static void assertDateNear(String timeThreshold, Date actual) {
		assertDateNear(timeThreshold, actual, false);
	}


	public static void assertDateNear(String timeThreshold, Instant actual) {
		assertDateNear(timeThreshold, new Date(actual.toEpochMilli()), false);
	}


	public static void assertDateNear(String timeThreshold, Date actual, boolean truncateMillis) {
		assertDateNear(HumanTime.dehumanizeTime(timeThreshold), actual, truncateMillis);
	}


	// helper for hamcrest
	public static <T extends Comparable<T>> Matcher<T> between(T lower, T upper) {
		return Matchers.allOf(Matchers.greaterThanOrEqualTo(lower), Matchers.lessThanOrEqualTo(upper));
	}


	@SuppressWarnings("unchecked")
	public static <T> Matcher<java.lang.Iterable<? extends T>> containsInOrder(T... items) {
		return IsIterableContainingInOrder.<T> contains(items);
	}

}
