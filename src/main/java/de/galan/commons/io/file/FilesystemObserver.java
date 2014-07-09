package de.galan.commons.io.file;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
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
			watcherThread = createWatcherThread();
			start();
		}
		catch (IOException ex) {
			throw new RuntimeException("Failed initializing FilesystemObserver", ex);
		}
	}


	protected Thread createWatcherThread() {
		return new Thread(() -> processEvents(), "FilesystemObserver-" + THREAD_COUNTER.getAndIncrement());
	}


	public void start() {
		if (!watcherThread.isAlive()) {
			watcherThread = createWatcherThread();
			watcherThread.start();
		}
	}


	public void stop() {
		if (watcherThread.isAlive()) {
			watcherThread.interrupt();
			while(watcherThread.isAlive()) {
				Sleeper.sleep(5L);
			}
		}
	}


	protected void processEvents() {
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
					if (kind == OVERFLOW) {
						continue;
					}

					// Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = Generics.cast(event);
					Path path = ev.context();
					//Path child = path.resolve(name);

					notifyFileListener(fileListener, kind, path);
					notifyDirectoryListener(directoryListener, kind, path);
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


	protected void notifyDirectoryListener(DirectoryListener directoryListener, Kind<?> kind, Path path) {
		if (directoryListener != null) {
			File file = new File(directoryListener.getDirectory(), path.getFileName().toString());
			LOG.info("dir: " + directoryListener.getDirectory().getAbsolutePath() + ", file:" + file.getName());
			if (kind == ENTRY_CREATE) {
				directoryListener.notifyFileCreated(file);
				if (directoryListener.isListeningRecursive() && file.isDirectory()) {
					try {
						registerDirectoryListener(directoryListener, Paths.get(file.toURI()));
					}
					catch (IOException ex) {
						LOG.info("Unable to register new subdirectory '" + directoryListener.getDirectory().getAbsolutePath() + "/"
								+ file.getName() + "'");
					}
				}
			}
			else if (kind == ENTRY_MODIFY) {
				directoryListener.notifyFileChanged(file);
			}
			else if (kind == ENTRY_DELETE) {
				directoryListener.notifyFileDeleted(file);
			}
		}
	}


	protected void notifyFileListener(FileListener fileListener, Kind<?> kind, Path path) {
		if (fileListener != null && StringUtils.equals(path.getFileName().toString(), fileListener.getFile().getName())) {
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
		registerDirectoryListener(directoryListener, path);
	}


	protected void registerDirectoryListener(DirectoryListener directoryListener, Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				RecursiveDirectoryListener listener = new RecursiveDirectoryListener(directoryListener, dir.toFile());
				registerDirectoryListenerInternal(listener);
				return listener.isListeningRecursive() ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
			}
		});
	}


	protected void registerDirectoryListenerInternal(DirectoryListener directoryListener) throws IOException {
		File directory = directoryListener.getDirectory();
		Path path = Paths.get(directory.toURI());
		WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keysDirectoryListener.put(key, directoryListener);
	}

}


/** Encapsulates the parent DirectoryListener in order to pass correct File references to it. */
class RecursiveDirectoryListener extends AbstractDirectoryListener {

	private DirectoryListener parent;


	public RecursiveDirectoryListener(DirectoryListener parent, File directory) {
		super(directory);
		this.parent = parent;
		setListeningRecursive(parent.isListeningRecursive());
	}


	@Override
	public void notifyFileChanged(File file) {
		parent.notifyFileChanged(file);
	}


	@Override
	public void notifyFileDeleted(File file) {
		parent.notifyFileDeleted(file);
	}


	@Override
	public void notifyFileCreated(File file) {
		parent.notifyFileCreated(file);
	}

}
