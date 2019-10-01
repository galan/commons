package de.galan.commons.logging;

import static java.lang.StackWalker.Option.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;


/**
 * Returns the Slf4j logger for the calling class (for Log4j2 use Logr). Can be used to determine the Logger, reduces
 * errors when copy&amp;paste.<br/>
 */
public class Slogr {

	private static final StackWalker WALKER = StackWalker.getInstance(Sets.newHashSet(RETAIN_CLASS_REFERENCE));


	/**
	 * To be used to get the Slf4J Logger in a class, eg. <code>private final static Logger LOG = Slogr.get();</code>.
	 * To avoid the Logger declaration completely, use the class <code>Say</code>.
	 */
	public static Logger get() {
		return LoggerFactory.getLogger(WALKER.getCallerClass());
	}

}
