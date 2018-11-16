package de.galan.commons.net;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

import java.net.ServerSocket;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;


/**
 * CUT Ports
 *
 * @author daniel
 */
public class PortsTest {

	@Test
	public void singlePort() throws Exception {
		Integer free = Ports.findFree(2000, 2000);
		assertThat(free).isEqualTo(2000);
	}


	@Test
	public void allInRange() throws Exception {
		Set<Integer> foundFree = IntStream.range(0, 100).mapToObj(x -> Ports.findFree(2000, 2002)).collect(toSet());
		assertThat(foundFree).containsOnly(2000, 2001, 2002);
	}


	@Test
	public void isFree() throws Exception {
		assertThat(Ports.isFree(3001)).isTrue();
		ServerSocket socket = new ServerSocket(3001);
		assertThat(Ports.isFree(3001)).isFalse();
		socket.close();
		assertThat(Ports.isFree(3001)).isTrue();
	}

}
