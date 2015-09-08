package de.galan.commons.time;

import static de.galan.commons.test.Tests.*;
import static de.galan.commons.time.Dates.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT DateDsl
 *
 * @author galan
 */
@Deprecated
public class DatesTest extends AbstractTestParent {

	@Test
	public void testNow() {
		assertDateNear("10s", now());
		assertDateNear("10s", from(now()).toDate());
	}


	@Test
	public void testTomorrow() {
		Date tx = DateUtils.addDays(new Date(), 1);
		assertBetween(tx.getTime() - 1000, tx.getTime() + 1000, tomorrow().getTime());
	}


	@Test
	public void testYesterday() {
		Date tx = DateUtils.addDays(new Date(), -1);
		assertBetween(tx.getTime() - 1000, tx.getTime() + 1000, yesterday().getTime());
	}


	@Test
	public void testDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 51);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 0);

		Date dateString = date("2012-05-31 17:51:01");
		Date dateInt = date(2012, 5, 31, 17, 51, 1);

		assertEquals(cal.getTime().getTime(), dateString.getTime());
		assertEquals(cal.getTime().getTime(), dateInt.getTime());
		assertEquals(dateString.getTime(), dateInt.getTime());
	}


	@Test
	public void toStringFormat() throws Exception {
		String dateString = "2012-05-31 17:51:01";
		assertEquals(dateString, from(date(dateString)).toString());
	}


	@Test
	public void toStringCustomFormat() {
		String dateString = "2012-05-31 17:51:01";
		assertEquals("05/31/2012", from(date(dateString)).toString("MM/dd/yyyy"));
	}


	@Test
	public void testFromTo() {
		assertEquals("2012-05-31 17:51:01", from(date("2012-05-31 17:51:01")).toString());
		assertDateNear("1s", from(now()).toDate());
	}


	@Test
	public void testIn() {
		assertEquals("2012-06-02 17:51:01", from(date("2012-05-31 17:51:01")).in(2, days()).toString());
		assertEquals("2012-08-31 17:51:01", from(date("2012-05-31 17:51:01")).in(3, months()).toString());
		assertEquals("2013-05-31 17:51:01", from(date("2012-05-31 17:51:01")).in(1, year()).toString());

		String date = from(date("2012-05-31 17:51:01")).in(2, years()).in(3, months()).in(1, hour()).in(10, minutes()).in(20, seconds()).toString();
		assertEquals("2014-08-31 19:01:21", date);
	}


	@Test
	public void testBefore() {
		assertEquals("2012-05-29 17:51:01", from(date("2012-05-31 17:51:01")).before(2, days()).toString());
		assertEquals("2012-02-29 17:51:01", from(date("2012-05-31 17:51:01")).before(3, months()).toString());
		assertEquals("2011-05-31 17:51:01", from(date("2012-05-31 17:51:01")).before(1, year()).toString());

		String date = from(date("2012-05-31 17:51:01")).before(2, years()).before(3, months()).before(1, hour()).before(10, minutes()).before(20,
			seconds()).toString();
		assertEquals("2010-02-28 16:40:41", date);
	}


	@Test
	public void testNext() {
		assertEquals("2013-01-01 00:00:00", from(date("2012-05-31 17:51:01")).next(year()).toString());
		assertEquals("2012-06-01 00:00:00", from(date("2012-05-31 17:51:01")).next(month()).toString());
		assertEquals("2012-06-01 00:00:00", from(date("2012-05-31 17:51:01")).next(day()).toString());
		assertEquals("2012-05-31 18:00:00", from(date("2012-05-31 17:51:01")).next(hour()).toString());
		assertEquals("2012-05-31 17:52:00", from(date("2012-05-31 17:51:01")).next(minute()).toString());
		assertEquals("2012-05-31 17:51:02", from(date("2012-05-31 17:51:01")).next(second()).toString());
	}


	@Test
	public void testPrevious() {
		assertEquals("2011-01-01 00:00:00", from(date("2012-05-31 17:51:01")).previous(year()).toString());
		assertEquals("2012-04-01 00:00:00", from(date("2012-05-31 17:51:01")).previous(month()).toString());
		assertEquals("2012-05-30 00:00:00", from(date("2012-05-31 17:51:01")).previous(day()).toString());
		assertEquals("2012-05-31 16:00:00", from(date("2012-05-31 17:51:01")).previous(hour()).toString());
		assertEquals("2012-05-31 17:50:00", from(date("2012-05-31 17:51:01")).previous(minute()).toString());
		assertEquals("2012-05-31 17:51:00", from(date("2012-05-31 17:51:01")).previous(second()).toString());
	}


	@Test
	public void testAt() {
		assertEquals("2012-05-31 12:00:00", from(date("2012-05-31 17:51:01")).atNoon().toString());
		assertEquals("2012-05-31 00:00:00", from(date("2012-05-31 17:51:01")).atMidnight().toString());
		assertEquals("2012-05-31 22:13:09", from(date("2012-05-31 17:51:01")).at("22:13:09").toString());
		assertEquals("2012-05-31 22:13:09", from(date("2012-05-31 17:51:01")).at(22, 13, 9).toString());
	}


	@Test
	public void testNextWeekday() {
		assertEquals("2012-06-07 17:51:01", from(date("2012-05-31 17:51:01")).next(thursday()).toString());
		assertEquals("2012-06-01 17:51:01", from(date("2012-05-31 17:51:01")).next(friday()).toString());
		assertEquals("2012-06-02 17:51:01", from(date("2012-05-31 17:51:01")).next(saturday()).toString());
		assertEquals("2012-06-03 17:51:01", from(date("2012-05-31 17:51:01")).next(sunday()).toString());
		assertEquals("2012-06-04 17:51:01", from(date("2012-05-31 17:51:01")).next(monday()).toString());
		assertEquals("2012-06-05 17:51:01", from(date("2012-05-31 17:51:01")).next(tuesday()).toString());
		assertEquals("2012-06-06 17:51:01", from(date("2012-05-31 17:51:01")).next(wednesday()).toString());
	}


	@Test
	public void testPreviousWeekday() {
		assertEquals("2012-05-24 17:51:01", from(date("2012-05-31 17:51:01")).previous(thursday()).toString());
		assertEquals("2012-05-25 17:51:01", from(date("2012-05-31 17:51:01")).previous(friday()).toString());
		assertEquals("2012-05-26 17:51:01", from(date("2012-05-31 17:51:01")).previous(saturday()).toString());
		assertEquals("2012-05-27 17:51:01", from(date("2012-05-31 17:51:01")).previous(sunday()).toString());
		assertEquals("2012-05-28 17:51:01", from(date("2012-05-31 17:51:01")).previous(monday()).toString());
		assertEquals("2012-05-29 17:51:01", from(date("2012-05-31 17:51:01")).previous(tuesday()).toString());
		assertEquals("2012-05-30 17:51:01", from(date("2012-05-31 17:51:01")).previous(wednesday()).toString());
	}


	@Test
	public void fromDuskTillDawn() throws Exception {
		long expected = Durations.dehumanize("12h").longValue();
		Date dusk = date("2012-11-01 18:00:00");
		Date dawn = date("2012-11-02 06:00:00");
		assertEquals(expected, from(dusk).till(dawn));
	}


	@Test
	public void toIso8601Utc() throws Exception {
		Date dateIso = dateIso("2013-07-04T07:36:11Z");
		assertEquals("2013-07-04T07:36:11Z", from(dateIso).toIso8601Utc());
	}


	@Test
	public void testDateIso() throws Exception {
		assertEquals(dateIso("2013-07-04T07:36:11Z"), Date.from(ZonedDateTime.of(2013, 7, 4, 7, 36, 11, 0, Instants.ZONE_UTC).toInstant()));
	}


	@Test
	public void dateLong() throws Exception {
		assertEquals(date(0L), dateIso("1970-01-01T00:00:00Z"));
		assertEquals(date(1372930571000L), dateIso("2013-07-04T09:36:11Z"));
	}


	@Test
	public void truncate() throws Exception {
		long timeWithoutMillis = 1372930571000L;
		Date dateWithMillis = date(timeWithoutMillis + 123L);
		assertThat(dateWithMillis).isEqualToIgnoringMillis(dateIso("2013-07-04T09:36:11Z"));
		assertThat(dateWithMillis).isNotEqualTo(dateIso("2013-07-04T09:36:11Z"));
		Date dateWithoutMillis = from(dateWithMillis).truncate(millis()).toDate();
		long longWithoutMillis = from(dateWithMillis).truncate(millis()).toLong();
		assertThat(dateWithoutMillis).isEqualTo(dateIso("2013-07-04T09:36:11Z"));
		assertThat(longWithoutMillis).isEqualTo(timeWithoutMillis);
	}


	@Test
	public void toLong() throws Exception {
		long timeWithoutMillis = 1372930571000L;
		assertThat(from(date(timeWithoutMillis)).toLong()).isEqualTo(timeWithoutMillis);
		long timeWithMillis = 1372930571123L;
		assertThat(from(date(timeWithMillis)).toLong()).isEqualTo(timeWithMillis);
	}


	@Test
	public void toInstant() throws Exception {
		long timeWithoutMillis = 1372930571000L;
		assertThat(from(date(timeWithoutMillis)).toInstant()).isEqualTo(Instant.ofEpochMilli(timeWithoutMillis));
		long timeWithMillis = 1372930571123L;
		assertThat(from(date(timeWithMillis)).toInstant()).isEqualTo(Instant.ofEpochMilli(timeWithMillis));
	}

}
