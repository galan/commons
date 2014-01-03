package de.galan.commons.time;

import static de.galan.commons.time.DateDsl.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.test.FixedDateSupplier;


/**
 * CUT HumanTime
 * 
 * @author daniel
 */
public class HumanTimeTest extends AbstractTestParent {

	@Test
	public void timeAgo() {
		assertEquals("2d 7h 26m 48s", HumanTime.timeAgo(date("2012-01-22 02:33:12"), date("2012-01-24 10:00:00")));
		assertEquals("65d 16h 46m 8s", HumanTime.timeAgo(date("2011-11-19 17:14:12"), date("2012-01-24 10:00:20")));
		assertEquals("15363d 9h 20s", HumanTime.timeAgo(new Date(0L), date("2012-01-24 10:00:20")));
		assertEquals("", HumanTime.timeAgo(null, date("2012-01-24 10:00:20")));
		assertEquals("", HumanTime.timeAgo(new Date(System.currentTimeMillis() + 1000L))); // in the future
	}


	@Test
	public void timeLeft() {
		DateDsl.setDateSupplier(new FixedDateSupplier("2012-11-20 09:15:00"));
		assertEquals("1m 10s", HumanTime.timeLeft(date("2012-11-20 09:16:10")));
		assertEquals("", HumanTime.timeLeft(date("2012-11-20 09:13:33"))); // in the past
	}


	@Test
	public void humanizeTime() {
		assertEquals("0ms", HumanTime.humanizeTime(0L));
		assertEquals("1s200ms", HumanTime.humanizeTime(1200L));
	}


	@Test
	public void dehumanizeTime() {
		assertThat(HumanTime.dehumanizeTime("0"), is(equalTo(0L)));
		assertNull(HumanTime.dehumanizeTime(""));
		assertNull(HumanTime.dehumanizeTime(null));
		assertNull(HumanTime.dehumanizeTime("4h30m200"));

		assertThat(HumanTime.dehumanizeTime("1200"), is(equalTo(1200L)));
		assertThat(HumanTime.dehumanizeTime("1200ms"), is(equalTo(1200L)));
		assertThat(HumanTime.dehumanizeTime("1s200ms"), is(equalTo(1200L)));
		assertThat(HumanTime.dehumanizeTime("1s 200ms"), is(equalTo(1200L)));
		assertThat(HumanTime.dehumanizeTime(" 1s    200ms "), is(equalTo(1200L)));

		assertThat(HumanTime.dehumanizeTime("4h30m200ms"), is(equalTo(16200200L)));
		assertThat(HumanTime.dehumanizeTime("4h 30m 200ms"), is(equalTo(16200200L)));
		assertThat(HumanTime.dehumanizeTime("4h  30m200ms"), is(equalTo(16200200L)));

		assertThat(HumanTime.dehumanizeTime("2w3d4h5m6s7ms"), is(equalTo(1483506007L)));

		assertThat(HumanTime.dehumanizeTime("4m"), is(equalTo(240000L)));
	}


	@Test
	public void dehumanizeTimeCache() {
		assertThat(HumanTime.dehumanizeTime("2w3d4h5m6s7ms"), is(equalTo(1483506007L)));
		assertThat(HumanTime.dehumanizeTime("2w3d4h5m6s7ms"), is(equalTo(1483506007L)));
	}

}
