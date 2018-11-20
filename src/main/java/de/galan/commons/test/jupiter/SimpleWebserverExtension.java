package de.galan.commons.test.jupiter;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.SocketProcessor;
import org.simpleframework.transport.connect.SocketConnection;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;


/**
 * Starts/stops a simple webserver. Usage: use startServer(Container) and anonymously override DummyContainer.<br/>
 * In progress
 */
public class SimpleWebserverExtension implements BeforeEachCallback, AfterEachCallback {

	private static final Logger LOG = Logr.get();

	protected SocketProcessor server;
	private boolean stopped;

	private SocketConnection connection;


	public static SimpleWebserverBuilder builder() {
		//return new SimpleWebserverBuilder();
		return null;
	}

	/** TODO */
	public static class SimpleWebserverBuilder {

		@SuppressWarnings("unused")
		private Integer builderPort;
		private SimpleWebserverExtension ext;


		public SimpleWebserverBuilder(SimpleWebserverExtension ext) {
			this.ext = ext;
		}


		public SimpleWebserverBuilder port(Integer port) {
			builderPort = port;
			return this;
		}


		public void build() {
			//	ext.server = new SimpleWebserver();
		}

	}


	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		if (!stopped) {
			stopServer();
		}
		// TODO set default httpclient timeout to eg. 10m for easier debugging
	}


	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		stopServer();
	}


	public synchronized void startServer(Container container) {
		try {
			server = new ContainerSocketProcessor(container);
			connection = new SocketConnection(server);
			SocketAddress address = new InetSocketAddress(getPort());
			connection.connect(address);
			LOG.info("Server started");
		}
		catch (Exception ex) {
			fail("Server could not be started: " + ex.getMessage());
		}
	}


	protected int getPort() {
		return 12345;
	}


	public void startServerDelayed(final Container container, final String delay) {
		new Thread() {

			@Override
			public void run() {
				Sleeper.sleep(delay);
				synchronized (SimpleWebserverExtension.this) {
					if (!stopped) {
						startServer(container);
					}
				}
			}

		}.start();
	}


	public synchronized void stopServer() {
		LOG.info("Stopping server");
		try {
			if (server != null) {
				server.stop();
			}
			if (connection != null) {
				connection.close();
			}
			stopped = true;
		}
		catch (IOException ex) {
			fail("Server could not be stopped: " + ex.getMessage());
		}
	}

}
