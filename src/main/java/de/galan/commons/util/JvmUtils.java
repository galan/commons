package de.galan.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.logging.log4j.Logger;

import com.google.common.base.StandardSystemProperty;

import de.galan.commons.logging.Logr;
import de.galan.commons.time.Sleeper;


/**
 * Provides access to information about the currently running JVM and some process-control.
 *
 * @author daniel
 */
public class JvmUtils {

	public static String getPid() {
		RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
		return rmxb.getName().split("@")[0];
	}


	public static String getMachineName() {
		RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
		return rmxb.getName().split("@")[1];
	}


	public static long getUptime() {
		RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
		return rmxb.getUptime();
	}


	public static TerminateBuilder terminate() {
		return new TerminateBuilder();
	}

	/** Builder to terminate the JVM */
	public static class TerminateBuilder {

		private static final Logger LOG = Logr.get();

		private int builderReturnCode;
		private boolean builderThreaded = true;
		private String builderMessage;


		/** Sets the exit value, default is 0 (zero). */
		public TerminateBuilder returnCode(int returnCode) {
			builderReturnCode = returnCode;
			return this;
		}


		/** Starts the termination in a separate thread or blocking, default is true. */
		public TerminateBuilder threaded(boolean threaded) {
			builderThreaded = threaded;
			return this;
		}


		/** Adds an optional user-defined message to the termination log. */
		public TerminateBuilder message(String message) {
			builderMessage = message;
			return this;
		}


		/** Stops the JVM in the specified time */
		public void in(String time) {
			shutdown(time);
		}


		/** Stops the JVM immediately */
		public void now() {
			shutdown(null);
		}


		protected void shutdown(String time) {
			if (builderThreaded) {
				Thread thread = new Thread(() -> {
					shutdownFinal(time);
				}, "JvmTermination-thread");
				thread.setDaemon(false);
				thread.start();
			}
			else {
				shutdownFinal(time);
			}
		}


		protected void shutdownFinal(String time) {
			if (time != null) {
				logMessage("The JavaVM will exit in {" + time + "}, return code will be {" + builderReturnCode + "}");
				Sleeper.sleep(time);
			}
			logMessage("The JavaVM will exit NOW, return code is {" + builderReturnCode + "}");
			System.exit(builderReturnCode);
		}


		//TODO extract infobox to own entity
		protected void logMessage(String message) {
			String ls = StandardSystemProperty.LINE_SEPARATOR.value();
			String indention = "\t";
			StringBuilder info = new StringBuilder(ls);
			info.append(indention + "┏" + repeat("━", 68) + "╍" + ls);
			info.append(indention + "┃ " + ls);
			info.append(indention + "┃ " + message + ls);
			info.append(indention + "┃ " + ls);
			if (isNotBlank(builderMessage)) {
				info.append(indention + "┃ " + builderMessage + ls);
				info.append(indention + "┃ " + ls);
			}
			info.append(indention + "┗" + repeat("━", 68) + "╍" + ls);
			LOG.info("{}", info.toString());
		}
	}

}
