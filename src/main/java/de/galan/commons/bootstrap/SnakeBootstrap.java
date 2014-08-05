package de.galan.commons.bootstrap;

import java.util.function.Supplier;

import de.galan.commons.snake.Snake;
import de.galan.commons.snake.SnakeModel;
import de.galan.commons.snake.access.MapPropertyAccess;
import de.galan.commons.snake.access.PropertyAccess;
import de.galan.commons.snake.source.FileSnakeSource;
import de.galan.commons.snake.source.SnakeSource;


/**
 * Initializes the properties.
 *
 * @author daniel
 */
public class SnakeBootstrap {

	private Supplier<PropertyAccess> supplierPropertyAccess = MapPropertyAccess::new;
	private Supplier<SnakeSource> supplierInit = FileSnakeSource::new;

	private boolean flagObserve = true;


	public SnakeModel initialize() {
		PropertyAccess access = supplierPropertyAccess.get();
		// Set snake system properties to PropertyAccess (in case they use the default values and are not defined),
		// so other configuration files can substitute them (such as log4j.xml)
		access.set(PropertyAccess.KEY_SNAKE_BASE, access.getDirectoryBase());
		access.set(PropertyAccess.KEY_SNAKE_INSTANCE, access.getInstance());

		new Log4jBootstrap().initialize(access.getDirectoryConfiguration());
		supplierInit.get().initialize(access, flagObserve);

		SnakeModel model = new SnakeModel(access);
		Snake.setSnakeModel(model);
		return model;
	}


	public SnakeBootstrap withAccess(Supplier<PropertyAccess> propertyAccessSupplier) {
		supplierPropertyAccess = propertyAccessSupplier;
		return this;
	}


	public SnakeBootstrap withInit(Supplier<SnakeSource> initSupplier) {
		supplierInit = initSupplier;
		return this;
	}


	public SnakeBootstrap observe(boolean observe) {
		flagObserve = observe;
		return this;
	}

}
