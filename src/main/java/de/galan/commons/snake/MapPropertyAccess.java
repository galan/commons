package de.galan.commons.snake;

import java.util.Map;


/**
 * Map-backed implementation.
 *
 * @author daniel
 */
public class MapPropertyAccess extends AbstractPropertyAccess {

	Map<String, String> properties;


	@Override
	public Map<String, String> getProperties() {
		return properties;
	}


	@Override
	public void set(String name, String value) {
		String valueOld = get(name);
		if (value == null) {
			getProperties().remove(name);
			notifyPropertyRemoved(name, valueOld);
		}
		else {
			getProperties().put(name, value);
			notifyPropertyModified(name, valueOld, value);
		}
	}

}
