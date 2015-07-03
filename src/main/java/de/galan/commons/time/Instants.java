package de.galan.commons.time;

import static org.apache.commons.lang3.StringUtils.*;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.Logger;

import de.galan.commons.logging.Logr;


/**
 * Construction of time-objects with a fluent interface. Provides a a simple but useful subset for creating, modfing and
 * formatting time-based objects. Application-wide time will be setup in ApplicationClock.<br/>
 * See also https://github.com/galan/commons/wiki/...
 *
 * @author galan
 */
public class Instants {

	/*
	TemporalAdjuster
	 */

	private static final Logger LOG = Logr.get();

	public static final String DATE_FORMAT_LOCAL = "yyyy-MM-dd HH:mm:ss[.SSS]";
	public static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'";

	public static final ZoneId ZONE_LOCAL = ZoneId.systemDefault().normalized();
	public static final ZoneId ZONE_UTC = ZoneId.of("UTC").normalized();

	private static final Map<String, DateTimeFormatter> FORMATTERS = new ConcurrentHashMap<>();


	private static DateTimeFormatter getFormater(String pattern, String timezone) {
		String key = pattern + "//" + timezone;
		DateTimeFormatter result = FORMATTERS.get(key);
		if (result == null) {
			result = DateTimeFormatter.ofPattern(pattern);
			if (isNotBlank(timezone)) {
				result = result.withZone(ZoneId.of(timezone));
			}
			FORMATTERS.put(key, result);
		}
		return result;
	}


	public static Instant now() {
		return Instant.now(ApplicationClock.getClock());
	}


	public static Instant tomorrow() {
		return now().plus(1L, ChronoUnit.DAYS);
	}


	public static Instant yesterday() {
		return now().minus(1L, ChronoUnit.DAYS);
	}


	public static Date dateLocal(String text) {
		return Date.from(instantLocal(text));
	}


	public static Date date(long epochMilli) {
		//Date.from(instant(epochMilli))
		return new Date(epochMilli);
	}


	public static Date dateUtc(String text) {
		return Date.from(instantUtc(text));
	}


	/** Creates an Instant, input format is "yyyy-MM-dd HH:mm:ss", system default (local) timezone is used. */
	public static Instant instantLocal(String text) {
		return instant(text, ZONE_LOCAL);
	}


	/** Creates an Instant, input format is "yyyy-MM-dd HH:mm:ss", given timezone is used. */
	public static Instant instant(String text, ZoneId zone) {
		Instant result = null;
		try {
			LocalDateTime ldt = LocalDateTime.parse(text, getFormater(DATE_FORMAT_LOCAL, null));
			ZonedDateTime zdt = ldt.atZone(zone);
			result = zdt.toInstant();
		}
		catch (DateTimeParseException ex) {
			LOG.warn("Instant not constructable {}", ex, text);
		}
		return result;
	}


	/** Creates an Instant, input format is "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone is UTC. */
	public static Instant instantUtc(String text) {
		Instant result = null;
		try {
			LocalDateTime ldt = LocalDateTime.parse(text, getFormater(DATE_FORMAT_UTC, "UTC"));
			result = ldt.toInstant(ZoneOffset.UTC);
		}
		catch (DateTimeParseException ex) {
			LOG.warn("Instant not constructable {}", ex, text);
		}
		return result;
	}


	/** Creates an Instant from the milliseconds since the epoch. */
	public static Instant instant(long epochMilli) {
		return Instant.ofEpochMilli(epochMilli);
	}


	public static InstantBuilder from(Instant instant) {
		return new InstantBuilder(instant);
	}


