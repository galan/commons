package de.galan.commons.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.galan.commons.time.DateDsl.WeekdayUnit;


/**
 * Comparison of Dates and time with a fluent interface.<br/>
 * See also https://github.com/galan/commons/wiki/TimeDsl
 * 
 * @author daniel
 */
public class TimeDsl {

	public static TimeBuilder when(Date date) {
		return new TimeBuilder(date);
	}

	/*
	public static boolean withoutMs() {
		return true;
	}
	*/

	/** TimeDsl Builder */
	public static class TimeBuilder {

		private Date ref;


		TimeBuilder(Date date) {
			ref = date;
		}


		public boolean equalsExactly(Date other) {
			return equals(other, false);
		}


		public boolean equals(Date other) {
			return equals(other, true);
		}


		protected boolean equals(Date other, boolean ignoreMs) {
			boolean result = false;
			if (ref != null) {
				if (ignoreMs) {
					result = truncate(ref) == truncate(other);
				}
				else {
					result = ref.getTime() == other.getTime();
				}
			}
			else if (ref == null && other == null) {
				result = true;
			}
			return result;
		}


		protected long truncate(Date date) {
			// removes milliseconds part
			return date.getTime() / 1000;
		}


		protected long truncate(long time) {
			// removes milliseconds part
			return time / 1000;
		}


		public boolean after(Date date) {
			return ref.getTime() >= date.getTime();
		}


		public boolean before(Date date) {
			return ref.getTime() <= date.getTime();
		}


		public BetweenTimeBuilder between(Date start) {
			return new BetweenTimeBuilder(ref, start);
		}


		public IsTimeBuilder isAtLeast(String time) {
			return isAtLeast(HumanTime.dehumanizeTime(time));
		}


		public IsTimeBuilder isAtLeast(long ms) {
			return new IsTimeBuilder(ref, ms, true);
		}


		public IsTimeBuilder isAtMost(String time) {
			return isAtMost(HumanTime.dehumanizeTime(time));
		}


		public IsTimeBuilder isAtMost(long ms) {
			return new IsTimeBuilder(ref, ms, false);
		}


		public boolean isWeekday(WeekdayUnit weekday) {
			Calendar cal = new GregorianCalendar(Locale.GERMAN);
			cal.setTime(ref);
			return cal.get(Calendar.DAY_OF_WEEK) == weekday.getField();
		}

	}

	/** TimeDsl Builder */
	public static class IsTimeBuilder {

		private Date ref;
		private long ms;
		private boolean atLeast;


		IsTimeBuilder(Date ref, long ms, boolean atLeast) {
			this.ref = ref;
			this.ms = ms;
			this.atLeast = atLeast;
		}


		public boolean before(Date date) {
			long timeRef = ref.getTime();
			long timeDate = date.getTime();
			if (atLeast) {
				return timeRef < timeDate - ms;
			}
			return timeRef >= timeDate - ms && timeRef <= timeDate;
		}


		public boolean after(Date date) {
			long timeRef = ref.getTime();
			long timeDate = date.getTime();
			if (atLeast) {
				return ref.getTime() > date.getTime() + ms;
			}
			return timeRef <= timeDate + ms && timeRef >= timeDate;
		}

	}

	/** TimeDsl Builder */
	public static class BetweenTimeBuilder {

		private Date ref;
		private Date start;


		BetweenTimeBuilder(Date ref, Date start) {
			this.ref = ref;
			this.start = start;
		}


		public boolean and(Date end) {
			long timeRef = ref.getTime();
			long timeStart = start.getTime();
			long timeEnd = end.getTime();
			if (timeStart > timeEnd) {
				long temp = timeStart;
				timeStart = timeEnd;
				timeEnd = temp;
			}
			return timeStart <= timeRef && timeEnd >= timeRef;
		}

	}

}
