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
		StubFileListener listener = new StubFileListener();
		observer.registerFileListener(listener, fileMonitor);
		// create
		touch(fileMonitor);
		assertFileListener(listener, fileMonitor, fileMonitor, null);
		// modify
		touch(fileMonitor);
		assertFileListener(listener, null, fileMonitor, null);
		// other files do not trigger listener
		touch(fileOther);
		assertFileListener(listener, null, null, null);
		// delete
		delete(fileMonitor);
		assertFileListener(listener, null, null, fileMonitor);
		// recreation is catched
		touch(fileMonitor);
		assertFileListener(listener, fileMonitor, fileMonitor, null);
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
		StubDirectoryListener listener = new StubDirectoryListener();
		observer.registerDirectoryListener(listener, dirMonitor, recursive);
		// create and modify files in directory
		touch(fileFirst);
		assertDirectoryListener(listener, fileFirst, fileFirst, null);
		touch(fileFirst);
		assertDirectoryListener(listener, null, fileFirst, null);
		touch(fileSecond);
		assertDirectoryListener(listener, fileSecond, fileSecond, null);
		delete(fileSecond);
		assertDirectoryListener(listener, null, null, fileSecond);
		// create subdirectory
		mkdir(dirSub);
		assertDirectoryListener(listener, dirSub, null, null);
		// file in subdirectory
		touch(fileSub);
		assertDirectoryListener(listener, recursive ? fileSub : null, recursive ? fileSub : null, null);
		// delete subdirectory
		delete(dirSub);
		assertDirectoryListener(listener, null, null, dirSub);
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


	protected void assertDirectoryListener(StubDirectoryListener listener, File expectedLastCreated, File expectedLastChanged, File expectedLastDeleted) {
		assertThat(listener.lastCreated).isEqualTo(expectedLastCreated);
		assertThat(listener.lastChanged).isEqualTo(expectedLastChanged);
		assertThat(listener.lastDeleted).isEqualTo(expectedLastDeleted);
		listener.lastCreated = null;
		listener.lastChanged = null;
		listener.lastDeleted = null;
	}


	protected void assertFileListener(StubFileListener listener, File expectedLastCreated, File expectedLastChanged, File expectedLastDeleted) {
		assertThat(listener.lastCreated).isEqualTo(expectedLastCreated);
		assertThat(listener.lastChanged).isEqualTo(expectedLastChanged);
		assertThat(listener.lastDeleted).isEqualTo(expectedLastDeleted);
		listener.lastCreated = null;
		listener.lastChanged = null;
		listener.lastDeleted = null;
	}

}


/** Counts the notifications */
class StubFileListener implements FileListener {

	long countFileCreated = 0L;
	long countFileChanged = 0L;
	long countFileDeleted = 0L;
	File lastCreated;
	File lastChanged;
	File lastDeleted;


	@Override
	public void notifyFileChanged(File file) {
		countFileChanged++;
		lastChanged = file;
	}


	@Override
	public void notifyFileDeleted(File file) {
		countFileDeleted++;
		lastDeleted = file;
	}


	@Override
	public void notifyFileCreated(File file) {
		countFileCreated++;
		lastCreated = file;
	}

}


/** Counts the notifications */
class StubDirectoryListener implements DirectoryListener {

	long countFileCreated = 0L;
	long countFileChanged = 0L;
	long countFileDeleted = 0L;
	File lastCreated;
	File lastChanged;
	File lastDeleted;


	@Override
	public void notifyFileChanged(File file) {
		countFileChanged++;
		lastChanged = file;
	}


	@Override
	public void notifyFileDeleted(File file) {
		countFileDeleted++;
		lastDeleted = file;
	}


	@Override
	public void notifyFileCreated(File file) {
		countFileCreated++;
		lastCreated = file;
	}

}
