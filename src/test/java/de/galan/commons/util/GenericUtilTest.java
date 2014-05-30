package de.galan.commons.util;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT GenericUtil
 *
 * @author daniel
 */
public class GenericUtilTest extends AbstractTestParent {

	@Test
	public void cast() throws Exception {
		Object x = new ArrayList<String>();
		List<String> y = GenericUtil.cast(x);
		assertThat(y).isNotNull();
		assertThat(y).isEmpty();
	}


	@Test
	public void testGetClass() throws Exception {
		List<String> list = new ArrayList<>();
		Class<List<String>> clz = GenericUtil.getClass(list);
		assertThat(clz).isEqualTo(ArrayList.class);
	}


	@Test
	public void toArray() throws Exception {
		List<String> list = Lists.newArrayList("a", "b", "c");
		String[] array = GenericUtil.toArray(String.class, list);
		assertThat(array).hasSize(3);
		assertThat(array).containsSequence("a", "b", "c");
	}


	@Test
	public void toArrayEmpty() throws Exception {
		List<String> list = new ArrayList<>();
		String[] array = GenericUtil.toArray(String.class, list);
		assertThat(array).isEmpty();
	}

}
