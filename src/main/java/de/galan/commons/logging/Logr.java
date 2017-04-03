package de.galan.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.ReflectionUtil;


/**
 * Returns the Log4j2 logger for the calling class (for Slf4j use Slogr). Can be used to determine the Logger, reduces
 * errors when copy&paste.<br/>
 * Note: Compareable to Log4j2 LogManager.getLogger();
 */
public class Logr {

	// Using ReflectionUtil directly with "2" like the log4j2 LogManager.getLogger()
	private static final int THREAD_TYPE_DEEP = 2;


	/**
	 * To be used to get the Log4j2 Logger in a class, eg. <code>private final static Logger LOG = Logr.get();</code>.
	 * To avoid the Logger declaration completely, use the class <code>Say</code>.
	 */
	public static Logger get() {
		return LogManager.getLogger(ReflectionUtil.getCallerClass(THREAD_TYPE_DEEP), PayloadMessageFactory.INSTANCE);
	}

}
