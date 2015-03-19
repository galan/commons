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

	private transient String formattedMessage;
	private final String paramMessagePattern;
	private String[] arguments;
	private Object[] paramArguments;


	public PayloadMessage(final String messagePattern, final Object[] argumentsObject) {
		Say.info("PayloadMessage");
		paramArguments = argumentsObject;
		paramMessagePattern = messagePattern;
		arguments = argumentsToStrings(argumentsObject);
	}


	protected String[] argumentsToStrings(Object[] argumentsObject) {
		if (argumentsObject == null) {
			return EMPTY_ARGUMENTS;
		}
		String[] strArgs = new String[argumentsObject.length];
		for (int i = 0; i < argumentsObject.length; i++) {
			strArgs[i] = convertToString(argumentsObject[i]);
		}
		return strArgs;
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
			formattedMessage = formatMessage(paramMessagePattern, arguments);
		}
		return formattedMessage;
	}


	private String formatMessage(String pattern, String[] args) {
		if (pattern == null) {
			return pattern;
		}
		//int tokenStart = 0;
		int startPosition = -1;
		int argCount = 0;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pattern.length(); i++) {
			final char curChar = pattern.charAt(i);
			if (startPosition != -1) {
				if (curChar == DELIM_STOP) {
					String argName = pattern.substring(startPosition + 1, i);
					builder.append(DELIM_START);
					if (!"".equals(argName)) {
						builder.append(argName);
						builder.append(":");
					}
					if (args != null && argCount < args.length) {
						builder.append(args[argCount++]);
					}
					else {
						builder = new StringBuilder("invalid amount of arguments (only ").append(args == null ? "null" : args.length).append(
								" available, at least one missing)");
						break;
					}
					builder.append(DELIM_STOP);
					startPosition = -1;
				}
				else {
					// inside argname
				}
			}
			else if (curChar == DELIM_START) {
				startPosition = i;
			}
			else {
				builder.append(curChar);
			}
		}
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
		return null;
	}

}
