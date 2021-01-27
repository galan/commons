package de.galan.commons.logging;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.message.Message;


/**
 * Log4j2 Message for Logger that support parameterized messages, using {} as placeholder, eg.:<br/>
 * <code>
 * info("Hello {}", "world"); // =&gt; "Hello {world}"<br/>
 * info("Hello {} {}", "beautiful", "world"); // =&gt; "Hello {beautiful} {world}"<br/>
 * error("Something failed: {}", ex, "do'h"); // =&gt; "Hello {beautiful} {world}"<br/>
 * </code> <br/>
 * <br/>
 * If the parameters should be available as json-encoded metadata for eg. logstash, you can provide names to the
 * parameters. Example:<br/>
 * <code>
 * info("Hello {location}", "world"); // ThreadContext will provide the json in a field called "payload"<br/>
 * </code> <br/>
 */
public class PayloadContextMessage implements Message {

	private static final String[] EMPTY_ARGUMENTS = new String[] {};
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
	private static final FastDateFormat FDF = FastDateFormat.getInstance(DATE_FORMAT_UTC, TIMEZONE_UTC);

	private transient String formattedMessage;
	private transient Throwable throwable;

	private final String paramMessagePattern;
	private String[] argsString;
	private Object[] argsObject;

	private int[] indexes;
	private String errormessage;

	public PayloadContextMessage(final String messagePattern, final Object[] argumentsObject) {
		this(messagePattern, argumentsObject, null);
	}


	public PayloadContextMessage(final String messagePattern, final Object[] argumentsObject, Throwable throwable) {
		this.throwable = throwable;
		paramMessagePattern = messagePattern;
		parseMessage(messagePattern);
		argsObject = determineArgumentsObject(argumentsObject);
		argsString = argumentsToStrings(argsObject);
		addErrorFields();
	}


	private Object[] determineArgumentsObject(final Object[] argumentsObject) {
		Object[] result = (argumentsObject == null) ? EMPTY_ARGUMENTS : argumentsObject;
		int patternAmountArgs = getPatternAmountArguments();
		if (result.length == patternAmountArgs) {
			// perfect
		}
		else if (result.length < patternAmountArgs) {
			// Check if throwable is part of the pattern arguments, and should be added to arguments-array instead
			if ((patternAmountArgs == result.length + 1) && throwable != null) {
				Object[] temp = new Object[result.length + 1];
				System.arraycopy(result, 0, temp, 0, result.length);
				temp[temp.length - 1] = throwable;
				throwable = null; // earase throwable -> is part of the arguments
				result = temp;
			}
		}

		else if (result.length > patternAmountArgs) {
			// Mimic ParameterizedMessage behaviour, last argument is throwable if not already set and arguments have one left
			if (result.length > 0 && (result.length - 1 == patternAmountArgs) && Throwable.class.isAssignableFrom(result[result.length - 1].getClass())) {
				if (throwable == null) {
					throwable = (Throwable)result[result.length - 1];
				}
				else {
					// throwable is already given by constructor, throw away superfluent exception
				}
				Object[] withoutThrowable = new Object[result.length - 1];
				System.arraycopy(result, 0, withoutThrowable, 0, result.length - 1);
				result = withoutThrowable;
			}
		}

		return result;
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


	protected String[] argumentsToStrings(Object[] argumentsObject) {
		if (argumentsObject.length == 0 && getPatternAmountArguments() == 0) {
			return EMPTY_ARGUMENTS;
		}
		String[] result = new String[argumentsObject.length];
		for (int i = 0; i < argumentsObject.length; i++) {
			result[i] = convertToString(argumentsObject[i]);
		}
		return result;
	}


	protected int getPatternAmountArguments() {
		return (indexes == null || indexes.length == 0) ? 0 : indexes.length / 2;
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
				formattedMessage = formatMessage(paramMessagePattern, argsString);
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
		else if (argsString.length < amount) {
			errormessage = "Invalid amount of arguments (only " + argsObject.length + " available, " + (amount - argsObject.length) + " missing). Pattern: '"
					+ pattern + "'";
		}
		else if (argsString.length > amount) {
			errormessage = "Invalid amount of arguments (" + argsObject.length + " given but only " + amount + " used). Pattern: '" + pattern + "'";
		}
		if (errormessage != null) {
			return errormessage;
		}
		StringBuilder builder = new StringBuilder();
		int indexPosition = 0;
		for (int i = 0; i < amount; i++) {
			int factor = i * 2;
			builder.append(paramMessagePattern.substring(indexPosition, indexes[factor]));
			if ((indexes[factor + 1] > indexes[factor]) && (argsString[i] != null)) {
				String tcName = paramMessagePattern.substring(indexes[factor], indexes[factor + 1]);
				MetaContext.put(tcName, argsObject[i]); // ThreadContext.put(tcName, arguments[i]);
			}
			builder.append(argsString[i]);
			indexPosition = indexes[factor + 1];
		}
		builder.append(paramMessagePattern.substring(indexPosition, paramMessagePattern.length()));

		return builder.toString();
	}


	private void addErrorFields() {
		if (throwable != null) {
			MetaContext.putIfAbsent("error_class", throwable.getClass());
			MetaContext.putIfAbsent("error_message", throwable.getMessage());

			Throwable rootCause = ExceptionUtils.getRootCause(throwable);
			if (rootCause != null && rootCause.getClass() != throwable.getClass()) {
				MetaContext.putIfAbsent("root_error_class", rootCause.getClass());
				MetaContext.putIfAbsent("root_error_message", rootCause.getMessage());
			}
		}
	}


	@Override
	public String getFormat() {
		return paramMessagePattern;
	}


	@Override
	public Object[] getParameters() {
		return argsObject;
	}


	@Override
	public Throwable getThrowable() {
		return throwable;
	}

}
