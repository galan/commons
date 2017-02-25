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
 * the '.every(n)' builder method). A total can be logged using finish() on the Measure object. Thread-safe.
 */
public class Measure {

	private Integer builderIteratons;
	private String builderWhat;

	private int counter = 0;
	private long[] times;
	private Long startMeasure;


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


	/** This method can be called to print the total amount of time taken until that point in time. */
	public void finish() {
		if (startMeasure == null) {
			Say.info("No invokations are measured");
		}
		else {
			long total = System.currentTimeMillis() - startMeasure;
			double average = 1d * total / counter;
			logFinished(total, average);
		}
	}


	public Measure total(boolean total) {
		return new Measure(builderWhat, builderIteratons);
	}


	/** Measure the invokation time of the given Runnable (without Exception). */
	public void runnable(Runnable runnable) {
		initStartTotal();
		long startInvokation = System.currentTimeMillis();
		runnable.run();
		invoked(System.currentTimeMillis() - startInvokation);
	}


	/** Measure the invokation time of the given ExceptionalRunnable. */
	public void run(ExceptionalRunnable runnable) throws Exception {
		initStartTotal();
		long startInvokation = System.currentTimeMillis();
		runnable.run();
		invoked(System.currentTimeMillis() - startInvokation);
	}


	/** Measure the invokation time of the given Callable. */
	public <V> V call(Callable<V> callable) throws Exception {
		initStartTotal();
		long startInvokation = System.currentTimeMillis();
		V result = callable.call();
		invoked(System.currentTimeMillis() - startInvokation);
		return result;
	}


	private void initStartTotal() {
		if (startMeasure == null) {
			startMeasure = System.currentTimeMillis();
		}
	}


	private void invoked(long millis) {
		if (builderIteratons == null) {
			counter++;
			log(millis);
		}
		else {
			synchronized (this) {
				times[counter++ % builderIteratons] = millis;
				if (counter % builderIteratons == 0) {
					double average = 1d * LongStream.of(times).sum() / builderIteratons;
					log(average);
					times = new long[builderIteratons];
				}
			}
		}
	}


	void logFinished(long total, double average) {
		Say.info("Measured {what} finished in {total}ms, took on average ~{time}ms in {iterations} iterations",
			builderWhat, total, String.format("%.2f", average), counter);
	}


	void log(double average) {
		Say.info("Measured {what} took on average ~{time}ms after {iterations} iterations", builderWhat, String.format("%.2f", average), counter);
	}


	void log(long millis) {
		Say.info("Measured {what} took {time}ms, that's {}", builderWhat, millis, Durations.humanize(millis, SPACE));
	}

}
