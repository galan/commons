package de.galan.commons.bootstrap;

/**
 * Initializes and starts the application
 *
 * @author daniel
 */
public class Launcher {

	public void launch() {
		// TODO create directory
		new SnakeBootstrap().initialize();
		// TODO Snake, Guice, dirs, Application, JMX, Logging.

	}

}
