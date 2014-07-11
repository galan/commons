package de.galan.commons.io.file;

import java.io.File;


/**
 * Listener that notifies about changes to a single file.
 *
 * @author daniel
 */
public interface FileListener {

	public File getFile();


	default void notifyFileCreated() {
		// can be overriden
	}


	default void notifyFileChanged() {
		// can be overriden
	}


	default void notifyFileDeleted() {
		// can be overriden
	}

}
