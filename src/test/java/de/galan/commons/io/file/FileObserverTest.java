package de.galan.commons.io.file;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;
import de.galan.commons.time.Sleeper;


/**
 * CUT FileObserver
 *
 * @author daniel
 */
public class FileObserverTest extends AbstractTestParent {

	File dirTemp;
	private FilesystemObserver observer;


	@Before
	public void before() throws IOException {
		dirTemp = new File(System.getProperty("java.io.tmpdir"), "de.galan.commons");
		FileUtils.deleteDirectory(dirTemp);
		dirTemp.mkdirs();
		observer = new FilesystemObserver();
	}


	@After
	public void after() throws IOException {
		FileUtils.deleteDirectory(dirTemp);
	}


	@Test
	public void stop() throws Exception {
		assertThat(observer.watcherThread.isAlive()).isTrue();
		observer.stop();
		assertThat(observer.watcherThread.isAlive()).isFalse();
		observer.stop();
		assertThat(observer.watcherThread.isAlive()).isFalse();
		observer.start();
		assertThat(observer.watcherThread.isAlive()).isTrue();
	}


	@Test
	public void monitorFile() throws Exception {
		File fileMonitor = new File(dirTemp, "dummy.txt");
		File fileOther = new File(dirTemp, "other.txt");
		StubListener listener = new StubListener();
		observer.registerFileListener(listener, fileMonitor);
		// create
		touch(fileMonitor);
		assertListener(listener, fileMonitor, fileMonitor, null);
		// modify
		touch(fileMonitor);
		assertListener(listener, null, fileMonitor, null);
		// other files do not trigger listener
		touch(fileOther);
		assertListener(listener, null, null, null);
		// delete
		delete(fileMonitor);
		assertListener(listener, null, null, fileMonitor);
		// recreation is catched
		touch(fileMonitor);
		assertListener(listener, fileMonitor, fileMonitor, null);
	}


	@Test
	public void monitorMultipleFiles() throws Exception {
		File fileFirst = new File(dirTemp, "first.txt");
		File fileSecond = new File(dirTemp, "second.txt");
		StubListener listener = new StubListener();
		observer.registerFileListener(listener, fileFirst);
		observer.registerFileListener(listener, fileSecond);
		// create first
		touch(fileFirst);
		assertListener(listener, fileFirst, fileFirst, null);
		// create second
		touch(fileSecond);
		assertListener(listener, fileSecond, fileSecond, null);
		// modify first
		touch(fileFirst);
		assertListener(listener, null, fileFirst, null);
		// modify second
		touch(fileSecond);
		assertListener(listener, null, fileSecond, null);
		// delete first
		delete(fileFirst);
		assertListener(listener, null, null, fileFirst);
		// delete second
		delete(fileSecond);
		assertListener(listener, null, null, fileSecond);
	}


	@Test
	public void monitorDirectory() throws Exception {
		monitorDirectory(false);
	}


	@Test
	public void monitorDirectoryRecursivly() throws Exception {
		monitorDirectory(true);
	}


	protected void monitorDirectory(boolean recursive) throws IOException {
		File dirMonitor = new File(dirTemp, "subdir");
		File fileFirst = new File(dirMonitor, "first.txt");
		File fileSecond = new File(dirMonitor, "second.txt");
		File dirSub = new File(dirMonitor, "deeper");
		File fileSub = new File(dirSub, "deepfile.txt");
		dirMonitor.mkdirs();
		StubListener listener = new StubListener();
		observer.registerDirectoryListener(listener, dirMonitor, recursive);
		// create and modify files in directory
		touch(fileFirst);
		assertListener(listener, fileFirst, fileFirst, null);
		touch(fileFirst);
		assertListener(listener, null, fileFirst, null);
		touch(fileSecond);
		assertListener(listener, fileSecond, fileSecond, null);
		delete(fileSecond);
		assertListener(listener, null, null, fileSecond);
		// create subdirectory
		mkdir(dirSub);
		assertListener(listener, dirSub, null, null);
		// file in subdirectory
		touch(fileSub);
		assertListener(listener, recursive ? fileSub : null, recursive ? fileSub : null, null);
		// delete subdirectory
		delete(dirSub);
		assertListener(listener, null, null, dirSub);
	}


	protected void mkdir(File dir) {
		assertThat(dir.exists()).isFalse();
		assertThat(dir.getParentFile().exists()).isTrue();
		dir.mkdir();
		Sleeper.sleep(20L);
	}


	protected void touch(File file) throws IOException {
		FileUtils.touch(file);
		Sleeper.sleep(20L);
	}


	protected void delete(File file) {
		assertThat(FileUtils.deleteQuietly(file)).isTrue();
		Sleeper.sleep(20L);
	}


	protected void assertListener(StubListener listener, File expectedLastCreated, File expectedLastChanged, File expectedLastDeleted) {
		assertThat(listener.lastCreated).isEqualTo(expectedLastCreated);
		assertThat(listener.lastChanged).isEqualTo(expectedLastChanged);
		assertThat(listener.lastDeleted).isEqualTo(expectedLastDeleted);
		listener.lastCreated = null;
		listener.lastChanged = null;
		listener.lastDeleted = null;
	}

}


/** Counts the notifications */
class StubListener implements FileListener {

	long countCreated = 0L;
	long countChanged = 0L;
	long countDeleted = 0L;
	File lastCreated;
	File lastChanged;
	File lastDeleted;


	@Override
	public void notifyFileChanged(File file) {
		countChanged++;
		lastChanged = file;
	}


	@Override
	public void notifyFileDeleted(File file) {
		countDeleted++;
		lastDeleted = file;
	}


	@Override
	public void notifyFileCreated(File file) {
		countCreated++;
		lastCreated = file;
	}

}
