package de.galan.commons.bootstrap;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;

import de.galan.commons.snake.Snake;
import de.galan.commons.snake.SnakeModel;
import de.galan.commons.snake.access.DefaultPropertyAccessSupplier;
import de.galan.commons.snake.access.PropertyAccess;
import de.galan.commons.snake.source.FileSnakeSource;
import de.galan.commons.snake.source.SnakeSource;
import de.galan.commons.util.JvmUtils;


/**
 * Initializes the properties.
 *
 * @author daniel
 */
public class SnakeBootstrap {

	private Supplier<PropertyAccess> supplierPropertyAccess = new DefaultPropertyAccessSupplier();
	private Supplier<SnakeSource> supplierSource = FileSnakeSource::new;

	private boolean flagObserve = true;


	//private Consumer<PropertyAccess> afterConsumer;

	public SnakeModel initialize() {
		PropertyAccess access = supplierPropertyAccess.get();
		//afterConsumer.accept(access);

		createDirectories(access);
		new Log4jBootstrap().initialize(access.getDirectoryConfiguration());

		supplierSource.get().initialize(access, flagObserve);

		SnakeModel model = new SnakeModel(access);
		Snake.setSnakeModel(model);
		return model;
	}


	protected void createDirectories(PropertyAccess access) {
		File instance = new File(access.getDirectoryInstance());
		if (!instance.isDirectory() || !instance.exists() || !instance.canExecute() || !instance.canWrite()) {
			JvmUtils.terminate().threaded(false).message("Instance-directory not available to write {" + instance + "}").now();
		}
		createDirectory(access.getDirectoryConfiguration());
		createDirectory(access.getDirectoryLog());
		createDirectory(access.getDirectoryScript());
		createDirectory(access.getDirectoryStorage());
		createDirectory(access.getDirectoryTemp());
	}


	protected void createDirectory(String directory) {
		try {
			FileUtils.forceMkdir(new File(directory));
		}
		catch (IOException ex) {
			JvmUtils.terminate().threaded(false).message("Unable to create directory {" + directory + "}").now();
		}
	}


	public SnakeBootstrap withAccess(Supplier<PropertyAccess> propertyAccessSupplier) {
		supplierPropertyAccess = propertyAccessSupplier;
		return this;
	}


	public SnakeBootstrap withSource(Supplier<SnakeSource> sourceSupplier) {
		supplierSource = sourceSupplier;
		return this;
	}


	public SnakeBootstrap afterDefaults(Consumer<PropertyAccess> after) {
		//afterConsumer = after;
		return this;
	}


	public SnakeBootstrap observe(boolean observe) {
		flagObserve = observe;
		return this;
	}

}
