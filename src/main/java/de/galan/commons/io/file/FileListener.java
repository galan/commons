package de.galan.commons.io.file;

import java.io.File;


/**
 * Listener that notifies about changes to a single file.
 *
 * @author daniel
 */
public interface FileListener {

	public File getFile();


	public void notifyFileCreated();


	public void notifyFileChanged();


	public void notifyFileDeleted();

}
