package de.galan.commons.time;

import static de.galan.commons.time.Instants.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import de.galan.commons.collection.SoftReferenceCache;


/**
 * Utility class to handle human readable time durations.
 */
public class Durations {

	private static final Pattern PATTERN_HUMAN_TIME = Pattern
		.compile("^([0-9]+w)?[ ]*([0-9]+d)?[ ]*([0-9]+h)?[ ]*([0-9]+m[^s]{0})?[ ]*([0-9]+s)?[ ]*([0-9]+ms)?$");

	public static final long MS_MILLISECOND = 1L;
	public static final long MS_SECOND = 1000L;
	public static final long MS_MINUTE = MS_SECOND * 60L;
	public static final long MS_HOUR = MS_MINUTE * 60L;
	public static final long MS_DAY = MS_HOUR * 24L;
	public static final long MS_WEEK = MS_DAY * 7L;

	/** Cache for calculated humantimes */
	private static SoftReferenceCache<Long> humantimeCache;


	/** Prints how long the given date is in the future in the format "Xd Xh Xm Xs Xms" */
	public static String timeLeft(Date date) {
		return timeLeft(date.toInstant());
	}


	/** Prints how long the given date is in the future in the format "Xd Xh Xm Xs Xms" */
	public static String timeLeft(Instant date) {
		return timeAgo(now(), date);
	}


	/** Prints how long the given date is ago in the format "Xd Xh Xm Xs Xms" */
	public static String timeAgo(Date date) {
		return timeAgo(date.toInstant());
	}


	/** Prints how long the given date is ago in the format "Xd Xh Xm Xs Xms" */
	public static String timeAgo(Instant date) {
		return timeAgo(date, now());
	}


	protected static String timeAgo(Instant date, Instant reference) {
		String result = "";
		if ((date != null) && (reference != null)) {
			long time = reference.toEpochMilli() - date.toEpochMilli();
			result = humanize(time, SPACE);
		}
		return result;
	}


	/** Converts a time in miliseconds to human readable duration in the format "Xd Xh Xm Xs Xms" */
	public static String humanize(long time) {
		return humanize(time, EMPTY);
	}


	public static String humanize(long time, String separator) {
		StringBuilder result = new StringBuilder();
		if (time == 0L) {
			result.append("0ms");
		}
		else {
			long timeLeft = time;
			timeLeft = appendUnit(timeLeft, separator, MS_DAY, "d", result);
			timeLeft = appendUnit(timeLeft, separator, MS_HOUR, "h", result);
			timeLeft = appendUnit(timeLeft, separator, MS_MINUTE, "m", result);
			timeLeft = appendUnit(timeLeft, separator, MS_SECOND, "s", result);
			timeLeft = appendUnit(timeLeft, separator, MS_MILLISECOND, "ms", result);
		}
		return result.toString().trim();
	}


	private static long appendUnit(long time, String separator, long unit, String text, StringBuilder buffer) {
		long result = time;
		if (time >= unit) {
			long hours = time / unit;
			buffer.append(hours);
			buffer.append(text);
			buffer.append(separator);
			result -= unit * hours;
		}
		return result;
	}


	private static SoftReferenceCache<Long> getHumantimeCache() {
		if (humantimeCache == null) {
			humantimeCache = new SoftReferenceCache<Long>();
		}
		return humantimeCache;
	}


	/** Converts a human readable duration in the format "Xd Xh Xm Xs Xms" to miliseconds. */
	public static Long dehumanize(String time) {
		Long result = getHumantimeCache().get(time);
		if (result == null) {
			if (isNotBlank(time)) {
				String input = time.trim();
				if (NumberUtils.isDigits(input)) {
					result = NumberUtils.toLong(input);
				}
				else {
					Matcher matcher = PATTERN_HUMAN_TIME.matcher(input);
					if (matcher.matches()) {
						long sum = 0L;
						sum += dehumanizeUnit(matcher.group(1), MS_WEEK);
						sum += dehumanizeUnit(matcher.group(2), MS_DAY);
						sum += dehumanizeUnit(matcher.group(3), MS_HOUR);
						sum += dehumanizeUnit(matcher.group(4), MS_MINUTE);
						sum += dehumanizeUnit(matcher.group(5), MS_SECOND);
						sum += dehumanizeUnit(matcher.group(6), MS_MILLISECOND);
						result = sum;
					}
				}
				getHumantimeCache().put(time, result);
			}
		}
		return result;
	}


	private static long dehumanizeUnit(String group, long unit) {
		long result = 0L;
		if (isNotBlank(group)) {
			String longString = group.replaceAll("[a-z ]", "");
			result = Long.valueOf(longString) * unit;
		}
		return result;
	}


	/** Converts a time in human readable format "Xd Xh Xm Xs Xms" to java.util.time.Duration (for interoperability). */
	public static Duration toDuration(String time) {
		if (isBlank(time)) {
			return Duration.ZERO;
		}
		return Duration.of(dehumanize(time), ChronoUnit.MILLIS);
	}


	/**
	 * Converts a time from java.util.time.Duration to human readable format "Xd Xh Xm Xs Xms" (for interoperability).
	 */
	public static String fromDuration(Duration duration) {
		if (duration == null) {
			return null;
		}
		return humanize(duration.get(ChronoUnit.MILLIS));
	}

}
