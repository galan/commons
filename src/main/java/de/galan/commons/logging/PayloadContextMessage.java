package de.galan.commons.logging;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.message.Message;


/**
 * Log4j2 Message for Logger that support parameterized messages, using {} as placeholder, eg.:<br/>
 * <code>
 * info("Hello {}", "world"); // => "Hello {world}"<br/>
 * info("Hello {} {}", "beautiful", "world"); // => "Hello {beautiful} {world}"<br/>
 * error("Something failed: {}", ex, "do'h"); // => "Hello {beautiful} {world}"<br/>
 * </code> <br/>
 * <br/>
 * If the parameters should be available as json-encoded metadata for eg. logstash, you can provide names to the
 * parameters. Example:<br/>
 * <code>
 * info("Hello {location}", "world"); // ThreadContext will provide the json in a field called "payload"<br/>
 * </code> <br/>
 *
 * @author galan
 */
public class PayloadContextMessage implements Message {

	private static final String[] EMPTY_ARGUMENTS = new String[] {};
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
	private static final FastDateFormat FDF = FastDateFormat.getInstance(DATE_FORMAT_UTC, TIMEZONE_UTC);

	private transient String formattedMessage;
	private final String paramMessagePattern;
	private String[] arguments;
	private Object[] paramArguments;
	private int[] indexes;
	private String errormessage;
	private transient Throwable throwable;


	public PayloadContextMessage(final String messagePattern, final Object[] argumentsObject) {
		this(messagePattern, argumentsObject, null);
	}


	public PayloadContextMessage(final String messagePattern, final Object[] argumentsObject, Throwable throwable) {
		paramArguments = argumentsObject == null ? EMPTY_ARGUMENTS : argumentsObject;
		paramMessagePattern = messagePattern;
		this.throwable = throwable;
		parseMessage(messagePattern);
		arguments = argumentsToStrings(paramArguments);
	}


	protected String[] argumentsToStrings(Object[] argumentsObject) {
		int patternArguments = getPatternAmountArguments();
		if (argumentsObject.length == 0 && patternArguments == 0) {
			return EMPTY_ARGUMENTS;
		}
		Object[] objArgs = argumentsObject;
		int realLength = objArgs.length;
		// Check if throwable is part of the pattern arguments, and should be added to arguments-array instead
		if (throwable != null && patternArguments == realLength + 1) {
			Object[] temp = new Object[objArgs.length + 1];
			System.arraycopy(objArgs, 0, temp, 0, objArgs.length);
			temp[temp.length - 1] = throwable;
			throwable = null; // earase throwable -> is part of the arguments
			objArgs = temp;
			realLength = objArgs.length;
		}
		// Mimic ParameterizedMessage behaviour, last argument is throwable if not already set and arguments have one left
		else if (realLength > 0 && (realLength - 1 == patternArguments) && Throwable.class.isAssignableFrom(objArgs[realLength - 1].getClass())) {
			if (throwable == null) {
				throwable = (Throwable)objArgs[realLength - 1];
			}
			else {
				// throwable is already given by constructor, throw away superfluent exception
			}
			realLength -= 1;
			Object[] withoutThrowable = new Object[realLength];
			System.arraycopy(objArgs, 0, withoutThrowable, 0, realLength);
			paramArguments = withoutThrowable;
		}
		String[] strArgs = new String[realLength];
		for (int i = 0; i < realLength; i++) {
			strArgs[i] = convertToString(objArgs[i]);
		}
		return strArgs;
	}


	protected int getPatternAmountArguments() {
		return (indexes == null || indexes.length == 0) ? 0 : indexes.length / 2;
	}


	protected void parseMessage(String messagePattern) {
		if (messagePattern == null || messagePattern.length() == 0) {
			return;
		}
		int startPosition = -1;
		for (int i = 0; i < messagePattern.length(); i++) {
			final char curChar = messagePattern.charAt(i);
			if (startPosition != -1) {
				if (curChar == DELIM_STOP) {
					indexes[indexes.length - 1] = i;
					startPosition = -1;
				}
				else {
					// inside argname
				}
			}
			else if (curChar == DELIM_START) {
				if (indexes == null) {
					indexes = new int[2];
				}
				else {
					int[] next = new int[indexes.length + 2];
					System.arraycopy(indexes, 0, next, 0, indexes.length);
					indexes = next;
				}
				indexes[indexes.length - 2] = i + 1;
				startPosition = i;
			}
		}
		if (startPosition != -1) {
			errormessage = "Invalid pattern, curly brace left unclosed.";
		}
	}


	protected String convertToString(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String)object;
		}
		if (object instanceof Date) {
			return FDF.format((Date)object);
		}
		return object.toString();
	}


	@Override
	public String getFormattedMessage() {
		if (formattedMessage == null) {
			if (errormessage != null) {
				formattedMessage = errormessage;
			}
			else {
				formattedMessage = formatMessage(paramMessagePattern, arguments);
			}
		}
		return formattedMessage;
	}


	private String formatMessage(String pattern, String[] args) {
		if (pattern == null) {
			return pattern;
		}
		int amount = getPatternAmountArguments();
		if (amount == 0) {
			return pattern;
		}
		else if (arguments.length < amount) {
			errormessage = "Invalid amount of arguments (only " + paramArguments.length + " available, " + (amount - paramArguments.length) + " missing)";
		}
		else if (arguments.length > amount) {
			errormessage = "Invalid amount of arguments (" + paramArguments.length + " given but only " + amount + " used in pattern)";
		}
		if (errormessage != null) {
			return errormessage;
		}
		StringBuilder builder = new StringBuilder();
		int indexPosition = 0;
		for (int i = 0; i < amount; i++) {
			int factor = i * 2;
			builder.append(paramMessagePattern.substring(indexPosition, indexes[factor]));
			if ((indexes[factor + 1] > indexes[factor]) && (arguments[i] != null)) {
				String tcName = paramMessagePattern.substring(indexes[factor], indexes[factor + 1]);
				MetaContext.put(tcName, paramArguments[i]); // ThreadContext.put(tcName, arguments[i]);
			}
			builder.append(arguments[i]);
			indexPosition = indexes[factor + 1];
		}
		builder.append(paramMessagePattern.substring(indexPosition, paramMessagePattern.length()));
		return builder.toString();
	}


	@Override
	public String getFormat() {
		return paramMessagePattern;
	}


	@Override
	public Object[] getParameters() {
		return paramArguments;
	}


	@Override
	public Throwable getThrowable() {
		return throwable;
	}

}
