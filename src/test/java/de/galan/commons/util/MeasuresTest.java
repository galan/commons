package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.Callable;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import de.galan.commons.time.Sleeper;
import de.galan.commons.util.Measures;
import de.galan.commons.util.Measures.Measure;


/**
 * CUT Measures
 */
public class MeasuresTest {

	@Test
	public void singleRuns() throws Exception {
		Measure measure = spy(Measures.measure("dummy"));
		measure.run(() -> Sleeper.sleep(10L));
		checkLog(measure, 0L, 100L);
		reset(measure);
		measure.run(() -> Sleeper.sleep(200L));
		checkLog(measure, 199L, 240L);
	}


	@Test
	public void singleCalls() throws Exception {
		Measure measure = spy(Measures.measure("dummy"));
		String returnValue1 = measure.call(callableString(10L, "abc"));
		assertThat(returnValue1).isEqualTo("abc");
		checkLog(measure, 0L, 100L);

		reset(measure);
		String returnValue2 = measure.call(callableString(200L, "def"));
		assertThat(returnValue2).isEqualTo("def");
		checkLog(measure, 199L, 240L);
	}


	private Callable<String> callableString(long sleep, String result) {
		return () -> {
			Sleeper.sleep(sleep);
			return result;
		};
	}


	@Test
	public void everyRuns() throws Exception {
		Measure measure = spy(Measures.measure("dummy").every(4));
		measure.run(() -> Sleeper.sleep(10L));
		measure.run(() -> Sleeper.sleep(10L));
		measure.run(() -> Sleeper.sleep(10L));
		verify(measure, times(0)).log(anyLong());
		verify(measure, times(0)).log(anyDouble());
		measure.run(() -> Sleeper.sleep(1L));
		checkLog(measure, 6d, 9d);

		reset(measure);
		measure.run(() -> Sleeper.sleep(100L));
		measure.run(() -> Sleeper.sleep(100L));
		measure.run(() -> Sleeper.sleep(100L));
		verify(measure, times(0)).log(anyLong());
		verify(measure, times(0)).log(anyDouble());
		measure.run(() -> Sleeper.sleep(1L));
		checkLog(measure, 60d, 95d);
	}


	@Test
	public void everyCalls() throws Exception {
		Measure measure = spy(Measures.measure("dummy").every(4));
		measure.call(callableString(10L, "abc"));
		measure.call(callableString(10L, "abc"));
		measure.call(callableString(10L, "abc"));
		verify(measure, times(0)).log(anyLong());
		verify(measure, times(0)).log(anyDouble());
		measure.call(callableString(1L, "abc"));
		checkLog(measure, 6d, 9d);

		reset(measure);
		measure.call(callableString(100L, "abc"));
		measure.call(callableString(100L, "abc"));
		measure.call(callableString(100L, "abc"));
		verify(measure, times(0)).log(anyLong());
		verify(measure, times(0)).log(anyDouble());
		measure.call(callableString(1L, "abc"));
		checkLog(measure, 60d, 95d);
	}


	@Test
	public void everyMix() throws Exception {
		Measure measure = spy(Measures.measure("dummy").every(4));
		measure.call(callableString(10L, "abc"));
		measure.run(() -> Sleeper.sleep(10L));
		measure.call(callableString(10L, "abc"));
		verify(measure, times(0)).log(anyLong());
		verify(measure, times(0)).log(anyDouble());
		measure.run(() -> Sleeper.sleep(1L));
		checkLog(measure, 6d, 9d);
	}


	private void checkLog(Measure measure, Long start, Long end) {
		verify(measure, times(0)).log(anyDouble());
		ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
		verify(measure, times(1)).log(argumentCaptor.capture());
		assertThat(argumentCaptor.getValue()).isBetween(start, end);
	}


	private void checkLog(Measure measure, Double start, Double end) {
		verify(measure, times(0)).log(anyLong());
		ArgumentCaptor<Double> argumentCaptor = ArgumentCaptor.forClass(Double.class);
		verify(measure, times(1)).log(argumentCaptor.capture());
		assertThat(argumentCaptor.getValue()).isBetween(start, end);
	}

}
