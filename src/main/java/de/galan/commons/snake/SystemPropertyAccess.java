package de.galan.commons.snake;

/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class SystemPropertyAccess extends AbstractPropertyAccess {

	@Override
	public String get(String name) {
		return System.getProperty("snake." + name);
	}


	@Override
	public void set(String name, String value) {
		if (value == null) {
			System.clearProperty("snake." + name);
			//TODO notify removed
		}
		else {
			System.setProperty("snake." + name, value);
			//TODO notify added/updated
		}
	}

}
