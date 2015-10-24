package de.galan.commons.logging;

import static de.galan.commons.time.Instants.*;
import static org.assertj.core.api.StrictAssertions.*;

import org.junit.After;
import org.junit.Test;

import de.galan.commons.util.Pair;


/**
 * CUT MetaContext
 *
 * @author daniel
 */
public class MetaContextTest {

	@After
	public void teardown() {
		MetaContext.clear();
	}


	@Test
	public void kvString() throws Exception {
		MetaContext.put("a", "b");
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":\"b\"}");
	}


	@Test
	public void kvNumber() throws Exception {
		MetaContext.put("a", 9L);
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":9}");
	}


	@Test
	public void kvDouble() throws Exception {
		MetaContext.put("a", 123.456d);
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":123.456}");
	}


	@Test
	public void kvDate() throws Exception {
		MetaContext.put("a", dateUtc("2015-05-01T18:10:20.123Z"));
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":\"2015-05-01T18:10:20.123Z\"}");
	}


	@Test
	public void kvInstant() throws Exception {
		MetaContext.put("a", instantUtc("2015-05-01T18:10:20.123Z"));
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":\"2015-05-01T18:10:20.123Z\"}");
	}


	@Test
	public void kvNested() throws Exception {
		MetaContext.put("a", new Pair<String, String>("x", "y"));
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":{\"key\":\"x\",\"value\":\"y\"}}");
	}


	@Test
	public void kvMultiple() throws Exception {
		MetaContext.put("f1", 111L);
		MetaContext.put("f2", new Pair<String, String>("x", "y"));
		MetaContext.put("loc", "world");
		String json = MetaContext.toJson();
		assertThat(json).contains("\"loc\":\"world\"", "\"f1\":111", "\"f2\":{\"key\":\"x\",\"value\":\"y\"}}");
		assertThat(json).startsWith("{").endsWith("}");
		assertThat(json).hasSize(53);
	}


	@Test
	public void kvEscaped() throws Exception {
		MetaContext.put("a", "#{}'\"");
		assertThat(MetaContext.toJson()).isEqualTo("{\"a\":\"#{}'\\\"\"}");
	}

}
