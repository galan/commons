package de.galan.commons.snake;

import org.slf4j.Logger;

import de.galan.commons.logging.Logr;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class InstanceModel {

	private static final Logger LOG = Logr.get();

	private PropertyAccess properties;


	public InstanceModel(PropertyAccess properties) {
		this.properties = properties;
	}


	protected PropertyAccess getPropertyAccess() {
		return properties;
	}


	public String getInstance() {
		return getPropertyAccess().get("snake.instance", "instance");
	}


	public String getDirectoryBase() {
		return getPropertyAccess().get("snake.base", getPropertyAccess().getSystem().getUserHome());
	}


	/** Convenience */
	protected String getFs() {
		return getPropertyAccess().getSystem().getFileSeparator();
	}


	public String getDirectoryInstance() {
		return getDirectoryBase() + getFs() + getInstance() + getFs();
	}


	public String getDirectoryStorage() {
		return getDirectoryInstance() + "storage" + getFs();
	}


	public String getDirectoryStorage(String subdirectory) {
		return getDirectoryStorage() + subdirectory + getFs();
	}


	public String getDirectoryLog() {
		return getDirectoryInstance() + "log" + getFs();
	}


	public String getDirectoryConfiguration() {
		return getDirectoryInstance() + "configuration" + getFs();
	}


	public String getDirectoryTemp() {
		return getDirectoryInstance() + "temp" + getFs();
	}


	public String getDirectoryScript() {
		return getDirectoryInstance() + "script" + getFs();
	}

}
