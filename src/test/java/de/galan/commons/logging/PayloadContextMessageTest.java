package de.galan.commons.logging;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT PayloadMessage
 */
public class PayloadContextMessageTest extends AbstractTestParent {

	NullPointerException ex = new NullPointerException("BAM");
	ObjectMapper mapper = new ObjectMapper();


	protected void assertMsg(String pattern, Object[] args, Throwable throwable, Object[] argsExpected, Throwable throwableExpected, String messageExpected, Map<String, Object> metaExpected) throws JsonProcessingException, IOException {
		MetaContext.clear();
		PayloadContextMessage msg = new PayloadContextMessage(pattern, args, throwable);
		assertThat(msg.getFormat()).isEqualTo(pattern);
		assertThat(msg.getParameters()).containsExactly(argsExpected);
		assertThat(msg.getThrowable()).isEqualTo(throwableExpected);
		assertThat(msg.getFormattedMessage()).isEqualTo(messageExpected);

		ObjectNode nodeMetaExpected = (ObjectNode)mapper.valueToTree(metaExpected);
		JsonNode nodeMetaActual = mapper.readTree(MetaContext.toJson());
		// align expected exceptions
		for (String name: Lists.newArrayList(nodeMetaExpected.fieldNames())) {
			JsonNode node = nodeMetaExpected.get(name);
			if (node.isObject() && (node.get("cause").getNodeType() == JsonNodeType.NULL)) {
				((ObjectNode)node).remove("cause");
			}
		}
		assertThat(nodeMetaActual).isEqualTo(nodeMetaExpected);
	}


	@Test
	public void simple() throws Exception {
		assertMsg("Hello world", args(), null, args(), null, "Hello world", ImmutableMap.of());
	}


	@Test
	public void parameter() throws Exception {
		assertMsg("x {} y {}", args("a", 1), null, args("a", 1), null, "x {a} y {1}", ImmutableMap.of());
	}


	@Test
	public void parameterNames() throws Exception {
		assertMsg("Hello {first} world {second}", args("a", 1), null, args("a", 1), null, "Hello {a} world {1}", ImmutableMap.of("first", "a", "second", 1));
	}


	@Test
	public void parameterNextToEachOther() throws Exception {
		assertMsg("{first}{second}{third}", args("a", 1, true), null, args("a", 1, true), null, "{a}{1}{true}",
			ImmutableMap.of("first", "a", "second", 1, "third", true));
	}


	@Test
	public void parameterNameSparse() throws Exception {
		assertMsg("{}x{second}y{}", args("a", 1, true), null, args("a", 1, true), null, "{a}x{1}y{true}", ImmutableMap.of("second", 1));
	}


	@Test
	public void unclosedCurlyBrackets() throws Exception {
		assertMsg("x {a} y {b", args("a", 1), null, args("a", 1), null, "Invalid pattern, curly brace left unclosed.", ImmutableMap.of());
	}


	@Test
	public void missingArgument() throws Exception {
		assertMsg("Hello {} world {}, {x}!", args("a"), null, args("a"), null, "Invalid amount of arguments (only 1 available, 2 missing)", ImmutableMap.of());
		assertMsg("Hello {} world {}, {x}!", args("a"), null, args("a"), null, "Invalid amount of arguments (only 1 available, 2 missing)", ImmutableMap.of());
	}


	@Test
	public void parameterNamesThrowable() throws Exception {
		NullPointerException exception = new NullPointerException("BAM");
		assertMsg("Hello {first} world {second}", args("a", 1, exception), null, args("a", 1), exception, "Hello {a} world {1}",
			ImmutableMap.of("first", "a", "second", 1));
	}


	@Test
	public void parameterExplicitThrowable() throws Exception {
		NullPointerException exception = new NullPointerException("BAM");
		assertMsg("Hello {first} world {second}", args("a", 1), exception, args("a", 1), exception, "Hello {a} world {1}",
			ImmutableMap.of("first", "a", "second", 1));
	}


	@Test
	public void parameterExplicitThrowableToMuch() throws Exception {
		NullPointerException ex2 = new NullPointerException("BUM");
		assertMsg("Hello {first} world {second}", args("a", 1, ex2), ex, args("a", 1), ex, "Hello {a} world {1}", ImmutableMap.of("first", "a", "second", 1));
	}


	@Test
	public void parameterNamesToMuch() throws Exception {
		assertMsg("{first} x {second}", args("a", 1, 4), null, args("a", 1, 4), null, "Invalid amount of arguments (3 given but only 2 used in pattern)",
			ImmutableMap.of());
	}


	@Test
	public void parameterNamesToMuchWithThrowable() throws Exception {
		assertMsg("{a} x {b}", args("a", 1, 4, ex), null, args("a", 1, 4, ex), null, "Invalid amount of arguments (4 given but only 2 used in pattern)",
			ImmutableMap.of());
	}


	// Deficit of Log4j not enforcing separation  of arguments and throwable
	@Test
	public void parameterNamesIsThrowable() throws Exception {
		assertMsg("{ex} Lee", args(ex), null, args(ex), null, "{java.lang.NullPointerException: BAM} Lee",
			ImmutableMap.of("ex", ex));
	}


	@Test
	public void explicitThrowableAsParameter() throws Exception {
		assertMsg("{} Lee", null, ex, args(ex), null, "{java.lang.NullPointerException: BAM} Lee", ImmutableMap.of());
	}


	@Test
	public void explicitThrowableAsParameterSS() throws Exception {
		assertMsg("{A} Lee", null, ex, args(ex), null, "{java.lang.NullPointerException: BAM} Lee", ImmutableMap.of("A", ex));
	}


	protected Object[] args(Object... arguments) {
		return arguments;
	}

}
