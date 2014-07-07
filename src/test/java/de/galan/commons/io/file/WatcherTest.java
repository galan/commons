package de.galan.commons.io.file;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.Sleeper;


/**
 * manuel test, TODO delete me later
 *
 * @author daniel
 */
public class WatcherTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		FilesystemObserver ob1 = new FilesystemObserver();

		FileListener file1 = new AbstractFileListener("/home/daniel/temp/dummy.txt") {};
		ob1.registerFileListener(file1);

		DirectoryListener dir1 = new AbstractDirectoryListener("/home/daniel/temp/") {

			@Override
			public Boolean isListeningRecursive() {
				return true;
			}

		};
		ob1.registerDirectoryListener(dir1);

		//FilesystemObserver ob2 = new FilesystemObserver();

		//DirectoryListener dir2 = new AbstractDirectoryListener("/home/daniel/temp/subdir") {};
		//ob2.registerDirectoryListener(dir2);

		Sleeper.sleep("2d");
	}

}
