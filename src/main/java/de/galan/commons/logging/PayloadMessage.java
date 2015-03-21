package de.galan.commons.logging;

import org.apache.logging.log4j.message.Message;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class PayloadMessage implements Message {

	private static final String[] EMPTY_ARGUMENTS = new String[] {};
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final char KV_SEPARATOR = ':';

	private transient String formattedMessage;
	private final String paramMessagePattern;
	private String[] arguments;
	private Object[] paramArguments;
	private int[] indexes;
	private boolean includeIdentifier;
	private String errormessage;
	private transient Throwable throwable;


	public PayloadMessage(final String messagePattern, final Object[] argumentsObject) {
		this(messagePattern, argumentsObject, false);
	}


	public PayloadMessage(final String messagePattern, final Object[] argumentsObject, boolean includeIdentifier) {
		paramArguments = argumentsObject;
		paramMessagePattern = messagePattern;
		this.includeIdentifier = includeIdentifier;
		parseMessage(messagePattern);
		arguments = argumentsToStrings(argumentsObject);
	}


	protected String[] argumentsToStrings(Object[] argumentsObject) {
		if (argumentsObject == null) {
			return EMPTY_ARGUMENTS;
		}
		int patternArguments = getPatternAmountArguments();
		int realLength = argumentsObject.length;
		if (realLength > 0 && (realLength - 1 == patternArguments) && Throwable.class.isAssignableFrom(argumentsObject[realLength - 1].getClass())) {
			throwable = (Throwable)argumentsObject[realLength - 1];
			realLength -= 1;
			Object[] withoutThrowable = new Object[realLength];
			System.arraycopy(argumentsObject, 0, withoutThrowable, 0, realLength);
			paramArguments = withoutThrowable;
		}
		String[] strArgs = new String[realLength];
		for (int i = 0; i < realLength; i++) {
			strArgs[i] = convertToString(argumentsObject[i]);
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
		if (paramArguments.length < amount) {
			errormessage = "Invalid amount of arguments (only " + paramArguments.length + " available, " + (amount - paramArguments.length) + " missing)";
		}
		if (paramArguments.length > amount) {
			errormessage = "Invalid amount of arguments (" + paramArguments.length + " given but only " + amount + " used in pattern)";
		}
		if (amount == 0) {
			return pattern;
		}
		if (errormessage != null) {
			return errormessage;
		}
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
				builder.append(paramMessagePattern.substring(indexPosition, indexes[factor]));
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
