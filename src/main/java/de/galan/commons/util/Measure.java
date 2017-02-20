package de.galan.commons.util;

import static de.galan.commons.util.Sugar.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

import com.google.common.base.Preconditions;

import de.galan.commons.logging.Say;
import de.galan.commons.time.Durations;


/**
 * Measures invokation of Runnables/Callables in milliseconds. Average of n-invocations can also be calculated (using
 * the '.every(n)' builder method). Thread-safe.
 */
public class Measure {

	private Integer builderIteratons;
	private String builderWhat;

	private int counter = 0;
	private long[] times;


	/** Builds a new Measure object to measure invokations of runnables/callables. */
	public static Measure measure(String what) {
		return new Measure(what, null);
	}


	Measure(String what, Integer iterations) {
		builderWhat = what;
		builderIteratons = iterations;

		Preconditions.checkState(iterations == null || iterations >= 0, "iterations must be non-negative or null");
		builderIteratons = (iterations == null || iterations <= 1L) ? null : iterations;
		if (iterations != null) {
			times = new long[optional(iterations).orElse(0)];
		}
	}


	/** If provided, the average on a single invokation will be calculated/logged after every n-iterations. */
	public Measure every(Integer iterations) {
		return new Measure(builderWhat, iterations);
	}


	/** Measure the invokation time of the given Runnable. */
	public void run(Runnable runnable) {
		long start = System.currentTimeMillis();
		runnable.run();
		invoked(System.currentTimeMillis() - start);
	}


	/** Measure the invokation time of the given Callable. */
	public <V> V call(Callable<V> callable) throws Exception {
		long start = System.currentTimeMillis();
		V result = callable.call();
		invoked(System.currentTimeMillis() - start);
		return result;
	}


	private void invoked(long millis) {
		if (builderIteratons == null) {
			log(millis);
		}
		else {
			synchronized (this) {
				times[counter++] = millis;
				if (counter >= builderIteratons) {
					double average = 1d * LongStream.of(times).sum() / counter;
					log(average);
					counter = 0;
					times = new long[builderIteratons];
				}
			}
		}
	}


	void log(double average) {
		Say.info("Measured {what} took on average ~{time}ms/{iterations}", builderWhat, String.format("%.2f", average), counter);
	}


	void log(long millis) {
		Say.info("Measured {what} took {time}ms, that's {}", builderWhat, millis, Durations.humanize(millis, SPACE));
	}

}
