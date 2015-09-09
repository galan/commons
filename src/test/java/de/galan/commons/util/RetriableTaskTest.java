package de.galan.commons.util;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT RetriableTask
 *
 * @author galan
 */
public class RetriableTaskTest extends AbstractTestParent {

	@Test
	public void doesNotComplete() throws Exception {
		try {
			RetriableTask<String> rs = new RetriableTask<String>(10, "100ms", new Callable<String>() {

				@Override
				public String call() throws Exception {
					throw new Exception("I always fail");
				}

			});
			rs.call();
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
		RetriableTask<String> rs = new RetriableTask<String>(10, "100ms", new Callable<String>() {

			int counter = 0;


			@Override
			public String call() throws Exception {
				if (counter++ < 9) {
					throw new Exception("fails 9 times");
				}
				return "returned";
			}

		}).message("complete");
		assertEquals("returned", rs.call());
	}


	@Test(expected = Error.class)
	public void infinite() throws Exception {
		RetriableTask<String> rs = new RetriableTask<String>(RetriableTask.INFINITE, "100ms", new Callable<String>() {

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
		assertEquals("returned", rs.call());
	}

}
