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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	Map<WatchKey, Set<ProxyFileListener>> keysFileListener;
	Map<WatchKey, ProxyDirectoryListener> keysDirectoryListener;
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

			Set<ProxyFileListener> fileListeners = keysFileListener.get(key);
			ProxyDirectoryListener directoryListener = keysDirectoryListener.get(key);
			if (directoryListener != null || (fileListeners != null && !fileListeners.isEmpty())) {
				List<WatchEvent<?>> events = key.pollEvents();
				for (WatchEvent<?> event: events) {
					Kind<?> kind = event.kind();
					if (kind == OVERFLOW) {
						continue;
					}

					// Context for directory entry event is the file name of entry
					WatchEvent<Path> ev = Generics.cast(event);
					Path path = ev.context();
					//Path child = path.resolve(name);

					if (fileListeners != null) {
						fileListeners.forEach(fileListener -> notifyFileListener(fileListener, kind, path));
					}
					notifyDirectoryListener(directoryListener, kind, path);
				}
			}
			else {
				LOG.info("WatchKey not recognized");
				continue;
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			Sleeper.sleep(100L); // avoids duplicates - http://stackoverflow.com/questions/16133590/why-does-watchservice-generate-so-many-operations
			if (!valid) {
				keysDirectoryListener.remove(key);

				// all directories are inaccessible
				if (keysDirectoryListener.isEmpty()) {
					break;
				}
			}
		}
	}


	protected void notifyDirectoryListener(ProxyDirectoryListener proxyDirectoryListener, Kind<?> kind, Path path) {
		if (proxyDirectoryListener != null) {
			File file = new File(proxyDirectoryListener.getDirectory(), path.getFileName().toString());
			LOG.info("dir: " + proxyDirectoryListener.getDirectory().getAbsolutePath() + ", file:" + file.getName());
			if (kind == ENTRY_CREATE) {
				proxyDirectoryListener.notifyFileCreated(file);
				if (proxyDirectoryListener.isRecursive() && file.isDirectory()) {
					try {
						registerDirectoryListener(proxyDirectoryListener, Paths.get(file.toURI()), proxyDirectoryListener.isRecursive());
					}
					catch (IOException ex) {
						LOG.info("Unable to register new subdirectory '" + proxyDirectoryListener.getDirectory().getAbsolutePath() + "/" + file.getName() + "'");
					}
				}
			}
			else if (kind == ENTRY_MODIFY) {
				proxyDirectoryListener.notifyFileChanged(file);
			}
			else if (kind == ENTRY_DELETE) {
				proxyDirectoryListener.notifyFileDeleted(file);
			}
		}
	}


	protected void notifyFileListener(ProxyFileListener fileListener, Kind<?> kind, Path path) {
		if (fileListener != null && StringUtils.equals(path.getFileName().toString(), fileListener.getFile().getName())) {
			LOG.info("kind: " + kind + ", file: " + fileListener.getFile().getAbsolutePath()); // TODO change to debug later
			if (kind == ENTRY_CREATE) {
				fileListener.notifyFileCreated(fileListener.getFile());
			}
			else if (kind == ENTRY_MODIFY) {
				fileListener.notifyFileChanged(fileListener.getFile());
			}
			else if (kind == ENTRY_DELETE) {
				fileListener.notifyFileDeleted(fileListener.getFile());
			}
		}
	}


	public void registerFileListener(FileListener fileListener, File file) throws IOException {
		Preconditions.checkNotNull(fileListener, "FileListener is null");
		Preconditions.checkNotNull(file, "File is null");
		Preconditions.checkArgument(!file.isDirectory(), "File in assumed to be a file, not a directory");
		File directory = file.getParentFile();
		Path path = Paths.get(directory.toURI());
		WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keysFileListener.computeIfAbsent(key, k -> new HashSet<>()).add(new ProxyFileListener(fileListener, file));
		//keysFileListener.put(key, new ProxyFileListener(fileListener, file));
	}


	public void registerDirectoryListener(FileListener directoryListener, File directory, boolean recursive) throws IOException {
		Preconditions.checkNotNull(directoryListener, "DirectoryListener is null");
		Preconditions.checkNotNull(directory, "Directory is null");
		Preconditions.checkArgument(directory.isDirectory(), "Directory is assumed to be a directory, not a file");
		Path path = Paths.get(directory.toURI());
		registerDirectoryListener(directoryListener, path, recursive);
	}


	protected void registerDirectoryListener(FileListener directoryListener, Path directory, boolean recursive) throws IOException {
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				ProxyDirectoryListener listener = new ProxyDirectoryListener(directoryListener, dir.toFile(), recursive);
				registerDirectoryListenerInternal(listener);
				return recursive ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
			}
		});
	}


	protected void registerDirectoryListenerInternal(ProxyDirectoryListener proxyDirectoryListener) throws IOException {
		File directory = proxyDirectoryListener.getDirectory();
		Path path = Paths.get(directory.toURI());
		WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keysDirectoryListener.put(key, proxyDirectoryListener);
	}

}


/** Encapsulates the parent FileListener in order to pass correct File references to it. */
class ProxyFileListener implements FileListener {

	private FileListener listener;
	private File watchingFile;


	ProxyFileListener(FileListener listener, File file) {
		this.listener = listener;
		watchingFile = file;
	}


	public FileListener getListener() {
		return listener;
	}


	public File getFile() {
		return watchingFile;
	}


	@Override
	public void notifyFileCreated(File file) {
		listener.notifyFileCreated(file);
	}


	@Override
	public void notifyFileChanged(File file) {
		listener.notifyFileChanged(file);
	}


	@Override
	public void notifyFileDeleted(File file) {
		listener.notifyFileDeleted(file);
	}

}


/** Encapsulates the parent FileListener in order to pass correct File references to it. */
class ProxyDirectoryListener implements FileListener {

	private FileListener listener;
	private boolean recursive;
	private File directory;


	public ProxyDirectoryListener(FileListener listener, File directory, boolean recursive) {
		this.listener = listener;
		this.directory = directory;
		this.recursive = recursive;
	}


	public boolean isRecursive() {
		return recursive;
	}


	public File getDirectory() {
		return directory;
	}


	@Override
	public void notifyFileCreated(File file) {
		listener.notifyFileCreated(file);
	}


	@Override
	public void notifyFileChanged(File file) {
		listener.notifyFileChanged(file);
	}


	@Override
	public void notifyFileDeleted(File file) {
		listener.notifyFileDeleted(file);
	}

}
