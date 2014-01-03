package de.galan.commons.time;

import static de.galan.commons.time.DateDsl.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.test.FixedDateSupplier;


/**
 * CUT TimeDsl
 * 
 * @author daniel
 */
public class TimeDslTest extends AbstractTestParent {

	Date sep12 = date("2012-09-07 15:15:00");
	Date may12 = date("2012-05-01 12:00:00");
	Date may40 = date("2040-05-01 12:00:00");


	@Before
	public void setup() {
		DateDsl.setDateSupplier(new FixedDateSupplier("2012-09-07 18:00:00"));
	}


	@Test
	public void equals() {
		assertFalse(TimeDsl.when(sep12).equals(may12));
		assertFalse(TimeDsl.when(sep12).equalsExactly(may12));
		assertFalse(TimeDsl.when(may12).equals(sep12));
		assertFalse(TimeDsl.when(may12).equalsExactly(sep12));

		assertTrue(TimeDsl.when(may12).equals(may12));
		assertTrue(TimeDsl.when(may12).equalsExactly(may12));

		Date now = now();
		Date now10 = from(now).in(10, millis()).toDate();
		assertTrue(TimeDsl.when(now()).equals(now10));
		assertFalse(TimeDsl.when(now()).equalsExactly(now10));
	}


	@Test
	public void after() {
		assertTrue(TimeDsl.when(sep12).after(may12));
		assertFalse(TimeDsl.when(may12).after(sep12));
		assertTrue(TimeDsl.when(now()).after(now()));
	}


	@Test
	public void before() {
		assertFalse(TimeDsl.when(sep12).before(may12));
		assertTrue(TimeDsl.when(may12).before(sep12));
		assertTrue(TimeDsl.when(now()).before(now()));
	}


	@Test
	public void between() {
		assertTrue(TimeDsl.when(now()).between(may12).and(may40));
		assertFalse(TimeDsl.when(may12).between(sep12).and(may40));
		assertTrue(TimeDsl.when(may12).between(may12).and(may40));
		assertTrue(TimeDsl.when(sep12).between(may12).and(sep12));
		assertTrue(TimeDsl.when(sep12).between(may40).and(may12));
	}


	@Test
	public void isAtLeastBefore() {
		assertTrue(TimeDsl.when(may12).isAtLeast("20m").before(sep12));
		assertTrue(TimeDsl.when(date("2012-05-01 11:39:00")).isAtLeast("20m").before(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 11:40:00")).isAtLeast("20m").before(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 12:40:00")).isAtLeast("20m").before(may12));
		assertFalse(TimeDsl.when(may12).isAtLeast("20m").before(may12));
	}


	@Test
	public void isAtMostBefore() {
		assertFalse(TimeDsl.when(may12).isAtMost("20m").before(sep12));
		assertFalse(TimeDsl.when(date("2012-05-01 11:39:00")).isAtMost("20m").before(may12));
		assertTrue(TimeDsl.when(date("2012-05-01 11:40:00")).isAtMost("20m").before(may12));
		assertTrue(TimeDsl.when(date("2012-05-01 11:50:00")).isAtMost("20m").before(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 12:40:00")).isAtMost("20m").before(may12));
		assertTrue(TimeDsl.when(may12).isAtMost("20m").before(may12));
	}


	@Test
	public void isAtLeastAfter() {
		assertTrue(TimeDsl.when(sep12).isAtLeast("20m").after(may12));
		assertTrue(TimeDsl.when(date("2012-05-01 12:21:00")).isAtLeast("20m").after(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 12:20:00")).isAtLeast("20m").after(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 11:20:00")).isAtLeast("20m").after(may12));
		assertFalse(TimeDsl.when(may12).isAtLeast("20m").after(may12));
	}


	@Test
	public void isAtMostAfter() {
		assertFalse(TimeDsl.when(sep12).isAtMost("20m").after(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 12:21:00")).isAtMost("20m").after(may12));
		assertTrue(TimeDsl.when(date("2012-05-01 12:20:00")).isAtMost("20m").after(may12));
		assertTrue(TimeDsl.when(date("2012-05-01 12:10:00")).isAtMost("20m").after(may12));
		assertFalse(TimeDsl.when(date("2012-05-01 11:20:00")).isAtMost("20m").after(may12));
		assertTrue(TimeDsl.when(may12).isAtMost("20m").after(may12));
	}


	@Test
	public void weekday() throws Exception {
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(monday()));
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(tuesday()));
		assertTrue(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(wednesday()));
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(thursday()));
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(friday()));
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(saturday()));
		assertFalse(TimeDsl.when(date("2012-11-07 11:55:55")).isWeekday(sunday()));
	}

}
