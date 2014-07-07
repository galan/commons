package de.galan.commons.io.file;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;
import de.galan.commons.util.Generics;


/**
 * File- and DirectoryListener can be registered at the observer to recevive notifications if they change. Uses
 * internally the WatchService.
 *
 * @author daniel
 */
public class FilesystemObserver {

	private final static Logger LOG = Logr.get();
	private final static AtomicInteger THREAD_COUNTER = new AtomicInteger();

	WatchService watcher;
	Map<WatchKey, FileListener> keysFileListener;
	Map<WatchKey, DirectoryListener> keysDirectoryListener;
	Thread watcherThread;


	public FilesystemObserver() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			keysFileListener = new HashMap<>();
			keysDirectoryListener = new HashMap<>();
			watcherThread = new Thread(() -> startThread(), "FilesystemObserver-" + THREAD_COUNTER.getAndIncrement());
			start();
		}
		catch (IOException ex) {
			throw new RuntimeException("Failed initializing FilesystemObserver", ex);
		}
	}


	public void start() {
		watcherThread.start();
	}


	public void stop() {
		watcherThread.interrupt();
		while(watcherThread.isAlive()) {
			Sleeper.sleep(5L);
		}
	}


	protected void startThread() {
		while(true) {
			WatchKey key = null;
			try {
				key = watcher.take();
			}
			catch (InterruptedException iex) {
				LOG.info("interrupted");
				return;
			}

			FileListener fileListener = keysFileListener.get(key);
			DirectoryListener directoryListener = keysDirectoryListener.get(key);
			if (fileListener != null || directoryListener != null) {
				for (WatchEvent<?> event: key.pollEvents()) {
					Kind<?> kind = event.kind();

					// TODO - what is OVERFLOW/how to handle best?
					if (kind == OVERFLOW) {
						continue;
					}

					// Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = Generics.cast(event);
					Path name = ev.context();

					//Path child = path.resolve(name);

					if (fileListener != null && StringUtils.equals(name.getFileName().toString(), fileListener.getFile().getName())) {
						LOG.info("file: " + fileListener.getFile().getAbsolutePath());
						if (kind == ENTRY_CREATE) {
							fileListener.notifyFileCreated();
						}
						else if (kind == ENTRY_MODIFY) {
							fileListener.notifyFileChanged();
						}
						else if (kind == ENTRY_DELETE) {
							fileListener.notifyFileDeleted();
						}
					}
					if (directoryListener != null) {
						File file = new File(directoryListener.getDirectory(), name.getFileName().toString());
						LOG.info("dir: " + directoryListener.getDirectory().getAbsolutePath() + ", file:" + file.getName());
						if (kind == ENTRY_CREATE) {
							directoryListener.notifyFileCreated(file);
						}
						else if (kind == ENTRY_MODIFY) {
							directoryListener.notifyFileChanged(file);
						}
						else if (kind == ENTRY_DELETE) {
							directoryListener.notifyFileDeleted(file);
						}
						// if directory is created, and watching recursively, then
						// register it and its sub-directories
						/*
						if (recursive && (kind == ENTRY_CREATE)) {
							try {
								if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
									registerAll(child);
								}
							}
							catch (IOException x) {
								// ignore to keep sample readbale
							}
						}
						 */
					}
				}
			}
			else {
				LOG.info("WatchKey not recognized");
				continue;
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keysDirectoryListener.remove(key);

				// all directories are inaccessible
				if (keysDirectoryListener.isEmpty()) {
					break;
				}
			}
		}

	}


	public void registerFileListener(FileListener fileListener) throws IOException {
		Preconditions.checkNotNull(fileListener, "FileListener null");
		Preconditions.checkNotNull(fileListener.getFile(), "FileListener is missing file");
		Preconditions.checkArgument(!fileListener.getFile().isDirectory(), "File in FileListener is assumed to be a file, not a directory");
		File file = fileListener.getFile();
		File directory = file.getParentFile();
		Path path = Paths.get(directory.toURI());
		WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keysFileListener.put(key, fileListener);
	}


	public void registerDirectoryListener(DirectoryListener directoryListener) throws IOException {
		Preconditions.checkNotNull(directoryListener, "DirectoryListener null");
		Preconditions.checkNotNull(directoryListener.getDirectory(), "DirectoryListener is missing directory");
		Preconditions.checkArgument(directoryListener.getDirectory().isDirectory(), "Directory in DirectoryListener is assumed to be a directory, not a file");
		File directory = directoryListener.getDirectory();
		Path path = Paths.get(directory.toURI());
		WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keysDirectoryListener.put(key, directoryListener);
	}

}
