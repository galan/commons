package de.galan.commons.io.file;

import java.io.File;


/**
 * Listener that notifies about changes to files/directories in a directory.
 *
 * @author daniel
 */
public interface DirectoryListener {

	public File getDirectory();


	public Boolean isListeningRecursive();


	public void notifyFileCreated(File file);


	public void notifyFileChanged(File file);


	public void notifyFileDeleted(File file);

}
