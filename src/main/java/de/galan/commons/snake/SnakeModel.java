package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.snake.access.PropertyAccess;
import de.galan.commons.snake.util.SnakeListener;
import de.galan.commons.time.HumanTime;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class SnakeModel {

	private static final Logger LOG = Logr.get();

	private PropertyAccess properties;


	public SnakeModel(PropertyAccess properties) {
		setPropertyAccess(properties);
	}


	public void setPropertyAccess(PropertyAccess properties) {
		this.properties = properties;
	}


	protected PropertyAccess getPropertyAccess() {
		return properties;
	}


	public Map<String, String> getProperties() {
		return getPropertyAccess().getProperties();
	}


	public Set<String> getProperties(String prefix) {
		return getPropertyAccess().getProperties(prefix);
	}


	public boolean isSet(String name) {
		return isNotBlank(getPropertyAccess().get(name));
	}


	public String get(String name) {
		return getPropertyAccess().get(name);
	}


	public String get(String name, String fallback) {
		return getPropertyAccess().get(name, fallback);
	}


	public void set(String name, String value) {
		getPropertyAccess().set(name, value);
	}


	protected void setObject(String name, Object value) {
		getPropertyAccess().setObject(name, value);
	}


	public void remove(String name) {
		getPropertyAccess().remove(name);
	}


	public Integer getInt(String name) {
		Integer result = null;
		try {
			String value = get(name);
			result = isNotBlank(value) ? Integer.valueOf(value) : null;
		}
		catch (NumberFormatException nfex) {
			LOG.error("Property could not be converted to Integer: {}", name);
		}
		return result;
	}


	public Integer getInt(String name, Integer fallback) {
		Integer value = getInt(name);
		return (value != null) ? value : fallback;
	}


	public void setInt(String name, Integer value) {
		setObject(name, value);
	}


	public Double getDouble(String name) {
		Double result = null;
		try {
			String value = get(name);
			result = isNotBlank(value) ? Double.valueOf(value) : null;
		}
		catch (NumberFormatException nfex) {
			LOG.error("Property could not be converted to Double: {}", name);
		}
		return result;
	}


	public Double getDouble(String name, Double fallback) {
		Double value = getDouble(name);
		return (value != null) ? value : fallback;
	}


	public void setDouble(String name, Double value) {
		setObject(name, value);
	}


	public Long getLong(String name) {
		Long result = null;
		try {
			String value = get(name);
			result = isNotBlank(value) ? Long.valueOf(value) : null;
		}
		catch (NumberFormatException nfex) {
			LOG.error("Property could not be converted to Double: {}", name);
		}
		return result;
	}


	public Long getLong(String name, Long fallback) {
		Long value = getLong(name);
		return (value != null) ? value : fallback;
	}


	public void setLong(String name, Long value) {
		setObject(name, value);
	}


	public boolean getBool(String name) {
		return getBool(name, false);
	}


	public boolean getBool(String name, boolean fallback) {
		boolean result = false;
		String value = get(name);
		if (StringUtils.equals(value, "true")) {
			result = true;
		}
		else if (StringUtils.equals(value, "false")) {
			result = false;
		}
		else {
			result = fallback;
		}
		return result;
	}


	public void setBool(String name, boolean value) {
		setObject(name, BooleanUtils.toStringTrueFalse(value));
	}


	public Long getTime(String name) {
		return getTime(name, null);
	}


	public Long getTime(String name, String fallback) {
		String value = get(name);
		Long result = HumanTime.dehumanizeTime(value);
		if (result == null) {
			result = HumanTime.dehumanizeTime(fallback);
		}
		return result;
	}


	public void setMs(String name, String value) {
		set(name, value);
	}


	public void setMs(String name, Long value) {
		set(name, value == null ? null : HumanTime.humanizeTime(value));
	}


	public String getInstance() {
		return getPropertyAccess().getInstance();
	}


	public String getDirectoryBase() {
		return getPropertyAccess().getDirectoryBase();
	}


	public String getDirectoryInstance() {
		return getPropertyAccess().getDirectoryInstance();
	}


	public String getDirectoryStorage() {
		return getPropertyAccess().getDirectoryStorage();
	}


	public String getDirectoryStorage(String subdirectory) {
		return getPropertyAccess().getDirectoryStorage() + subdirectory + getPropertyAccess().system().getFileSeparator();
	}


	public String getDirectoryLog() {
		return getPropertyAccess().getDirectoryLog();
	}


	public String getDirectoryConfiguration() {
		return getPropertyAccess().getDirectoryConfiguration();
	}


	public String getDirectoryTemp() {
		return getPropertyAccess().getDirectoryTemp();
	}


	public String getDirectoryScript() {
		return getPropertyAccess().getDirectoryScript();
	}


	/** Logs an overview of the properties */
	public void printProperties() {
		getPropertyAccess().printProperties();
	}


	public void addListener(SnakeListener listener) {
		getPropertyAccess().addListener(listener);
	}


	public void removeListener(SnakeListener listener) {
		getPropertyAccess().removeListener(listener);
	}

}
