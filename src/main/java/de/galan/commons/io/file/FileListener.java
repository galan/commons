package de.galan.commons.io.file;

import java.io.File;


/**
 * Listener that notifies about changes to a file or directory.
 */
public interface FileListener {

	default void notifyFileCreated(File file) {
		// can be overriden
	}


	default void notifyFileChanged(File file) {
		// can be overriden
	}


	default void notifyFileDeleted(File file) {
		// can be overriden
	}

}
