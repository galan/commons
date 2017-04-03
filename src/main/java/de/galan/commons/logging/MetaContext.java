package de.galan.commons.logging;

import static java.nio.charset.StandardCharsets.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Manages fields per thread, which are serialized as json for further processing (eg. logstash). See 'Say' class for
 * usage.
 */
public class MetaContext {

	private static final String ERROR_KEY = "serializationError";
	private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
	private static final FastDateFormat FDF = FastDateFormat.getInstance(DATE_FORMAT_UTC, TIMEZONE_UTC);

	private static ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(() -> null);
	private static JsonFactory factory;


	public static void put(String key, Object value) {
		if (value != null) {
			Map<String, Object> map = context.get();
			if (map == null) {
				map = new HashMap<>();
				context.set(map);
			}
			map.put(key, value);
		}
	}


	public static boolean hasMeta() {
		Map<String, Object> map = context.get();
		return map != null && !map.isEmpty();
	}


	public static String toJson() {
		ByteArrayBuilder out = new ByteArrayBuilder();

		try {
			JsonGenerator generator = getJsonFactory().createGenerator(out, JsonEncoding.UTF8);
			generator.writeStartObject();

			Map<String, Object> map = context.get();
			if (map != null) {
				for (String key: map.keySet()) {
					Object object = map.get(key);
					if (object instanceof String) {
						generator.writeStringField(key, (String)object);
					}
					else if (Number.class.isAssignableFrom(object.getClass())) {
						if (object instanceof Integer) {
							generator.writeNumberField(key, ((Integer)object).intValue());
						}
						else if (object instanceof Long) {
							generator.writeNumberField(key, ((Long)object).longValue());
						}
						else if (object instanceof Double) {
							generator.writeNumberField(key, ((Double)object).doubleValue());
						}
						else if (object instanceof Float) {
							generator.writeNumberField(key, ((Float)object).floatValue());
						}
						else if (object instanceof Short) {
							generator.writeFieldName(key);
							generator.writeNumber(((Short)object).shortValue());
						}
						else if (object instanceof BigDecimal) {
							generator.writeNumberField(key, (BigDecimal)object);
						}
						else if (object instanceof BigInteger) {
							generator.writeFieldName(key);
							generator.writeNumber((BigInteger)object);
						}
						else {
							generator.writeFieldName(key);
							generator.writeNumber(object.toString());
						}
					}
					else if (object instanceof Date) {
						generator.writeStringField(key, FDF.format((Date)object));
					}
					else if (object instanceof Instant) {
						generator.writeStringField(key, ((Instant)object).toString());
					}
					else {
						generator.writeObjectField(key, object);
					}
				}
			}

			generator.writeEndObject();
			generator.close();
		}
		catch (Exception ex) {
			out = new ByteArrayBuilder();
			try {
				JsonGenerator generator = getJsonFactory().createGenerator(out, JsonEncoding.UTF8);
				generator.writeStartObject();
				generator.writeStringField(ERROR_KEY, "Failed serializing the logmessage: " + ex.getMessage());
				generator.writeEndObject();
				generator.close();
			}
			catch (Exception exx) {
				return "{\"" + ERROR_KEY + "\": \"Error while serializing th exception-message.\"}";
			}
		}

		return new String(out.toByteArray(), UTF_8);
	}


	private static JsonFactory getJsonFactory() {
		if (factory == null) {
			ObjectMapper mapper = new ObjectMapper();
			//mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.setSerializationInclusion(Include.NON_NULL);
			factory = new MappingJsonFactory(mapper).enable(JsonGenerator.Feature.ESCAPE_NON_ASCII).disable(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM);
		}
		return factory;
	}


	public static void clear() {
		Map<String, Object> map = context.get();
		if (map != null) {
			context.set(null);
		}
	}

}
