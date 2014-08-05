package de.galan.commons.snake.access;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Map-backed implementation.
 *
 * @author daniel
 */
public class MapPropertyAccess extends AbstractPropertyAccess {

	Map<String, String> properties = Collections.emptyMap();


	@Override
	public Map<String, String> getProperties() {
		return properties;
	}


	@Override
	public void set(String name, String value) {
		String valueOld = get(name);
		Map<String, String> modifiableMap = new HashMap<String, String>(properties);
		if (value == null) {
			modifiableMap.remove(name);
			//getProperties().remove(name);
			properties = Collections.unmodifiableMap(modifiableMap);
			notifyPropertyRemoved(name, valueOld);
		}
		else {
			modifiableMap.put(name, value);
			properties = Collections.unmodifiableMap(modifiableMap);
			//getProperties().put(name, value);
			notifyPropertyModified(name, valueOld, value);
		}
	}

}