	public static InstantBuilder from(Date date) {
		return new InstantBuilder(date.toInstant());
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
	public static class InstantBuilder {

		private Instant current;
		private ZoneId zone = ZONE_LOCAL;


		public InstantBuilder(Instant instant) {
			current = instant;
		}


		public InstantBuilder zone(ZoneId zoneId) {
			zone = zoneId == null ? ZONE_LOCAL : zoneId;
			return this;
		}


		public InstantBuilder with(TemporalAdjuster adjuster) {
			current = current.with(adjuster);
			return this;
		}


		public InstantBuilder in(int amount, DatetimeUnit unit) {
			if (unit == DatetimeUnit.week || unit == DatetimeUnit.month || unit == DatetimeUnit.year) {
				ZonedDateTime zdt = ZonedDateTime.ofInstant(current, zone);
				current = zdt.plus(amount, unit.getUnit()).toInstant();
			}
			else {
				current = current.plus(amount, unit.getUnit());
			}
			return this;
		}


		public InstantBuilder before(int amount, DatetimeUnit unit) {
			if (unit == DatetimeUnit.week || unit == DatetimeUnit.month || unit == DatetimeUnit.year) {
				ZonedDateTime zdt = ZonedDateTime.ofInstant(current, zone);
				current = zdt.minus(amount, unit.getUnit()).toInstant();
			}
			else {
				current = current.minus(amount, unit.getUnit());
			}
			return this;
		}


		public InstantBuilder next(DatetimeUnit unit) {
			return truncate(unit).in(1, unit);
		}


		public InstantBuilder previous(DatetimeUnit unit) {
			return truncate(unit).before(1, unit);
		}


		/** Truncates the time using the defined timezone */
		public InstantBuilder truncate(DatetimeUnit unit) {
			ZonedDateTime zdt = ZonedDateTime.ofInstant(current, zone);
			switch (unit) {
				case year:
					zdt = zdt.withMonth(1);
				case month:
					zdt = zdt.withDayOfMonth(1);
				case day:
					zdt = zdt.withHour(0);
				case hours:
					zdt = zdt.withMinute(0);
				case minutes:
					zdt = zdt.withSecond(0);
				case seconds:
					zdt = zdt.withNano(0);
					break;
				case millis:
					long millis = zdt.getLong(ChronoField.MILLI_OF_SECOND);
					zdt = zdt.withNano((int)(millis * 1_000_000L));
			}
			current = zdt.toInstant();
			return this;
		}


		public InstantBuilder next(WeekdayUnit unit) {
			ZonedDateTime zdt = ZonedDateTime.ofInstant(current, zone);
			current = zdt.with(TemporalAdjusters.next(unit.getUnit())).toInstant();
			return this;
			//current = current.with(TemporalAdjusters.next(unit.getUnit()));
			/*
			in(1, day());
			int dow = current.get(ChronoField.DAY_OF_WEEK);
			int days = (unit.getField() - dow + 7) % 7;
			return in(days, days());
			 */
		}


		public InstantBuilder previous(WeekdayUnit unit) {
			ZonedDateTime zdt = ZonedDateTime.ofInstant(current, zone);
			current = zdt.with(TemporalAdjusters.previous(unit.getUnit())).toInstant();
			return this;
			//current = current.with(TemporalAdjusters.previous(unit.getUnit()));
			/*
			int dow = current.get(ChronoField.DAY_OF_WEEK);
			int days = 7 - (unit.getField() - dow + 7) % 7;
			return before(days, days());
			 */
		}


		public InstantBuilder at(int hour, int minute, int second) {
			truncate(day());
			//current = current.with(ChronoField.HOUR_OF_DAY, hour).with(ChronoField.MINUTE_OF_HOUR, minute).with(ChronoField.SECOND_OF_MINUTE, second);
			current = current.plus(hour, ChronoUnit.HOURS).plus(minute, ChronoUnit.MINUTES).plus(second, ChronoUnit.SECONDS);
			return this;
		}


		public InstantBuilder at(String time) {
			//TODO improve
			String[] split = time.split(":");
			int hour = Integer.valueOf(split[0]);
			int min = Integer.valueOf(split[1]);
			int sec = Integer.valueOf(split[2]);
			return at(hour, min, sec);
		}


		public InstantBuilder atMidnight() {
			return at(0, 0, 0);
		}


		public InstantBuilder atNoon() {
			return at(12, 0, 0);
		}


		public long till(Date date) {
			return date.getTime() - toLong();
		}


		public long till(Instant instant) {
			return instant.toEpochMilli() - toLong();
		}


		@Override
		public String toString() {
			return toString(DATE_FORMAT_LOCAL);
		}


		public String toString(String format) {
			ZonedDateTime zdt = ZonedDateTime.ofInstant(current, ZONE_UTC);
			return getFormater(format, ZONE_LOCAL.getId()).format(zdt);
		}


		public String toStringIsoUtc() {
			return getFormater(DATE_FORMAT_UTC, "UTC").format(current);
		}


		public long toLong() {
			return current.toEpochMilli();
		}


		public Instant toInstant() {
			return current;
		}


		public Date toDate() {
			return Date.from(current);
		}


		/** Returns the instant as ZonedDateTime with UTC as ZoneId */
		public ZonedDateTime toZdt() {
			return toZdt(ZONE_UTC);
		}


		public ZonedDateTime toZdt(ZoneId zoneId) {
			return ZonedDateTime.ofInstant(current, zoneId);
		}

	}

	/** Units as own enum for better code-completition support (instead of having ints everywhere) */
	public static enum DatetimeUnit {
		//TODO nanos(Calendar.MILLISECOND, ChronoUnit.MILLIS),
		millis(Calendar.MILLISECOND, ChronoUnit.MILLIS),
		seconds(Calendar.SECOND, ChronoUnit.SECONDS),
		minutes(Calendar.MINUTE, ChronoUnit.MINUTES),
		hours(Calendar.HOUR_OF_DAY, ChronoUnit.HOURS),
		day(Calendar.DAY_OF_YEAR, ChronoUnit.DAYS),
		week(Calendar.WEEK_OF_YEAR, ChronoUnit.WEEKS),
		month(Calendar.MONTH, ChronoUnit.MONTHS),
		year(Calendar.YEAR, ChronoUnit.YEARS);

		private final int field;
		private TemporalUnit unit;


		private DatetimeUnit(int field, TemporalUnit unit) {
			this.field = field;
			this.unit = unit;
		}


		public int getField() {
			return field;
		}


		public TemporalUnit getUnit() {
			return unit;
		}
	}

	/** Units as own enum for better code-completition support (instead of having ints everywhere) */
	public static enum WeekdayUnit {
		monday(Calendar.MONDAY, DayOfWeek.MONDAY),
		tuesday(Calendar.TUESDAY, DayOfWeek.TUESDAY),
		wednesday(Calendar.WEDNESDAY, DayOfWeek.WEDNESDAY),
		thursday(Calendar.THURSDAY, DayOfWeek.THURSDAY),
		friday(Calendar.FRIDAY, DayOfWeek.FRIDAY),
		saturday(Calendar.SATURDAY, DayOfWeek.SATURDAY),
		sunday(Calendar.SUNDAY, DayOfWeek.SUNDAY);

		private final int field;
		private DayOfWeek unit;


		private WeekdayUnit(int field, DayOfWeek unit) {
			this.field = field;
			this.unit = unit;
		}


		public int getField() {
			return field;
		}


		public DayOfWeek getUnit() {
			return unit;
		}
	}

}
