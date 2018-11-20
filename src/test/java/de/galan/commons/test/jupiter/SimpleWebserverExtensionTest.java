package de.galan.commons.test.jupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import de.galan.commons.logging.Say;


/**
 * CUT SimpleWebserverExtension
 */
public class SimpleWebserverExtensionTest {

	@RegisterExtension
	SimpleWebserverExtension ext = new SimpleWebserverExtension();


	@Test
	public void tst() {
		ext.startServer((req, resp) -> {
			Say.info("WIP");
		});
	}

}
