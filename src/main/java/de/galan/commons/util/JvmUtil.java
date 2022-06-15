package de.galan.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import de.galan.commons.logging.Say;
import de.galan.commons.time.Sleeper;


/**
 * Provides access to information about the currently running JVM and some process-control.
 */
public class JvmUtil {

	public static String getPid() {
		return Long.toString(ProcessHandle.current().pid());
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

		private int builderReturnCode;
		private boolean builderThreaded = false;
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
				Thread thread = new Thread(() -> shutdownFinal(time), "JvmTermination-thread");
				thread.setDaemon(true);
				thread.start();
			}
			else {
				shutdownFinal(time);
			}
		}


		protected String getMessage() {
			return isNotBlank(builderMessage) ? builderMessage : "none";
		}


		protected void shutdownFinal(String time) {
			if (time != null) {
				Say.info("The JavaVM will exit in {time}, return code will be {code}, message: {message}", time, builderReturnCode, getMessage());
				Sleeper.sleep(time);
			}
			Say.info("The JavaVM will exit NOW, return code is {code}, message: {message}", builderReturnCode, getMessage());
			System.exit(builderReturnCode);
		}

	}

	/** Null-safe shortcut to Runtime method, catching RuntimeExceptions. */
	public synchronized static void addShutdownHook(Runnable task) {
		if (task != null) {
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					task.run();
				}
				catch (RuntimeException rex) {
					Say.warn("Exception while processing shutdown-hook", rex);
				}
			}));
		}
	}

}
