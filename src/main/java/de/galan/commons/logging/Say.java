package de.galan.commons.logging;

import static java.lang.StackWalker.Option.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;

import com.google.common.collect.Sets;


/**
 * Facade for the logging-framework (currently Log4j2). Uses the PayloadContextMessage for parameterization of messages.
 * Supports Parameterized messages, using {} as placeholder, eg.:<br/>
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
 * Additionally you can provide fields using a fluent interface using field(..) or f(..):<br>
 * <code>
 * Say.field("key", "value").field("other", someObject).info("Hello {location}", "world");
 * </code> <br/>
 * <br/>
 * You can omit the curly braces in the message by setting the environment-variable SAY_FIELDS_ENCLOSED to "false".
 */
public class Say {

	static final String JSON_FIELD = "payload";
	// Using ReflectionUtil directly, "2" is taken from log4j2 LogManager.getLogger(), adding 1 for determineLogger() and adding 1 for log(..)
	//private static final int THREAD_TYPE_DEEP = 2 + 1 + 1;

	private static final StackWalker WALKER = StackWalker.getInstance(Sets.newHashSet(RETAIN_CLASS_REFERENCE));

	// If SAY_FIELD_ENCLOSED is set to "false", you can remove the curly braces in the message. Defaults to "true". (experimental)
	static {
		PayloadContextMessage.envSayFieldsEnclosed = !"false".equals(System.getenv("SAY_FIELDS_ENCLOSED"));
	}

	/* Determines the class and the appropiate logger of the calling class. */
	/*
	static Logger determineLogger() {
		Class<?> callerClass = WALKER.getCallerClass();
		//Class<?> callerClass = ReflectionUtil.getCallerClass(THREAD_TYPE_DEEP);
		//callerClass = org.apache.logging.log4j.util.ReflectionUtil.getCallerClass(THREAD_TYPE_DEEP);
		return LogManager.getLogger(callerClass, PayloadContextMessageFactory.INSTANCE);
	}
	*/

	protected static Message payload(final Object message, final Object[] arguments, Throwable throwable) {
		return new PayloadContextMessage(message == null ? null : message.toString(), arguments, throwable);
	}

	// -------------------------------- logging --------------------------------


	protected static void log(Level level, Class<?> callerClass, Object message, Throwable throwable, Object... args) {
		Message payload = payload(message, args, throwable);
		payload.getFormattedMessage(); // preformat message (cached), so formating has processed and added fields to MetaContext
		if (MetaContext.hasMeta()) {
			// serialize metacontext map to json, which is put into a designated threadcontext field
			ThreadContext.put(JSON_FIELD, MetaContext.toJson());
			MetaContext.clear();
		}
		//determineLogger().log(level, payload, payload.getThrowable());
		Logger logger = LogManager.getLogger(callerClass, PayloadContextMessageFactory.INSTANCE);
		logger.log(level, payload, payload.getThrowable());

		if (ThreadContext.getContext() != null && ThreadContext.containsKey(JSON_FIELD)) {
			ThreadContext.remove(JSON_FIELD);
		}
	}

	// -------------------------------- ContextBuilder --------------------------------
	/**
	 * The builder-class is is designed to be fluent, but state is only put into ThreadLocal, therefore the builder can
	 * be static, no need to create objects for each log-statement.
	 */
	private static ContextBuilder builder = new ContextBuilder();

	public static ContextBuilder f(String key, Object value) {
		return builder.field(key, value);
	}


	public static ContextBuilder field(String key, Object value) {
		return builder.field(key, value);
	}

	/** Using fluent interface to construct ThreadContext informations (formerly known as MDC /NDC) */
	public static class ContextBuilder {

		public ContextBuilder f(String key, Object value) {
			return field(key, value);
		}


		public ContextBuilder field(String key, Object value) {
			MetaContext.put(key, value);
			return this;
		}


