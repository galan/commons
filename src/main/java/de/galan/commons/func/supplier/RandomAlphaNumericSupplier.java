package de.galan.commons.func.supplier;

import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;


/**
 * Provides simple random alphanumeric strings.
 */
public class RandomAlphaNumericSupplier implements Supplier<String> {

	private final int length;


	public RandomAlphaNumericSupplier(int length) {
		this.length = (length >= 0) ? length : 0;
	}


	@Override
	public String get() {
		return RandomStringUtils.randomAlphanumeric(length);
	}

}
