package de.galan.commons.logging;

import static java.lang.StackWalker.Option.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Sets;


/**
 * Returns the Log4j2 logger for the calling class (for Slf4j use Slogr). Can be used to determine the Logger, reduces
 * errors when copy&amp;paste.<br/>
 * Note: Compareable to Log4j2 LogManager.getLogger();
 */
public class Logr {

	private static final StackWalker WALKER = StackWalker.getInstance(Sets.newHashSet(RETAIN_CLASS_REFERENCE));


	/**
	 * To be used to get the Log4j2 Logger in a class, eg. <code>private final static Logger LOG = Logr.get();</code>.
	 * To avoid the Logger declaration completely, use the class <code>Say</code>.
	 */
	public static Logger get() {
		return LogManager.getLogger(WALKER.getCallerClass(), PayloadMessageFactory.INSTANCE);
	}

}
