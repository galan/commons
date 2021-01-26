package de.galan.commons.net;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;


/**
 * CUT CommonProxy
 */
public class CommonProxyTest {

	@Test
	public void name() throws Exception {
		assertProxy(new CommonProxy("1.1.1.1"), "1.1.1.1", 80, null, null, "1.1.1.1:80");
		assertProxy(new CommonProxy("1.1.1.1", 123), "1.1.1.1", 123, null, null, "1.1.1.1:123");
		assertProxy(new CommonProxy("1.1.1.1", null, "user", null), "1.1.1.1", 80, "user", null, "user@1.1.1.1:80");
		assertProxy(new CommonProxy("1.1.1.1", null, "user", "pass"), "1.1.1.1", 80, "user", "pass", "user:pass@1.1.1.1:80");
		assertProxy(new CommonProxy("1.1.1.1", null, null, "pass"), "1.1.1.1", 80, null, "pass", "1.1.1.1:80");
		assertProxy(CommonProxy.parse("1.1.1.1:666"), "1.1.1.1", 666, null, null, "1.1.1.1:666");
	}


	@Test
	public void testToString() throws Exception {
		assertNull(new CommonProxy(null).toString());
		assertEquals("1.2.3.4:222", new CommonProxy("1.2.3.4", 222).toString());
		assertEquals("1.2.3.4:80", new CommonProxy("1.2.3.4").toString());
	}


	private void assertProxy(CommonProxy cp, String ip, int port, String username, String password, String toString) {
		assertEquals(ip, cp.getIp());
		assertEquals(port, cp.getPort());
		assertEquals(username, cp.getUsername());
		assertEquals(password, cp.getPassword());
		assertEquals(toString, cp.toString());
	}

}
