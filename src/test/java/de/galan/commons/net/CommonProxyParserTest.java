package de.galan.commons.net;

import static org.junit.Assert.*;

import org.junit.Test;

import de.galan.commons.test.AbstractTestParent;


/**
 * CUT CommonProxyParser
 *
 * @author daniel
 */
public class CommonProxyParserTest extends AbstractTestParent {

	CommonProxyParser pp = new CommonProxyParser();


	@Test
	public void parse() {
		parse("1.2.3.4");
		parse("example.com");
		parse("some.example.com");
	}


	private void parse(String hostname) {
		assertValid(hostname, null, null, hostname, 80);
		assertValid(hostname + ":8888", null, null, hostname, 8888);
		assertValid(hostname + ":123", null, null, hostname, 123);
		assertValid("uuu@" + hostname, "uuu", null, hostname, 80);
		assertValid("uuu@" + hostname + ":123", "uuu", null, hostname, 123);
		assertValid("uuu:ppp@" + hostname, "uuu", "ppp", hostname, 80);
		assertValid("uuu:ppp@" + hostname + ":123", "uuu", "ppp", hostname, 123);
		assertValid("@" + hostname, null, null, hostname, 80);
		assertValid("uuu:@" + hostname, "uuu", null, hostname, 80);
		assertValid("uuu@ppp@" + hostname, "uuu", null, "ppp@" + hostname, 80); // seems to be valid for java.net.URL/URI
		assertValid(":ppp@" + hostname, null, "ppp", hostname, 80);
		assertValid("uuu:ppp@" + hostname + "@12323", "uuu", "ppp", hostname + "@12323", 80); // seems to be valid for java.net.URL/URI
		assertInvalid("uuu@ppp:" + hostname + "@12323");
		assertInvalid(hostname + ":8888,9999");
	}


	protected void assertInvalid(String proxy) {
		assertNull(pp.parse(proxy));
	}


	protected void assertValid(String proxy, String username, String password, String ip, int port) {
		CommonProxy result = pp.parse(proxy);
		assertEquals(ip, result.getIp());
		assertEquals(port, result.getPort());
		assertEquals(username, result.getUsername());
		assertEquals(password, result.getPassword());
	}

}
