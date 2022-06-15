package de.galan.commons.logging;

import org.apache.logging.log4j.message.Message;


/**
 * (Log4j2) Message for Logger that support parameterized messages, using {} as placeholder, eg.:<br/>
 * <code>
 * info("Hello {}", "world"); // =&gt; "Hello {world}"<br/>
 * info("Hello {} {}", "beautiful", "world"); // =&gt; "Hello {beautiful} {world}"<br/>
 * error("Something failed: {}", ex, "do'h"); // =&gt; "Hello {beautiful} {world}"<br/>
 * </code> <br/>
 * <br/>
 * It is also encouraged to give the parameter names with identifier set to true. This can be useful for later
 * integrations/parsing. If no parameters are given, key will be generated as sequence numbers. Example:<br/>
 * <code>
 * info("Hello {}", "world"); // =&gt; "Hello {0:world}"<br/>
 * info("Hello {} {}", "beautiful", "world"); // =&gt; "Hello {0:beautiful} {1:world}"<br/>
 * info("The Answer is {answer}", 42L); // =&gt; "The Answer is {answer:42}"
 * </code> <br/>
 * <br/>
 * You can omit the curly braces in the message by setting the environment-variable SAY_ENCLOSED to "false".
 */
public class PayloadMessage implements Message {

	private static final String[] EMPTY_ARGUMENTS = new String[] {};
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final char KV_SEPARATOR = ':';

	// This is controlled by Say directly
	static boolean envSayFieldEnclosed = true;

	private transient String formattedMessage;
	private final String paramMessagePattern;
	private String[] arguments;
	private Object[] paramArguments;
	private int[] indexes;
	private boolean includeIdentifier;
	private String errorMessage;
	private transient Throwable throwable;

	public PayloadMessage(final String messagePattern, final Object[] argumentsObject) {
		this(messagePattern, argumentsObject, false, null);
	}


	public PayloadMessage(final String messagePattern, final Object[] argumentsObject, boolean includeIdentifier,
			Throwable throwable) {
		paramArguments = argumentsObject == null ? EMPTY_ARGUMENTS : argumentsObject;
		paramMessagePattern = messagePattern;
		this.includeIdentifier = includeIdentifier;
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
			errorMessage = "Invalid pattern, curly brace left unclosed.";
		}
	}


	protected String convertToString(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String)object;
		}
		return object.toString();
	}


	@Override
	public String getFormattedMessage() {
		if (formattedMessage == null) {
			if (errorMessage != null) {
				formattedMessage = errorMessage;
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
		if (arguments.length < amount) {
			errorMessage = "Invalid amount of arguments (only " + paramArguments.length + " available, " + (amount - paramArguments.length) + " missing)";
		}
		if (arguments.length > amount) {
			errorMessage = "Invalid amount of arguments (" + paramArguments.length + " given but only " + amount + " used in pattern)";
		}
		if (amount == 0) {
			return pattern;
		}
		if (errorMessage != null) {
			return errorMessage;
		}

		int delimLengthRemoval = envSayFieldEnclosed ? 0 : 1;
		StringBuilder builder = new StringBuilder();
		int indexPosition = 0;
		int argumentWithoutIdentifier = 0;
		for (int i = 0; i < amount; i++) {
			int factor = i * 2;
			if (includeIdentifier) {
				builder.append(paramMessagePattern.substring(indexPosition, indexes[factor + 1]));
				if (indexes[factor + 1] - indexes[factor] == 0) {
					builder.append(argumentWithoutIdentifier++);
				}
				builder.append(KV_SEPARATOR);
			}
			else {
				builder.append(paramMessagePattern.substring(indexPosition, indexes[factor] - delimLengthRemoval));
			}
			builder.append(arguments[i]);
			indexPosition = indexes[factor + 1] + delimLengthRemoval;
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
