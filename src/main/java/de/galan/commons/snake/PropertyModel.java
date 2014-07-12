package de.galan.commons.snake;

/**
 * Methods required by the Snake
 *
 * @author daniel
 */
public interface PropertyModel {

	/**
	 * Returns the property with the given name.
	 *
	 * @param name The name of the property
	 * @return The value of the property, null if not set
	 */
	public String get(String name);


	/**
	 * Returns the property with the given name. If not existent, the fallback value will be used.
	 *
	 * @param name The name of the property
	 * @param fallback An alternative value, if the property is not set
	 * @return Value of the property or the fallback
	 */
	public String get(String name, String fallback);


	/**
	 * Sets the property
	 *
	 * @param name Name of the property
	 * @param value The value of the property
	 */
	public void set(String name, String value);

}
