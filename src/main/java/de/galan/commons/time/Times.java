package de.galan.commons.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import de.galan.commons.time.Instants.WeekdayUnit;


/**
 * Comparison of Dates and time with a fluent interface.<br/>
 * See also https://github.com/galan/commons/blob/master/documentation/Times.md
 */
public class Times {

	public static TimeBuilder when(Date date) {
		return when(date.toInstant());
	}


	public static TimeBuilder when(Instant date) {
		return new TimeBuilder(date);
	}

	/** TimeDsl Builder */
	public static class TimeBuilder {

		private Instant ref;


		TimeBuilder(Instant date) {
			ref = date;
		}


		public boolean equalsExactly(Date other) {
			return equalsExactly(other.toInstant());
		}


		public boolean equalsExactly(Instant other) {
			return equals(other, false);
		}


		public boolean equals(Date other) {
			return equals(other.toInstant());
		}


		public boolean equals(Instant other) {
			return equals(other, true);
		}


		protected boolean equals(Instant other, boolean ignoreMs) {
			boolean result = false;
			if (ref != null) {
				if (ignoreMs) {
					result = truncate(ref) == truncate(other);
				}
				else {
					result = ref.toEpochMilli() == other.toEpochMilli();
				}
			}
			else if (ref == null && other == null) {
				result = true;
			}
			return result;
		}


		/** removes milliseconds part */
		protected long truncate(Instant date) {
			return date.toEpochMilli() / 1000;
		}


		/** removes milliseconds part */
		protected long truncate(long time) {
			return time / 1000;
		}


		public boolean after(Date date) {
			return after(date.toInstant());
		}


		public boolean after(Instant date) {
			return ref.toEpochMilli() > date.toEpochMilli();
		}


		public boolean before(Date date) {
			return before(date.toInstant());
		}


		public boolean before(Instant date) {
			return ref.toEpochMilli() < date.toEpochMilli();
		}


		public BetweenTimeBuilder between(Date start) {
			return between(start.toInstant());
		}


		public BetweenTimeBuilder between(Instant start) {
			return new BetweenTimeBuilder(ref, start);
		}


		public IsTimeBuilder isAtLeast(String time) {
			return isAtLeast(Durations.dehumanize(time));
		}


		public IsTimeBuilder isAtLeast(long ms) {
			return new IsTimeBuilder(ref, ms, true);
		}


		public IsTimeBuilder isAtMost(String time) {
			return isAtMost(Durations.dehumanize(time));
		}


		public IsTimeBuilder isAtMost(long ms) {
			return new IsTimeBuilder(ref, ms, false);
		}


		public boolean isWeekday(WeekdayUnit weekday) {
			return isWeekday(weekday, ZoneId.systemDefault());
		}


		public boolean isWeekday(WeekdayUnit weekday, ZoneId zone) {
			LocalDateTime ldt = LocalDateTime.ofInstant(ref, zone);
			return ldt.getDayOfWeek().equals(weekday.getUnit());
			//Calendar cal = new GregorianCalendar(Locale.GERMAN);
			//cal.setTime(ref);
			//return cal.get(Calendar.DAY_OF_WEEK) == weekday.getField();
		}

	}

	/** TimeDsl Builder */
	public static class IsTimeBuilder {

		private Instant ref;
		private long ms;
		private boolean atLeast;


		IsTimeBuilder(Instant ref, long ms, boolean atLeast) {
			this.ref = ref;
			this.ms = ms;
			this.atLeast = atLeast;
		}


		public boolean before(Date date) {
			return before(date.toInstant());
		}


		public boolean before(Instant date) {
			long timeRef = ref.toEpochMilli();
			long timeDate = date.toEpochMilli();
			if (atLeast) {
				return timeRef < timeDate - ms;
			}
			return timeRef >= timeDate - ms && timeRef <= timeDate;
		}


		public boolean after(Date date) {
			return after(date.toInstant());
		}


		public boolean after(Instant date) {
			long timeRef = ref.toEpochMilli();
			long timeDate = date.toEpochMilli();
			if (atLeast) {
				return ref.toEpochMilli() > date.toEpochMilli() + ms;
			}
			return timeRef <= timeDate + ms && timeRef >= timeDate;
		}

	}

	/** TimeDsl Builder */
	public static class BetweenTimeBuilder {

		private Instant ref;
		private Instant start;


		BetweenTimeBuilder(Instant ref, Instant start) {
			this.ref = ref;
			this.start = start;
		}


		public boolean and(Date end) {
			return and(end.toInstant());
		}


		public boolean and(Instant end) {
			long timeRef = ref.toEpochMilli();
			long timeStart = start.toEpochMilli();
			long timeEnd = end.toEpochMilli();
			if (timeStart > timeEnd) {
				long temp = timeStart;
				timeStart = timeEnd;
				timeEnd = temp;
			}
			return timeStart <= timeRef && timeEnd >= timeRef;
		}

	}

}
