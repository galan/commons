package de.galan.commons.io.file;

import java.io.File;


/**
 * Standard-implementation where notifications of interest can be overriden.
 *
 * @author daniel
 */
public abstract class AbstractFileListener implements FileListener {

	private File file;


	public AbstractFileListener(File file) {
		this.file = file;
	}


	public AbstractFileListener(String file) {
		this(new File(file));
	}


	@Override
	public File getFile() {
		return file;
	}

}
