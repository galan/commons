package de.galan.commons.logging;

import org.apache.logging.log4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Returns the Slf4j logger for the calling class (for Log4j2 use Logr). Can be used to determine the Logger, reduces
 * errors when copy&paste.<br/>
 */
public class Slogr {

	// Using ReflectionUtil directly with "2" like the log4j2 LogManager.getLogger()
	private static final int THREAD_TYPE_DEEP = 2;


	/**
	 * To be used to get the Slf4J Logger in a class, eg. <code>private final static Logger LOG = Slogr.get();</code>.
	 * To avoid the Logger declaration completely, use the class <code>Say</code>.
	 */
	public static Logger get() {
		return LoggerFactory.getLogger(ReflectionUtil.getCallerClass(THREAD_TYPE_DEEP));
	}

}
