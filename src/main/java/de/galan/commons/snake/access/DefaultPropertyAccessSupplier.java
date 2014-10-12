package de.galan.commons.snake.access;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.function.Supplier;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class DefaultPropertyAccessSupplier implements Supplier<PropertyAccess> {

	@Override
	public PropertyAccess get() {
		// Set snake system properties to PropertyAccess (in case they use the default values and are not defined),
		// so other configuration files can substitute them (such as log4j.xml)
		PropertyAccess access = new MapPropertyAccess();
		initializeDefaults(access);
		return access;
	}

	protected void initializeDefaults(PropertyAccess access) {
		String base = defaultIfBlank(access.system().get(PropertyAccess.KEY_SNAKE_BASE), access.getDirectoryBase());
		access.set(PropertyAccess.KEY_SNAKE_BASE, base);
		String instance = defaultIfBlank(access.system().get(PropertyAccess.KEY_SNAKE_INSTANCE), access.getInstance());
		access.set(PropertyAccess.KEY_SNAKE_INSTANCE, instance);
	}

}
