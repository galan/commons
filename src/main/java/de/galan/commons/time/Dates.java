package de.galan.commons.time;

import static org.apache.commons.lang3.StringUtils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.Logger;

import de.galan.commons.logging.Logr;


/**
 * Construction of Date-objects with a fluent interface. Provides a a simple but useful subset for creating, modfing and
 * formatting time-based objects. Application-wide time will be setup in ApplicationClock.<br/>
 * See also https://github.com/galan/commons/wiki/DateDsl<br/>
 * <br/>
 * Deprecated in favor of de.galan.commons.time.Instants.<br/>
 *
 * @author galan
 */
@Deprecated
public class Dates {

	private static final Logger LOG = Logr.get();

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static final ThreadLocal<Map<String, SimpleDateFormat>> LOCAL_SDF = ThreadLocal.withInitial(HashMap::new);


	private static SimpleDateFormat getDateFormater(String pattern, String timezone) {
		String key = pattern + "//" + timezone;
		SimpleDateFormat result = LOCAL_SDF.get().get(key);
		if (result == null) {
			result = new SimpleDateFormat(pattern);
			if (isNotBlank(timezone)) {
				result.setTimeZone(TimeZone.getTimeZone(timezone));
			}
			LOCAL_SDF.get().put(key, result);
		}
		return result;
	}


	public static Date now() {
		return new Date(ApplicationClock.getClock().millis());
	}


	public static Date tomorrow() {
		return DateUtils.addDays(now(), 1);
	}


	public static Date yesterday() {
		return DateUtils.addDays(now(), -1);
	}


	public static DateBuilder from(Date date) {
		return new DateBuilder(date);
	}


	/** Creates a java.util.date, input format is "yyyy-MM-dd HH:mm:ss" */
	public static Date date(String date) {
		Date result = null;
		try {
			result = getDateFormater(DATE_FORMAT, null).parse(date);
		}
		catch (ParseException pex) {
			LOG.warn("Date not constructable {}", pex, date);
		}
		return result;
	}


