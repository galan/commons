package de.galan.commons.util;

import java.io.IOException;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.commons.test.Tests;


/**
 * CUT MessageBox
 */
public class MessageBoxTest {

	@Test
	public void generate() throws Exception {
		box("simple", null, "a");
		box("multilines", null, "aaa aaa", "bbb bbb", "", "ccc ccc");
		box("title", "hello world", "some", "  lines", "\t€", "äöüß");
	}


	protected void box(String filename, String title, String... lines) throws IOException {
		Tests.assertFileEqualsToString("MessageBoxTest-" + filename + ".txt", getClass(), MessageBox.generateBox(title, Lists.newArrayList(lines)));
	}

}
