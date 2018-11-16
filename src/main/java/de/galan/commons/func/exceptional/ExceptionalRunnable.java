package de.galan.commons.func.exceptional;

/**
 * Functional Interface similar to Runnable, but could throw an Exception.
 */
@FunctionalInterface
public interface ExceptionalRunnable {

	void run() throws Exception;

}
