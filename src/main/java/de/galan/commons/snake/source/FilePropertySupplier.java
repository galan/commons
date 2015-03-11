package de.galan.commons.snake.source;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;

import de.galan.commons.logging.Logr;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class FilePropertySupplier implements Supplier<Map<String, String>> {

	private static final Logger LOG = Logr.get();

	private File file;
	private File overwrite;


	public FilePropertySupplier(File file, File overwrite) {
		this.file = file;
		this.overwrite = overwrite;
	}


	@Override
	public Map<String, String> get() {
		Map<String, String> result = new HashMap<>();
		Properties properties = new Properties();
		properties.putAll(loadProperties(file));
		properties.putAll(loadProperties(overwrite));
		for (Object key: properties.keySet()) {
			result.put((String)key, properties.getProperty((String)key));
		}
		return result;
	}


	protected Properties loadProperties(File propertyFile) {
		Properties result = new Properties();
		if (propertyFile != null && propertyFile.exists()) {
			try (FileReader reader = new FileReader(propertyFile)) {
				result.load(reader);
			}
			catch (Exception ex) {
				LOG.error("Snake properties file {} could not be read", ex, propertyFile.getName());
			}
		}
		return result;
	}

}
