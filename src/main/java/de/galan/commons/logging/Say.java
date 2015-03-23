package de.galan.commons.logging;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.ReflectionUtil;


/**
 * Facade for the logging-framework (currently Log4j2). Uses the PayloadMessage for parameterization of messages.
 *
 * @author daniel
 */
public class Say {

	private static final int THREAD_TYPE_DEEP = 2 + 1;
	private static boolean includeIdentifier = false;

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

	public static void trace(String message) {
		//public PayloadMessage(final String messagePattern, final Object[] argumentsObject, boolean includeIdentifier, Throwable throwed) {
		determineLogger().trace(new PayloadMessage(message, null, includeIdentifier, null));
	}


	/*
	public static void trace(Object message, Object... args) {
		determineLogger().trace(message,);
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
	 */

	protected static PayloadMessage payload(final Object message, final Object[] arguments, Throwable throwable) {
		return new PayloadMessage(message == null ? null : message.toString(), arguments, includeIdentifier, throwable);
	}


	public static void info(Object message) {
		determineLogger().info(payload(message, null, null));
	}


	public static void info(Object message, Object... args) {
		determineLogger().info(payload(message, args, null));
	}


	public static void info(Object message, Throwable throwable) {
		PayloadMessage payload = payload(message, null, throwable);
		determineLogger().info(payload, payload.getThrowable());
	}


	public static void info(Object message, Throwable throwable, Object... args) {
		determineLogger().info(payload(message, args, throwable), throwable);
	}


	/*
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

	public static Logger getLogger() {
		return determineLogger(determineCaller());
	}


	public static void please() {
		String caller = determineCaller();
		Logger log = determineLogger(caller);
		log.info("\u0059\u006F\u0075\u0027\u0072\u0065\u0020\u0077\u0065\u006C\u0063\u006F\u006D\u0065\u0020" + System.getProperty("user.name") + "\u0021");
	}


	static List<String> provideKeyList(Object[] args) {
		return new ArrayList<String>(args.length);
	}
	 */

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
	 * @return The logger for the caller
	 */
	//static Logger determineLogger(StackTraceElement caller) {
	static Logger determineLogger() { // (String callerClassName)
		/*
		//String className = caller.getClassName();
		String className = callerClassName;
		Logger result = logger.get(className);
		if (result == null) {
			logger.put(className, LoggerFactory.getLogger(className));
			result = logger.get(className);
			// plain log4j: logger.put(className, Logger.getLogger(stackTraceElements[THREAD_TYPE_DEEP].getClassName());
		}
		return result;
		 */
		return LogManager.getLogger(ReflectionUtil.getCallerClass(3), PayloadMessageFactory.INSTANCE);
	}

}
