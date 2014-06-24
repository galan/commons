package de.galan.commons.func.supplier;

import java.util.Random;

import com.google.common.base.Supplier;


/**
 * Generate simple random alphanumeric strings.
 *
 * @author daniel
 */
public class RandomAlphaNumericSupplier implements Supplier<String> {

	private static final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	private final int length;


	public RandomAlphaNumericSupplier(int length) {
		this.length = (length >= 0) ? length : 0;
	}


	@Override
	public String get() {
		StringBuilder result = new StringBuilder(length);
		if (length >= 0) {
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				result.append(ALPHA_NUMERIC.charAt(random.nextInt(ALPHA_NUMERIC.length())));
			}
		}
		return result.toString();
	}

}
