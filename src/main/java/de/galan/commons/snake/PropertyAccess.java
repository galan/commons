package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;


/**
 * Methods required by the Snake
 *
 * @author daniel
 */
public interface PropertyAccess {

	/**
	 * Returns the property for the given name.
	 *
	 * @param name The name of the property
	 * @return The value of the property, null if not set
	 */
	public String get(String name);


	default String get(String name, String fallback) {
		String result = get(name);
		return isNotBlank(result) ? result : fallback;
	}


	/**
	 * Sets the property. When the value is null or blank it will be removed.
	 *
	 * @param name Name of the property
	 * @param value The value of the property
	 */
	public void set(String name, String value);


	default void setObject(String name, Object value) {
		set(name, value == null ? null : value.toString());
	}


	default void remove(String name) {
		set(name, null);
	}


	public SystemModel getSystem();


	public InstanceModel getInstance();

}
