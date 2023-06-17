package de.galan.commons.util;

import static de.galan.commons.util.Sugar.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

import com.google.common.base.Preconditions;

import de.galan.commons.func.exceptional.ExceptionalRunnable;
import de.galan.commons.logging.Say;
import de.galan.commons.time.Durations;


/**
 * Measures invocation of Runnables/Callables in milliseconds. Average of n-invocations can also be calculated (using
 * the '.every(n)' builder method). A total can be logged using finish() on the Measure object. Thread-safe.
 */
public class Measure {

	private Integer builderIterations;
	private String builderWhat;
	private boolean builderLogStartMessage;

	private int counter = 0;
	private long[] times;
	private Long startMeasure;
	private long sum = 0L;

	/** Builds a new Measure object to measure invocations of runnables/callables. */
	public static Measure measure(String what) {
		return new Measure(what, null, false);
	}


	Measure(String what, Integer iterations, boolean logStartMessage) {
		builderWhat = what;
		builderIterations = iterations;
		builderLogStartMessage = logStartMessage;

		Preconditions.checkState(iterations == null || iterations >= 0, "iterations must be non-negative or null");
		builderIterations = (iterations == null || iterations <= 1L) ? null : iterations;
		if (iterations != null) {
			times = new long[optional(iterations).orElse(0)];
		}
	}


	/** If provided, the average on a single invocation will be calculated/logged after every n-iterations. */
	public Measure every(Integer iterations) {
		return new Measure(builderWhat, iterations, builderLogStartMessage);
	}


	public Measure logStartMessage(boolean logStartMessage) {
		return new Measure(builderWhat, builderIterations, logStartMessage);
	}


	/** This method can be called to print the total amount of time taken until that point in time. */
	public Double finish() {
		if (counter == 0) {
			Say.info("No invocations are measured");
			return null;
		}
		else {
			double average = 1d * sum / counter;
			logFinished(sum, average);
			return average;
		}
	}


	/** Measure the invocation time of the given Runnable (without Exception). */
	public Measure runnable(Runnable runnable) {
		init();
		long startInvocation = System.currentTimeMillis();
		runnable.run();
		invoked(System.currentTimeMillis() - startInvocation);
		return this;
	}


	/** Measure the invocation time of the given ExceptionalRunnable. */
	public Measure run(ExceptionalRunnable runnable) throws Exception {
		init();
		long startInvocation = System.currentTimeMillis();
		runnable.run();
		invoked(System.currentTimeMillis() - startInvocation);
		return this;
	}


	/** Measure the invocation time of the given Callable. */
	public <V> V call(Callable<V> callable) throws Exception {
		init();
		long startInvocation = System.currentTimeMillis();
		V result = callable.call();
		invoked(System.currentTimeMillis() - startInvocation);
		return result;
	}


	private void init() {
		if (builderLogStartMessage && counter == 0) {
			logStart();
		}
		if (startMeasure == null) {
			startMeasure = System.currentTimeMillis();
		}
	}


	private void invoked(long millis) {
		sum += millis;
		if (builderIterations == null) {
			counter++;
			log(millis);
		}
		else {
			synchronized (this) {
				times[counter++ % builderIterations] = millis;
				if (counter % builderIterations == 0) {
					double average = 1d * LongStream.of(times).sum() / builderIterations;
					log(average);
					times = new long[builderIterations];
				}
			}
		}
	}


	void logStart() {
		Say.info("Starting measurement of {what}", builderWhat);
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
