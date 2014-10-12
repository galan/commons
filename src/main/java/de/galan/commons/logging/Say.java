package de.galan.commons.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Facade for the logging-framework (currently Log4j/SLF4J). Support additional functionality, like formatted error
 * messages with parameters.<br/>
 * To use parameterised message, just use <code>%s</code> as placeholder, eg.:
 * <p>
 * <code>
 * info("Hello {}", "world"); // => "Hello {world}"<br/>
 * info("Hello {} {}", "beautiful", "world"); // => "Hello {beautiful} {world}"<br/>
 * info("The Answer is {}", 42L); // => "The Answer is {42}"<br/>
 * </code>
 * </p>
 * To log exceptions you can use one of the following (Note that exceptions have to be declared before the arguments):
 * <p>
 * <code>
 * info("Hello World", ex);<br/>
 * info("Hello {}", ex, "World");<br/>
 * </code>
 * </p>
 * It is also encouraged to give the parameter names, this can be useful for later integrations such as allwissend. If
 * no parameters are given, key will be generated as sequence numbers. Example:
 * <p>
 * <code>
 * info("Hello {person}", "Someone");<br/>
 * </code>
 * </p>
 * Why is there no fatal level? See here: <a href="http://www.slf4j.org/faq.html#fatal">SLF4j FAQ</a>
 *
 * @author daniel
 */
public class Say {

	private static final String ARG_OPEN = "{";
	private static final String ARG_CLOSE = "}";
	private static final int THREAD_TYPE_DEEP = 2 + 1;
	private static final String NULL = "null";
	private static final String SPACE = " ";
	private static final String FORMAT_PLACEHOLDER = "%s";

	/** Cache of the dynamically created loggers. Multithreading considerations: a simple HashMap is sufficient. */
	private static Map<String, Logger> logger = new HashMap<String, Logger>();

	/** A custom security manager that exposes the getClassContext() information */
	static class CallerSecurityManager extends SecurityManager {

		public String getCallerClassName(int callStackDepth) {
			return getClassContext()[callStackDepth].getName();
		}
	}

	/**
	 * Using a Custom SecurityManager to get the caller classname. Using the old reflection approach had some drawbacks:<br/>
	 * 1. From Java7u10 to Javau11 the THREAD_TYPE_DEEP differs (+1) due to internal Java changes.<br/>
	 * 2. From Java7u40 onwards the method is only supported if "-Djdk.reflect.allowGetCallerClass" is set on start<br/>
	 * 3. From Java8 on the method is removed<br/>
	 * <br/>
	 * See also http://www.infoq.com/news/2013/07/Oracle-Removes-getCallerClass
	 */
	private static final CallerSecurityManager CSM = new CallerSecurityManager();


	// -------------------------------- TRACE --------------------------------

	public static void trace(Object message) {
		Logger log = determineLogger(determineCaller());
		if (log.isTraceEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.trace(parsedMessage);
		}
	}


