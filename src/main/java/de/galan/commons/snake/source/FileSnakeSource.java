package de.galan.commons.snake.source;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;

import de.galan.commons.io.file.FilesystemObserver;
import de.galan.commons.snake.RefreshPropertiesListener;
import de.galan.commons.snake.access.PropertyAccess;
import de.galan.commons.util.JvmUtils;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class FileSnakeSource implements SnakeSource {

	@Override
	public void initialize(PropertyAccess access, boolean observe) {
		String snakeFile = access.get("snake.file");
		String snakeFileOverwrite = access.get("snake.file.overwrite");

		// changes do not disappear after removed daniel.properties -> RefreshPropertiesListener.refresh()
		File file = determineSnakeFile(access, snakeFile); // Necessary system property file
		File fileOverwrite = determineSnakeOverwriteFile(access, snakeFileOverwrite); // Optional override file

		FilePropertySupplier fileSupplier = new FilePropertySupplier(file, fileOverwrite);
		RefreshPropertiesListener listener = new RefreshPropertiesListener(access, fileSupplier);
		listener.refresh();

		if (observe) {
			try {
				FilesystemObserver observer = new FilesystemObserver();
				observer.registerFileListener(listener, file);
				observer.registerFileListener(listener, fileOverwrite);
			}
			catch (Exception ex) {
				terminateApplication(access, "Failed observing files");
			}
		}
	}


	protected File determineSnakeFile(PropertyAccess access, String filename) {
		File file = new File(trimToEmpty(filename));
		if (isEmpty(filename)) {
			String instanceFilename = access.getDirectoryConfiguration() + "instance.properties";
			File fileInstance = new File(instanceFilename);
			if (fileInstance.exists()) {
				file = fileInstance;
			}
			else {
				terminateApplication(access, "No file for Snake properties is given.");
			}
		}
		if (!file.exists() || file.isDirectory()) {
			terminateApplication(access, "The given Snake properties-file could not be found (" + filename + ")");
		}
		return file;
	}


	protected File determineSnakeOverwriteFile(PropertyAccess access, String filename) {
		File result = null;
		if (isNotEmpty(filename)) {
			File file = new File(filename);
			//if (file.exists()) {
			result = file;
			//}
		}
		else {
			String instanceFilename = access.getDirectoryConfiguration() + access.system().getUserName() + ".properties";
			File fileInstance = new File(instanceFilename);
			//if (fileInstance.exists()) {
			result = fileInstance;
			//}
		}
		return result;
	}


	protected void terminateApplication(PropertyAccess access, String message) {
		JvmUtils.terminate().message(message).now();
	}

}
