package de.galan.commons.io.file;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT FileObserver
 *
 * @author daniel
 */
public class FileObserverTest extends AbstractTestParent {

	@Test
	public void testName() throws Exception {
		FilesystemObserver fo = new FilesystemObserver();
		assertThat(fo.watcherThread.isAlive()).isTrue();
		fo.stop();
		assertThat(fo.watcherThread.isAlive()).isFalse();
	}

}
