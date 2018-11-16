package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


/**
 * CUT GenericUtil
 */
public class GenericsTest {

	@Test
	public void cast() throws Exception {
		Object x = new ArrayList<String>();
		List<String> y = Generics.cast(x);
		assertThat(y).isNotNull();
		assertThat(y).isEmpty();
	}


	@Test
	public void testGetClass() throws Exception {
		List<String> list = new ArrayList<>();
		Class<List<String>> clz = Generics.getClass(list);
		assertThat(clz).isEqualTo(ArrayList.class);
	}

}
