package de.galan.commons.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;


/**
 * Concurrent utilities.
 */
public class Concurrents {

	private static ExecutorService newFixedThreadPoolWithQueueSize(int nThreads, int queueSize) {
		return new ThreadPoolExecutor(nThreads, nThreads, 5000L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize, true),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}


	/**
	 * Construct a new ExecutorService with a fixed amount of threads. Invoker can submit tasks to service until
	 * queueSize has reached limit. In that case the invoker gets blocked until the queueSize shrinks.
	 */
	public static ExecutorService newBoundedExecutor(int threads, int queueSize) {
		return MoreExecutors.listeningDecorator(newFixedThreadPoolWithQueueSize(threads, queueSize));
	}

}
