package de.galan.commons.time;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.Test;


/**
 * CUT Durations
 */
public class DurationsTest {

	@Test
	public void timeAgo() {
		assertEquals("2d 7h 26m 48s", Durations.timeAgo(instantLocal("2012-01-22 02:33:12"), instantLocal("2012-01-24 10:00:00")));
		assertEquals("65d 16h 46m 8s", Durations.timeAgo(instantLocal("2011-11-19 17:14:12"), instantLocal("2012-01-24 10:00:20")));
		assertEquals("15363d 10h 20s", Durations.timeAgo(instant(0L), instantUtc("2012-01-24T10:00:20Z")));
		assertEquals("", Durations.timeAgo(null, instantLocal("2012-01-24 10:00:20")));
		assertEquals("", Durations.timeAgo(new Date(System.currentTimeMillis() + 1000L))); // in the future
	}


	@Test
	public void timeLeft() {
		ApplicationClock.setLocal("2012-11-20 09:15:00");
		//Dates.setDateSupplier(new FixedDateSupplier("2012-11-20 09:15:00"));
		assertEquals("1m 10s", Durations.timeLeft(instantLocal("2012-11-20 09:16:10")));
		assertEquals("", Durations.timeLeft(instantLocal("2012-11-20 09:13:33"))); // in the past
	}


	@Test
	public void humanize() {
		assertEquals("0ms", Durations.humanize(0L));
		assertEquals("1s200ms", Durations.humanize(1200L));
		assertEquals("1m10s", Durations.humanize(Duration.ofSeconds(70L)));
	}


	@Test
	public void dehumanize() {
		assertThat(Durations.dehumanize("0")).isEqualTo(0L);
		assertNull(Durations.dehumanize(""));
		assertNull(Durations.dehumanize(null));
		assertNull(Durations.dehumanize("4h30m200"));

		assertThat(Durations.dehumanize("1200")).isEqualTo(1200L);
		assertThat(Durations.dehumanize("1200ms")).isEqualTo(1200L);
		assertThat(Durations.dehumanize("1s200ms")).isEqualTo(1200L);
		assertThat(Durations.dehumanize("1s 200ms")).isEqualTo(1200L);
		assertThat(Durations.dehumanize(" 1s    200ms ")).isEqualTo(1200L);

		assertThat(Durations.dehumanize("4h30m200ms")).isEqualTo(16200200L);
		assertThat(Durations.dehumanize("4h 30m 200ms")).isEqualTo(16200200L);
		assertThat(Durations.dehumanize("4h  30m200ms")).isEqualTo(16200200L);

		assertThat(Durations.dehumanize("2w3d4h5m6s7ms")).isEqualTo(1483506007L);

		assertThat(Durations.dehumanize("4m")).isEqualTo(240000L);
	}


	@Test
	public void dehumanizeTimeCache() {
		assertThat(Durations.dehumanize("2w3d4h5m6s7ms")).isEqualTo(1483506007L);
		assertThat(Durations.dehumanize("2w3d4h5m6s7ms")).isEqualTo(1483506007L);
	}


	@Test
	public void toDuration() throws Exception {
		assertThat(Durations.toDuration(null).toMillis()).isEqualTo(0L);
		assertThat(Durations.toDuration("").toMillis()).isEqualTo(0L);

		assertThat(Durations.toDuration("4h30m200ms").toMillis()).isEqualTo(16200200L);
		assertThat(Durations.toDuration("4h30m200ms").toString()).isEqualTo("PT4H30M0.2S");
	}


	@Test
	public void fromDuration() throws Exception {
		Duration d = Duration.ofSeconds(1234567);
		assertThat(Durations.fromDuration(null)).isNull();
		assertThat(Durations.fromDuration(d)).isEqualTo("14d 6h 56m 7s");
	}

}
