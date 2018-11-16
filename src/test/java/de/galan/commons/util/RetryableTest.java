package de.galan.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;

import de.galan.commons.logging.Say;


/**
 * CUT Retryable
 */
public class RetryableTest {

	@Test
	public void doesNotComplete() throws Exception {
		try {
			Retryable.retry(10L).timeToWait("100ms").call(new Callable<String>() {

				@Override
				public String call() throws Exception {
					throw new Exception("I always fail");
				}

			});
			fail("should not complete");
		}
		catch (RetryException ex) {
			assertEquals(10L, ex.getNumberOfRetries());
			assertEquals("100ms", ex.getTimeBetween());
		}
		catch (Exception ex) {
			assertEquals("I always fail", ex.getMessage());
		}
	}


	@Test
	public void complete() throws Exception {
		String result = Retryable.retry(10).timeToWait("100ms").message("complete").call(new Callable<String>() {

			int counter = 0;


			@Override
			public String call() throws Exception {
				if (counter++ < 9) {
					throw new Exception("fails 9 times");
				}
				return "returned";
			}
		});
		assertEquals("returned", result);
	}


	@Test
	public void compatibleExceptionalRunnable() throws Exception {
		Retryable.retry(1).timeToWait("100ms").message("complete").run(() -> Say.info("called runnabled"));
	}


	@Test
	public void infinite() throws Exception {
		assertThrows(Error.class, () -> {
			String call = Retryable.infinite().timeToWait(100L).call(new Callable<String>() {

				int counter = 0;


				@Override
				public String call() throws Exception {
					if (counter++ < 11) {
						throw new Exception("fails 11 times");
					}
					else if (counter >= 11) {// we have to abort the unit tests at some point ;)
						throw new Error("Should have been thrown");
					}
					return "failed";
				}

			});
			assertEquals("returned", call);
		});
	}

}
