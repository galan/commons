package de.galan.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.ReflectionUtil;


/**
 * Returns the Log4j2 logger for the calling class (for Slf4j use Slogr). Can be used to determine the Logger, reduces
 * errors when copy&paste.<br/>
 * Note: Can be replaced by Log4j2 LogManager.getLogger();
 *
 * @author galan
 */
public class Logr {

	// Using ReflectionUtil directly with "2" like the log4j2 LogManager.getLogger()
	private static final int THREAD_TYPE_DEEP = 2;


	public static Logger get() {
		return LogManager.getLogger(ReflectionUtil.getCallerClass(THREAD_TYPE_DEEP), PayloadMessageFactory.INSTANCE);
	}

	//private final static int THREAD_TYPE_DEEP = 2;

	/** A custom security manager that exposes the getClassContext() information */
	//static class CallerSecurityManager extends SecurityManager {
	//	public String getCallerClassName(int callStackDepth) {
	//		return getClassContext()[callStackDepth].getName();
	//	}
	//}

	/**
	 * Using a Custom SecurityManager to get the caller classname. Using the old reflection approach had some drawbacks:<br/>
	 * 1. From Java7u10 to Javau11 the THREAD_TYPE_DEEP differs (+1) due to internal Java changes.<br/>
	 * 2. From Java7u40 onwards the method is only supported if "-Djdk.reflect.allowGetCallerClass" is set on start<br/>
	 * 3. From Java8 on the method is removed<br/>
	 * <br/>
	 * See also http://www.infoq.com/news/2013/07/Oracle-Removes-getCallerClass
	 */
	//private static final CallerSecurityManager CSM = new CallerSecurityManager();

	//public static Logger get() {
	//	String className = CSM.getCallerClassName(THREAD_TYPE_DEEP);
	//	return determineLogger(className);
	//}

	/**
	 * Determines the class and the appropiate logger of the calling class.
	 *
	 * @return The (slf4j) logger of the caller
	 */
	//static Logger determineLogger(String callerClassName) {
	//	return LogManager.getLogger(callerClassName);
	//}

}
