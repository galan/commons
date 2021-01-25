package de.galan.commons.io.file;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.galan.commons.test.Tests;
import de.galan.commons.time.Sleeper;


/**
 * CUT FileSupport
 */
public class FileSupportTest {

	File dir = Tests.getTestDirectory(true);
	File file = new File(dir, "test-touch.txt");

	@Test
	void touchNew() throws Exception {
		assertThat(file).doesNotExist();
		FileSupport.touch(file);
		assertThat(file).exists().isEmpty();
	}


	@Test
	void touchExisting() throws Exception {
		assertThat(file).doesNotExist();
		FileSupport.touch(file);
		long lastModified = file.lastModified();
		Sleeper.sleep("100ms");
		assertThat(file).exists().isEmpty();

		FileSupport.touch(file);
		assertThat(file).exists().isEmpty();
		assertThat(lastModified).isLessThan(file.lastModified());
	}


	@Test
	void delete() throws Exception {
		File dirSub1 = new File(dir, "subdir1");
		File fileFirst = new File(dirSub1, "first.txt");
		File fileSecond = new File(dirSub1, "second.txt");
		File dirSub2 = new File(dirSub1, "deeper");
		File fileSub = new File(dirSub2, "deepfile.txt");
		dirSub2.mkdirs();
		FileSupport.touch(fileFirst);
		FileSupport.touch(fileSecond);
		FileSupport.touch(fileSub);

		assertThat(fileSub).exists();

		FileSupport.deleteFile(dirSub1);
		assertThat(fileSub).doesNotExist();
		assertThat(dirSub2).doesNotExist();
		assertThat(dirSub1).doesNotExist();
	}

}
