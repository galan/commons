package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import de.galan.commons.test.Tests;


/**
 * CUT BOM
 */
public class BOMTest {

	private String bom1;
	private String bom2;


	@Before
	public void before() throws IOException {
		bom1 = Tests.readFile(getClass(), getClass().getSimpleName() + "-bom01.txt");
		bom2 = Tests.readFile(getClass(), getClass().getSimpleName() + "-bom02.txt");
	}


	@Test
	public void isBom() throws Exception {
		assertThat(BOM.isUTF8(bom1.getBytes())).isFalse();
		assertThat(BOM.isUTF8(bom2.getBytes())).isTrue();
	}


	@Test
	public void clean() throws Exception {
		String clean1 = BOM.clean(bom1);
		String clean2 = BOM.clean(bom2);
		assertThat(BOM.isUTF8(clean1.getBytes())).isFalse();
		assertThat(BOM.isUTF8(clean2.getBytes())).isFalse();
		assertThat(clean1).isEqualTo("Hello world 01 äöü€*!");
		assertThat(clean2).isEqualTo("Hello world 02 äöü€*!");
	}


	@Test
	public void writeBom() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		assertThat(baos.size()).isEqualTo(0);
		assertThat(BOM.isUTF8(baos.toByteArray())).isFalse();
		BOM.writeBom(baos);
		assertThat(BOM.isUTF8(baos.toByteArray())).isTrue();
	}

}
