package de.galan.commons.test;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;


/**
 * For usage with the simpleframework webserver in the SimpleWebserverTestParent.
 *
 * @author galan
 */
public abstract class DummyContainer implements Container {

	@Override
	public void handle(Request req, Response resp) {
		try {
			serve(req, resp);
			resp.close();
		}
		catch (Exception ex) {
			throw new RuntimeException("failed in server: " + ex.getMessage());
		}
	}


	public abstract void serve(Request req, Response resp) throws Exception;

}