	public static Date date(int year, int month, int day, int hour, int minute, int second) {
		return date(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
	}


	/** Same as new Date(dateAsLong) */
	public static Date date(long dateAsLong) {
		return new Date(dateAsLong);
	}


	public static Date dateIso(String date) {
		Date result = null;
		try {
			result = getDateFormater(DATE_FORMAT_ISO, "UTC").parse(date);
		}
		catch (ParseException pex) {
			LOG.warn("Date not constructable {}", pex, date);
		}
		return result;
	}


	public static DatetimeUnit milli() {
		return DatetimeUnit.millis;
	}


	public static DatetimeUnit millis() {
		return DatetimeUnit.millis;
	}


	public static DatetimeUnit second() {
		return DatetimeUnit.seconds;
	}


	public static DatetimeUnit seconds() {
		return DatetimeUnit.seconds;
	}


	public static DatetimeUnit minute() {
		return DatetimeUnit.minutes;
	}


	public static DatetimeUnit minutes() {
		return DatetimeUnit.minutes;
	}


	public static DatetimeUnit hour() {
		return DatetimeUnit.hours;
	}


	public static DatetimeUnit hours() {
		return DatetimeUnit.hours;
	}


	public static DatetimeUnit day() {
		return DatetimeUnit.day;
	}


	public static DatetimeUnit days() {
		return DatetimeUnit.day;
	}


	public static DatetimeUnit week() {
		return DatetimeUnit.week;
	}


	public static DatetimeUnit weeks() {
		return DatetimeUnit.week;
	}


	public static DatetimeUnit month() {
		return DatetimeUnit.month;
	}


	public static DatetimeUnit months() {
		return DatetimeUnit.month;
	}


	public static DatetimeUnit year() {
		return DatetimeUnit.year;
	}


	public static DatetimeUnit years() {
		return DatetimeUnit.year;
	}


	public static WeekdayUnit monday() {
		return WeekdayUnit.monday;
	}


	public static WeekdayUnit tuesday() {
		return WeekdayUnit.tuesday;
	}


	public static WeekdayUnit wednesday() {
		return WeekdayUnit.wednesday;
	}


	public static WeekdayUnit thursday() {
		return WeekdayUnit.thursday;
	}


	public static WeekdayUnit friday() {
		return WeekdayUnit.friday;
	}


	public static WeekdayUnit saturday() {
		return WeekdayUnit.saturday;
	}


	public static WeekdayUnit sunday() {
		return WeekdayUnit.sunday;
	}

	/** Actual builder class */
	public static class DateBuilder {

		private Calendar cal;


		public DateBuilder(Date date) {
			cal = new GregorianCalendar(Locale.GERMAN);
			cal.setTime(date);
			cal.setLenient(true);
			//cal.set(Calendar.MILLISECOND, 0);
		}


		public DateBuilder in(int amount, DatetimeUnit unit) {
			cal.add(unit.getField(), amount);
			return this;
		}


		public DateBuilder before(int amount, DatetimeUnit unit) {
			cal.add(unit.getField(), -1 * amount);
			return this;
		}


		public DateBuilder next(DatetimeUnit unit) {
			return move(unit, 1);
		}


		public DateBuilder previous(DatetimeUnit unit) {
			return move(unit, -1);
		}


		public DateBuilder next(WeekdayUnit unit) {
			in(1, day());
			int dow = cal.get(Calendar.DAY_OF_WEEK);
			int days = (unit.getField() - dow + 7) % 7;
			return in(days, days());
		}


		public DateBuilder previous(WeekdayUnit unit) {
			int dow = cal.get(Calendar.DAY_OF_WEEK);
			int days = 7 - (unit.getField() - dow + 7) % 7;
			return before(days, days());
		}


		protected DateBuilder move(DatetimeUnit unit, int amount) {
			return truncate(unit).in(amount, unit);
		}


		public DateBuilder at(int hour, int minute, int second) {
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.SECOND, second);
			return this;
		}


		public DateBuilder at(String time) {
			//TODO improve
			String[] split = time.split(":");
			int hour = Integer.valueOf(split[0]);
			int min = Integer.valueOf(split[1]);
			int sec = Integer.valueOf(split[2]);
			return at(hour, min, sec);
		}


		public DateBuilder atMidnight() {
			return at(0, 0, 0);
		}


		public DateBuilder atNoon() {
			return at(12, 0, 0);
		}


		public DateBuilder truncate(DatetimeUnit unit) {
			switch (unit) {
				case year:
					cal.set(Calendar.MONTH, 0);
				case month:
					cal.set(Calendar.DAY_OF_MONTH, 1);
				case day:
					cal.set(Calendar.HOUR_OF_DAY, 0);
				case hours:
					cal.set(Calendar.MINUTE, 0);
				case minutes:
					cal.set(Calendar.SECOND, 0);
				case millis:
					cal.set(Calendar.MILLISECOND, 0);
			}
			return this;
		}


		public long till(Date date) {
			return date.getTime() - cal.getTimeInMillis();
		}


		public Date toDate() {
			return cal.getTime();
		}


		@Override
		public String toString() {
			return toString(DATE_FORMAT);
		}


		public String toString(String format) {
			return getDateFormater(format, null).format(cal.getTime());
		}


		public String toIso8601Utc() {
			return getDateFormater(DATE_FORMAT_ISO, "UTC").format(cal.getTime());
		}


		public long toLong() {
			return cal.getTimeInMillis();
		}


		public Instant toInstant() {
			return Instant.ofEpochMilli(toLong());
		}
	}

	/** Units as own enum for better code-completition support (instead of having ints everywhere) */
	public static enum DatetimeUnit {
		millis(Calendar.MILLISECOND),
		seconds(Calendar.SECOND),
		minutes(Calendar.MINUTE),
		hours(Calendar.HOUR_OF_DAY),
		day(Calendar.DAY_OF_YEAR),
		week(Calendar.WEEK_OF_YEAR),
		month(Calendar.MONTH),
		year(Calendar.YEAR);

		private final int field;


		private DatetimeUnit(int field) {
			this.field = field;
		}


		public int getField() {
			return field;
		}
	}

	/** Units as own enum for better code-completition support (instead of having ints everywhere) */
	public static enum WeekdayUnit {
		monday(Calendar.MONDAY),
		tuesday(Calendar.TUESDAY),
		wednesday(Calendar.WEDNESDAY),
		thursday(Calendar.THURSDAY),
		friday(Calendar.FRIDAY),
		saturday(Calendar.SATURDAY),
		sunday(Calendar.SUNDAY);

		private final int field;


		private WeekdayUnit(int field) {
			this.field = field;
		}


		public int getField() {
			return field;
		}
	}

}
