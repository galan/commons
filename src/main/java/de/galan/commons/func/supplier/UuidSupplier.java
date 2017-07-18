package de.galan.commons.func.supplier;

import java.util.UUID;
import java.util.function.Supplier;


/**
 * Supplies random UUIDs.
 */
public class UuidSupplier implements Supplier<String> {

	@Override
	public String get() {
		return UUID.randomUUID().toString();
	}

}
