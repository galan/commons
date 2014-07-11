package de.galan.commons.io.file;

import java.io.File;


/**
 * Standard-implementation where notifications of interest can be overriden.
 *
 * @author daniel
 */
public abstract class AbstractDirectoryListener implements DirectoryListener {

	private File directory;
	private boolean recursive;


	public AbstractDirectoryListener(File directory) {
		this.directory = directory;
	}


	public AbstractDirectoryListener(String directory) {
		this(new File(directory));
	}


	public void setListeningRecursive(boolean recursive) {
		this.recursive = recursive;
	}


	@Override
	public Boolean isListeningRecursive() {
		return recursive;
	}


	@Override
	public File getDirectory() {
		return directory;
	}

}