		// -------------------------------- TRACE Builder --------------------------------
		public void trace(Object message) {
			log(Level.TRACE, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void trace(Object message, Object... args) {
			log(Level.TRACE, WALKER.getCallerClass(), message, null, args);
		}


		public void trace(Object message, Throwable throwable) {
			log(Level.TRACE, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void trace(Object message, Throwable throwable, Object... args) {
			log(Level.TRACE, WALKER.getCallerClass(), message, throwable, args);
		}


		// -------------------------------- DEBUG Builder --------------------------------
		public void debug(Object message) {
			log(Level.DEBUG, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void debug(Object message, Object... args) {
			log(Level.DEBUG, WALKER.getCallerClass(), message, null, args);
		}


		public void debug(Object message, Throwable throwable) {
			log(Level.DEBUG, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void debug(Object message, Throwable throwable, Object... args) {
			log(Level.DEBUG, WALKER.getCallerClass(), message, throwable, args);
		}


		// -------------------------------- INFO Builder --------------------------------
		public void info(Object message) {
			log(Level.INFO, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void info(Object message, Object... args) {
			log(Level.INFO, WALKER.getCallerClass(), message, null, args);
		}


		public void info(Object message, Throwable throwable) {
			log(Level.INFO, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void info(Object message, Throwable throwable, Object... args) {
			log(Level.INFO, WALKER.getCallerClass(), message, throwable, args);
		}


		// -------------------------------- WARN Builder --------------------------------
		public void warn(Object message) {
			log(Level.WARN, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void warn(Object message, Object... args) {
			log(Level.WARN, WALKER.getCallerClass(), message, null, args);
		}


		public void warn(Object message, Throwable throwable) {
			log(Level.WARN, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void warn(Object message, Throwable throwable, Object... args) {
			log(Level.WARN, WALKER.getCallerClass(), message, throwable, args);
		}

		// -------------------------------- ERROR Builder --------------------------------


		public void error(Object message) {
			log(Level.ERROR, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void error(Object message, Object... args) {
			log(Level.ERROR, WALKER.getCallerClass(), message, null, args);
		}


		public void error(Object message, Throwable throwable) {
			log(Level.ERROR, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void error(Object message, Throwable throwable, Object... args) {
			log(Level.ERROR, WALKER.getCallerClass(), message, throwable, args);
		}

		// -------------------------------- FATAL Builder --------------------------------


		public void fatal(Object message) {
			log(Level.FATAL, WALKER.getCallerClass(), message, null, (Object[])null);
		}


		public void fatal(Object message, Object... args) {
			log(Level.FATAL, WALKER.getCallerClass(), message, null, args);
		}


		public void fatal(Object message, Throwable throwable) {
			log(Level.FATAL, WALKER.getCallerClass(), message, throwable, (Object[])null);
		}


		public void fatal(Object message, Throwable throwable, Object... args) {
			log(Level.FATAL, WALKER.getCallerClass(), message, throwable, args);
		}

	}

	// -------------------------------- TRACE --------------------------------

	public static void trace(Object message) {
		log(Level.TRACE, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void trace(Object message, Object... args) {
		log(Level.TRACE, WALKER.getCallerClass(), message, null, args);
	}


	public static void trace(Object message, Throwable throwable) {
		log(Level.TRACE, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void trace(Object message, Throwable throwable, Object... args) {
		log(Level.TRACE, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- DEBUG --------------------------------


	public static void debug(Object message) {
		log(Level.DEBUG, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void debug(Object message, Object... args) {
		log(Level.DEBUG, WALKER.getCallerClass(), message, null, args);
	}


	public static void debug(Object message, Throwable throwable) {
		log(Level.DEBUG, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void debug(Object message, Throwable throwable, Object... args) {
		log(Level.DEBUG, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- INFO --------------------------------


	public static void info(Object message) {
		log(Level.INFO, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void info(Object message, Object... args) {
		log(Level.INFO, WALKER.getCallerClass(), message, null, args);
	}


	public static void info(Object message, Throwable throwable) {
		log(Level.INFO, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void info(Object message, Throwable throwable, Object... args) {
		log(Level.INFO, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- WARN --------------------------------


	public static void warn(Object message) {
		log(Level.WARN, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void warn(Object message, Object... args) {
		log(Level.WARN, WALKER.getCallerClass(), message, null, args);
	}


	public static void warn(Object message, Throwable throwable) {
		log(Level.WARN, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void warn(Object message, Throwable throwable, Object... args) {
		log(Level.WARN, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- ERROR --------------------------------


	public static void error(Object message) {
		log(Level.ERROR, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void error(Object message, Object... args) {
		log(Level.ERROR, WALKER.getCallerClass(), message, null, args);
	}


	public static void error(Object message, Throwable throwable) {
		log(Level.ERROR, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void error(Object message, Throwable throwable, Object... args) {
		log(Level.ERROR, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- FATAL --------------------------------


	public static void fatal(Object message) {
		log(Level.FATAL, WALKER.getCallerClass(), message, null, (Object[])null);
	}


	public static void fatal(Object message, Object... args) {
		log(Level.FATAL, WALKER.getCallerClass(), message, null, args);
	}


	public static void fatal(Object message, Throwable throwable) {
		log(Level.FATAL, WALKER.getCallerClass(), message, throwable, (Object[])null);
	}


	public static void fatal(Object message, Throwable throwable, Object... args) {
		log(Level.FATAL, WALKER.getCallerClass(), message, throwable, args);
	}

	// -------------------------------- MISC --------------------------------


	public static void please() {
		log(Level.INFO, WALKER.getCallerClass(),
			"\u0059\u006F\u0075\u0027\u0072\u0065\u0020\u0077\u0065\u006C\u0063\u006F\u006D\u0065\u0020" + System.getProperty("user.name") + "\u0021", null,
			(Object[])null);
	}

	// Old stuff

	// potential improvements:
	// - Integrate Rethrow into Say (see snippet at end)
	// - Say returns generated message

	/* A custom security manager that exposes the getClassContext() information */
	/*
	static class CallerSecurityManager extends SecurityManager {
	
		public String getCallerClassName(int callStackDepth) {
			return getClassContext()[callStackDepth].getName();
		}
	}
	 */

	/*
	public static Logger/checkstyle/ getLogger() {
		return determineLogger();
	}
	*/

	/* Cache of the dynamically created loggers. Multithreading considerations: a simple HashMap is sufficient. */
	//private static Map<String, Logger> logger = new HashMap<String, Logger>();

	/*
	 * Determines the class and the appropiate logger of the calling class.
	 *
	 * @return The logger for the caller
	 */
	/*
	//static Logger determineLogger(StackTraceElement caller) {
	//static Logger determineLogger() { // (String callerClassName)
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
	 */

	/* TODO rethrow
	public static <T extends Throwable> T traceThrows(T throwable) throws T {
		//PayloadMessage payload = payload(message, null, throwable);
		throw determineLogger(0).throwing(Level.ERROR, throwable);
	}
	*/

}