	public static void trace(Object message, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isTraceEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.trace(format(parsedMessage, encloseArgs(args)));
		}
	}


	public static void trace(Object message, Throwable throwable) {
		Logger log = determineLogger(determineCaller());
		if (log.isTraceEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.trace(parsedMessage, throwable);
		}
	}


	public static void trace(Object message, Throwable throwable, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isTraceEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.trace(format(parsedMessage, encloseArgs(args)), throwable);
		}
	}


	public static boolean isTraceEnabled() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		return log.isTraceEnabled();
	}


	// -------------------------------- DEBUG --------------------------------

	public static void debug(Object message) {
		Logger log = determineLogger(determineCaller());
		if (log.isDebugEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.debug(parsedMessage);
		}
	}


	public static void debug(Object message, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isDebugEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.debug(format(parsedMessage, encloseArgs(args)));
		}
	}


	public static void debug(Object message, Throwable throwable) {
		Logger log = determineLogger(determineCaller());
		if (log.isDebugEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.debug(parsedMessage, throwable);
		}
	}


	public static void debug(Object message, Throwable throwable, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isDebugEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.debug(format(parsedMessage, encloseArgs(args)), throwable);
		}
	}


	public static boolean isDebugEnabled() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		return log.isDebugEnabled();
	}


	// -------------------------------- INFO --------------------------------

	public static void info(Object message) {
		Logger log = determineLogger(determineCaller());
		if (log.isInfoEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.info(parsedMessage);
		}
	}


	public static void info(Object message, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isInfoEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.info(format(parsedMessage, encloseArgs(args)));
		}
	}


	public static void info(Object message, Throwable throwable) {
		Logger log = determineLogger(determineCaller());
		if (log.isInfoEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.info(parsedMessage, throwable);
		}
	}


	public static void info(Object message, Throwable throwable, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isInfoEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.info(format(parsedMessage, encloseArgs(args)), throwable);
		}
	}


	public static boolean isInfoEnabled() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		return log.isInfoEnabled();
	}


	// -------------------------------- WARN --------------------------------

	public static void warn(Object message) {
		Logger log = determineLogger(determineCaller());
		if (log.isWarnEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.warn(parsedMessage);
		}
	}


	public static void warn(Object message, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isWarnEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.warn(format(parsedMessage, encloseArgs(args)));
		}
	}


	public static void warn(Object message, Throwable throwable) {
		Logger log = determineLogger(determineCaller());
		String parsedMessage = parseMessage(message, null);
		if (log.isWarnEnabled()) {
			log.warn(parsedMessage, throwable);
		}
	}


	public static void warn(Object message, Throwable throwable, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isWarnEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.warn(format(parsedMessage, encloseArgs(args)), throwable);
		}
	}


	public static boolean isWarnEnabled() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		return log.isWarnEnabled();
	}


	// -------------------------------- ERROR --------------------------------

	public static void error(Object message) {
		Logger log = determineLogger(determineCaller());
		if (log.isErrorEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.error(parsedMessage);
		}
	}


	public static void error(Object message, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isErrorEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.error(format(parsedMessage, encloseArgs(args)));
		}
	}


	public static void error(Object message, Throwable throwable) {
		Logger log = determineLogger(determineCaller());
		if (log.isErrorEnabled()) {
			String parsedMessage = parseMessage(message, null);
			log.error(parsedMessage, throwable);
		}
	}


	public static void error(Object message, Throwable throwable, Object... args) {
		Logger log = determineLogger(determineCaller());
		if (log.isErrorEnabled()) {
			List<String> keys = provideKeyList(args);
			String parsedMessage = parseMessage(message, keys);
			log.error(format(parsedMessage, encloseArgs(args)), throwable);
		}
	}


	public static boolean isErrorEnabled() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		return log.isErrorEnabled();
	}


	// -------------------------------- MISC --------------------------------

	public static void please() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		log.info("\u0059\u006F\u0075\u0027\u0072\u0065\u0020\u0077\u0065\u006C\u0063\u006F\u006D\u0065\u0020" + System.getProperty("user.name") + "\u0021");
	}


	static List<String> provideKeyList(Object[] args) {
		return new ArrayList<String>(args.length);
	}


	/**
	 * Determines the calling class (and method/linenumber too)
	 *
	 * @return A StrackTraceElement, which contains the Classname of the caller, and (if allwissend is integrated) the
	 *         method and linenumber, too.
	 */
	static String determineCaller() {
		return CSM.getCallerClassName(THREAD_TYPE_DEEP);
		//@SuppressWarnings("restriction")
		//String className = sun.reflect.Reflection.getCallerClass(THREAD_TYPE_DEEP).getName();
		//return new StackTraceElement(className, "", null, 0);
	}


	/**
	 * Determines the class and the appropiate logger of the calling class.
	 *
	 * @return The (log4j) logger of the caller
	 */
	//static Logger determineLogger(StackTraceElement caller) {
	static Logger determineLogger(String callerClassName) {
		//String className = caller.getClassName();
		String className = callerClassName;
		Logger result = logger.get(className);
		if (result == null) {
			logger.put(className, LoggerFactory.getLogger(className));
			result = logger.get(className);
			// plain log4j: logger.put(className, Logger.getLogger(stackTraceElements[THREAD_TYPE_DEEP].getClassName());
		}
		return result;
	}


	/**
	 * Parses the message String for the keynames, which are enclosed in { and } chars. If not set, the keys will be
	 * generated as sequence.<br/>
	 * Before this, the message is checked on null, "" (Empty String) and Exceptions, to fill these with a detailed
	 * message.
	 *
	 * @param message The used message by the user
	 * @return The cleaned message
	 */
	static String parseMessage(Object message, List<String> keyNames) {
		String result = null;
		if (message == null || ((message instanceof String) && (StringUtils.isBlank((String)message)))) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			// Spaces included, to make the stack clickable in an IDE
			result = "Empty message from " + ARG_OPEN + SPACE + stackTraceElements[THREAD_TYPE_DEEP] + SPACE + ARG_CLOSE;
		}
		else if (Throwable.class.isAssignableFrom(message.getClass())) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			// Spaces included, to make the stack clickable in an IDE
			result = "Exception " + ARG_OPEN + message.toString() + ARG_CLOSE + " from " + ARG_OPEN + SPACE + stackTraceElements[THREAD_TYPE_DEEP] + SPACE
					+ ARG_CLOSE;
			//TODO exception as payload?
		}
		else if (message instanceof String) {
			int currentPosition = 0;
			String msg = (String)message;
			int open = 0;
			int argCounter = 1;
			while((open = StringUtils.indexOf(msg, ARG_OPEN, currentPosition)) != -1) {
				int close = StringUtils.indexOf(msg, ARG_CLOSE, open);
				if (close == -1) {
					break;
				}
				String key = StringUtils.substring(msg, open + 1, close);
				if (StringUtils.isEmpty(key)) {
					key = "" + argCounter++;
				}
				if (keyNames != null) {
					keyNames.add(key);
				}
				msg = StringUtils.substring(msg, 0, open) + FORMAT_PLACEHOLDER + StringUtils.substring(msg, close + 1, msg.length());
				currentPosition = open + 2;
			}
			result = msg;
		}
		else {
			result = message.toString();
		}
		return result;
	}


	/**
	 * Formats the message with the given arguments, and informs if the argument amount is not correct.
	 *
	 * @param message The pattern
	 * @param args The individual arguments
	 * @return The formated String
	 */
	static String format(String message, Object... args) {
		try {
			return String.format(message, args);
		}
		catch (Exception ex) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			return "Amount of logging arguments is not correct: " + ARG_OPEN + message + ARG_CLOSE + " : " + ARG_OPEN + SPACE
					+ stackTraceElements[THREAD_TYPE_DEEP] + SPACE + ARG_CLOSE;
		}
	}


	/**
	 * Encapsulates arguments into their corresponding characters, so they can be parsed.
	 *
	 * @param args The used arguments
	 * @return String-Array including enclosed paramters
	 */
	static Object[] encloseArgs(Object... args) {
		String[] result = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			// Performance Hint: Will be converted from the compiler in a StringBuilder anyway.
			String arg = (args[i] == null) ? NULL : args[i].toString();
			result[i] = ARG_OPEN + arg + ARG_CLOSE;
		}
		return result;
	}

}
