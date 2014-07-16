package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.HumanTime;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class SnakeModel {

	private static final Logger LOG = Logr.get();

	private PropertyAccess properties;

	private List<SnakeListener> listeners;


	public SnakeModel(PropertyAccess properties) {
		setPropertyAccess(properties);
		listeners = new ArrayList<>();
	}


	public void setPropertyAccess(PropertyAccess properties) {
		this.properties = properties;
	}


	protected PropertyAccess getPropertyAccess() {
		return properties;
	}


	public SystemModel system() {
		return getPropertyAccess().getSystem();
	}


	/*
	public Properties getProperties() {
		return getPropertyAccess().getProperties();
	}


	public Set<String> getProperties(String prefix) {
		return getPropertyAccess().getProperties(prefix);
	}
	 */

	public boolean isSet(String name) {
		return isNotBlank(get(name));
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


	/*
	public void setDirect(String name, String value) {
		getPropertyAccess().setDirect(name, value);
	}
	 */
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


	public Long getMs(String name) {
		return getMs(name, null);
	}


	public Long getMs(String name, String fallback) {
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


	public InstanceModel instance() {
		return getPropertyAccess().getInstance();
	}


	public String getInstance() {
		return instance().getInstance();
	}


	public String getDirectoryBase() {
		return instance().getDirectoryBase();
	}


	public String getDirectoryInstance() {
		return instance().getDirectoryInstance();
	}


	public String getDirectoryStorage() {
		return instance().getDirectoryStorage();
	}


	public String getDirectoryStorage(String subdirectory) {
		return instance().getDirectoryStorage(subdirectory);
	}


	public String getDirectoryLog() {
		return instance().getDirectoryLog();
	}


	public String getDirectoryConfiguration() {
		return instance().getDirectoryConfiguration();
	}


	public String getDirectoryTemp() {
		return instance().getDirectoryTemp();
	}


	public String getDirectoryScript() {
		return instance().getDirectoryScript();
	}


	public void printProperties(boolean includeSystemProperties) {
		//TODO getPropertyAccess().printProperties(includeSystemProperties);
	}


	protected List<SnakeListener> getListeners() {
		return listeners;
	}


	public void addListener(SnakeListener listener) {
		getListeners().add(listener);
	}


	public void removeListener(SnakeListener listener) {
		getListeners().remove(listener);
	}


	public void notifyListenerRefreshed() {
		//getPropertyAccess().notifyListenerRefreshed();
	}


	protected void notifyPropertyAdded() {

	}

}
