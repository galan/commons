package de.galan.commons.bootstrap;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.net.MalformedURLException;


/**
 * Initializes the log4j xml configuration
 *
 * @author daniel
 */
public class Log4jBootstrap {

	private static final String LOG4J_PROPERTY = "log4j.configuration";


	public void initialize(String log4jXmlDirectory) {
		if (isBlank(System.getProperty(LOG4J_PROPERTY))) {
			String log4jPath = log4jXmlDirectory + "log4j.xml";
			File log4jFile = new File(log4jPath);
			if (log4jFile.exists()) {
				String log4jUrl;
				try {
					log4jUrl = log4jFile.toURI().toURL().toString();
				}
				catch (MalformedURLException ex) {
					log4jUrl = "file:" + log4jPath;
				}
				System.setProperty(LOG4J_PROPERTY, log4jUrl);
			}
		}
	}

}
