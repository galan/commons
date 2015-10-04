package de.galan.commons.net;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.lang3.RandomUtils;


/**
 * Port-related utility methods.
 *
 * @author daniel
 */
public class Ports {

	/**
	 * Tries to find a free port in the range defined by the IANA for
	 * <a href="https://en.wikipedia.org/wiki/Ephemeral_ports">ephemeral ports</a>.
	 *
	 */
	public static Integer findFree() {
		return findFree(49152, 65535);
	}


	/** Returns a free port in the defined range, returns null if none is available. */
	public static Integer findFree(int lowIncluse, int highInclusive) {
		int low = Math.max(1, Math.min(lowIncluse, highInclusive));
		int high = Math.min(65535, Math.max(lowIncluse, highInclusive));
		Integer result = null;
		int split = RandomUtils.nextInt(low, high + 1);
		for (int port = split; port <= high; port++) {
			if (isFree(port)) {
				result = port;
				break;
			}
		}
		if (result == null) {
			for (int port = low; port < split; port++) {
				if (isFree(port)) {
					result = port;
					break;
				}
			}
		}
		return result;
	}


	public static boolean isFree(int port) {
		boolean result = false;
		try (ServerSocket socket = new ServerSocket(port)) {
			result = true;
		}
		catch (IOException ex) {
			// nada
		}
		return result;
	}

}
