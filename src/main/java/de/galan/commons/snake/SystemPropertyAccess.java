package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Properties are backed by the SystemProperties, prefixed with "snake.".
 *
 * @author daniel
 */
public class SystemPropertyAccess extends AbstractPropertyAccess {

	public void clear() {
		for (String key: System.getProperties().stringPropertyNames()) {
			if (startsWithAny(key, "snake.", "snake:")) {
				System.clearProperty(key);
			}
		}
	}


	@Override
	public Map<String, String> getProperties() {
		Map<String, String> result = new HashMap<>();
		for (String key: System.getProperties().stringPropertyNames()) {
			String name = denormalizeName(key);
			if (isNotBlank(name)) {
				result.put(name, System.getProperty(key));
			}
		}
		return result;
	}


	@Override
	public String get(String name) {
		return System.getProperty(normalizeName(name));
	}


	@Override
	public void set(String name, String value) {
		String valueOld = get(name);
		if (value == null) {
			System.clearProperty(normalizeName(name));
			notifyPropertyRemoved(name, valueOld);
		}
		else {
			System.setProperty(normalizeName(name), value);
			notifyPropertyModified(name, valueOld, value);
		}
	}


	protected String normalizeName(String name) {
		return startsWith(name, "snake.") ? name : "snake:" + name;
	}


	protected String denormalizeName(String name) {
		if (startsWith(name, "snake.")) {
			return name;
		}
		else if (startsWith(name, "snake:")) {
			return removeStart(name, "snake:");
		}
		return null;
	}

}
