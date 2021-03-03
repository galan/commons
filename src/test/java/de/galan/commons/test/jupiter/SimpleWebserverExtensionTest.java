package de.galan.commons.test.jupiter;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import de.galan.commons.logging.Say;
import de.galan.commons.net.flux.HttpClientException;


/**
 * CUT SimpleWebserverExtension
 */
public class SimpleWebserverExtensionTest {

	@RegisterExtension
	SimpleWebserverExtension ext = new SimpleWebserverExtension();

	@Test
	public void tst() throws HttpClientException, IOException {
		ext.startServer((req, resp) -> {
			Say.info("WIP");
			//	resp.setStatus(Status.OK);
			//	try {
			//		resp.getPrintStream().write("hello".getBytes(UTF_8));
			//	}
			//	catch (IOException ex) {
			//		Say.warn("Unspecified error from daniel", ex);
			//	}
		});
		//Response response = Flux.request("http://localhost:12345").get();
		//String stream = response.getStreamAsString();
		//Say.info("Stream: {}", stream);
	}

}
