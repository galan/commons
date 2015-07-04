package de.galan.commons.time;

import static de.galan.commons.test.Tests.*;
import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.StrictAssertions.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT Instants
 *
 * @author galan
 */
public class InstantsTest extends AbstractTestParent {

	@Test
	public void proofOfConceptForEqualsOnTimezone() throws Exception {
		ZonedDateTime zdtUtc = ZonedDateTime.of(2015, 2, 1, 14, 30, 0, 0, ZoneId.of("UTC").normalized());
		ZonedDateTime zdtZ = ZonedDateTime.of(2015, 2, 1, 14, 30, 0, 0, ZoneId.of("Z").normalized());
		assertEquals(zdtUtc, zdtZ);
	}


	@Test
	public void testNow() {
		assertDateNear("1s", now());
		assertDateNear("1s", from(now()).toDate());
	}


	@Test
	public void testTomorrow() {
		Date tx = DateUtils.addDays(new Date(), 1);
		assertBetween(tx.getTime() - 1000, tx.getTime() + 1000, tomorrow().toEpochMilli());
	}


	@Test
	public void testYesterday() {
		Date tx = DateUtils.addDays(new Date(), -1);
		assertBetween(tx.getTime() - 1000, tx.getTime() + 1000, yesterday().toEpochMilli());
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

		Instant inst = instantLocal("2012-05-31 17:51:01");
		assertThat(cal.getTime().getTime()).isEqualTo(inst.toEpochMilli());
	}


	@Test
	public void toStringFormat() throws Exception {
		assertThat("2012-05-31 17:51:01.000").isEqualTo(from(instantLocal("2012-05-31 17:51:01")).toString());
		assertThat("2014-08-10 20:45:15.000").isEqualTo(from(instantLocal("2014-08-10 20:45:15")).toString());
		assertThat("2014-08-10 20:45:15.000").isEqualTo(from(instantUtc("2014-08-10T18:45:15Z")).toString());
		assertThat("2014-12-10 19:45:15.000").isEqualTo(from(instantUtc("2014-12-10T18:45:15Z")).toString());
		assertThat("2014-12-10 19:45:15.000").isEqualTo(from(instantLocal("2014-12-10 19:45:15")).toString());
	}


	@Test
	public void toStringCustomFormat() {
		assertThat(from(instantLocal("2012-05-31 17:51:01")).toString("MM/dd/yyyy")).isEqualTo("05/31/2012");
	}


	@Test
	public void testFromTo() {
		assertThat(from(instantLocal("2012-05-31 17:51:01")).toString()).isEqualTo("2012-05-31 17:51:01.000");
		assertDateNear("1s", from(now()).toDate());
	}


	@Test
	public void testIn() {
		assertThat(from(instantLocal("2012-05-31 17:51:01")).in(2, days()).toString()).isEqualTo("2012-06-02 17:51:01.000");
		assertThat(from(instantLocal("2012-05-31 17:51:01")).in(3, months()).toString()).isEqualTo("2012-08-31 17:51:01.000");
		assertThat(from(instantLocal("2012-05-31 17:51:01")).in(1, year()).toString()).isEqualTo("2013-05-31 17:51:01.000");
		assertThat(from(instantLocal("2012-05-31 17:51:01")).in(1, year()).toStringIsoUtc()).isEqualTo("2013-05-31T15:51:01.000Z");

		String date = from(instantLocal("2012-05-31 17:51:01")).in(2, years()).in(3, months()).in(1, hour()).in(10, minutes()).in(20, seconds()).toString();
		assertThat(date).isEqualTo("2014-08-31 19:01:21.000");
	}


	@Test
	public void testBefore() {
		assertThat(from(instantLocal("2012-05-31 17:51:01")).before(2, days()).toString()).isEqualTo("2012-05-29 17:51:01.000");
		assertThat(from(instantLocal("2012-05-31 17:51:01")).before(3, months()).toString()).isEqualTo("2012-02-29 17:51:01.000"); // -ST>WT -1-
		assertThat(from(instantLocal("2012-05-31 17:51:01")).before(1, year()).toString()).isEqualTo("2011-05-31 17:51:01.000");

		String date = from(instantLocal("2012-05-31 17:51:01")).before(2, years()).before(3, months()).before(1, hour()).before(10, minutes()).before(20,
			seconds()).toString();
		assertThat(date).isEqualTo("2010-02-28 16:40:41.000"); // -ST>WT -1-
	}


