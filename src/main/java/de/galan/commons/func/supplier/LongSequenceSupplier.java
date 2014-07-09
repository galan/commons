package de.galan.commons.func.supplier;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;


/**
 * Provides sequences of long values, starting with startValue or zero.
 *
 * @author daniel
 */
public class LongSequenceSupplier implements Supplier<Long> {

	private AtomicLong counter;


	public LongSequenceSupplier() {
		this(null);
	}


	public LongSequenceSupplier(Long startValue) {
		counter = (startValue != null) ? new AtomicLong(startValue.longValue()) : new AtomicLong();
	}


	@Override
	public Long get() {
		return counter.getAndIncrement();
	}

}
