package de.galan.commons.io.file;

import static de.galan.commons.time.Instants.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.Objects;

import com.google.common.base.Preconditions;

import de.galan.commons.logging.Say;


/**
 * Supportive file operations
 */
public class FileSupport {

	/**
	 * Deletes a File. If it is a directory it will be performed recursivly.
	 *
	 * @throws IOException
	 */
	public static void deleteFile(File file) throws IOException {
		if (file == null || !file.exists()) {
			return;
		}
		Files.walk(file.toPath())
			.map(Path::toFile)
			.sorted(Comparator.reverseOrder())
			.forEach(File::delete);
	}


	/**
	 * Deletes a File. If it is a directory it will be performed recursivly. Exceptions will be swallowed, only a
	 * logmessage will be printed.
	 *
	 * @return true if successfull
	 */
	public static boolean deleteFileQuiet(File file) {
		try {
			deleteFile(file);
		}
		catch (IOException ex) {
			Say.warn("Unable to delete directory", ex);
			return false;
		}
		return true;
	}


	public static void touch(final File file) throws IOException {
		Objects.requireNonNull(file, "fileis null");
		touch(file.toPath());
	}


	public static void touch(final Path path) throws IOException {
		Objects.requireNonNull(path, "path is null");
		Preconditions.checkArgument(!path.toFile().isDirectory(), "path is a directory");
		if (Files.exists(path)) {
			Files.setLastModifiedTime(path, FileTime.from(now()));
		}
		else {
			Files.createFile(path);
		}
	}

}
