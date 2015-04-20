package de.galan.commons.logging;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.logging.log4j.jul.LogManager;


/**
 * Configures the logger (Log4j2)
 *
 * @author daniel
 */
public class BootstrapLogger {

	public static final String LOG4J2_XML = "log4j2.xml";
	private static final String JUL_MANAGER = "java.util.logging.manager";
	private static final String LOG4J2_PROPERTY = "log4j.configurationFile";


	/**
	 * Sets the required properties to initialize the logging (log4j2).
	 *
	 * @param log4jConfigurationPath Absolute path to the log4j2.xml configuration file.
	 */
	public void initializeLogger(String log4jConfigurationPath) {
		if (isBlank(System.getProperty(LOG4J2_PROPERTY)) && !isBlank(log4jConfigurationPath)) {
			File log4jFile = new File(log4jConfigurationPath);
			if (log4jFile.exists()) {
				String log4jUrl;
				try {
					log4jUrl = log4jFile.toURI().toURL().toString();
				}
				catch (MalformedURLException ex) {
					log4jUrl = "file:" + log4jConfigurationPath;
				}
				System.setProperty(LOG4J2_PROPERTY, log4jUrl);
			}
		}
		// Install JUL bridge
		System.setProperty(JUL_MANAGER, LogManager.class.getName());
	}

}
