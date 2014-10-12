package de.galan.commons.time;

import static de.galan.commons.time.Instants.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT TimeDsl
 *
 * @author daniel
 */
public class TimeDslTest extends AbstractTestParent {

	Date sep12 = dateLocal("2012-09-07 15:15:00");
	Date may12 = dateLocal("2012-05-01 12:00:00");
	Date may40 = dateLocal("2040-05-01 12:00:00");


	@Before
	public void setup() {
		ApplicationClock.setLocal("2012-09-07 18:00:00");
		//Dates.setDateSupplier(new FixedDateSupplier("2012-09-07 18:00:00"));
	}


	@Test
	public void equals() {
		assertFalse(Times.when(sep12).equals(may12));
		assertFalse(Times.when(sep12).equalsExactly(may12));
		assertFalse(Times.when(may12).equals(sep12));
		assertFalse(Times.when(may12).equalsExactly(sep12));

		assertTrue(Times.when(may12).equals(may12));
		assertTrue(Times.when(may12).equalsExactly(may12));

		Instant now = now();
		Date now10 = from(now).in(10, millis()).toDate();
		assertTrue(Times.when(now()).equals(now10));
		assertFalse(Times.when(now()).equalsExactly(now10));
	}


	@Test
	public void after() {
		assertTrue(Times.when(sep12).after(may12));
		assertFalse(Times.when(may12).after(sep12));
		assertTrue(Times.when(now()).after(now()));
	}


	@Test
	public void before() {
		assertFalse(Times.when(sep12).before(may12));
		assertTrue(Times.when(may12).before(sep12));
		assertTrue(Times.when(now()).before(now()));
	}


	@Test
	public void between() {
		assertTrue(Times.when(now()).between(may12).and(may40));
		assertFalse(Times.when(may12).between(sep12).and(may40));
		assertTrue(Times.when(may12).between(may12).and(may40));
		assertTrue(Times.when(sep12).between(may12).and(sep12));
		assertTrue(Times.when(sep12).between(may40).and(may12));
	}


	@Test
	public void isAtLeastBefore() {
		assertTrue(Times.when(may12).isAtLeast("20m").before(sep12));
		assertTrue(Times.when(dateLocal("2012-05-01 11:39:00")).isAtLeast("20m").before(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 11:40:00")).isAtLeast("20m").before(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 12:40:00")).isAtLeast("20m").before(may12));
		assertFalse(Times.when(may12).isAtLeast("20m").before(may12));
	}


	@Test
	public void isAtMostBefore() {
		assertFalse(Times.when(may12).isAtMost("20m").before(sep12));
		assertFalse(Times.when(dateLocal("2012-05-01 11:39:00")).isAtMost("20m").before(may12));
		assertTrue(Times.when(dateLocal("2012-05-01 11:40:00")).isAtMost("20m").before(may12));
		assertTrue(Times.when(dateLocal("2012-05-01 11:50:00")).isAtMost("20m").before(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 12:40:00")).isAtMost("20m").before(may12));
		assertTrue(Times.when(may12).isAtMost("20m").before(may12));
	}


	@Test
	public void isAtLeastAfter() {
		assertTrue(Times.when(sep12).isAtLeast("20m").after(may12));
		assertTrue(Times.when(dateLocal("2012-05-01 12:21:00")).isAtLeast("20m").after(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 12:20:00")).isAtLeast("20m").after(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 11:20:00")).isAtLeast("20m").after(may12));
		assertFalse(Times.when(may12).isAtLeast("20m").after(may12));
	}


	@Test
	public void isAtMostAfter() {
		assertFalse(Times.when(sep12).isAtMost("20m").after(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 12:21:00")).isAtMost("20m").after(may12));
		assertTrue(Times.when(dateLocal("2012-05-01 12:20:00")).isAtMost("20m").after(may12));
		assertTrue(Times.when(dateLocal("2012-05-01 12:10:00")).isAtMost("20m").after(may12));
		assertFalse(Times.when(dateLocal("2012-05-01 11:20:00")).isAtMost("20m").after(may12));
		assertTrue(Times.when(may12).isAtMost("20m").after(may12));
	}


	@Test
	public void weekday() throws Exception {
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(monday()));
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(tuesday()));
		assertTrue(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(wednesday()));
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(thursday()));
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(friday()));
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(saturday()));
		assertFalse(Times.when(dateLocal("2012-11-07 11:55:55")).isWeekday(sunday()));
	}

}
