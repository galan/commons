package de.galan.commons.func.supplier;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * CUT UuidSupplier
 */
public class UuidSupplierTest {

	@Test
	public void checkUuidPattern() throws Exception {
		UuidSupplier supplier = new UuidSupplier();
		String uuid = supplier.get();
		assertThat(uuid).matches("[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}");
	}


	@Test
	public void notSame() throws Exception {
		UuidSupplier supplier = new UuidSupplier();
		assertThat(supplier.get()).isNotEqualTo(supplier.get());
	}

}