	@Test
	public void testNext() {
		assertEquals("2013-01-01 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).next(year()).toString());
		assertEquals("2012-06-01 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).next(month()).toString());
		assertEquals("2012-06-07 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(week()).toString());
		assertEquals("2012-06-01 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).next(day()).toString());
		assertEquals("2012-05-31 18:00:00.000", from(instantLocal("2012-05-31 17:51:01")).next(hour()).toString());
		assertEquals("2012-05-31 17:52:00.000", from(instantLocal("2012-05-31 17:51:01")).next(minute()).toString());
		assertEquals("2012-05-31 17:51:02.000", from(instantLocal("2012-05-31 17:51:01")).next(second()).toString());
	}


	@Test
	public void testPrevious() {
		assertEquals("2011-01-01 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(year()).toString());
		assertEquals("2012-04-01 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(month()).toString());
		assertEquals("2012-05-24 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(week()).toString());
		assertEquals("2012-05-30 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(day()).toString());
		assertEquals("2012-05-31 16:00:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(hour()).toString());
		assertEquals("2012-05-31 17:50:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(minute()).toString());
		assertEquals("2012-05-31 17:51:00.000", from(instantLocal("2012-05-31 17:51:01")).previous(second()).toString());
	}


	@Test
	public void testAt() {
		assertEquals("2012-05-31 12:00:00.000", from(instantLocal("2012-05-31 17:51:01")).atNoon().toString());
		assertEquals("2012-05-31 00:00:00.000", from(instantLocal("2012-05-31 17:51:01")).atMidnight().toString());
		assertEquals("2012-05-31 22:13:09.000", from(instantLocal("2012-05-31 17:51:01")).at("22:13:09").toString());
		assertEquals("2012-05-31 22:13:09.000", from(instantLocal("2012-05-31 17:51:01")).at(22, 13, 9).toString());
	}


	@Test
	public void truncate() throws Exception {
		Instant baseline = instantLocal("2014-09-17 14:15:16").plusMillis(123L).plusNanos(321L);
		assertThat(from(baseline).truncate(millis()).toInstant()).isEqualTo(instantLocal("2014-09-17 14:15:16").plusMillis(123L));
		assertThat(from(baseline).truncate(seconds()).toInstant()).isEqualTo(instantLocal("2014-09-17 14:15:16"));
		assertThat(from(baseline).truncate(minutes()).toInstant()).isEqualTo(instantLocal("2014-09-17 14:15:00"));
		assertThat(from(baseline).truncate(hours()).toInstant()).isEqualTo(instantLocal("2014-09-17 14:00:00"));
		assertThat(from(baseline).truncate(days()).toInstant()).isEqualTo(instantLocal("2014-09-17 00:00:00"));
		assertThat(from(baseline).truncate(months()).toInstant()).isEqualTo(instantLocal("2014-09-01 00:00:00"));
		assertThat(from(baseline).truncate(years()).toInstant()).isEqualTo(instantLocal("2014-01-01 00:00:00"));

		assertThat(from(baseline).zone(ZONE_UTC).truncate(millis()).toInstant()).isEqualTo(instantUtc("2014-09-17T12:15:16Z").plusMillis(123L));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(seconds()).toInstant()).isEqualTo(instantUtc("2014-09-17T12:15:16Z"));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(minutes()).toInstant()).isEqualTo(instantUtc("2014-09-17T12:15:00Z"));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(hours()).toInstant()).isEqualTo(instantUtc("2014-09-17T12:00:00Z"));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(days()).toInstant()).isEqualTo(instantUtc("2014-09-17T00:00:00Z"));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(months()).toInstant()).isEqualTo(instantUtc("2014-09-01T00:00:00Z"));
		assertThat(from(baseline).zone(ZONE_UTC).truncate(years()).toInstant()).isEqualTo(instantUtc("2014-01-01T00:00:00Z"));

		assertThat(from(baseline).toInstant().getNano()).isEqualTo(123000321);
		assertThat(from(baseline).truncate(millis()).toInstant().getNano()).isEqualTo(123000000);
	}


	@Test
	public void testNextWeekday() {
		assertEquals("2012-06-07 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(thursday()).toString());
		assertEquals("2012-06-01 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(friday()).toString());
		assertEquals("2012-06-02 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(saturday()).toString());
		assertEquals("2012-06-03 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(sunday()).toString());
		assertEquals("2012-06-04 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(monday()).toString());
		assertEquals("2012-06-05 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(tuesday()).toString());
		assertEquals("2012-06-06 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).next(wednesday()).toString());
	}


	@Test
	public void testPreviousWeekday() {
		assertEquals("2012-05-24 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(thursday()).toString());
		assertEquals("2012-05-25 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(friday()).toString());
		assertEquals("2012-05-26 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(saturday()).toString());
		assertEquals("2012-05-27 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(sunday()).toString());
		assertEquals("2012-05-28 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(monday()).toString());
		assertEquals("2012-05-29 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(tuesday()).toString());
		assertEquals("2012-05-30 17:51:01.000", from(instantLocal("2012-05-31 17:51:01")).previous(wednesday()).toString());
	}


	@Test
	public void fromDuskTillDawnDate() throws Exception {
		long expected = Durations.dehumanize("12h").longValue();
		Date dusk = dateLocal("2012-11-01 18:00:00");
		Date dawn = dateLocal("2012-11-02 06:00:00");
		assertEquals(expected, from(dusk).till(dawn));
	}


	@Test
	public void fromDuskTillDawnInstant() throws Exception {
		long expected = Durations.dehumanize("12h").longValue();
		Instant dusk = instantLocal("2012-11-01 18:00:00");
		Instant dawn = instantLocal("2012-11-02 06:00:00");
		assertEquals(expected, from(dusk).till(dawn));
	}


	@Test
	public void toIso8601Utc() throws Exception {
		Date dateLocal = dateLocal("2013-07-04 09:36:11");
		assertEquals("2013-07-04T07:36:11.000Z", from(dateLocal).toStringIsoUtc());
		Date dateLocalMs = dateLocal("2013-07-04 09:36:11.123");
		assertEquals("2013-07-04T07:36:11.123Z", from(dateLocalMs).toStringIsoUtc());
	}


	@Test
	public void testLocalUtc() throws Exception {
		assertEquals(dateUtc("2013-07-04T07:36:11Z"), dateLocal("2013-07-04 09:36:11"));
		assertEquals(instantUtc("2013-07-04T07:36:11Z"), instantLocal("2013-07-04 09:36:11"));
	}


	@Test
	public void invalidInput() throws Exception {
		assertThat(instantLocal("abc")).isNull();
		assertThat(dateLocal("abc")).isNull();
	}


	@Test
	public void dateEpoch() throws Exception {
		assertThat(dateUtc("1970-01-01T00:00:00Z")).isEqualTo(date(0L));
		assertEquals(date(1372930571000L), dateUtc("2013-07-04T09:36:11Z"));
	}


	@Test
	public void instantEpoch() throws Exception {
		Instant epochInstant = Instant.ofEpochSecond(0L);
		Instant epochDateInstant = new Date(0L).toInstant();
		assertThat(epochInstant).isEqualTo(epochDateInstant);

		assertThat(new Date(0L).getTime()).isEqualTo(new Date(0L).toInstant().toEpochMilli());
		// would fail UTC vs. GMT - assertThat(new Date(0L)).isEqualTo(Instant.ofEpochMilli(0L));
		assertThat(instantUtc("1970-01-01T00:00:00Z")).isEqualTo(instant(0L));
		assertThat(instantUtc("1970-01-01T00:00:00Z")).isEqualTo(date(0L).toInstant());
		assertEquals(instant(1372930571000L), instantUtc("2013-07-04T09:36:11Z"));
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


	@Test
	public void toZdt() throws Exception {
		assertThat(from(dateUtc("2014-12-31T13:37:00Z")).toZdt()).isEqualTo(ZonedDateTime.of(2014, 12, 31, 13, 37, 0, 0, ZONE_UTC));
		assertThat(from(dateUtc("2014-12-31T13:37:00Z")).toZdt(ZONE_UTC)).isEqualTo(ZonedDateTime.of(2014, 12, 31, 13, 37, 0, 0, ZONE_UTC));
	}

}
