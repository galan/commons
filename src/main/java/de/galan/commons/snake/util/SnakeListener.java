package de.galan.commons.snake.util;

/**
 * This interface notifies about changes in the backed propertymodel
 *
 * @author daniel
 */
public interface SnakeListener {

	/**
	 * A property has been added
	 *
	 * @param name Name of the property that has been added
	 * @param value The value the new property has
	 */
	default void propertyAdded(String name, String value) {
		// to be overriden if required
	}


	/**
	 * A property has been changed
	 *
	 * @param name Name of the property that has been modified
	 * @param valueOld Contains the value for the property before changed
	 * @param valueNew Contains the new value for the property
	 */
	default void propertyModified(String name, String valueOld, String valueNew) {
		// to be overriden if required
	}


	/**
	 * A property has been removed
	 *
	 * @param name Name of the property that has been removed
	 * @param value Contains the value for the property before removed
	 */
	default void propertyDeleted(String name, String value) {
		// to be overriden if required
	}


	/**
	 * Properties have been refreshed
	 */
	default public void propertiesRefreshed() {
		// to be overriden if required
	}

}
