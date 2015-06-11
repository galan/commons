package de.galan.commons.func.supplier;

import java.math.BigDecimal;
import java.util.function.Supplier;


/**
 * Provides sequences of BigDecimal values, starting with startValue or zero. Code is synchronized, so might be improved
 * (see http://stackoverflow.com/q/8567596/363281).
 *
 * @author galan
 */
public class BigDecimalSequenceSupplier implements Supplier<BigDecimal> {

	private BigDecimal counter = BigDecimal.ZERO;


	public BigDecimalSequenceSupplier() {
		this(null);
	}


	public BigDecimalSequenceSupplier(BigDecimal startValue) {
		if (startValue != null) {
			counter = startValue;
		}
	}


	@Override
	public synchronized BigDecimal get() {
		BigDecimal result = counter;
		counter = counter.add(new BigDecimal(1));
		return result;
	}

}
